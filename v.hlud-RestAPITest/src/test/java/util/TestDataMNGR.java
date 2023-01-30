package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Log4j
public class TestDataMNGR {
    public final static String JSON_SOURCE = "src/test/resources/testData.json";
    public static JSONParser parser;
    private static ObjectMapper objectMapper;

    static {
        parser = new JSONParser();
        objectMapper = new ObjectMapper();
    }

    public static <T> T readJSONtoBean(String source, Class<?> target) {
        T data = null;
        try {
            data = (T) objectMapper.readValue(new File(source), Class.forName(target.getName()));
        } catch (IOException | ClassNotFoundException e) {
            log.error("parsing errror", e);
        }
        return data;
    }

    public static <T> T getValue(String key) {
        T value = null;
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(JSON_SOURCE));
            value = (T) jsonObject.get(key);
        } catch (Exception e) {
            log.error("parsing error", e);
        }
        return value;
    }

    public static <T> T getValueFromArray(String listName, String key) {
        T value = null;
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(JSON_SOURCE));
            JSONArray elements = (JSONArray) jsonObject.get(listName);
            for (Object object : elements) {
                JSONObject obj = (JSONObject) object;
                value = (T) obj.get(key);
            }
        } catch (Exception e) {
            log.error("parsing error", e);
        }
        return value;
    }
}

