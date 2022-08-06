package org.example.json;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class JsonFromUrlReader {
    private final StringToJsonParser stringToJsonParser = new StringToJsonParser();
    @Value("${getjson.url}")
    private String getJsonUrl;

    public JsonNode getJsonFromUrl(int quantity){
        try {
            InputStream is = new URL(getJsonUrl + quantity).openStream();
            return readJson(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode readJson(InputStream is) throws IOException {
        try (is) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return stringToJsonParser.parse(jsonText);
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
