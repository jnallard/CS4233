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

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * @author Joshua and Yan
 * This class is the implementation of Sparrow Hanto Piece.
 *
 */
public class SparrowPiece extends AbsHantoPiece {
	
	/**
	 * Creates a Sparrow object, with the given player color.
	 * @param pieceColor the color of the piece's player
	 */
	public SparrowPiece(HantoPlayerColor pieceColor){
		super(pieceColor);
	}
	
	
	/**
	 * Returns the type of the piece, i.e. Sparrow
	 */
	public HantoPieceType getType() {
		return HantoPieceType.SPARROW;
	}

}
