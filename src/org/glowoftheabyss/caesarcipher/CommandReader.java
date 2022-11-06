package org.glowoftheabyss.caesarcipher;

public class CommandReader {

    private static final String ENCODE = "encode";
    private static final String DECODE = "decode";
    private static final String BRUTE_FORCE = "bruteForce";

    public void Read (String[] args) {

        errorsCheck(args);

        Encoder encoder = new Encoder();
        String command = args[0];
        String sourcePatch = args[1];
        int key = Integer.parseInt(args[2]);

        if (ENCODE.equals(command)) {
            encoder.Encode(command, sourcePatch, key);
        } else if (DECODE.equals(command)) {
            // decode
        } else if (BRUTE_FORCE.equals(command)) {
            // brute force
        }

    }

    private void errorsCheck(String[] args) {

        if (args.length == 0) {
            throw new IllegalArgumentException("Command not found");
        }

        if (!(ENCODE.equals(args[0]))
                && !(DECODE.equals(args[0]))
                && !(BRUTE_FORCE.equals(args[0]))) {
            throw new IllegalArgumentException(String.format("Unknown command %s", args[0]));
        } else if (args.length < 2) {
            throw new IllegalArgumentException("Patch to file not found!");
        } else if (args.length < 3 && BRUTE_FORCE.equals(args[0])) {
            throw new IllegalArgumentException("Patch to reference text not found!");
        } else if (args.length < 3) {
            throw new IllegalArgumentException("Key not found!");
        }

    }
}
