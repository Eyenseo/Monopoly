package ui.gui.components;

import java.awt.*;
import java.util.ArrayList;

public class MapLayout implements LayoutManager2 {
	private final ArrayList<FieldCard> fieldCardComponents;

	public MapLayout() {
		fieldCardComponents = new ArrayList<FieldCard>();
	}

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

	@Override public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@Override public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override public void invalidateLayout(Container target) {
	}

	@Override public void addLayoutComponent(String name, Component comp) {
	}

	@Override public void removeLayoutComponent(Component comp) {
		fieldCardComponents.remove(comp);
	}

	@Override public Dimension preferredLayoutSize(Container parent) {
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
		dimension.width = ((mapSize / 4) - 1) * FieldCard.widthCard + 2 * FieldCard.heightCard;
		dimension.height = dimension.width;

		return dimension;
	}

	@Override public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	@Override public void layoutContainer(Container parent) {
		int cardOverflow = fieldCardComponents.size() % 4;
		int cardsPerSide = fieldCardComponents.size() / 4;
		boolean even = true;
		Point point = new Point();

		//calculates where to start drawing the the field cards
		if(cardOverflow != 0) {
			point.x = cardsPerSide * FieldCard.widthCard + FieldCard.heightCard;
			even = false;
		} else {
			point.x = (cardsPerSide - 1) * FieldCard.widthCard + FieldCard.heightCard;
		}
		point.y = point.x;

		int currentCardIndex = 0;
		for(int i = 0; i < 4; i++) {
			if(cardOverflow > 0) {
				for(int j = 0; j < (cardsPerSide + 1); j++, currentCardIndex++) {
					setBoundaries(i, j, point, currentCardIndex, FieldCard.widthCard, cardsPerSide + 1);
				}
			} else {
				for(int j = 0; j < cardsPerSide; j++, currentCardIndex++) {
					if(even) {
						setBoundaries(i, j, point, currentCardIndex, FieldCard.widthCard, cardsPerSide);
					} else {
						//calculates the x of the card when it is needed to stretch it
						int widthCard = (FieldCard.widthCard * cardsPerSide - 1) / (cardsPerSide - 1);
						setBoundaries(i, j, point, currentCardIndex, widthCard, cardsPerSide);
					}
				}
			}
			if(cardOverflow != 0) {
				cardOverflow--;
			}
		}
	}

	public void setBoundaries(int siteNumber, int currentSiteCard, Point point, int currentCardIndex, int cardWidth,
	                          int cardsPerSide) {
		FieldCard.Rotation angle = FieldCard.Rotation.BOTTOM;

		//rotates the drawing direction of the cards
		switch(siteNumber) {
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
		if(currentSiteCard == 0) {
			fieldCardComponents.get(currentCardIndex)
			                   .setBounds(point.x, point.y, FieldCard.heightCard, FieldCard.heightCard);
		}

		//sets the right size for the current card and the position for the next card
		switch(siteNumber) {
			case 0:
				if(currentSiteCard != 0) {
					fieldCardComponents.get(currentCardIndex)
					                   .setBounds(point.x, point.y, cardWidth, FieldCard.heightCard);
				}

				//sets the position for the next card in the corner
				if(currentSiteCard == cardsPerSide - 1) {
					point.x = point.x - FieldCard.heightCard;
					//sets the position for the next normal card
				} else {
					point.x = point.x - cardWidth;
				}
				break;
			case 1:
				if(currentSiteCard != 0) {
					fieldCardComponents.get(currentCardIndex)
					                   .setBounds(point.x, point.y, FieldCard.heightCard, cardWidth);
				}

				if(currentSiteCard == cardsPerSide - 1) {
					point.y = point.y - FieldCard.heightCard;
				} else {
					point.y = point.y - cardWidth;
				}
				break;
			case 2:
				if(currentSiteCard != 0) {
					fieldCardComponents.get(currentCardIndex)
					                   .setBounds(point.x, point.y, cardWidth, FieldCard.heightCard);
				}

				if(currentSiteCard == 0) {
					point.x = point.x + FieldCard.heightCard;
				} else {
					point.x = point.x + cardWidth;
				}
				break;
			case 3:
				if(currentSiteCard != 0) {
					fieldCardComponents.get(currentCardIndex)
					                   .setBounds(point.x, point.y, FieldCard.heightCard, cardWidth);
				}

				if(currentSiteCard == 0) {
					point.y = point.y + FieldCard.heightCard;
				} else {
					point.y = point.y + cardWidth;
				}
		}
	}
}
