package com.mgcqr.jest.core.stuff;

import com.mgcqr.jest.core.dto.*;
import com.mgcqr.jest.core.enumeration.*;
import com.mgcqr.jest.core.role.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The method main() host the flow of game.
 *
 * @author Yufei Wu
 */
public class Table implements Runnable {

    private Thread t;
    private String gameId;

    private Deck deck;
    private Stack stack;
    private Card trophy[];
    private boolean awardInOrder = false;
    private int currentPlayer;
    private Step currentStep;
    private Joueur joueurs[];
    private int round = 0;
    private String[] noms;

    final public static int nbCartDefault = 17;

    @Getter
    private final MailBox mailBox;
    @Getter
    private int nbJoueur = 0;
    private int nbAI;
    private GameMode gameMode;
    @Getter@Setter
    private int hasTakenOffer = 0;//used as a list of boolean,  1 for true
    @Setter
    private int hasBeenTakenOffer;//Index of player whose card has been taken last time.
    //public static Card weekestCard = new Card ("Heart	1	joker	null	0");//weekest card in deciding order


    public Table(String gameId) {
        this.mailBox = new MailBox();
        this.gameId = gameId;
    }


    public void run() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        InitialInfoDto dto = mailBox.consume(InitialInfoDto.class);
        this.setParametre(3, 0, dto.getUserIds().toArray(new String[]{}), GameMode.Original);

        initialiser();

        mailBox.produce(new TrophyDisplayDto(trophy));

        setCurrentStep(Step.start);
        deck.showDeck();
        deck.dealCard();
        while (!deck.isEnpty()) {//倒数第二个回合会把牌发空  单写最后一个回合
            playRound();
        }
        playRound();//last round

        System.out.println("/************ Game Over ******************/");

        awardTrophy();//发奖杯


        for (int i = 0; i < nbJoueur; i++) {
            joueurs[i].showJest();
        }

