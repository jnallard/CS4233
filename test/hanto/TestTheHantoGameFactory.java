/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto;

import static org.junit.Assert.*;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.HantoGameFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua and Yan
 * This class tests creating different games with the HantoGameFactory
 *
 */
public class TestTheHantoGameFactory {

	private HantoGameFactory factory;
	
	@Before
	public void setUp() {
		factory = HantoGameFactory.getInstance();
	}

	@Test
	public void testGetInstance() {
		assertNotNull(factory);
	}

	@Test
	public void testMakeAlphaHantoGameHantoGameID() {
		HantoGame game = factory.makeHantoGame(HantoGameID.ALPHA_HANTO);
		assertNotNull(game);
	}

	@Test
	public void testMakeAlphaHantoGameHantoGameIDHantoPlayerColor() {
		HantoGame game = factory.makeHantoGame(HantoGameID.ALPHA_HANTO, HantoPlayerColor.RED);
		assertNotNull(game);
	}

	@Test
	public void testMakeBetaHantoGameHantoGameID() {
		HantoGame game = factory.makeHantoGame(HantoGameID.BETA_HANTO);
		assertNotNull(game);
	}

	@Test
	public void testMakeBetaHantoGameHantoGameIDHantoPlayerColor() {
		HantoGame game = factory.makeHantoGame(HantoGameID.BETA_HANTO, HantoPlayerColor.RED);
		assertNotNull(game);
	}

}
