package com.mgcqr.jest.core.role;

import com.mgcqr.jest.core.enumeration.CardAim;
import com.mgcqr.jest.core.stuff.*;

/**
 * Define the movement of human. Concentrate on input and output.
 * @author Yufei Wu 
 *
 */

//就是各种打印、读输入、读到输入调role.Joueur.cardOut(boolean cardIsFaceUp)
//没什么东西
public class HumanMove implements Movement {
    public void makeOffer(Joueur j , int choice) {


//		while (s != 0 && s != 1) {
//			System.out.println("Choose a card to be shown face-up: (0 for the left, 1 for the right)");
//			s = Table.scan.nextInt();
//		}

        System.out.println(choice);

        j.getOffer()[choice].setFaceUp();



    }
    public void takeCard(Joueur j, int playerID,boolean faceUp) {
        Joueur[] js = Table.getJoueurs();




        System.out.printf("To choose a card, input player ID :");
        //int playerID = Table.scan.nextInt();


        System.out.print("Choose a card (true for the face-up, false for the face-down):");
        //boolean faceUp = Table.scan.nextBoolean();
        Card c = js[playerID].cardOut(faceUp);
        j.cardIn(c, CardAim.jest);

        Table.hasBeenTakenOffer = playerID;


    }
}
