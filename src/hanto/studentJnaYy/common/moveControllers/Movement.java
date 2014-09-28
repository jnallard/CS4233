package hanto.studentJnaYy.common.moveControllers;

import java.util.Map;

import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.studentJnaYy.common.GameCoordinate;

public interface Movement {

	void checkMovement(GameCoordinate to, GameCoordinate from, 
			Map<GameCoordinate, HantoPiece> board) throws HantoException;
}
