package org.example.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class CsvReader {
    public static String CsvReader(List<String> list){
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data.csv"));
            CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(br)) {
            String string = "";
            for(int i=0; i<list.size(); i++){
                if(i != list.size()-1){
                    string += (list.get(i) + ", ");
                }else{
                    string += (list.get(i) + "\n");
                }
            }
            for(CSVRecord record : parser) {
                for(int i = 0; i<list.size(); i++){
                    if(i != list.size()-1){
                        string += (record.get(list.get(i)) + ", ");
                    }else{
                        string += (record.get(list.get(i)) + "\n");
                    }
                }
            }
            System.out.println(string);
            return string;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
