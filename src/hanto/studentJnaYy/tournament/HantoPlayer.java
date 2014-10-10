package hanto.studentJnaYy.tournament;

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.epsilon.EpsilonHantoGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

public class HantoPlayer implements HantoGamePlayer {

	private static final HantoPlayerColor RED = HantoPlayerColor.RED;
	private static final HantoPlayerColor BLUE = HantoPlayerColor.BLUE;
	private EpsilonHantoGame game;

	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst) {
		HantoPlayerColor startColor = myColor;
		if(doIMoveFirst){
			startColor = myColor;
		}
		else{
			startColor = (myColor == BLUE) ? RED : BLUE;
		}
		
		game = new EpsilonHantoGame(startColor);
	}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		try{
			game.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
		} catch (HantoException e){
			//This should never happen, assuming we've implemented all of the rules correctly.
			System.out.println("The opponent's move failed in our game..");
		}
		return null;
	}

}
