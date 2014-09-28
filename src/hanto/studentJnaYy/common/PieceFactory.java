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

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * @author Joshua and Yan
 * This class is the factory that returns the HantoPiece with the correct color and type.
 *
 */
public class PieceFactory {

	private static final PieceFactory INSTANCE = new PieceFactory();
	
	/**
	 * Creates the piece Factory instance.
	 */
	private PieceFactory(){
	}

	/**
	 * @return the PieceFactory instance
	 */
	public static PieceFactory getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Creates a HantoPiece from the given type and color
	 * @param type - the HantoPieceType wanted
	 * @param color - the HantoPlayerColor wanted
	 * @return the wanted HantoPiece
	 */
	public HantoPiece makeGamePiece(HantoPieceType type, HantoPlayerColor color){
		HantoPiece piece;
		switch(type){
			case BUTTERFLY: 
				piece = new ButterflyPiece(color);
				break;
			case SPARROW:
				piece = new SparrowPiece(color);
				break;
			case CRAB:
				piece = new CrabPiece(color);
				break;
			default:
				piece = null;
				break;
		}
		return piece;
	}
}
