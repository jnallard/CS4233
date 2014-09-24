package hanto.studentJnaYy.gamma;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
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
	private HantoTestGame redFirstGame;
	private HantoTestGame blueFirstGame;
	private static final HantoPieceType SPARROW = HantoPieceType.SPARROW;
	private static final HantoPieceType BUTTERFLY = HantoPieceType.BUTTERFLY;
	private final GameCoordinate GAME_COORDINATE_ORIGIN = new GameCoordinate(0, 0);
	
	@Before
	public void setUp() throws Exception {
		redFirstGame = HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO, 
				RED);
		blueFirstGame = HantoTestGameFactory.getInstance().makeTestHantoGame(HantoGameID.GAMMA_HANTO);
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
		
		blueFirstGame.initializeBoard(pieces);
		blueFirstGame.setTurnNumber(1);
		blueFirstGame.setPlayerMoving(BLUE);
		blueFirstGame.makeMove(SPARROW, null, new GameCoordinate(0, 2));
	}
	
	@Test(expected=HantoException.class)
	public void testPlaceRedButterflyNextToBlue() throws HantoException{

		PieceLocationPair[] pieces = {
				new PieceLocationPair(RED, SPARROW, GAME_COORDINATE_ORIGIN),
				new PieceLocationPair(BLUE, BUTTERFLY, new GameCoordinate(0, 1)),
		};
		
		blueFirstGame.initializeBoard(pieces);
		blueFirstGame.setTurnNumber(1);
		blueFirstGame.setPlayerMoving(RED);
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

}
