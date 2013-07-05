package gui;

import java.awt.*;
import java.util.ArrayList;

public class MapLayout implements LayoutManager2 {
	private final ArrayList<FieldCard> fieldCardComponents;

	public MapLayout() {
		fieldCardComponents = new ArrayList<FieldCard>();
	}

	@Override public void addLayoutComponent(Component comp, Object constraints) {
		//To change body of implemented methods use File | Settings | File Templates.
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
		return new Dimension(Integer.MAX_VALUE,
		                     Integer.MAX_VALUE);  //To change body of implemented methods use File | Settings | File
		// Templates.
	}

	@Override public float getLayoutAlignmentX(Container target) {
		return 0.5f;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override public float getLayoutAlignmentY(Container target) {
		return 0.5f;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override public void invalidateLayout(Container target) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override public void addLayoutComponent(String name, Component comp) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override public void removeLayoutComponent(Component comp) {
		fieldCardComponents.remove(comp);
	}

	@Override public Dimension preferredLayoutSize(Container parent) {
		int mapSize = fieldCardComponents.size();
		Dimension dimension = new Dimension(0, 0);
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
		dimension.width = ((mapSize / 4) - 1) + 2 * FieldCard.heightCard;
		dimension.height = dimension.width;

		return dimension;
	}

	@Override public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	@Override public void layoutContainer(Container parent) {
		int cardOverflow = fieldCardComponents.size() % 4;
		int cardsPerSite = fieldCardComponents.size() / 4;
		boolean even = true;
		Dimension point = new Dimension();

		if(cardOverflow != 0) {
			point.width = cardsPerSite * FieldCard.widthCard + FieldCard.heightCard;
			even = false;
		} else {
			point.width = (cardsPerSite - 1) * FieldCard.widthCard + FieldCard.heightCard;
		}

		point.height = point.width;
		int currentCardIndex = 0;
		for(int i = 0; i < 4; i++) {
			if(cardOverflow > 0) {
				for(int j = 0; j < cardsPerSite; j++, currentCardIndex++) {
					setBoundaries(i, j, point, currentCardIndex, FieldCard.widthCard, cardsPerSite);
				}
			} else {
				for(int j = 0; j < cardsPerSite; j++, currentCardIndex++) {
					if(even) {
						setBoundaries(i, j, point, currentCardIndex, FieldCard.widthCard, cardsPerSite);
					} else {
						int widthCard = FieldCard.widthCard / (cardsPerSite - 1);
						setBoundaries(i, j, point, currentCardIndex, widthCard, cardsPerSite);
					}
				}
			}
			if(cardOverflow != 0) {
				cardOverflow--;
			}
		}

		//
	}

	public Dimension setBoundaries(int siteNumber, int currentSiteCard, Dimension point, int currentCardIndex,
	                               int cardWidth, int cardsPerSite) {
		FieldCard.Rotation angle = FieldCard.Rotation.BOTTOM;
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

		switch(siteNumber) {
			case 0:
				if(currentSiteCard == 0) {
					fieldCardComponents.get(currentCardIndex).setBounds(point.width, point.height,
					                                                    FieldCard.heightCard,
					                                                    FieldCard.heightCard);
				} else {
					fieldCardComponents.get(currentCardIndex)
					                   .setBounds(point.width, point.height, cardWidth, FieldCard.heightCard);
				}

				if(currentSiteCard == cardsPerSite - 1) {
					point.width = point.width - FieldCard.heightCard;
				} else {
					point.width = point.width - cardWidth;
				}
				break;
			case 1:
				if(currentSiteCard == 0) {
					fieldCardComponents.get(currentCardIndex).setBounds(point.width, point.height,
					                                                    FieldCard.heightCard,
					                                                    FieldCard.heightCard);
				} else {
					fieldCardComponents.get(currentCardIndex)
					                   .setBounds(point.width, point.height, FieldCard.heightCard, cardWidth);
				}

				if(currentSiteCard == cardsPerSite - 1) {
					point.height = point.height - FieldCard.heightCard;
				} else {
					point.height = point.height - cardWidth;
				}
				break;
			case 2:
				if(currentSiteCard == 0) {
					fieldCardComponents.get(currentCardIndex).setBounds(point.width, point.height,
					                                                    FieldCard.heightCard,
					                                                    FieldCard.heightCard);
				} else {
					fieldCardComponents.get(currentCardIndex)
					                   .setBounds(point.width, point.height, cardWidth, FieldCard.heightCard);
				}

				if(currentSiteCard == cardsPerSite - 1) {
					point.width = point.width + FieldCard.heightCard;
				} else {
					point.width = point.width + cardWidth;
				}
				break;
			case 3:
				if(currentSiteCard == 0) {
					fieldCardComponents.get(currentCardIndex).setBounds(point.width, point.height,
					                                                    FieldCard.heightCard,
					                                                    FieldCard.heightCard);
				} else {
					fieldCardComponents.get(currentCardIndex)
					                   .setBounds(point.width, point.height, FieldCard.heightCard, cardWidth);
				}

				if(currentSiteCard == cardsPerSite - 1) {
					point.height = point.height + FieldCard.heightCard;
				} else {
					point.height = point.height + cardWidth;
				}
		}
		fieldCardComponents.get(currentCardIndex).setRotation(angle);
		return point;
	}
}
