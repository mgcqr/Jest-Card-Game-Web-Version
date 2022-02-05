package com.mgcqr.jest.core.view;

import com.mgcqr.jest.core.controller.StartInterfaceController;
import com.mgcqr.jest.core.enumeration.GameMode;
import com.mgcqr.jest.core.stuff.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartInterface {

    private JFrame frame;

    private static int nbPlayer = 3;
    private static int nbAI = 2;
    private int nbHuman = 1;
    private String[] model;
    private static String[] names;
    private JComboBox comboBox_1;
    private static GameMode gameMode = GameMode.Original;

    private JButton btnOk;
    private JTextField textField_0;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField[] textFields = new JTextField[4];


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Table t = Table.getInstance();
                    StartInterface window = new StartInterface(t);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public StartInterface(Table table) {
        initialize();
        new StartInterfaceController(table, btnOk , this);
    }

    public void setInterfaceVisible(boolean b) {
        frame.setVisible(b);
    }
    public static Object[] getParametre() {
        Object[] obj = new Object[4];
        obj[0] = nbPlayer;
        obj[1] = nbAI;
        obj[2] = names;
        obj[3] = gameMode;
        return obj;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);



        JLabel lblAmountOfPlayer = new JLabel("Amount of player");
        lblAmountOfPlayer.setBounds(22, 41, 108, 15);
        frame.getContentPane().add(lblAmountOfPlayer);

        btnOk = new JButton("OK");
        btnOk.setBounds(479, 230, 97, 23);
//		btnOk.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println(names.length);
//				for(int i = 0; i < names.length; i++) {
//					System.out.println(names[i]);
//				}
//			}
//		});
        frame.getContentPane().add(btnOk);

        JLabel lblAmountOfAi = new JLabel("Amount of AI");
        lblAmountOfAi.setBounds(22, 82, 108, 15);
        frame.getContentPane().add(lblAmountOfAi);


        JComboBox comboBox_0 = new JComboBox();
        comboBox_0.setBounds(140, 33, 85, 23);
        comboBox_0.setModel(new DefaultComboBoxModel(new String[] {"3","4"}));
        comboBox_0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() instanceof JComboBox){
                    JComboBox comboBox = (JComboBox  )e.getSource();
                    String str = comboBox.getSelectedItem().toString();
                    nbPlayer = Integer.parseInt(str);
                    //System.out.println(nbPlayer);
                    //System.out.printf("%s%n", "actionPerformed called");
                    model = new String[nbPlayer];
                    for(int i = 0; i < nbPlayer; i++) {
                        model[i] = Integer.toString(i);
                    }
                    comboBox_1.setModel(new DefaultComboBoxModel (model));
                }

            }
        });
        frame.getContentPane().add(comboBox_0);

        comboBox_1 = new JComboBox();
        comboBox_1.setBounds(140, 74, 85, 23);
        //comboBox_1.setModel(new DefaultComboBoxModel (model));
        comboBox_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() instanceof JComboBox){
                    JComboBox comboBox = (JComboBox  )e.getSource();
                    String str = comboBox.getSelectedItem().toString();
                    nbAI = Integer.parseInt(str);
                    nbHuman = nbPlayer - nbAI;
                    names = new String[nbHuman];
                    for(int i = 0; i < 4; i++) {
                        textFields[i].setVisible(false);
                        textFields[i].setEnabled(false);
                    }
                    for(int i = 0; i < nbHuman; i++) {
                        textFields[i].setVisible(true);
                        textFields[i].setEnabled(true);
                    }
                }

            }
        });
        frame.getContentPane().add(comboBox_1);

        JComboBox comboBox_2 = new JComboBox();
        comboBox_2.setBounds(140, 115, 85, 23);
        comboBox_2.setModel(new DefaultComboBoxModel(GameMode.values()));
        comboBox_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() instanceof JComboBox){
                    JComboBox comboBox = (JComboBox  )e.getSource();
                    if(comboBox.getSelectedItem() instanceof GameMode) {
                        gameMode = (GameMode) comboBox.getSelectedItem();
                    }
                    //System.out.println(gameMode);
                    //System.out.println(gameMode.getClass());

                }

            }
        });
        frame.getContentPane().add(comboBox_2);

        JLabel lblGameMode = new JLabel("Game mode");
        lblGameMode.setBounds(22, 123, 58, 15);
        frame.getContentPane().add(lblGameMode);

        JLabel lblNameOfPlayers = new JLabel("Name of players press enter each time");
        lblNameOfPlayers.setBounds(305, 33, 260, 22);
        frame.getContentPane().add(lblNameOfPlayers);

        textField_0 = new JTextField(200);
        textField_0.setBounds(352, 65, 66, 21);
        frame.getContentPane().add(textField_0);
        textField_0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println(textField_0.getText());
                names[0] = textField_0.getText();
            }
        });
        textField_0.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(352, 95, 66, 21);
        frame.getContentPane().add(textField_1);
        textField_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println(textField_0.getText());
                names[1] = textField_1.getText();
            }
        });
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(352, 126, 66, 21);
        frame.getContentPane().add(textField_2);
        textField_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println(textField_0.getText());
                names[2] = textField_2.getText();
            }
        });
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(352, 157, 66, 21);
        frame.getContentPane().add(textField_3);
        textField_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println(textField_0.getText());
                names[3] = textField_3.getText();
            }
        });
        textField_3.setColumns(10);

        textFields[0] = textField_0;
        textFields[1] = textField_1;
        textFields[2] = textField_2;
        textFields[3] = textField_3;

        for(int i = 0; i < 4; i++) {
            textFields[i].setVisible(false);
            textFields[i].setEnabled(false);
        }

    }
}
