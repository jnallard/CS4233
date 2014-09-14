/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentJnaYy.alpha;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import hanto.common.*;
import hanto.studentJnaYy.common.ButterflyPiece;
import hanto.studentJnaYy.common.GameCoordinate;


/**
 * This is the implementation of the AlphaHantoGame, where only two Butterfly pieces can be placed
 * adjacently on a board, before the game is over.
 * 
 * @author Joshua and Yan
 */
public class AlphaHantoGame implements HantoGame
{
	private static final GameCoordinate GAME_ORIGIN_COORDINATE = new GameCoordinate(0, 0);
	private static final int MAX_MOVE_COUNT = 2;
	private HantoPlayerColor currentColor = HantoPlayerColor.BLUE;
	private int moveCount = 0;
	Map<GameCoordinate, HantoPiece> board = new HashMap<GameCoordinate, HantoPiece>();
	private String exceptionMessage;
	
	/**
	 * This method executes a move in the game. It is called for every move that must be
	 * made.
	 * 
	 * @param pieceType
	 *            the piece type that is being moved
	 * @param from
	 *            the coordinate where the piece begins. If the coordinate is null, then
	 *            the piece begins off the board (that is, it is placed on the board in
	 *            this move).
	 * @param to
	 *            the coordinated where the piece is after the move has been made.
	 * @return the result of the move
	 * @throws HantoException
	 *             if there are any problems in making the move (such as specifying a
	 *             coordinate that does not have the appropriate piece, or the color of
	 *             the piece is not the color of the player who is moving.
	 */
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException{
		
		if(from != null){
			exceptionMessage = "Movement of pieces is not supported.";
			throw new HantoException(exceptionMessage);
		}
		
		GameCoordinate ourToCoordinate = new GameCoordinate(to);
		
		if(!isValidPiece(pieceType) || !isMoveValid(ourToCoordinate) || isGameOver()){
			throw new HantoException(exceptionMessage);
		}
		
		MoveResult moveResult = MoveResult.OK;
		if(moveCount + 1 == MAX_MOVE_COUNT){
			moveResult = MoveResult.DRAW;
		}
			
		finalizeMove(ourToCoordinate);
		return moveResult;
	}

	/**
	 * finishes the verified move - put the piece and coordinate onto the board, 
	 * increments the move count, and switches the current color.
	 * @param toCoordinate The coordinate to place the butterfly at
	 */
	private void finalizeMove(GameCoordinate toCoordinate) {
		board.put(toCoordinate, new ButterflyPiece(currentColor));
		moveCount++;
		switchCurrentColor();
	}
	
	/**
	 * Checks to see if the given piece is valid for the game.
	 * @param pieceType the type to check for
	 * @return true if the piece type is BUTTERFLY
	 */
	private boolean isValidPiece(HantoPieceType pieceType) {
		
		boolean isValid = pieceType == HantoPieceType.BUTTERFLY;
		
		if(!isValid){
			exceptionMessage = "The particular piece (" + pieceType + ") is not valid.";
		}
		return isValid;
	}

	/**
	 * Checks to see if the move is valid 
	 * A move is considered valid if the first piece is placed at (0,0)
	 * And if the next piece is adjacent to it.
	 * @param to The location the piece is trying to move to.
	 * @return true if the move is valid
	 */
	private boolean isMoveValid(GameCoordinate to) {
		boolean isValid;
		if(moveCount == 0){
			isValid = GAME_ORIGIN_COORDINATE.equals(to);
		}
		else{
			isValid = GAME_ORIGIN_COORDINATE.isAdjacent(to);
		}
		
		if(!isValid){
			exceptionMessage = "The move was not valid - please try again.";
		}
			
		return isValid;
	}

	/**
	 * Checks to see if the game is over (max move count is passed)
	 * @return true if the game has ended.
	 */
	private boolean isGameOver(){
		boolean isOver = moveCount >= MAX_MOVE_COUNT;
		if(isOver){
			exceptionMessage = "The game is over - moves are no longer allowed.";
		}
		return isOver;
	}

	/**
	 * Switches the current player color to the next available one.
	 */
	private void switchCurrentColor() {
		switch(currentColor){
			case BLUE: 
				currentColor = HantoPlayerColor.RED;
				break;
			case RED: 
				currentColor = HantoPlayerColor.BLUE;
				break;
		}
	}

	/**
	 * @param where the coordinate to query
	 * @return the piece at the specified coordinate or null if there is no 
	 * 	piece at that position
	 */
	public HantoPiece getPieceAt(HantoCoordinate where){
		GameCoordinate newWhere = new GameCoordinate(where);
		return board.get(newWhere);
	}

	/**
	 * Returns the board in this format: 
	 * "(x1, y1) Color1 Piece1
	 * (x2, y2) Color2 Piece2"
	 * (There is no particular order when printing)
	 * @return a printable representation of the board.
	 */
	public String getPrintableBoard(){
		String printedBoard = "";
		Iterator<Entry<GameCoordinate, HantoPiece>> it = board.entrySet().iterator();
		while(it.hasNext()){
			Entry<GameCoordinate, HantoPiece> entry = it.next();
			GameCoordinate coord = entry.getKey();
			HantoPiece piece = entry.getValue();
			printedBoard += "(" + coord.getX() + "," + coord.getY() + ") " 
					+ piece.getColor() + " " + piece.getType() + "\n";
		}
		return printedBoard;
	}
}
