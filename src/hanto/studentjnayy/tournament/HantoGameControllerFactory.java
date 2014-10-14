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

import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentjnayy.epsilon.EpsilonHantoGameController;

/**
 * This class allows a GameController to be made for specific game versions.
 * @author Joshua and Yan
 *
 */
public class HantoGameControllerFactory {
	
	private static HantoGameControllerFactory instance = new HantoGameControllerFactory();
	
	/**
	 * @return the static instance of the HantoGameControllerFactory
	 */
	public static HantoGameControllerFactory getInstance(){
		return instance;
	}
	
	/**
	 * Gets the specific game controller for each game
	 * @param version the game to get a controller for.
	 * @param movesFirst the player that moves first in the game
	 * @param controllerColor the player that the controller is configured for
	 * @return the game controller for the game and player
	 */
	public HantoGameController getGameController(HantoGameID version, HantoPlayerColor movesFirst, HantoPlayerColor controllerColor){
		HantoGameController controller = null;
		switch(version){
		case EPSILON_HANTO:
			controller = new EpsilonHantoGameController(movesFirst, controllerColor);
			break;
		default:
			break;
		
		}
		return controller;
	}

}
