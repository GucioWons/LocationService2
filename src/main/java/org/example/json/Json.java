package org.example.json;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class Json {
    private static ObjectMapper objectMapper = getDefaultObjectMapper();
    private static CsvMapper csvMapper = new CsvMapper();

    private static ObjectMapper getDefaultObjectMapper(){
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        return defaultObjectMapper;
    }

    public static JsonNode parse(String src){
        try {
            return objectMapper.readTree(src);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static JsonNode parseMap(Map<String, JsonNode> map){
        String json = null;
        try {
            json = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return parse(json);
    }

    public static JsonNode toJson(Object a){
        return objectMapper.valueToTree(a);
    }
    public static JsonNode merge(JsonNode mainNode, JsonNode updateNode) {
        Iterator<String> fieldNames = updateNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode jsonNode = mainNode.get(fieldName);
            // if field exists and is an embedded object
            if (jsonNode != null && jsonNode.isObject()) {
                merge(jsonNode, updateNode.get(fieldName));
            }
            else {
                if (mainNode instanceof ObjectNode) {
                    // Overwrite field
                    JsonNode value = updateNode.get(fieldName);
                    ((ObjectNode) mainNode).put(fieldName, value);
                }
            }
        }
        return mainNode;
    }
    public static JsonNode flatten(JsonNode jsonNode){
        List<Map<String,JsonNode>> maps = new ArrayList<Map<String,JsonNode>>();
        Iterator<JsonNode> elements = jsonNode.elements();
        for(int i=0; i<jsonNode.size(); i++){
            JsonNode tmp = elements.next();
            Iterator<JsonNode> fields = tmp.elements();
            Iterator<String> fieldnames = tmp.fieldNames();
            Map<String, JsonNode> map = new HashMap<>();
            for(int j=0; j<tmp.size(); j++){
                JsonNode tmp2 = fields.next();
                String fieldname = fieldnames.next();
                if(tmp2.isObject()){
                    Iterator<JsonNode> objectFields = tmp2.elements();
                    Iterator<String> objectFieldnames = tmp2.fieldNames();
                    map.put(objectFieldnames.next(), objectFields.next());
                    map.put(objectFieldnames.next(), objectFields.next());
                }else{
                    map.put(fieldname, tmp2);
                }
            }
            maps.add(map);
        }
        System.out.println(objectMapper.valueToTree(maps));
        return objectMapper.valueToTree(maps);
    }
    public static void JsonToCsv(JsonNode jsonNode){
        System.out.println(jsonNode);
        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        jsonNode.elements().next().fieldNames().forEachRemaining(f -> csvSchemaBuilder.addColumn(f));
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
    public static JsonNode JsonFromUrl(int x){
        InputStream is = null;
        try {
            is = new URL("http://localhost:8080/generate/json/" + x).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JsonNode json = parse(jsonText);
                return json;
            } finally {
                is.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
