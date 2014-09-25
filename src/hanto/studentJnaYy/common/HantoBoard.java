/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentJnaYy.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;








import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

/**
 * @author Joshua and Yan
 * This class is the board for playing hanto game
 */
public class HantoBoard {

	private static final int MIN_ADJACENT_NEIGHBORS = 1;
	private final GameCoordinate GAME_COORDINATE_ORIGIN = new GameCoordinate(0, 0);
	protected Map<GameCoordinate, HantoPiece> board = new HashMap<GameCoordinate, HantoPiece>();
	protected int turnCount = 0;
	private String exceptionMessage = "";
	private int maxTurnCount;
	private GameCoordinate redButterflyCoord = null;
	private GameCoordinate blueButterflyCoord = null;
	private int butterflyOptionalTurns;
	private final int MaxNumNeighbors = 6;
	private HantoPlayerColor movesFirst;

	/**
	 * Creates the board
	 * @param maxTurnCount the number of moves (turns * 2) that the board may handle.
	 * @param butterflyOptionalTurns the number of moves where a butterfly isn't forced to be placed.
	 */
	public HantoBoard(int maxTurnCount, int butterflyOptionalTurns, HantoPlayerColor movesFirst){
		this.maxTurnCount = maxTurnCount;
		this.butterflyOptionalTurns = butterflyOptionalTurns;
		this.movesFirst = movesFirst;
	}
	
	/**
	 * Checks to see if the move is valid 
	 * A move is considered valid if the first piece is placed at (0,0)
	 * And if the next piece is adjacent to it.
	 * @param to The location the piece is trying to move to.
	 * @param type the piece type to validate
	 * @param color the piece color to validate
	 * @return true if the move is valid
	 * @throws HantoException 
	 */
	public void checkMoveValidity(HantoCoordinate to, HantoPieceType type, HantoPlayerColor color) throws HantoException {
		GameCoordinate ourToCoodinate = new GameCoordinate(to);
		
		exceptionMessage = "";
		boolean isValid = areButterflyConditionsMet(type, color);
		if(turnCount == 0 && color == movesFirst){
			isValid &= GAME_COORDINATE_ORIGIN.equals(to);
		}
		else{
			isValid &= isAdjacentToNOrMorePieces(ourToCoodinate, MIN_ADJACENT_NEIGHBORS) && 
					!isCoordinateTaken(ourToCoodinate);
		}
		
		isValid &= getGameStatus() == MoveResult.OK;
			
		if(!isValid){
			throw new HantoException(exceptionMessage);
		}
	}

	/**
	 * Checks to see if the conditions for a butterfly placement have been met.
	 * @param type the piece type
	 * @param color the piece color
	 * @return true if the piece passes the conditions set for butterflies.
	 */
	private boolean areButterflyConditionsMet(HantoPieceType type, HantoPlayerColor color) {
		
		boolean isButterflyCoordSet = getButterflyCoordByColor(color) != null;
		boolean isPieceButterfly = type.equals(HantoPieceType.BUTTERFLY);
		
		boolean areConditionsMet = true;
		if(turnCount >= butterflyOptionalTurns){
			areConditionsMet = isButterflyCoordSet || isPieceButterfly;
		}
		
		return areConditionsMet;
	}
	
	/**
	 * Gets the butterfly coordinate based upon the player color
	 * @param color the player color to filter by
	 * @return A GameCoordinate for the color's butterfly, null if not set.
	 */
	private GameCoordinate getButterflyCoordByColor(HantoPlayerColor color){
		GameCoordinate butterflyCoord = null;
		switch(color){
			case RED:
				butterflyCoord = redButterflyCoord;
				break;
			case BLUE:
				butterflyCoord = blueButterflyCoord;
				break;
		}
		return butterflyCoord;
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
			assignButterflyCoord(coordinate, piece.getColor());
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
	private MoveResult getGameStatus() {
		MoveResult gameResult = hasAPlayerWon();
		
		if(areTurnsOver() && gameResult == MoveResult.OK){
			gameResult = MoveResult.DRAW;
		} 
		
		return gameResult;
	}

	/**
	 * Checks to see if a player has won.
	 * @return A MoveResult dictating the result of the game this far.
	 */
	private MoveResult hasAPlayerWon() {
		MoveResult moveResult = MoveResult.OK;
		
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
			numberOfOccupiedNeighbors = getNumberOfNeighbors(coordinate);
		}
		
		boolean hasNNeighbors = numberOfOccupiedNeighbors >= numAdajcentTo;
		if(!hasNNeighbors){
			exceptionMessage = coordinate + " is not adjacent to " + numAdajcentTo + " piece(s).";
		}
		return numberOfOccupiedNeighbors >= numAdajcentTo;
	}

