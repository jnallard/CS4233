/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.common.moveControllers;

import hanto.common.HantoException;
import hanto.studentJnaYy.common.GameCoordinate;
import hanto.studentJnaYy.common.HantoPieceLocationController;

/**
 * This class is used for represent a piece that has no movement.
 * @author Joshua and Yan
 *
 */
public class NoMovement extends AbsMovement implements Movement {
	
	/**
	 * Checks to see if the move for a certain piece is valid given the state of the board.
	 * @param from the start coordinate of the piece
	 * @param to the destination of the piece
	 * @param board the current hashmap representing the pieces on the board.
	 * @throws HantoException when a move occurs, the exception is thrown
	 */
	@Override
	public void checkMovement(GameCoordinate to, GameCoordinate from,
			HantoPieceLocationController board) throws HantoException {
		throw new HantoException("This piece is not allowed to move.");
	}

}
