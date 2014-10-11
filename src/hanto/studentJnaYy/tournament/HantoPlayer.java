package hanto.studentJnaYy.tournament;

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.epsilon.EpsilonHantoGameController;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

public class HantoPlayer implements HantoGamePlayer {

	private static final HantoPlayerColor RED = HantoPlayerColor.RED;
	private static final HantoPlayerColor BLUE = HantoPlayerColor.BLUE;
	private HantoGameController gameController;
	private HantoPlayerColor myColor;

	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst) {
		HantoPlayerColor startColor = myColor;
		this.myColor = myColor;
		if(doIMoveFirst){
			startColor = myColor;
		}
		else{
			startColor = (myColor == BLUE) ? RED : BLUE;
		}
		
		gameController = HantoGameControllerFactory.getInstance().getGameController(version, startColor, myColor);
	}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		HantoMoveRecord move = null;
		try{
			if(opponentsMove != null){
				gameController.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
			}
		} catch (HantoException e){
			e.printStackTrace();
			//This should never happen, assuming we've implemented all of the rules correctly.
			System.out.println("The opponent's move failed in our game..");
		}
		
		try{
			move = gameController.getBestMove();
			System.out.println(myColor + "'s move: " + move.getPiece() + " " + move.getFrom() + " " + move.getTo());

			gameController.makeMove(move.getPiece(), move.getFrom(), move.getTo());
		} catch (HantoException e){
			e.printStackTrace();
			System.out.println(myColor + "'s move failed in its game..");
		}
		return move;
	}

}
