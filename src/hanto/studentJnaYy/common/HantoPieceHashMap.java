/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This creates a HashMap specifically for GameCoordinates to Pieces,
 * with all of the respective functions included.
 * @author Joshua and Yan
 *
 */
public class HantoPieceHashMap extends HashMap<GameCoordinate, HantoPiece> implements HantoPieceMap{
	
	/**
	 * Creates a new empty Map
	 */
	public HantoPieceHashMap() {
	}
	
	/**
	 * Creates a copy of a previous map
	 * @param copy the map to copy
	 */
	public HantoPieceHashMap(Map<GameCoordinate, HantoPiece> copy) {
		super(copy);
	}
	
	/**
	 * Checks to see if the pieces on the board are all connected
	 * @throws HantoException
	 */
	public boolean arePiecesConnected(){
		List<GameCoordinate> foundCoords = new ArrayList<GameCoordinate>();
		GameCoordinate startingCoord = (GameCoordinate) keySet().toArray()[0];
		
		findAllConnectedCoords(startingCoord, foundCoords);
		
		
		return foundCoords.size() == size();
	}

	/**
	 * Gets all of the connected coordinates from a given coordinate (recursive)
	 * @param startingCoord the location to start from
	 * @param foundCoords the list to store the connected coordinates in
	 */
	private void findAllConnectedCoords(GameCoordinate startingCoord,
			List<GameCoordinate> foundCoords) {
		foundCoords.add(startingCoord);
		
		List<GameCoordinate> allNeighbors = startingCoord.getAdjacentCoordinates();
		List<GameCoordinate> foundNeighbors = new ArrayList<GameCoordinate>();
		
		for(GameCoordinate neighbor : allNeighbors){
			if(containsKey(neighbor)){
				foundNeighbors.add(neighbor);
			}
		}
		
		for(GameCoordinate neighbor : foundNeighbors){
			if(!foundCoords.contains(neighbor)){
				findAllConnectedCoords(neighbor, foundCoords);
			}
		}
		
	}
	
	/**
	 * Returns a list of all the coordinates not taken by a piece, but adjacent to one.
	 * @return the list of possible open coordinates
	 */
	public List<GameCoordinate> getAllOpenCoordinates(){
		List<GameCoordinate> openCoords = new ArrayList<GameCoordinate>();
		for(GameCoordinate coord: keySet()){
			List<GameCoordinate> neighbors = coord.getAdjacentCoordinates();
			for(GameCoordinate neighbor: neighbors){
				if(!containsKey(neighbor) && !openCoords.contains(neighbor)){
					openCoords.add(neighbor);
				}
			}
		}
		return openCoords;
	}
	
	/**
	 * Returns the number of neighbor pieces to a certain coordinate
	 * @param coordinate the coordinate on the board to check for
	 * @return the number of neighbor pieces
	 */
	public int getNumberOfNeighborPieces(GameCoordinate coordinate) {
		int numberOfOccupiedNeighbors = 0;
		List<GameCoordinate> neighbors = coordinate.getAdjacentCoordinates();
		for(int i = 0; i < neighbors.size(); i++){
			GameCoordinate neighbor = neighbors.get(i);
			if(containsKey(neighbor)){
				numberOfOccupiedNeighbors++;
			}
		}
		return numberOfOccupiedNeighbors;
	}
	
	/**
	 * Check to see if a piece is only added next to its own color
	 * @param coord the location to check
	 * @param currentColor the color to check the neighbors for
	 * @return true if the opposite color is not next to the given coord
	 */
	public boolean isPieceOnlyNextToColor(HantoCoordinate coord,
			HantoPlayerColor currentColor) {
		GameCoordinate toCoord = new GameCoordinate(coord);
		List<GameCoordinate> neighbors = toCoord.getAdjacentCoordinates();
		boolean isNextToOnlyThisColor = true;
		
		for(GameCoordinate neighbor : neighbors){
			HantoPiece piece = get(neighbor);
			if(piece != null){
				isNextToOnlyThisColor &= piece.getColor() == currentColor;
			}
		}
		return isNextToOnlyThisColor;
	}
	
	/**
	 * Checks to see if the piece wanted is at the given location.
	 * @param fromCoordinate the coordinate to check
	 * @param pieceType the piece type to check for
	 * @param currentColor the piece color to check for
	 * @throws HantoException 
	 */
	public boolean isPieceHere(HantoCoordinate fromCoordinate,
			HantoPieceType pieceType, HantoPlayerColor currentColor){
		
		HantoPiece piece = getPieceAt(fromCoordinate);
		
		boolean isPieceHereResult = false;
		if(piece != null){
			isPieceHereResult = piece.getColor() == currentColor;
			isPieceHereResult &= piece.getType() == pieceType;
		}
		return isPieceHereResult;
	}

	/**
	 * Return the piece based on the coordinate given
	 * @param coordinate the coordinate given
	 * @return hanto piece on this coordinate, or null if there is no piece on this coordinate
	 */
	public HantoPiece getPieceAt(HantoCoordinate coordinate){
		
		return get(new GameCoordinate(coordinate));
	}
	
	/**
	 * Returns the board in this format: 
	 * "(x1, y1) Color1 Piece1
	 * (x2, y2) Color2 Piece2"
	 * (There is no particular order when printing)
	 * @return a printable representation of the board.
	 */
	public String getPrintableBoard(){
		String printedBoard = "";
		for(Entry<GameCoordinate, HantoPiece> entry : entrySet()){
			GameCoordinate coord = entry.getKey();
			HantoPiece piece = entry.getValue();
			printedBoard += coord.toString() + piece.getColor() + " " + piece.getType() + "\n";
		}
		return printedBoard;
	}
	
	public Object clone(){
		super.clone();
		return new HantoPieceHashMap(this);
	}

}
