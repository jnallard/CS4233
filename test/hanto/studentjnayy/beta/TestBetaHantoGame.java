/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentjnayy.beta;

import static org.junit.Assert.*;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjnayy.HantoGameFactory;
import hanto.studentjnayy.beta.BetaHantoGame;
import hanto.studentjnayy.common.GameCoordinate;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua and Yan
 * This class tests all of the aspects considered with a BetaHantoGame.
 *
 */
public class TestBetaHantoGame {
	private static final HantoPieceType SPARROW = HantoPieceType.SPARROW;
	private static final HantoPieceType BUTTERFLY = HantoPieceType.BUTTERFLY;
	private final GameCoordinate GAME_COORDINATE_ORIGIN = new GameCoordinate(0, 0);
	private HantoGame blueFirstGame;
	private HantoGame redFirstGame;
	private GameCoordinate previousLinearCoord;
	private final boolean noExceptionOccured = true;

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

	@Test
	public void testPlaceThirdPieceNotAround0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		blueFirstGame.makeMove(SPARROW, null, firstCoordinate);
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(SPARROW, null, secondCoordinate);
		HantoCoordinate thirdCoordinate =  new GameCoordinate(1, 1);
		blueFirstGame.makeMove(SPARROW, null, thirdCoordinate);
		assertTrue(blueFirstGame.getPieceAt(thirdCoordinate).getType() == SPARROW);
	}
	
	@Test(expected=HantoException.class)
	public void testPlaceSecondPieceNotAround0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		blueFirstGame.makeMove(SPARROW, null, firstCoordinate);
		HantoCoordinate secondCoordinate =  new GameCoordinate(5, 1);
		blueFirstGame.makeMove(SPARROW, null, secondCoordinate);
	}
	
	@Test
	public void testPlaceThirdPieceAround0_0() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		blueFirstGame.makeMove(SPARROW, null, firstCoordinate);
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(SPARROW, null, secondCoordinate);
		HantoCoordinate thirdCoordinate =  new GameCoordinate(0, -1);
		blueFirstGame.makeMove(SPARROW, null, thirdCoordinate);
		assertTrue(blueFirstGame.getPieceAt(thirdCoordinate).getType() == SPARROW);
	}
	
	@Test(expected=HantoException.class)
	public void testPlaceThirdPieceNotAdjacentToAnything() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		blueFirstGame.makeMove(SPARROW, null, firstCoordinate);
		HantoCoordinate secondCoordinate =  new GameCoordinate(0, 1);
		blueFirstGame.makeMove(SPARROW, null, secondCoordinate);
		HantoCoordinate thirdCoordinate =  new GameCoordinate(6, -1);
		blueFirstGame.makeMove(SPARROW, null, thirdCoordinate);
	}

	@Test
	public void testFirstButterflyIsBlue() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		blueFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		assertTrue(blueFirstGame.getPieceAt(firstCoordinate).getColor() == HantoPlayerColor.BLUE);
	}

	@Test
	public void testFirstButterflyIsRed() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		redFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		assertTrue(redFirstGame.getPieceAt(firstCoordinate).getColor() == HantoPlayerColor.RED);
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
	
	@Test
	public void testPrintableBoard() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		HantoCoordinate secondCoordinate =  new GameCoordinate(1, -1);
		redFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		redFirstGame.makeMove(BUTTERFLY, null, secondCoordinate);
		assertNotNull(redFirstGame.getPrintableBoard());
	}
	
	@Test(expected=HantoException.class)
	public void testAddTwoButterfliesForRed() throws HantoException{
		HantoCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		HantoCoordinate secondCoordinate =  new GameCoordinate(1, -1);
		HantoCoordinate thirdCoordinate = new GameCoordinate(0, 1);
		redFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		redFirstGame.makeMove(BUTTERFLY, null, secondCoordinate);
		redFirstGame.makeMove(BUTTERFLY, null, thirdCoordinate);
	}
	
	@Test(expected=HantoException.class)
	public void testGameEndsAfter6Turns() throws HantoException{
		GameCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		GameCoordinate secondCoordinate =  new GameCoordinate(1, 0);
		redFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		redFirstGame.makeMove(BUTTERFLY, null, secondCoordinate);
		previousLinearCoord = secondCoordinate;
		for(int i = 0; i < 11; i++){
			createNextLinearCoordAndMakeMove(SPARROW, redFirstGame);
		}
	}
	
	@Test
	public void testGameRanUntil6Turns() throws HantoException{
		GameCoordinate firstCoordinate =  GAME_COORDINATE_ORIGIN;
		GameCoordinate secondCoordinate =  new GameCoordinate(1, 0);
		redFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		redFirstGame.makeMove(BUTTERFLY, null, secondCoordinate);
		previousLinearCoord = secondCoordinate;
		for(int i = 0; i < 10; i++){
			createNextLinearCoordAndMakeMove(SPARROW, redFirstGame);
		}
		assertTrue(noExceptionOccured);
	}
	
	@Test(expected=HantoException.class)
	public void testUse6Sparrows() throws HantoException{
		previousLinearCoord = GAME_COORDINATE_ORIGIN;
		for(int i = 0; i < 11; i++){
			createNextLinearCoordAndMakeMove(SPARROW, redFirstGame);
		}
	}
	

	@Test
	public void testNotPlaceButterflyByRound4RedBefore() throws HantoException{
		previousLinearCoord = GAME_COORDINATE_ORIGIN;
		blueFirstGame.makeMove(BUTTERFLY, null, GAME_COORDINATE_ORIGIN);
		for(int i = 0; i < 6; i++){
			createNextLinearCoordAndMakeMove(SPARROW, blueFirstGame);
		}
		assertTrue(noExceptionOccured);
	}
	
	@Test(expected=HantoException.class)
	public void testNotPlaceButterflyByRound4Red() throws HantoException{
		previousLinearCoord = GAME_COORDINATE_ORIGIN;
		blueFirstGame.makeMove(BUTTERFLY, null, GAME_COORDINATE_ORIGIN);
		for(int i = 0; i < 7; i++){
			createNextLinearCoordAndMakeMove(SPARROW, blueFirstGame);
		}
	}
	
	@Test
	public void testNotPlaceButterflyByRound4BlueBefore() throws HantoException{
		GameCoordinate secondCoordinate = new GameCoordinate(1, 0);
		previousLinearCoord = secondCoordinate;
		
		blueFirstGame.makeMove(SPARROW, null, GAME_COORDINATE_ORIGIN);
		blueFirstGame.makeMove(BUTTERFLY, null, secondCoordinate);
		for(int i = 0; i < 4; i++){
			createNextLinearCoordAndMakeMove(SPARROW, blueFirstGame);
		}
		assertTrue(noExceptionOccured);
	}
	
	@Test(expected=HantoException.class)
	public void testNotPlaceButterflyByRound4Blue() throws HantoException{
		GameCoordinate secondCoordinate = new GameCoordinate(1, 0);
		previousLinearCoord = secondCoordinate;
		
		blueFirstGame.makeMove(SPARROW, null, GAME_COORDINATE_ORIGIN);
		blueFirstGame.makeMove(BUTTERFLY, null, secondCoordinate);
		for(int i = 0; i < 5; i++){
			createNextLinearCoordAndMakeMove(SPARROW, blueFirstGame);
		}
	}
	

	
	@Test
	public void testVictoryBySurroundingBlueButterfly() throws HantoException{
		GameCoordinate firstCoordinate = GAME_COORDINATE_ORIGIN;
		GameCoordinate secondCoordinate = new GameCoordinate(0, 1);
		GameCoordinate thirdCoordinate = new GameCoordinate(1, 0);
		GameCoordinate fourthCoordinate = new GameCoordinate(1, -1);
		GameCoordinate fifthCoordinate = new GameCoordinate(0, -1);
		GameCoordinate sixthCoordinate = new GameCoordinate(-1, 0);
		GameCoordinate seventhCoordinate = new GameCoordinate(-1, 1);
		blueFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(BUTTERFLY, null, secondCoordinate);
		blueFirstGame.makeMove(SPARROW, null, thirdCoordinate);
		blueFirstGame.makeMove(SPARROW, null, fourthCoordinate);
		blueFirstGame.makeMove(SPARROW, null, fifthCoordinate);
		blueFirstGame.makeMove(SPARROW, null, sixthCoordinate);
		MoveResult result = blueFirstGame.makeMove(SPARROW, null, seventhCoordinate);
		assertEquals(MoveResult.RED_WINS, result);
	}
	
	@Test
	public void testVictoryBySurroundingRedButterfly() throws HantoException{
		GameCoordinate firstCoordinate = GAME_COORDINATE_ORIGIN;
		GameCoordinate secondCoordinate = new GameCoordinate(0, 1);
		GameCoordinate thirdCoordinate = new GameCoordinate(1, 0);
		GameCoordinate fourthCoordinate = new GameCoordinate(1, 1);
		GameCoordinate fifthCoordinate = new GameCoordinate(0, 2);
		GameCoordinate sixthCoordinate = new GameCoordinate(-1, 2);
		GameCoordinate seventhCoordinate = new GameCoordinate(-1, 1);
		blueFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(BUTTERFLY, null, secondCoordinate);
		blueFirstGame.makeMove(SPARROW, null, thirdCoordinate);
		blueFirstGame.makeMove(SPARROW, null, fourthCoordinate);
		blueFirstGame.makeMove(SPARROW, null, fifthCoordinate);
		blueFirstGame.makeMove(SPARROW, null, sixthCoordinate);
		MoveResult result = blueFirstGame.makeMove(SPARROW, null, seventhCoordinate);
		assertEquals(MoveResult.BLUE_WINS, result);
	}
	
	@Test
	public void testDrawyBySurrounding2Butterflies() throws HantoException{
		GameCoordinate firstCoordinate = GAME_COORDINATE_ORIGIN;
		GameCoordinate secondCoordinate = new GameCoordinate(0, 1);
		GameCoordinate thirdCoordinate = new GameCoordinate(1, 0);
		GameCoordinate fourthCoordinate = new GameCoordinate(1, 1);
		GameCoordinate fifthCoordinate = new GameCoordinate(0, 2);
		GameCoordinate sixthCoordinate = new GameCoordinate(-1, 2);
		GameCoordinate seventhCoordinate = new GameCoordinate(-1, 0);
		GameCoordinate eighthCoordinate = new GameCoordinate(0, -1);
		GameCoordinate ninthCoordinate = new GameCoordinate(1, -1);
		GameCoordinate tenthCoordinate = new GameCoordinate(-1, 1);

		blueFirstGame.makeMove(BUTTERFLY, null, firstCoordinate);
		blueFirstGame.makeMove(BUTTERFLY, null, secondCoordinate);
		blueFirstGame.makeMove(SPARROW, null, thirdCoordinate);
		blueFirstGame.makeMove(SPARROW, null, fourthCoordinate);
		blueFirstGame.makeMove(SPARROW, null, fifthCoordinate);
		blueFirstGame.makeMove(SPARROW, null, sixthCoordinate);
		blueFirstGame.makeMove(SPARROW, null, seventhCoordinate);
		blueFirstGame.makeMove(SPARROW, null, eighthCoordinate);
		blueFirstGame.makeMove(SPARROW, null, ninthCoordinate);

		MoveResult result = blueFirstGame.makeMove(SPARROW, null, tenthCoordinate);
		assertEquals(MoveResult.DRAW, result);
	}
	
	@Test
	public void testDraw() throws HantoException{
		GameCoordinate secondCoordinate = new GameCoordinate(1, 0);
		previousLinearCoord = secondCoordinate;
		
		blueFirstGame.makeMove(BUTTERFLY, null, GAME_COORDINATE_ORIGIN);
		blueFirstGame.makeMove(BUTTERFLY, null, secondCoordinate);
		for(int i = 0; i < 9; i++){
			createNextLinearCoordAndMakeMove(SPARROW, blueFirstGame);
		}
		MoveResult result = blueFirstGame.makeMove(SPARROW, null, new GameCoordinate(0, -1));
		assertEquals(MoveResult.DRAW, result);
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
	
	@Test(expected = HantoException.class)
	public void testAttemptToMoveRatherThanPlace() throws HantoException {
		blueFirstGame.makeMove(BUTTERFLY, new GameCoordinate(0, 1),
				new GameCoordinate(0, 0));
	}
	
	private void createNextLinearCoordAndMakeMove(HantoPieceType type, HantoGame game)
		throws HantoException{
		GameCoordinate nextCoord = new GameCoordinate(previousLinearCoord.getX() + 1, 
				previousLinearCoord.getY());
		previousLinearCoord = nextCoord;
		game.makeMove(type, null, nextCoord);
	}
}
