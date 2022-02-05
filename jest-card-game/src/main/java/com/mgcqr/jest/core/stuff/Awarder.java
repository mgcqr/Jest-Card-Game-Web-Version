package com.mgcqr.jest.core.stuff;
import com.mgcqr.jest.core.enumeration.*;

public interface Awarder {
    /**
     *
     * @param c
     * @return Return the playerID of player who wins the trophy.
     */
    public int award(Condition c);

    public static Awarder getInstance(ConType type) {//按condition种类不同返回不同的对象    说实话我觉得这个case避免不了 可能有别的思路吧
        switch(type) {
            case joker:
                return new AwardJoker();
            case moin:
                return new AwardMoin();
            case plus:
                return new AwardPlus();
            case plus_sans_joker:
                return new AwardPlusSansJoker();
            default:
                return null;
        }
    }
}
