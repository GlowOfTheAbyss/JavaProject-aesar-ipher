package org.glow.caesarcipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Encoder {

    private static final List<Character> ALPHABET_LOWER_CASE = new ArrayList<>();
    private static final List<Character> ALPHABET_UPPER_CASE = new ArrayList<>();

    public Encoder() {
        setAlphabet();
    }

    public void Encode(String sourceStringPath, int key, Path outPath) {
        Path sourcePath = Path.of(sourceStringPath);

        try {

            byte[] inputText = Files.readAllBytes(sourcePath);

            List<Character> encryptedAlphabetLowerCase = applyKeyToAlphabet(ALPHABET_LOWER_CASE, key);
            List<Character> encryptedAlphabetUpperCase = applyKeyToAlphabet(ALPHABET_UPPER_CASE, key);

            byte[] outputText = new byte[inputText.length];

            for (int i = 0; i < inputText.length; i++) {

                if (encryptedAlphabetLowerCase.contains((char)inputText[i])) {

                    int letterIndex = ALPHABET_LOWER_CASE.indexOf((char)inputText[i]);
                    outputText[i] = (byte)encryptedAlphabetLowerCase.get(letterIndex).charValue();

                } else if (encryptedAlphabetUpperCase.contains((char)inputText[i])) {

                    int letterIndex = ALPHABET_UPPER_CASE.indexOf((char)inputText[i]);
                    outputText[i] = (byte)encryptedAlphabetUpperCase.get(letterIndex).charValue();

                } else {

                    outputText[i] = inputText[i];

                }

            }

            Files.write(outPath, outputText);

        } catch (IOException exception) {
            throw new IllegalArgumentException("Invalid file path");
        }

    }

    public void Decode(String sourcePath, int key, Path outPath) {
        Encode(sourcePath, key * - 1, outPath);
    }

    public void BruteForce(String sourceStringPath, String referenceStringPath) {

        Path sourcePath = Path.of(sourceStringPath);
        Path referencePath = Path.of(referenceStringPath);
        PathGenerator pathGenerator = new PathGenerator();

        try {

            byte[] referenceText = Files.readAllBytes(referencePath);
            byte[] sourceText = Files.readAllBytes(sourcePath);

            CryptoAnalyzer cryptanalyzer = new CryptoAnalyzer();
            int key = cryptanalyzer.findKey(referenceText, sourceText);

            Path outPath = pathGenerator.makeOutPathBruteForce(sourceStringPath, key);
            Decode(sourceStringPath, key, outPath);

        } catch (IOException exception) {
            throw new IllegalArgumentException("Invalid file or reference path");
        }
    }

    private void setAlphabet() {

        for (int i = 'a'; i <= 'z'; i++) {
            Encoder.ALPHABET_LOWER_CASE.add((char) i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            Encoder.ALPHABET_UPPER_CASE.add((char) i);
        }

    }

    private static List<Character> applyKeyToAlphabet(List<Character> alphabet, int key) {

        List<Character> keyAlphabet = new ArrayList<>(alphabet);
        Collections.rotate(keyAlphabet, key * -1);

        return keyAlphabet;
    }

}
