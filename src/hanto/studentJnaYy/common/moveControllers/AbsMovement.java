/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.common.moveControllers;

import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.studentJnaYy.common.GameCoordinate;
import hanto.studentJnaYy.common.HantoPieceHashMap;
import hanto.studentJnaYy.common.HantoPieceLocationController;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a base class for the Movements that need it. 
 * Creates a template for getting all of the possible moves, which
 * is varied by all of the subclasses by implementing their own 
 * checkMovement function
 * @author Joshua and Yan
 *
 */
public abstract class AbsMovement implements Movement {

	/**
	 * Checks to see if the move for a certain piece is valid given the state of the board.
	 * @param from the start coordinate of the piece
	 * @param to the destination of the piece
	 * @param board the current hashmap representing the pieces on the board.
	 * @throws HantoException if the move is not valid
	 */
	public abstract void checkMovement(GameCoordinate to, GameCoordinate from,
			HantoPieceLocationController board) throws HantoException;

	/**
	 * Gets a list of all possible moves that a movement can make from a certain position.
	 * @param from the location to make movements from
	 * @param board the board containing the current piece configuration
	 * @return the list of all possible moves
	 */
	public List<GameCoordinate> getPossibleMoves(GameCoordinate from,
			HantoPieceLocationController board) {
		List<GameCoordinate> newCoords = board.getAllOpenCoordinates();
		List<GameCoordinate> moves = new ArrayList<GameCoordinate>();
		for (GameCoordinate coord : newCoords) {
			try {
				HantoPieceLocationController newBoard = new HantoPieceHashMap(
						board);
				HantoPiece piece = newBoard.getPieceAt(from);
				checkMovement(coord, from, newBoard);
				newBoard.removePiece(from);
				newBoard.addPiece(coord, piece);
				if(newBoard.arePiecesConnected()){
					moves.add(coord);
				}
			} catch (HantoException e) {
				continue;
			}
			
		}
		return moves;
	}

	
}
