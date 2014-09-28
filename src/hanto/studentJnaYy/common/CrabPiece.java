/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.common;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * @author Joshua and Yan
 * This class is the implementation of Crab Hanto Piece.
 *
 */
public class CrabPiece extends AbsHantoPiece {
	
	/**
	 * Creates a Crab object, with the given player color.
	 * @param pieceColor the color of the piece's player
	 */
	public CrabPiece(HantoPlayerColor pieceColor){
		super(pieceColor);
	}
	
	
	/**
	 * Returns the type of the piece, i.e. Crab
	 */
	public HantoPieceType getType() {
		return HantoPieceType.CRAB;
	}

}
