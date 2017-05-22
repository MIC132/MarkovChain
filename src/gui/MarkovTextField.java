package gui;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import parsing.ChainBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by MIC on 2017-05-08.
 */
public class MarkovTextField extends TextField {
    private final List<String> entries = new ArrayList<>();
    private ContextMenu entriesPopup = new ContextMenu();
    private int amount = 10; //TODO: Allow changing.

    public MarkovTextField(ChainBase base){
        super();
        textProperty().addListener((observable, oldValue, newValue) -> {
            List<String> temp = new ArrayList<>(Arrays.asList(getText().split("[^\\w-']+"))); //get text from textfield and split into words
            List<String> words = new ArrayList<>(); //this next part is needed to put everything into lowercase, since the tree is in lowercase
            for(String s : temp){
                words.add(s.toLowerCase());
            }
            if(words.isEmpty()){//If the filed is empty (or we just started new sentence) we hide suggestions
                entriesPopup.hide();
            }else{
                List<String> follow = base.getFrequentFollowing(words, amount); //this gets frequent following with maximum depth of the base, since the function takes care of depths > depth of base
                while(words.size() > 1 && follow.size() < amount){// then, if we don't get enough suggestions we decrease depth, to get "less accurate" suggestions
                    words.remove(0);
                    for(String w : base.getFrequentFollowing(words, amount - follow.size())){ //avoid duplicates
                        if(!follow.contains(w)){
                            follow.add(w);
                        }
                    }
                }

                //This part generates the suggestion entries. DO NOT TOUCH. Just add anyhthing that's needed to "follow"
                if(follow != null && !follow.isEmpty()){
                    List<CustomMenuItem> menuItems = new ArrayList<>();
                    for(String s : follow){
                        Label entryLabel = new Label(s);
                        CustomMenuItem item = new CustomMenuItem(entryLabel, true);
                        item.setOnAction((event -> {
                            setText(getText().concat(" "+s));
                            positionCaret(getText().length());
                        }));
                        menuItems.add(item);
                    }
                    entriesPopup.getItems().clear();
                    entriesPopup.getItems().addAll(menuItems);
                    if (!entriesPopup.isShowing()) {
                        entriesPopup.show(MarkovTextField.this, Side.BOTTOM, 0, 0);
                    }
                }else{
                    entriesPopup.hide();
                }
                //End of generator

            }
        });
    }
}
