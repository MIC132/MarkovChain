/**
 * Created by MIC on 2017-04-06.
 */
public class ChainElement extends ChainBase{

    final String word;
    private int count = 1;

    public ChainElement(String word) {
        this.word = word;
    }

    public int getCount(){
        return count;
    }

    public void incCount(){
        count++;
    }
}
