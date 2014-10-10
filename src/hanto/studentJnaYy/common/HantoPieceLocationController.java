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

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This creates a PieceLocationController specifically for GameCoordinates to Pieces,
 * with all of the respective functions included.
 * @author Joshua and Yan
 *
 */
public interface HantoPieceLocationController{
	
	/**
	 * Checks to see if the pieces on the board are all connected
	 * @throws HantoException
	 * @return True if all of the pieces are connected
	 */
	boolean arePiecesConnected() throws HantoException;
	
	/**
	 * Returns a list of all the coordinates not taken by a piece, but adjacent to one.
	 * @return the list of possible open coordinates
	 */
	List<GameCoordinate> getAllOpenCoordinates();
	
	/**
	 * Returns the number of neighbor pieces to a certain coordinate
	 * @param coordinate the coordinate on the board to check for
	 * @return the number of neighbor pieces
	 */
	int getNumberOfNeighborPieces(GameCoordinate coordinate);
	
	/**
	 * Check to see if a piece is only added next to its own color
	 * @param coord the location to check
	 * @param currentColor the color to check the neighbors for
	 * @return true if the opposite color is not next to the given coord
	 */
	boolean isPieceOnlyNextToColor(HantoCoordinate coord,
			HantoPlayerColor currentColor) ;
	
	/**
	 * Checks to see if the piece wanted is at the given location.
	 * @param fromCoordinate the coordinate to check
	 * @param pieceType the piece type to check for
	 * @param currentColor the piece color to check for
	 * @throws HantoException 
	 * @return true if the piece is at the location
	 */
	boolean isPieceHere(HantoCoordinate fromCoordinate,
			HantoPieceType pieceType, HantoPlayerColor currentColor) throws HantoException ;

	/**
	 * Return the piece based on the coordinate given
	 * @param coordinate the coordinate given
	 * @return hanto piece on this coordinate, or null if there is no piece on this coordinate
	 */
	HantoPiece getPieceAt(HantoCoordinate coordinate);
	
	/**
	 * Returns the board in this format: 
	 * "(x1, y1) Color1 Piece1
	 * (x2, y2) Color2 Piece2"
	 * (There is no particular order when printing)
	 * @return a printable representation of the board.
	 */
	String getPrintableBoard();
	
	/**
	 * Returns the board that the map contains, so it can be copied when needed
	 * @return the map inside this piece location holder
	 */
	Map<GameCoordinate, HantoPiece> getUnderlyingBoard();

	/**
	 * Checks to see if the board contains the coordinate
	 * @param to the location to check for
	 * @return true if the board contains it
	 */
	boolean containsCoordinate(GameCoordinate to);

	/**
	 * Adds a piece to the board, at the given coordinate
	 * @param coordinate the location of the piece
	 * @param piece the piece to add
	 */
	void addPiece(GameCoordinate coordinate, HantoPiece piece);

	/**
	 * Removes a piece/location from the board
	 * @param gameCoordinate the location to clear
	 */
	void removePiece(GameCoordinate gameCoordinate);

	/**
	 * Removes all pieces from the board
	 */
	void clearBoard();

	/**
	 * Returns a list of all the coordinates set for the board.
	 * @return the list of all coordinates
	 */
	Set<GameCoordinate> getAllCoordinates();

}
