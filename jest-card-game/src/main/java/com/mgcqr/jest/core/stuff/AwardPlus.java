package com.mgcqr.jest.core.stuff;

import com.mgcqr.jest.core.enumeration.Suit;
import com.mgcqr.jest.core.role.Calculator;
import com.mgcqr.jest.core.role.Joueur;


public class AwardPlus implements Awarder {
    public int award(Condition c) {
        Joueur[] js = Table.getJoueurs();
        int value = c.getValue();
        Suit suit = c.getSuit();

        if(suit == null && value == 0 ) {//highest score (best jest)  逐个算分遍历找最大值
            int tempID = -1;
            int temp = -100;
            for(int i = 0; i < Table.nbJoueur; i++) {
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
        else if(suit == null) {//most amount of card of certain value (Majority)  遍历找最大值
            int tempID = -1;
            int temp = 0;
            Suit tempSuit = Suit.Heart;
            for(int i = 0; i < Table.nbJoueur; i++) {//player i
                int counter = 0;
                Suit strongSuit = Suit.Heart;
                int[] values = js[i].getJestValue();
                for(int j = 0; j < js[i].getJest().length; j++) {//card j
                    if(values[j] == value) {
                        counter++;
                        if(js[i].getJest()[j].getSuit().ordinal() <= strongSuit.ordinal()) {//拼点
                            strongSuit = js[i].getJest()[j].getSuit();
                        }
                    }
                }
                if(counter > temp) {//如果数量更多 直接替换
                    temp = counter;
                    tempID = i;
                    tempSuit = strongSuit;
                }
                else if(counter == temp) {//如果数量相等  拼点
                    if (strongSuit.ordinal() <= tempSuit.ordinal() ) {
                        tempID = i;
                        tempSuit = strongSuit;
                    }
                }
            }
            return tempID;
        }
        else {//highest value of card of certain suit (Highest)  遍历找最大值  同一花色不会有相同value 所有比较不必取等号
            int tempID = -1;
            int temp = 0;
            for(int i = 0; i < Table.nbJoueur; i++) {//player i
                int high = 0;
                int[] values = js[i].getJestValue();
                for(int j = 0; j < js[i].getJest().length; j++) {//card j
                    if(js[i].getJest()[j].getSuit() == suit) {
                        if(values[j] > high) {
                            high = values[j];
                        }
                    }
                }
                if(high > temp) {
                    temp = high;
                    tempID = i;
                }
            }
            return tempID;
        }
    }
}
