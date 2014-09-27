/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************/
package hanto.studentJnaYy.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Joshua and Yan
 * This class tests different aspects of the HantoBoard object.
 *
 */
public class TestHantoBoard {

	private HantoBoard board;
	
	private static final GameCoordinate GAME_COORDINATE_ORIGIN = new GameCoordinate(0, 0);
	private static final HantoPieceType BUTTERFLY = HantoPieceType.BUTTERFLY;
	private static final HantoPieceType SPARROW = HantoPieceType.SPARROW;

	private static final HantoPlayerColor BLUE = HantoPlayerColor.BLUE;
	private static final HantoPlayerColor RED = HantoPlayerColor.RED;
	
	private static final MoveResult OK = MoveResult.OK;
	private static final MoveResult DRAW = MoveResult.DRAW;
	private static final MoveResult RED_WINS = MoveResult.RED_WINS;

	
	private static final PieceFactory FACTORY = PieceFactory.getInstance();
	private static HantoPiece blueButterfly;
	private static HantoPiece redButterfly;
	private static HantoPiece redSparrow;
	private static HantoPiece blueSparrow;

	
	private GameCoordinate previousLinearCoord;
	
	@Before
	public void setUp() {
		board = new HantoBoard(4, 1, BLUE);
		blueButterfly = FACTORY.makeGamePiece(BUTTERFLY, BLUE);
		redButterfly = FACTORY.makeGamePiece(BUTTERFLY, RED);
		redSparrow = FACTORY.makeGamePiece(SPARROW, RED);
		blueSparrow = FACTORY.makeGamePiece(SPARROW, BLUE);
		previousLinearCoord = GAME_COORDINATE_ORIGIN;
	}

	@Test
	public void testPlaceFirstPiece() throws HantoException {
		board.checkMoveValidity(GAME_COORDINATE_ORIGIN, BUTTERFLY, BLUE);
		assertTrue(true);
	}
	
	@Test(expected=HantoException.class)
	public void testPlaceFirstPieceOffOrigin() throws HantoException {
		board.checkMoveValidity(new GameCoordinate(2, 2), BUTTERFLY, BLUE);
	}
	
	@Test(expected=HantoException.class)
	public void testPlaceSecondPieceOnOrigin() throws HantoException{
		board.addPiece(GAME_COORDINATE_ORIGIN, blueButterfly);
		board.checkMoveValidity(GAME_COORDINATE_ORIGIN, BUTTERFLY, RED);
	}
	
	@Test
	public void testPlaceSecondPieceOffOrigin() throws HantoException{
		board.addPiece(GAME_COORDINATE_ORIGIN, FACTORY.makeGamePiece(BUTTERFLY, BLUE));
		board.checkMoveValidity(new GameCoordinate(0, 1), BUTTERFLY, RED);
		assertTrue(true);
	}
	
	@Test(expected=HantoException.class)
	public void testPlaceSecondPieceOffOriginFail() throws HantoException{
		board.addPiece(GAME_COORDINATE_ORIGIN, FACTORY.makeGamePiece(BUTTERFLY, BLUE));
		board.addPiece(new GameCoordinate(3, 1),  FACTORY.makeGamePiece(BUTTERFLY, RED));
		board.checkPieceConnectivity();
	}
	
	@Test
	public void testAddPieceReturnsOK() throws HantoException{
		MoveResult result = board.addPiece(GAME_COORDINATE_ORIGIN, blueButterfly);
		assertEquals(OK, result);
	}
	
	@Test
	public void testAddPieceReturnsDraw() throws HantoException{
		MoveResult result;
		board.addPiece(GAME_COORDINATE_ORIGIN, blueButterfly);
		createNextLinearCoordAndMakeMove(redButterfly);
		createNextLinearCoordAndMakeMove(blueSparrow);
		createNextLinearCoordAndMakeMove(redSparrow);
		createNextLinearCoordAndMakeMove(blueSparrow);
		createNextLinearCoordAndMakeMove(redSparrow);
		createNextLinearCoordAndMakeMove(blueSparrow);
		result = createNextLinearCoordAndMakeMove(redSparrow);

		assertEquals(DRAW, result);
	}
	
	@Test
	public void testAddPieceReturnsWin() throws HantoException{
		MoveResult result;
		board.addPiece(GAME_COORDINATE_ORIGIN, blueButterfly);
		board.addPiece(new GameCoordinate(1, 0), redButterfly);
		board.addPiece(new GameCoordinate(1, -1), blueSparrow);
		board.addPiece(new GameCoordinate(-1, 0), redSparrow);
		board.addPiece(new GameCoordinate(-1, 1), blueSparrow);
		board.addPiece(new GameCoordinate(0, 1), redSparrow);
		result = board.addPiece(new GameCoordinate(0, -1), blueSparrow);
		assertEquals(RED_WINS, result);

	}
	
	@Test(expected=HantoException.class)
	public void testMoveValidNoButterflyBlue() throws HantoException{
		board.addPiece(GAME_COORDINATE_ORIGIN, blueSparrow);
		createNextLinearCoordAndMakeMove(redButterfly);
		board.checkMoveValidity(getNextLinearCoord(), SPARROW, BLUE);
	}
	
	@Test(expected = HantoException.class)
	public void testMoveValidNoButterflyRed() throws HantoException{
		board.addPiece(GAME_COORDINATE_ORIGIN, blueButterfly);
		createNextLinearCoordAndMakeMove(redSparrow);
		createNextLinearCoordAndMakeMove(blueSparrow);
		board.checkMoveValidity(getNextLinearCoord(), SPARROW, RED);	
	}
	
	@Test
	public void testMoveValidButterflyBlue() throws HantoException{
		board.addPiece(GAME_COORDINATE_ORIGIN, blueSparrow);
		createNextLinearCoordAndMakeMove(redButterfly);
		board.checkMoveValidity(getNextLinearCoord(), BUTTERFLY, BLUE);
		assertTrue(true);
	}
	
	@Test
	public void testMoveValidButterflyRed() throws HantoException{
		board.addPiece(GAME_COORDINATE_ORIGIN, blueButterfly);
		createNextLinearCoordAndMakeMove(redSparrow);
		createNextLinearCoordAndMakeMove(blueSparrow);
		board.checkMoveValidity(getNextLinearCoord(), BUTTERFLY, RED);	
		assertTrue(true);
	}
	
	@Test
	public void testGetPieceAt() throws HantoException{
		board.addPiece(GAME_COORDINATE_ORIGIN, blueButterfly);
		HantoPiece returnedPiece = board.getPieceAt(GAME_COORDINATE_ORIGIN);
		assertEquals(blueButterfly, returnedPiece);
	}
	
	@Test
	public void testGetPrintableBoard() throws HantoException{
		board.addPiece(GAME_COORDINATE_ORIGIN, blueButterfly);
		String printableBoard = board.getPrintableBoard();
		assertNotNull(printableBoard);
	}
	
	@Test(expected=HantoException.class)
	public void testFailMove() throws HantoException{
		board.addPiece(new GameCoordinate(1, 1), blueButterfly);
	}
	
	private MoveResult createNextLinearCoordAndMakeMove(HantoPiece piece)
		throws HantoException{
		GameCoordinate nextCoord = getNextLinearCoord();
		previousLinearCoord = nextCoord;
		return board.addPiece(nextCoord, piece);
	}

	/**
	 * @return
	 */
	private GameCoordinate getNextLinearCoord() {
		GameCoordinate nextCoord = new GameCoordinate(previousLinearCoord.getX() + 1, 
				previousLinearCoord.getY());
		return nextCoord;
	}
	

}
