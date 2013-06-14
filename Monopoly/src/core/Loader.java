package core;

import core.data.MapArrayCreator;
import objects.Player;
import objects.card.CardStack;
import objects.exceptions.MessageStackException;
import objects.exceptions.core.LoaderException;
import objects.map.FieldCircularList;
import ui.Menu;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * The loader is responsible for the persistence of the data.
 */
class Loader {
	private final String path = "/storage/";
	private FieldCircularList go;
	private FieldCircularList jail;
	private ArrayList<Player> playerArrayList = new ArrayList<Player>();
	private Menu menu;

	/**
	 * In the constructor the files that are not present will be copied to the execute location and if there isn't
	 * serialized the file will be created.
	 *
	 * @param menu to be deleted
	 * @throws MessageStackException the exception will be thrown if there was a problem while reading or writing from
	 *                               the files
	 */
	//TODO remove menu
	public Loader(Menu menu) throws MessageStackException {
		copyFile("map.txt");
		copyFile("community.txt");
		copyFile("events.txt");
		if(isSerialized()) {
			try {
				ObjectInputStream serializedFile = new ObjectInputStream(new FileInputStream("monopoly.ser"));
				go = (FieldCircularList) serializedFile.readObject();
				jail = (FieldCircularList) serializedFile.readObject();
				playerArrayList = (ArrayList<Player>) serializedFile.readObject();
				this.menu = (Menu) serializedFile.readObject();
			} catch(IOException e) {
				new File("monopoly.ser").delete();
				throw new LoaderException("The problem occurred while reading the file monopoly.ser.", e);
			} catch(ClassNotFoundException e) {
				new File("monopoly.ser").delete();
				throw new LoaderException("The problem occurred while reading a class from the file monopoly.ser.", e);
			}
		} else {
			try {
				//TODO load files in three threads and check for a.join(), b.join() c.join()
				MapArrayCreator mac = new MapArrayCreator();
				CardStack event = new CardStack("events.txt", "Event Karte");
				CardStack community = new CardStack("community.txt", "Gemeinschafts Karte");
				new Connector().connect(mac.getMap(), event, community, playerArrayList, menu);
				go = mac.getGo();
				jail = mac.getJail();
				this.menu = menu;
				ObjectOutputStream serializedFile =
						new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("monopoly.ser")));
				serializedFile.writeObject(go);
				serializedFile.writeObject(jail);
				serializedFile.writeObject(playerArrayList);
				serializedFile.writeObject(menu);
				serializedFile.close();
			} catch(IOException e) {
				new File("monopoly.ser").delete();
				throw new LoaderException("The problem occurred while writing to the monopoly.ser file.", e);
			}
		}
	}

	/**
	 * The method copies the file if it finds a file to the dictionary the program is run in
	 *
	 * @param file the value determines the file to be loaded
	 * @throws LoaderException the exception is thrown if there is a problem while reading or writing the file
	 */
	private void copyFile(String file) throws LoaderException {
		try {
			File out = new File(file);
			if(!out.exists()) {
				File original = new File(getClass().getResource(path + file).toURI());
				Files.copy(original.toPath(), out.toPath());
			}
		} catch(URISyntaxException e) {
			throw new LoaderException("The problem occurred while reading the file:\n\t" + file, e);
		} catch(IOException e) {
			throw new LoaderException("The problem occurred while reading the file:\n\t" + file, e);
		} catch(NullPointerException e) {
			throw new LoaderException("The problem occurred while reading the file:\n\t" + file, e);
		}
	}

	/**
	 * @return the return value is true if there is the serialized monopoly file
	 */
	private boolean isSerialized() {
		File file = new File("monopoly.ser");
		return file.exists();
	}

	/**
	 * @return the return value is the first field of the map
	 */
	public FieldCircularList getGo() {
		return go;
	}

	/**
	 * @return the return value is the jail field of the map
	 */
	public FieldCircularList getJail() {
		return jail;
	}

	/**
	 * @return the return value is the Player ArrayList
	 */
	public ArrayList<Player> getPlayerArrayList() {
		return playerArrayList;
	}
}
