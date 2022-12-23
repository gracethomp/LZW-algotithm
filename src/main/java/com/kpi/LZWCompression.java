package com.kpi;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LZWCompression {
    private static int size = 0;
    private static Map<String, Integer> dictionary = new HashMap<>();

    private static void fillDictionary(){
        dictionary = new HashMap<>();
        try {
            String file = FileUtils.readFileToString(new File("src/main/resources/dictionary.txt"), StandardCharsets.US_ASCII);
            String[] lines = file.split("\n");
            for(String line: lines) {
                String[] values = line.split("\t");
                if(values[2].equals("space")) {
                    dictionary.put(" ", size);
                }else {
                    dictionary.put(values[2], size);
                }
                size++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dictionary);
    }

    public static void encode(StringBuilder message) {
        fillDictionary();
        StringBuilder X = new StringBuilder();
        X.append(message.charAt(0));
        int i = 1;
        while (i != message.length()) {
            StringBuilder Y = new StringBuilder();
            Y.append(message.charAt(i));
            String XY = X + Y.toString();
            if(dictionary.containsKey(XY)) {
                X = new StringBuilder(XY);
            }
            else {
                X = new StringBuilder(Y);
                dictionary.put(XY, size);
                size++;
            }
            i++;
        }
        System.out.println(dictionary);
    }
}
