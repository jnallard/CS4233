/**
 * 
 */
package hanto.studentJnaYy.alpha;

import static org.junit.Assert.*;
import hanto.HantoGameFactory;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua
 *
 */
public class TestAlphaHantoGame {
	private HantoGame game;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		HantoGameFactory factory = HantoGameFactory.getInstance();
		game = factory.makeHantoGame(HantoGameID.ALPHA_HANTO);
	}

	@Test
	public void testCreateNewGame() {
		boolean isAlphaGame = game instanceof AlphaHantoGame;
		assertTrue(isAlphaGame);
	}

}
