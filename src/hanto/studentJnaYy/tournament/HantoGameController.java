package hanto.studentJnaYy.tournament;

import hanto.common.HantoGame;
import hanto.tournament.HantoMoveRecord;

public interface HantoGameController extends HantoGame {
	
	HantoMoveRecord getBestMove();

}
