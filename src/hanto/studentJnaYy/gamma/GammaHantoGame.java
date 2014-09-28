/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentJnaYy.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.common.BaseHantoGame;
import hanto.studentJnaYy.common.moveControllers.MovementType;
import hanto.studentJnaYy.common.moveControllers.Walking;


/**
 * This is the implementation of the BetaHantoGame, where each player 
 * can only place 1 butterfly and 5 sparrows.
 * 
 * @author Joshua and Yan
 */
public class GammaHantoGame extends BaseHantoGame
{
	protected final static int MAX_TURN_COUNT = 20;
	
	// Moves where the butterfly doesn't have to be placed on the board.
	protected final static int OPTIONAL_BUTTERFLY_TURNS = 3;
	
	private final static int MAX_BUTTERFLY_COUNT = 1;
	private final static int MAX_SPARROW_COUNT = 5;
	
	
	/**
	 * Creates a BetaHanto Game instance
	 * @param movesFirst the color of the first piece to be played.
	 */
	public GammaHantoGame(HantoPlayerColor movesFirst){
		super(movesFirst, MAX_TURN_COUNT, OPTIONAL_BUTTERFLY_TURNS);
	}

	/**
	 * Checks to see if the suggested move is valid.
	 * @param pieceType - the HantoPieceType of the given piece.
	 * @param toCoordinate - the desired HantoCoordinate for the given piece.
	 * @return true if the move can be done
	 * @throws HantoException 
	 */
	@Override
	protected void checkMoveValidityPrior(HantoPieceType pieceType, HantoCoordinate fromCoordinate, HantoCoordinate toCoordinate) throws HantoException {
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
		moveController.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.WALK);
		moveController.setMovementForType(HantoPieceType.SPARROW, MovementType.WALK);
	}
}
