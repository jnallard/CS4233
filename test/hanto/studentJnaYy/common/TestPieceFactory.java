/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.common.pieces.PieceFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua and Yan
 * This class tests creating different pieces with the PieceFactory
 *
 */
public class TestPieceFactory {

	private PieceFactory factory;
	
	@Before
	public void setUp() {
		factory = PieceFactory.getInstance();
	}

	@Test
	public void testGetInstance() {
		assertNotNull(factory);
	}
	
	@Test
	public void testMakeButterflyBlue(){
		HantoPiece piece = factory.makeGamePiece(HantoPieceType.BUTTERFLY, HantoPlayerColor.BLUE);
		boolean doesTypeMatch = piece.getType() == HantoPieceType.BUTTERFLY;
		boolean doesColorMatch = piece.getColor() == HantoPlayerColor.BLUE;
		assertTrue(doesColorMatch && doesTypeMatch);
	}

	@Test
	public void testMakeSparrowRed(){
		HantoPiece piece = factory.makeGamePiece(HantoPieceType.SPARROW, HantoPlayerColor.RED);
		boolean doesTypeMatch = piece.getType() == HantoPieceType.SPARROW;
		boolean doesColorMatch = piece.getColor() == HantoPlayerColor.RED;
		assertTrue(doesColorMatch && doesTypeMatch);
	}

}
