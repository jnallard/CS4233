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

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentjnayy.common.HantoColorHelper;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

/**
 * This class implements the HantoGamePlayer interface, and is used for making moves.
 * It uses a HantoGameController to determine the best move.
 * @author Joshua and Yan
 *
 */
public class HantoPlayer implements HantoGamePlayer {

	private HantoGameController gameController;

	/**
	 * Initializes a game for the HantoPlayer, creating a HantoGameController, to be 
	 * used for determining moves.
	 * @param version the game version, determining which controller to use
	 * @param myColor the player's HantoPlayerColor
	 * @param doIMoveFirst whether or not the player moves first
	 */
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst) {
		HantoPlayerColor startColor = myColor;
		if(doIMoveFirst){
			startColor = myColor;
		}
		else{
			startColor = HantoColorHelper.getOppositeColor(myColor);
		}
		
		gameController = HantoGameControllerFactory.getInstance().getGameController(version, startColor, myColor);
	}

	/**
	 * Make the player's next move.
	 * @param opponentsMove this is the result of the opponent's last move, in response
	 * 	to your last move. This will be null if you are making the first move of the game.
	 * @return your move
	 */
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		HantoMoveRecord move = null;
		try{
			if(opponentsMove != null){
				gameController.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
			}
		} catch (HantoException e){
			//This should never happen, assuming we've implemented all of the rules correctly.
			move = new HantoMoveRecord(null, null, null);
		}
		
		try{
			move = gameController.getBestMove();

			gameController.makeMove(move.getPiece(), move.getFrom(), move.getTo());
		} catch (HantoException e){
			//Assuming our controller is perfect, this should never be called, unless
			//someone calls makeMove after a game has ended.
			move = new HantoMoveRecord(null, null, null);
		}
		return move;
	}

}
