/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.tournament;

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentJnaYy.common.HantoColorHelper;
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
	private HantoPlayerColor myColor;

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
		this.myColor = myColor;
		if(doIMoveFirst){
			startColor = myColor;
		}
		else{
			startColor = HantoColorHelper.getOppositeColor(myColor);
		}
		
		gameController = HantoGameControllerFactory.getInstance().getGameController(version, startColor, myColor);
	}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		HantoMoveRecord move = null;
		try{
			if(opponentsMove != null){
				gameController.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
			}
		} catch (HantoException e){
			e.printStackTrace();
			//This should never happen, assuming we've implemented all of the rules correctly.
			System.out.println("The opponent's move failed in our game..");
		}
		
		try{
			move = gameController.getBestMove();
			System.out.println(myColor + "'s move: " + move.getPiece() + " " + move.getFrom() + " " + move.getTo());

			gameController.makeMove(move.getPiece(), move.getFrom(), move.getTo());
		} catch (HantoException e){
			//Assuming our controller is perfect, this should never be called, unless
			//someone calls makeMove after a game has ended.
			e.printStackTrace();
			System.out.println(myColor + "'s move failed in its game..");
		}
		return move;
	}

}
