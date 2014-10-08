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

	private static final int HASH_CODE_BIT_MASK = 0x0000FFFF;
	private static final int HASH_CODE_BIT_SHIFT = 16;
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
	public boolean isAdjacent(GameCoordinate secondCoordinate) {
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
	
	public boolean isStraightLine(GameCoordinate secondCoordinate) {
		int xDifference = xCoordinate - secondCoordinate.xCoordinate;
		int yDifference = yCoordinate - secondCoordinate.yCoordinate;
		
		boolean isXEqual = xDifference == 0;
		boolean isYEqual = yDifference == 0;
		
		boolean areValuesOpposite = xDifference == -yDifference;
		
		
		return isXEqual || isYEqual || areValuesOpposite;
		
	}
	
	public List<GameCoordinate> getStraightLineCoordsBetween(GameCoordinate secondCoordinate) {
		
		List<GameCoordinate> coords = new ArrayList<GameCoordinate>();
		if(isStraightLine(secondCoordinate)){

			int xDifference = xCoordinate - secondCoordinate.xCoordinate;
			int yDifference = yCoordinate - secondCoordinate.yCoordinate;
			
			int xAdd = 0;
			int yAdd = 0;
			
			if(xDifference != 0){
				xAdd = xDifference / Math.abs(xDifference);
			}
			
			if(yDifference != 0){
				yAdd = yDifference / Math.abs(yDifference);
			}
			
			GameCoordinate newCoord = new GameCoordinate(secondCoordinate.xCoordinate + xAdd, 
					secondCoordinate.yCoordinate + yAdd);
			
			while(!newCoord.equals(this)){
				coords.add(newCoord);
				newCoord = new GameCoordinate(newCoord.xCoordinate + xAdd, 
						newCoord.yCoordinate + yAdd);
			}
		}
		return coords;
		
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
	 * Returns a list of coordinate adjacent to both coordinates
	 * @param otherCoord the coordinate to compare this coordinate with
	 * @return the list of common adjacent coordinates
	 */
	public List<GameCoordinate> getCommonAdjacentCoordinates(GameCoordinate otherCoord){
		List<GameCoordinate> commonNeighbors = new ArrayList<GameCoordinate>();
		for(GameCoordinate coord : getAdjacentCoordinates()){
			if(otherCoord.getAdjacentCoordinates().contains(coord)){
				commonNeighbors.add(coord);
			}
		}
		return commonNeighbors;
	}
	
	/**
	 * Gets the integer distance between two coordinates
	 * (The minimum number of moves you'd need to make to move there)
	 * @param toCoord the coordinate to find the distance to
	 * @return the integer distance
	 */
	public int getDistance(GameCoordinate toCoord){
		int distance = getRecursiveDistance(xCoordinate - toCoord.xCoordinate, 
				yCoordinate - toCoord.yCoordinate);
		return distance;
	}
	
	/**
	 * Gets the distance to a coordinate, recursively if needed
	 * @param xDiff the x difference between the coords
	 * @param yDiff the y difference between the coords
	 * @param distanceThusFar the distance traveled thus far
	 * @return
	 */
	private int getRecursiveDistance(int xDiff, int yDiff) {
		
		int distanceThusFar = 0;
		if(xDiff < 0 &&  yDiff > 0){
			distanceThusFar = 1 + getRecursiveDistance(xDiff + 1, yDiff - 1);
		}
		else if(xDiff > 0 &&  yDiff < 0){
			distanceThusFar = 1 + getRecursiveDistance(xDiff - 1, yDiff + 1);
		}
		else{
			distanceThusFar += Math.abs(xDiff) + Math.abs( yDiff);
		}
		
		return distanceThusFar;
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
		return (xCoordinate == coor.getX()) && (yCoordinate == coor.getY());
	}
	
	/**
	 * Overrides the hashcode method.
	 */
	@Override
	public int hashCode(){
		int hash = 0;
		hash |= (xCoordinate << HASH_CODE_BIT_SHIFT);
		hash |= (yCoordinate & HASH_CODE_BIT_MASK);
		return hash;
	}
	
	/**
	 * Converts the GameCoordinate into a string-representable value
	 */
	@Override
	public String toString(){
		return "(" + xCoordinate + ", " + yCoordinate + ") ";
	}
}
