package eventTest;

public class XyzEventDestination implements XyzEventListener {

	@Override
	public void handleMyEventClassEvent(XyzEvent e) {
		System.err.println("Event!");
	}
}
