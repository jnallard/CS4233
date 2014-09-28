/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.common;

import static org.junit.Assert.assertTrue;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.common.pieces.ButterflyPiece;
import hanto.studentJnaYy.common.pieces.SparrowPiece;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua and Yan
 * This class tests creating different pieces
 *
 */
public class TestPieces {

	private HantoPiece butterflyPiece;
	private HantoPiece sparrowPiece;
	
	@Before
	public void setUp() {
		butterflyPiece = new ButterflyPiece(HantoPlayerColor.RED);
		sparrowPiece = new SparrowPiece(HantoPlayerColor.BLUE);
	}

	@Test
	public void testGetType() {
		boolean isButterfly = butterflyPiece.getType() == HantoPieceType.BUTTERFLY;
		boolean isSparrow = sparrowPiece.getType() == HantoPieceType.SPARROW;
		assertTrue(isSparrow && isButterfly);
	}
	
	@Test
	public void testGetColor() {
		boolean isRed = butterflyPiece.getColor() == HantoPlayerColor.RED;
		boolean isBlue = sparrowPiece.getColor() == HantoPlayerColor.BLUE;
		assertTrue(isRed && isBlue);
	}

}
