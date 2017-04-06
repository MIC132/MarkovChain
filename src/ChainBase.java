import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by MIC on 2017-04-06.
 */
public class ChainBase {
    Random rng = new Random();
    HashMap<String, ChainElement> words = new HashMap<>();

    private int countSum = 0;

    public void addWord(String word){
        ChainElement find = words.get(word);
        if(find != null){
            find.incCount();
        }else{
            ChainElement tmp = new ChainElement(word);
            words.put(word,tmp);
        }
        countSum++;
    }

    public List<String> getFrequentFollowing(String word, int amount){
        List<String> list = words.get(word).words.values().stream().sorted((x,y) -> y.getCount() - x.getCount()).map(x -> x.word).collect(Collectors.toList());
        return list.subList(0, amount);
    }

    public String getRandomWord(){
       int index = rng.nextInt(countSum);
       for(ChainElement e : words.values()){
           if(index < e.getCount()){
               return e.word;
           }else{
               index -= e.getCount();
           }
       }
       return "";
    }

    public String generateSentence(int length){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i< length; i++){
            output.append(getRandomWord());
            if(i != length-1){
                output.append(" ");
            }
        }
        return output.toString();
    }
}
