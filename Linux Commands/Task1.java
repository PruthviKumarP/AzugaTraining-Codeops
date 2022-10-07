/**
 * copyright (c) 2022.   -  All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - Pruthvi (Pruthvi Kumar P)
 * @version - JDK 11.0.15.1
 */

package com.main.java;

import java.io.*;
import java.nio.file.*;


/** Task1  Class is created to mimic the functions of LINUX basic commands
 */

public class Task1 {
    /**
     * The code below will create a directory if it doesn't already exits.
     * @param path - It accepts the input from user.
     */
    public static String mkdir(String path){
        File f = new File(path);
        //Creating a directory
        return(f.mkdirs())?f.getAbsolutePath()+" $ "+f.getName()+" Directory is Successfully Created "
                :f.getAbsolutePath()+" $ "+f.getName()+" Directory is Already Created ";
    }

    /**
     *The code below will remove a directory from the path. if it already exit.
     * @param path - It accepts the input from user.
     */
    public static void rmdir(String path) {
        File f = new File(path);
        //Store file path in String Array
        String arr[] = f.list();
        //Removing a empty directory.
        if (arr.length > 0)
            System.out.println("Cannot be Deleted,it contains some files");
        else {
            f.delete();
            System.out.println("File deleted successfull");
        }
    }

    /**
     *The code below will remove a fiel or directory from the path. if it already exit.
     * @param path - It accepts the input from user.
     */
    public static String rmFile(String path) {
        File f = new File(path);
        //Removing a file.
        return (f.delete()) ? f.getName() + " File is Successfully Removed " : f.getName() + "File doesn't exist";
    }

    /**
     *The code below will to create, change and modify timestamps of a file.
     * @param path - It accepts the input from user.
     */
    public static void touch(String path) {
        try {
            File f = new File(path);
            // Creating a file
            if (!f.exists() && f.createNewFile()) {
                System.out.println("File created: " + f.getName());
            } else
                System.out.println("File already exists");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    /**
     *The code below will move file or directorie from one place to another.
     * @param p1 - It accepts the path1 from user.
     */
    public static void mv(String p1, String p2) {
        //moving file from path1 to path2
        try {
            //File Move one path to another path location
            Files.move(Paths.get(p1), Paths.get(p2));
            System.out.println("File moved successfully");
        } catch (Exception e) {
            System.out.println("File cannot be moved");
            System.out.println(e.getMessage());

        }
    }


    public static void main(String args[]) {
        //Switch case handle Basic CLI Command Options
        switch (args[0]) {
            case "mkdir":
                System.out.println(mkdir(args[1]));
                break;
            case "rmdir":
                rmdir(args[1]);
                break;

            case "rm":
                System.out.println(rmFile(args[1]));
                break;

            case "touch":
                touch(args[1]);
                break;

            case "mv":
                mv(args[1], args[2]);
                break;

            default:
                System.out.print("Invalid Input");
                break;
        }
    }

}
