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
import hanto.common.HantoPieceType;
import hanto.studentJnaYy.common.GameCoordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used for keeping track of what movement is associated with piece types
 * @author Joshua and Yan
 *
 */
public class MoveHandler {
	
	Map<HantoPieceType, Movement> movements = new HashMap<HantoPieceType, Movement>();
	
	/**
	 * Checks to see if the move for a certain piece is valid given the state of the board.
	 * @param from the start coordinate of the piece
	 * @param to the destination of the piece
	 * @param board the current hashmap representing the pieces on the board.
	 * @param type the piece type, used to find the movement associated.
	 * @throws HantoException
	 */
	public void checkMovement(GameCoordinate from, GameCoordinate to, Map<GameCoordinate, 
			HantoPiece> board, HantoPieceType type) throws HantoException{
		Movement movement = getMovementForType(type);
		movement.checkMovement(from, to, board);
	}
	
	/**
	 * Sets the movement type for the piece.
	 * @param pieceType the piece type to define a movement for
	 * @param movementType the enumeration representing the movement wanted
	 */
	public void setMovementForType(HantoPieceType pieceType, MovementType movementType){
		setMovementForType(pieceType, movementType, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets the movement type for the piece.
	 * @param pieceType the piece type to define a movement for
	 * @param movementType the enumeration representing the movement wanted
	 */
	public void setMovementForType(HantoPieceType pieceType, MovementType movementType, int max){
		Movement moveMaker = null;
		switch(movementType){
			case WALK: 
				moveMaker = new Walking();
				break;
			case FLY:
				moveMaker = new Flying(max);
				break;
			case NO_MOVEMENT:
				moveMaker = new NoMovement();
				break;
			case JUMP:
				moveMaker = new Jumping();
				break;
			default:
				break;
		}
		setMovementObjectForType(pieceType, moveMaker);
	}
	
	/**
	 * Sets the movement for the piece type, given the actual movement object
	 * @param type the piece type to set the movement for
	 * @param moveMaker the movement object for the piece
	 */
	private void setMovementObjectForType(HantoPieceType type, Movement moveMaker){
		movements.put(type, moveMaker);
	}
	
	/**
	 * Gets the movement specified for each piece
	 * @param type the piece type to search for
	 * @return the movement object represented for each piece type
	 * @throws HantoException
	 */
	private Movement getMovementForType(HantoPieceType type) throws HantoException{
		Movement moveMaker = movements.get(type);
		if(moveMaker == null){
			throw new HantoException("There is no movement defined for this piece.");
		}
		return moveMaker;
	}
	
	/**
	 * Returns the list of possible move pieces for a piece type, from a location on the board
	 * @param type the piece type, used to find the Movement
	 * @param from the location the piece is at
	 * @param board the configuration of pieces already placed
	 * @return the list of possible coordinates it can move to.
	 */
	public List<GameCoordinate> getPossibleCoordinates(HantoPieceType type, GameCoordinate from, 
			Map<GameCoordinate, HantoPiece> board){
		Movement moveMaker = movements.get(type);
		List<GameCoordinate> possibleCoords = new ArrayList<GameCoordinate>();
		if(moveMaker != null){
			possibleCoords = moveMaker.getPossibleMoves(from, board);
		}
		return possibleCoords;
	}
}
