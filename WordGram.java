
public class WordGram {
    private String[] myWords;
    private int myHash;
    
    //intializes array to a subArray that is passed as a parameter
    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }

    public int length(){
        return myWords.length;
    }

    public String toString(){
        String ret = "";
        for(String s : myWords){
            ret +=" "+s;
        }
        return ret.trim();
    }
    
    //compares myWords array to subarray of the training text to see if they are equal 
    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        if(other.length()!=myWords.length){
            return false;
        }
        
        for(int i=0;i<myWords.length;i++){
            if(!myWords[i].equals(other.wordAt(i))){
                return false;
            }
        }
        return true;
    }
    
    // shift all words one towards 0 and add word at the end, first word is lost
    public WordGram shiftAdd(String word) { 
        WordGram out = new WordGram(myWords, 0, myWords.length);
        
        for(int i=0;i<myWords.length-1;i++){
            myWords[i]=myWords[i+1];
        }
        myWords[myWords.length-1]=word;
        return out;
    }
    
    //overriding hashCode so that object can be mapped to its corresponding ArrayList
    public int hashCode(){
        return toString().hashCode();
    }
}
