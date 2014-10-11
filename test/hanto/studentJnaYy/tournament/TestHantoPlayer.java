/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************/
package hanto.studentJnaYy.tournament;

import static org.junit.Assert.*;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.common.GameCoordinate;
import hanto.tournament.HantoGamePlayer;

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
	}

	@Test
	public void testMakePlayer() {
		assertNotNull(hantoPlayer);
		hantoPlayer.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED, true);
	}
	
	@Test
	public void testRunGame() {
		assertTrue(new TestTournament().runGame());
	}
	
	
}
