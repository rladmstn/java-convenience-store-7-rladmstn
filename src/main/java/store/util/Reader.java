package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Reader {
    public static List<String> readFile(String filePath) throws IOException {
        List<String> content = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();

            String read;
            while ((read = reader.readLine()) != null) {
                content.add(read);
            }
        }

        return content;
    }
}
