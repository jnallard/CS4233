/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentjnayy.tournament;

import hanto.common.HantoGame;
import hanto.tournament.HantoMoveRecord;

/**
 * This interface is used for creating controllers that a HantoPlayer can use to generate 
 * moves for a game.
 * @author Joshua and Yan
 *
 */
public interface HantoGameController extends HantoGame {
	
	/**
	 * Returns the "best" move for a game, as determined
	 * by the HantoGameController
	 * @return A HantoMoveRecord representing the "best" move
	 */
	HantoMoveRecord getBestMove();

}
