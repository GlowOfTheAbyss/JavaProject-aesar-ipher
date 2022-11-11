package org.glow.caesarcipher;

import java.nio.file.Path;

public class PathGenerator {

    public Path makeOutPathEncode (String sourcePath) {

        StringBuilder sb = createStringBuilderRemovingBrackets(sourcePath);
        sb.insert(sb.lastIndexOf("."), "(encoded)");

        return Path.of(sb.toString());
    }

    public Path makeOutPathDecode (String sourcePath) {

        StringBuilder sb = createStringBuilderRemovingBrackets(sourcePath);
        sb.insert(sb.lastIndexOf("."), "(decoded)");

        return Path.of(sb.toString());
    }

    public Path makeOutPathBruteForce (String sourcePath, int key) {

        StringBuilder sb = createStringBuilderRemovingBrackets(sourcePath);
        sb.insert(sb.lastIndexOf("."), "(decoded key " + key + ")");

        return Path.of(sb.toString());
    }

    private StringBuilder createStringBuilderRemovingBrackets (String sourcePath) {

        StringBuilder sb = new StringBuilder(sourcePath);

        if (sb.toString().contains("(") && sb.toString().contains(")")) {
            sb.replace(sb.lastIndexOf("("), sb.lastIndexOf(")") + 1, "");
        }

        return sb;
    }

}
