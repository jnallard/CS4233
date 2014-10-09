/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentJnaYy.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentJnaYy.common.moveControllers.MoveHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joshua and Yan
 * This class is the board for playing hanto game
 */
public class HantoBoard {

	
	protected final HantoPieceMap board = new HantoPieceHashMap();
	protected int turnCount = 1;
	protected HantoBoardButterflyManager butterflyManager;
	
	private final GameCoordinate GAME_COORDINATE_ORIGIN = new GameCoordinate(0, 0);
	private String exceptionMessage = "";
	private int maxTurnCount;
	private final int MaxNumNeighbors = 6;
	private HantoPlayerColor movesFirst;
	private MoveResult previousGameStatus = MoveResult.OK;
	private MoveHandler moveController;

	/**
	 * Creates the board
	 * @param maxTurnCount the number of moves (turns * 2) that the board may handle.
	 * @param butterflyOptionalTurns the number of moves where a butterfly isn't forced to be placed.
	 * @param movesFirst The player that will move first
	 * @param moveController The object responsible for checking to see if a movement is valid
	 */
	public HantoBoard(int maxTurnCount, int butterflyOptionalTurns, HantoPlayerColor movesFirst, 
			MoveHandler moveController){
		this.maxTurnCount = maxTurnCount;
		this.movesFirst = movesFirst;
		this.moveController = moveController;
		butterflyManager = new HantoBoardButterflyManager(butterflyOptionalTurns);
	}
	
	/**
	 * Checks to see if the move is valid 
	 * A move is considered valid if the first piece is placed at (0,0)
	 * And if the next piece is adjacent to it.
	 * @param to The location the piece is trying to move to.
	 * @param type the piece type to validate
	 * @param color the piece color to validate
	 * @throws HantoException 
	 */
	public void checkMoveValidity(HantoCoordinate to, HantoPieceType type, 
			HantoPlayerColor color) throws HantoException {
		GameCoordinate ourToCoodinate = new GameCoordinate(to);
		
		exceptionMessage = "";
		boolean isValid = butterflyManager.areButterflyConditionsMet(turnCount, type, color);
		
		isValid &= !isCoordinateTaken(ourToCoodinate);
		if(turnCount == 1 && color == movesFirst){
			isValid &= GAME_COORDINATE_ORIGIN.equals(to);
		}
		
		isValid &= getGameStatus() == MoveResult.OK;
			
		if(!isValid){
			throw new HantoException(exceptionMessage);
		}
	}
	
	/**
	 * Checks to see if the given coordinate is claimed.
	 * @param to the coordinate to check for
	 * @return true if the coordinate exists already on the board.
	 */
	private boolean isCoordinateTaken(GameCoordinate to){
		boolean isTaken = board.containsKey(to);
		if(isTaken){
			exceptionMessage = to + " is already occupied by another piece.";
		}
		
		return(isTaken);
	}

	/**
	 * Add a piece to the board
	 * @param to the coordinate of the new piece
	 * @param piece the type of the piece
	 * @throws HantoException 
	 * @return Will return the MoveResult that occurs after the move
	 */
	public MoveResult addPiece(HantoCoordinate to, HantoPiece piece) throws HantoException{

		GameCoordinate coordinate = new GameCoordinate(to);
		
		checkMoveValidity(coordinate, piece.getType(), piece.getColor());
		
		if(piece.getType() == HantoPieceType.BUTTERFLY){
			butterflyManager.setButterflyCoord(piece.getColor(), coordinate);
		}
		
		board.put(coordinate, piece);
		
		if(piece.getColor() != movesFirst){
			turnCount++;
		}
		
		return getGameStatus();
	}
	
	/**
	 * Checks to see the current status of the game
	 * @return A move result dictating the result of the game, thus far.
	 */
	public MoveResult getGameStatus() {
		MoveResult gameResult = previousGameStatus;
		if(gameResult == MoveResult.OK){
			gameResult = hasAPlayerWon();
			
			if(areTurnsOver() && gameResult == MoveResult.OK){
				gameResult = MoveResult.DRAW;
			} 
		}
		previousGameStatus = gameResult;
		return gameResult;
	}

