/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.beta;

import static org.junit.Assert.*;
import hanto.HantoGameFactory;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentJnaYy.common.GameCoordinate;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua and Yan
 * This class tests all of the aspects considered with an BetaHantoGame.
 *
 */
public class TestBetaHantoGame {
	private HantoGame blueFirstGame;
	private HantoGame redFirstGame;

	@Before
	public void setUp() {
		HantoGameFactory factory = HantoGameFactory.getInstance();
		blueFirstGame = factory.makeHantoGame(HantoGameID.BETA_HANTO);
		redFirstGame = factory.makeHantoGame(HantoGameID.BETA_HANTO, HantoPlayerColor.RED);
	}

	@Test
	public void testCreateNewGame() {
		boolean isBetaGame = blueFirstGame instanceof BetaHantoGame;
		assertTrue(isBetaGame);
	}

	@Test
	public void testPlaceFirstButterfly() throws HantoException{
		MoveResult result = blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, new GameCoordinate(0, 0));
		assertTrue(result == MoveResult.OK);
	}
	

	@Test
	public void testPlaceFirstButterflyAt0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 0);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		assertTrue(blueFirstGame.getPieceAt(firstCoordinate).getType() == HantoPieceType.BUTTERFLY);
	}

	@Test
	public void testPlaceFirstSparrowt0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 0);
		blueFirstGame.makeMove(HantoPieceType.SPARROW, null, firstCoordinate);
		assertTrue(blueFirstGame.getPieceAt(firstCoordinate).getType() == HantoPieceType.BUTTERFLY);
	}

	@Test(expected=HantoException.class)
	public void testPlaceFirstButterflyNotAt0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
	}

	@Test(expected=HantoException.class)
	public void testPlaceFirstSparrowNotAt0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(HantoPieceType.SPARROW, null, firstCoordinate);
	}

	@Test
	public void testFirstButterflyIsBlue() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 0);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		assertTrue(blueFirstGame.getPieceAt(firstCoordinate).getColor() == HantoPlayerColor.BLUE);
	}

	@Test
	public void testFirstButterflyIsRed() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 0);
		redFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		assertTrue(redFirstGame.getPieceAt(firstCoordinate).getColor() == HantoPlayerColor.RED);
	}


	
	@Test(expected=HantoException.class)
	public void testInvalidPieceTypeCrab() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 0);
		redFirstGame.makeMove(HantoPieceType.CRAB, null, firstCoordinate);
	}
	
	@Test(expected=HantoException.class)
	public void testInvalidPieceTypeCrabSecondMove() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 0);
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 5);
		redFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		redFirstGame.makeMove(HantoPieceType.CRAB, null, secondCoordinate);
	}
	
	@Test
	public void testPrintableBoard() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 0);
		HantoCoordinate secondCoordinate =  new GameCoordinate(1, -1);
		redFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		redFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
		assertNotNull(redFirstGame.getPrintableBoard());
	}
}
