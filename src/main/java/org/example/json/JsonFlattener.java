package org.example.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class JsonFlattener {
    private static final ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper(){
        return new ObjectMapper();
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

    private static Map<String,JsonNode> flattenJson(JsonNode jsonObject){
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

    private static void rewriteJsonValues(JsonNode jsonNode, String jsonNodeFieldname, Map<String, JsonNode> namesAndValues){
        if(jsonNode.isObject()){
            Iterator<JsonNode> objectFields = jsonNode.elements();
            Iterator<String> objectFieldnames = jsonNode.fieldNames();
            namesAndValues.put(objectFieldnames.next(), objectFields.next());
            namesAndValues.put(objectFieldnames.next(), objectFields.next());
        }else{
            namesAndValues.put(jsonNodeFieldname, jsonNode);
        }
    }
}
