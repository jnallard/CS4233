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

import java.util.List;

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
			HantoPieceMap board) throws HantoException {
		
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

}
