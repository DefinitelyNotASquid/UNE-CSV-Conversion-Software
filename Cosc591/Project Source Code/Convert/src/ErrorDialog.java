import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorDialog {


    private boolean answer;

    /*
                   Basic error dialog to catch exceptions, takes a title and string, captures the modality of the
                   program and requires the user to sanction a response to continue. this is to alert the user to
                   any possible issues when converting the program.
     */


    public static void displaystring(String title, String message) {


        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(230);


        //Set message on the label
        Label label = new Label();
        label.setText(message);

        //Set the buttons up
        Button Confirm = new Button("OK");


        Confirm.setOnAction(e -> {
            window.close();
        });


        BorderPane layout = new BorderPane();
        layout.setTop(label);
        layout.setLeft(Confirm);


        layout.setPadding(new Insets(10, 30, 10, 30));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }




}

