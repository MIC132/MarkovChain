import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by MIC on 2017-04-10.
 */
public abstract class ChainElement {
    private static Random rng = new Random();
    protected HashMap<String, ChainWord> words = new HashMap<>();
    //Counts how many times a word has occured
    protected int count = 0;
    //Counts how many times a word was the last word of a sentence
    protected int sentenceEndCount = 0;
    //Sum of "count" of all words that follow this one. Used for random generation
    protected int followCount;
    //How deep is the tree, counting from this word. Last layer has 0, etc.
    protected final int depth;

    protected ChainElement(int depth) {
        this.depth = depth;
    }

    /*The string versions simply add the word as following to this word, and/or increment it's count by one*/

    void addWord(String word){
        addWord(word, false);
    }

    void addWord(String word, boolean senteceEnd){
        List<String> tmp = new ArrayList<>();
        tmp.add(word);
        addWord(tmp, senteceEnd);
    }

    /*The list versions accept a list of words, where the last word in the list is the one actually being added, and the others are a sequence of predecessors.
    If any of the words in the list is not in the structure, it gets added dynamically*/

    void addWord(List<String> wordList){
        addWord(wordList, false);
    }

    void addWord(List<String> wordList, boolean sentenceEnd){
        String word = wordList.get(0);
        ChainWord target = words.computeIfAbsent(word, w -> new ChainWord(w, depth - 1));
        if(wordList.size() == 1){
            target.count++;
            followCount++;
            if(sentenceEnd){
                target.sentenceEndCount++;
            }
        }else{
            target.addWord(wordList.subList(1, wordList.size()));
        }
    }

    public List<String> getFrequentFollowing(String word, int amount){
        List<String> tmp = new ArrayList<>();
        tmp.add(word);
        return getFrequentFollowing(tmp, amount);
    }

    /*The list version gives frequently following words after a chain of words.
    So if the list is [A,B,C] it will give words that follow C if that C follows B that follows A. And so on.*/

    public List<String> getFrequentFollowing(List<String> wordList, int amount){
        if(wordList.isEmpty()){
            List<String> list = words.values().stream().sorted((x,y) -> y.getCount() - x.getCount()).map(x -> x.word).collect(Collectors.toList());
            return list.subList(0, amount);
        }else{
            ChainWord target = words.get(wordList.get(0));
            if(target == null){
                return null;
            }
            return target.getFrequentFollowing(wordList.subList(1, wordList.size()),amount);
        }
    }

    public String getRandomWord(){
        int index = rng.nextInt(followCount);
        for(ChainWord e : words.values()){
            if(index < e.getCount()){
                return e.word;
            }else{
                index -= e.getCount();
            }
        }
        return "";
    }

    public String getRandomNext(List<String> wordList){
        if(wordList.size() > depth-1){
            wordList = wordList.subList(wordList.size() - (depth-1), wordList.size());
        }
        if(wordList.isEmpty()){
            return getRandomWord();
        }else{
            ChainWord target = words.get(wordList.get(0));
            if(target == null){
                return null;
            }else{
                return getRandomNext(wordList.subList(1, wordList.size()));
            }
        }
    }

    public String generateSentence(int length){
        List<String> sentence = new ArrayList<>();
        sentence.add(getRandomWord());
        while (sentence.size() < length){
            sentence.add(getRandomNext(sentence));
        }
        StringBuilder output = new StringBuilder();
        output.append(sentence.get(0).substring(0,1).toUpperCase()).append(sentence.get(0).substring(1));
        for(int i = 1; i<sentence.size(); i++){
            output.append(" ").append(sentence.get(i));
        }
        output.append(".");
        return output.toString();
    }

    public String generateGreaterSentence(int maxLength){
        List<String> sentence = new ArrayList<>();
        sentence.add(getRandomWord());
        String word = getSingleNext(sentence.get(0));
        do{
            sentence.add(word);
            word = getDoubleNext(sentence.get(sentence.size()-2), sentence.get(sentence.size()-1));
        } while (sentence.size() < maxLength && word != null);

        StringBuilder output = new StringBuilder();
        output.append(sentence.get(0).substring(0,1).toUpperCase()).append(sentence.get(0).substring(1));
        for(int i = 1; i<sentence.size(); i++){
            output.append(" ").append(sentence.get(i));
        }
        output.append(".");
        return output.toString();
    }

    public String getSingleNext(String word){
        List<String> possible = new ArrayList<>(words.get(word).words.keySet());
        int number = (new Random()).nextInt(possible.size());
        return possible.get(number);
    }

    public String getDoubleNext(String word1, String word2){
        ChainWord firstWord = words.get(word1);
        if (firstWord == null)
            return null;
        ChainWord secondWord = firstWord.words.get(word2);
        if (secondWord == null)
            return null;
        List<String> possible = new ArrayList<>(secondWord.words.keySet());
        if (possible.size() < 1)
            return null;

        int number = (new Random()).nextInt(possible.size());
        return possible.get(number);
    }


    public int getCount(){
        return count;
    }
}
