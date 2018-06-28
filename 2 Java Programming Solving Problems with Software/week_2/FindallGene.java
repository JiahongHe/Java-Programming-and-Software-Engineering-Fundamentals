
/**
 * Write a description of FindallGene here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.StorageResource;
import edu.duke.FileResource;

public class FindallGene {
    public Result findGene(String dna, int index_start) {
        Result result = new Result();
        int startIndex = dna.indexOf("ATG", index_start);
        if (startIndex == -1) {
            result.status = -1; // status -1: no start codon found after index_start
            return result;
        }
        int currIndex = findStopCodon(dna, startIndex);
        while (currIndex != -1) {
            if ((currIndex - startIndex) % 3 == 0) {
                String gene = dna.substring(startIndex, currIndex + 3);
                result.sequence = gene;
                result.status = 1; // status 1: gene found
                result.last_index = currIndex + 3; // index of the last used dna+1
                return result;
            }
            else {
                currIndex += 1;
                currIndex = findStopCodon(dna, currIndex);
            }
        }
        result.status = -2; // status -2: no stop codon found for this particular start codon
        result.last_index = startIndex + 1;
        return result; 
    }
    
    public int findStopCodon(String dna, int start_index) {
        int index_taa = dna.indexOf("TAA", start_index);
        int index_tag = dna.indexOf("TAG", start_index);
        int index_tga = dna.indexOf("TGA", start_index);
        int index = -1;
        if(index_taa > 0 & index_tag > 0 ) {
            index = Math.min(index_taa, index_tag);
        }
        else if (index_taa > 0 & index_tag < 0) {
            index = index_taa;
        }
        else if (index_taa < 0 & index_tag > 0) {
            index = index_tag;
        }
        else {
            index = -1;
        }
        if(index > 0 & index_tga > 0 ) {
            index = Math.min(index, index_tga);
        }
        if (index < 0 & index_tga > 0) {
            index = index_tga;
        }
        return index;
    }
    
    public void findAllGene(String dna) {
        int curr_index = 0;
        int count = 0;
        Result result = new Result();
        StorageResource sr = new StorageResource();
        result = findGene(dna, curr_index);
        while (result.status != -1) {
            if(result.status == 1){
                System.out.println("Gene found: " + result.sequence);
                sr.add(result.sequence);
                count += 1;
            }
            curr_index = result.last_index;
            result = findGene(dna, curr_index);
        }
        System.out.println("total number of found genes: " + count);
    }
    
    public void test(String testCase) {
        findAllGene(testCase);
    }
    
    public StorageResource getAllGenes(String dna) {
        int curr_index = 0;
        int count = 0;
        Result result = new Result();
        StorageResource sr = new StorageResource();
        result = findGene(dna, curr_index);
        while (result.status != -1) {
            if(result.status == 1){
                System.out.println("Gene found: " + result.sequence);
                sr.add(result.sequence);
                count += 1;
            }
            curr_index = result.last_index;
            result = findGene(dna, curr_index);
        }
        System.out.println("total number of found genes: " + count);
        return sr;
    }
    public int occurence(String s, String c){
        int count = 0;
        int index = s.indexOf(c);
        while (index != -1) {
            count += 1;
            index = s.indexOf(c, index + 1);
        }
        return count;
    }
    
    public int countCTG(String dna) {
        int count = occurence(dna, "CTG");
        return count;
    }
    
    public double cgRatio(String dna){
        int c_count = occurence(dna, "C");
        int g_count = occurence(dna, "G");
        double total = (double)dna.length();
        return (c_count + g_count)/total;
    }
    
    public void processGenes(StorageResource sr) {
        int count_longer_than_9 = 0;
        int count_cgRation_greater_035 = 0;
        int longest_length = 0;
        String longest_s = "";
        for (String s : sr.data()) {
            int len = s.length();
            if (len > 9) {
                System.out.println(s);
                count_longer_than_9 += 1;
            }
            if (len > longest_length) {
                longest_s = s;
                longest_length = len;
            }
            if (cgRatio(s) > 0.35) {
                System.out.println(s);
                count_cgRation_greater_035 += 1;
            }
        }
        System.out.println("number of genes longer than 9 " + count_longer_than_9);
        System.out.println("number of genes that has CG ration greater than 0.35 " + count_cgRation_greater_035);
    }
    public static void main(String[] args) {
        FindallGene find = new FindallGene();
        StorageResource sr = new StorageResource();
        String testCase1 = "ATCGATTCGAATGGCCGATATATAACTAGTAGTATAGCGGCCTAATATGCCACTCGTCATGTAACCAACCTGCAATGTGAACGTACTAGTTCAGTAA";
        String testCase2 = "AATGCTAACTAGCTGACTAAT";
        String testCase3 = "ATGCCCCCCTAACCATGCTGACCATGCCCCCCCCCTGAATGCCCTAG";
        FileResource fr = new FileResource("D:\\Program Files (x86)\\BlueJ\\BlueJProjects\\Week2Coding\\brca1line.fa");
        String dna = fr.asString().toUpperCase();
        //System.out.println(dna);
        sr = find.getAllGenes(dna);
        find.processGenes(sr);
        //find.test(testCase3);
        //find.test(testCase1);
    }
}
    
    
    
    
    
