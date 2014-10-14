/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentjnayy.gamma;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentjnayy.common.BaseHantoGame;
import hanto.studentjnayy.common.moveControllers.MovementType;


/**
 * This is the implementation of the GammaHantoGame, where each player 
 * can place 1 butterfly and 5 sparrows, and also move them.
 * 
 * @author Joshua and Yan
 */
public class GammaHantoGame extends BaseHantoGame
{
	protected static final int MAX_TURN_COUNT = 20;
	
	// Moves where the butterfly doesn't have to be placed on the board.
	protected static final int OPTIONAL_BUTTERFLY_TURNS = 3;
	
	private static final int MAX_BUTTERFLY_COUNT = 1;
	private static final int MAX_SPARROW_COUNT = 5;
	
	
	/**
	 * Creates a BetaHanto Game instance
	 * @param movesFirst the color of the first piece to be played.
	 */
	public GammaHantoGame(HantoPlayerColor movesFirst){
		super(movesFirst, MAX_TURN_COUNT, OPTIONAL_BUTTERFLY_TURNS);
	}
	
	/**
	 * Initializes the amount of pieces that each player can place and their movement styles.
	 */
	protected void initializePieceSet(){
		pieceCounter.initializePieceCount(HantoPieceType.BUTTERFLY, MAX_BUTTERFLY_COUNT);
		pieceCounter.initializePieceCount(HantoPieceType.SPARROW, MAX_SPARROW_COUNT);
		moveController.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.WALK);
		moveController.setMovementForType(HantoPieceType.SPARROW, MovementType.WALK);
	}
}
