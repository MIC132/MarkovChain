import parsing.ChainBase;
import parsing.Parser;

/**
 * Created by MIC on 2017-04-06.
 */
public class Main {
    public static void main(String[] args) {
        ChainBase base = Parser.parseFile("lovecraft.txt",3);
        System.out.println(base.getFrequentFollowing("the", 10));
        /*System.out.println(base.generateGreaterSentence(20));
        System.out.println(base.generateGreaterSentence(20));
        System.out.println(base.generateGreaterSentence(20));
        System.out.println(base.generateGreaterSentence(20));
        System.out.println(base.generateGreaterSentence(20));
        System.out.println(base.getWeightedNextTop(new ArrayList<String>(), 10));*/
    }
}
