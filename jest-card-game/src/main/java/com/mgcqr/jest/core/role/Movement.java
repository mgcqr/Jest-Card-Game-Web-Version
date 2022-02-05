package com.mgcqr.jest.core.role;
//import com.mgcqr.jest.core.stuff.*;
public interface Movement {
    public void makeOffer(Joueur j, int choice);
    /**
     *
     * @param j
     * @return Return playerID of the player whose card has been taken
     */
    public void takeCard(Joueur j,int PlayerId,boolean isFaceUp);
}
//返回被拿牌玩家的id
