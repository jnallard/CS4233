/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentJnaYy.epsilon;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.studentJnaYy.common.BaseHantoGame;
import hanto.studentJnaYy.common.moveControllers.MovementType;


/**
 * This is the implementation of the DeltaHantoGame, where each player 
 * can only place 1 butterfly, 4 crabs,  4 sparrows, and also move them.
 * 
 * @author Joshua and Yan
 */
public class EpsilonHantoGame extends BaseHantoGame
{
	protected static final int MAX_TURN_COUNT = Integer.MAX_VALUE;
	
	// Moves where the butterfly doesn't have to be placed on the board.
	protected static final int OPTIONAL_BUTTERFLY_TURNS = 3;
	
	private static final int MAX_BUTTERFLY_COUNT = 1;
	private static final int MAX_SPARROW_COUNT = 2;
	private static final int MAX_CRAB_COUNT = 6;
	private static final int MAX_HORSE_COUNT = 4;
	
	private static final int MAX_FLY_DISTANCE = 4;
	
	
	/**
	 * Creates a BetaHanto Game instance
	 * @param movesFirst the color of the first piece to be played.
	 */
	public EpsilonHantoGame(HantoPlayerColor movesFirst){
		super(movesFirst, MAX_TURN_COUNT, OPTIONAL_BUTTERFLY_TURNS);
	}

	/**
	 * Checks to see if the special condition (resignation) is met
	 * in order to justify breaking the normal template process
	 * @throws HantoException 
	 */
	@Override
	protected boolean checkSpecialMoves(HantoPieceType pieceType, 
			HantoCoordinate from, HantoCoordinate to) throws HantoException {
		return !isResigned(pieceType, from, to);
	}
	
	/**
	 * Checks to see if a move was a resignation move
	 * @param pieceType the type of the piece, (null to resign)
	 * @param from the location of the piece, (null to resign)
	 * @param to the destination of the piece, (null to resign)
	 * @return true if all the conditions are met to resign
	 * @throws HantoException 
	 */
	@Override
	protected boolean isResigned(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		boolean result = false;
		if(super.isResigned(pieceType, from, to)){
			boolean areMovesAvailable = board.getPossibleMovesForPlayer(currentColor).size() != 0;
			boolean arePlacementsAvailable = board.getPlacementsAvailable(currentColor, 
												PiecePlacementOwnColorExceptionTurns).size() != 0;
			boolean arePiecesAvailable = pieceCounter.getPiecesAvailableCount(currentColor) != 0;
			if(areMovesAvailable || (arePlacementsAvailable && arePiecesAvailable)){
				throw new HantoPrematureResignationException();
			}
			result = true;
		}
		return result;
	}
	
	/**
	 * Initializes the amount of pieces that each player can place and their movement styles.
	 */
	protected void initializePieceSet(){
		pieceCounter.initializePieceCount(HantoPieceType.BUTTERFLY, MAX_BUTTERFLY_COUNT);
		pieceCounter.initializePieceCount(HantoPieceType.SPARROW, MAX_SPARROW_COUNT);
		pieceCounter.initializePieceCount(HantoPieceType.CRAB, MAX_CRAB_COUNT);
		pieceCounter.initializePieceCount(HantoPieceType.HORSE, MAX_HORSE_COUNT);

		moveController.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.WALK);
		moveController.setMovementForType(HantoPieceType.SPARROW, MovementType.FLY, MAX_FLY_DISTANCE);
		moveController.setMovementForType(HantoPieceType.CRAB, MovementType.WALK);
		moveController.setMovementForType(HantoPieceType.HORSE, MovementType.JUMP);
	}
	
	
}
