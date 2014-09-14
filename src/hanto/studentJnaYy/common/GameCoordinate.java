/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentJnaYy.common;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;

/**
 * @author Joshua and Yan
 * This class is the implementation of HantoCoordinate
 * We are following this "http://www.vbforums.com/showthread.php?663283-Hexagonal-coordinate-system"
 * hexagonal board implementation.
 */
public class GameCoordinate implements HantoCoordinate {

	private int xCoordinate;
	private int yCoordinate;
	
	/**
	 * Creates the game coordinate instance
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public GameCoordinate(int x, int y){
		xCoordinate = x;
		yCoordinate = y;
	}
	
	/**
	 * Copies the HantoCoordinate and returns a GameCoordinate
	 * @param copy the coordinate to copy
	 */
	public GameCoordinate(HantoCoordinate copy){
		xCoordinate = copy.getX();
		yCoordinate = copy.getY();
	}
	
	/**
	 * Returns the X Coordinate
	 */
	public int getX() {
		return xCoordinate;
	}

	/**
	 * Returns the Y Coordinate
	 */
	public int getY() {
		return yCoordinate;
	}
	
	/**
	 * Checks to see if the two pieces are adjacent on a hexagonal board, using this
	 * implementation "http://www.vbforums.com/showthread.php?663283-Hexagonal-coordinate-system"
	 * @param secondCoordinate the second coordinate to check for
	 * @return true if the second piece is considered adjacent to the first piece.
	 */
	public boolean isAdjacent(HantoCoordinate secondCoordinate) {
		boolean areCoordsAdjacent;
		int xDifference = this.getX() - secondCoordinate.getX();
		int yDifference = this.getY() - secondCoordinate.getY();
		switch(xDifference){
			case 0:
				areCoordsAdjacent = Math.abs(yDifference) == 1;
				break;
			case 1:
				areCoordsAdjacent = yDifference == 0 || yDifference == -1;
				break;
			case -1:
				areCoordsAdjacent = yDifference == 0 || yDifference == 1;
				break;
			default:
				areCoordsAdjacent = false;
				break;
		}
		return areCoordsAdjacent;
	}

	/**
	 * This is used to return all of the current coordinate's neighbors.
	 * @return The list of adjacent coordinates
	 */
	public List<GameCoordinate> getAdjacentCoordinates(){
		List<GameCoordinate> adjacentCoords = new ArrayList<GameCoordinate>();
		adjacentCoords.add(new GameCoordinate(xCoordinate + 1, yCoordinate));
		adjacentCoords.add(new GameCoordinate(xCoordinate + 1, yCoordinate - 1));
		adjacentCoords.add(new GameCoordinate(xCoordinate - 1, yCoordinate));
		adjacentCoords.add(new GameCoordinate(xCoordinate - 1, yCoordinate + 1));
		adjacentCoords.add(new GameCoordinate(xCoordinate, yCoordinate + 1));
		adjacentCoords.add(new GameCoordinate(xCoordinate, yCoordinate - 1));
		return adjacentCoords;
	}
	
	/**
	 * Overrides the equals method, so that the only thing used to 
	 * determine if coordinates are equal are the x and y coordinates.
	 */
	@Override
	public boolean equals(Object other){
		if(!(other instanceof HantoCoordinate)){
			return false;
		}
		HantoCoordinate coor = (HantoCoordinate) other;
		return xCoordinate == coor.getX() && (yCoordinate == coor.getY());
	}
	
	/**
	 * Overrides the hashcode method.
	 */
	@Override
	public int hashCode(){
		int hash = 0;
		hash |= (xCoordinate << 16);
		hash |= (yCoordinate & 0x0000FFFF);
		return hash;
	}
}
