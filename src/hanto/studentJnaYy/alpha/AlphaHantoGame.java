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

import hanto.common.*;
import hanto.studentJnaYy.common.ButterflyPiece;
import hanto.studentJnaYy.common.HantoBoard;


/**
 * This is the implementation of the AlphaHantoGame, where only two Butterfly pieces can be placed
 * adjacently on a board, before the game is over.
 * 
 * @author Joshua and Yan
 */
public class AlphaHantoGame implements HantoGame
{
	private static final int MAX_MOVE_COUNT = 2;
	private static final int OPTIONAL_BUTTERFLY_MOVES = 0;
	private HantoPlayerColor currentColor = HantoPlayerColor.BLUE;
	HantoBoard board = new HantoBoard(MAX_MOVE_COUNT, OPTIONAL_BUTTERFLY_MOVES);
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
		
		if(!checkValidity(pieceType, to)){
			throw new HantoException(exceptionMessage);
		}
			
		MoveResult moveResult = finalizeMove(to);
		return moveResult;
	}

	/**
	 * Checks to see if the piece and move are valid
	 * @param pieceType the piece to check for
	 * @param to the coordinate to check for
	 * @return true if the piece and move are valid, false otherwise
	 */
	private boolean checkValidity(HantoPieceType pieceType, HantoCoordinate to) {
		
		boolean isValidPiece = isValidPiece(pieceType);
		boolean isValidMove = board.isMoveValid(to, pieceType, currentColor);
		
		if(!isValidMove){
			exceptionMessage = board.getErrorMessage();
		}
		
		return isValidMove && isValidPiece;
	}

	/**
	 * finishes the verified move - put the piece and coordinate onto the board, 
	 * increments the move count, and switches the current color.
	 * @param toCoordinate The coordinate to place the butterfly at
	 * @throws HantoException 
	 */
	private MoveResult finalizeMove(HantoCoordinate toCoordinate) throws HantoException {
		MoveResult result = board.addPiece(toCoordinate, new ButterflyPiece(currentColor));
		switchCurrentColor();
		return result;
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
		return board.getPieceAt(where);
	}

	/**
	 * Returns the board in this format: 
	 * "(x1, y1) Color1 Piece1
	 * (x2, y2) Color2 Piece2"
	 * (There is no particular order when printing)
	 * @return a printable representation of the board.
	 */
	public String getPrintableBoard(){
		return board.getPrintableBoard();
	}
}
