package codsworth;

import java.io.IOException;

import codsworth.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Codsworth codsworth = new Codsworth("codsworth.txt");
    private Image codsworthImage = new Image(this.getClass().getResourceAsStream("/images/Codsworth.png"));

    @Override
    public void start(Stage stage) {
        assert codsworth != null : "Codsworth could not be created";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Options
            stage.setMinHeight(340);
            stage.setMinWidth(417);
            stage.setTitle("Codsworth");
            stage.getIcons().add(codsworthImage);

            fxmlLoader.<MainWindow>getController().setCodsworth(codsworth);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