	/**
	 * Returns the number of neighbor pieces to a certain coordinate
	 * @param coordinate the coordinate on the board to check for
	 * @return the number of neighbor pieces
	 */
	private int getNumberOfNeighbors(GameCoordinate coordinate) {
		int numberOfOccupiedNeighbors = 0;
		List<GameCoordinate> neighbors = coordinate.getAdjacentCoordinates();
		for(int i = 0; i < neighbors.size(); i++){
			GameCoordinate neighbor = neighbors.get(i);
			if(board.containsKey(neighbor)){
				numberOfOccupiedNeighbors++;
			}
		}
		return numberOfOccupiedNeighbors;
	}

	/**
	 * Assigns the coordinate to the specific butterfly location of the player color.
	 * @param coordinate the location of the butterfly
	 * @param color the player color for the butterfly
	 */
	private void assignButterflyCoord(GameCoordinate coordinate, HantoPlayerColor color) {
		switch(color){
			case RED:
				redButterflyCoord = coordinate;
				break;
			case BLUE:
				blueButterflyCoord = coordinate;
				break;
		}
	}

	/**
	 * Checks to see if the game is over, by the move count being
	 * greater than the max move count, or by a butterfly being surrounded.
	 * @return true if the game is over
	 */
	private boolean areTurnsOver(){
		boolean isOver = turnCount >= maxTurnCount;
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
		
		return board.get(new GameCoordinate(coordinate));
	}
	
	/**
	 * Returns the board in this format: 
	 * "(x1, y1) Color1 Piece1
	 * (x2, y2) Color2 Piece2"
	 * (There is no particular order when printing)
	 * @return a printable representation of the board.
	 */
	public String getPrintableBoard(){
		String printedBoard = "";
		Iterator<Entry<GameCoordinate, HantoPiece>> it = board.entrySet().iterator();
		while(it.hasNext()){
			Entry<GameCoordinate, HantoPiece> entry = it.next();
			GameCoordinate coord = entry.getKey();
			HantoPiece piece = entry.getValue();
			printedBoard += "(" + coord.getX() + "," + coord.getY() + ") " 
					+ piece.getColor() + " " + piece.getType() + "\n";
		}
		return printedBoard;
	}

	/**
	 * Checks to see if the piece wanted is at the given location.
	 * @param fromCoordinate the coordinate to check
	 * @param pieceType the piece type to check for
	 * @param currentColor the piece color to check for
	 * @return true if the piece type and color match the piece found, if one was found
	 * @throws HantoException 
	 */
	public void isPieceHere(HantoCoordinate fromCoordinate,
			HantoPieceType pieceType, HantoPlayerColor currentColor) throws HantoException {
		
		HantoPiece piece = getPieceAt(fromCoordinate);
		
		boolean isPieceHereResult = false;
		if(piece != null){
			isPieceHereResult = piece.getColor() == currentColor;
			isPieceHereResult &= piece.getType() == pieceType;
		}
		
		if(!isPieceHereResult){
			throw new HantoException("The piece wanted is not at this location.");
		}
	}

	public void checkPieceAddedNextToOwnColorRule(HantoCoordinate coord, HantoPlayerColor currentColor, int ruleExceptionTurns) throws HantoException {
		GameCoordinate toCoord = new GameCoordinate(coord);
		List<GameCoordinate> neighbors = toCoord.getAdjacentCoordinates();
		boolean isNextToOnlyThisColor = true;
		
		for(GameCoordinate neighbor : neighbors){
			HantoPiece piece = board.get(neighbor);
			if(piece != null){
				isNextToOnlyThisColor &= piece.getColor() == currentColor;
			}
		}
		
		if(!isNextToOnlyThisColor && turnCount >= ruleExceptionTurns){
			throw new HantoException( "The given coordinate has at least one "
					+ "piece that does not belong to " + currentColor);
		}
	}
	
	
}
