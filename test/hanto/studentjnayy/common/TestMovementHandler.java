/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentjnayy.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentjnayy.common.GameCoordinate;
import hanto.studentjnayy.common.HantoPieceHashMap;
import hanto.studentjnayy.common.moveControllers.MoveHandler;
import hanto.studentjnayy.common.moveControllers.MovementType;
import hanto.studentjnayy.common.pieces.ButterflyPiece;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua and Yan
 * This class tests creating different pieces with the MoveHandler
 *
 */
public class TestMovementHandler {

	private MoveHandler handler;
	private HantoPieceHashMap board;
	
	@Before
	public void setUp() {
		handler = new MoveHandler();
		board = new HantoPieceHashMap();
	}
	
	@Test
	public void testSetMovementTypeWalking() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.WALK);
		board.addPiece(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.addPiece(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		
		handler.checkMovement(new GameCoordinate(0, 0), new GameCoordinate(0, 1), board, HantoPieceType.BUTTERFLY);
		assertTrue(true);
	}

	@Test
	public void testSetMovementTypeFlying() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.FLY);
		board.addPiece(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.addPiece(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		
		handler.checkMovement(new GameCoordinate(0, 0), new GameCoordinate(2, 0), board, HantoPieceType.BUTTERFLY);
		assertTrue(true);
	}
	
	@Test(expected = HantoException.class)
	public void testSetMovementTypeNoMovement() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.NO_MOVEMENT);
		board.addPiece(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.addPiece(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		
		handler.checkMovement(new GameCoordinate(0, 0), new GameCoordinate(2, 0), board, HantoPieceType.BUTTERFLY);
	}

	@Test(expected = HantoException.class)
	public void testSetMovementTypeNull() throws HantoException{
		board.addPiece(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.addPiece(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		
		handler.checkMovement(new GameCoordinate(0, 0), new GameCoordinate(2, 0), board, HantoPieceType.BUTTERFLY);
	}

	@Test
	public void testGetWalkingMoves() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.WALK);
		board.addPiece(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.addPiece(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		
		List<GameCoordinate> coords = handler.getPossibleCoordinates(HantoPieceType.BUTTERFLY, 
				new GameCoordinate(0,0), board);
		assertEquals(coords.size(), 2);
	}
	


	@Test
	public void testGetWalkingMovesSliding() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.WALK);
		board.addPiece(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.addPiece(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		board.addPiece(new GameCoordinate(-1, 1), new ButterflyPiece(HantoPlayerColor.RED));
		
		List<GameCoordinate> coords = handler.getPossibleCoordinates(HantoPieceType.BUTTERFLY, 
				new GameCoordinate(0,0), board);
		assertEquals(coords.size(), 0);
	}
	
	@Test
	public void testGetJumpingMoves() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.JUMP);
		board.addPiece(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.addPiece(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		board.addPiece(new GameCoordinate(0, 1), new ButterflyPiece(HantoPlayerColor.RED));
		
		List<GameCoordinate> coords = handler.getPossibleCoordinates(HantoPieceType.BUTTERFLY, 
				new GameCoordinate(0,0), board);
		

		assertTrue(coords.contains(new GameCoordinate(0, 2)) && coords.contains(new GameCoordinate(2, 0)));
		assertEquals(coords.size(), 2);
	}
	
	@Test
	public void testGetJumpingMovesNotContinuous() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.JUMP);
		board.addPiece(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.addPiece(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		board.addPiece(new GameCoordinate(-1, 1), new ButterflyPiece(HantoPlayerColor.RED));
		
		List<GameCoordinate> coords = handler.getPossibleCoordinates(HantoPieceType.BUTTERFLY, 
				new GameCoordinate(0,0), board);
		assertEquals(coords.size(), 0);
	}
	

	
	@Test
	public void testGetFlyingMoves() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.FLY);
		board.addPiece(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.addPiece(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		board.addPiece(new GameCoordinate(0, 1), new ButterflyPiece(HantoPlayerColor.RED));
		
		List<GameCoordinate> coords = handler.getPossibleCoordinates(HantoPieceType.BUTTERFLY, 
				new GameCoordinate(0,0), board);
		

		assertTrue(coords.contains(new GameCoordinate(0, 2)) && coords.contains(new GameCoordinate(2, 0)));
		assertEquals(coords.size(), 7);
	}
	
	@Test
	public void testGetFlyingMovesNotContinuous() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.FLY);
		board.addPiece(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.addPiece(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		board.addPiece(new GameCoordinate(-1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		
		List<GameCoordinate> coords = handler.getPossibleCoordinates(HantoPieceType.BUTTERFLY, 
				new GameCoordinate(0,0), board);
		assertEquals(coords.size(), 0);
	}
	
	@Test
	public void testGetFlyingMovesLimit1() throws HantoException{
		handler.setMovementForType(HantoPieceType.BUTTERFLY, MovementType.FLY, 1);
		board.addPiece(new GameCoordinate(0, 0), new ButterflyPiece(HantoPlayerColor.BLUE));
		board.addPiece(new GameCoordinate(1, 0), new ButterflyPiece(HantoPlayerColor.RED));
		board.addPiece(new GameCoordinate(0, 1), new ButterflyPiece(HantoPlayerColor.RED));
		
		List<GameCoordinate> coords = handler.getPossibleCoordinates(HantoPieceType.BUTTERFLY, 
				new GameCoordinate(0,0), board);
		assertEquals(coords.size(), 2);
	}
}
