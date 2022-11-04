package org.vr.caesarcipher;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        if (args.length > 0) {
            commandReader(args);
        } else {
            throw new IllegalArgumentException("Command not found");
        }

    }

    public static void commandReader(String[] args) {
        String command = args[0];

        if (command.equals("encode")) {

            Path sourcePatch = Path.of(args[1]);
            int key = Integer.parseInt(args[2]);
            Encoder.Encode(command, sourcePatch, key);

        } else if (command.equals("decode")) {

            Path sourcePatch = Path.of(args[1]);
            int key = Integer.parseInt(args[2]);
            Encoder.Decode(command, sourcePatch, key);

        } else if (command.equals("bruteForce")) {

            Path sourcePatch = Path.of(args[1]);
            Path referencePatch = Path.of(args[2]);
            Encoder.BruteForce(command, sourcePatch, referencePatch);

        } else {
            throw new IllegalArgumentException(String.format("Unknown command %s", command));
        }
    }
}
