package ui.gui.layout_manager;

import ui.gui.components.FieldCard;

import java.awt.*;
import java.util.ArrayList;

/**
 * The MapLayout is used to lay out FieldCards in a rectangle starting in the bottom right hand corner
 */
public class MapLayout implements LayoutManager2 {
	private final ArrayList<FieldCard> fieldCardComponents;

	public MapLayout() {
		fieldCardComponents = new ArrayList<FieldCard>();
	}

	/**
	 * @param comp        The value determines the component o be added to the Layout
	 * @param constraints The value determines the constraints to be used - ADD WITH null !!
	 */
	//TODO get the components from the parent
	@Override public void addLayoutComponent(Component comp, Object constraints) {
		//checks if added component is of correct type
		if(comp instanceof FieldCard) {
			if(constraints == null) {
				fieldCardComponents.add((FieldCard) comp);
			} else {
				throw new IllegalArgumentException("Layout Constraint must be of the type: NULL.");
			}
		} else {
			throw new IllegalArgumentException("Layout Component must be of the type: FieldCard.");
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
		fieldCardComponents.remove(comp);
	}

	/**
	 * @param parent The value determines the parent component
	 * @return The return value is the preferred layout size - the size is the size of the parent
	 */
	@Override public Dimension preferredLayoutSize(Container parent) {
		return parent.getParent().getSize();
	}

	/**
	 * @return The return value is the real size that the component needs
	 */
	private Dimension realSize() {
		int mapSize = fieldCardComponents.size();
		Dimension dimension = new Dimension(0, 0);
		//rounds up the map size if the amount of cards is divisible by four
		if(fieldCardComponents.size() % 4 != 0) {
			switch(mapSize % 4) {
				case 1:
					mapSize += 3;
					break;
				case 2:
					mapSize += 2;
					break;
				case 3:
					mapSize += 1;
					break;
			}
		}
		//calculates the total x and y of the map
		dimension.width = (((mapSize / 4) - 1) * FieldCard.widthCard + 2 * FieldCard.heightCard);
		dimension.height = dimension.width;

		return dimension;
	}

	/**
	 * @param parent The value determines the parent component
	 * @return The return value is the minimum layout size - the size is the size of the parent
	 */
	@Override public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	/**
	 * The method will lay out all components, if the amount of components is not dividable by 4 the components will
	 * get
	 * stretched
	 *
	 * @param parent The value determines the parent component
	 */
	@Override public void layoutContainer(Container parent) {
		int cardOverflow = fieldCardComponents.size() % 4;
		int cardsPerSide = fieldCardComponents.size() / 4;
		boolean even = true;
		Point point = new Point();

		Dimension preferredSize = realSize();
		double scaleX = (double) parent.getParent().getSize().width / (double) preferredSize.width;
		double scaleY = (double) parent.getParent().getSize().height / (double) preferredSize.height;
		double scale = 1;
		if(scaleX > scaleY) {
			scale = scaleY;
		} else {
			scale = scaleX;
		}

		//calculates where to start drawing the the field cards
		if(cardOverflow != 0) {
			point.x = cardsPerSide * FieldCard.widthCard + FieldCard.heightCard;
			even = false;
		} else {
			point.x = (cardsPerSide - 1) * FieldCard.widthCard + FieldCard.heightCard;
		}
		point.x = (int) (point.x * scale);
		point.y = point.x;

		int currentCardIndex = 0;
		for(int i = 0; i < 4; i++) {
			if(cardOverflow > 0) {
				for(int j = 0; j < (cardsPerSide + 1); j++, currentCardIndex++) {
					setBoundaries(scale, i, j, point, currentCardIndex, (int) (FieldCard.widthCard * scale),
					              (int) (FieldCard.heightCard * scale), cardsPerSide + 1);
				}
			} else {
				for(int j = 0; j < cardsPerSide; j++, currentCardIndex++) {
					if(even) {
						setBoundaries(scale, i, j, point, currentCardIndex, (int) (FieldCard.widthCard * scale),
						              (int) (FieldCard.heightCard * scale), cardsPerSide);
					} else {
						//calculates the x of the card when it is needed to stretch it
						int widthCard = (int) (((FieldCard.widthCard * cardsPerSide - 1) / (cardsPerSide - 1)) * scale);
						setBoundaries(scale, i, j, point, currentCardIndex, widthCard,
						              (int) (FieldCard.heightCard * scale), cardsPerSide);
					}
				}
			}
			if(cardOverflow != 0) {
				cardOverflow--;
			}
		}
	}

	/**
	 * The method will set the boundary of the component
	 *
	 * @param scale            The value determines the scale of the component
	 * @param sideNumber       The value determines side <ul></ul> <li>0: bottom</li> <li>1: left</li> <li>2: top</li>
	 *                         <li>3: right</li>
	 * @param currentSideCard  The value determines the index from the side
	 * @param point            The value determines the point at which the card has to be drawn
	 * @param currentCardIndex The value determines the the index of the card from all cards
	 * @param cardWidth        The value determines the card width
	 * @param cardHeight       The value determines the card height
	 * @param cardsPerSide     The value determines the how many cards will be placed on the side
	 */
	public void setBoundaries(double scale, int sideNumber, int currentSideCard, Point point, int currentCardIndex,
	                          int cardWidth, int cardHeight, int cardsPerSide) {
		FieldCard.Rotation angle = FieldCard.Rotation.BOTTOM;

		fieldCardComponents.get(currentCardIndex).setScale(scale);

		//rotates the drawing direction of the cards
		switch(sideNumber) {
			case 0:
				angle = FieldCard.Rotation.BOTTOM;
				break;
			case 1:
				angle = FieldCard.Rotation.LEFT;
				break;
			case 2:
				angle = FieldCard.Rotation.TOP;
				break;
			case 3:
				angle = FieldCard.Rotation.RIGHT;
		}

		fieldCardComponents.get(currentCardIndex).setRotation(angle);

		//sets the right size for the first card in the corner
		if(currentSideCard == 0) {
			fieldCardComponents.get(currentCardIndex).setBounds(point.x, point.y, cardHeight, cardHeight);
		}

		//sets the right size for the current card and the position for the next card
		switch(sideNumber) {
			case 0:
				if(currentSideCard != 0) {
					fieldCardComponents.get(currentCardIndex).setBounds(point.x, point.y, cardWidth, cardHeight);
				}

				//sets the position for the next card in the corner
				if(currentSideCard == cardsPerSide - 1) {
					point.x = point.x - cardHeight;
					//sets the position for the next normal card
				} else {
					point.x = point.x - cardWidth;
				}
				break;
			case 1:
				if(currentSideCard != 0) {
					fieldCardComponents.get(currentCardIndex).setBounds(point.x, point.y, cardHeight, cardWidth);
				}

				if(currentSideCard == cardsPerSide - 1) {
					point.y = point.y - cardHeight;
				} else {
					point.y = point.y - cardWidth;
				}
				break;
			case 2:
				if(currentSideCard != 0) {
					fieldCardComponents.get(currentCardIndex).setBounds(point.x, point.y, cardWidth, cardHeight);
				}

				if(currentSideCard == 0) {
					point.x = point.x + cardHeight;
				} else {
					point.x = point.x + cardWidth;
				}
				break;
			case 3:
				if(currentSideCard != 0) {
					fieldCardComponents.get(currentCardIndex).setBounds(point.x, point.y, cardHeight, cardWidth);
				}

				if(currentSideCard == 0) {
					point.y = point.y + cardHeight;
				} else {
					point.y = point.y + cardWidth;
				}
		}
	}
}
