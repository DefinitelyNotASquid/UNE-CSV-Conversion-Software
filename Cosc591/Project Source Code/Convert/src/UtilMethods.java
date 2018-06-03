import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Liam Carney.
 */
public class UtilMethods {

    public static String displayOpen(final Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Import Data (*.Csv | *.txt)", "*.csv" , "*.text" , "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        String target = file.getAbsolutePath();
        return target;
    }
    /** Read File Method
     * @param file Takes a file to read
     * @return returns the contents of a file as a string
     */

    public static String readFile(File file) {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text + "\n");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }

    /**
     * Method that looks gets a recursive listing of folders and files and returns them as a tree
     * @param directory
     * @return Tree
     */

    public static TreeItem<String> getNodesForDirectory(File directory) {
        TreeItem<String> root = new TreeItem<>(directory.getName());


        for (File f : directory.listFiles()) {
            System.out.println("Loading " + f.getName());
            if (f.isDirectory()) { //Then we call the function recursively

                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<>(f.getName()));
            }
        }
        return root;
    }
}
