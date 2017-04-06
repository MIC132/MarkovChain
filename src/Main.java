/**
 * Created by MIC on 2017-04-06.
 */
public class Main {
    public static void main(String[] args) {
        ChainBase base = Parser.parseFile("bible.txt");
        System.out.println(base.generateSentence(10));
    }
}
