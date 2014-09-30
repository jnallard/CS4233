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

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.common.moveControllers.MoveHandler;
import hanto.studentJnaYy.common.moveControllers.MovementType;
import hanto.studentJnaYy.common.moveControllers.Walking;
import hanto.studentJnaYy.common.pieces.ButterflyPiece;
import hanto.studentJnaYy.common.pieces.PieceFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua and Yan
 * This class tests creating different pieces with the MoveHandler
 *
 */
public class TestMovementHandler {

	private MoveHandler handler;
	private Map<GameCoordinate, HantoPiece> board;
	
	@Before
	public void setUp() {
		handler = new MoveHandler();
		board = new HashMap<GameCoordinate, HantoPiece>();
	}
	
	@Test
	public void testSetMovementTypeWalking() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.WALK);
		board.put(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.put(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		
		handler.checkMovement(new GameCoordinate(0, 0), new GameCoordinate(0, 1), board, HantoPieceType.BUTTERFLY);
		assertTrue(true);
	}

	@Test
	public void testSetMovementTypeFlying() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.FLY);
		board.put(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.put(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		
		handler.checkMovement(new GameCoordinate(0, 0), new GameCoordinate(2, 0), board, HantoPieceType.BUTTERFLY);
		assertTrue(true);
	}

	@Test(expected = HantoException.class)
	public void testSetMovementTypeNull() throws HantoException{
		board.put(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.put(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		
		handler.checkMovement(new GameCoordinate(0, 0), new GameCoordinate(2, 0), board, HantoPieceType.BUTTERFLY);
	}
}
