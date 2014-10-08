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

import java.util.List;
import java.util.Map;

/**
 * This class is used for represent a flying movement, which checks to
 * see if a flying move is valid.
 * @author Joshua and Yan
 *
 */
public class Jumping extends AbsMovement implements Movement {
	
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
		
		if(!to.isStraightLine(from)){
			throw new HantoException("The piece tried to jump not in a straight line.");
		}
		
		List<GameCoordinate> coordsBetween = from.getStraightLineCoordsBetween(to);
		
		if(coordsBetween.size() <= 0){
			throw new HantoException("The piece did not jump over any pieces.");
		}
		
		boolean isContinuous = true;
		for(GameCoordinate coord: coordsBetween){
			isContinuous &= board.containsKey(coord);
		}
		
		if(!isContinuous){
			throw new HantoException("The line jumped did not contain pieces in every location.");
		}
	}
//
//	@Override
//	public List<GameCoordinate> getPossibleMoves(GameCoordinate from,
//			Map<GameCoordinate, HantoPiece> board) {
//		List<GameCoordinate> newCoords = getStraightLineMoves(from, board);
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
//	
//	private List<GameCoordinate> getStraightLineMoves(GameCoordinate from, 
//			Map<GameCoordinate, HantoPiece> board){
//		List<GameCoordinate> adjacentCoords = from.getAdjacentCoordinates();
//		List<GameCoordinate> moves = new ArrayList<GameCoordinate>();
//		for(GameCoordinate coord: adjacentCoords){
//			if(board.containsKey(coord)){
//				GameCoordinate newCoord = getNextCoordAvailableInLine(from, coord, board);
//				moves.add(newCoord);
//			}
//		}
//		return moves;
//	}
//
//	private GameCoordinate getNextCoordAvailableInLine(GameCoordinate from,
//			GameCoordinate coord, Map<GameCoordinate, HantoPiece> board) {
//		int addX = coord.getX() - from.getX();
//		int addY = coord.getY() - from.getY();
//		GameCoordinate newCoord = coord;
//		if(board.containsKey(coord)){
//			newCoord = getNextCoordAvailableInLine(coord, 
//					new GameCoordinate(coord.getX() + addX, coord.getY() + addY), board);
//		}
//		return newCoord;
//	}

}
