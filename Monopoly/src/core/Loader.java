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
import java.util.Vector;

//JAVADOC
public class Loader {
	private String path = "/storage/";
	private FieldCircularList go;
	private FieldCircularList jail;
	private Vector<Player> playerVector = new Vector<Player>();
	private Menu menu;

	//JAVADOC
	public Loader(Menu menu) throws MessageStackException {
		copyFile("map.txt");
		copyFile("community.txt");
		copyFile("events.txt");
		if(isSerialized()) {
			try {
				ObjectInputStream serializedFile = new ObjectInputStream(new FileInputStream("monopoly.ser"));
				go = (FieldCircularList) serializedFile.readObject();
				jail = (FieldCircularList) serializedFile.readObject();
				playerVector = (Vector<Player>) serializedFile.readObject();
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
				MapArrayCreator mac = new MapArrayCreator();
				CardStack event = new CardStack("events.txt", "Event Karte");
				CardStack community = new CardStack("community.txt", "Gemeinschafts Karte");
				new Connector().connect(mac.getMap(), event, community, playerVector, menu);
				this.go = mac.getGo();
				this.jail = mac.getJail();
				this.menu = menu;
				ObjectOutputStream serializedFile = new ObjectOutputStream(new FileOutputStream("monopoly.ser"));
				serializedFile.writeObject(go);
				serializedFile.writeObject(jail);
				serializedFile.writeObject(playerVector);
				serializedFile.writeObject(menu);
				serializedFile.close();
			} catch(IOException e) {
				new File("monopoly.ser").delete();
				throw new LoaderException("The problem occurred while writing to the monopoly.ser file.", e);
			}
		}
	}

	//JAVADOC
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

	//JAVADOC
	private boolean isSerialized() {
		File file = new File("monopoly.ser");
		return file.exists();
	}

	//JAVADOC
	public FieldCircularList getGo() {
		return go;
	}

	//JAVADOC
	public FieldCircularList getJail() {
		return jail;
	}

	//JAVADOC
	public Vector<Player> getPlayerVector() {
		return playerVector;
	}

	//JAVADOC
	public Menu getMenu() {
		return menu;
	}
}
