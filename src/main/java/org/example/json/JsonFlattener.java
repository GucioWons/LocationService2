package org.example.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JsonFlattener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode getFlattenedJson(JsonNode jsonNode){
        List<Map<String,JsonNode>> namesAndObjects = new ArrayList<>();
        Iterator<JsonNode> jsonObjects = jsonNode.elements();
        for(int i=0; i<jsonNode.size(); i++){
            JsonNode jsonObject = jsonObjects.next();
            namesAndObjects.add(flattenJson(jsonObject));
        }
        return objectMapper.valueToTree(namesAndObjects);
    }

    private Map<String,JsonNode> flattenJson(JsonNode jsonObject){
        Map<String, JsonNode> namesAndValues = new HashMap<>();
        Iterator<JsonNode> jsonFields = jsonObject.elements();
        Iterator<String> jsonFieldNames = jsonObject.fieldNames();
        for(int i=0; i<jsonObject.size(); i++){
            JsonNode jsonNode = jsonFields.next();
            String jsonNodeFieldName = jsonFieldNames.next();
            rewriteJsonValues(jsonNode, jsonNodeFieldName, namesAndValues);
        }
        return namesAndValues;
    }

    private void rewriteJsonValues(JsonNode jsonNode, String jsonNodeFieldName, Map<String, JsonNode> namesAndValues){
        if(jsonNode.isObject()){
            Iterator<JsonNode> objectFields = jsonNode.elements();
            Iterator<String> objectFieldNames = jsonNode.fieldNames();
            putObjectNamesAndValues(objectFields, objectFieldNames, namesAndValues);
        }else{
            namesAndValues.put(jsonNodeFieldName, jsonNode);
        }
    }

    private void putObjectNamesAndValues(Iterator<JsonNode> objectFields, Iterator<String> objectFieldNames, Map<String, JsonNode> namesAndValues) {
        for(int i=1; i<=2; i++) {
            namesAndValues.put(objectFieldNames.next(), objectFields.next());
        }
    }
}
