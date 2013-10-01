package ui.gui.layout_manager;

import ui.gui.components.PurchasableCardPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * The PurchasableCardLayout is intended to lay out PurchasableCardPanel that are added via the <code>add(Component
 * comp, Object constraints)</code> method. it will use all available space of the parent component to lay out the
 * PurchasableCardPanels.
 */
public class PurchasableCardLayout implements LayoutManager2 {
	private final ArrayList<PurchasableCardPanel> streetComponents;
	private final ArrayList<PurchasableCardPanel> stationComponents;
	private final ArrayList<PurchasableCardPanel> facilityComponents;
	private final int                             gapX;
	private final int                             gapY;

	public PurchasableCardLayout() {
		streetComponents = new ArrayList<PurchasableCardPanel>();
		stationComponents = new ArrayList<PurchasableCardPanel>();
		facilityComponents = new ArrayList<PurchasableCardPanel>();
		gapX = 5;
		gapY = 5;
	}

	/**
	 * Teh method adds a new layout component with constraints
	 *
	 * @param comp        The value has to be a <code>PurchasableCardPanel</code> object
	 * @param constraints the value has to be a <code>PurchasableCardLayoutConstraints</code> object
	 */
	@Override public void addLayoutComponent(Component comp, Object constraints) {
		if(comp instanceof PurchasableCardPanel) {
			if(constraints instanceof PurchasableCardLayoutConstraints) {
				PurchasableCardLayoutConstraints con = (PurchasableCardLayoutConstraints) constraints;
				switch(con.type) {
					case STREET:
						addSorted(streetComponents, (PurchasableCardPanel) comp);
						break;
					case STATION:
						addSorted(stationComponents, (PurchasableCardPanel) comp);
						break;
					case FACILITY:
						addSorted(facilityComponents, (PurchasableCardPanel) comp);
						break;
					default:
						throw new IllegalArgumentException(
								"The PurchasableCardLayoutConstraints has to have the type and position attribute " +
								"set!");
				}
			} else {
				throw new IllegalArgumentException(
						"Layout Constraint must be of the type: PurchasableCardLayoutConstraints");
			}
		} else {
			throw new IllegalArgumentException("Layout Component must be of the type: PurchasableCardPanel");
		}
	}

	/**
	 * This method will add the component based on the constrains position attribute.
	 *
	 * @param group The value determines in which group the component will be added
	 * @param comp  The value determines the component to be added
	 */
	private void addSorted(ArrayList<PurchasableCardPanel> group, PurchasableCardPanel comp) {
		boolean added = false;
		int position = comp.getData().getFieldNumber();
		for(int i = 0; i < group.size(); i++) {
			if(!(group.get(i).getData().getFieldNumber() < position)) {
				group.add(i, comp);
				added = true;
				break;
			}
		}
		if(!added) {
			group.add(comp);
		}
	}

	/**
	 * @param target not needed for this layoutManager;
	 * @return the return value is a Dimension with <code>Integer.MAX_VALUE</code> size
	 */
	@Override public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * @param target not needed for this layoutManager;
	 * @return The return value is <code>0.5f</code>
	 */
	@Override public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	/**
	 * @param target not needed for this layoutManager;
	 * @return The return value is <code>0.5f</code>
	 */
	@Override public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	/**
	 * Not implemented / needed
	 *
	 * @param target not needed for this layoutManager;
	 */
	@Override public void invalidateLayout(Container target) {
	}

	/**
	 * The layout manager doesn't associate strings with components
	 *
	 * @param name not needed for this layoutManager;
	 * @param comp not needed for this layoutManager;
	 */
	@Override public void addLayoutComponent(String name, Component comp) {
		//Empty since the layout manager doesn't associate strings with components
	}

	/**
	 * @param comp The value determines the component to be removed
	 */
	@Override public void removeLayoutComponent(Component comp) {
		streetComponents.remove(comp);
		stationComponents.remove(comp);
		facilityComponents.remove(comp);
	}

	/**
	 * @param parent The value determines the parent component
	 * @return The return value is the preferred layout size
	 */
	@Override public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, true);
	}

	/**
	 * @param parent    The value determines the parent component
	 * @param preferred The value determines if the preferred layout size shall be calculated or the minimal
	 * @return The return value is based on the value of <code>preferred</code> either the minimal, or preferred size of
	 *         the layout
	 */
	private Dimension getLayoutSize(Container parent, boolean preferred) {
		Dimension dimension = new Dimension(0, 0);
		int maxWidth;

		if(preferred) {
			maxWidth = (parent.getWidth() - (parent.getInsets().left + parent.getInsets().right));
		} else {
			maxWidth = (PurchasableCardPanel.width + (parent.getInsets().left + parent.getInsets().right));
		}
		dimension = calculateGroupSize(streetComponents, maxWidth, dimension);
		dimension = calculateGroupSize(stationComponents, maxWidth, dimension);
		dimension = calculateGroupSize(facilityComponents, maxWidth, dimension);

		return dimension;
	}

	/**
	 * @param group     The value determines of which group the size shall be calculated
	 * @param maxWidth  The value determines the max width of the layout
	 * @param dimension The value determines the position of the last component
	 * @return The return value is the  the position of the last component
	 */
	private Dimension calculateGroupSize(ArrayList<PurchasableCardPanel> group, int maxWidth, Dimension dimension) {
		for(PurchasableCardPanel cardPanel : group) {
			if(dimension.width == 0 && dimension.height == 0) {
				dimension.width = gapX;
				dimension.height += cardPanel.getHeight() + gapY + gapY; //Two gapY for the top and bottom
			} else if(dimension.width + cardPanel.getWidth() + gapX > maxWidth) {
				dimension.height += cardPanel.getHeight() + gapY;
				dimension.width = gapX;
			}
			dimension.width += cardPanel.getWidth() + gapX;
		}

		return dimension;
	}

	/**
	 * @param parent The value determines the parent component
	 * @return The return value is the minimum layout sSize
	 */
	@Override public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, false);
	}

	/**
	 * The method will lay out all components
	 *
	 * @param parent The value determines the parent component
	 */
	@Override public void layoutContainer(Container parent) {
		int maxWidth = (parent.getWidth() - (parent.getInsets().left + parent.getInsets().right));
		Dimension dimension = new Dimension(gapX, gapY);

		layoutGroup(streetComponents, maxWidth, dimension);
		layoutGroup(stationComponents, maxWidth, dimension);
		layoutGroup(facilityComponents, maxWidth, dimension);
	}

	/**
	 * The method will lay out all components of the <code>group</code>
	 *
	 * @param group     The value determines of which group the size shall be calculated
	 * @param maxWidth  The value determines the max width of the layout
	 * @param dimension The value determines the position of the last component
	 */
	private void layoutGroup(ArrayList<PurchasableCardPanel> group, int maxWidth, Dimension dimension) {
		for(PurchasableCardPanel cardPanel : group) {
			if(dimension.width + cardPanel.getWidth() + gapX > maxWidth) {
				dimension.height += cardPanel.getHeight() + gapY;
				dimension.width = gapX;
			}
			cardPanel.setBounds(dimension.width, dimension.height, cardPanel.getWidth(), cardPanel.getHeight());
			dimension.width += cardPanel.getWidth() + gapX;
		}
	}
}
