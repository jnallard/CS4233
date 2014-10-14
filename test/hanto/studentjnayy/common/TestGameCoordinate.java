/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************/
package hanto.studentjnayy.common;

import static org.junit.Assert.*;
import hanto.studentjnayy.common.GameCoordinate;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Joshua and Yan
 * This class tests different aspects of the GameCoordinate object.
 *
 */
public class TestGameCoordinate {

	private GameCoordinate coordinateOrigin;
	private GameCoordinate coordinate2;
	private List<GameCoordinate> expectedNeighborsForOrigin;
	
	@Before
	public void setUp() {
		coordinateOrigin = new GameCoordinate(0, 0);
		coordinate2 = new GameCoordinate(1, -2);

		expectedNeighborsForOrigin = new ArrayList<GameCoordinate>();
		expectedNeighborsForOrigin.add(new GameCoordinate(1, 0));
		expectedNeighborsForOrigin.add(new GameCoordinate(-1, 0));
		expectedNeighborsForOrigin.add(new GameCoordinate(0, 1));
		expectedNeighborsForOrigin.add(new GameCoordinate(0, -1));
		expectedNeighborsForOrigin.add(new GameCoordinate(1, -1));
		expectedNeighborsForOrigin.add(new GameCoordinate(-1, 1));
	}

	@Test
	public void testGetX() {
		assertEquals(0, coordinateOrigin.getX());
	}

	@Test
	public void testGetY() {
		assertEquals(0, coordinateOrigin.getY());
	}

	@Test
	public void testEqualsTrue() {
		assertTrue(coordinateOrigin.equals(new GameCoordinate(0, 0)));
	}
	

	@Test
	public void testEqualsDifferentCoord() {
		assertFalse(coordinateOrigin.equals(new GameCoordinate(1, 0)));
	}
	
	@Test
	public void testEqualsDifferentObject() {
		assertFalse(coordinateOrigin.equals(1));
	}
	
	@Test
	public void testHashCodeOrigin() {
		
		assertEquals(0, coordinateOrigin.hashCode());
	}
	
	@Test
	public void testHashCodeNotOrigin() {
		
		assertEquals(131070, coordinate2.hashCode());
	}
	
	@Test
	public void testAreAdjacent() {
		boolean areAdjacent = true;
		for(int i = 0; i < 6; i++){
			areAdjacent &= expectedNeighborsForOrigin.get(i).isAdjacent(coordinateOrigin);
		}
		
		assertTrue(areAdjacent);
	}
	

	
	@Test
	public void testIsAdjacentFalse() {
		
		assertFalse(coordinateOrigin.isAdjacent(new GameCoordinate(5, 5)));
	}
	
	
	@Test
	public void testGetAdjacentCoords() {
		List<GameCoordinate> neighbors = coordinateOrigin.getAdjacentCoordinates();
		
		assertTrue(expectedNeighborsForOrigin.containsAll(neighbors));
	}
	
	@Test
	public void testGetStraightLineVertical() {
		
		assertTrue(coordinateOrigin.isStraightLine(new GameCoordinate(0, 5)));
	}
	
	@Test
	public void testGetStraightLineUpDiagonal() {
		
		assertTrue(coordinateOrigin.isStraightLine(new GameCoordinate(-5, 0)));
	}
	
	@Test
	public void testGetStraightDownDiagonal() {
		
		assertTrue(coordinateOrigin.isStraightLine(new GameCoordinate(-2, 2)));
	}
	
	@Test
	public void testGetStraightLineFail() {
		
		assertFalse(coordinateOrigin.isStraightLine(new GameCoordinate(5, 4)));
	}
	

	
	@Test
	public void testGetStraightLineCoordsVertical() {
		
		assertEquals(coordinateOrigin.getStraightLineCoordsBetween(new GameCoordinate(0, 5)).size(), 4);
	}
	
	@Test
	public void testGetStraightLineCoordsUpDiagonal() {
		
		assertEquals(coordinateOrigin.getStraightLineCoordsBetween(new GameCoordinate(-5, 0)).size(), 4);
	}
	
	@Test
	public void testGetStraightLineCoordsDownDiagonal() {
		
		assertEquals(coordinateOrigin.getStraightLineCoordsBetween(new GameCoordinate(-2, 2)).size(), 1);
	}
	
	@Test
	public void testGetStraightLineCoordsFail() {
		
		assertEquals(coordinateOrigin.getStraightLineCoordsBetween(new GameCoordinate(5, 4)).size(), 0);
	}
	
	@Test
	public void testCopy() {
		GameCoordinate copy = new GameCoordinate(coordinateOrigin);
		assertEquals(coordinateOrigin, copy);
	}

}
