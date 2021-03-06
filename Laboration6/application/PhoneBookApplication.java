package application;
import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.Optional;

import javax.swing.JFileChooser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import phonebook.MapPhoneBook;
import phonebook.PhoneBook;

public class PhoneBookApplication extends Application{
	private PhoneBook phoneBook;
	private NameListView nameListView;

	/**
	 * The entry point for the Java program.
	 * @param args
	 */
	public static void main(String[] args) {	
		// launch() do the following:
		// - creates an instance of the Main class
		// - calls Main.init()
		// - create and start the javaFX application thread
		// - waits for the javaFX application to finish (close all windows)
		// the javaFX application thread do:
		// - calls Main.start(Stage s)
		// - runs the event handling loop
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if (!load()){
			phoneBook = new phonebook.myPhoneBook();
		}
		// set default locale english 
		Locale.setDefault(Locale.ENGLISH);
		
		nameListView = new NameListView(phoneBook);
		BorderPane root = new BorderPane();
		root.setTop(new PhoneBookMenu(phoneBook, nameListView));
		root.setCenter(nameListView);		
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("PhoneBook");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private void save(){
		Optional<String> result = Dialogs.oneInputDialog("Save File", "Enter filename", "Name" );
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(result.get()));
			out.writeObject(phoneBook);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private boolean load(){
		Component comp = null;
		JFileChooser choose = new JFileChooser("/home/nochem/workspaces/edaa30-workspace/lab6");
		@SuppressWarnings("unused")
		int i = choose.showOpenDialog(comp);
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(choose.getSelectedFile().toString()));
			phoneBook = (PhoneBook) in.readObject();
			in.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void stop(){
		save();
	}
	
}
