package application;

import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import phonebook.PhoneBook;

public class PhoneBookMenu extends MenuBar {
	private PhoneBook phoneBook;
	private NameListView nameListView;

	/**
	 * Creates the menu for the phone book application.
	 * 
	 * @param phoneBook
	 *            the phone book with names and numbers
	 * @param nameListView
	 *            handles the list view for the names
	 */
	public PhoneBookMenu(PhoneBook phoneBook, NameListView nameListView) {
		this.phoneBook = phoneBook;
		this.nameListView = nameListView;

		final Menu menuPhoneBook = new Menu("PhoneBook");
		final MenuItem menuQuit = new MenuItem("Quit");
		menuQuit.setOnAction(e -> Platform.exit());
		menuPhoneBook.getItems().addAll(menuQuit);

		final Menu menuFind = new Menu("Find");

		final MenuItem menuShowAll = new MenuItem("Show All");
		menuShowAll.setOnAction(e -> showAll());
		menuFind.getItems().addAll(menuShowAll);

		final MenuItem findNumber = new MenuItem("Find number");
		findNumber.setOnAction(e -> findNumber());
		menuFind.getItems().addAll(findNumber);

		final MenuItem findName = new MenuItem("Find Name");
		findName.setOnAction(e -> findName());
		menuFind.getItems().addAll(findName);

		getMenus().addAll(menuPhoneBook, menuFind);
		setUseSystemMenuBar(true); // if you want operating system rendered menus, uncomment this line
	}

	private void showAll() {
		nameListView.fillList(phoneBook.names());
		nameListView.clearSelection();
	}

	private void findNumber() {
		Optional<String> result = Dialogs.oneInputDialog("Find number", "Enter the name", "Name");
		if (result == null) {
			Dialogs.alert("Number not found", null, "There is no number tied to the name");
		} else {
			nameListView.fillList(phoneBook.findNumbers(result.get()));
			nameListView.clearSelection();
		}
	}

	private void findName() {
		Optional<String> result = Dialogs.oneInputDialog("Find name", "Enter the number", "Number");
		if (result == null) {
			Dialogs.alert("Name not found", null, "There is no such name in the book");
		} else {
			nameListView.fillList(phoneBook.findNames(result.get()));
			nameListView.clearSelection();
		}
	}
}
