package eventTest;

import java.util.ArrayList;
import java.util.HashMap;

enum XyzEventName {
	BLA, NAME

}

public class XyzEventSource {
	private HashMap<XyzEventName, ArrayList<XyzEventListener>> listener =
			new HashMap<XyzEventName, ArrayList<XyzEventListener>>();

	XyzEventSource() {
		for(XyzEventName e : XyzEventName.values()) {
			listener.put(e, new ArrayList<XyzEventListener>());
		}
	}

	int bla = 0;

	public void setBla(int bla) {
		this.bla = bla;
		fireXyzEvent(XyzEventName.BLA);
	}

	public void addXyzEventListener(XyzEventName name, XyzEventListener listener) {
		this.listener.get(name).add(listener);
	}

	public void removeXyzEventListener(XyzEventName name, XyzEventListener listener) {
		this.listener.get(name).remove(listener);
	}

	private void fireXyzEvent(XyzEventName name) {
		XyzEvent event = new XyzEvent(this);
		for(Object l : listener.get(name)) {
			((XyzEventListener) l).handleMyEventClassEvent(event);
		}
	}
}
