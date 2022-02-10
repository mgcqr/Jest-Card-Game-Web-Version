package com.mgcqr.jest.core.stuff;

import com.mgcqr.jest.core.role.Calculator;
import com.mgcqr.jest.core.role.Joueur;

//highest score without joker
public class AwardPlusSansJoker implements Awarder{
    public int award(Condition c, Table table) {
        Joueur[] js = table.getJoueurs();
        int tempID = -1;
        int temp = -100;

        Awarder a = new AwardJoker();
        int jokerIndex = a.award(c, table);


        for(int i = 0; i < table.getNbJoueur(); i++) {
            if(i == jokerIndex) continue;//skip the player who has joker  跳过有joker的人  其他跟best jest完全一样

            js[i].accept(new Calculator());

            if(js[i].getScore() > temp) {
                temp = js[i].getScore();
                tempID = i;
            }
            else if(js[i].getScore() == temp) {
                if(js[i].getGreatestCard().isGrater(js[tempID].getGreatestCard())){
                    tempID = i;
                }
            }
        }
        return tempID;

    }
}
