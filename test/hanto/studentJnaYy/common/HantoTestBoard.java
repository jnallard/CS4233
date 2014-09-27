/**
 * 
 */
package hanto.studentJnaYy.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * @author Joshua
 *
 */
public class HantoTestBoard extends HantoBoard {

	/**
	 * Created a test board that has extended features beyond the base hanto board
	 * @param maxTurnCount the the maximum number of turns per game, 1 based.
	 * @param butterflyOptionalTurns the number of turns where butterfly placement is optional
	 * @param movesFirst the player color for who moves first
	 */
	public HantoTestBoard(int maxTurnCount, int butterflyOptionalTurns,
			HantoPlayerColor movesFirst) {
		super(maxTurnCount, butterflyOptionalTurns, movesFirst);
	}
	
	/**
	 * Sets the turn count for the current board
	 * @param turnCount the turn count (1 based) for the current game
	 */
	public void setTurnCount(int turnCount){
		this.turnCount = turnCount;
	}
	
	/**
	 * Forces a movement onto the board without error checking.
	 * @param to the coordinate to place the piece.
	 * @param piece the piece to place
	 */
	public void forcePiecePlacement(HantoCoordinate to, HantoPiece piece){
		GameCoordinate toCoord = new GameCoordinate(to);
		board.put(toCoord, piece);
		if(piece.getType() == HantoPieceType.BUTTERFLY){
			assignButterflyCoord(toCoord, piece.getColor());
		}
	}

}