	/**
	 * Checks to see if a player has won.
	 * @return A MoveResult dictating the result of the game this far.
	 */
	private MoveResult hasAPlayerWon() {
		MoveResult moveResult = MoveResult.OK;
		
		GameCoordinate blueButterflyCoord = butterflyManager.getButterflyCoord(
				HantoPlayerColor.BLUE);
		GameCoordinate redButterflyCoord = butterflyManager.getButterflyCoord(
				HantoPlayerColor.RED);
		
		boolean didRedWin = isAdjacentToNOrMorePieces(blueButterflyCoord, MaxNumNeighbors);
		boolean didBlueWin = isAdjacentToNOrMorePieces(redButterflyCoord, MaxNumNeighbors);
		
		if(didRedWin && didBlueWin){
			moveResult = MoveResult.DRAW;
		}
		else if(didRedWin){
			moveResult = MoveResult.RED_WINS;
		} 
		else if (didBlueWin){
			moveResult = MoveResult.BLUE_WINS;
		}
		return moveResult;
	}

	/**
	 * Checks to see if a coordinate is completely surrounded by other pieces.
	 * @param coordinate the location to check for
	 * @return true if the coordinate is surrounded
	 */
	private boolean isAdjacentToNOrMorePieces(GameCoordinate coordinate, int numAdajcentTo) {
		int numberOfOccupiedNeighbors = 0;
		if(coordinate != null){
			numberOfOccupiedNeighbors = board.getNumberOfNeighborPieces(coordinate);
		}
		
		boolean hasNNeighbors = numberOfOccupiedNeighbors >= numAdajcentTo;
		if(!hasNNeighbors){
			exceptionMessage = coordinate + " is not adjacent to " + numAdajcentTo + " piece(s).";
		}
		return numberOfOccupiedNeighbors >= numAdajcentTo;
	}

	

	/**
	 * Checks to see if the game is over, by the move count being
	 * greater than the max move count, or by a butterfly being surrounded.
	 * @return true if the game is over
	 */
	private boolean areTurnsOver(){
		boolean isOver = turnCount > maxTurnCount;
		if(isOver){
			exceptionMessage = "The game is over - moves are no longer allowed.";
		}
		return isOver;
	}
	
	/**
	 * Return the piece based on the coordinate given
	 * @param coordinate the coordinate given
	 * @return hanto piece on this coordinate, or null if there is no piece on this coordinate
	 */
	public HantoPiece getPieceAt(HantoCoordinate coordinate){
		
		return board.getPieceAt(coordinate);
	}
	
	/**
	 * Returns the board in this format: 
	 * "(x1, y1) Color1 Piece1
	 * (x2, y2) Color2 Piece2"
	 * (There is no particular order when printing)
	 * @return a printable representation of the board.
	 */
	public String getPrintableBoard(){
		return board.getPrintableBoard();
	}

	/**
	 * Checks to see if the piece wanted is at the given location.
	 * @param fromCoordinate the coordinate to check
	 * @param pieceType the piece type to check for
	 * @param currentColor the piece color to check for
	 * @throws HantoException 
	 */
	public void checkPieceHere(HantoCoordinate fromCoordinate,
			HantoPieceType pieceType, HantoPlayerColor currentColor) throws HantoException {
		
		boolean isPieceHere = board.isPieceHere(fromCoordinate, pieceType, currentColor);
		
		if(!isPieceHere){
			throw new HantoException("The piece wanted is not at this location.");
		}
	}

