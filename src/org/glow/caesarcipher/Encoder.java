package org.glow.caesarcipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Encoder {

    private static final String ENGLISH = "ENG";
    private static final String RUSSIAN = "RU";

    public void encode(String sourceStringPath, int key, Path outPath) {
        Path sourcePath = Path.of(sourceStringPath);

        try {

            byte[] inputText = Files.readAllBytes(sourcePath);
            byte[] outputText = encryptText(inputText, key);
            Files.write(outPath, outputText);

        } catch (IOException exception) {
            throw new IllegalArgumentException("Invalid file path");
        }

    }

    public void decode(String sourcePath, int key, Path outPath) {
        encode(sourcePath, key * - 1, outPath);
    }

    public void bruteForce(String sourceStringPath, String referenceStringPath) {

        Path sourcePath = Path.of(sourceStringPath);
        Path referencePath = Path.of(referenceStringPath);
        PathGenerator pathGenerator = new PathGenerator();

        try {

            byte[] referenceText = Files.readAllBytes(referencePath);
            byte[] sourceText = Files.readAllBytes(sourcePath);

            CryptoAnalyzer cryptoAnalyzer = new CryptoAnalyzer();
            int key = cryptoAnalyzer.findKey(referenceText, sourceText);

            Path outPath = pathGenerator.makeOutPathBruteForce(sourceStringPath, key);
            decode(sourceStringPath, key, outPath);

        } catch (IOException exception) {
            throw new IllegalArgumentException("Invalid file or reference path");
        }
    }

    private byte[] encryptText(byte[] inputText, int key) {

        String language = identifyLanguageOfText(inputText);
        System.out.println(language);

        List<Character> alphabetLowerCase = setAlphabetLower(language);
        List<Character> alphabetUpperCase = setAlphabetUpper(language);

        List<Character> encryptedAlphabetLowerCase = applyKeyToAlphabet(alphabetLowerCase, key);
        List<Character> encryptedAlphabetUpperCase = applyKeyToAlphabet(alphabetUpperCase, key);

        byte[] outputText = new byte[inputText.length];

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

        return outputText;
    }

    public String identifyLanguageOfText (byte[] inputText) {

        List<Character> alphabetENG = setAlphabetLower(ENGLISH);
        List<Character> alphabetRU = setAlphabetLower(RUSSIAN);

        int countENG = 0;
        int countRU = 0;

        for (byte character : inputText) {

            Character letter = (char) Character.toLowerCase(character);

            if (alphabetENG.contains(letter)) {
                countENG++;

            } else if (alphabetRU.contains(letter)) {
                countRU++;
            }

        }
        System.out.println(countENG);
        System.out.println(countRU);
        if (countENG > countRU) {
            return ENGLISH;
        } else if (countRU > countENG) {
            return RUSSIAN;
        } else {
            throw new IllegalArgumentException("unknown text language!");
        }

    }

    public List<Character> setAlphabetLower(String language) {

        List<Character> alphabet = new ArrayList<>();

        if (ENGLISH.equals(language)) {

            for (int i = 'a'; i <= 'z'; i++) {
                alphabet.add((char) i);
            }

        } else if (RUSSIAN.equals(language)) {

            for (int i = 'а'; i <= 'я'; i++) {
                alphabet.add((char) i);
                if (i == 'е') {
                    alphabet.add('ё');
                }
            }

        } else {
            throw new IllegalArgumentException("unknown language");
        }

        return alphabet;
    }

    private List<Character> setAlphabetUpper(String language) {

        List<Character> alphabet = new ArrayList<>();

        if ("ENG".equals(language)) {

            for (int i = 'A'; i <= 'Z'; i++) {
                alphabet.add((char) i);
            }

        } else if ("RU".equals(language)) {

            for (int i = 'А'; i <= 'Я'; i++) {
                alphabet.add((char) i);
                if (i == 'Е') {
                    alphabet.add('Ё');
                }
            }

        } else {
            throw new IllegalArgumentException("unknown language");
        }

        return alphabet;
    }

    private static List<Character> applyKeyToAlphabet(List<Character> alphabet, int key) {

        List<Character> keyAlphabet = new ArrayList<>(alphabet);
        Collections.rotate(keyAlphabet, key * -1);

        return keyAlphabet;
    }

}
