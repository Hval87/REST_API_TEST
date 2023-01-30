package util;

import lombok.extern.log4j.Log4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

@Log4j
public class ConfigDataMNGR {

    private static final String JSON_CONFIG_SOURCE = "src/test/resources/ConfigData.json";
    private static JSONParser parser;
    static{
        parser = new JSONParser();
    }

    public static <T> T getConfValue(String key) {
        JSONObject data = null;
        try {
            data = (JSONObject) parser.parse(new FileReader(JSON_CONFIG_SOURCE));
        } catch (Exception e) {
            log.error("parsing error", e);
        }
        return (T) data.get(key);
    }

    public static String getValueFromArray(String listName, String key) {
        String value = null;
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(JSON_CONFIG_SOURCE));
            JSONArray elements = (JSONArray) jsonObject.get(listName);
            for (Object object : elements) {
                JSONObject obj = (JSONObject) object;
                value = obj.get(key).toString();
            }
        } catch (Exception e) {
            log.error("parsing error", e);
        }
        return value;
    }
}