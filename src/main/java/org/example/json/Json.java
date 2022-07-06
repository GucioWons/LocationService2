package org.example.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Json {
    private static final ObjectMapper objectMapper = getDefaultObjectMapper();
    private static final CsvMapper csvMapper = new CsvMapper();

    private static ObjectMapper getDefaultObjectMapper(){
        return new ObjectMapper();
    }

    public static JsonNode parse(String src){
        try {
            return objectMapper.readTree(src);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode getFlattenedJson(JsonNode jsonNode){
        List<Map<String,JsonNode>> namesAndObjects = new ArrayList<>();
        Iterator<JsonNode> jsonObjects = jsonNode.elements();
        for(int i=0; i<jsonNode.size(); i++){
            JsonNode jsonObject = jsonObjects.next();
            namesAndObjects.add(flattenJson(jsonObject));
        }
        return objectMapper.valueToTree(namesAndObjects);
    }

    public static Map<String,JsonNode> flattenJson(JsonNode jsonObject){
        Iterator<JsonNode> jsonFields = jsonObject.elements();
        Iterator<String> jsonFieldnames = jsonObject.fieldNames();
        Map<String, JsonNode> namesAndValues = new HashMap<>();
        for(int i=0; i<jsonObject.size(); i++){
            JsonNode jsonNode = jsonFields.next();
            String jsonNodeFieldname = jsonFieldnames.next();
            rewriteJsonValues(jsonNode, jsonNodeFieldname, namesAndValues);
        }
        return namesAndValues;
    }

    public static void rewriteJsonValues(JsonNode jsonNode, String jsonNodeFieldname, Map<String, JsonNode> namesAndValues){
        if(jsonNode.isObject()){
            Iterator<JsonNode> objectFields = jsonNode.elements();
            Iterator<String> objectFieldnames = jsonNode.fieldNames();
            namesAndValues.put(objectFieldnames.next(), objectFields.next());
            namesAndValues.put(objectFieldnames.next(), objectFields.next());
        }else{
            namesAndValues.put(jsonNodeFieldname, jsonNode);
        }
    }

    public static void JsonToCsv(JsonNode jsonNode){
        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        jsonNode.elements().next().fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
        try {
            csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValue(new File("src/main/resources/data.csv"), jsonNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JsonNode getJsonFromUrl(int quantity){
        try {
            InputStream is = new URL("http://localhost:8080/generate/json/" + quantity).openStream();
            return readJson(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode readJson(InputStream is) throws IOException {
        try (is) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return parse(jsonText);
        }
    }
}
