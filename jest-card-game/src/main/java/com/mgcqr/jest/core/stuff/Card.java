package com.mgcqr.jest.core.stuff;

import com.mgcqr.jest.core.enumeration.*;

import java.util.Scanner;


public class Card {
    private Suit suit;
    private int value;
    private String name;
    private boolean faceUp = false;

    private Condition condition;

    public Card(String property) {//用 scanner拆分字符串
        Scanner scan = new Scanner(property);
        suit = Suit.convertString(scan.next());
        value = Integer.parseInt(scan.next());
        condition = new Condition(ConType.convertString(scan.next()), Suit.convertString(scan.next()), Integer.parseInt(scan.next()));
        if(suit == null) {
            name = "joker";
        }else {
            name = suit.toString() + value;
        }
        scan.close();
    }


    public Suit getSuit() {
        return suit;
    }
    public int getValue() {
        return value;
    }
    public String getName() {
        return name;
    }

    public void show() {

        System.out.printf("["+ suit + "\t"+ value + "]");

    }

    public boolean isGrater(Card c) {//拼点
        if(this.value > c.value) {
            return true;
        }
        else if(this.value < c.value) {
            return false;
        }
        else {
            if(this.suit == null) return true;//两张joker比较  避免NullPointerException
            if(this.suit.ordinal() <= c.suit.ordinal()) {
                //must be <= rather than <    used in Table.playRound()
                //相同牌返回True
                return true;
            }
            else {
                return false;
            }
        }

    }
    public Condition getCondition() {
        return condition;
    }
    public boolean isFaceUp() {return faceUp;}
    public void setFaceUp() {faceUp = true;}
    public void setFaceDown() {faceUp = false;}

}
