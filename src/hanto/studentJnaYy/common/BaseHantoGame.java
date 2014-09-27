/**
 * 
 */
package hanto.studentJnaYy.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

/**
 * @author Joshua
 *
 */
public abstract class BaseHantoGame implements HantoGame {

	protected PieceFactory pieceFactory = PieceFactory.getInstance();
	protected PieceAvailabilityCounter pieceCounter = new PieceAvailabilityCounter();
	
	protected HantoPlayerColor currentColor;
	protected String exceptionMessage;
	
	protected HantoBoard board;
	
	protected static final HantoCoordinate OFF_BOARD_LOCATION = null;
	
	/**
	 * Creates an abstract version of a Hanto Game
	 * @param movesFirst the player who moves first in the game
	 * @param maxMoveCount the maximum amount of turns in the game
	 * @param optionalButterflyMoves the number of moves where placing a butterfly is optional
	 */
	protected BaseHantoGame(HantoPlayerColor movesFirst, int maxMoveCount, int optionalButterflyMoves) {
		currentColor = movesFirst;
		board = new HantoBoard(maxMoveCount, optionalButterflyMoves, movesFirst);
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
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		
		checkMoveValidityPrior(pieceType, from, to);
		
		finalizeMove(pieceType, from, to);

		checkMoveValidityAfter();
		
		return board.getGameStatus();
	}
	

	/**
	 * Checks the state of the game after a move for any exceptions.
	 * @throws HantoException
	 */
	protected void checkMoveValidityAfter() throws HantoException {
		board.checkPieceConnectivity();
	}


	/**
	 * Checks to see if the suggested move is valid.
	 * @param pieceType - the HantoPieceType of the given piece.
	 * @param fromCoordinate - the coordinate the piece is moving from
	 * @param toCoordinate - the desired HantoCoordinate for the given piece.
	 * @throws HantoException 
	 */
	protected void checkMoveValidityPrior(HantoPieceType pieceType, HantoCoordinate fromCoordinate, 
			HantoCoordinate toCoordinate) throws HantoException {
		if(isFromOffTheBoard(fromCoordinate)){
			pieceCounter.checkPieceAvailability(pieceType, currentColor);
		}
		else{
			board.isPieceHere(fromCoordinate, pieceType, currentColor);
			board.checkMovement(fromCoordinate, toCoordinate);
		}	
		
		board.checkMoveValidity(toCoordinate, pieceType, currentColor);
	}
	
	/**
	 * finishes the verified move - put the piece and coordinate onto the board, 
	 * increments the move count, and switches the current color.
	 * @param type The Piece type being moved
	 * @param toCoordinate The coordinate to place the piece at
	 * @param fromCoordinate the coordinate the piece is moving from (null if from off the board)
	 * @throws HantoException
	 */
	protected void finalizeMove(HantoPieceType type, HantoCoordinate fromCoordinate, HantoCoordinate toCoordinate) 
			throws HantoException {
		
		if(isFromOffTheBoard(fromCoordinate)){

			pieceCounter.utilizePiece(type, currentColor);
		}
		else{
			board.removePiece(fromCoordinate);
		}
			
		board.addPiece(toCoordinate, pieceFactory.makeGamePiece(type, currentColor));
		
		switchCurrentColor();
	}


	/**
	 * Checks to see if the piece given is a new piece (from null)
	 * @param fromCoordinate - the coordinate of the piece to check
	 * @return true if the piece is from off the board (null)
	 */
	protected boolean isFromOffTheBoard(HantoCoordinate fromCoordinate) {
		return fromCoordinate == OFF_BOARD_LOCATION;
	}

	/**
	 * Switches the current player color to the next available one.
	 */
	protected void switchCurrentColor() {
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
	
	/**
	 * Is used to set the piece counts for each game.
	 * It is abstract, assuming that each game has a different piece set.
	 */
	protected abstract void initializePieceCounts();
	
}
