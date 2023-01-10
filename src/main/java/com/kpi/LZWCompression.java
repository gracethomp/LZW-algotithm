package com.kpi;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LZWCompression {
    private static int sizeEncode = 32;
    private static int sizeDecode = 32;
    private static int index = 256;
    private static Map<String, Integer> dictionaryEncode;
    private static Map<Integer, String> dictionaryDecode;

    private static void fillDictionaryEncode(){
        dictionaryEncode = new HashMap<>();
        try {
            String file = FileUtils.readFileToString(new File("src/main/resources/dictionary.txt"), StandardCharsets.US_ASCII);
            String[] lines = file.split("\n");
            for(String line: lines) {
                if(line.equals("space")) {
                    dictionaryEncode.put(" ", sizeEncode);
                }else {
                    dictionaryEncode.put(line, sizeEncode);
                }
                sizeEncode++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fillDictionaryDecode(){
        dictionaryDecode = new HashMap<>();
        try {
            String file = FileUtils.readFileToString(new File("src/main/resources/dictionary.txt"), StandardCharsets.US_ASCII);
            String[] lines = file.split("\n");
            for(String line: lines) {
                if(line.equals("space")) {
                    dictionaryDecode.put(sizeDecode, " ");
                }else {
                    dictionaryDecode.put(sizeDecode, line);
                }
                sizeDecode++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StringBuilder encode(StringBuilder message) {
        index = 256;
        fillDictionaryEncode();
        StringBuilder X = new StringBuilder();
        StringBuilder result = new StringBuilder();
        X.append(message.charAt(0));
        int i = 1;
        while (true) {
            if(i == message.length()){
                result.append(dictionaryEncode.get(X.toString()));
                break;
            }
            StringBuilder Y = new StringBuilder();
            Y.append(message.charAt(i));
            String XY = X + Y.toString();
            if(dictionaryEncode.containsKey(XY))
                X = new StringBuilder(XY);
            else {
                result.append(dictionaryEncode.get(X.toString())).append(", ");
                X = new StringBuilder(Y);
                dictionaryEncode.put(XY, index);
                index++;
            }
            i++;
        }
        return result;
    }

    public static StringBuilder decode(StringBuilder message){
        index = 256;
        fillDictionaryDecode();
        StringBuilder X = new StringBuilder();
        StringBuilder result = new StringBuilder();
        String[] values = message.toString().split(", ");
        X.append(values[0]);
        int i = 1;
        while(true) {
            if(i == values.length){
                result.append(dictionaryDecode.get(Integer.valueOf(X.toString())));
                break;
            }
            StringBuilder Y = new StringBuilder();
            Y.append(values[i]);
            StringBuilder XY = new StringBuilder(dictionaryDecode.get(Integer.valueOf(X.toString())));
            XY.append(dictionaryDecode.get(Integer.valueOf(Y.toString())));
            if(!dictionaryDecode.containsValue(XY.toString())) {
                result.append(dictionaryDecode.get(Integer.valueOf(X.toString())));
                dictionaryDecode.put(index++, XY.toString());
                X = new StringBuilder(Y.toString());
            }
            i++;
        }
        return result;
    }
}
