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
	
	protected BaseHantoGame(HantoPlayerColor movesFirst, int maxMoveCount, int optionalButterflyMoves) {
		currentColor = movesFirst;
		board = new HantoBoard(maxMoveCount, optionalButterflyMoves, movesFirst);
		initializePieceCounts();
	}
	
	
	/* (non-Javadoc)
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		if(!checkMoveValidity(pieceType, from, to)){
			throw new HantoException(exceptionMessage);
		}
		
		return finalizeMove(pieceType, from, to);
	}
	
	/**
	 * finishes the verified move - put the piece and coordinate onto the board, 
	 * increments the move count, and switches the current color.
	 * @param toCoordinate The coordinate to place the butterfly at
	 */
	protected MoveResult finalizeMove(HantoPieceType type, HantoCoordinate fromCoordinate, HantoCoordinate toCoordinate) 
			throws HantoException {
		
		if(fromCoordinate == OFF_BOARD_LOCATION){

			pieceCounter.utilizePiece(type, currentColor);
		}
		else{
			
		}
			
		MoveResult result = board.addPiece(toCoordinate, 
				pieceFactory.makeGamePiece(type, currentColor));
		
		switchCurrentColor();
		return result;
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
	
	protected abstract void initializePieceCounts();
	protected abstract boolean checkMoveValidity(HantoPieceType pieceType, 
			HantoCoordinate fromCoordinate, HantoCoordinate toCoordinate);
}
