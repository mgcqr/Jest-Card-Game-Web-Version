package com.mgcqr.jest.core.stuff;

import com.mgcqr.jest.core.role.*;

/**
 * Deal card to players since 2nd round.
 * Call super.dealCard() to deal cards to players. 
 * @author Yufei Wu
 *
 */
public class Stack extends Deck {

    public Stack(int length, Table table) {
        this.table = table;
        super.setLength(length);

    }

    /**
     * At end of round,first collect the card remained in players' offer, then get cards from deck.
     * Do NOT do this at last round.
     * Assuming that after each time deal card to players, the deck is clear,
     * just set index to 0 and collect cards for next round.
     * Called by Table.main()
     */
    public void collectCard() {//收集offer中剩下的牌  *并改为正面向下*
        Joueur[] joueurs = table.getJoueurs();
        Card c;
        for(int i = 0; i<table.getNbJoueur();i++) {
            c = joueurs[i].cardOut();
            c.setFaceDown();
            cardIn(c);
        }
    }
    public void clear() {//重置指针即清空
        super.index = -1;
    }

}
