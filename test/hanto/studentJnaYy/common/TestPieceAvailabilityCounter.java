/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************/
package hanto.studentJnaYy.common;

import static org.junit.Assert.assertFalse;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Joshua and Yan
 * This class tests different aspects of the PieceAvailabilityCounter object.
 *
 */
public class TestPieceAvailabilityCounter {

	private static final HantoPieceType SPARROW = HantoPieceType.SPARROW;
	private static final HantoPieceType BUTTERFLY = HantoPieceType.BUTTERFLY;
	private static final HantoPlayerColor BLUE = HantoPlayerColor.BLUE;
	private PieceAvailabilityCounter counter;
	
	@Before
	public void setUp() {
		counter = new PieceAvailabilityCounter();
		counter.initializePieceCount(BUTTERFLY, 1);
		counter.initializePieceCount(SPARROW, 2);
	}

	@Test
	public void testNotInitialized () {
		assertFalse(counter.isPieceAvailable(HantoPieceType.CRAB, BLUE));
	}
	
	@Test
	public void testCounterRunsDown () throws HantoException {
		counter.utilizePiece(BUTTERFLY, BLUE);
		assertFalse(counter.isPieceAvailable(BUTTERFLY, BLUE));
	}
	
	
	@Test(expected=HantoException.class)
	public void testUtilizeFails () throws HantoException {
		counter.utilizePiece(BUTTERFLY, BLUE);
		counter.utilizePiece(BUTTERFLY, BLUE);
	}
	
	@Test
	public void testErrorMessage () throws HantoException {
		counter.utilizePiece(BUTTERFLY, BLUE);
		counter.isPieceAvailable(BUTTERFLY, BLUE);
		String errorMessage = counter.getErrorMessage();
		assertFalse(errorMessage.isEmpty());
	}

}