	/**
	 * Checks to see if the piece will be added next to only its own color
	 * @param coord the location to check
	 * @param currentColor the color to check for
	 * @param ruleExceptionTurns the number of turns (starting from 1) where this rule does not apply
	 * @throws HantoException
	 */
	public void checkPieceAddedNextToOwnColorRule(HantoCoordinate coord, 
			HantoPlayerColor currentColor, int ruleExceptionTurns) throws HantoException {
		boolean isNextToOnlyThisColor = board.isPieceOnlyNextToColor(coord,
				currentColor);
		
		if(!isNextToOnlyThisColor && turnCount > ruleExceptionTurns){
			throw new HantoException( "The given coordinate has at least one "
					+ "piece that does not belong to " + currentColor);
		}
	}

	
	
	/**
	 * Checks to see if every piece on the board is connected
	 * @throws HantoException
	 */
	public void checkPieceConnectivity() throws HantoException{

		if(!board.arePiecesConnected()){
			throw new HantoException("The board is no longer contiguous.");
		}
	}
	
	

	/**
	 * Removes the piece at the location from the board
	 * @param fromCoordinate the coordinate to remove a piece
	 */
	public void removePiece(HantoCoordinate fromCoordinate) {
		board.remove(new GameCoordinate(fromCoordinate));
	}

	/**
	 * Checks the movement rules for a piece being moved on the board
	 * @param fromCoordinate the coordinate the piece is moving from
	 * @param toCoordinate the coordinate the piece is moving to
	 * @param type the hanto piece type used to get the movement type for the piece
	 * @throws HantoException
	 */
	public void checkMovement(HantoCoordinate fromCoordinate, HantoCoordinate toCoordinate, 
			HantoPieceType type) throws HantoException {
		
		GameCoordinate from = new GameCoordinate(fromCoordinate);
		GameCoordinate to = new GameCoordinate(toCoordinate);
		moveController.checkMovement(to, from, board, type);
	}



	/**
	 * Sets the current player as the resigning player (as in, the other player wins)
	 * @param currentColor the player who is resigning
	 */
	public void setResigning(HantoPlayerColor currentColor) {
		MoveResult winning = currentColor == HantoPlayerColor.RED ? 
				MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		previousGameStatus = winning;
	}

	/**
	 * Clears the board of all pieces
	 */
	public void clear() {
		board.clear();
		butterflyManager.setButterflyCoord(HantoPlayerColor.RED, null);
		butterflyManager.setButterflyCoord(HantoPlayerColor.BLUE, null);
		turnCount = 0;
	}
	
	/**
	 * Gets all the possible moves that a player can make
	 * @param color the player to check for
	 * @return a Map of a piece's location to the list of locations it can go.
	 */
	public Map<GameCoordinate, List<GameCoordinate>> getPossibleMovesForPlayer(HantoPlayerColor color){
		Map<GameCoordinate, List<GameCoordinate>> moves = new HashMap<GameCoordinate, List<GameCoordinate>>();
		for(GameCoordinate coord: board.keySet()){
			HantoPiece piece = getPieceAt(coord);
			if(piece.getColor() == color){
				List<GameCoordinate> possibleMoves = moveController.getPossibleCoordinates(piece.getType(), coord, board);
				if(!possibleMoves.isEmpty()){
					moves.put(coord, possibleMoves);
				}
			}
		}
		return moves;
	}

	/**
	 * Checks to see if a piece can be placed anywhere on the board for a player
	 * @param color the player to check for
	 * @param ruleExceptionTurns the number of turns where the rule of opposite player
	 * restrictions doesn't apply
	 * @return true if there is a spot where a piece can be placed
	 */
	public boolean canAPieceBePlacedByPlayer(HantoPlayerColor color, int ruleExceptionTurns){
		boolean isSpotAvailable = false;
		for(GameCoordinate coord: board.getAllOpenCoordinates()){
			try{
				checkPieceAddedNextToOwnColorRule(coord, color, ruleExceptionTurns);
				isSpotAvailable = true;
				break;
			}
			catch(HantoException e){
				continue;
			}
		}
		
		if(turnCount <= ruleExceptionTurns){
			isSpotAvailable = true;
		}
		
		return isSpotAvailable;
	}
}
