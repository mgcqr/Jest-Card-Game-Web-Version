package com.mgcqr.jest.core.view;

import com.mgcqr.jest.core.controller.Controller;
import com.mgcqr.jest.core.enumeration.Step;
import com.mgcqr.jest.core.role.Joueur;
import com.mgcqr.jest.core.stuff.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GameInterface implements Observer {

    private JFrame frame;
    private JButton button[] = new JButton[4];
    private JLabel[] jest = new JLabel[7];
    private JLabel[] offerLabel = new JLabel[4];
    private JLabel[][] offers = new JLabel[4][2];
    private JLabel[] trophy = new JLabel[2];
    private JLabel[] result = new JLabel[4];
    private JLabel lblInform;

    public void update(Observable o , Object arg ) {
        if(arg instanceof Step) {
            Step currentStep = (Step) arg;


            showJest();
            if(currentStep == Step.start) {
                Card[] trophyCard = Table.getInstance().getTrophy();
                String trace;
                ImageIcon icon;
                for(int i = 0; i < trophyCard.length; i++) {
                    trophy[i].setVisible(true);
                    trace = trophyCard[i].getImage();
                    icon = new ImageIcon(trace);
                    trophy[i].setIcon(icon);
                }
            }
            if(currentStep == Step.make_offer) {
                resetButton();
                resetOffer();

                Joueur j =Table.getInstance().getCurrentPlayer();
                Card[] playerOffer = j.getOffer();

                String trace;
                ImageIcon icon;
                offers[1][0].setVisible(true);
                trace = playerOffer[0].getImage();
                icon = new ImageIcon(trace);
                offers[1][0].setIcon(icon);

                offers[1][1].setVisible(true);
                trace = playerOffer[1].getImage();
                icon = new ImageIcon(trace);
                offers[1][1].setIcon(icon);

                String name = Table.getInstance().getCurrentPlayer().getName();
                int id = Table.getInstance().getCurrentPlayer().getID();
                lblInform.setText(name+ "(ID"+id+"),choose a card to be shown face up");
                button[0].setVisible(true);
                button[0].setText("Left");

                button[1].setVisible(true);
                button[1].setText("Right");


            }
            if(currentStep == Step.take_card_choose_ID) {

                resetButton();
                resetOffer();


                int counter = 0;
                boolean[] avaliableIndex = new boolean[Table.nbJoueur];
                Arrays.fill(avaliableIndex, false);

                Joueur[] js = Table.getJoueurs();
                //-------------------------------------------------------------------跳过只有一张牌的的人 剩下的人里随机挑一个随机拿一张牌
                for(int i = 0; i < Table.nbJoueur; i++) {//记录offer还有两张牌的人(isAvaliable == true) 并计数
                    if(js[i].isAvalliable()) {
                        avaliableIndex[i] = true;
                        counter++;
                    }
                }
                //counter表示的是可以被拿牌的人的数量
                if(counter > 1) {//如果不止1人有两张牌 当自己没有被拿牌的时候要排除自己 不能拿自己的牌 counter-- avaliableIndex也要设成false
                    if(avaliableIndex[Table.getInstance().getCurrentPlayer().getID()] ) {
                        counter--;
                        avaliableIndex[Table.getInstance().getCurrentPlayer().getID()] = false;
                    }
                }

                String name = Table.getInstance().getCurrentPlayer().getName();
                int id = Table.getInstance().getCurrentPlayer().getID();
                lblInform.setText(name+ "(ID"+id+"),choose a playe ID");
                for(int i = 0; i<Table.nbJoueur; i++) {
                    button[i].setVisible(true);
                    button[i].setText(Integer.toString(i));
                    if(!avaliableIndex[i]) {
                        button[i].setEnabled(false);

                    }
                }

                ImageIcon icon;
                String trace;
                for(int i = 0; i<Table.nbJoueur; i++) {
                    offerLabel[i].setVisible(true);
                    Card[] playerOffer = js[i].getOffer();
                    for(int j = 0; j<2; j++) {
                        if(playerOffer[j] != null) {
                            offers[i][j].setVisible(true);
                            if(playerOffer[j].isFaceUp()) {
                                trace = playerOffer[j].getImage();
                            }else {
                                trace = "./image/CardBack.png";
                            }
                            icon = new ImageIcon(trace);
                            offers[i][j].setIcon(icon);
                        }
                    }
                }


            }
            if(currentStep == Step.take_card_choose_card) {

                String name = Table.getInstance().getCurrentPlayer().getName();
                int id = Table.getInstance().getCurrentPlayer().getID();
                lblInform.setText(name+ "(ID"+id+"),which card do you want");
                resetButton();
                button[0].setVisible(true);
                button[0].setText("Face Up");

                button[1].setVisible(true);
                button[1].setText("Face Down");

            }

            if(currentStep == Step.finish) {
                lblInform.setText("Click to check jest");
                resetButton();
                resetOffer();
                for(int i = 0; i<Table.nbJoueur; i++) {
                    button[i].setVisible(true);
                    button[i].setText(Integer.toString(i));
                }

                Joueur[] js = Table.getJoueurs();
                for(int i = 0; i<Table.nbJoueur; i++) {
                    String str = "Score of ";
                    str += js[i].getName();
                    str += " (ID ";
                    str += js[i].getID();
                    str += ") is :";
                    str += js[i].getScore();
                    result[i].setText(str);
                    result[i].setVisible(true);
                }

            }




        }
    }

    private void resetButton() {
        for(int i = 0; i<4; i++) {
            button[i].setVisible(false);
            button[i].setEnabled(true);
        }
    }
    private void resetJest() {
        for(int i = 0; i<jest.length; i++) {
            jest[i].setVisible(false);
        }
    }
    private void resetOffer() {
        for(int i = 0; i < 4; i++) {
            offers[i][0].setVisible(false);
            offers[i][1].setVisible(false);
            offerLabel[i].setVisible(false);
        }
    }
    private void showJest() {
        Joueur j = Table.getInstance().getCurrentPlayer();
        showJest(j);
    }
    public void showJest(Joueur j) {
        resetJest();
        Card[] playerJest = j.getJest();
        for(int i = 0; i < playerJest.length; i++) {
            jest[i].setVisible(true);
            String trace = playerJest[i].getImage();
            ImageIcon icon = new ImageIcon(trace);
            jest[i].setIcon(icon);
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        Table playDesk = Table.getInstance();
        playDesk.play();
        ConsoleInput in = new ConsoleInput();
        in.start();
        //System.out.println(Thread.currentThread().getName());
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameInterface window = new GameInterface(playDesk);
                    window.frame.setVisible(true);




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {


                    StartInterface si = new StartInterface(playDesk);
                    si.setInterfaceVisible(true);;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Create the application.
     */
    public GameInterface(Table table) {
        initialize();
        table.addObserver(this);
        new Controller(table , button, this);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(0, 0, 1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnButton_0 = new JButton("Yes");
        btnButton_0.setBounds(1183, 387, 100, 23);
        frame.getContentPane().add(btnButton_0);


        JButton btnButton_1 = new JButton("No");
        btnButton_1.setBounds(1308, 387, 100, 23);
        frame.getContentPane().add(btnButton_1);

        JButton btnButton_2 = new JButton("Yes");
        btnButton_2.setBounds(1183, 442, 100, 23);
        frame.getContentPane().add(btnButton_2);

        JButton btnButton_3 = new JButton("No");
        btnButton_3.setBounds(1308, 442, 100, 23);
        frame.getContentPane().add(btnButton_3);

        lblInform = new JLabel("Label  information instruction");
        lblInform.setBounds(1181, 294, 290, 28);
        frame.getContentPane().add(lblInform);

        JLabel lblYourJest = new JLabel("Your jest");
        lblYourJest.setBounds(25, 32, 58, 15);
        frame.getContentPane().add(lblYourJest);

        JLabel trophyLabel = new JLabel("Trophy");
        trophyLabel.setBounds(26, 289, 137, 15);
        frame.getContentPane().add(trophyLabel);

        ImageIcon icon = new ImageIcon("./image/CardBack.png");
        JLabel jest_0 = new JLabel("New label");
        jest_0.setBounds(10, 57, 152, 217);
        jest_0.setIcon(icon);
        frame.getContentPane().add(jest_0);


        JLabel jest_1 = new JLabel("New label");
        jest_1.setBounds(180, 57, 152, 217);
        jest_1.setIcon(icon);
        frame.getContentPane().add(jest_1);

        JLabel jest_2 = new JLabel("New label");
        jest_2.setBounds(360, 57, 152, 217);
        jest_2.setIcon(icon);
        frame.getContentPane().add(jest_2);

        JLabel jest_3 = new JLabel("New label");
        jest_3.setBounds(540, 57, 152, 217);
        frame.getContentPane().add(jest_3);

        JLabel jest_4 = new JLabel("New label");
        jest_4.setBounds(720, 57, 152, 217);
        frame.getContentPane().add(jest_4);

        JLabel jest_5 = new JLabel("New label");
        jest_5.setBounds(900, 57, 152, 217);
        frame.getContentPane().add(jest_5);

        JLabel jest_6 = new JLabel("New label");
        jest_6.setBounds(1080, 57, 152, 217);
        jest_6.setIcon(icon);
        frame.getContentPane().add(jest_6);

        JLabel offer_00 = new JLabel("New label");
        offer_00.setBounds(405, 326, 152, 217);
        offer_00.setIcon(icon);
        frame.getContentPane().add(offer_00);

        JLabel offer_01 = new JLabel("New label");
        offer_01.setBounds(575, 326, 152, 217);
        offer_01.setIcon(icon);
        frame.getContentPane().add(offer_01);

        JLabel offer_10 = new JLabel("New label");
        offer_10.setBounds(809, 326, 152, 217);
        offer_10.setIcon(icon);
        frame.getContentPane().add(offer_10);

        JLabel offer_11 = new JLabel("New label");
        offer_11.setBounds(979, 326, 152, 217);
        offer_11.setIcon(icon);
        frame.getContentPane().add(offer_11);

        JLabel offer_20 = new JLabel("New label");
        offer_20.setBounds(405, 601, 152, 217);
        frame.getContentPane().add(offer_20);

        JLabel offer_21 = new JLabel("New label");
        offer_21.setBounds(575, 601, 152, 217);
        frame.getContentPane().add(offer_21);

        JLabel offer_30 = new JLabel("New label");
        offer_30.setBounds(809, 601, 152, 217);
        frame.getContentPane().add(offer_30);

        JLabel offer_31 = new JLabel("New label");
        offer_31.setBounds(979, 601, 152, 217);
        frame.getContentPane().add(offer_31);

        JLabel offerLabel_0 = new JLabel("Offer of player No.0");
        offerLabel_0.setBounds(420, 301, 137, 15);
        frame.getContentPane().add(offerLabel_0);

        JLabel offerLabel_1 = new JLabel("Offer of player No.1");
        offerLabel_1.setBounds(824, 301, 137, 15);
        frame.getContentPane().add(offerLabel_1);

        JLabel offerLabel_2 = new JLabel("Offer of player No.2");
        offerLabel_2.setBounds(420, 576, 137, 15);
        frame.getContentPane().add(offerLabel_2);

        JLabel offerLabel_3 = new JLabel("Offer of player No.3");
        offerLabel_3.setBounds(824, 576, 137, 15);
        frame.getContentPane().add(offerLabel_3);

        JLabel trophy_0 = new JLabel("New label");
        trophy_0.setBounds(11, 314, 152, 217);
        trophy_0.setVisible(false);
        frame.getContentPane().add(trophy_0);

        JLabel trophy_1 = new JLabel("New label");
        trophy_1.setBounds(181, 314, 152, 217);
        trophy_1.setVisible(false);
        frame.getContentPane().add(trophy_1);

        JLabel result_0 = new JLabel("New label");
        result_0.setBounds(1183, 531, 179, 15);
        result_0.setVisible(false);
        frame.getContentPane().add(result_0);

        JLabel result_1 = new JLabel("New label");
        result_1.setBounds(1183, 556, 179, 15);
        result_1.setVisible(false);
        frame.getContentPane().add(result_1);

        JLabel result_2 = new JLabel("New label");
        result_2.setBounds(1183, 575, 179, 15);
        result_2.setVisible(false);
        frame.getContentPane().add(result_2);

        JLabel result_3 = new JLabel("New label");
        result_3.setBounds(1183, 600, 179, 15);
        result_3.setVisible(false);
        frame.getContentPane().add(result_3);



        button[0] = btnButton_0;
        button[1] = btnButton_1;
        button[2] = btnButton_2;
        button[3] = btnButton_3;

        jest[0] = jest_0;
        jest[1] = jest_1;
        jest[2] = jest_2;
        jest[3] = jest_3;
        jest[4] = jest_4;
        jest[5] = jest_5;
        jest[6] = jest_6;

        offers[0][0] = offer_00;
        offers[0][1] = offer_01;
        offers[1][0] = offer_10;
        offers[1][1] = offer_11;
        offers[2][0] = offer_20;
        offers[2][1] = offer_21;
        offers[3][0] = offer_30;
        offers[3][1] = offer_31;

        offerLabel[0] = offerLabel_0;
        offerLabel[1] = offerLabel_1;
        offerLabel[2] = offerLabel_2;
        offerLabel[3] = offerLabel_3;

        trophy[0] = trophy_0;
        trophy[1] = trophy_1;

        result[0] = result_0;
        result[1] = result_1;
        result[2] = result_2;
        result[3] = result_3;


    }
}
