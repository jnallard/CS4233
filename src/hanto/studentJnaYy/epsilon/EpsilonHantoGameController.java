/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.epsilon;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentJnaYy.common.GameCoordinate;
import hanto.studentJnaYy.common.HantoColorHelper;
import hanto.studentJnaYy.tournament.HantoGameController;
import hanto.tournament.HantoMoveRecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for reporting moves that the HantoPlayer can use
 * This one is designed for an Epsilon game.
 * @author Joshua and Yan
 *
 */
public class EpsilonHantoGameController implements HantoGameController {
	private static final int ONLY_PLACEMENT_TURNS = 2;
	private double chanceOfPickingMoveOverPlace = 0.50;
	private EpsilonHantoGame game;
	private HantoPlayerColor controllerColor;
	private HantoPlayerColor opposingColor;
	
	/**
	 * This class is used for randomly pulling an item from a collection
	 * @param <itemType> is the type of items for the collection.
	 * @author Joshua and Yan
	 *
	 */
	private class RandomGenerator<itemType>{
		
		/**
		 * This function randomly pulls an item from a collection
		 * @param items the collection to select from
		 * @return the random item
		 */
		public itemType getRandomFromCollection(Collection<itemType> items) {
			int randomIndex1 = (int) (Math.random() * items.size());
			itemType item = new ArrayList<itemType>(items).get(randomIndex1);
			return item;
		}
	};

	/**
	 * Creates an epsilon game controller
	 * @param movesFirst the player that moves first
	 * @param controllerColor the player that this controller is being used by
	 */
	public EpsilonHantoGameController(HantoPlayerColor movesFirst,
			HantoPlayerColor controllerColor) {
		game = new EpsilonHantoGame(movesFirst);
		this.controllerColor = controllerColor;
		opposingColor = HantoColorHelper.getOppositeColor(controllerColor);
	}

