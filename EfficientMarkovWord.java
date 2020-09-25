import java.util.*;

public class EfficientMarkovWord implements IMarkovModel{
    private String [] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>> myMap;
    
    public EfficientMarkovWord(int order) {
        myOrder = order;
        myRandom = new Random();
        myMap =  new HashMap<WordGram, ArrayList<String>>();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
        buildMap();
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        WordGram kGram = new WordGram(myText,index,myOrder);
        
        sb.append(kGram.toString());
        sb.append(" ");
        
        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = getFollows(kGram);
            //System.out.println(follows);
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
        return myMap.get(kGram);
    }
    
    public void buildMap(){
        
        for(int i=0;i<myText.length-myOrder+1;i++){
            
            if(i==myText.length - myOrder){
                WordGram keyGram = new WordGram(myText,i,myOrder);
                if (!myMap.containsKey(keyGram)) {
                    myMap.put(keyGram, new ArrayList<String>());
                }
                break;
            }
            
            WordGram keyGram = new WordGram(myText,i,myOrder);
            String follow = myText[i+myOrder];
            
            if(!myMap.containsKey(keyGram)){
                ArrayList<String> list = new ArrayList<String>();
                list.add(follow);
                myMap.put(keyGram,list);
            }
            else{
                myMap.get(keyGram).add(follow);
            }  
        }
        printHashMapInfo();
    }
    
    public void printHashMapInfo(){
        //System.out.println("Keys: "+myMap.keySet());
        System.out.println("Number of Keys: "+myMap.size());
        int max=0;
        for(WordGram wg : myMap.keySet()){
            if(myMap.get(wg).size()>max){
                max = myMap.get(wg).size();
            }
        }
        System.out.println("Largest array list size: "+max);
        System.out.println("Keys mapped to array lists that are of the max size: ");
        for(WordGram wg : myMap.keySet()){
            if(myMap.get(wg).size()==max){
                System.out.println(wg.toString());
            }
        }
    }
}
