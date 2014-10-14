/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentjnayy.alpha;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentjnayy.common.BaseHantoGame;
import hanto.studentjnayy.common.moveControllers.MovementType;


/**
 * This is the implementation of the AlphaHantoGame, where only two Butterfly pieces can be placed
 * adjacently on a board, before the game is over.
 * 
 * @author Joshua and Yan
 */
public class AlphaHantoGame extends BaseHantoGame
{
	private static final int MAX_TURN_COUNT = 1;
	private static final int OPTIONAL_BUTTERFLY_TURNS = 0;
	private static final int MAX_BUTTERFLY_COUNT = 1;
	
	/**
	 * Creates a new instance of an AlphaHantoGame
	 */
	public AlphaHantoGame() {
		super(HantoPlayerColor.BLUE, MAX_TURN_COUNT, OPTIONAL_BUTTERFLY_TURNS);
		PiecePlacementOwnColorExceptionTurns = MAX_TURN_COUNT;
	}
	
	/**
	 * Initializes the amount of pieces that each player can place and their movement styles.
	 */
	@Override
	protected void initializePieceSet() {
		pieceCounter.initializePieceCount(HantoPieceType.BUTTERFLY, MAX_BUTTERFLY_COUNT);
		moveController.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.NO_MOVEMENT);
	}
}
