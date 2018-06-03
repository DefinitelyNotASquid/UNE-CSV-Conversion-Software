import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Liam Carney for UNE Cosc591.
 * Copyright 2018 Liam Carney
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy of this
 *   software and associated documentation files (the "Software"), to deal in the Software
 *   without restriction, including without limitation the rights to use, copy, modify,
 *   merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *   permit persons to whom the Software is furnished to do so, subject to the following
 *   conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all copies
 *   or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *   INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 *   PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *   HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *   CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 *   OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
public class Main extends Application {



    public static String location;
    private final Core core = new Core();


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Main starting method, creates the javafx thread and launches "core" the main
     * object of the program
     * @param primaryStage main stage
     * @throws Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage window = primaryStage;

        window.setTitle("Csv Conversion Software");
        window.setScene(core.getScene());
        ConfirmBox confirmBox = new ConfirmBox();
        window.setResizable(true);
        window.show();


        //Handle Application Exit
        window.setOnCloseRequest(e -> {
            e.consume();
            confirmBox.handleClose(window, "Exit", "Sure you want to exit?");
        });

    }

    /**
     * Gets the global location of the last file opened, used mostly for debugging purposes
     * @return
     */
    public static String getLocation() {
        return location;
    }

    /**
     * sets the global location of the last file opened, used mostly for debugging purposes
     * @param location
     */
    public static void setLocation(String location) {
        Main.location = location;
    }

}







