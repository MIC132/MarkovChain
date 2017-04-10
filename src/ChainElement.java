import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by MIC on 2017-04-10.
 */
public abstract class ChainElement {
    private Random rng = new Random();
    protected HashMap<String, ChainWord> words = new HashMap<>();
    protected int count = 1;
    protected int followCount;
    protected final int depth;

    protected ChainElement(int depth) {
        this.depth = depth;
    }

    void addWord(String word){
        List<String> tmp = new ArrayList<>();
        tmp.add(word);
        addWord(tmp);
    }

    void addWord(List<String> wordList){
        String word = wordList.get(0);
        ChainWord target = words.computeIfAbsent(word, w -> new ChainWord(w, depth - 1));
        if(wordList.size() == 1){
           target.count++;
           followCount++;
        }else{
            target.addWord(wordList.subList(1, wordList.size()));
        }

    }

    public List<String> getFrequentFollowing(String word, int amount){
        List<String> tmp = new ArrayList<>();
        tmp.add(word);
        return getFrequentFollowing(tmp, amount);
    }

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


    public int getCount(){
        return count;
    }
}
