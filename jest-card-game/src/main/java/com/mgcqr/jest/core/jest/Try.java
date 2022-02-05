package com.mgcqr.jest.core.jest;


import java.util.Scanner;


public class Try {


    public static void main(String[] args) {



        Scanner scan = new Scanner(System.in);
        String str;
        while(true) {
            if(scan.hasNext()) {
                str = scan.next();
                try {
                    int a = Integer.parseInt(str);
                    System.out.println("int");
                    System.out.println(a);
                }catch(Exception e) {
                    boolean b = Boolean.parseBoolean(str);
                    System.out.println("boolean");
                    System.out.println(b);
                }

            }

        }

    }
}