        for (int i = 0; i < nbJoueur; i++) {//结算
            joueurs[i].accept(new Calculator());
            System.out.printf("Score of %s (ID %d) is :", joueurs[i].getNom(), i);
            System.out.println(joueurs[i].getScore());
        }
        List<UserGameResult> results = new ArrayList<>();
        for(int i = 0; i < nbJoueur; i++){
            UserGameResult userRes = new UserGameResult();
            userRes.setUserId(noms[i]);
            userRes.setJest(joueurs[i].getJest());
            userRes.setScore(joueurs[i].getScore());
            results.add(userRes);
        }
        mailBox.produce(new GameResultDto(results));
        setCurrentStep(Step.finish);
    }

    public Joueur getCurrentPlayer() {
        return joueurs[currentPlayer];
    }

    public Step getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Step step) {
        currentStep = step;
//        setChanged();
//        notifyObservers(currentStep);

    }

    public void setParametre(int nbPlayer, int nbAI, String[] names, GameMode gm) {
        this.nbJoueur = nbPlayer;
        this.nbAI = nbAI;
        this.noms = names;
        this.gameMode = gm;
    }

    /**
     * Set all the players.Define play mode.
     * Initialize deck, stack, trophy,and shuffle deck.
     */
    private void initialiser() {


        //generate players
        ArrayList<Joueur> al = new ArrayList<Joueur>();
        for (int i = 0; i < noms.length; i++) {
            al.add(new HumanPlayer(noms[i], new HumanMove()));
        }
        for (int i = 0; i < nbAI; i++) {
            al.add(new AI(i, 1));
        }
        joueurs = new Joueur[nbJoueur];
        joueurs = (Joueur[]) al.toArray(new Joueur[0]);

        //generate deck and stack
        deck = new Deck(nbCartDefault, this);
        deck.shuffle();
        stack = new Stack(nbJoueur * 2, this);

        trophy = new Card[5 - nbJoueur];//1 for 4 player  /2 for 3 player
        boolean hasHeart = false;
        boolean hasJoker = false;
        for (int i = 0; i < trophy.length; i++) {//发牌并判断有无红桃和joker
            trophy[i] = deck.getCard();
            if (trophy[i].getSuit() == null) {
                hasJoker = true;
            }
            if (trophy[i].getSuit() == Suit.Heart) {
                hasHeart = true;
            }
        }
        awardInOrder = hasHeart && hasJoker;
        if (awardInOrder) {
            if (trophy[0].getSuit() == Suit.Heart) {//调换顺序 joker在前
                Card tempCard;
                tempCard = trophy[0];
                trophy[0] = trophy[1];
                trophy[1] = tempCard;

            }
        }


        round++;

    }


    /**
     * Start by make offer, end with deal card.
     */
    private void playRound() {//见附录6
        int allHaveTakenOffer;
        if (nbJoueur == 3) {
            allHaveTakenOffer = 7;//0b0111
        } else {
            allHaveTakenOffer = 15;//0b1111
        }
        hasTakenOffer = 0;

        System.out.printf("/************ Round %d ******************/\n", round);
        // step of making offer
        //make offer 的展示信息全部发出再逐个等Instruction
        //异步到达的Instruction由GameRunner排好序按序发到mailbox
        Map<String, Card[]> offers = new HashMap<>();
        for (int i = 0; i < nbJoueur; i++){
            offers.put(noms[i], joueurs[i].getOffer());
        }
        MakeOfferDisplayDto makeOfferDisplayDto = new MakeOfferDisplayDto();
        makeOfferDisplayDto.setUserOffers(offers);
        mailBox.produce(makeOfferDisplayDto);

        for (int i = 0; i < nbJoueur; i++) {
            currentPlayer = i;
            setCurrentStep(Step.make_offer);
            joueurs[i].jouer(Operation.make_offer, this);
        }


        // step of taking card
        //setCurrentStep(Step.take_card_choose_ID);
        while (hasTakenOffer < allHaveTakenOffer) {


            //find the first player to take card.
            int tempIndex = 0;
            Card tempCard = null;
            for (int i = 0; i < nbJoueur; i++) {
                if (((hasTakenOffer >> i) & 1) == 1) continue;
                if (tempCard == null) tempCard = joueurs[i].getOfferFaceUp();
                if (joueurs[i].getOfferFaceUp().isGrater(tempCard)) {
                    tempIndex = i;
                    tempCard = joueurs[i].getOfferFaceUp();
                }
            }


            int cardTaker = tempIndex;
            while (((hasTakenOffer >> cardTaker) & 1) == 0) {
                currentPlayer = cardTaker;
                setCurrentStep(Step.take_card_choose_ID);
                joueurs[cardTaker].jouer(Operation.take_card, this);
                cardTaker = hasBeenTakenOffer;
            }


        }


        //step collect card and deal card
        if (deck.isEnpty()) {
            for (int i = 0; i < nbJoueur; i++) {
                joueurs[i].tackBackOffer();
            }
        } else {
            stack.clear();
            stack.collectCard();
            deck.dealCard(stack);
            stack.shuffle();
            stack.dealCard();
        }

        round++;

    }


    private void awardTrophy() {
        if (gameMode == GameMode.Original) {
            if (awardInOrder) {//获得playerID直接加入jest
                int winnerOfTrophy = 0;
                for (int i = 0; i < trophy.length; i++) {
                    winnerOfTrophy = trophy[i].getCondition().award(this);
                    joueurs[winnerOfTrophy].cardIn(trophy[i], CardAim.jest);
                }
            } else {//缓存playerID 统一加入jest
                int[] winnerOfTrophy = new int[trophy.length];
                for (int i = 0; i < trophy.length; i++) {
                    winnerOfTrophy[i] = trophy[i].getCondition().award(this);
                }
                for (int i = 0; i < trophy.length; i++) {
                    joueurs[winnerOfTrophy[i]].cardIn(trophy[i], CardAim.jest);
                }
            }
        } else if (gameMode == GameMode.Extend_Rule) {
            for (int i = 0; i < joueurs.length; i++) {
                for (int j = 0; j < trophy.length; j++) {
                    joueurs[i].cardIn(trophy[j], CardAim.jest);
                }
            }
        }

    }

    public void play() {
        if (t == null) {
            t = new Thread(null, this, "game id:" + gameId);
            t.start();
        }

    }

    /**
     * 直接以命令行模式启动
     * @param args
     */
    public static void main(String args[]) {
        Table t = new Table("Local Game");
        t.play();

    }


    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public int getRound() {
        return round;
    }

    public Card[] getTrophy() {
        return trophy;
    }


}
