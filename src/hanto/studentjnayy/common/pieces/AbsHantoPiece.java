/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentjnayy.common.pieces;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * @author Joshua and Yan
 * This class is the implementation of a Hanto Piece.
 *
 */
public abstract class AbsHantoPiece implements HantoPiece {

	private HantoPlayerColor pieceColor;
	
	/**
	 * Creates a hanto piece object, with the given player color.
	 * @param pieceColor the color of the piece's player
	 */
	protected AbsHantoPiece(HantoPlayerColor pieceColor){
		this.pieceColor = pieceColor;
	}
	
	/**
	 * Returns the Color of the piece
	 */
	public HantoPlayerColor getColor() {
		return pieceColor;
	}

	/**
	 * Returns the type of the piece, i.e. Sparrow, Butterfly, etc.
	 */
	public abstract HantoPieceType getType();

}
