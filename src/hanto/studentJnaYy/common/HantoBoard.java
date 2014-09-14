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

	private final GameCoordinate GAME_COORDINATE_ORIGIN = new GameCoordinate(0, 0);
	private Map<GameCoordinate, HantoPiece> board = new HashMap<GameCoordinate, HantoPiece>();
	private int moveCount = 0;
	private String exceptionMessage = "";
	private int maxMoveCount;
	private GameCoordinate redButterflyCoord = null;
	private GameCoordinate blueButterflyCoord = null;
	private int butterflyOptionalMoves;
	private final int MaxNumNeighbors = 6;

	/**
	 * Creates the board
	 * @param maxMoveCount the number of moves (turns * 2) that the board may handle.
	 * @param butterflyOptionalMoves the number of moves where a butterfly isn't forced to be placed.
	 */
	public HantoBoard(int maxMoveCount, int butterflyOptionalMoves){
		this.maxMoveCount = maxMoveCount;
		this.butterflyOptionalMoves = butterflyOptionalMoves;
	}
	
	/**
	 * Checks to see if the move is valid 
	 * A move is considered valid if the first piece is placed at (0,0)
	 * And if the next piece is adjacent to it.
	 * @param to The location the piece is trying to move to.
	 * @param type the piece type to validate
	 * @param color the piece color to validate
	 * @return true if the move is valid
	 */
	public boolean isMoveValid(GameCoordinate to, HantoPieceType type, HantoPlayerColor color) {
		exceptionMessage = "";
		boolean isValid = areButterflyConditionsMet(type, color);
		if(moveCount == 0){
			isValid &= GAME_COORDINATE_ORIGIN.equals(to);
		}
		else{
			isValid &= isAdjacentToAnyPiece(to) && !isCoordinateTaken(to);
		}
		
		isValid &= getGameStatus() == MoveResult.OK;
			
		return isValid;
	}

	private boolean areButterflyConditionsMet(HantoPieceType type, HantoPlayerColor color) {
		
		boolean isButterflyCoordSet = getButterflyCoordByColor(color) != null;
		boolean isPieceButterfly = type.equals(HantoPieceType.BUTTERFLY);
		
		boolean areConditionsMet = true;
		if(moveCount >= butterflyOptionalMoves){
			areConditionsMet = isButterflyCoordSet || isPieceButterfly;
		}
		
		return areConditionsMet;
	}
	
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

	private boolean isAdjacentToAnyPiece(GameCoordinate to) {
		boolean isAdjacent = false;
		Iterator<Entry<GameCoordinate, HantoPiece>> it = board.entrySet().iterator();
		while(it.hasNext()){
			Entry<GameCoordinate, HantoPiece> entry = it.next();
			GameCoordinate coord = entry.getKey();
			isAdjacent |= coord.isAdjacent(to);
		}
		
		if(!isAdjacent){
			exceptionMessage = to + " is not adjacent to any piece.";
		}
		
		return isAdjacent;
	}
	
	private boolean isCoordinateTaken(GameCoordinate to){
		boolean isTaken = board.containsKey(to);
		if(isTaken){
			exceptionMessage = to + " is already occupied by another piece.";
		}
		
		return(isTaken);
	}

	/**
	 * Add a piece to the board
	 * @param coordinate the coordinate of the new piece
	 * @param piece the type of the piece
	 * @throws HantoException 
	 * @return Will return the MoveResult that occurs after the move
	 */
	public MoveResult addPiece(GameCoordinate coordinate, HantoPiece piece) throws HantoException{
		if(!isMoveValid(coordinate, piece.getType(), piece.getColor())){
			throw new HantoException(exceptionMessage);
		}
		
		if(piece.getType() == HantoPieceType.BUTTERFLY){
			assignButterflyCoord(coordinate, piece.getColor());
		}
		
		board.put(coordinate, piece);
		moveCount++;
		return getGameStatus();
	}
	
	private MoveResult getGameStatus() {
		MoveResult gameResult = MoveResult.OK;
		if(isGameOver()){
			gameResult = MoveResult.DRAW;
		} else {
			gameResult = hasAPlayerWon();
		}
		return gameResult;
	}

	private MoveResult hasAPlayerWon() {
		MoveResult moveResult = MoveResult.OK;
		
		boolean didRedWin = isCoordinateSurrounded(blueButterflyCoord);
		boolean didBlueWin = isCoordinateSurrounded(redButterflyCoord);
		if(didRedWin){
			moveResult = MoveResult.RED_WINS;
		} else if (didBlueWin){
			moveResult = MoveResult.BLUE_WINS;
		}
		return moveResult;
	}

	private boolean isCoordinateSurrounded(GameCoordinate coordinate) {
		int numberOfOccupiedNeighbors = 0;
		if(coordinate != null){
			List<GameCoordinate> neighbors = coordinate.getAdjacentCoordinates();
			for(int i = 0; i < neighbors.size(); i++){
				GameCoordinate neighbor = neighbors.get(i);
				if(board.containsKey(neighbor)){
					numberOfOccupiedNeighbors++;
				}
			}
		}
		return numberOfOccupiedNeighbors == MaxNumNeighbors;
	}

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
	private boolean isGameOver(){
		boolean isOver = moveCount >= maxMoveCount;
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
	public HantoPiece getPieceAt(GameCoordinate coordinate){
		return board.get(coordinate);
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
	 * Returns the message that corresponds to why a move is invalid
	 * @return the string error message
	 */
	public String getErrorMessage() {
		return exceptionMessage;
	}
	
	
}
