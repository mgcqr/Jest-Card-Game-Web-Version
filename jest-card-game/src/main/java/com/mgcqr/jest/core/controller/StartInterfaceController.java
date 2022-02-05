package com.mgcqr.jest.core.controller;

import com.mgcqr.jest.core.enumeration.*;
import com.mgcqr.jest.core.stuff.MailBox;
import com.mgcqr.jest.core.stuff.Table;
import com.mgcqr.jest.core.view.StartInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartInterfaceController {
    public StartInterfaceController(Table table,JButton bt ,StartInterface inter) {
        bt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] obj = StartInterface.getParametre();
                table.setParametre((int)obj[0], (int)obj[1], (String[])obj[2], (GameMode)obj[3]);
                inter.setInterfaceVisible(false);


                MailBox.produce(new Object());


            }
        });
    }
}
