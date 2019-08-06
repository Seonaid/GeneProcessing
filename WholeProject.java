
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
    public String findSimpleGene(String dna, String startCodon, String stopCodon){
        return "";
    }
    
    public void testFindSimpleGene(){
        String testDNA = "";
        String testGene = "";
        String startCodon = "ATG";
        String stopCodon = "TAA";
        // Case 1: startCodon-stopCodon, no intermediate code
        testDNA = "ATGTAA";
        testGene = findSimpleGene(testDNA, startCodon, stopCodon);
        if (testGene != "ATGTAA") System.out.println("Cannot find basic gene: " + testDNA);
        
        // Case2: works with lower case dna
        // Case3: works with lower case start and stop codons
        
    }
}
