package com.mgcqr.jest.core.stuff;

import com.mgcqr.jest.core.enumeration.CardAim;
import com.mgcqr.jest.core.role.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

/**
 * The draw deck. In the first round, deal card to players, in after rounds, deal card to stack. 
 * @author Yufei Wu
 *
 */
public class Deck {

    protected Table table;

    protected Card[] deck;
    protected int index = -1;//从-1开始  index等于最后一个卡牌的下标

    public Deck() {}
    /**
     * The entrance of all the cards.
     * @param dose The amount of cards in the beginning of game.
     */
    public Deck(int dose, Table table) {//入参没什么意义 本来是给新卡准备的 固定传一个17
        this.table = table;
        deck = new Card[dose];

        Resource resource = new ClassPathResource("cards.txt");
        Scanner scanFile = null;
        try {
            InputStream inputStream = resource.getInputStream();
            scanFile = new Scanner(inputStream);//Read
        } catch (Exception e) {}

        scanFile.nextLine();//跳过标题

        while ( scanFile.hasNext() ) {
            cardIn( new Card(scanFile.nextLine()) );//逐行读文件创建卡牌
        }

    }
    /**
     * Put card in. Called by Deck.dealCard(Stack)
     * @param c
     * @return
     */
    public boolean cardIn(Card c) {
        if(index >= deck.length) return false;
        index++;
        deck[index] = c;
        return true;
    }
    /**
     * A method for class Stack, a child class of this, to set the length of Card[] deck.
     * @param l
     */
    protected void setLength(int l) {
        deck = new Card[l];
    }
    public void showDeck() {
        for(int i=0; i < deck.length; i++) {
            deck[i].show();
            System.out.println();
        }
    }
    /**
     * Shuffle
     */
    public void shuffle() {
        //Knuth-Durstenfeld Shuffle

        Random r = new Random();
        for(int i = deck.length - 1; i >=
                0; i--) {
            swap( r.nextInt(i+1),i );
        }

    }
    private void swap(int A, int B) {
        Card temp;
        temp = deck[A];
        deck[A] = deck[B];
        deck[B] = temp;
    }
    /**
     * Deal card to player.
     */
    public void dealCard() {//第一局发两张牌给所有玩家
        Joueur[] joueur = table.getJoueurs();
        for(int i = 0; i < Table.nbJoueur;i++) {
            // deal 2 cards
            joueur[i].cardIn(getCard(), CardAim.offer);
            joueur[i].cardIn(getCard(), CardAim.offer);
        }


    }
    /**
     * Deal card to stack.
     * @param stack
     */
    public void dealCard(Stack stack) {//发牌给Stack
        //System.out.println(Table.nbJoueur);
        for(int i = 0; i< Table.nbJoueur;i++) {
            stack.cardIn(getCard());
        }
    }
    /**
     * Get a card from deck. A low layer method to deal card. Only used by Table to deal card to Trophy.
     * @return
     */
    public Card getCard() {//发牌给trophy用
        Card c = deck[index];
        index--;
        return c ;
    }

    public boolean isEnpty() {
        return index == -1;
    }


}
