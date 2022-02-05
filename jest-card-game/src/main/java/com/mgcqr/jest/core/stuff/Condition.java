package com.mgcqr.jest.core.stuff;

import com.mgcqr.jest.core.enumeration.*;
import com.mgcqr.jest.core.role.*;


/**
 * The condition define who will be presented the trophy when the card works as a trophy in a game.
 *
 * @author Yufei Wu
 *
 */
public class Condition {

    private ConType type;
    //"plus" "moin" "joker" "plus_san_joker"
    private Suit suit;
    private int value;
    public Awarder awarder = null;

    public Condition(ConType type, Suit suit, int value) {
        this.type = type;
        this.suit = suit;
        this.value = value;

    }

    public void show() {
        System.out.print("/* condition */\t");
        System.out.print(type+"\t");
        System.out.print(suit+"\t");
        System.out.println(value);
    }
    public Suit getSuit() {
        return suit;
    }
    public int getValue() {
        return value;
    }


    /**
     *
     * @return index of the player who wins the trophy.
     */
    public int award() {//只有trophy才会被调用这个方法  只需要给trophy实例化对应的Awarder
        this.awarder = Awarder.getInstance(type);
        return this.awarder.award(this);
    }


}
