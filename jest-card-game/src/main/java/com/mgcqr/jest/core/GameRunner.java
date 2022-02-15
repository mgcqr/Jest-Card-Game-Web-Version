package com.mgcqr.jest.core;

import com.mgcqr.jest.core.dto.*;
import com.mgcqr.jest.core.enumeration.Operation;
import com.mgcqr.jest.core.stuff.Card;
import com.mgcqr.jest.core.stuff.MailBox;
import com.mgcqr.jest.core.stuff.Table;
import com.mgcqr.jest.dto.ws.*;
import com.mgcqr.jest.enumeration.InstructionType;
import com.mgcqr.jest.model.RuntimeUserInfo;
import com.mgcqr.jest.service.WaitingHallService;
import com.mgcqr.jest.websocket.WebSocketRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameRunner {

    @Autowired
    private WebSocketRouter webSocketRouter;
    @Autowired
    private WaitingHallService waitingHallService;

    @Async
    public void runGame(String gameId, List<RuntimeUserInfo> users){
        if(users.size() != 3) return;
        CoreInterface coreInterface = new CoreInterface();

        List<String> userIds = users.stream().map(RuntimeUserInfo::getId).collect(Collectors.toList());
        //if registerGame fail then return
        if(! webSocketRouter.registerGame(userIds, coreInterface)) {
            webSocketRouter.cancelGame(userIds);
            waitingHallService.finishGame(gameId, null);
            return;
        }

        Table table = new Table(gameId);
        MailBox mailBoxIn = table.getMailBoxIn();
        MailBox mailBoxOut = table.getMailBoxOut();

        table.play();

        //Write game info to game core
        mailBoxIn.produce(new InitialInfoDto(userIds));

        //Distribute initial response to client :
        //game partner and trophy
        TrophyDisplayDto trophyDisplayDto = mailBoxOut.consume(TrophyDisplayDto.class);
        List<String> trophyName = cardArrayToNameList(trophyDisplayDto.getTrophy());
        for(int i = 0; i < 3; i++ ){
            InitialResDto resDto = new InitialResDto();
            resDto.setTrophy(trophyName);
            resDto.setLeftUser(users.get( (i + 2) % 3 ));
            resDto.setRightUser(users.get( (i + 1) % 3 ));
            webSocketRouter.sendMessage(userIds.get(i), resDto);
        }

        //Five rounds to play
        for(int round = 0; round < 5; round ++){
            //*********************
            //Make offer
            //*********************
            MakeOfferDisplayDto makeOfferDisplayDto = mailBoxOut.consume(MakeOfferDisplayDto.class);
            Map<String, Card[]> userOffers = makeOfferDisplayDto.getUserOffers();
            //Send all users their card in hand.
            for(String userId : userIds){
                MakeOfferResDto makeOfferResDto = new MakeOfferResDto();
                List<String> cardNames = cardArrayToNameList(userOffers.get(userId));
                makeOfferResDto.setOfferCardNames(cardNames);
                webSocketRouter.sendMessage(userId, makeOfferResDto);
            }
            //Collect client instruction of make offer
            Map<String, String> userIdToChosenCardName = new HashMap<>();
            for(int i = 0; i < 3; i++){
                GameInstructionDto instruction;
                do{
                    instruction = coreInterface.consume();
                }while (instruction.getType() != InstructionType.MakeOffer);
                userIdToChosenCardName.put(instruction.getUserId(), instruction.getCardName());
            }
            //produce choices to mailBox in order of user
            for(String userId : userIds){
                String chosenName = userIdToChosenCardName.get(userId);
                int choice;
                if(userOffers.get(userId)[0].getName().equals(chosenName)){
                    choice = 0;
                }else {
                    choice = 1;
                }
                //produce to mail box
                mailBoxIn.produce(new MakeOfferInstructionDto(choice));

                //Broadcast result of make offer to
                InfoBroadcastDto infoBroadcastDto = new InfoBroadcastDto();
                infoBroadcastDto.setOperation(Operation.MakeOffer);
                infoBroadcastDto.setUserId(userId);
                infoBroadcastDto.setCardName(chosenName);
                webSocketRouter.multicast(userIds, infoBroadcastDto);

            }

            //*********************
            //Take Card
            //*********************
            for(int i = 0; i < 3; i++){
                //send response
                TakeCardDisplayDto takeCardDisplayDto = mailBoxOut.consume(TakeCardDisplayDto.class);
                Map<String, Card[]> avaOffer = takeCardDisplayDto.getAvailableOffers();
                Map<String, List<String>> avaOfferCardName = new HashMap<>();
                for(String key : avaOffer.keySet()){
                    Card[] cards = avaOffer.get(key);
                    avaOfferCardName.put(key, cardArrayToNameList(cards));
                }
                webSocketRouter.sendMessage(takeCardDisplayDto.getUserId(), new TakeCardResDto(avaOfferCardName));

                //get instruction
                GameInstructionDto instructionDto;
                do{
                    instructionDto = coreInterface.consume();
                }while (instructionDto.getType() != InstructionType.TakeCard);

                //produce in mail box
                int targetNum = 0;
                for(int k = 0; k < 3; k++){
                    if(userIds.get(k).equals(instructionDto.getTargetUserId())){
                        targetNum = k;
                        break;
                    }
                }
                mailBoxIn.produce(new TakeCardInstructionDto(targetNum, instructionDto.getCardName()));

                //Inform all
                InfoBroadcastDto infoBroadcastDto = new InfoBroadcastDto();
                infoBroadcastDto.setOperation(Operation.TakeCard);
                infoBroadcastDto.setUserId(takeCardDisplayDto.getUserId());
                infoBroadcastDto.setTargetUserId(instructionDto.getTargetUserId());
                infoBroadcastDto.setCardName(instructionDto.getCardName());
                webSocketRouter.multicast(userIds, infoBroadcastDto);
            }

        }
        GameResultDto resultDto = mailBoxOut.consume(GameResultDto.class);
        GameResultResDto gameResultResDto = new GameResultResDto();
        gameResultResDto.setResults(resultDto.getResults());
        webSocketRouter.multicast(userIds, gameResultResDto);


        webSocketRouter.cancelGame(userIds);
        waitingHallService.finishGame(gameId, resultDto);
    }

    private List<String> cardArrayToNameList(Card[] cards){
        List<String> res = new ArrayList<>();
        for (Card card : cards) {
            res.add(card.getName());
        }
        return  res;
    }
}