	/**
	 * This method executes a move in the game. It is called for every move that must be
	 * made.
	 * 
	 * @param pieceType
	 *            the piece type that is being moved
	 * @param from
	 *            the coordinate where the piece begins. If the coordinate is null, then
	 *            the piece begins off the board (that is, it is placed on the board in
	 *            this move).
	 * @param to
	 *            the coordinated where the piece is after the move has been made.
	 * @return the result of the move
	 * @throws HantoException
	 *             if there are any problems in making the move (such as specifying a
	 *             coordinate that does not have the appropriate piece, or the color of
	 *             the piece is not the color of the player who is moving.
	 */
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		return game.makeMove(pieceType, from, to);
	}

	/**
	 * @param where the coordinate to query
	 * @return the piece at the specified coordinate or null if there is no 
	 * 	piece at that position
	 */
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return game.getPieceAt(where);
	}

	/**
	 * @return a printable representation of the board.
	 */
	public String getPrintableBoard() {
		return game.getPrintableBoard();
	}

	/**
	 * Returns the "best" move for the player, as determined
	 * by certain algorithms.
	 */
	public HantoMoveRecord getBestMove() {
		if(controllerColor == HantoPlayerColor.BLUE){
			return getGreedyBestMove();
		}
		else{
			return getGreedyBestMoveOrProtectButterfly();
		}
	}

	/**
	 * Returns a random move - use the setChanceOfPickingMoveOverPlace to
	 * change the chance of placing versus moving
	 * @return a random possible move
	 */
	public HantoMoveRecord getRandomMove() {
		HantoMoveRecord move = null;

		double random = Math.random();
		boolean randomBoolean = random > chanceOfPickingMoveOverPlace;
		Map<GameCoordinate, List<GameCoordinate>> moves = game.getAllMovesForCurrentPlayer();
		Map<HantoPieceType, List<GameCoordinate>> placements = game.getAllPlacementsForCurrentPlayer();
		
		if (placements.isEmpty() && moves.isEmpty()) {
			
			move = new HantoMoveRecord(null, null, null);
			
		} else if ((randomBoolean && !placements.isEmpty()) || moves.isEmpty()) {
			
			HantoPieceType type = new RandomGenerator<HantoPieceType>().getRandomFromCollection(placements.keySet());
			GameCoordinate to = new RandomGenerator<GameCoordinate>().getRandomFromCollection(placements.get(type));
			move = new HantoMoveRecord(type, null, to);
			
		} else {
			
			GameCoordinate from = new RandomGenerator<GameCoordinate>().getRandomFromCollection(moves.keySet());
			GameCoordinate to = new RandomGenerator<GameCoordinate>().getRandomFromCollection(moves.get(from));
			HantoPieceType type = game.getPieceAt(from).getType();
			move = new HantoMoveRecord(type, from, to);

		}
		
		return move;
	}

	/**
	 * Returns a slightly less random move - use the setChanceOfPickingMoveOverPlace to
	 * change the chance of placing versus moving
	 * The function tries to find a move that will bring the pieces closer to the opponents piece.
	 * @return the move that will be closest to the enemy's butterfly
	 */
	public HantoMoveRecord getGreedyBestMove() {
		HantoMoveRecord move = null;

		Map<GameCoordinate, List<GameCoordinate>> moves = game.getAllMovesForCurrentPlayer();
		Map<HantoPieceType, List<GameCoordinate>> placements = game.getAllPlacementsForCurrentPlayer();

		move = getPlacementOrMove(moves, placements, true);
		return move;
	}
	
	/**
	 * Returns a slightly less random move - use the setChanceOfPickingMoveOverPlace to
	 * change the chance of placing versus moving
	 * The function tries to find a move that will bring the pieces closer to the opponents piece.
	 * @return the move that will be closest to the enemy's butterfly
	 */
	public HantoMoveRecord getGreedyBestMoveOrProtectButterfly() {
		HantoMoveRecord move = null;
		HantoMoveRecord butterflyMove = null;

		Map<GameCoordinate, List<GameCoordinate>> moves = game.getAllMovesForCurrentPlayer();
		Map<HantoPieceType, List<GameCoordinate>> placements = game.getAllPlacementsForCurrentPlayer();


		GameCoordinate ourButterfly = game.getButterflyCoordinate(opposingColor);
		List<GameCoordinate> openSpots = new ArrayList<GameCoordinate>();
		if(ourButterfly == null){
			openSpots = placements.get(HantoPieceType.BUTTERFLY);
		}
		else{
			openSpots = moves.get(ourButterfly);
		}

		butterflyMove = getGreedyButterflyAction(openSpots);


		move = getPlacementOrMove(moves, placements, false);
		
		return getBetterMove(move, butterflyMove);
	}

	/**
	 * Returns a move or placement, based upon chance.
	 * @param moves the possible moves for every available piece
	 * @param placements the possible placements left
	 * @return the move or placement that was considered best.
	 */
	private HantoMoveRecord getPlacementOrMove(Map<GameCoordinate, List<GameCoordinate>> moves,
			Map<HantoPieceType, List<GameCoordinate>> placements, boolean isButterflyIncluded) {
		
		HantoMoveRecord move;
		GameCoordinate opposingButterfly = game.getButterflyCoordinate(opposingColor);


		double random = Math.random();
		boolean randomBoolean = random > chanceOfPickingMoveOverPlace;
		if (placements.isEmpty() && moves.isEmpty()) {
			move = new HantoMoveRecord(null, null, null);
		} else if ((randomBoolean && !placements.isEmpty()) || moves.isEmpty() || game.getTurnCount() <= ONLY_PLACEMENT_TURNS) {
			move = getGreedyPlacementOption(placements, opposingButterfly, isButterflyIncluded);
		} else {

			move = getGreedyMoveOption(moves, opposingButterfly, isButterflyIncluded);
		}
		return move;
	}

	/**
	 * Returns the best placement option for a player (does not account for piece type)
	 * @param placements the possible spots to place a piece
	 * @param opposingButterfly the location of the other butterfly
	 * @param isButterflyIncluded whether or not to include the player's own butterfly
	 * @return the move that is considered best
	 */
	public HantoMoveRecord getGreedyPlacementOption(Map<HantoPieceType, List<GameCoordinate>> placements,
			GameCoordinate opposingButterfly, boolean isButterflyIncluded) {
		HantoMoveRecord move;
		GameCoordinate bestTo = null;
		int bestDistance = Integer.MAX_VALUE;
		for (GameCoordinate coord : new ArrayList<List<GameCoordinate>>(
				placements.values()).get(0)) {

			int distanceToOpposingButterfly = Integer.MAX_VALUE;
			if (opposingButterfly != null) {
				distanceToOpposingButterfly = game.getButterflyCoordinate(
						opposingColor).getDistance(coord);
			}

			if (bestTo == null || distanceToOpposingButterfly <= bestDistance) {
				bestTo = coord;
				bestDistance = distanceToOpposingButterfly;
			}

		}
		
		if(!isButterflyIncluded && placements.containsKey(HantoPieceType.BUTTERFLY) && placements.size() > 1){
			placements.remove(HantoPieceType.BUTTERFLY);
		}
		
		RandomGenerator<HantoPieceType> pieceGenerator =  new RandomGenerator<HantoPieceType>();
		HantoPieceType type = pieceGenerator.getRandomFromCollection(placements.keySet());

		move = new HantoMoveRecord(type, null, bestTo);
		return move;
	}

	/**
	 * Returns the best movement option for a player
	 * @param moves the moves every available piece can make
	 * @param opposingButterfly the location of the other butterfly
	 * @param isButterflyIncluded whether or not to include the player's own butterfly
	 * @return the move that is considered best
	 */
	public HantoMoveRecord getGreedyMoveOption(Map<GameCoordinate, List<GameCoordinate>> moves,
			GameCoordinate opposingButterfly, boolean isButterflyIncluded) {
		HantoMoveRecord move;
		GameCoordinate bestTo = null;
		GameCoordinate bestFrom = null;
		int bestDistance = Integer.MAX_VALUE;
		for (GameCoordinate fromCoord : moves.keySet()) {
			
			if(game.getPieceAt(fromCoord).getType() == HantoPieceType.BUTTERFLY 
					&& !isButterflyIncluded && moves.size() > 1){
				
				continue; 
			}
			
			int currentDistance = Integer.MAX_VALUE;
			
			for (GameCoordinate toCoord : moves.get(fromCoord)) {
				int distanceToOpposingButterfly = Integer.MAX_VALUE;
				if (opposingButterfly != null) {
					distanceToOpposingButterfly = game
							.getButterflyCoordinate(opposingColor)
							.getDistance(toCoord);
					currentDistance = game.getButterflyCoordinate(opposingColor).getDistance(fromCoord);
				}

				if (bestTo == null || (distanceToOpposingButterfly <= bestDistance && currentDistance != 1)) {
					bestTo = toCoord;
					bestFrom = fromCoord;
					bestDistance = distanceToOpposingButterfly;
				}
			}
		}

		HantoPieceType type = game.getPieceAt(bestFrom).getType();
		move = new HantoMoveRecord(type, bestFrom, bestTo);
		return move;
	}
	

	
	/**
	 * Gets the "better" move for a piece, comparing a normal move to a butterfly move.
	 * @param move a move not containing a butterfly
	 * @param butterflyMove a move containing a butterfly - possibly null
	 * @return the move that is determined to be better
	 */
	private HantoMoveRecord getBetterMove(HantoMoveRecord move, HantoMoveRecord butterflyMove) {
		HantoMoveRecord betterMove = move;
		if(butterflyMove != null){
			GameCoordinate butterflyFrom = game.getButterflyCoordinate(controllerColor);
			int toNeighbors = game.getNumberOfNeighbors(butterflyMove.getTo());
			
			if(butterflyFrom != null && game.getNumberOfNeighbors(butterflyFrom) > toNeighbors){
				betterMove = butterflyMove;
			}
		}
		return betterMove;
	}

	/**
	 * Returns the best move for a butterfly, hopefully trying to protect itself.
	 * @param openSpots the spots open for a butterfly
	 * @return the move that is the safest for the butterfly
	 */
	private HantoMoveRecord getGreedyButterflyAction(List<GameCoordinate> openSpots) {

		GameCoordinate opposingButterfly = game.getButterflyCoordinate(opposingColor);
		GameCoordinate ourButterfly = game.getButterflyCoordinate(opposingColor);
		
		HantoMoveRecord move = null;
		if(openSpots != null){
			GameCoordinate bestTo = null;
			int bestDistance = Integer.MIN_VALUE;
			for (GameCoordinate coord : openSpots) {
	
				int distanceToOpposingButterfly = Integer.MIN_VALUE;
				if (opposingButterfly != null) {
					distanceToOpposingButterfly = game.getButterflyCoordinate(
							opposingColor).getDistance(coord);
				}
	
				if (bestTo == null || distanceToOpposingButterfly >= bestDistance) {
					bestTo = coord;
					bestDistance = distanceToOpposingButterfly;
				}
				move = new HantoMoveRecord(HantoPieceType.BUTTERFLY, ourButterfly, bestTo);
			}
		}
		return move;
	}


	
	/**
	 * Allows the chance of picking a move action over a placement action to be set.
	 * (Assuming both can be done).
	 * @param chanceOfPickingMoveOverPlace a double between 0 and 1 to determine the chance.
	 */
	public void setChanceOfPickingMoveOverPlace(double chanceOfPickingMoveOverPlace) {
		this.chanceOfPickingMoveOverPlace = chanceOfPickingMoveOverPlace;
	}
}
