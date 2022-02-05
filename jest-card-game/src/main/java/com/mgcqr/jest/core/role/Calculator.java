package com.mgcqr.jest.core.role;

import com.mgcqr.jest.core.enumeration.Suit;
import com.mgcqr.jest.core.stuff.*;

/**
 * Using visitor pattern to calculate the score of jest
 * @author Yufei Wu 
 *
 */
public class Calculator  implements Visitor  {
    //用访问者模式算分
    public void visit(Element e) throws ClassCastException {
        Joueur jou;
        if(e instanceof Joueur) {
            jou = (Joueur) e;
        }
        else {
            throw new ClassCastException();
        }

        Card[] jest = jou.getJest();
        int[] value = jou.getJestValue();//返回value序列 这里1已经换成5了
        int score = 0;
        int scoreHeart = 0;
        boolean hasJoker = false;



        //分别计算红桃的分数和其他三花色的分数 最后依情况相加相减或忽略
        //草花或黑桃就相加 方片就减，红桃就加在scoreHeart上
        // Calculate score without Hearts and calculate score of Heart
        for(int i = 0; i < jest.length; i++) {
            if(jest[i].getSuit() == Suit.Club || jest[i].getSuit() == Suit.Spade ) {
                score += value[i];
            }
            else if(jest[i].getSuit() == Suit.Diamond ) {
                score -= value[i];
            }
            else if(jest[i].getSuit() == Suit.Heart) {
                scoreHeart += value[i];
            }
            else hasJoker = true;
        }

        //计算对子
        //扫描jest 每找到一张黑桃拍就找同value的草花牌 找到就加分
        //黑桃5和草花1不认为是对子
        //pair
        for(int i = 0; i < jest.length; i++) {

            if(jest[i].getSuit() == Suit.Club ) {

                for(int j = 0; j < jest.length; j++) {

                    if(jest[j].getSuit() == Suit.Spade && value[i] == value[j] ) {
                        score += 2;
                    }
                }
            }
        }
        //如果没有joker 结束
        //如果有joker 红桃分为10 有所有的红桃，+10   红桃分为0，没有红桃，joker +4
        if(hasJoker) {
            if(scoreHeart == 10) {// got all Hearts
                score += 10;
            }
            else if(scoreHeart == 0) {//got no Heart
                score += 4;
            }
            else {
                score -= scoreHeart;
            }
        }

        jou.setScore(score);
    }
}
