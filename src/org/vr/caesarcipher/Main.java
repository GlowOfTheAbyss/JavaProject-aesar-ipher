package org.vr.caesarcipher;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        System.out.println(Arrays.toString(args));

        if (args.length > 0) {
            commandReader(args);
        } else {
            throw new IllegalArgumentException("Command not found");
        }

    }

    public static void commandReader(String[] args) {
        String command = args[0];

        if (command.equals("encode")) {

            String filePatch = args[1];
            int key = Integer.parseInt(args[2]);
            Encoder.Encode(filePatch, key);

        } else if (command.equals("decode")) {

            System.out.println("decode detected");

        } else if (command.equals("bruteForce")) {

            System.out.println("bruteForce detected");

        } else {
            throw new IllegalArgumentException(String.format("Unknown command %s", command));
        }
    }
}
