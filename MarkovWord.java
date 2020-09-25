import java.util.*;

public class MarkovWord implements IMarkovModel{
    
    private String [] myText;
    private Random myRandom;
    private int myOrder;
    
    public MarkovWord(int order) {
        myOrder = order;
        myRandom = new Random();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        WordGram kGram = new WordGram(myText,index,myOrder);
        
        sb.append(kGram.toString());
        sb.append(" ");
        
        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = getFollows(kGram);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            kGram.shiftAdd(next);
        }
        
        return sb.toString().trim();
    }
    
    public int indexOf(String [] words, WordGram target, int start){
        
        for(int i=start;i<words.length-target.length();i++){
            String [] temp = Arrays.copyOfRange(words,i,i+target.length());
            WordGram wg = new WordGram(temp,0,target.length());
            if(target.equals(wg)){
                return i;
            }
        }
        
        return -1;
    }
    
    public void testIndexOf(){
        String s = "you thought that this was just a test, this is just a test";
        String [] words = s.split(" ");
        WordGram target = new WordGram(words,3,4);
        int start = 2;
        System.out.println(indexOf(words, target,start));
    }
    
    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        int idx=0, start=0;
        
        do{
            idx = indexOf(myText, kGram, start);
            if(idx!=-1&&idx<myText.length-kGram.length()){
                follows.add(myText[idx+kGram.length()]);
            }
            start = idx+kGram.length();
            
        }while(idx!=-1);
        
        return follows;
    }
}
