package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.constants.ErrorMessage;

public final class Reader {
    public static List<String> readFile(String filePath) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> content = new ArrayList<>();
            reader.readLine();

            String read;
            while ((read = reader.readLine()) != null) {
                content.add(read);
            }

            return content;
        } catch (IOException e){
            System.out.println(ErrorMessage.FAILED_TO_READ_FILE.getMessage());
            return readFile(filePath);
        }
    }
}
