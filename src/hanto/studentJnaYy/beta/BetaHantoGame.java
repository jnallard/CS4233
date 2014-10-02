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

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.common.BaseHantoGame;
import hanto.studentJnaYy.common.moveControllers.MovementType;


/**
 * This is the implementation of the BetaHantoGame, where each player 
 * can only place 1 butterfly and 5 sparrows.
 * 
 * @author Joshua and Yan
 */
public class BetaHantoGame extends BaseHantoGame
{
	private static final int MAX_TURN_COUNT = 6;
	
	// Moves where the butterfly doesn't have to be placed on the board.
	private static final int OPTIONAL_BUTTERFLY_TURNS = 3;
	
	private static final int MAX_BUTTERFLY_COUNT = 1;
	private static final int MAX_SPARROW_COUNT = 5;
	
	
	/**
	 * Creates a BetaHanto Game instance
	 * @param movesFirst the color of the first piece to be played.
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst){
		super(movesFirst, MAX_TURN_COUNT, OPTIONAL_BUTTERFLY_TURNS);
		PiecePlacementOwnColorExceptionTurns = MAX_TURN_COUNT;
	}
	
	/**
	 * Initializes the amount of pieces that each player can place and their movement styles.
	 */
	protected void initializePieceSet(){
		pieceCounter.initializePieceCount(HantoPieceType.BUTTERFLY, MAX_BUTTERFLY_COUNT);
		pieceCounter.initializePieceCount(HantoPieceType.SPARROW, MAX_SPARROW_COUNT);
		moveController.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.NO_MOVEMENT);
		moveController.setMovementForType(HantoPieceType.SPARROW, MovementType.NO_MOVEMENT);
	}
}
