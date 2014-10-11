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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanto.common.HantoException;
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
	private String exceptionMessage = "";

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
	 * @throws HantoException 
	 */
	public void checkPieceAvailability(HantoPieceType pieceType, HantoPlayerColor color) 
			throws HantoException {
		boolean isValid = isValidPiece(pieceType);
		if(isValid){
			isValid = isPieceCountNotZero(pieceType, color);
		}
		
		if(!isValid){
			throw new HantoException(exceptionMessage);
		}
	}

	/**
	 * Checks to see if the type has a count above zero
	 * @param pieceType the type to check for
	 * @param color the player to check for
	 * @return true if there is at least one piece left
	 */
	private boolean isPieceCountNotZero(HantoPieceType pieceType, HantoPlayerColor color) {
		boolean isValid;
		isValid = getPieceCounterByColor(color).get(pieceType) > 0;
		if(!isValid){
			exceptionMessage = "A " + color + " " + pieceType + " is not available";
		}
		return isValid;
	}
	
	/**
	 * Subtracts one piece of the particular type from the piece counter.
	 * @param type - the type to change the count for
	 * @param color - the player to change the count for
	 * @throws HantoException - if the piece fails validation, an exception is thrown.
	 */
	public void utilizePiece(HantoPieceType type, HantoPlayerColor color) throws HantoException{
		checkPieceAvailability(type, color);
		int value = getPieceCounterByColor(color).get(type);
		getPieceCounterByColor(color).put(type, --value);
	}
	
	/**
	 * Checks to see how many pieces are available for a given player.
	 * @param color the player to check for
	 * @return the number of pieces available
	 */
	public int getPiecesAvailableCount(HantoPlayerColor color){
		int count = 0;
		Map<HantoPieceType, Integer> colorPieceCounter = getPieceCounterByColor(color);
		for(HantoPieceType type: colorPieceCounter.keySet()){
			count += colorPieceCounter.get(type);
		}
		return count;
	}
	
	/**
	 * Checks to see if the given piece is valid for the game.
	 * @param pieceType the type to check for
	 * @return true if the piece type is contained in the hashmaps
	 */
	private boolean isValidPiece(HantoPieceType pieceType) {
		
		boolean isValid = redPiecesCount.containsKey(pieceType);
		if(!isValid){
			exceptionMessage = pieceType + " is not a valid piece type for the game.";
		}
		
		return isValid;
	}
	
	/**
	 * Returns the respective counter for each color
	 * @param color - the color associated with the wanted counter
	 * @return the color's piece counter
	 */
	private Map<HantoPieceType, Integer> getPieceCounterByColor(HantoPlayerColor color){
		Map<HantoPieceType, Integer> counter = null;
		switch(color){
			case RED:
				counter = redPiecesCount;
				break;
			case BLUE:
				counter = bluePiecesCount;
				break;
		}
		return counter;
	}

	public List<HantoPieceType> getPieceTypesAvailable(
			HantoPlayerColor color) {
		List<HantoPieceType> piecesAvailable = new ArrayList<HantoPieceType>();
		Map<HantoPieceType, Integer> counter = getPieceCounterByColor(color);
		for(HantoPieceType type: counter.keySet()){
			if(counter.get(type) > 0){
				piecesAvailable.add(type);
			}
		}
		return piecesAvailable;
	}

}
