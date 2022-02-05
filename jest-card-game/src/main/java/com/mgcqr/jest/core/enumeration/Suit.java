package com.mgcqr.jest.core.enumeration;


/**
 * Define type of suit.
 * The order helps in define 
 * @author Yufei Wu
 *
 */
public enum Suit {
    Spade,Club,Diamond,Heart;

    /**
     * Convert input String to enum Suit
     * @param s String read from file.
     * @return enum Suit
     */
    public static Suit convertString(String s) {
        switch(s) {
            case "Heart": return Suit.Heart;
            case "Spade": return Suit.Spade;
            case "Club": return Suit.Club;
            case "Diamond": return Suit.Diamond;
            default: return null;
        }
    }
}
