package org.example.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class CsvReader {
    public static String getJsonParsedToCsv(List<String> fields){
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data.csv"));
            CSVParser parser = CSVFormat.DEFAULT.builder().setHeader().setDelimiter(',').build().parse(br)){
            return parseJsonToCsv(fields, parser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseJsonToCsv(List<String> fields, CSVParser parser){
        StringBuilder csvString = new StringBuilder();
        for(int i=0; i<fields.size(); i++){
            if(i != fields.size()-1){
                csvString.append(fields.get(i)).append(", ");
            }else{
                csvString.append(fields.get(i)).append("\n");
            }
        }
        for(CSVRecord record : parser) {
            csvString.append(readField(fields, record));
        }
        return csvString.toString();
    }

    public static String readField(List<String> fields, CSVRecord record){
        StringBuilder csvString = new StringBuilder();
        for(int i = 0; i<fields.size(); i++){
            if(i != fields.size()-1){
                csvString.append(record.get(fields.get(i))).append(", ");
            }else{
                csvString.append(record.get(fields.get(i))).append("\n");
            }
        }
        return csvString.toString();
    }
}
