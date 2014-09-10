/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentJnaYy.common;

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
		return super.hashCode();
	}
}
