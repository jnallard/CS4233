package hanto.studentJnaYy.common.moveControllers;

import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.studentJnaYy.common.BoardHelperClass;
import hanto.studentJnaYy.common.GameCoordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbsMovement implements Movement {

	@Override
	public abstract void checkMovement(GameCoordinate to, GameCoordinate from,
			Map<GameCoordinate, HantoPiece> board) throws HantoException;

	@Override
	public List<GameCoordinate> getPossibleMoves(GameCoordinate from,
			Map<GameCoordinate, HantoPiece> board) {
		List<GameCoordinate> newCoords = BoardHelperClass.getAllOpenCoordinates(board);
		List<GameCoordinate> moves = new ArrayList<GameCoordinate>();
		for (GameCoordinate coord : newCoords) {
			try {
				Map<GameCoordinate, HantoPiece> newBoard = new HashMap<GameCoordinate, HantoPiece>(
						board);
				HantoPiece piece = newBoard.get(from);
				checkMovement(coord, from, newBoard);
				newBoard.remove(from);
				newBoard.put(coord, piece);
				BoardHelperClass.checkPieceConnectivity(newBoard);
				moves.add(coord);
			} catch (HantoException e) {
				//The piece/move is not valid.
			}
			
		}
		return moves;
	}

	
}
