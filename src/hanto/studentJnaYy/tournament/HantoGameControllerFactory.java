package hanto.studentJnaYy.tournament;

import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.epsilon.EpsilonHantoGameController;

public class HantoGameControllerFactory {
	
	private static HantoGameControllerFactory instance = new HantoGameControllerFactory();
	
	public static HantoGameControllerFactory getInstance(){
		return instance;
	}
	
	public HantoGameController getGameController(HantoGameID version, HantoPlayerColor movesFirst, HantoPlayerColor controllerColor){
		HantoGameController controller = null;
		switch(version){
		case EPSILON_HANTO:
			controller = new EpsilonHantoGameController(movesFirst, controllerColor);
			break;
		default:
			break;
		
		}
		return controller;
	}

}
