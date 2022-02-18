package com.mgcqr.jest.core.role;

import com.mgcqr.jest.core.dto.TakeCardNameDisplayDto;
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

        System.out.println(choice);

        j.getOffer()[choice].setFaceUp();



    }
    public void takeCard(Joueur j, int playerID, boolean isFaceUp, Table table) {
        Joueur[] js = table.getJoueurs();

        Card c = js[playerID].cardOut(isFaceUp);
        j.cardIn(c, CardAim.jest);
        table.getMailBoxOut().produce(new TakeCardNameDisplayDto(c.getName()));

        table.setHasBeenTakenOffer(playerID);

    }
}
