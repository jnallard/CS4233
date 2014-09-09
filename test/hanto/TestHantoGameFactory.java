/**
 * 
 */
package hanto;

import static org.junit.Assert.*;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joshua
 *
 */
public class TestHantoGameFactory {

	private HantoGameFactory factory;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		factory = HantoGameFactory.getInstance();
	}

	/**
	 * Test method for {@link hanto.HantoGameFactory#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		assertNotNull(factory);
	}

	/**
	 * Test method for {@link hanto.HantoGameFactory#makeHantoGame(hanto.common.HantoGameID)}.
	 */
	@Test
	public void testMakeAlpahHantoGameHantoGameID() {
		HantoGame game = factory.makeHantoGame(HantoGameID.ALPHA_HANTO);
		assertNotNull(game);
	}

	/**
	 * Test method for {@link hanto.HantoGameFactory#makeHantoGame(hanto.common.HantoGameID, hanto.common.HantoPlayerColor)}.
	 */
	@Test
	public void testMakeAlphaHantoGameHantoGameIDHantoPlayerColor() {
		HantoGame game = factory.makeHantoGame(HantoGameID.ALPHA_HANTO, HantoPlayerColor.RED);
		assertNotNull(game);
	}

}
