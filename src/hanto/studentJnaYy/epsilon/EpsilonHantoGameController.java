package hanto.studentJnaYy.epsilon;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentJnaYy.common.GameCoordinate;
import hanto.studentJnaYy.tournament.HantoGameController;
import hanto.tournament.HantoMoveRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EpsilonHantoGameController implements HantoGameController {
	private EpsilonHantoGame game;
	private HantoPlayerColor controllerColor;
	private HantoPlayerColor opposingColor;

	public EpsilonHantoGameController(HantoPlayerColor movesFirst,
			HantoPlayerColor controllerColor) {
		game = new EpsilonHantoGame(movesFirst);
		this.controllerColor = controllerColor;
		opposingColor = (controllerColor == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED
				: HantoPlayerColor.BLUE;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		return game.makeMove(pieceType, from, to);
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return game.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		return game.getPrintableBoard();
	}

	@Override
	public HantoMoveRecord getBestMove() {
		if(controllerColor == HantoPlayerColor.BLUE){
			return getPrimitiveGreedyBestMove();
		}
		else{
			return getLessPrimitiveGreedyBestMove();
		}
	}

	private HantoMoveRecord getRandomMove() {
		HantoMoveRecord move = null;

		double random = Math.random();
		boolean randomBoolean = random > 0.50;
		Map<GameCoordinate, List<GameCoordinate>> moves = game
				.getAllMovesForCurrentPlayer();
		Map<HantoPieceType, List<GameCoordinate>> placements = game
				.getAllPlacementsForCurrentPlayer();
		if (placements.isEmpty() && moves.isEmpty()) {
			move = new HantoMoveRecord(null, null, null);
		} else if ((randomBoolean && !placements.isEmpty()) || moves.isEmpty()) {
			int randomIndex1 = (int) (Math.random() * placements.size());
			HantoPieceType type = new ArrayList<HantoPieceType>(
					placements.keySet()).get(randomIndex1);
			int randomIndex2 = (int) (Math.random() * placements.get(type)
					.size());
			GameCoordinate coord = new ArrayList<GameCoordinate>(
					placements.get(type)).get(randomIndex2);
			move = new HantoMoveRecord(type, null, coord);
		} else {
			int randomIndex1 = (int) (Math.random() * moves.size());
			GameCoordinate from = new ArrayList<GameCoordinate>(moves.keySet())
					.get(randomIndex1);
			int randomIndex2 = (int) (Math.random() * moves.get(from).size());
			GameCoordinate to = new ArrayList<GameCoordinate>(moves.get(from))
					.get(randomIndex2);
			HantoPieceType type = game.getPieceAt(from).getType();
			move = new HantoMoveRecord(type, from, to);

		}
		return move;
	}

	private HantoMoveRecord getPrimitiveGreedyBestMove() {
		HantoMoveRecord move = null;

		double random = Math.random();
		boolean randomBoolean = random > 0.50;
		Map<GameCoordinate, List<GameCoordinate>> moves = game
				.getAllMovesForCurrentPlayer();
		Map<HantoPieceType, List<GameCoordinate>> placements = game
				.getAllPlacementsForCurrentPlayer();

		GameCoordinate opposingButterfly = game
				.getButterflyCoordinate(opposingColor);

		if (placements.isEmpty() && moves.isEmpty()) {
			move = new HantoMoveRecord(null, null, null);
		} else if ((randomBoolean && !placements.isEmpty()) || moves.isEmpty()) {
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

			move = new HantoMoveRecord(new ArrayList<HantoPieceType>(
					placements.keySet()).get(0), null, bestTo);
		} else {

			GameCoordinate bestTo = null;
			GameCoordinate bestFrom = null;
			int bestDistance = Integer.MAX_VALUE;
			for (GameCoordinate fromCoord : moves.keySet()) {
				for (GameCoordinate toCoord : moves.get(fromCoord)) {
					int distanceToOpposingButterfly = Integer.MAX_VALUE;
					if (opposingButterfly != null) {
						distanceToOpposingButterfly = game
								.getButterflyCoordinate(opposingColor)
								.getDistance(toCoord);
					}

					if (bestTo == null || distanceToOpposingButterfly <= bestDistance) {
						bestTo = toCoord;
						bestFrom = fromCoord;
						bestDistance = distanceToOpposingButterfly;
					}
				}
			}

			HantoPieceType type = game.getPieceAt(bestFrom).getType();
			move = new HantoMoveRecord(type, bestFrom, bestTo);
		}
		return move;
	}
	
	private HantoMoveRecord getLessPrimitiveGreedyBestMove() {
		HantoMoveRecord move = null;

		double random = Math.random();
		boolean randomBoolean = random > 0.50;
		Map<GameCoordinate, List<GameCoordinate>> moves = game
				.getAllMovesForCurrentPlayer();
		Map<HantoPieceType, List<GameCoordinate>> placements = game
				.getAllPlacementsForCurrentPlayer();

		GameCoordinate opposingButterfly = game
				.getButterflyCoordinate(opposingColor);

		if (placements.isEmpty() && moves.isEmpty()) {
			move = new HantoMoveRecord(null, null, null);
		} else if ((randomBoolean && !placements.isEmpty()) || moves.isEmpty()) {
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

			move = new HantoMoveRecord(new ArrayList<HantoPieceType>(
					placements.keySet()).get(0), null, bestTo);
		} else {

			GameCoordinate bestTo = null;
			GameCoordinate bestFrom = null;
			int bestDistance = Integer.MAX_VALUE;
			for (GameCoordinate fromCoord : moves.keySet()) {
				for (GameCoordinate toCoord : moves.get(fromCoord)) {
					int distanceToOpposingButterfly = Integer.MAX_VALUE;
					if (opposingButterfly != null) {
						distanceToOpposingButterfly = game
								.getButterflyCoordinate(opposingColor)
								.getDistance(toCoord);
					}

					if (bestTo == null || distanceToOpposingButterfly <= bestDistance) {
						bestTo = toCoord;
						bestFrom = fromCoord;
						bestDistance = distanceToOpposingButterfly;
					}
				}
			}

			HantoPieceType type = game.getPieceAt(bestFrom).getType();
			move = new HantoMoveRecord(type, bestFrom, bestTo);
		}
		return move;
	}
}
