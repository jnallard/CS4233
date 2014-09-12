/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.common;

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * @author Joshua and Yan
 * This class is the object that keeps track of how many pieces of each type are left to placed
 * on the board.
 *
 */
public class PieceAvailabilityCounter {

	private Map<HantoPieceType, Integer> redPiecesCount = new HashMap<HantoPieceType, Integer>();
	private Map<HantoPieceType, Integer> bluePiecesCount = new HashMap<HantoPieceType, Integer>();

	
	/**
	 * Creates a PieceAvailabilityCounter instance.
	 */
	public PieceAvailabilityCounter(){
		
	}
	
	/**
	 * This initializes the max count for the type given.
	 * @param type the type to initialize
	 * @param maximumTypeCount the maximum amount one player can have of a type on the board.
	 */
	public void initializePieceCount(HantoPieceType type, int maximumTypeCount){
		redPiecesCount.put(type, maximumTypeCount);
		bluePiecesCount.put(type, maximumTypeCount);
	}
	
	/**
	 * Checks to see if there are still pieces available (count > 0) for the given type and color
	 * @param pieceType - the type to check for 
	 * @param color - the player to check for
	 * @return true if there is at least one piece left
	 */
	public boolean isPieceAvailable(HantoPieceType pieceType, HantoPlayerColor color) {
		boolean isValid = isValidPiece(pieceType);
		if(isValid){
			switch(color){
				case RED:
					isValid = redPiecesCount.get(pieceType) > 0;
					break;
				case BLUE:
					isValid = bluePiecesCount.get(pieceType) > 0;
					break;			
			}
			
		}
		return isValid;
	}
	
	/**
	 * Checks to see if the given piece is valid for the game.
	 * @param pieceType the type to check for
	 * @return true if the piece type is contained in the hashmaps
	 */
	private boolean isValidPiece(HantoPieceType pieceType) {
		
		return redPiecesCount.containsKey(pieceType);
	}

}
