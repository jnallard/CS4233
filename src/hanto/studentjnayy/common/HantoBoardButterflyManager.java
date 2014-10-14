/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentjnayy.common;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * This class is used for keeping track of the butterflies on the board
 * as well as keeping track of the special conditions for them
 * @author Joshua and Yan
 *
 */
public class HantoBoardButterflyManager {
	private GameCoordinate redButterflyCoord = null;
	private GameCoordinate blueButterflyCoord = null;
	private int butterflyOptionalTurns;

	/**
	 * Returns a new ButterflyManager object
	 * @param butterflyOptionalTurns the number of turns where placing a
	 * butterfly is optional
	 */
	public HantoBoardButterflyManager(int butterflyOptionalTurns) {
		this.butterflyOptionalTurns = butterflyOptionalTurns;
	}

	/**
	 * Assigns the coordinate to the specific butterfly location of the player color.
	 * @param butterflyCoord the location of the butterfly
	 * @param player the player color for the butterfly
	 */
	public void setButterflyCoord(HantoPlayerColor player, GameCoordinate butterflyCoord) {
		switch(player){
			case RED:
				redButterflyCoord = butterflyCoord;
				break;
			case BLUE:
				blueButterflyCoord = butterflyCoord;
				break;
		}
	}
	
	/**
	 * Checks to see if the conditions for a butterfly placement have been met.
	 * @param turnCount the move count for the board
	 * @param type the piece type
	 * @param color the piece color
	 * @return true if the piece passes the conditions set for butterflies.
	 */
	public boolean areButterflyConditionsMet(int turnCount, HantoPieceType type, 
			HantoPlayerColor color) {
		
		boolean isButterflyCoordSet = getButterflyCoord(color) != null;
		boolean isPieceButterfly = HantoPieceType.BUTTERFLY.equals(type);
		
		boolean areConditionsMet = true;
		if(turnCount > butterflyOptionalTurns){
			areConditionsMet = isButterflyCoordSet || isPieceButterfly;
		}
		
		return areConditionsMet;
	}
	
	/**
	 * Gets the butterfly coordinate based upon the player color
	 * @param color the player color to filter by
	 * @return A GameCoordinate for the color's butterfly, null if not set.
	 */
	public GameCoordinate getButterflyCoord(HantoPlayerColor color){
		GameCoordinate butterflyCoord = null;
		switch(color){
			case RED:
				butterflyCoord = redButterflyCoord;
				break;
			case BLUE:
				butterflyCoord = blueButterflyCoord;
				break;
		}
		return butterflyCoord;
	}
}