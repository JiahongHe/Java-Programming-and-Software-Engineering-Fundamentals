
/**
 * Write a description of BabyNnames here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import org.apache.commons.csv.*;
import edu.duke.*;
import java.io.*;
public class BabyNnames {
    public int getRank(int year, String name, String gender) {
        String file_name = make_file_name(year);
        FileResource fr = new FileResource(file_name);
        CSVParser parser = fr.getCSVParser(false);
        int rank = -1;
        for (CSVRecord record : parser) {
            if (gender.equals("F") || gender.equals("Female")|| gender.equals("female")) {
                if (record.get(1).equals("F")) {
                    if (rank == -1) {
                        rank = 0;
                    }
                    rank ++;
                    if (name.equals(record.get(0))) {
                        return rank;
                    }
                }
            }
            else if (gender.equals("M") || gender.equals("Male")|| gender.equals("male")) {
                if (record.get(1).equals("M")) {
                    if (rank == -1) {
                        rank = 0;
                    }
                    rank ++;
                    if (name.equals(record.get(0))) {
                        return rank;
                    }
                }
            }
        }
        rank = -1;
        return rank;
    }
    
    public String getName(int year, int rank, String gender) {
        String file_name = make_file_name(year);
        FileResource fr = new FileResource(file_name);
        CSVParser parser = fr.getCSVParser(false);
        int curr_rank = -1;
        for (CSVRecord record : parser) {
            if (gender.equals("F") || gender.equals("Female")|| gender.equals("female")) {
                if (record.get(1).equals("F")) {
                    if (curr_rank == -1) {
                        curr_rank = 0;
                    }
                    curr_rank ++;
                    if (curr_rank == rank) {
                        return record.get(0);
                    }
                }
            }
            else if (gender.equals("M") || gender.equals("Male")|| gender.equals("male")) {
                if (record.get(1).equals("M")) {
                    if (curr_rank == -1) {
                        curr_rank = 0;
                    }
                    curr_rank ++;
                    if (curr_rank == rank) {
                        return record.get(0);
                    }
                }
            }
        }
        return "NO NAME";
    }
    
    public result yearOfHighestRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int year_highest = -1;
        int rank_highest = -1;
        int year = -1;
        result res = new result();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser(false);
            year = Integer.parseInt(f.getName().substring(3,7));
            for (CSVRecord record : parser) {
                if (rank_highest == -1) {
                    rank_highest = getRank (year, name, gender);
                    year_highest = year;
                    continue;
                }
                else if (rank_highest > getRank (year, name, gender)) {
                    rank_highest = getRank (year, name, gender);
                    year_highest = year;
                }
            }
        }
        res.year = year_highest;
        res.rank = rank_highest;
        return res;
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        String curr_name = null;
        int name_rank = getRank(year, name, gender);
        int total_birth = 0;
        int birth_count = 0;
        for (int rank = 1; rank < name_rank; rank++) {
            curr_name = getName(year, rank, gender);
            birth_count = getBirth(year, curr_name, gender);
            total_birth += birth_count;
        }
        return total_birth;
    }
    
    public String make_file_name(int year) {
        String file_name = "us_babynames_by_year//yob" + year + ".csv";
        String year_s = Integer.toString(year);
        return file_name;
    }
    
    public int getBirth(int year, String name, String gender) {
        String file_name = make_file_name(year);
        FileResource fr = new FileResource(file_name);
        CSVParser parser = fr.getCSVParser(false);
        for(CSVRecord record : parser) {
            if (name.equals(record.get(0)) && gender.equals(record.get(1))) {
                return Integer.parseInt(record.get(2));
            }
        }
        return 0;
    }
    
    public double getAverageRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int count = 0;
        int total = 0;
        int rank = 0;
        int year;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser(false);
            year = Integer.parseInt(f.getName().substring(3,7));
            for (CSVRecord record : parser) {
                rank = getRank (year, name, gender);
                total += rank;
                count++;
            }
        }
        if (count == 0) {
            return -1;
        }
        double res = total / (double) count;
        return res;
    }
    
    public void whatIsNameInYear(String name, int year, int new_year, String gender) {
        int rank = getRank(year, name, gender);
        String he_she = null;
        if (gender.equals("F")) {
            he_she = "she";
        }
        if (gender.equals("M")) {
            he_she = "he";
        }
        String new_name = getName(new_year, rank, gender);
        System.out.print(name + " born in " + year);
        System.out.println(" would be " + new_name + " if " + he_she + " was born in " + new_year);
    }
    
    public void totalBirths () {
        int boy_birth_count = 0;
        int girl_birth_count = 0;
        int boy_name_count = 0;
        int girl_name_count = 0;
        String sex;
        int count;
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord record : parser) {
            sex = record.get(1);
            count = Integer.parseInt(record.get(2));
            if (sex.equals("F")) {
                girl_birth_count += count;
                girl_name_count ++;
            }
            if (sex.equals("M")) {
                boy_birth_count += count;
                boy_name_count ++;
            }
        }
        int name_total = girl_name_count + boy_name_count;
        int birth_total = girl_birth_count + boy_birth_count;
        System.out.println("total name count: " + name_total);
        System.out.println("boys: " + boy_name_count + " girls: " + girl_name_count);
        System.out.println("total birth count: " + birth_total);
        System.out.println("boys: " + boy_birth_count + " girls: " + girl_birth_count);
    }
    
    public static void main(String[] args) { 
        BabyNnames baby = new BabyNnames();
        //baby.whatIsNameInYear("Owen", 1974, 2014, "F");
        //result res = baby.yearOfHighestRank("Genevieve", "F");
        //System.out.println("Genevieve's highest rank was " + res.rank + " in " + res.year);
        //double avg_rank = baby.getAverageRank("Susan", "F");
        //System.out.println("Susan's average rank was " + avg_rank);
        int birth_total = baby.getTotalBirthsRankedHigher(2012, "Drew", "M");
        System.out.println("total birth count before Drew in 1990: " + birth_total);
    }
    
}
