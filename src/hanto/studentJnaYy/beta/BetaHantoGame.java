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

import hanto.common.*;
import hanto.studentJnaYy.common.ButterflyPiece;
import hanto.studentJnaYy.common.GameCoordinate;
import hanto.studentJnaYy.common.HantoBoard;
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
	private final int MaxMoveCount = 12;
	
	// Moves where the butterfly doesn't have to be placed on the board.
	private final int OptionalButterflyMoves = 6;
	
	private final int MaxButterflyCount = 1;
	private final int MaxSparrowCount = 5;
	
	private PieceFactory pieceFactory = PieceFactory.getInstance();
	private PieceAvailabilityCounter pieceCounter = new PieceAvailabilityCounter();
	
	private HantoPlayerColor currentColor;
	private String exceptionMessage;
	
	private HantoBoard board = new HantoBoard(MaxMoveCount, OptionalButterflyMoves);
	
	
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
		
		if(from != null){
			exceptionMessage = "Movement of pieces is not supported.";
			throw new HantoException(exceptionMessage);
		}
		
		GameCoordinate ourToCoordinate = new GameCoordinate(to);
		
		if(!checkMoveValidity(pieceType, ourToCoordinate)){
			throw new HantoException(exceptionMessage);
		}
		
		return finalizeMove(pieceType, ourToCoordinate);
	}

	/**
	 * Checks to see if the suggested move is valid.
	 * @param pieceType - the HantoPieceType of the given piece.
	 * @param toCoordinate - the desired HantoCoordinate for the given piece.
	 * @return true if the move can be done
	 */
	private boolean checkMoveValidity(HantoPieceType pieceType, GameCoordinate toCoordinate) {
		
		boolean isPieceAvailable = pieceCounter.isPieceAvailable(pieceType, currentColor);
		if(isPieceAvailable){
			exceptionMessage = pieceCounter.getErrorMessage();
		}
		
		boolean isMoveValid = board.isMoveValid(toCoordinate, pieceType, currentColor);
		if(isMoveValid){
			exceptionMessage = board.getErrorMessage();
		}
		
		return isMoveValid && isPieceAvailable;
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
	private MoveResult finalizeMove(HantoPieceType type, GameCoordinate toCoordinate) throws HantoException {
		MoveResult result = board.addPiece(toCoordinate, pieceFactory.makeGamePiece(type, currentColor));
		pieceCounter.utilizePiece(type, currentColor);
		switchCurrentColor();
		return result;
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
		return board.getPieceAt(newWhere);
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
