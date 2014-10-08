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
import hanto.common.HantoPiece;
import hanto.studentJnaYy.common.GameCoordinate;

import java.util.Map;

/**
 * This class is used for represent a flying movement, which checks to
 * see if a flying move is valid.
 * @author Joshua and Yan
 *
 */
public class Flying extends AbsMovement implements Movement {
	private int maxDistance;
	
	/**
	 * Creates a new instance of a Flying Movement, with a max Distance
	 * @param maxDistance the maximum amount of spaces a piece can move.
	 */
	public Flying(int maxDistance) {
		this.maxDistance = maxDistance;
	}
	
	/**
	 * Checks to see if the move for a certain piece is valid given the state of the board.
	 * @param from the start coordinate of the piece
	 * @param to the destination of the piece
	 * @param board the current hashmap representing the pieces on the board.
	 * @throws HantoException if the move is not valid
	 */
	@Override
	public void checkMovement(GameCoordinate to, GameCoordinate from,
			Map<GameCoordinate, HantoPiece> board) throws HantoException {
		int distance = from.getDistance(to);
		if(distance > maxDistance){
			throw new HantoException("You flew further than the max distance set.");
		}
	}

//	@Override
//	public List<GameCoordinate> getPossibleMoves(GameCoordinate from,
//			Map<GameCoordinate, HantoPiece> board) {
//		List<GameCoordinate> newCoords = BoardHelperClass.getAllOpenCoordinates(board);
//		List<GameCoordinate> moves = new ArrayList<GameCoordinate>();
//		for (GameCoordinate coord : newCoords) {
//			try {
//				Map<GameCoordinate, HantoPiece> newBoard = new HashMap<GameCoordinate, HantoPiece>(
//						board);
//				HantoPiece piece = newBoard.get(from);
//				checkMovement(coord, from, newBoard);
//				newBoard.remove(from);
//				newBoard.put(coord, piece);
//				BoardHelperClass.checkPieceConnectivity(newBoard);
//				moves.add(coord);
//			} catch (HantoException e) {
//				//The piece/move is not valid.
//			}
//			
//		}
//		return moves;
//	}


}
