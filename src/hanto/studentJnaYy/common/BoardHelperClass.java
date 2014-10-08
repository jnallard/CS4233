package hanto.studentJnaYy.common;

import hanto.common.HantoException;
import hanto.common.HantoPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardHelperClass {
	
	/**
	 * Checks to see if every piece on the board is connected
	 * @throws HantoException
	 */
	public static void checkPieceConnectivity(Map<GameCoordinate, HantoPiece> theBoard) throws HantoException{
		List<GameCoordinate> foundCoords = new ArrayList<GameCoordinate>();
		GameCoordinate startingCoord = (GameCoordinate) theBoard.keySet().toArray()[0];
		
		findAllConnectedCoords(startingCoord, foundCoords, theBoard);
		
		if(foundCoords.size() != theBoard.size()){
			throw new HantoException("The board is no longer contiguous.");
		}
	}

	/**
	 * Gets all of the connected coordinates from a given coordinate (recursive)
	 * @param startingCoord the location to start from
	 * @param foundCoords the list to store the connected coordinates in
	 */
	private static void findAllConnectedCoords(GameCoordinate startingCoord,
			List<GameCoordinate> foundCoords, Map<GameCoordinate, HantoPiece> theBoard) {
		foundCoords.add(startingCoord);
		
		List<GameCoordinate> allNeighbors = startingCoord.getAdjacentCoordinates();
		List<GameCoordinate> foundNeighbors = new ArrayList<GameCoordinate>();
		
		for(GameCoordinate neighbor : allNeighbors){
			if(theBoard.containsKey(neighbor)){
				foundNeighbors.add(neighbor);
			}
		}
		
		for(GameCoordinate neighbor : foundNeighbors){
			if(!foundCoords.contains(neighbor)){
				findAllConnectedCoords(neighbor, foundCoords, theBoard);
			}
		}
		
	}
	
	public static List<GameCoordinate> getAllOpenCoordinates(Map<GameCoordinate, HantoPiece> board){
		List<GameCoordinate> openCoords = new ArrayList<GameCoordinate>();
		for(GameCoordinate coord: board.keySet()){
			List<GameCoordinate> neighbors = coord.getAdjacentCoordinates();
			for(GameCoordinate neighbor: neighbors){
				if(!board.containsKey(neighbor) && !openCoords.contains(neighbor)){
					openCoords.add(neighbor);
				}
			}
		}
		return openCoords;
	}

}
