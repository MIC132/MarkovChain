/**
 * Created by MIC on 2017-04-06.
 */
public class Main {
    public static void main(String[] args) {
        ChainBase base = Parser.parseFile("lovecraft.txt",2);
        System.out.println(base.getFrequentFollowing("the", 10));
        System.out.println(base.generateSentence(20));
    }
}
