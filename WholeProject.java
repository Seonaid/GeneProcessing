
/**
 * Write a description of WholeProject here.
 * 
 * This project will take a DNA file as input, find all the genes in the file, 
 * and output a list of all genes with more than a specific number of codons, 
 * as well as producing a list of all genes with a high CG-ratio
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WholeProject {
    public String findSingleGene(String dna, String startCodon, String stopCodon){
        String result = "";
        int startIndex = dna.toUpperCase().indexOf(startCodon.toUpperCase());
        int stopIndex = dna.length();
        
        // If the startCodon is not found in the dna string, there is no gene
        if (startIndex == -1){
            return result;
        }
        
        stopIndex = dna.toUpperCase().indexOf(stopCodon.toUpperCase(), startIndex+3);
        if (stopIndex == -1){
            return result; // if there is no stop codon, there is no gene
        }       
        
        result = dna.substring(startIndex, stopIndex+3);
        return result;
    }
    
    public void testFindSingleGene(){
        System.out.println("\nStart testFindSingleGene");
        String testDNA = "";
        String testGene = "";
        String startCodon = "ATG";
        String stopCodon = "TAA";
        
        // Case: no start codon = no gene
        testDNA = "TAACCGTGA";
        testGene = findSingleGene(testDNA, startCodon, stopCodon);
        if (!testGene.equals("")) System.out.println("Should not find gene without startCodon: " + testDNA);
        
        // Case: startCodon-stopCodon, no intermediate code
        testDNA = "ATGTAA";
        testGene = findSingleGene(testDNA, startCodon, stopCodon);
        if (!testGene.equals("ATGTAA")) System.out.println("Cannot find basic gene: " + testDNA);
        
        // Case: Finds gene with three codons
        testDNA = "ATGCGATAA";
        testGene = findSingleGene(testDNA, startCodon, stopCodon);
        if (!testGene.equals("ATGCGATAA")) System.out.println("Cannot find three codon gene: " + testDNA);        
        
        // Case: Finds gene with trailing codons
        testDNA = "ATGCGATAACCG";
        testGene = findSingleGene(testDNA, startCodon, stopCodon);
        if (!testGene.equals("ATGCGATAA")) System.out.println("Trailing codons: " + testGene);        

        // Case: works with lower case dna
        testDNA = "atgcgataaccg";
        testGene = findSingleGene(testDNA, startCodon, stopCodon);
        if (!testGene.equals("atgcgataa")) System.out.println("Lower case dna: " + testGene);              

        // Case: works with lower case start codon
        startCodon = "atg";
        testGene = findSingleGene(testDNA, startCodon, stopCodon);
        if (!testGene.equals("atgcgataa")) System.out.println("Lower case startCodon: " + testGene);
        
        // Case: works with "TAG" stop codon
        stopCodon = "TAG";
        testDNA = "atgcgatagccg";        
        testGene = findSingleGene(testDNA, startCodon, stopCodon);
        if (!testGene.equals("atgcgatag")) System.out.println("Change stopCodon: " + testGene);        
        
        System.out.println("All tests run FindSingleGene");
        
    }
}
