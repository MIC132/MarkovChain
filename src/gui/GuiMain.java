package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import parsing.ChainBase;
import parsing.Parser;


/**
 * Created by MIC on 2017-05-06.
 */
public class GuiMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ChainBase base = Parser.parseFile("lovecraft.txt",3);
        MarkovTextField field = new MarkovTextField(base);

        StackPane root = new StackPane();
        root.getChildren().add(field);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
