package org.vr.caesarcipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Encoder {

    private static final List<Character> alphabetLowerCase = new ArrayList<>();
    private static final List<Character> alphabetUpperCase = new ArrayList<>();
    private static final Encoder encoder = new Encoder();

    private Encoder() {
        setAlphabet();
    }

    public static void Encode(String command, Path sourcePath, int key) {

        Path outPath = generateOutPatch(sourcePath, command);

        try {
            byte[] inputText = Files.readAllBytes(sourcePath);
            byte[] outputText = new byte[inputText.length];

            List<Character> encryptedAlphabetLowerCase = keyToAlphabetLower(key);
            List<Character> encryptedAlphabetUpperCase = keyToAlphabetUpper(key);

            for (int i = 0; i < inputText.length; i++) {

                if (encryptedAlphabetLowerCase.contains((char)inputText[i])) {

                    int letterIndex = alphabetLowerCase.indexOf((char)inputText[i]);
                    outputText[i] = (byte)encryptedAlphabetLowerCase.get(letterIndex).charValue();

                } else if (encryptedAlphabetUpperCase.contains((char)inputText[i])) {

                    int letterIndex = alphabetUpperCase.indexOf((char)inputText[i]);
                    outputText[i] = (byte)encryptedAlphabetUpperCase.get(letterIndex).charValue();

                } else {

                    outputText[i] = inputText[i];

                }

            }

            assert outPath != null;
            Files.write(outPath, outputText);

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public static void Decode(String command, Path sourcePath, int key) {

        Encode(command, sourcePath, key * -1);

    }

    private static Path generateOutPatch(Path sourcePath, String command) {

        String fileName = sourcePath.getFileName().toString();

        if (command.equals("encode")) {
            return Path.of(sourcePath.getParent().toString()
                    + "\\" + fileName.substring(0, fileName.indexOf('.'))
                    + "(encoded).txt");
        } else if (command.equals("decode")) {
            return Path.of(sourcePath.getParent().toString()
                    + "\\" + fileName.substring(0, fileName.indexOf('.'))
                    + "(decoded).txt");
        }

        return null;
    }

    private void setAlphabet() {

        for (int i = 'a'; i <= 'z'; i++) {
            Encoder.alphabetLowerCase.add((char) i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            Encoder.alphabetUpperCase.add((char) i);
        }

    }

    private static List<Character> keyToAlphabetLower(int key) {

        List<Character> keyAlphabet = new ArrayList<>(alphabetLowerCase);
        Collections.rotate(keyAlphabet, key * -1);

        return keyAlphabet;
    }

    private static List<Character> keyToAlphabetUpper(int key) {

        List<Character> keyAlphabet = new ArrayList<>(alphabetUpperCase);
        Collections.rotate(keyAlphabet, key * -1);

        return keyAlphabet;
    }

}
