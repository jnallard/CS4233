/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************/
package hanto.studentjnayy.tournament;

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentjnayy.common.GameCoordinate;
import hanto.studentjnayy.common.HantoColorHelper;
import hanto.studentjnayy.epsilon.EpsilonHantoGameController;
import hanto.studentjnayy.tournament.HantoPlayer;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

import org.junit.Before;
import org.junit.Test;

public class TestHantoPlayer {
	private static final HantoPieceType SPARROW = HantoPieceType.SPARROW;
	private static final HantoPieceType BUTTERFLY = HantoPieceType.BUTTERFLY;
	private static final HantoPieceType CRAB = HantoPieceType.CRAB;
	private final GameCoordinate GAME_COORDINATE_ORIGIN = new GameCoordinate(0, 0);
	private HantoGamePlayer hantoPlayer;
	
	@Before
	public void setUp() throws Exception {
		hantoPlayer = new HantoPlayer();
		hantoPlayer.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED, true);
	}

	@Test
	public void testMakePlayer() {
		assertNotNull(hantoPlayer);
	}
	
	@Test
	public void testSwitchColor() {
		assertEquals(new HantoColorHelper().getOppositeColor(HantoPlayerColor.BLUE), HantoPlayerColor.RED);
	}
	
	@Test
	public void testRunGame() {
		assertTrue(new TestTournament().runGame());
	}
	

	
	@Test
	public void testFailedOpponentsMove() {
		HantoMoveRecord move = new HantoMoveRecord(BUTTERFLY, null, new GameCoordinate(0, 1));
		hantoPlayer.makeMove(move);
		assertTrue(true);
	}
	
	@Test
	public void testFailedOurMove() {
		TestTournament tournament = new TestTournament();
		tournament.runGame();
		tournament.getRedPlayer().makeMove(null);
		tournament.getBluePlayer().makeMove(null);
		assertTrue(true);
	}
	
	@Test
	public void testEpsilonController() throws HantoException {
		EpsilonHantoGameController controller = new EpsilonHantoGameController(HantoPlayerColor.RED, HantoPlayerColor.BLUE);
		controller.makeMove(BUTTERFLY, null, GAME_COORDINATE_ORIGIN);
		controller.makeMove(BUTTERFLY, null, new GameCoordinate(0, 1));
		
		controller.setChanceOfPickingMoveOverPlace(0);
		controller.getBestMove();
		controller.getGreedyBestMoveOrProtectButterfly();
		controller.getGreedyBestMove();
		controller.getRandomMove();
		
		controller.setChanceOfPickingMoveOverPlace(1);
		controller.getBestMove();
		controller.getGreedyBestMoveOrProtectButterfly();
		controller.getGreedyBestMove();
		controller.getRandomMove();
		
		assertNotNull(controller.getPrintableBoard());
		assertEquals(controller.getPieceAt(GAME_COORDINATE_ORIGIN).getType(), BUTTERFLY);
		assertTrue(true);
	}
	
	@Test
	public void testControllerResigns() throws HantoException {
		EpsilonHantoGameController controller = new EpsilonHantoGameController(HantoPlayerColor.RED, HantoPlayerColor.BLUE);
		controller.makeMove(BUTTERFLY, null, GAME_COORDINATE_ORIGIN);
		controller.makeMove(BUTTERFLY, null, new GameCoordinate(0, 1));
		controller.makeMove(BUTTERFLY, GAME_COORDINATE_ORIGIN, new GameCoordinate(1, 0));
		controller.makeMove(SPARROW, null, new GameCoordinate(-1, 1));
		controller.makeMove(BUTTERFLY, new GameCoordinate(1, 0), GAME_COORDINATE_ORIGIN);
		controller.makeMove(SPARROW, new GameCoordinate(-1, 1), new GameCoordinate(0, -1));
		
		assertTrue(isResign(controller.getBestMove()));
		assertTrue(isResign(controller.getGreedyBestMoveOrProtectButterfly()));
		assertTrue(isResign(controller.getGreedyBestMove()));
		assertTrue(isResign(controller.getRandomMove()));
	}

	private boolean isResign(HantoMoveRecord move) {
		return move.getFrom() == null && move.getPiece() == null && move.getTo() == null;
	}
}
