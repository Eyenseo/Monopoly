package randomTests;

import core.Connector;
import core.data.MapArrayCreator;
import objects.Player;
import objects.card.CardStack;
import objects.exceptions.MessageStackException;
import objects.exceptions.core.LoaderException;
import objects.map.FieldCircularList;
import ui.Menu;
import ui.cui.ConsoleMenu;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Vector;

public class FileTest {
	private String path = "/storage/";
	private FieldCircularList go;
	private FieldCircularList jail;
	private Vector<Player> playerVector = new Vector<Player>();
	private Menu menu;

	public static void main(String[] args) {
		FileTest f = new FileTest();
		Menu menu = new ConsoleMenu();
		try {
			f.testFile(menu);
		} catch(MessageStackException e) {
			System.err.println(e.getMessageStack());
		}
	}

	private void testFile(Menu menu) throws MessageStackException {
		long t = System.currentTimeMillis();
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
				copyFile("map.txt");
				copyFile("community.txt");
				copyFile("events.txt");
				MapArrayCreator mac = new MapArrayCreator();
				CardStack event = new CardStack("events.txt", "Event Karte");
				CardStack community = new CardStack("community.txt", "Gemeinschafts Karte");
				new Connector().connect(mac.getMap(), event, community, playerVector, menu);
				this.go = mac.getGo();
				this.jail = mac.getJail();
				ObjectOutputStream serializedFile = new ObjectOutputStream(new FileOutputStream("monopoly.ser"));
				serializedFile.writeObject(go);
				serializedFile.writeObject(jail);
				serializedFile.writeObject(playerVector);
				serializedFile.writeObject(this.menu);
				serializedFile.close();
			} catch(IOException e) {
				new File("monopoly.ser").delete();
				throw new LoaderException("The problem occurred while writing to the monopoly.ser file.", e);
			}
		}
		System.out.println(System.currentTimeMillis() - t);
	}

	private void copyFile(String file) throws LoaderException {
		try {
			File out = new File(file);
			File original = new File(getClass().getResource(path + file).toURI());
			if(!out.exists()) {
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

	private boolean isSerialized() {
		File file = new File("monopoly.ser");
		return file.exists();
	}
}
