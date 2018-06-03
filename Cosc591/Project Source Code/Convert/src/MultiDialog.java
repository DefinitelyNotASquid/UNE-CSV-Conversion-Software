import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.GridPane.getColumnIndex;
import static javafx.scene.layout.GridPane.getRowIndex;

/**
 * Created by Liam Carney.
 */

public class MultiDialog {

    private static boolean answer;
    private static Stage multiFileDialogStage = new Stage();
    private static int buttonNumberCounter = 0;
    private static GridPane gridPaneMultFile = new GridPane();
    public static String location;

    public static boolean display()

    {

        /*
            Checks the modality of the multi-file dialog, to make sure that modality is not set twice
         */
        if (!(multiFileDialogStage.getModality().equals(Modality.APPLICATION_MODAL))) {

            multiFileDialogStage.initModality(Modality.APPLICATION_MODAL);

        }

        /*
            Clears the previous children from the gridPaneMultFile as to not persist on next multi file import
         */

        gridPaneMultFile.getChildren().clear();

        multiFileDialogStage.setTitle("Multiple File Import");
        createNewButton();


        Button btnConvert = new Button("Convert");


        Button Addnew = new Button("+");
        Button remove = new Button("-");


        /*
            Convert lambda, if the fields aren't populate we have a check in place to throw an error
         */

        btnConvert.setOnAction(e ->
        {
            int tempnum = buttonNumberCounter;
            boolean error_check = false;

            /*
                While tempnum (the number of buttons created) is greater than zero we check if all fields are populated
            */

            while (tempnum > 0) {
                TextField tf1 = (TextField) getNodeByRowColumnIndex(buttonNumberCounter, 1, gridPaneMultFile);
                TextField tf2 = (TextField) getNodeByRowColumnIndex(buttonNumberCounter, 2, gridPaneMultFile);
                TextField tf3 = (TextField) getNodeByRowColumnIndex(buttonNumberCounter, 3, gridPaneMultFile);
                TextField tf4 = (TextField) getNodeByRowColumnIndex(buttonNumberCounter, 4, gridPaneMultFile);

                if (tf1.getText() == null || tf1.getText().trim().isEmpty()) {

                    error_check = false;
                    break;
                }
                if (tf2.getText() == null || tf1.getText().trim().isEmpty()) {

                    error_check = false;
                    break;
                }
                if (tf3.getText() == null || tf1.getText().trim().isEmpty()) {

                    error_check = false;
                    break;
                }
                if (tf4.getText() == null || tf1.getText().trim().isEmpty()) {

                    error_check = false;
                    break;

                }
                if (tempnum == 1) {

                    error_check = true;
                    break;
                }

                tempnum--;
            }

            /*
                If error check is valid, then we proceed to converting all of the files
            */

            if (error_check) {
                while (buttonNumberCounter > 0) {
                    TextField tf1 = (TextField) getNodeByRowColumnIndex(buttonNumberCounter, 1, gridPaneMultFile);
                    TextField tf2 = (TextField) getNodeByRowColumnIndex(buttonNumberCounter, 2, gridPaneMultFile);
                    TextField tf3 = (TextField) getNodeByRowColumnIndex(buttonNumberCounter, 3, gridPaneMultFile);
                    TextField tf4 = (TextField) getNodeByRowColumnIndex(buttonNumberCounter, 4, gridPaneMultFile);

                    int kmh = Integer.parseInt(tf1.getText());
                    String name = tf2.getText();
                    String location = tf4.getText();
                    int tnum = Integer.parseInt(tf3.getText());


                    try {
                        Convert.convert(location, name, tnum, kmh);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    buttonNumberCounter--;
                }

                answer = true;
                multiFileDialogStage.close();
            } else {
            /*
                Catches an integer parse error if one occurs
             */
               ErrorDialog.displaystring("Error Parsed Value", "One or more of the fields do not contain a value, or has an incorrect value");


            }
        });

        /*
            Creates an additional import
         */
        Addnew.setOnAction(e ->
                createNewButton()

        );

        /*
            Removes an additional import
         */
        remove.setOnAction(e ->
                {
                    deleteButton();
                }

        );

        /*
           scroll pane for the dialog
         */

        ScrollPane scrollPane = new ScrollPane();
        /*
           Border pane for the dialog
         */
        BorderPane layout = new BorderPane();

        /*
           Setting the content to the stage
         */
        layout.setCenter(gridPaneMultFile);
        /*
           Tool bar for the +, - and convert button
         */
        ToolBar tb = new ToolBar();
        tb.getItems().addAll(Addnew, remove, btnConvert);

        layout.setBottom(tb);
        scrollPane.setContent(layout);

        /*
           Scroll pane policy
         */
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        Scene scene1 = new Scene(scrollPane, 860, 350);


        multiFileDialogStage.setScene(scene1);

        multiFileDialogStage.showAndWait();

        return answer;
    }



    /**
     *  Builder method for creating new buttons dynamically for the Multi-File dialog
     */

    private static void createNewButton() {
        buttonNumberCounter++;
        Button open_file_location = new Button("Open File Location");

        open_file_location.setOnAction(e -> {

            location = UtilMethods.displayOpen(multiFileDialogStage);

            Node source = (Node) e.getSource();
            Integer rowIndex = GridPane.getRowIndex(source);

            SetNodeByRowColumnIndex(rowIndex, gridPaneMultFile, location);


        });


        TextField f1text = new TextField("");
        TextField f2text = new TextField("");

        TextField f3text = new TextField("");
        TextField f4text = new TextField("");

        f1text.setPromptText("Enter Km/h ");
        f2text.setPromptText("Enter Name ");
        f3text.setPromptText("Trial Number ");
        f4text.setPromptText("File Location ");

        f4text.setMinWidth(300);

        gridPaneMultFile.add(open_file_location, 0, buttonNumberCounter, 1, 1);
        gridPaneMultFile.add(f1text, 1, buttonNumberCounter, 1, 1);
        gridPaneMultFile.add(f2text, 2, buttonNumberCounter, 1, 1);
        gridPaneMultFile.add(f3text, 3, buttonNumberCounter, 1, 1);
        gridPaneMultFile.add(f4text, 4, buttonNumberCounter, 1, 1);

    }

    /**
     *  Method for deleting buttons dynamically for the Multi-File dialog
     */

    private static void deleteButton() {

        if (buttonNumberCounter >= 1) {

            gridPaneMultFile.getChildren().removeIf(node -> getRowIndex(node) == buttonNumberCounter);
            buttonNumberCounter--;
        }


    }

    /**
     * Method for retrieving a specific node at a row and column
     * @param row   Row of a grid pane element
     * @param column    Column of a grid pane element
     * @param gridPane  GridPane to iterate through
     * @return  Node from the gird
     */

    private static Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (getRowIndex(node) == row && getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    /**
     * Method for setting a specific node at row and column
     * @param row   Row of a grid pane element
     * @param gridPane  GridPane to iterate through
     * @param local   String to set the node at the location
     */

    private static void SetNodeByRowColumnIndex(final int row, GridPane gridPane, String local) {
        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if (node instanceof TextField
                    && getColumnIndex(node) == 4
                    && getRowIndex(node) == row) {
                ((TextField) node).setText(local);
            }
        }
    }


}

