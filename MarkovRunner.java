import edu.duke.*;

public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWord markovWord = new MarkovWord(5); 
        runModel(markovWord, st, 200,844); 
    } 

    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
    } 
    
    public void testHashMap(){
   
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' ');
        
        EfficientMarkovWord markovWord = new EfficientMarkovWord(2); 
        runModel(markovWord, st, 50,65); 
        
    }
    
    public void compareMethods(){
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        
        MarkovWord markovWord = new MarkovWord(2); 
        EfficientMarkovWord eMarkovWord = new EfficientMarkovWord(2); 
        
        long startTime = System.currentTimeMillis();
        runModel(markovWord, st, 1000,42);
        long endTime = System.currentTimeMillis();
        double inefficient = endTime - startTime;
        System.out.println("That took " + inefficient + " milliseconds\n");
        
        System.out.println("Starting efficient markov...");
        
        startTime = System.currentTimeMillis();
        runModel(eMarkovWord, st, 1000,42);
        endTime = System.currentTimeMillis();
        double efficient = endTime - startTime;
        System.out.println("That took " + efficient + " milliseconds\n");
        
        System.out.println("\nInefficent: " + inefficient + " milliseconds\n");
        System.out.println("Efficient: " + efficient + " milliseconds\n");
    }

}
