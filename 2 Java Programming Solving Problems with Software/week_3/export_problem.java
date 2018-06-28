
/**
 * Write a description of CSV_practice here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
public class export_problem {
    public CSVParser parse_csv() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        return parser;
    }
    
    public String countryInfo(CSVParser parser, String country) {
        String country_name;
        for (CSVRecord record : parser) {
            country_name = record.get("Country");
            if (country_name.equals(country)) {
                String exports = record.get("Exports");
                String val = record.get("Value (dollars)");
                String result = country_name + ": " + exports + ": " + val;
                return result;
            }
        }
        return "NOT FOUND";
    }
    
    public void  listExportersTwoProducts(CSVParser parser, String item1, String item2) {
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(item1) && exports.contains(item2)) {
                String name = record.get("Country");
                System.out.println(name);
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem) {
        int count = 0;
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(exportItem)) {
                count++;
            }
        }
        return count;
    }
    
    public void bigExporters(CSVParser parser, String amount) {
        for (CSVRecord record : parser) {
            String val = record.get("Value (dollars)");
            if (val.length() > amount.length()) {
                System.out.println(record.get("Country"));
            }
        }
    }
    public static void main() {
        export_problem p = new export_problem();
        CSVParser parser = p.parse_csv();
        //p.listExportersTwoProducts(parser, "cotton", "flowers");
        //int number = p.numberOfExporters(parser, "cocoa");
        //String info = p.countryInfo(parser, "Nauru");
        //System.out.println("number of countries that exports cocoa: " + number);
        //System.out.println(info);
        p.bigExporters(parser, "$999,999,999,999");
    }
}