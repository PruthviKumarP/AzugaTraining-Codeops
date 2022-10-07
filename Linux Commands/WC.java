/**
 * copyright (c) 2022.   -  All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - Pruthvi (Pruthvi Kumar P)
 * @version - JDK 11.0.15.1
 */


package com.main.java;

import java.io.*;
import java.io.IOException;
import java.nio.file.*;;

/** WC  Class is created to mimic the functions of LINUX wc commands
 */

public class WC {

    /**
     * To print no. of lines, word count, byte and characters count in the files specified
     * @param path -It is used to take path input from user
     *
     */
    public static void wc(String path) {
        int lineCount = 0;
        int wordCount = 0;
        try {
            FileReader f = new FileReader(path);
            BufferedReader br = new BufferedReader(new FileReader(path));
            String currentLine = br.readLine();

            while (currentLine != null) {
                //Lines Count
                lineCount++;
                //Words Count
                String words[] = currentLine.split(" ");
                wordCount += words.length;
                currentLine = br.readLine();

            }

            System.out.print("\t" + lineCount + "\t" + wordCount + "\t" + Files.size(Path.of(path)) + "\t" + path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To print no. of lines, word count, byte and characters count in the files specified
     * @param path -It is used to take path input from user
     *
     */

    public static void wcl(String path) {
        int lineCount = 0;
        try {
            FileReader f = new FileReader(path);
            BufferedReader br = new BufferedReader(new FileReader(path));
            String currentLine = br.readLine();
            //Lines Count
            while (currentLine != null) {
                lineCount++;
                currentLine = br.readLine();
            }

            System.out.print("\t" + lineCount + "\t" + path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To print no. of lines, word count, byte and characters count in the files specified
     * @param path -It is used to take path input from user
     *
     */

    public static void wcw(String path) {
        int wordCount = 0;
        try {
            FileReader f = new FileReader(path);
            BufferedReader br = new BufferedReader(new FileReader(path));
            String currentLine = br.readLine();

            //Word Count
            while (currentLine != null) {
                String words[] = currentLine.split(" ");
                wordCount += words.length;
                currentLine = br.readLine();

            }

            System.out.print("\t" + wordCount + "\t" + path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To print no. of lines, word count, byte and characters count in the files specified
     * @param path -It is used to take path input from user
     *
     */
    public static void wcc(String path) {
        try {
            //Character Count
            System.out.print("\t" + Files.size(Path.of(path)) + "\t" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]) {

        //switch handle wc options
        switch (args[0]) {
            case "wc":
                wc(args[1]);
                break;

            case "wc-l":
                wcl(args[1]);
                break;

            case "wc-w":
                wcw(args[1]);
                break;

            case "wc-c":
                wcc(args[1]);
                break;

            default:
                System.out.println("Invalid Input");
        }
    }
}
