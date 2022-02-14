package com.mgcqr.jest.core.role;

import com.mgcqr.jest.core.dto.MakeOfferInstructionDto;
import com.mgcqr.jest.core.dto.TakeCardDisplayDto;
import com.mgcqr.jest.core.dto.TakeCardInstructionDto;
import com.mgcqr.jest.core.enumeration.*;
import com.mgcqr.jest.core.stuff.Card;
import com.mgcqr.jest.core.stuff.Table;

import java.util.HashMap;
import java.util.Map;

public class HumanPlayer extends Joueur {
    public HumanPlayer(String nom, Movement mov) {
        super(nom,mov);
    }

    public void jouer( Operation label, Table table) {//两种游戏步骤分别调用movement的方法
        if(label == Operation.MakeOffer) {
            System.out.println(table.getCurrentPlayer().getNom()+" (ID"+table.getCurrentPlayer().getID()+ ") Making offer");
            this.getOffer()[0].show();
            this.getOffer()[1].show();
            System.out.println();

            MakeOfferInstructionDto dto = table.getMailBox().consume(MakeOfferInstructionDto.class);
            movement.makeOffer(this,dto.getChoice());
        }
        else if(label == Operation.TakeCard) {
            System.out.printf("Player %s (ID %d) is tacking card \n",nom,this.getID());

            System.out.println("Taking card");

            Joueur[] js = table.getJoueurs();
            for(int i = 0; i < table.getNbJoueur(); i++) {
                System.out.printf("Player %s (ID %d) :",js[i].nom,i);
                for(int k = 0; k < 2; k++) {
                    if(js[i].getOffer()[k] == null ) continue;
                    if ( js[i].getOffer()[k].isFaceUp() ) {
                        js[i].getOffer()[k].show();
                    }
                    else {
                        System.out.printf("[****\t*]");
                    }
                }
                System.out.println();
            }

            Map<String, Card[]> availableOffers = new HashMap<>();
            for(Joueur j : js){
                if(j.isAvaliable()){
                    availableOffers.put(j.getNom(), j.getOffer());
                }
            }
            //只有只剩唯一选项的时候才能选择自己的牌
            if(availableOffers.size() > 1){
                availableOffers.remove(nom);
            }
            TakeCardDisplayDto takeCardDisplayDto = new TakeCardDisplayDto();
            takeCardDisplayDto.setAvailableOffers(availableOffers);
            takeCardDisplayDto.setUserId(this.nom);
            table.getMailBox().produce(takeCardDisplayDto);


            System.out.println("To choose a card, input player ID :");
            TakeCardInstructionDto takeCardInstructionDto = table.getMailBox().consume(TakeCardInstructionDto.class);
            Integer playerID = takeCardInstructionDto.getPlayerID();
            System.out.println(playerID);

            table.setCurrentStep(Step.take_card_choose_card);

            System.out.print("Choose a card (true for the face-up, false for the face-down):");
            String targetCardName = takeCardInstructionDto.getCardName();
            System.out.println(targetCardName);

            movement.takeCard(this, playerID , targetCardName, table );


            //movement.takeCard(this);

            table.setHasTakenOffer(table.getHasTakenOffer() + (1<< this.getID()));//将代表自己的位置1 表示自己已经进行过takecard环节

            System.out.println();

        }
    }

}
//也没什么东西