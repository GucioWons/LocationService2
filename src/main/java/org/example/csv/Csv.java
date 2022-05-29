package org.example.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class Csv {
    public static void CsvReader(List<String> list){
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data.csv"));
            CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(br)) {

            for(String tmp : list){
                System.out.print(tmp + ", ");
            }
            for(CSVRecord record : parser) {
                for(String tmp : list){
                    System.out.print(record.get(tmp) + ", ");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
