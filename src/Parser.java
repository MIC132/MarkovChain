import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MIC on 2017-04-06.
 */
public class Parser {
    private Parser(){}

    public static ChainBase parseFile(String filename, int depth){
        ChainBase output = new ChainBase(depth);

        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            StringBuilder buffer = new StringBuilder();
            for(String line; (line = br.readLine()) != null; ){
                buffer.append(" ");
                buffer.append(line.toLowerCase());
                int index = buffer.toString().indexOf(".");
                if(index != -1){
                    List<String> words = new ArrayList<>(Arrays.asList(buffer.substring(0, index).split("\\W+")));
                    if(!words.isEmpty()){
                        //words[0] is space for some reason, so we remove it
                        words.remove(0);
                        for(int i = 0; i < words.size(); i++){
                            int start = Math.max(0, i - (depth-1));
                            output.addWord(words.subList(start, i+1));
                        }
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
