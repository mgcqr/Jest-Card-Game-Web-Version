package com.mgcqr.jest.core.role;

import com.mgcqr.jest.core.enumeration.*;
import com.mgcqr.jest.core.stuff.MailBox;
import com.mgcqr.jest.core.stuff.Table;

public class HumanPlayer extends Joueur {
    public HumanPlayer(String nom, Movement mov) {
        super(nom,mov);
    }

    public void jouer( Operation label) {//两种游戏步骤分别调用movement的方法
        if(label == Operation.make_offer) {
            System.out.println(Table.getInstance().getCurrentPlayer().getName()+" (ID"+Table.getInstance().getCurrentPlayer().getID()+ ") Making offer");
            this.getOffer()[0].show();
            this.getOffer()[1].show();
            System.out.println();

            int choice = 0;

            boolean condi = true;
            while(condi) {
                Object o = MailBox.consume();
                if(o instanceof Integer) {
                    choice = (int)o;
                    condi = false;
                }

            }
            movement.makeOffer(this,choice);
        }
        else if(label == Operation.take_card) {
            System.out.printf("Player %s (ID %d) is tacking card \n",nom,this.getID());

            System.out.println("Taking card");

            Joueur[] js = Table.getJoueurs();
            for(int i = 0; i < Table.nbJoueur; i++) {
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

            int playerID = 0;
            boolean faceUp = false;


            boolean condi = true;
            while(condi) {
                Object o = MailBox.consume();
                if(o instanceof Integer) {
                    playerID = (int)o;
                    condi = false;
                }

            }
            System.out.println(playerID);

            Table.getInstance().setCurrentStep(Step.take_card_choose_card);

            condi = true;
            while(condi) {
                Object o = MailBox.consume();
                if(o instanceof Boolean) {
                    faceUp = (boolean)o;
                    condi = false;
                }

            }

            System.out.println(faceUp);

            movement.takeCard(this, playerID , faceUp );


            //movement.takeCard(this);

            Table.hasTakenOffer += (1<< this.getID());//将代表自己的位置1 表示自己已经进行过takecard环节

            System.out.println();

        }
    }

}
//也没什么东西