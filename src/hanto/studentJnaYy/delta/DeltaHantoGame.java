/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentJnaYy.delta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.common.BaseHantoGame;
import hanto.studentJnaYy.common.moveControllers.MovementType;


/**
 * This is the implementation of the DeltaHantoGame, where each player 
 * can only place 1 butterfly, 4 crabs,  4 sparrows, and also move them.
 * 
 * @author Joshua and Yan
 */
public class DeltaHantoGame extends BaseHantoGame
{
	protected static final int MAX_TURN_COUNT = Integer.MAX_VALUE;
	
	// Moves where the butterfly doesn't have to be placed on the board.
	protected static final int OPTIONAL_BUTTERFLY_TURNS = 3;
	
	private static final int MAX_BUTTERFLY_COUNT = 1;
	private static final int MAX_SPARROW_COUNT = 4;
	private static final int MAX_CRAB_COUNT = 4;
	
	
	/**
	 * Creates a BetaHanto Game instance
	 * @param movesFirst the color of the first piece to be played.
	 */
	public DeltaHantoGame(HantoPlayerColor movesFirst){
		super(movesFirst, MAX_TURN_COUNT, OPTIONAL_BUTTERFLY_TURNS);
	}

	@Override
	protected boolean checkSpecialConditions(HantoPieceType pieceType, 
			HantoCoordinate from, HantoCoordinate to) {
		return !isResigned(pieceType, from, to);
	}
	
	private boolean isResigned(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		boolean isPieceNull = pieceType == null;
		boolean isFromNull = from == null;
		boolean isToNull = to == null;
		boolean isResigning = isPieceNull && isFromNull && isToNull;
		if(isResigning){
			board.setResigning(currentColor);
		}
		return isResigning;
	}

	/**
	 * Checks to see if the suggested move is valid.
	 * @param pieceType - the HantoPieceType of the given piece.
	 * @param toCoordinate - the desired HantoCoordinate for the given piece.
	 * @return true if the move can be done
	 * @throws HantoException 
	 */
	@Override
	protected void checkMoveValidityPrior(HantoPieceType pieceType, HantoCoordinate fromCoordinate, 
			HantoCoordinate toCoordinate) throws HantoException {
		if(isFromOffTheBoard(fromCoordinate)){
			board.checkPieceAddedNextToOwnColorRule(toCoordinate, currentColor, 1);
		}
		super.checkMoveValidityPrior(pieceType, fromCoordinate, toCoordinate);
	}

	
	/**
	 * Initializes the amount of pieces that each player can place.
	 */
	protected void initializePieceSet(){
		pieceCounter.initializePieceCount(HantoPieceType.BUTTERFLY, MAX_BUTTERFLY_COUNT);
		pieceCounter.initializePieceCount(HantoPieceType.SPARROW, MAX_SPARROW_COUNT);
		pieceCounter.initializePieceCount(HantoPieceType.CRAB, MAX_CRAB_COUNT);

		moveController.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.WALK);
		moveController.setMovementForType(HantoPieceType.SPARROW, MovementType.FLY);
		moveController.setMovementForType(HantoPieceType.CRAB, MovementType.WALK);
	}
	
	
}
