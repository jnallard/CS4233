/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentJnaYy.beta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.common.BaseHantoGame;


/**
 * This is the implementation of the BetaHantoGame, where each player 
 * can only place 1 butterfly and 5 sparrows.
 * 
 * @author Joshua and Yan
 */
public class BetaHantoGame extends BaseHantoGame
{
	private final static int MaxTurnCount = 6;
	
	// Moves where the butterfly doesn't have to be placed on the board.
	private final static int OptionalButterflyTurns = 3;
	
	private final int MaxButterflyCount = 1;
	private final int MaxSparrowCount = 5;
	
	
	/**
	 * Creates a BetaHanto Game instance
	 * @param movesFirst the color of the first piece to be played.
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst){
		super(movesFirst, MaxTurnCount, OptionalButterflyTurns);
	}

	/**
	 * Checks to see if the suggested move is valid.
	 * @param pieceType - the HantoPieceType of the given piece.
	 * @param toCoordinate - the desired HantoCoordinate for the given piece.
	 * @return true if the move can be done
	 */
	protected boolean checkMoveValidity(HantoPieceType pieceType, HantoCoordinate fromCoordinate, HantoCoordinate toCoordinate) {
		
		boolean isFromNull = fromCoordinate == OFF_BOARD_LOCATION;
		if(!isFromNull){
			exceptionMessage = "Movement of pieces is not supported.";
		}
		
		boolean isPieceAvailable = pieceCounter.isPieceAvailable(pieceType, currentColor);
		if(isPieceAvailable){
			exceptionMessage = pieceCounter.getErrorMessage();
		}
		
		boolean isMoveValid = board.isMoveValid(toCoordinate, pieceType, currentColor);
		if(isMoveValid){
			exceptionMessage = board.getErrorMessage();
		}
		
		return isMoveValid && isPieceAvailable && isFromNull;
	}
	
	/**
	 * Initializes the amount of pieces that each player can place.
	 */
	protected void initializePieceCounts(){
		pieceCounter.initializePieceCount(HantoPieceType.BUTTERFLY, MaxButterflyCount);
		pieceCounter.initializePieceCount(HantoPieceType.SPARROW, MaxSparrowCount);
	}
}
