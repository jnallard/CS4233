/**
 * 
 */
package hanto.studentJnaYy.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;

/**
 * @author Joshua
 *
 */
public class HantoTestBoard extends HantoBoard {

	public HantoTestBoard(int maxTurnCount, int butterflyOptionalTurns,
			HantoPlayerColor movesFirst) {
		super(maxTurnCount, butterflyOptionalTurns, movesFirst);
	}
	
	public void setTurnCount(int turnCount){
		this.turnCount = turnCount;
	}
	
	public void forcePiecePlacement(HantoCoordinate to, HantoPiece piece){
		board.put(new GameCoordinate(to), piece);
	}

}
