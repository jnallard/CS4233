/**
 * 
 */
package hanto.studentJnaYy.gamma;

import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.common.HantoTestBoard;

import common.HantoTestGame;

/**
 * @author Joshua
 *
 */
public class GammaHantoTestGame extends GammaHantoGame implements HantoTestGame{

	
	public GammaHantoTestGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		board = new HantoTestBoard(MaxTurnCount, OptionalButterflyTurns, movesFirst);
	}

	/* (non-Javadoc)
	 * @see common.HantoTestGame#initializeBoard(common.HantoTestGame.PieceLocationPair[])
	 */
	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
		for(PieceLocationPair piece : initialPieces){
			getBoardAsTestBoard().forcePiecePlacement(piece.location, 
					pieceFactory.makeGamePiece(piece.pieceType, piece.player));
		}

	}

	/* (non-Javadoc)
	 * @see common.HantoTestGame#setTurnNumber(int)
	 */
	@Override
	public void setTurnNumber(int turnNumber) {
		getBoardAsTestBoard().setTurnCount(turnNumber);
	}

	/* (non-Javadoc)
	 * @see common.HantoTestGame#setPlayerMoving(hanto.common.HantoPlayerColor)
	 */
	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		currentColor = player;
	}

	private HantoTestBoard getBoardAsTestBoard(){
		return ((HantoTestBoard)board);
	}
}
