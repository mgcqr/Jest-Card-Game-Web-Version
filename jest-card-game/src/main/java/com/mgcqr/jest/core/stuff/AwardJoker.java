package com.mgcqr.jest.core.stuff;

import com.mgcqr.jest.core.role.Joueur;

public class AwardJoker implements Awarder {//遍历找joker joker的suit是null
    public int award(Condition c) {
        Joueur[] js = Table.getJoueurs();
        for(int i = 0; i < Table.nbJoueur; i++) {
            for(int j = 0; j < js[i].getJest().length; j++) {
                if(js[i].getJest()[j].getSuit() == null) {
                    return i;
                }
            }
        }
        return -1;
    }
}
