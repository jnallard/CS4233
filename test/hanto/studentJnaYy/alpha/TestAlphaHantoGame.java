/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.alpha;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentJnaYy.HantoGameFactory;
import hanto.studentJnaYy.common.GameCoordinate;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua and Yan
 * This class tests all of the aspects considered with an AlphaHantoGame.
 *
 */
public class TestAlphaHantoGame {
	private static final GameCoordinate GAME_COORDINATE_START = new GameCoordinate(0, 0);
	private HantoGame blueFirstGame;
	private HantoGame redFirstGame;

	@Before
	public void setUp() {
		HantoGameFactory factory = HantoGameFactory.getInstance();
		blueFirstGame = factory.makeHantoGame(HantoGameID.ALPHA_HANTO);
		redFirstGame = factory.makeHantoGame(HantoGameID.ALPHA_HANTO, HantoPlayerColor.RED);
	}

	@Test
	public void testCreateNewGame() {
		boolean isAlphaGame = blueFirstGame instanceof AlphaHantoGame;
		assertTrue(isAlphaGame);
	}

	@Test
	public void testPlaceFirstButterfly() throws HantoException{
		MoveResult result = blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, GAME_COORDINATE_START);
		assertTrue(result == MoveResult.OK);
	}
	

	@Test
	public void testPlaceFirstButterflyAt0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		assertTrue(blueFirstGame.getPieceAt(firstCoordinate).getType() == HantoPieceType.BUTTERFLY);
	}
	

	@Test(expected=HantoException.class)
	public void testPlaceFirstButterflyNotAt0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
	}
	

	@Test
	public void testFirstButterflyIsBlue() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		assertTrue(blueFirstGame.getPieceAt(firstCoordinate).getColor() == HantoPlayerColor.BLUE);
	}

	@Test
	public void testFirstButterflyIsNotRed() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		redFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		assertFalse(redFirstGame.getPieceAt(firstCoordinate).getColor() == HantoPlayerColor.RED);
	}

	@Test
	public void testSecondMoveIsDraw() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		MoveResult result = blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
		assertEquals(MoveResult.DRAW, result);
	}
	


	@Test(expected=HantoException.class)
	public void testGameIsOverAfter2Moves() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 1);
		HantoCoordinate thirdCoordinate =  new GameCoordinate(0, -1);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, thirdCoordinate);
	}
	
	@Test
	public void testSecondPieceIsAdjacent1() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
		assertEquals(blueFirstGame.getPieceAt(secondCoordinate).getColor(), HantoPlayerColor.RED);
	}
	
	@Test
	public void testSecondPieceIsAdjacent2() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, -1);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
		assertEquals(blueFirstGame.getPieceAt(secondCoordinate).getColor(), HantoPlayerColor.RED);
	}
	
	@Test
	public void testSecondPieceIsAdjacent3() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(1, 0);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
		assertEquals(blueFirstGame.getPieceAt(secondCoordinate).getColor(), HantoPlayerColor.RED);
	}
	
	@Test
	public void testSecondPieceIsAdjacent4() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(1, -1);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
		assertEquals(blueFirstGame.getPieceAt(secondCoordinate).getColor(), HantoPlayerColor.RED);
	}
	
	@Test
	public void testSecondPieceIsAdjacent5() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(-1, 1);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
		assertEquals(blueFirstGame.getPieceAt(secondCoordinate).getColor(), HantoPlayerColor.RED);
	}
	
	@Test
	public void testSecondPieceIsAdjacent6() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(-1, 0);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
		assertEquals(blueFirstGame.getPieceAt(secondCoordinate).getColor(), HantoPlayerColor.RED);
	}
	
	@Test(expected=HantoException.class)
	public void testSecondPieceIsInvalid1() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(-1, -1);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
	}
	
	@Test(expected=HantoException.class)
	public void testSecondPieceIsInvalid2() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(1, 1);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
	}
	
	@Test(expected=HantoException.class)
	public void testSecondPieceIsInvalid3() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 5);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
	}
	
	@Test(expected=HantoException.class)
	public void testSecondPieceIsInvalid4() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(4, 5);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
	}
	
	@Test(expected=HantoException.class)
	public void testInvalidPieceTypeCrab() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		blueFirstGame.makeMove(HantoPieceType.CRAB, null, firstCoordinate);
	}
	
	@Test(expected=HantoException.class)
	public void testInvalidPieceTypeCrabSecondMove() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 5);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.CRAB, null, secondCoordinate);
	}
	
	@Test
	public void testPrintableBoard() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_START;
		HantoCoordinate secondCoordinate =  new GameCoordinate(1, -1);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
		assertNotNull(blueFirstGame.getPrintableBoard());
	}
	
	@Test(expected = HantoException.class)
	public void testAttemptToMoveRatherThanPlace() throws HantoException {
		blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, new GameCoordinate(0, 1),
				new GameCoordinate(0, 0));
	}
}
