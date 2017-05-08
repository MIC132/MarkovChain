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
            List<String> temp = new ArrayList<>(Arrays.asList(getText().split("[^\\w-']+")));
            List<String> words = new ArrayList<>();
            for(String s : temp){
                words.add(s.toLowerCase());
            }
            if(words.isEmpty()){
                entriesPopup.hide();
            }else{
                List<String> follow = base.getFrequentFollowing(words, amount);
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

            }
        });
    }
}
