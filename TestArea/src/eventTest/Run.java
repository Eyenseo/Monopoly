package eventTest;

public class Run {
	public static void main(String[] args) {
		XyzEventSource source = new XyzEventSource();
		XyzEventDestination destination = new XyzEventDestination();
		source.addXyzEventListener(XyzEventName.BLA, destination);

		source.setBla(5);
	}
}
