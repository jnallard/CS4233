/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentJnaYy.common;

import hanto.common.HantoPlayerColor;

/**
 * This class is used for utilities related to only HantoPlayerColors.
 * @author Joshua and Yan
 *
 */
public class HantoColorHelper {
	
	private static final HantoPlayerColor BLUE = HantoPlayerColor.BLUE;
	private static final HantoPlayerColor RED = HantoPlayerColor.RED;

	/**
	 * Returns the opposite Player color of the color given.
	 * @param color the color to get the opposite of
	 * @return the opposite color
	 */
	public static HantoPlayerColor getOppositeColor(HantoPlayerColor color){
		return (color == BLUE) ? RED : BLUE;
	}

}
