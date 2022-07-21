package org.example.csv;

import org.example.json.JsonFlattener;
import org.example.json.JsonFromUrlReader;
import org.example.json.JsonToCsvConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.csv.CsvReader.getJsonParsedToCsv;

@Service
public class CsvService {
    JsonFlattener jsonFlattener = new JsonFlattener();
    JsonToCsvConverter jsonToCsvConverter = new JsonToCsvConverter();
    JsonFromUrlReader jsonFromUrlReader = new JsonFromUrlReader();
    public String getFieldsFromUrl(int x){
        jsonToCsvConverter.JsonToCsv(jsonFlattener.getFlattenedJson(jsonFromUrlReader.getJsonFromUrl(x)));
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
        jsonToCsvConverter.JsonToCsv(jsonFlattener.getFlattenedJson(jsonFromUrlReader.getJsonFromUrl(x)));
        return getJsonParsedToCsv(fields);
    }

}
