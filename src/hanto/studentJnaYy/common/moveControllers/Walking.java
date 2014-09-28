package hanto.studentJnaYy.common.moveControllers;

import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.studentJnaYy.common.GameCoordinate;

import java.util.Map;

public class Walking implements Movement {

	@Override
	public void checkMovement(GameCoordinate to, GameCoordinate from,
			Map<GameCoordinate, HantoPiece> board) throws HantoException {
		
		//Assuming walking is only one step for now - darn TDD~
		if(!from.isAdjacent(to)){
			throw new HantoException("The piece was walking more than one hex.");
		}
		
		boolean arePiecesInNeigboringCoordinates = true;
		for(GameCoordinate commonNeighbor : from.getCommonAdjacentCoordinates(to)){
			if(!board.containsKey(commonNeighbor)){
				arePiecesInNeigboringCoordinates = false;
			}
		}
		if(arePiecesInNeigboringCoordinates){
			throw new HantoException("The piece cannot walk, because slding is prohibited.");
		}
	}

}
