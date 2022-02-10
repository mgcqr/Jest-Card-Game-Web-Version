package com.mgcqr.jest.core.role;

//import java.util.Scanner;

import com.mgcqr.jest.core.enumeration.*;
import com.mgcqr.jest.core.stuff.*;

import java.util.ArrayList;

/**
 * Attribute offer : After Movement.makeOffer() offer[0] face-up
 * @author Yufei Wu
 *
 */
public abstract class Joueur implements Element {


    protected String nom;
    private ArrayList<Card> jest = new ArrayList<Card>();
    private ArrayList<Integer> valueAsIsFive = new ArrayList<Integer>();
    private Card[] offer = {null,null};
    private int playerID;
    private int score = 0;
    protected Movement movement;

    private static int idCounter = 0;

    public Joueur() {//playerID就是这个玩家在Table的玩家数组中的下标
        playerID = idCounter;
        idCounter++;
    }
    public Joueur( String nom, Movement mov) {
        this();
        this.nom = nom;
        this.movement = mov;

    }

    /**
     *
     * @param label Only accept "make_offer" and c
     */
    public abstract void jouer( Operation label, Table table);


    /**
     * In the last round, take the left card in offer to jest
     */
    public void tackBackOffer() {//把本该给stack的牌放进自己的jest 所以也是调用cardOut()
        this.cardIn(this.cardOut(), CardAim.jest);
    }

    /**
     * Cards can only be taken out from offer.
     * Called by Movement.takeCard()
     * Must set reference to null when a Card is taken.
     * @return Card return null for illegal input.
     */
    public Card cardOut(boolean cardIsFaceUp) {//挑选正放或反放的牌返回   *并将指针回空*

        for(int i = 0; i < 2; i++) {
            if (offer[i].isFaceUp() == cardIsFaceUp) {
                Card c = offer[i];
                offer[i] = null;
                return c;
            }
        }
        return null;
    }
    /**
     * Called by stuff.Stack.collectCard() and tackBackOffer()
     * @return
     */
    public Card cardOut() {//返回非空的牌   *并将指针回空*
        Card c;
        if(offer[0] != null ^ offer[1] != null) {//XOR 异或
            if(offer[0] != null) {
                c = offer[0];
                offer[0] = null;
            }
            else {
                c = offer[1];
                offer[1] = null;
            }
            return c;
        }
        else {
            return null;
        }


    }

    /**
     * Put card in.
     * Called by Movement.takeOffer(), Deck.dealCard(Joueur[])
     * maintain valueAsIsFive
     * @param c The input card.
     * @param aim To where input the card. Only "offer" and "jest" are accepted.
     * @return insert succeed return true,failed return false
     */
    public boolean cardIn(Card c, CardAim aim) {
        if(aim == CardAim.offer) {//Deck 和 Stack 来的牌进入offer 找个非空的地方放下
            for(int i = 0;i<2;i++) {
                if(offer[i] == null) {
                    offer[i] = c;
                    return true;
                }
            }
            return false;
        }
        else if(aim == CardAim.jest) {//从玩家处拿来的牌进入jest
            jest.add(c);
            valueAsIsFive.add(c.getValue());

            //维护valueAsIsFive
            for(int i = 0; i < jest.size(); i++) {//扫描jest 每当字面value是1 默认置5 找同花色牌 找到置1
                if(jest.get(i).getValue() == 1) {
                    valueAsIsFive.set(i, 5);
                    for(int j = 0; j < jest.size(); j++) {
                        if(jest.get(j).getSuit() == jest.get(i).getSuit() && j!=i) {
                            valueAsIsFive.set(i, 1);
                            break;
                        }
                    }
                }
            }

            return true;
        }else return false;
    }

    /**
     * If offer is full.
     * @return
     */
    public boolean isAvalliable() {
        return (offer[0] != null && offer[1] != null);
    }

    public Card[] getOffer() {
        return offer;
    }

    public Card getOfferFaceUp() {//返回offer里正面向上的牌  用于决定拿牌顺序
        if(offer[0].isFaceUp()) {
            return offer[0];
        }
        else return offer[1];

    }

    public void setScore(int s) {
        this.score = s;
    }
    public int getScore() {
        return score;
    }
    public Card[] getJest() {//转成数组型好操作
        return (Card[]) jest.toArray(new Card[0]);
    }
    public String getName() {
        return nom;
    }
    public void accept(Visitor visitor) {//访问者模式  传入一个访问者对象 调用这个访问者的visit方法  新加的方法写在visit方法里
        visitor.visit(this);
    }
    public void showJest() {
        for(int i = 0; i < jest.size(); i++) {
            jest.get(i).show();
        }
        System.out.println();
    }
    public int getID() {
        return playerID;
    }
    public Movement getMovement() {
        return movement;
    }
    /**
     *
     * @return A consequence of {@code int} containing values of card in jest. As has been converted to 5 when possible.
     */
    public int[] getJestValue() {//转成数组型好操作
        Integer[] v =  valueAsIsFive.toArray(new Integer[0]);
        int[] value = new int[v.length];
        for(int i = 0; i < v.length; i++) {
            value[i] = v[i].intValue();
        }
        return  value;
    }
    //compare the order of cards
    public Card getGreatestCard() {//找最高优先级的牌
        Card temp = null;
        for(int i = 0; i < jest.size();i++) {
            if(temp == null) temp = jest.get(i);
            if(jest.get(i).isGrater(temp)) {
                temp = jest.get(i);
            }
        }
        return temp;
    }

}
