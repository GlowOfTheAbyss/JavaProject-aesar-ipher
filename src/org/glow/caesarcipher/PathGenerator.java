package org.glow.caesarcipher;

import java.nio.file.Path;

public class PathGenerator {

    public Path makeOutPathEncode (String sourcePath) {

        StringBuilder sb = new StringBuilder(sourcePath);
        sb.insert(sourcePath.length() - 4, "(encoded)");

        return Path.of(sb.toString());
    }

    public Path makeOutPathDecode (String sourcePath) {

        StringBuilder sb = new StringBuilder(sourcePath);
        sb.insert(sourcePath.length() - 4, "(decoded)");

        return Path.of(sb.toString());
    }

    public Path makeOutPathBruteForce (String sourcePath, int key) {

        StringBuilder sb = new StringBuilder(sourcePath);
        sb.insert(sourcePath.length() - 4, "(decoded key " + key + ")");

        return Path.of(sb.toString());
    }

}
