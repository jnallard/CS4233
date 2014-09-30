/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************/
package hanto.studentJnaYy.gamma;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

import common.HantoTestGame;
import common.HantoTestGame.PieceLocationPair;
import common.HantoTestGameFactory;

public class TestGammaHanto {
	private static final HantoPlayerColor RED = HantoPlayerColor.RED;
	private static final HantoPlayerColor BLUE = HantoPlayerColor.BLUE;
	private HantoTestGame redFirstTestGame;
	private HantoTestGame blueFirstTestGame;
	private HantoGame redFirstGame;
	private HantoGame blueFirstGame;
	private static final HantoPieceType SPARROW = HantoPieceType.SPARROW;
	private static final HantoPieceType BUTTERFLY = HantoPieceType.BUTTERFLY;
	private final GameCoordinate GAME_COORDINATE_ORIGIN = new GameCoordinate(0, 0);
	
	@Before
	public void setUp() throws Exception {
		redFirstTestGame = HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO, 
				RED);
		redFirstGame = redFirstTestGame;
		blueFirstTestGame = HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO);
		blueFirstGame = blueFirstTestGame;
	}

	@Test
	public void testMakeGame() {
		assertNotNull(redFirstGame);
	}
	
	@Test
	public void testBlueGoesFirst() throws HantoException{
		blueFirstGame.makeMove(SPARROW, null, GAME_COORDINATE_ORIGIN);
		assertEquals(blueFirstGame.getPieceAt(GAME_COORDINATE_ORIGIN).getColor(), BLUE);
	}
	
	@Test
	public void testRedGoesFirst() throws HantoException{
		redFirstGame.makeMove(SPARROW, null, GAME_COORDINATE_ORIGIN);
		assertEquals(redFirstGame.getPieceAt(GAME_COORDINATE_ORIGIN).getColor(), RED);
	}

	@Test
	public void testPlaceFirstButterfly() throws HantoException{
		MoveResult result = blueFirstGame.makeMove(BUTTERFLY, null, GAME_COORDINATE_ORIGIN);
		assertTrue(result == MoveResult.OK);
	}
	

	@Test
	public void testPlaceFirstButterflyAt0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		blueFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		assertTrue(blueFirstGame.getPieceAt(firstCoordinate).getType() == BUTTERFLY);
	}

	@Test
	public void testPlaceFirstSparrowt0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		blueFirstGame.makeMove(SPARROW, null, firstCoordinate);
		assertTrue(blueFirstGame.getPieceAt(firstCoordinate).getType() == SPARROW);
	}

	@Test(expected=HantoException.class)
	public void testPlaceFirstButterflyNotAt0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
	}

	@Test(expected=HantoException.class)
	public void testPlaceFirstSparrowNotAt0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(SPARROW, null, firstCoordinate);
	}
	
	@Test
	public void testPlaceSecondPieceAround0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		blueFirstGame.makeMove(SPARROW, null, firstCoordinate);
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(SPARROW, null, secondCoordinate);
		assertTrue(blueFirstGame.getPieceAt(secondCoordinate).getType() == SPARROW);
	}
	
	@Test(expected=HantoException.class)
	public void testPlaceSameLocationOrigin() throws HantoException{
		
		blueFirstGame.makeMove(BUTTERFLY, null, GAME_COORDINATE_ORIGIN);
		blueFirstGame.makeMove(BUTTERFLY, null, GAME_COORDINATE_ORIGIN);
	}
	
	@Test(expected=HantoException.class)
	public void testPlaceSameLocationElsewhere() throws HantoException{

		GameCoordinate secondCoordinate = new GameCoordinate(1, 0);
		blueFirstGame.makeMove(BUTTERFLY, null, GAME_COORDINATE_ORIGIN);
		blueFirstGame.makeMove(BUTTERFLY, null, secondCoordinate);
		blueFirstGame.makeMove(SPARROW, null, secondCoordinate);
	}

	
	@Test(expected=HantoException.class)
	public void testInvalidPieceTypeCrab() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		redFirstGame.makeMove(HantoPieceType.CRAB, null, firstCoordinate);
	}
	
	@Test(expected=HantoException.class)
	public void testInvalidPieceTypeCrabSecondMove() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 5);
		redFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		redFirstGame.makeMove(HantoPieceType.CRAB, null, secondCoordinate);
	}
	
	@Test(expected=HantoException.class)
	public void testPlaceBlueSparrowNextToRed() throws HantoException{
		PieceLocationPair[] pieces = {
				new PieceLocationPair(BLUE, BUTTERFLY, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(RED, BUTTERFLY, new GameCoordinate(0, 1)),
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(1);
		blueFirstTestGame.setPlayerMoving(BLUE);
		blueFirstGame.makeMove(SPARROW, null, new GameCoordinate(0, 2));
	}
	
	@Test(expected=HantoException.class)
	public void testPlaceRedButterflyNextToBlue() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(RED, SPARROW, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(BLUE, BUTTERFLY, new GameCoordinate(0, 1)),
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(2);
		blueFirstTestGame.setPlayerMoving(RED);
		blueFirstGame.makeMove(BUTTERFLY, null, new GameCoordinate(0, 2));
	}
	
	@Test
	public void testPlaceBlueSparrowNextToRedFirstRound() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 1);
		redFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		MoveResult result = redFirstGame.makeMove(HantoPieceType.SPARROW, null, secondCoordinate);
		assertEquals(MoveResult.OK, result);
	}
	
	@Test
	public void testPlaceRedButterflyNextToBlueFirstRound() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		MoveResult result = blueFirstGame.makeMove(HantoPieceType.BUTTERFLY, null, secondCoordinate);
		assertEquals(MoveResult.OK, result);
	}
	

	
	@Test(expected=HantoException.class)
	public void testButterflyPlacedBy4thTurn() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(RED, SPARROW, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(BLUE, BUTTERFLY, new GameCoordinate(0, 1)),
				new PieceLocationPair(RED, SPARROW, new GameCoordinate(0, 2)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(0, 3)),
				new PieceLocationPair(RED, SPARROW, new GameCoordinate(0, 4)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(0, 5))
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(4);
		blueFirstTestGame.setPlayerMoving(RED);
		blueFirstGame.makeMove(BUTTERFLY, null, new GameCoordinate(0, 6));
	}
	
	@Test(expected=HantoException.class)
	public void testOnlyPlace1ButterflyPerPlayer() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(RED, BUTTERFLY, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(BLUE, BUTTERFLY, new GameCoordinate(0, 1)),
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(2);
		blueFirstTestGame.setPlayerMoving(RED);
		blueFirstGame.makeMove(BUTTERFLY, null, new GameCoordinate(0, -1));
	}
	
	@Test(expected=HantoException.class)
	public void testPlaceNoMoreThan5SparrowsPerPlayer() throws HantoException{

		PieceLocationPair[] pieces = generateTestGameInALine();
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(4);
		blueFirstTestGame.setPlayerMoving(RED);
		blueFirstGame.makeMove(SPARROW, null, new GameCoordinate(0, -6));
	}
	

	
	@Test
	public void testMoveButterfly1() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(RED, BUTTERFLY, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(BLUE, BUTTERFLY, new GameCoordinate(0, 1)),
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(2);
		blueFirstTestGame.setPlayerMoving(RED);
		MoveResult result = blueFirstGame.makeMove(BUTTERFLY, 
				GAME_COORDINATE_ORIGIN, new GameCoordinate(1, 0));
		assertEquals(MoveResult.OK, result);
	}
	
	@Test(expected = HantoException.class)
	public void testMoveButterfly2() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(RED, BUTTERFLY, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(BLUE, BUTTERFLY, new GameCoordinate(0, 1)),
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(2);
		blueFirstTestGame.setPlayerMoving(RED);
		blueFirstGame.makeMove(BUTTERFLY, GAME_COORDINATE_ORIGIN, new GameCoordinate(1, 1));
	}

	
	@Test
	public void testMoveSparrow1() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(RED, SPARROW, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(BLUE, BUTTERFLY, new GameCoordinate(0, 1)),
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(2);
		blueFirstTestGame.setPlayerMoving(RED);
		MoveResult result = blueFirstGame.makeMove(SPARROW, 
				GAME_COORDINATE_ORIGIN, new GameCoordinate(1, 0));
		assertEquals(MoveResult.OK, result);
	}
	
	@Test(expected = HantoException.class)
	public void testMoveSparrow2() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(RED, SPARROW, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(BLUE, BUTTERFLY, new GameCoordinate(0, 1)),
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(2);
		blueFirstTestGame.setPlayerMoving(RED);
		blueFirstGame.makeMove(SPARROW, GAME_COORDINATE_ORIGIN, new GameCoordinate(1, 1));
	}
	

	
	@Test(expected = HantoException.class)
	public void testMoveSparrowFromUnknownLocation() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(RED, SPARROW, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(BLUE, BUTTERFLY, new GameCoordinate(0, 1)),
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(2);
		blueFirstTestGame.setPlayerMoving(RED);
		blueFirstGame.makeMove(SPARROW, new GameCoordinate(0, 5), new GameCoordinate(0, 6));
	}
	
	@Test(expected = HantoException.class)
	public void testMoveSparrowAsIfButterfly() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(RED, SPARROW, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(BLUE, BUTTERFLY, new GameCoordinate(0, 1)),
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(2);
		blueFirstTestGame.setPlayerMoving(RED);
		blueFirstGame.makeMove(BUTTERFLY, GAME_COORDINATE_ORIGIN, new GameCoordinate(1, 0));
	}
	
	@Test(expected = HantoException.class)
	public void testWalkPieceThatCannotSlide() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(BLUE, BUTTERFLY, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(RED, BUTTERFLY, new GameCoordinate(0, 1)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(-1, 0)),
				new PieceLocationPair(RED, SPARROW, new GameCoordinate(0, 2))
				
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(3);
		blueFirstTestGame.setPlayerMoving(BLUE);
		blueFirstGame.makeMove(BUTTERFLY, GAME_COORDINATE_ORIGIN, new GameCoordinate(-1, 1));
	}
	

	
	@Test(expected = HantoException.class)
	public void testPiecesNotConnectedAfterMovement() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(BLUE, BUTTERFLY, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(RED, BUTTERFLY, new GameCoordinate(0, 1)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(-1, 0)),
				new PieceLocationPair(RED, SPARROW, new GameCoordinate(0, 2))
				
		};
		
		blueFirstTestGame.initializeBoard(pieces);
		blueFirstTestGame.setTurnNumber(3);
		blueFirstTestGame.setPlayerMoving(BLUE);
		blueFirstGame.makeMove(BUTTERFLY, GAME_COORDINATE_ORIGIN, new GameCoordinate(0, -1));
	}
	

	@Test
	public void testGameStillRunningAt19Turns() throws HantoException{

		PieceLocationPair[] pieces = generateTestGameInALine();
		
		redFirstTestGame.initializeBoard(pieces);
		redFirstTestGame.setTurnNumber(19);
		redFirstTestGame.setPlayerMoving(RED);
		MoveResult result = redFirstGame.makeMove(SPARROW, new GameCoordinate(0, -5), new GameCoordinate(1, -5));
		assertEquals(MoveResult.OK, result);
	}
	
	@Test
	public void testGameDrawAfter20Turns() throws HantoException{

		PieceLocationPair[] pieces = generateTestGameInALine();
		
		redFirstTestGame.initializeBoard(pieces);
		redFirstTestGame.setTurnNumber(20);
		redFirstTestGame.setPlayerMoving(BLUE);
		MoveResult result = redFirstGame.makeMove(SPARROW, new GameCoordinate(0, 6), new GameCoordinate(1, 5));
		assertEquals(MoveResult.DRAW, result);
	}

	@Test
	public void testGameButterflySurround() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(BLUE, BUTTERFLY, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(RED, BUTTERFLY, new GameCoordinate(0, 1)),
				new PieceLocationPair(RED, SPARROW, new GameCoordinate(-1, 1)),
				new PieceLocationPair(RED, SPARROW, new GameCoordinate(1, 0)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(1, -1)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(0, -1)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(-1, -1))
				
		};
		
		redFirstTestGame.initializeBoard(pieces);
		redFirstTestGame.setTurnNumber(10);
		redFirstTestGame.setPlayerMoving(BLUE);
		MoveResult result = redFirstGame.makeMove(SPARROW, new GameCoordinate(-1, -1), new GameCoordinate(-1, 0));
		assertEquals(MoveResult.RED_WINS, result);
	}
	
	private PieceLocationPair[] generateTestGameInALine() {
		PieceLocationPair[] pieces = {
				new PieceLocationPair(RED, BUTTERFLY, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(BLUE, BUTTERFLY, new GameCoordinate(0, 1)),
				new PieceLocationPair(RED, SPARROW, new GameCoordinate(0, -1)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(0, 2)),
				new PieceLocationPair(RED, SPARROW, new GameCoordinate(0, -2)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(0, 3)),
				new PieceLocationPair(RED, SPARROW, new GameCoordinate(0, -3)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(0, 4)),
				new PieceLocationPair(RED, SPARROW, new GameCoordinate(0, -4)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(0, 5)),
				new PieceLocationPair(RED, SPARROW, new GameCoordinate(0, -5)),
				new PieceLocationPair(BLUE, SPARROW, new GameCoordinate(0, 6))
		};
		return pieces;
	}
}
