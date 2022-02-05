package com.mgcqr.jest.core.jest;

import org.springframework.core.io.ClassPathResource;

import org.springframework.core.io.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

public class Files {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //File f = new File("classpath:cards.txt");

        Resource resource = new ClassPathResource("cards.txt");

        Scanner scan = null;
        try {
            InputStream inputStream = resource.getInputStream();
            scan = new Scanner(inputStream);//从文件接收数据
        } catch (Exception e) {}

        StringBuffer sb = new StringBuffer();
        scan.nextLine();
        sb.append( scan.next() );
        sb.append( scan.next() );
        sb.append( scan.next() );

//		while (scan.hasNextLine()) {
//			sb.append(scan.nextLine());
//			System.out.println(sb);
//			sb.delete(0,sb.length());
//		}		
//		StringBuffer sb = new StringBuffer();
//		sb.append("123456789");
//		sb.delete(0,2);

        System.out.println(sb);
        System.out.println(sb.length());
    }

}
