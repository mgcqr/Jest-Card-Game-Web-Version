package com.mgcqr.jest.core.role;

import com.mgcqr.jest.core.enumeration.CardAim;
import com.mgcqr.jest.core.stuff.Card;
import com.mgcqr.jest.core.stuff.Table;

import java.util.Arrays;
import java.util.Random;

/**
 * Easy ai choose all the card by chance. 
 * @author Yufei Wu 
 *
 */
public class EasyAI implements Movement {

    // All choice made by chance
    public void makeOffer(Joueur j,int choice) {
        int i = 0;
        if(Math.random() < 0.5) {
            i = 1;
        }
        j.getOffer()[i].setFaceUp();

    }
    public void takeCard(Joueur j,int PlayerId,boolean isFaceUp, Table table) {
        int counter = 0;
        boolean[] avaliableIndex = new boolean[table.getNbJoueur()];
        Arrays.fill(avaliableIndex, false);

        Joueur[] js = table.getJoueurs();

        //---------------------------------------------------------------打印所有玩家的offer信息
        for(int i = 0; i < table.getNbJoueur(); i++) {
            System.out.printf("Player %s (ID %d) :",js[i].nom,i);
            for(int k = 0; k < 2; k++) {
                if(js[i].getOffer()[k] == null ) continue;
                if ( js[i].getOffer()[k].isFaceUp() ) {
                    js[i].getOffer()[k].show();
                }
                else {
                    System.out.printf("[****\t*]");
                }
            }
            System.out.println();
        }
        //-------------------------------------------------------------------跳过只有一张牌的的人 剩下的人里随机挑一个随机拿一张牌
        for(int i = 0; i < table.getNbJoueur(); i++) {//记录offer还有两张牌的人(isAvaliable == true) 并计数
            if(js[i].isAvalliable()) {
                avaliableIndex[i] = true;
                counter++;
            }
        }
        //counter表示的是可以被拿牌的人的数量
        if(counter > 1) {//如果不止1人有两张牌 当自己没有被拿牌的时候要排除自己 不能拿自己的牌 counter-- avaliableIndex也要设成false
            if(avaliableIndex[j.getID()] ) {
                counter--;
                avaliableIndex[j.getID()] = false;
            }
        }


        int[] playerIndex = new int[counter];//只记录所有可被拿牌玩家的编号 排除不可被拿牌的人
        int k = 0;
        for(int i = 0; i < table.getNbJoueur(); i++) {
            if(avaliableIndex[i]) {
                playerIndex[k] = i;
                k++;
            }
        }

        Random r = new Random();
        int aim = r.nextInt(counter);//[0,counter-1]
        //产生一个随机数访问这个随机下标的playerIndex拿到对应的玩家编号
        int playerID = playerIndex[aim];



        //offer里两张牌随机选一张 见joueur的方法定义
        boolean faceUp = r.nextBoolean();
        Card c = js[playerID].cardOut(faceUp);
        j.cardIn(c, CardAim.jest);
        Table.hasBeenTakenOffer = playerID;
    }
}
