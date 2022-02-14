package com.mgcqr.jest.core.role;

import com.mgcqr.jest.core.enumeration.Operation;
import com.mgcqr.jest.core.stuff.Table;

public class AI extends Joueur {

    private int difficulty;

    public AI(int num,int diff) {
        super();
        super.nom = "AIplayer"+num;
        this.difficulty = diff;
        super.movement = new EasyAI();

    }

    public void jouer( Operation label, Table table) {//两种游戏步骤分别调用movement的方法

        if(label == Operation.MakeOffer) {
            movement.makeOffer(this,-1);
        }
        else if(label == Operation.TakeCard) {
            System.out.printf("Player %s (ID %d) is tacking card \n",nom,this.getID());
            movement.takeCard(this,0,null, table);
            //将代表自己的位置1 表示自己已经进行过takecard环节
            table.setHasTakenOffer(table.getHasTakenOffer() + (1<< this.getID()));
        }
        System.out.println();

    }

}
