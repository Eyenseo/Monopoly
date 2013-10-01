package core;

import core.data.MapArrayCreator;
import objects.Player;
import objects.card.CardStack;
import objects.exceptions.MessageStackException;
import objects.exceptions.core.LoaderException;
import objects.map.FieldCircularList;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * The loader is responsible for the persistence of the data.
 */
class Loader {
	private final String path = "/storage/";
	private FieldCircularList go;
	private FieldCircularList jail;
	private CardStack         event;
	private CardStack         community;
	private HashMap<Integer, Player> playerHashMap = new HashMap<Integer, Player>();

	/**
	 * In the constructor the files that are not present will be copied to the execute location and if there isn't
	 * serialized the file will be created.
	 *
	 * @throws MessageStackException the exception will be thrown if there was a problem while reading or writing from the
	 *                               files
	 */
	public Loader() throws MessageStackException {
		copyFile("map.txt");
		copyFile("community.txt");
		copyFile("events.txt");
		if(isSerialized()) {
			try {
				//reads serialized data from monopoly.ser to get every map data and player data
				ObjectInputStream serializedFile = new ObjectInputStream(new FileInputStream("monopoly.ser"));
				go = (FieldCircularList) serializedFile.readObject();
				jail = (FieldCircularList) serializedFile.readObject();
				event = (CardStack) serializedFile.readObject();
				community = (CardStack) serializedFile.readObject();
				playerHashMap = (HashMap<Integer, Player>) serializedFile.readObject();
			} catch(IOException e) {
				new File("monopoly.ser").delete();
				throw new LoaderException("The problem occurred while reading the file monopoly.ser.", e);
			} catch(ClassNotFoundException e) {
				new File("monopoly.ser").delete();
				throw new LoaderException("The problem occurred while reading a class from the file monopoly.ser.", e);
			}
		} else {
			//creates a new MapArrayCreator, gets every map data and creates/connects a new map.
			//newly created data is serialized and stored in monopoly.ser
			try {
				//TODO load files in three threads and check for a.join(), b.join() c.join()
				MapArrayCreator mac = new MapArrayCreator();
				event = new CardStack("events.txt", "Event Karte");
				community = new CardStack("community.txt", "Gemeinschafts Karte");
				new Connector().connect(mac.getMap(), event, community, playerHashMap);
				go = mac.getGo();
				jail = mac.getJail();
				ObjectOutputStream serializedFile =
						new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("monopoly.ser")));
				serializedFile.writeObject(go);
				serializedFile.writeObject(jail);
				serializedFile.writeObject(event);
				serializedFile.writeObject(community);
				serializedFile.writeObject(playerHashMap);
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
			//copies files from storage to executing directory
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
	public HashMap<Integer, Player> getPlayerHashMap() {
		return playerHashMap;
	}

	/**
	 * @return The return is the event CardStack
	 */
	CardStack getEvent() {
		return event;
	}

	/**
	 * @return The return value is the community CardStack
	 */
	CardStack getCommunity() {
		return community;
	}
}
