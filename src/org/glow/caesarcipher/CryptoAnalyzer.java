package org.glow.caesarcipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CryptoAnalyzer {

    public int findKey (byte[] referenceText, byte[] sourceText) {

        List<Character> alphabet = new ArrayList<>();
        for (int i = 'a'; i <= 'z'; i++) {
            alphabet.add((char)i);
        }

        int[] referenceLetterCount = LetterCount(referenceText, alphabet);
        int[] sourceLetterCount = LetterCount(sourceText, alphabet);

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
        return key - 'a';

    }

    private int[] LetterCount(byte[] letters, List<Character> alphabet) {

        int[] letterCount = new int[alphabet.size()];

        for (int i = 0; i < letters.length; i++) {

            char letter = Character.toLowerCase((char) letters[i]);
            if (alphabet.contains(letter)) {
                letterCount[alphabet.indexOf(letter)] += 1;
            }

        }

        Normalize(letterCount);

        return letterCount;
    }

    private void Normalize(int[] lettersCount) {

        int maxValue = Collections.max(Arrays.stream(lettersCount).boxed().toList());
        for (int i = 0; i < lettersCount.length; i++) {
            lettersCount[i] = lettersCount[i] * 100 / maxValue;
        }

    }

}
