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

import java.util.Map;

import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.studentJnaYy.common.GameCoordinate;


/**
 * This interface is used for represent a movement; classes
 * that implement this are used to see if a valid move has been made.
 * @author Joshua and Yan
 *
 */
public interface Movement {

	/**
	 * Checks to see if the move for a certain piece is valid given the state of the board.
	 * @param from the start coordinate of the piece
	 * @param to the destination of the piece
	 * @param board the current hashmap representing the pieces on the board.
	 * @throws HantoException if the move is not valid
	 */
	void checkMovement(GameCoordinate to, GameCoordinate from, 
			Map<GameCoordinate, HantoPiece> board) throws HantoException;
}
