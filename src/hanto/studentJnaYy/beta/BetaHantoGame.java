/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentJnaYy.beta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hanto.common.*;
import hanto.studentJnaYy.common.ButterflyPiece;
import hanto.studentJnaYy.common.GameCoordinate;
import hanto.studentJnaYy.common.PieceAvailabilityCounter;
import hanto.studentJnaYy.common.PieceFactory;


/**
 * This is the implementation of the BetaHantoGame, where each player 
 * can only place 1 butterfly and 5 sparrows.
 * 
 * @author Joshua and Yan
 */
public class BetaHantoGame implements HantoGame
{
	private static final int MaxMoveCount = 12;
	private final int MaxButterflyCount = 1;
	private final int MaxSparrowCount = 5;
	
	private PieceFactory pieceFactory = PieceFactory.getInstance();
	private PieceAvailabilityCounter pieceCounter = new PieceAvailabilityCounter();
	
	private HantoPlayerColor currentColor;
	private int moveCount = 0;
	Map<HantoCoordinate, HantoPiece> board = new HashMap<HantoCoordinate, HantoPiece>();
	private String exceptionMessage;
	
	
	/**
	 * Creates a BetaHanto Game instance
	 * @param movesFirst the color of the first piece to be played.
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst){
		currentColor = movesFirst;
		initializePieceCounts();
	}
	
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
		
		if(checkMoveValidity(pieceType, to)){
			throw new HantoException(exceptionMessage);
		}
		
		MoveResult moveResult = MoveResult.OK;
		if(moveCount == 1){
			moveResult = MoveResult.DRAW;
		}
			
		finalizeMove(pieceType, to);
		return moveResult;
	}

	/**
	 * @param pieceType
	 * @param toCoordinate
	 * @return
	 */
	private boolean checkMoveValidity(HantoPieceType pieceType, HantoCoordinate toCoordinate) {
		boolean isPieceAvailable = pieceCounter.isPieceAvailable(pieceType, currentColor);
		if(isPieceAvailable){
			exceptionMessage = "The " + pieceType + " is not currently available.";
		}
		
		return !isMoveValid(toCoordinate) || isGameOver() || !isPieceAvailable;
	}
	
	/**
	 * Initializes the amount of pieces that each player can place.
	 */
	private void initializePieceCounts(){
		pieceCounter.initializePieceCount(HantoPieceType.BUTTERFLY, MaxButterflyCount);
		pieceCounter.initializePieceCount(HantoPieceType.SPARROW, MaxSparrowCount);
	}

	/**
	 * finishes the verified move - put the piece and coordinate onto the board, 
	 * increments the move count, and switches the current color.
	 * @param toCoordinate The coordinate to place the butterfly at
	 */
	private void finalizeMove(HantoPieceType type, HantoCoordinate toCoordinate) {
		board.put(toCoordinate, pieceFactory.makeGamePiece(type, currentColor));
		moveCount++;
		switchCurrentColor();
	}
	
	/**
	 * Checks to see if the move is valid 
	 * A move is considered valid if the first piece is placed at (0,0)
	 * And if the next piece is adjacent to it.
	 * @param to The location the piece is trying to move to.
	 * @return true if the move is valid
	 */
	private boolean isMoveValid(HantoCoordinate to) {
		boolean isValid;
		if(moveCount == 0){
			isValid = to.equals(new GameCoordinate(0, 0));
		}
		else{
			isValid = isAdjacent(new GameCoordinate(0, 0), to);
		}
		
		if(!isValid){
			exceptionMessage = "The move was not valid - please try again.";
		}
			
		return isValid;
	}

	private boolean isGameOver(){
		boolean isOver = moveCount >= MaxMoveCount;
		if(isOver){
			exceptionMessage = "The game is over - moves are no longer allowed.";
		}
		return isOver;
	}
	/**
	 * Checks to see if the two pieces are adjacent on a hexagonal board, using this
	 * implementation "http://www.vbforums.com/showthread.php?663283-Hexagonal-coordinate-system"
	 * @param firstCoordinate the first coordinate to check for
	 * @param secondCoordinate the second coordinate to check for
	 * @return true if the second piece is considered adjacent to the first piece.
	 */
	private boolean isAdjacent(GameCoordinate firstCoordinate, HantoCoordinate secondCoordinate) {
		boolean areCoordsAdjacent;
		int xDifference = firstCoordinate.getX() - secondCoordinate.getX();
		int yDifference = firstCoordinate.getY() - secondCoordinate.getY();
		switch(xDifference){
			case 0:
				areCoordsAdjacent = Math.abs(yDifference) == 1;
				break;
			case 1:
				areCoordsAdjacent = yDifference == 0 || yDifference == -1;
				break;
			case -1:
				areCoordsAdjacent = yDifference == 0 || yDifference == 1;
				break;
			default:
				areCoordsAdjacent = false;
				break;
		}
		return areCoordsAdjacent;
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
		return board.get(where);
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
		Iterator<Entry<HantoCoordinate, HantoPiece>> it = board.entrySet().iterator();
		while(it.hasNext()){
			Entry<HantoCoordinate, HantoPiece> entry = it.next();
			HantoCoordinate coord = entry.getKey();
			HantoPiece piece = entry.getValue();
			printedBoard += "(" + coord.getX() + "," + coord.getY() + ") " 
					+ piece.getColor() + " " + piece.getType() + "\n";
		}
		return printedBoard;
	}
}
