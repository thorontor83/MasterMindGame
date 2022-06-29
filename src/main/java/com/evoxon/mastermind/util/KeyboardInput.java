package com.evoxon.mastermind.util;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class KeyboardInput {

    private final Scanner scanner = new Scanner(System.in);

    public String getNewInput(){
        return scanner.nextLine();
    }
}
