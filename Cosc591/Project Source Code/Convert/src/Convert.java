import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * Created by Liam Carney.
 */

public class Convert {

    private static StringBuilder CSV_converted_string = new StringBuilder();
    private static double seconds;


    /**
     * Main Covert method for the files
     * @param location  The location path to the file, this is an absolute path
     * @param name  Gathered from the gui Name field
     * @param trial Gathered from the gui Trial field
     * @param speed Gathered from the gui Speed field
     * @return Returns the string of the final converted file
     * @throws IOException
     */

    static String convert(String location, String name, int trial, int speed) throws IOException {
        try (
                /*
                   reader for extracting the information out of the to be converted files
                 */
                Reader reader = Files.newBufferedReader(Paths.get(location));


                /*
                   Appache csv parse to parse the information across
                 */
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withHeader("File", "Frame", "X", "Y", "Z")
                        .withIgnoreHeaderCase()
                        .withTrim())
        ) {
                /*
                   For statement to iterate through the import data
                 */
            for (CSVRecord csvRecord : csvParser) {

                String Frame = csvRecord.get("Frame");
                String X = csvRecord.get("X");
                String Y = csvRecord.get("Y");
                String Z = csvRecord.get("Z");

                /*
                   Parse the frame import
                 */
                try {
                    seconds = Double.parseDouble(Frame);
                } catch (NumberFormatException ex) {
                }

                /*
                   Creates the head of the csv document if its the first line
                 */
                if (X.equals("X")) {
                    CSV_converted_string.setLength(0);
                    CSV_converted_string.append("label, speed, time, X, Y, Z \n");
                } else {
                    /*
                        Formatting the conversion string
                    */
                    CSV_converted_string.append("\"" + name + "\"" + "," + "\"" + speed + " km/hr" + "\"" + "," + (seconds / 100) + "," + X + "," + Y + "," + Z + "\n");
                }


            }
        }

        /*
            Set the main text area to be the converted string
        */
        Core.textArea.setText(CSV_converted_string.toString());
        String test = CSV_converted_string.toString();

        /*
            Get the relative directory, make a new directory with the participants name
         */
        new File(System.getProperty("user.dir") + "/Database/" + name + "/").mkdirs();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(

                /*
                   Output the converted csv file to the directory
                */
                new FileOutputStream(System.getProperty("user.dir") + "/Database/" + name + "/" + name + "_" + trial + ".csv"), "utf-8"))) {

            writer.write(test);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return test;
    }


}
