package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Reader {
    public static List<String> readFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String> content = new ArrayList<>();
        readContent(reader, content);
        reader.close();
        return content;
    }

    private static void readContent(BufferedReader reader, List<String> content) throws IOException {
        reader.readLine();
        String read;
        while ((read = reader.readLine()) != null) {
            content.add(read);
        }
    }
}
