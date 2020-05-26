package tglanz.limestone.util;

import java.io.IOException;
import java.util.Optional;

public class ReadInput {
    public static String untilCharInclusive(String prompt, char terminator, boolean terminatorInclusive)
            throws IOException {

        Optional
                .ofNullable(prompt)
                .ifPresent(System.out::print);

        StringBuilder line = new StringBuilder();

        while (true) {
            byte input = (byte)System.in.read();

            if (input != terminator || terminatorInclusive) {
                line.append((char) input);
            }

            if (input == terminator) {
                break;
            }
        }

        return line.toString();
    }

}
