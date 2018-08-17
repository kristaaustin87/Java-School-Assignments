/**SongDatabase
@author Krista Delap
May 6, 2018
provides gui for managing database of music*/

//imports for gui & input/output
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import java.text.DecimalFormat;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import javafx.collections.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import javafx.scene.layout.Region;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SongDatabase extends Application
{

    //global vars
    public static File file;
    ObservableList<Songs> songsList = FXCollections.observableArrayList();
    
    /**@param args from command to get database file
    checks for file and launches stage*/
    public static void main(String args[]) throws IOException
    {
            //sets global file variable
			file = new File(args[0]);
			
			launch(args);
			
    }
    
    /**creates main view*/
    public void start(Stage myStage) throws IOException
    {   
        //var to determine if continuing
        boolean cont = false;
        
        //show alert if file does not exist
        if(!file.exists())
        {
            cont = alert();
        }
        else
        {
            //if file exists, load GUI and load songlist
            cont = true;
            loadList();
        }
        
        //show stage if continuing
        if(cont == true)
        {
			myStage.setTitle("Song Database");
		
			GridPane root = new GridPane();
		
			Scene scene = new Scene(root, 500, 250);
		
			//create combo box
			Label title = new Label("Song Title");
			
			ComboBox songBox = new ComboBox<Songs>(songsList);
			songBox.setPrefWidth(200);
			songBox.getSelectionModel().selectFirst();
			
			//create fields
			Label code = new Label("Item Code");
			TextField codeBox = new TextField();
			codeBox.setPrefWidth(50);
			codeBox.setEditable(false); 
			
			Label description = new Label("Description");
			TextField descripBox = new TextField();
			descripBox.setPrefWidth(300);
			descripBox.setEditable(false); 
			
			
			Label artist = new Label("Artist");
			TextField artistBox = new TextField();
			artistBox.setPrefWidth(300);
			artistBox.setEditable(false);
			
			Label album = new Label("Album");
			TextField albumBox = new TextField();
			albumBox.setPrefWidth(300);
			albumBox.setEditable(false);
			
			Label price = new Label("Price");
			TextField priceBox = new TextField();
			priceBox.setPrefWidth(100);
			priceBox.setEditable(false);
			
			Label mode = new Label("Mode: ");
			Label modeType = new Label("View");
			
			//create buttons
			Button add = new Button("New");
			Button edit = new Button("Edit");
			Button delete = new Button("Delete");
			Button accept = new Button("Accept");
			accept.setDisable(true);
			Button cancel = new Button("Cancel");
			cancel.setDisable(true);
			Button exit = new Button("Exit");
			
			//only add enable for new DB
			if(songsList.size() == 0)
			{
			    edit.setDisable(true);
			    delete.setDisable(true);
			}
			//otherwise set up fields by selected song
			else
			{
			    populate(songBox.getSelectionModel().getSelectedIndex(), codeBox,
			    descripBox, artistBox, albumBox, priceBox, songBox);
			}
			
			
			//set button actions
			songBox.setOnAction(new EventHandler<ActionEvent>()
			{
			    public void handle(ActionEvent ae)
			    {
			        //set up fields by selected song
			        populate(songBox.getSelectionModel().getSelectedIndex(), codeBox,
			        descripBox, artistBox, albumBox, priceBox, songBox);
			    }
			});
			
			add.setOnAction(new EventHandler<ActionEvent>()
			{
			    /**sets action for New button*/
			    public void handle(ActionEvent ae)
			    {
			        //enable only accept/cancel
			        modeType.setText("Add");
			        add.setDisable(true);
			        edit.setDisable(true);
			        delete.setDisable(true);
			        accept.setDisable(false);
			        cancel.setDisable(false);
			         
			         //set fields to editable
			        songBox.setEditable(true); 
			        codeBox.setEditable(true); 
			        descripBox.setEditable(true); 
			        artistBox.setEditable(true);
			        albumBox.setEditable(true);
			        priceBox.setEditable(true);
			        //clear fields
			        songBox.getSelectionModel().clearSelection();
			        codeBox.setText("");
			        descripBox.setText("");
			        artistBox.setText("");
			        albumBox.setText("");
			        priceBox.setText("");
			    }
			});
			
			
			edit.setOnAction(new EventHandler<ActionEvent>()
			{
			    /**sets action for Edit button*/
			    public void handle(ActionEvent ae)
			    {
			        //only accept/cancel enabled
			        modeType.setText("Edit");
			        add.setDisable(true);
			        edit.setDisable(true);
			        delete.setDisable(true);
			        accept.setDisable(false);
			        cancel.setDisable(false);
			         
			        //set all fields except song & code editble
			        descripBox.setEditable(true); 
			        artistBox.setEditable(true);
			        albumBox.setEditable(true);
			        priceBox.setEditable(true);
			        int getCurrent = songBox.getSelectionModel().getSelectedIndex();
			        songBox.getSelectionModel().clearSelection();
			        songBox.getSelectionModel().select(getCurrent);

			    }
			});
			
			delete.setOnAction(new EventHandler<ActionEvent>()
			{
			    /**sets action for Delete button*/
			    public void handle(ActionEvent ae)
			    {
			        //set only accept/cancel to enabled
			        modeType.setText("Delete");
			        edit.setDisable(true);
			        add.setDisable(true);
			        delete.setDisable(true);
			        accept.setDisable(false);
			        cancel.setDisable(false);
			        songBox.setEditable(false); 
			        codeBox.setEditable(false); 
			        descripBox.setEditable(false); 
			        artistBox.setEditable(false);
			        albumBox.setEditable(false);
			        priceBox.setEditable(false);
			    }
			});
			
			accept.setOnAction(new EventHandler<ActionEvent>()
			{
			    /**sets action for Accept button*/
			    public void handle(ActionEvent ae)
			    {
			        
			        //process data and check for errors
			        boolean error = doAccept(modeType, songBox, codeBox, descripBox,
			        artistBox, albumBox, priceBox);
			        
			        //if no errors, reset view
			        if(!error)
			        {
						modeType.setText("View");
						add.setDisable(false);
						edit.setDisable(false);
						delete.setDisable(false);
						accept.setDisable(true);
						cancel.setDisable(true);
						songBox.setEditable(false); 
						codeBox.setEditable(false); 
						descripBox.setEditable(false); 
						artistBox.setEditable(false);
						albumBox.setEditable(false);
						priceBox.setEditable(false);
					
						if(songsList.size() == 0)
						{
							edit.setDisable(true);
							delete.setDisable(true);
						}
					}
			        
			        
			    }
			});
			
			cancel.setOnAction(new EventHandler<ActionEvent>()
			{
			    /**sets action for Cancel button*/
			    public void handle(ActionEvent ae)
			    {
			        //reset editable fields
			        songBox.setEditable(false); 
			        codeBox.setEditable(false); 
			        descripBox.setEditable(false); 
			        artistBox.setEditable(false);
			        albumBox.setEditable(false);
			        priceBox.setEditable(false);
			        
			        //clear changes
			        clearFields(modeType, songBox, codeBox, descripBox,
			        artistBox, albumBox, priceBox);
			        
			        //reset GUI
			        modeType.setText("View");
			        add.setDisable(false);
			        edit.setDisable(false);
			        delete.setDisable(false);
			        accept.setDisable(true);
			        cancel.setDisable(true);
			        
			        if(songsList.size() == 0)
			        {
						edit.setDisable(true);
						delete.setDisable(true);
					}  
			    }
			});
			
			//set exit button action
			exit.setOnAction(new EventHandler<ActionEvent>()
			{
			    /**sets action for Exit button*/
			    public void handle(ActionEvent ae)
			    {
			        Platform.exit();
			    }
			});
				
			//add nodes
			root.add(title, 0, 0, 1, 1);
			root.add(songBox, 2, 0, 4, 1);
			root.add(code, 0, 2, 1, 1);
			root.add(codeBox, 2, 2, 1, 1);
			root.add(description, 0, 3, 1, 1);
			root.add(descripBox, 2, 3, 5, 1);
			root.add(artist, 0, 4, 1, 1);
			root.add(artistBox, 2, 4, 5, 1);
			root.add(album, 0, 5, 1, 1);
			root.add(albumBox, 2, 5, 5, 1);
			root.add(price, 0, 6, 1, 1);
			root.add(priceBox, 2, 6, 2, 1);
			root.add(add, 2, 7, 1, 1);
			root.add(edit, 3, 7, 1, 1);
			root.add(delete, 4, 7, 1, 1);
			root.add(accept, 5, 7, 1, 1);
			root.add(cancel, 6, 7, 1, 1);
			root.add(exit, 4, 8, 1, 1);
			root.add(mode, 0, 9, 1, 1);
			root.add(modeType, 1, 9, 1, 1);
		
		    //show
			myStage.setScene(scene);    
			myStage.show();
        }
    }
    
    /**Overrides stop method to output songs to file*/
    public void stop() throws IOException
    {
        FileWriter output = null;
        
        try
        {
				//to output to the file
				output = new FileWriter(file);
				for (Songs s : songsList)
				{
				    output.write(s.title + "," + s.code + "," + s.description + "," + 
				    s.artist + "," + s.album + "," + s.price);
				    output.write("\n");
				}	
        }
        
        finally
        {
            if(output != null)
			{
  			    output.close();
  			}
        }
    }
    
    /**Alerts to missing database file
    @return boolean to make a new file or exit*/
    public static boolean alert()
    {
        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, 
        ButtonType.NO);
        alert.setTitle("Song Database Not Found");
        alert.setHeaderText("The database you enterend was not found");
        alert.setContentText("Would you like to create a new database?");
        alert.showAndWait();
        
        //get results of user input
        if (alert.getResult() == ButtonType.YES) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**Alerts to blank fields upon accepting*/
    public static void alert2()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Blank Field Detected");
        alert.setContentText("Text must be entered in each field");
        alert.showAndWait();
    }
    
    /**Alerts to invalid price field upon accepting*/
    public static void alert3()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Price");
        alert.setContentText("Price must be in valid decimal format");
        alert.showAndWait();
    }
    
    /**Processes data once accept has been clicked if no errors found
    @param modeType The current mode of processing
    @param songBox The GUI comboBox
    @param codeBox The field which contains the code
    @param descripBox The description field
    @param artistBox The artist field
    @param albumBox The album field
    @param priceBox The price field
    @return boolean to determine if any errors present*/
    public boolean doAccept(Label modeType, ComboBox songBox, 
    TextField codeBox, TextField descripBox, TextField artistBox, 
    TextField albumBox, TextField priceBox)
    {
        //check for blanks
        if(albumBox.getText().equals("") || songBox.getValue().toString().equals("")
        || codeBox.getText().equals("") || descripBox.getText().equals("")
        || artistBox.getText().equals("") || priceBox.getText().equals(""))
        {
            alert2();
            return true;
            
        }
        
        //check for letters
        else if(priceBox.getText().matches("[a-zA-Z]+"))
        {
            alert3();
            return true;
        }
        
        else
        {
			if(modeType.getText().equals("Add"))
			{
			    //create new song and set values
				Songs song = new Songs();
				song.setSong(songBox.getValue().toString(), albumBox.getText(), 
				priceBox.getText(), codeBox.getText(), 
				artistBox.getText(), descripBox.getText());
				//add to song list
				songsList.add(song);
				songBox.getSelectionModel().select(songsList.indexOf(song));
				populate(songsList.indexOf(song), codeBox,descripBox, artistBox, 
				albumBox, priceBox, songBox);
			}
			else if(modeType.getText().equals("Edit"))
			{
			    //get currently selected song and update values
				int getCurrentSong = songBox.getSelectionModel().getSelectedIndex();
				Songs song = songsList.get(getCurrentSong);
				song.setSong(songBox.getValue().toString(), albumBox.getText(), 
				priceBox.getText(), codeBox.getText(), 
				artistBox.getText(), descripBox.getText());
				songBox.getSelectionModel().select(songsList.indexOf(song));
				populate(songsList.indexOf(song), codeBox,descripBox, artistBox, 
				albumBox, priceBox, songBox);
			}
			else if(modeType.getText().equals("Delete"))
			{
			    //get currently selected song and remove from list
				int getCurrentSong = songBox.getSelectionModel().getSelectedIndex();
				songsList.remove(getCurrentSong);
				songBox.getSelectionModel().selectFirst();
				//reset GUI to initial item
				populate(0, codeBox,descripBox, artistBox, 
				albumBox, priceBox, songBox);
			}
			return false;
		}
    }
    
    /**Resets GUI after cancel
    @param modeType The current mode of processing
    @param songBox The GUI comboBox
    @param codeBox The field which contains the code
    @param descripBox The description field
    @param artistBox The artist field
    @param albumBox The album field
    @param priceBox The price field*/
    public void clearFields(Label modeType, ComboBox songBox, 
    TextField codeBox, TextField descripBox, TextField artistBox, 
    TextField albumBox, TextField priceBox)
    {
        //if no new added, reset to initial value
        if(modeType.getText().equals("Add"))
        {
            songBox.getSelectionModel().selectFirst();
            populate(songBox.getSelectionModel().getSelectedIndex(), codeBox,
			descripBox, artistBox, albumBox, priceBox, songBox);
        }
        //esle display selected in previous state
        else if(modeType.getText().equals("Edit") || modeType.getText().equals("Delete"))
        {
            populate(songBox.getSelectionModel().getSelectedIndex(), codeBox,
			descripBox, artistBox, albumBox, priceBox, songBox);
        }
    }
    
    /**Populates the list of songs*/
    public void loadList() throws IOException
    {
        BufferedReader input = null;
        
        try
        {
				//to read the file if it already exists
				input = new BufferedReader(new FileReader(file));
				String line = input.readLine();
				
				while(line != null)
				{
				    //read by line and split by delimiter
				    String stringArray[];
				    stringArray = line.split(",");
				    
				    //create new songs obj
				    Songs song = new Songs();
				    song.setSong(stringArray[0], stringArray[4], 
				    stringArray[5], stringArray[1], 
				    stringArray[3], stringArray[2]);
				    //add to list
				    songsList.add(song);
				    
				    line = input.readLine();
				}

		}
			
			
		finally
		{
		    if(input != null)
		    {
			    input.close();
			}
		}
    }
    
    /**Populates GUI fields
    @param current The current index of the songBox item
    @param songBox The GUI comboBox
    @param codeBox The field which contains the code
    @param descripBox The description field
    @param artistBox The artist field
    @param albumBox The album field
    @param priceBox The price field*/
    public void populate(int current, TextField codeBox, TextField descripBox, 
    TextField artistBox, TextField albumBox, TextField priceBox, ComboBox songBox)
    {
        Songs currentSong = songsList.get(current);
        songBox.getSelectionModel().select(current);
        codeBox.setText(currentSong.code);
        descripBox.setText(currentSong.description);
        artistBox.setText(currentSong.artist);
        albumBox.setText(currentSong.album);
        priceBox.setText(currentSong.price);
    }
}