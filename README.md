# UNE-CSV-Conversion-Software

This software is designed to quickly convert the raw format of csv files to the appropriate file structure needed for the current research occuring at UNE. Ths software is licenced under MIT b, and is thus subject to free use.



# File Formats
The software will accept .csv, .txt, .tsv formats for the importation process, however it will only export .csv to the database for final storage.

# File Conversion
To convert a file to the correct .csv format and file extension, first navigate to either the “Open” button located at the bottom left of the program or “Open File” located under the “File” menu tab in left of the program. Once clicked a “File Dialog” will be displayed to the user, simply navigate to the file that you wish to import and click on the file you wish to import and press the “Open” button located at the bottom right of the “File Dialog”. 

You will notice that the “File Field” will now be populated with the directory path from your selected file. Now you must populate the “Name” ,  “Speed” and “Trial”  fields located at the bottom of the program. Note that both the “Speed” and “Trial” fields are integer sensitive, so only ensure that you populate them with characters [0-9].

Once all fields have been correctly populated simply click the “convert” button located at the bottom of the program and the file should now be converted and stored in the database. To view the database click on the database tab.




# Multi-File Conversion
To convert multiple files to the correct .csv format and file extension, first navigate to “Open Multiple Files” located under the “File” menu tab in left of the program. Once clicked a “Multi-File Dialog” will be displayed to the user. By default there is only one file import structure generated, by pressing the “+” button located at the bottom of the structure, additional fields will appear. Press the “+” button until the desired number of fields are created, note that if you have too many fields, pressing the “-” button will delete a field. 

Clicking on the “Open File” button will once again bring you to a file dialog. Navigating through this dialog and selecting the desired file will populate the file field. Do this for all files desired to be converted, and once completed, populate the “Name”, “Speed” and “Trial” fields with the appropriate data. Once again note, that all of the “Speed” and “Trial” fields are Integer sensitive, so only ensure that you populate them with characters [0-9]. If any fields are unpopulated or if the parser thinks they contain illegal characters, an error message will appear. Make sure that any unused multi-imports are removed using the “-” button otherwise the parser will consider them to be “empty” and will not convert the files.

Once all the files have been selected and all of the fields correctly populated, press the convert button, if done correctly the mult-file window will disappear and you will be presented with the first file displayed on the main tab. If you would like to view the database, clicking on the database tab will take you there.






# Database
To view the database, simply click on the “database” tab located in the middle left of the screen. This will take you to the database view, where you can see the screen divided up into three sections. If you see nothing in any of the sections, this means that no data has been imported into the database yet. If data has been imported you will notice on the left hand side a single list view of the database, clicking on the “Database” top level of the tree will expand it. 

The database consists of “names” each name corresponds to a folder created in the database for that participant. From the top down, it should read 
	
	> Database
		> Name of the participant
			> Trial x
			> Trial y
			> Trial z

Clicking on a name, will display a temporary graph on the right most section, this will be implemented with data about the participant at a later date. Clicking on any file with a .csv extension will show the data in the middle most section of the program. This data is not currently editable, but will be updatable at a later date.
