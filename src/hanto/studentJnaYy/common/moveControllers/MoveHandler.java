/**
 * 
 */
package hanto.studentJnaYy.common.moveControllers;

import java.util.HashMap;
import java.util.Map;

import com.sun.xml.internal.ws.handler.HandlerException;

import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.studentJnaYy.common.GameCoordinate;

/**
 * @author Joshua
 *
 */
public class MoveHandler {
	
	Map<HantoPieceType, Movement> movements = new HashMap<HantoPieceType, Movement>();
	public MoveHandler(){
		
	}
	
	public void checkMovement(GameCoordinate from, GameCoordinate to, Map<GameCoordinate, 
			HantoPiece> board, HantoPieceType type) throws HantoException{
		getMovementForType(type).checkMovement(from, to, board);
	}

	private Movement getMovementForType(HantoPieceType type) throws HantoException{
		Movement moveMaker = movements.get(type);
		if(moveMaker == null){
			throw new HantoException("There is no movement defined for this piece.");
		}
		return moveMaker;
	}
	
	private void setMovementForType(HantoPieceType type, Movement moveMaker){
		movements.put(type, moveMaker);
	}
	
	public void setMovementForType(HantoPieceType pieceType, MovementType movementType){
		Movement moveMaker = null;
		switch(movementType){
			case WALK: 
				moveMaker = new Walking();
				break;
			case FLY:
				moveMaker = new Flying();
				break;
			default:
				break;
		}
		setMovementForType(pieceType, moveMaker);
	}
}
