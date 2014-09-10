/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************/
package hanto.studentJnaYy.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Joshua and Yan
 * This class tests different aspects of the GameCoordinate object.
 *
 */
public class TestGameCoordinate {

	private GameCoordinate coordinate;
	
	@Before
	public void setUp() {
		coordinate = new GameCoordinate(0, 0);
	}

	@Test
	public void testGetX() {
		assertEquals(0, coordinate.getX());
	}

	@Test
	public void testGetY() {
		assertEquals(0, coordinate.getY());
	}

	@Test
	public void testEqualsTrue() {
		assertTrue(coordinate.equals(new GameCoordinate(0, 0)));
	}
	

	@Test
	public void testEqualsDifferentCoord() {
		assertFalse(coordinate.equals(new GameCoordinate(1, 0)));
	}
	
	@Test
	public void testEqualsDifferentObject() {
		assertFalse(coordinate.equals(1));
	}

}
