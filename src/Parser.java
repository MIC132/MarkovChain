import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by MIC on 2017-04-06.
 */
public class Parser {
    private Parser(){}

    public static ChainBase parseFile(String filename){
        ChainBase output = new ChainBase();

        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            StringBuilder buffer = new StringBuilder();
            for(String line; (line = br.readLine()) != null; ){
                buffer.append(" ");
                buffer.append(line);
                int index = buffer.toString().indexOf(".");
                if(index != -1){
                    String[] words = buffer.substring(0, index).split("\\W+");
                    //output.addWord(words[1]);
                    for(int i = 2; i<words.length; i++){
                        output.addWord(words[i-1]);
                        output.words.get(words[i-1]).addWord(words[i]);
                    }
                    buffer.delete(0, index+1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}
