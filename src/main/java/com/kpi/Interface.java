package com.kpi;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Interface {
    public static void showMenu() throws IOException {
        System.out.println("What do you want to do?\n1.Encode message\n2.Decode message\n3.Exit");
        label:
        while (true) {
            Scanner sc = new Scanner(System.in);
            String variant = sc.nextLine();
            switch (variant) {
                case "1": {
                    System.out.println("What do you want to encode?\n1.Write new message right now\n2.Read data from file");
                    String variant1 = sc.nextLine();
                    StringBuilder message;
                    while (true) {
                        if (variant1.equals("1")) {
                            System.out.println("Enter your message: ");
                            message = new StringBuilder(sc.nextLine());
                            break;
                        } else if (variant1.equals("2")) {
                            message = new StringBuilder(FileUtils
                                    .readFileToString(new File("src/main/resources/messageToEncode.txt")));
                            break;
                        } else
                            System.out.println("Try again!");
                    }
                    System.out.println("Encoded message: " + LZWCompression.encode(message));
                    System.out.println("\nWhat do you want to do?\n1. Encode message\n2.Decode message\n3.Exit");
                    break;
                }
                case "2": {
                    System.out.println("What do you want to encode?\n1.Write new message right now\n2.Read data from file");
                    String variant1 = sc.nextLine();
                    StringBuilder message;
                    while (true) {
                        if (variant1.equals("1")) {
                            System.out.println("Enter your message (use ', ' to split code: ");
                            message = new StringBuilder(sc.nextLine());
                            break;
                        } else if (variant1.equals("2")) {
                            message = new StringBuilder(FileUtils
                                    .readFileToString(new File("src/main/resources/messageToDecode.txt")));
                            break;
                        } else
                            System.out.println("Try again!");
                    }
                    System.out.println("Decoded message: " + LZWCompression.decode(message));
                    System.out.println("\nWhat do you want to do?\n1. Encode message\n2.Decode message\n3.Exit");
                    break;
                }
                case "3":
                    break label;
                default:
                    System.out.println("Try again!");
                    break;
            }
        }
    }
}
