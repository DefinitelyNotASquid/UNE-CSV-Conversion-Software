import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;


public class Core {

    private Stage window;
    private Desktop desktop = Desktop.getDesktop();
    private static boolean multiFileDialog;
    private String openFile;


    private TreeView<String> fileRetrievedList = new TreeView<>();
    private TextArea fileViewRight = new TextArea();
    private Scene scene;
    public String location;
    static TextArea textArea = new TextArea();


    public Core() {

        super();

        textArea.setWrapText(true);
        final FileChooser fileChooser = new FileChooser();
        ToolBar toolbar = new ToolBar();
        ToolBar toolsbar = new ToolBar();
        ToolBar convertbar = new ToolBar();
        TabPane tabPane = new TabPane();
        TextField fileLocationBar = new TextField();
        fileLocationBar.setEditable(false);
        fileLocationBar.setPromptText("File Location");


        Tab singleConvert = new Tab();
        Tab dataBase = new Tab();

        singleConvert.setText("Import Csv");
        dataBase.setText("Database");


        tabPane.getTabs().addAll(singleConvert, dataBase);


        //Creating a GridPane container

        Button view_database = new Button("Import Database");

        toolsbar.getItems().addAll(view_database);


        GridPane grid3 = new GridPane();
        grid3.setPadding(new Insets(10, 10, 10, 10));
        grid3.setVgap(5);
        grid3.setHgap(5);


        BorderPane layout = new BorderPane();
        BorderPane layout2 = new BorderPane();
        BorderPane layout3 = new BorderPane();
        BorderPane layout4 = new BorderPane();
        BorderPane layout5 = new BorderPane();


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("noborder-scroll-pane");
        scrollPane.setContent(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);


        TextField Name = new TextField();
        TextField Speed = new TextField();
        TextField Trial = new TextField();
        Speed.setPromptText("Enter Km/h");
        Name.setPromptText("Enter Name");
        Trial.setPromptText("Enter Trial Number");



        singleConvert.setContent(scrollPane);

        layout.setCenter(layout2);
        layout2.setLeft(layout3);
        layout3.setLeft(tabPane);


        Button btnOpen = new Button("Open");
        Button btnConvert = new Button("Convert");


        ToolBar toolBar1 = new ToolBar();
        toolBar1.getItems().addAll(
                btnOpen,
                btnConvert,
                Name,
                Speed,
                Trial,
                fileLocationBar
        );

        layout3.setBottom(toolBar1);


        layout4.setTop(convertbar);
        layout5.setTop(toolsbar);




        MenuBar menuBar = new MenuBar();

        layout.setTop(menuBar);

        // File menu - new, save, exit
        Menu fileMenu = new Menu("File");


        MenuItem menuOpenFile = new MenuItem("Open File");



        /*
          Opens a CSV file, reads the file and sets the text area to said file
        */

        menuOpenFile.setOnAction(e ->{

            openFile = UtilMethods.displayOpen(window);
            File tempFile = new File(openFile);
            textArea.setText(UtilMethods.readFile(tempFile));
            fileLocationBar.setText(openFile);
            Main.setLocation(openFile);


        });

        /*
          Converts the open CSV file, sets the text area to the converted state
        */


        btnConvert.setOnAction((ActionEvent event) -> {

            {
                System.out.println(Main.getLocation());
                boolean test = true;

                if(fileLocationBar.getText().trim().isEmpty()){

                   test = false;

                }
                if( Name.getText().trim().isEmpty()){

                    test = false;

                }
                if( Trial.getText().trim().isEmpty()){

                    test = false;

                }
                if( Speed.getText().trim().isEmpty()){

                    test = false;

                }

                if (test == true){
                    try {
                    Convert.convert(fileLocationBar.getText(), Name.getText(), Integer.parseInt(Trial.getText()), Integer.parseInt(Speed.getText()));
                } catch (NumberFormatException  e) {
                    ErrorDialog.displaystring("Integer Expected Error", "The parser has detected that a numerical field contains an non-number character");


                } catch (IOException e) {
                    e.printStackTrace();
                }


                }
                else {

                    ErrorDialog.displaystring("Error Parsed Value", "One or more of the fields do not contain a value, or has an incorrect value");

                }
            }

        });

        /*
          Opens a CSV file, reads the file and sets the text area to said file
        */

        btnOpen.setOnAction( e -> {

            openFile = UtilMethods.displayOpen(window);
            File tempFile = new File(openFile);
            textArea.setText(UtilMethods.readFile(tempFile));
            fileLocationBar.setText(openFile);
            Main.setLocation(openFile);

        });

        MenuItem open_multiple_files = new MenuItem("Open Multiple Files");


        /*
          Opens a multi-file dialog to process multiple files,
          processes the file and sets the text area to said file
        */

        open_multiple_files.setOnAction(event -> multiFileDialog = MultiDialog.display());


        SplitPane Databasesplit = new SplitPane();
        dataBase.setContent(Databasesplit);

        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        fileMenu.getItems().addAll(menuOpenFile, new SeparatorMenuItem(), open_multiple_files,
                new SeparatorMenuItem(), exitMenuItem);

        Menu helpMenu = new Menu("Help");

        File choice = new File(System.getProperty("user.dir") + "/Database/");

        /*
          Creates the database folder at the current directory if it does not exist
        */

        if (!(choice.exists())) {

            new File(System.getProperty("user.dir") + "/Database/").mkdirs();
        }


        BorderPane Filedirectorylist = new BorderPane();
        BorderPane Databaseoptions = new BorderPane();
        GridPane GraphData = new GridPane();

        GraphData.setGridLinesVisible(true);


        fileRetrievedList.setRoot(UtilMethods.getNodesForDirectory(choice));
        Filedirectorylist.setCenter(fileRetrievedList);
        menuBar.getMenus().addAll(fileMenu, helpMenu);



        Databasesplit.getItems().add(Filedirectorylist);
        Databasesplit.getItems().add(Databaseoptions);
        Databasesplit.getItems().add(GraphData);
        Databaseoptions.setCenter(fileViewRight);

        Databasesplit.setDividerPositions(0.23f, 0.60f, 0.93);

        Button DeleteItem = new Button();
        Button UpdateItem = new Button();
        ToolBar DatabaseBar = new ToolBar();
        fileLocationBar.setMinWidth(250);

        DatabaseBar.getItems().addAll(DeleteItem, UpdateItem);


        /*
          Convert bar Gui construct
        */


        singleConvert.setOnSelectionChanged(e -> {

            toolBar1.setVisible(true);
            toolBar1.setManaged(true);
            fileRetrievedList.setRoot(UtilMethods.getNodesForDirectory(choice));

        });

        /*
          Removes the gui bar on database change.
        */

        dataBase.setOnSelectionChanged(e -> {

            toolBar1.setVisible(false);
            toolBar1.setManaged(false);

        });

        /*
          Retrieves the file structure and list of folders and appropriate files
        */


        fileRetrievedList.setOnMouseClicked((MouseEvent e) -> {


                    TreeItem<String> s = fileRetrievedList.getSelectionModel().getSelectedItem();

                    System.out.println(s);


                    File root = new File(System.getProperty("user.dir") + "/Database/");

                    Text display_name = new Text();
                    Text Variable_1 = new Text();
                    Text Variable_2 = new Text();
                    Text Variable_3 = new Text();
                    Text Variable_4 = new Text();
                    Text Variable_5 = new Text();
                    Text Variable_6 = new Text();

                    Text Speed_1 = new Text();
                    Text Speed_2 = new Text();
                    Text Speed_3 = new Text();
                    Text Speed_4 = new Text();
                    Text Speed_5 = new Text();

                    Speed_1.setText("Speed 11kmh   ");
                    Speed_2.setText("Speed 13kmh   ");
                    Speed_3.setText("Speed 15kmh   ");
                    Speed_4.setText("Speed 17kmh   ");
                    Speed_5.setText("Speed 19kmh   ");


                    Variable_1.setText("Average Speed");
                    Variable_2.setText("Stiffness");
                    Variable_3.setText("Viscosity");
                    Variable_4.setText("Average Amplitude");
                    Variable_5.setText("Energy Lost");
                    Variable_6.setText("Stride Frequency");


                    try {
                        if (s != null) {

                            if (!((s.getValue().contains("Database"))) && !(s.getValue().contains(".csv"))) {

                                GraphData.getChildren().clear();
                                GraphData.setGridLinesVisible(true);
                                GraphData.add(display_name, 3, 0);
                                GraphData.add(Variable_1, 0, 2);
                                GraphData.add(Variable_2, 0, 3);
                                GraphData.add(Variable_3, 0, 4);
                                GraphData.add(Variable_4, 0, 5);
                                GraphData.add(Variable_5, 0, 6);
                                GraphData.add(Speed_1, 1, 1, 2, 1);
                                GraphData.add(Speed_2, 3, 1, 2, 1);
                                GraphData.add(Speed_3, 5, 1, 2, 1);
                                GraphData.add(Speed_4, 7, 1, 2, 1);
                                GraphData.add(Speed_5, 9, 1, 2, 1);

                            }

                            display_name.setText(s.getValue());
                            boolean recursive = true;

                            Collection files = FileUtils.listFiles(root, null, recursive);

                            for (Iterator iterator = files.iterator(); iterator.hasNext(); ) {
                                File file = (File) iterator.next();
                                if (file.getName().equals(s.getValue())) {

                                    fileViewRight.setText(UtilMethods.readFile(file));
                                }
                            }


                        }

                      //  menuBar.prefWidthProperty().bind(Main.primaryStage.widthProperty());
                    } catch (Exception g) {
                        g.printStackTrace();
                    }
                }
        );



        scene = new Scene(layout, 900, 600);
        tabPane.prefWidthProperty().bind(scene.widthProperty());


    }
    public Scene getScene() {
        return scene;
    }

}
