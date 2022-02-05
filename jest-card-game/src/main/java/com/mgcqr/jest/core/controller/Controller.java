package com.mgcqr.jest.core.controller;

import com.mgcqr.jest.core.enumeration.Step;
import com.mgcqr.jest.core.stuff.MailBox;
import com.mgcqr.jest.core.stuff.Table;
import com.mgcqr.jest.core.view.GameInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    public Controller(Table table, JButton[] button, GameInterface inter) {

        //table.getJoueurs()[]
        button[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(table.getCurrentStep() == Step.make_offer) {
                    int choice = 0;
                    MailBox.produce(choice);
                }
                if(table.getCurrentStep() == Step.take_card_choose_ID) {
                    int choice = 0;
                    MailBox.produce(choice);
                }
                if(table.getCurrentStep() == Step.take_card_choose_card) {
                    boolean faceUp = true;
                    MailBox.produce(faceUp);
                }
                if(table.getCurrentStep() == Step.finish) {
                    inter.showJest(Table.getJoueurs()[0]);
                }

            }
        });

        button[1].addActionListener(new ActionListener() {
            public synchronized void actionPerformed(ActionEvent e) {
                if(table.getCurrentStep() == Step.make_offer) {
                    int choice = 1;
                    MailBox.produce(choice);
                }

                if(table.getCurrentStep() == Step.take_card_choose_ID) {
                    int choice = 1;

                    MailBox.produce(choice);
                }
                if(table.getCurrentStep() == Step.take_card_choose_card) {
                    boolean faceUp = false;
                    MailBox.produce(faceUp);
                }
                if(table.getCurrentStep() == Step.finish) {
                    inter.showJest(Table.getJoueurs()[1]);
                }


            }
        });

        button[2].addActionListener(new ActionListener() {
            public synchronized void actionPerformed(ActionEvent e) {

                if(table.getCurrentStep() == Step.take_card_choose_ID) {
                    int choice = 2;
                    Table.getInstance().setCurrentStep(Step.take_card_choose_card);
                    MailBox.produce(choice);
                }
                if(table.getCurrentStep() == Step.finish) {
                    inter.showJest(Table.getJoueurs()[2]);
                }

            }
        });

        button[3].addActionListener(new ActionListener() {
            public synchronized void actionPerformed(ActionEvent e) {

                if(table.getCurrentStep() == Step.take_card_choose_ID) {
                    int choice = 3;
                    Table.getInstance().setCurrentStep(Step.take_card_choose_card);
                    MailBox.produce(choice);
                }
                if(table.getCurrentStep() == Step.finish) {
                    inter.showJest(Table.getJoueurs()[3]);
                }

            }
        });
    }
}
