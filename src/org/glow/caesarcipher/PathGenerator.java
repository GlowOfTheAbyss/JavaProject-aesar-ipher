package org.glow.caesarcipher;

import java.nio.file.Path;

public class PathGenerator {

    public Path makeOutPathEncode (String sourcePath) {

        StringBuilder sb = new StringBuilder(sourcePath);
        sb.insert(sourcePath.length() - 4, "(Encoded)");

        return Path.of(sb.toString());
    }

    public Path makeOutPathDecode (String sourcePath) {

        StringBuilder sb = new StringBuilder(sourcePath);
        sb.insert(sourcePath.length() - 4, "(Decoded)");

        return Path.of(sb.toString());
    }

}
