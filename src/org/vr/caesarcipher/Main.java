package org.vr.caesarcipher;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        System.out.println(Arrays.toString(args));

        if (args.length > 0) {
            commandReader(args);
        }

    }

    public static void commandReader(String[] args) {
        String command = args[0];

        if (command.equals("encode")) {
            System.out.println("encode detected");
        } else if (command.equals("decode")) {
            System.out.println("decode detected");
        } else if (command.equals("bruteForce")) {
            System.out.println("bruteForce detected");
        } else {
            throw new IllegalArgumentException(String.format("Unknown command %s", command));
        }
    }
}
