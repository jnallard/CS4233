/**
 * 
 */
package hanto.studentJnaYy.delta;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.common.HantoTestBoard;
import common.HantoTestGame;

/**
 * @author Joshua
 *
 */
public class DeltaHantoTestGame extends DeltaHantoGame implements HantoTestGame{

	
	public DeltaHantoTestGame(HantoPlayerColor movesFirst) {
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
