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
import hanto.studentJnaYy.common.HantoPieceMap;

/**
 * This class is used for represent a walking movement, which checks to see if a
 * walking move is valid.
 * 
 * @author Joshua and Yan
 *
 */
public class Walking extends AbsMovement implements Movement {

	/**
	 * Checks to see if the move for a certain piece is valid given the state of
	 * the board.
	 * 
	 * @param from
	 *            the start coordinate of the piece
	 * @param to
	 *            the destination of the piece
	 * @param board
	 *            the current hashmap representing the pieces on the board.
	 * @throws HantoException
	 *             if the move is not valid
	 */
	@Override
	public void checkMovement(GameCoordinate to, GameCoordinate from,
			HantoPieceMap board) throws HantoException {

		// Assuming walking is only one step for now - darn TDD~
		if (!from.isAdjacent(to)) {
			throw new HantoException("The piece was walking more than one hex.");
		}

		boolean arePiecesInNeigboringCoordinates = true;
		for (GameCoordinate commonNeighbor : from
				.getCommonAdjacentCoordinates(to)) {
			if (!board.containsKey(commonNeighbor)) {
				arePiecesInNeigboringCoordinates = false;
			}
		}
		if (arePiecesInNeigboringCoordinates) {
			throw new HantoException(
					"The piece cannot walk, because slding is prohibited.");
		}
	}

}
