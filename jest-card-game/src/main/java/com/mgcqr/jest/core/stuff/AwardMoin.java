package com.mgcqr.jest.core.stuff;

import com.mgcqr.jest.core.enumeration.Suit;
import com.mgcqr.jest.core.role.Joueur;

//lowest
public class AwardMoin implements Awarder {//遍历找最小值
    public int award(Condition c) {
        Joueur[] js = Table.getJoueurs();
        int tempID = -1;
        int temp = 5;
        Suit suit = c.getSuit();

        for(int i = 0; i < Table.nbJoueur; i++) {//player i
            int low = 5;
            int[] value = js[i].getJestValue();

            for(int j = 0; j < js[i].getJest().length; j++) {//card j
                if(js[i].getJest()[j].getSuit() == suit) {
                    if(value[j] < low) {
                        low = value[j];
                    }
                }
            }
            if(low < temp) {
                temp = low;
                tempID = i;
            }
        }
        return tempID;
    }
}
