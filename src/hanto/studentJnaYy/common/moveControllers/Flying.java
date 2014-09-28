package hanto.studentJnaYy.common.moveControllers;

import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.studentJnaYy.common.GameCoordinate;

import java.util.Map;

public class Flying implements Movement {
	
	public Flying(){
	}
	
	@Override
	public void checkMovement(GameCoordinate to, GameCoordinate from,
			Map<GameCoordinate, HantoPiece> board) throws HantoException {
		return;
	}

}
