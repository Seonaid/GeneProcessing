
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
        while (stopIndex != -1){
            // look through the dna string for the next instance of the stopCodon
            // if it occurs, the gene length must be a multiple of three, or it is not a gene
            if ((stopIndex - startIndex) % 3 == 0){
                // the gene is a substring between the two codons
                result = dna.substring(startIndex, stopIndex+3);
                return result;
            } else {
                // look for another instance of the stop codon further along in the DNA
                stopIndex = dna.toUpperCase().indexOf(stopCodon.toUpperCase(), stopIndex+1);
            }

        }
        return result; // if there was no stop codon a proper distance away, return empty string
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
        
        // Case: There are start and stop codons, but they are not a gene
        // stopCodon = "TAG"
        testDNA = "atgcgtagaccg";        
        testGene = findSingleGene(testDNA, startCodon, stopCodon);
        if (!testGene.equals("")) System.out.println("Gene wrong length: " + testGene);
        
        // Case: There is a stop codon code earlier but you need the later one to find a gene
        // stopCodon = "TAG"
        testDNA = "atgcgtagaccgtagcgc";        
        testGene = findSingleGene(testDNA, startCodon, stopCodon);
        if (!testGene.equals("atgcgtagaccgtag")) System.out.println("Later stop codon: " + testGene);        
        
        System.out.println("All tests run FindSingleGene");
        
    }

    public int findGeneEnd(String dna, int startIndex, String stopCodon){
        String result = "";     
        int stopIndex = dna.toUpperCase().indexOf(stopCodon.toUpperCase(), startIndex);
        while (stopIndex != -1){
            // look through the dna string for the next instance of the stopCodon
            // if it occurs, the gene length must be a multiple of three, or it is not a gene
            if ((stopIndex - startIndex) % 3 == 0){
                // return the non-inclusive index for the stopCodon ending
                return stopIndex;
            } else {
                // look for another instance of the stop codon further along in the DNA
                stopIndex = dna.toUpperCase().indexOf(stopCodon.toUpperCase(), stopIndex+1);
            }

        }
        return dna.length(); // if there was no stop codon a proper distance away, return dna length
    }    

    public void testFindGeneEnd(){
        System.out.println("\nStart testFindGeneEnd");
        String testDNA = "";
        int testGeneEnd = 0;
        String stopCodon = "TAA";
        
        // Case: startCodon-stopCodon, no intermediate code
        testDNA = "ATGTAA";
        testGeneEnd = findGeneEnd(testDNA, 0, stopCodon);
        if (testGeneEnd != 6) System.out.println("Short gene with no codons: " + testGeneEnd);        
           
        System.out.println("All tests run FindGeneEnd");
    }
    
    public String pickAGene(String dna, int startIndex){
        // when given a string of dna where we don't know the stop codon,
        // this algorithm finds the shortest gene for each of three possible stopCodons
        // and returns that as the gene. If there is no gene, returns the empty string.
        String result = "";
        String startCodon = "ATG";
        startIndex = dna.toUpperCase().indexOf(startCodon.toUpperCase(), startIndex);
        
        // If the startCodon is not found in the dna string, there is no gene
        if (startIndex == -1){
            return result;
        }

        int taaEnd = findGeneEnd(dna, startIndex, "TAA");
        int tagEnd = findGeneEnd(dna, startIndex, "TAG");
        int tgaEnd = findGeneEnd(dna, startIndex, "TGA");
        
        int shortGene = Math.min(taaEnd, tagEnd);
        shortGene = Math.min(shortGene, tgaEnd);

        if (shortGene == dna.length()){
            return ""; // the stopIndex cannot be the dna length
        }
        
        return dna.substring(startIndex, shortGene+3);
    }
    
    public void testPickAGene(){
        System.out.println("\nStart testPickAGene");
        String testDNA = "";
        String testGene = "";

        // Case: startCodon-stopCodon, no intermediate code
        testDNA = "ATGTAA";
        testGene = pickAGene(testDNA, 0);
        if (!testGene.equals("ATGTAA")) System.out.println("Cannot find basic gene: " + testDNA);
        
        // Case: startCodon - codon - stopCodon
        testDNA = "ATGGCATAG";
        testGene = pickAGene(testDNA, 0);
        if (!testGene.equals("ATGGCATAG")) System.out.println("Cannot find three codon gene: " + testDNA);        
        
        // Case: there is a stop codon but no gene
        testDNA = "atgcgtagaccg";
        testGene = pickAGene(testDNA, 0);
        if (!testGene.equals("")) System.out.println("There is no gene here: " + testDNA);
        
        // Case: find "TGA" ending
        testDNA = "ATG123123123TGAAGA";
        testGene = pickAGene(testDNA, 0);
        if (!testGene.equals("ATG123123123TGA")) System.out.println("Did not find gene: " + testDNA);        
        
        System.out.println("All PickAGene tests run");

        
    }
    
    public void findAllGenes(String dna){
        // Takes an input dna that may contain zero-to-many genes
        // Loops through the dna, starting from the first instance of the startCodon and picking
        // a single gene (using pickAGene)...
        String currentGene = "";
        int startIndex = 0;
        while (true) {
            currentGene = pickAGene(dna, startIndex);
            if (currentGene.isEmpty()) break;
            // if there was a gene, print it
            System.out.println(currentGene);
            // move startIndex to just past the end of the gene
            startIndex = dna.toUpperCase().indexOf(currentGene.toUpperCase(), startIndex) + currentGene.length();
        }
        
        
        // set new dna string to the index that follows the gene we just got back
    }
    
    public void testFindAllGenes(){
        System.out.println("\nStart testFindAllGenes");
        String testDNA = "";
        
        // Case: two two-codon genes 
        System.out.println("Two genes, no spaces: ");
        testDNA = "ATGTAAATGTAG";
        findAllGenes(testDNA);
        
        // Case: two multi-codon genes
        // expected printout:
        // ATGATCTAA
        // ATGCTGCAACGGTGA
        System.out.println("Two multi-codon genes: ");
        testDNA = "ATGATCTAATTTATGCTGCAACGGTGAAGA";
        findAllGenes(testDNA);
        
        //expected printout:
        // atg123123123123tag
        // atg123123123123taa
        // atg123123tga
        System.out.println("Three genes: ");
        testDNA = "agacaagatg123123123123taggaccagaatg123123123123taaccagaatg123123tgacgaccatag";
        findAllGenes(testDNA);
        
        System.out.println("End testFindAllGenes... all tests run");
    }
    
}
