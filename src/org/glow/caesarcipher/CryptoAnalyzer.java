package org.glow.caesarcipher;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CryptoAnalyzer {

    public int findKey (String referenceText, String sourceText) {

        Encoder encoder = new Encoder();
        String languageReference = encoder.identifyLanguageOfText(referenceText);
        String languageSource = encoder.identifyLanguageOfText(sourceText);

        if (!languageReference.equals(languageSource)) {
            throw new IllegalArgumentException("texts have a different language!");
        }

        List<Character> alphabet = encoder.setAlphabetLower(languageSource);

        int[] referenceLetterCount = letterCount(referenceText.getBytes(StandardCharsets.UTF_8), alphabet);
        int[] sourceLetterCount = letterCount(sourceText.getBytes(), alphabet);

        List<Integer> sourceLetterCountList = new ArrayList<>(Arrays.stream(sourceLetterCount).boxed().toList());
        List<Integer> valuesDifference = new ArrayList<>();

        for (int i = 0; i < referenceLetterCount.length; i++) {

            int tempSum = 0;

            for (int j = 0; j < referenceLetterCount.length; j++) {
                tempSum += Math.abs(referenceLetterCount[j] - sourceLetterCountList.get(j));
            }

            valuesDifference.add(tempSum);
            Collections.rotate(sourceLetterCountList, -1);

        }

        int key = alphabet.get(valuesDifference.indexOf(Collections.min(valuesDifference)));
        return key - alphabet.get(0);

    }

    private int[] letterCount(byte[] letters, List<Character> alphabet) {

        int[] letterCount = new int[alphabet.size()];

        for (byte character : letters) {

            char letter = Character.toLowerCase((char) character);
            if (alphabet.contains(letter)) {
                letterCount[alphabet.indexOf(letter)] += 1;
            }

        }

        normalize(letterCount);

        return letterCount;
    }

    private void normalize(int[] lettersCount) {

        int maxValue = Collections.max(Arrays.stream(lettersCount).boxed().toList());
        for (int i = 0; i < lettersCount.length; i++) {
            lettersCount[i] = lettersCount[i] * 100 / maxValue;
        }

    }

}
