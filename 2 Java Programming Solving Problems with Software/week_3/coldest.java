
/**
 * Write a description of coldest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class coldest {
     public CSVRecord lowestHourInFile(CSVParser parser, String item) {
         CSVRecord coldest = null;
         for (CSVRecord record : parser) {
             if (record.get(item).equals("N/A")){
                 continue;   
             }
             if (coldest == null) {
                 coldest = record;
             }
             if (Double.parseDouble(coldest.get(item)) > Double.parseDouble(record.get(item))) {
                 coldest = record;
             }
         }
         return coldest;
     }
     
     public double averageTemperatureInFile(CSVParser parser, double humidity_threshold, boolean with_humidity) {
         double total = 0;
         double curr_temp;
         int count = 0;
         int low_count = 0;
         for (CSVRecord record : parser) {
             if (record.get("TemperatureF").equals("N/A")){
                 continue;   
             }
             curr_temp = Double.parseDouble(record.get("TemperatureF"));
             if (with_humidity) {
                 if (Double.parseDouble(record.get("Humidity")) < humidity_threshold) {
                     low_count ++ ;
                     continue;
                 }
             }
             total += curr_temp;
             count++;
         }
         if (low_count >= count) {
             System.out.println("No temperatures with that humidity");
         }
         return total/count;
     }
     
     public CSVRecord lowestHumidityInFile(CSVParser parser) {
        return lowestHourInFile(parser, "Humidity");
     }
     
     public CSVRecord coldestHourInFile(CSVParser parser) {
        return lowestHourInFile(parser, "TemperatureF");
     }
     
     public CSVParser get_parser(FileResource fr) {
        CSVParser parser = fr.getCSVParser();
        return parser;
    }
     
     public result fileWithColdestTemperature() {
         DirectoryResource dr = new DirectoryResource();
         result res = new result();
         File coldest = null;
         CSVRecord coldest_record = null;
         for (File f : dr.selectedFiles()) {
             FileResource fr = new FileResource(f);
             CSVParser curr_parser= get_parser(fr);
             if (coldest == null) {
                 coldest = f;
                 coldest_record = coldestHourInFile(curr_parser);
                 continue;
             }
             CSVRecord curr_row = coldestHourInFile(curr_parser);
             if (Double.parseDouble(curr_row.get("TemperatureF")) < Double.parseDouble(coldest_record.get("TemperatureF"))) {
                 coldest = f;
                 coldest_record = curr_row;
             }
         }
         res.record = coldest_record;
         res.file_name = coldest.getName();
         return res;
     }
     
     public result fileWithLowestHumidity() {
         DirectoryResource dr = new DirectoryResource();
         result res = new result();
         File coldest = null;
         CSVRecord coldest_record = null;
         for (File f : dr.selectedFiles()) {
             FileResource fr = new FileResource(f);
             CSVParser curr_parser= get_parser(fr);
             if (coldest == null) {
                 coldest = f;
                 coldest_record = lowestHumidityInFile(curr_parser);
                 continue;
             }
             CSVRecord curr_row = lowestHumidityInFile(curr_parser);
             if (Double.parseDouble(curr_row.get("Humidity")) < Double.parseDouble(coldest_record.get("Humidity"))) {
                 coldest = f;
                 coldest_record = curr_row;
             }
         }
         res.record = coldest_record;
         res.file_name = coldest.getName();
         return res;
     }
     
     public void testFileWithColdestTemperature() {
         DirectoryResource dr = new DirectoryResource();
         result coldest_res = fileWithColdestTemperature();
         String time = coldest_res.record.get("TimeEST");
         System.out.println("Coldest day was in file " + coldest_res.file_name);
         System.out.println("Coldest temperature on that day was " + coldest_res.record.get("TemperatureF"));
         System.out.println("at: " + time);
         /*
         System.out.println("All the Temperatures on the coldest day were");
         CSVRecord coldest;
         String name;
         String time;
         String temp;
         for (File f : dr.selectedFiles()) {
             FileResource fr = new FileResource(f);
             CSVParser parser= get_parser(fr);
             coldest = lowestHourInFile(parser, "TemperatureF");
             temp = coldest.get("TemperatureF");
             time = coldest.get("TimeEST");
             name = f.getName().substring(8, 18);
             System.out.println(name + " " + time + ": " + temp);
         }
         */
     }
     
     public void testfileWithLowestHumidity() {
         DirectoryResource dr = new DirectoryResource();
         result lowest_res = fileWithLowestHumidity();
         String time = lowest_res.record.get("DateUTC");
         System.out.println("Coldest day was in file " + lowest_res.file_name);
         System.out.println("Coldest temperature on that day was " + lowest_res.record.get("TemperatureF"));
         System.out.println("at: " + time);
     }
     
     public void testLowestHumidityInFile() {
         DirectoryResource dr = new DirectoryResource();
         double lowest_humi = 99999999;
         String time_lowest = "";
         for (File f : dr.selectedFiles()) {
             FileResource fr = new FileResource(f);
             CSVParser parser = fr.getCSVParser();
             CSVRecord lowest = lowestHumidityInFile(parser);
             double lowest_h = Double.parseDouble(lowest.get("Humidity"));
             String time = lowest.get("DateUTC");
             String date = f.getName().substring(8, 18);
             if (lowest_h < lowest_humi) {
                    lowest_humi = lowest_h;
                    time_lowest = time;
             }
             System.out.println("Lowest Humidity was " + lowest_h + " at " + date + " " + time);
         }
         
         System.out.println("Lowest Humidity was " + lowest_humi + " at " + time_lowest);
     }
     
     public void testAverageTemperatureInFile() {
         FileResource fr = new FileResource();
         CSVParser parser = fr.getCSVParser();
         double avg_temp = averageTemperatureInFile(parser, 80, true);
         System.out.println("Average Temp when high Humidity is " + avg_temp);
     }
     
}
