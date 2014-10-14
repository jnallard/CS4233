package hanto.studentjnayy.tournament;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjnayy.epsilon.EpsilonHantoGame;
import hanto.studentjnayy.tournament.HantoPlayer;
import hanto.tournament.HantoMoveRecord;

public class TestTournament {
	
	private HantoPlayer redPlayer;
	private HantoPlayer bluePlayer;

	public boolean runGame(){
		redPlayer = new HantoPlayer();
		redPlayer.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED, true);
		bluePlayer = new HantoPlayer();
		bluePlayer.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, false);
		HantoGame game = new EpsilonHantoGame(HantoPlayerColor.RED);
		
		try{
			int turnCount = 1;
			MoveResult result = MoveResult.OK;
			HantoMoveRecord move = null;
			while(result == MoveResult.OK && turnCount < 1000){
				move = redPlayer.makeMove(move);
				result = game.makeMove(move.getPiece(), move.getFrom(), move.getTo());
				if(result == MoveResult.OK){
					move = bluePlayer.makeMove(move);
					result = game.makeMove(move.getPiece(), move.getFrom(), move.getTo());
				}
				turnCount++;
			}
			return true;
		}
		catch(HantoException e){
			return false;
		}
	}

	public HantoPlayer getRedPlayer() {
		return redPlayer;
	}

	public HantoPlayer getBluePlayer() {
		return bluePlayer;
	}

}
