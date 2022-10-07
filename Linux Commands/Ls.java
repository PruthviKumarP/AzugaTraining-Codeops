/**
 * copyright (c) 2022.   -  All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - Pruthvi (Pruthvi Kumar P)
 * @version - JDK 11.0.15.1
 */

package com.main.java;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.*;


/** Ls  Class is created to mimic the functions of LINUX Ls commands
 */

public class Ls {

    /**
     * It prints list of files and dir.
     * @param path -It is used to take the input from user
     */

    public static void ls(String path) {
        File f = new File(path);
        //Iterating all Files
        for (File f1 : f.listFiles()) {
            System.out.print(f1.getName() + "\t");
        }
        return;
    }

    /**
     * It prints list of files along with hidden files and dir.
     * @param path -It is used to take the input from user
     */
    public static void ls_a(String path) {
        File f = new File(path);
        for (File f1 : f.listFiles()) {
            if (f1.isHidden()) {
                System.out.print(f1.getName() + "\t");
            }
            System.out.print(f1.getName() + "\t");
        }
        System.out.println();
    }

    /**
     * It prints list of files and dir. with full information.
     * @param path -It is used to take the input from user
     */
    public static void ls_ls(String path) {
        File f = new File(path);
        try {
            SimpleDateFormat pdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            for (File f1 : f.listFiles()) {
                Path p = Path.of(f1.getPath());
                PosixFileAttributes ats = Files.readAttributes(p, PosixFileAttributes.class);
                if (!f1.isHidden()) {
                    System.out.print(PosixFilePermissions.toString(ats.permissions()) + " ");
                    System.out.print(ats.owner().getName() + " ");
                    System.out.print(ats.group().getName() + " ");
                    System.out.print(ats.size() / 1024 + "kb ");
                    System.out.print(pdf.format(f1.lastModified()) + " ");
                    System.out.print(f1.getName());
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It prints list of files and dir. and hidden files and dir. with full information .
     * @param path -It is used to take the input from user
     */
    public static void ls_la(String path) {
        File f = new File(path);
        try {
            SimpleDateFormat pdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            for (File f1 : f.listFiles()) {
                Path p = Path.of(f1.getPath());
                PosixFileAttributes ats = Files.readAttributes(p, PosixFileAttributes.class);
                System.out.print(PosixFilePermissions.toString(ats.permissions()) + " ");
                System.out.print(ats.owner().getName() + " ");
                System.out.print(ats.group().getName() + " ");
                System.out.print(ats.size() / 1024 + "kb ");
                System.out.print(pdf.format(f1.lastModified()) + " ");
                System.out.print(f1.getName());
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It prints list of files and dir. and hidden files and dir. with full information according to the order of time.
     * @param path -It is used to take the input from user
     */
    public static void ls_LRT(String path) {
        File f = new File(path);
        Map<Long, File> mp = new TreeMap<>(Collections.reverseOrder());
        for (File obj : f.listFiles()) {
            mp.put(obj.lastModified(), obj);
        }
        SimpleDateFormat pdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        mp.entrySet().forEach(entry -> {
            try {
                Path p = Path.of(entry.getValue().getPath());
                PosixFileAttributes ats = Files.readAttributes(p, PosixFileAttributes.class);
                System.out.print(PosixFilePermissions.toString(ats.permissions()) + " ");
                System.out.print(ats.owner().getName() + " ");
                System.out.print(ats.group().getName() + " ");
                System.out.print(ats.size() / 1024 + "kb ");
                System.out.print(pdf.format(entry.getValue().lastModified()) + " ");
                System.out.print(entry.getValue().getName() + " ");
                System.out.println();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {

        //Switch handle ls options
        switch (args[0]) {
            case "ls":
                ls(args[1]);
                break;

            case "ls-a":
                ls_a(args[1]);
                break;

            case "ls-la":
                ls_la(args[1]);
                break;

            case "ls-ls":
                ls_ls(args[1]);
                break;

            case "ls-lrt":
                ls_LRT(args[1]);
                break;

            default:
                System.out.print("Invalid Input");
                break;
        }
    }
}


