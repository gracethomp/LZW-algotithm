package com.kpi;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Interface.showMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
