package org.example.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonFromUrlReader {
    private static final ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper(){
        return new ObjectMapper();
    }

    public JsonNode getJsonFromUrl(int quantity){
        try {
            InputStream is = new URL("http://localhost:8080/generate/json/" + quantity).openStream();
            return readJson(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode readJson(InputStream is) throws IOException {
        try (is) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringToJsonParser parser = new StringToJsonParser();
            String jsonText = readAll(rd);
            return parser.parse(jsonText);
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
