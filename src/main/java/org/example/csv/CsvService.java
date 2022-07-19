package org.example.csv;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.csv.CsvReader.getJsonParsedToCsv;
import static org.example.json.JsonFlattener.getFlattenedJson;
import static org.example.json.JsonFromUrlReader.getJsonFromUrl;
import static org.example.json.JsonToCsvConverter.JsonToCsv;

@Service
public class CsvService {
    public String getFieldsFromUrl(int x){
        JsonToCsv(getFlattenedJson(getJsonFromUrl(x)));
        List<String> fields = new ArrayList<>();
        fields.add("_type");
        fields.add("_id");
        fields.add("name");
        fields.add("type");
        fields.add("latitude");
        fields.add("longitude");
        return getJsonParsedToCsv(fields);
    }

    public String getChosenFieldsFromUrl(int x, List<String> fields){
        JsonToCsv(getFlattenedJson(getJsonFromUrl(x)));
        return getJsonParsedToCsv(fields);
    }

}
