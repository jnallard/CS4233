/**
 * 
 */
package hanto.studentjnayy.gamma;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.studentjnayy.common.HantoTestBoard;
import hanto.studentjnayy.gamma.GammaHantoGame;
import common.HantoTestGame;

/**
 * @author Joshua
 *
 */
public class GammaHantoTestGame extends GammaHantoGame implements HantoTestGame{

	
	public GammaHantoTestGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		board = new HantoTestBoard(MAX_TURN_COUNT, OPTIONAL_BUTTERFLY_TURNS, movesFirst, moveController);
	}

	/* (non-Javadoc)
	 * @see common.HantoTestGame#initializeBoard(common.HantoTestGame.PieceLocationPair[])
	 */
	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
		board.clear();
		for(PieceLocationPair piece : initialPieces){
			try{
				getBoardAsTestBoard().forcePiecePlacement(piece.location, 
				pieceFactory.makeGamePiece(piece.pieceType, piece.player));
				pieceCounter.utilizePiece(piece.pieceType, piece.player);
			}
			catch (HantoException e){
				System.out.println(e.getMessage());
			}
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

	/**
	 * Returns our HantoBoard as a test board, which we set in the constructor
	 * @return
	 */
	private HantoTestBoard getBoardAsTestBoard(){
		return ((HantoTestBoard)board);
	}
}
