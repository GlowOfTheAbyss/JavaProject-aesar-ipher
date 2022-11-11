package org.glow.caesarcipher;

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
            throw new IllegalArgumentException("Texts have a different language");
        }

        List<Character> alphabet = encoder.createAlphabetLower(languageSource);

        int[] referenceLetterCount = letterCount(referenceText, alphabet);
        int[] sourceLetterCount = letterCount(sourceText, alphabet);

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

        return valuesDifference.indexOf(Collections.min(valuesDifference));

    }

    private int[] letterCount(String letters, List<Character> alphabet) {

        int[] letterCount = new int[alphabet.size()];

        for (char character : letters.toCharArray()) {

            char letter = Character.toLowerCase(character);
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
