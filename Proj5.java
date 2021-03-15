import java.io.File;
import java.io.IOException;
import java.util.*;


class Proj5 {

	/**
	 * <Proj5.java>
	 * <Parker Schmitt / CIS 200 (Lab A) Thursday @ 2:30-4:20PM>
	 *
	 * This project simulates John Horton Conway's Game of Life. Conway's Game of Life is essentially
	 * several simple "rules" that when put together yield complex results. The rules for each cell are:
	 * 1. If an alive cell has less than 2 or more than three neighbors, it dies.
	 * 2. If an alive cell has two or three neighbors, it lives.
	 * 3. If a dead cell has three neighbors, it comes back to life.
	 * This program allows users to explore the game of life by letting users input a text file through
	 * the command line (text file must include the height followed by the width on seperate lines, then
	 * the rest of the lines must be 0s or ones, where a 1 is a alive cell and 0 is a dead cell). The
	 * program will run indifidently. To stop the program through the terminal, press CTRL-C on Mac.
	 *
	 */

	/**
	* (This method reads in a text file of 1s (alive cells) and 0s (dead cells) and stores it in a 2D
	* boolean array. Since a cell can only be alive or dead, we will use booleans to save on memory.)
	* The first two lines of the text file must be the height and width- in that order. The rest of the
	* lines must not extend past the bounds of the width and height.
	* @param inputFilePath this is the string of whatever file you want to load in/
	* @return The initial 2D array of live and dead cells that wjjje will use to start the simulation.
	* @throws IOException
	*/

	public static boolean[][] readBoard(String inputFilePath) throws IOException {
		Scanner file = new Scanner(new File(inputFilePath));
		String row;
		int height;
		int width;
		boolean[][] board;
		//Height of the game
		height = Integer.parseInt(file.nextLine());
		//Width of the game
		width = Integer.parseInt(file.nextLine());
		board = new boolean[height][width];


		for (int i=0; i<height; i++) {
			row = file.nextLine();
			for (int j=0;j<width;j++) {
				if (row.charAt(j) == '0') {
					board[i][j] = false;
				} else {
					board[i][j] = true;
				}
			}
		}
		return board;


	}


	/**
	* (This method displays the board. It uses an asterisk (*) to showcase alive cells and a period (.) to * showcase dead cells. Height and width of the display is equal to the boards height and width.)
	* @param board the 2D boolean array of our alive and dead cells
	* @return The final string that translates the boolean values into text that we can then showcase in  * the console.
	*/

	public static String boardDisplay(boolean[][] board) {
		String outputString = "";
		//Go through each row
		for (int i=0;i<board.length; i++) {
			for (int j =0; j<board[i].length; j++) {
				if (board[i][j] == true) {
					//Alive
					outputString += "*";
				} else {
					//Dead
					outputString += ".";
				}
			}
			outputString += "\n";
		}
		return outputString;


	}
	/**
	* (This method includes the logical for updating the board. It goes through each cell, and determines * if it resurrects, dies, or stays alive based on the number of neighbors it has (less than 2 or more * than three, dies; equal to 2 or three, lives, and if a dead cell has three neighbors it comes back  * to life.))
	* @param board the 2D boolean array of our alive and dead cells
	* @return The newly updated 2D boolean array that has the next iteration of cells- updated from the *   board given in the parameter.
	*/

	public static boolean[][] update(boolean[][] board) {
		int neighborTotal;
		boolean[][] tempBoard = new boolean[board.length][board[0].length];
		for (int y=0;y<=board.length-1; y++) {
			for (int x =0; x<=board[y].length-1; x++) {

				neighborTotal = neighbors(board,y,x);
				tempBoard[y][x] = board[y][x];


				if (tempBoard[y][x] == true && ( neighborTotal < 2 || neighborTotal > 3)) {
					tempBoard[y][x] = false;
				}
				else if (tempBoard[y][x] == true && neighborTotal == 2) {
					tempBoard[y][x] = true;
					//If the neighborTotal is 3 the cell becomes/stays alive regardless of it started out
					//dead or alive.
				} else if (neighborTotal == 3) {
					tempBoard[y][x] = true;
				}
			}
		}
		return tempBoard;
	}


	/**
	* (This method returns the amount of neighboring cells. It uses two for loops to check each surronding
	*  nearby cell. It uses Max,min operators to avoid using if statements to check array bounds in the *   code and keep things consise.)
	* @param board the 2D boolean array of our alive and dead cells
	* @param y the y position of the cell we are checking (height, first dimension in 2D boolean array)
	* @param x the x position of the cell we are checking (width, second dimension in 2D boolean array)
	* @return the integer of how many neighboring cells there are.
	*/

	public static int neighbors(boolean[][] board, int y, int x) {
		int neighborsTotal=0;
		//i = whatever value is larger (y,1 or 0). This is to prevent checking a out of bounds index
		//i <= whatever value is smaller (y+1 or board.length-1). Prevents checking out of bounds.
		for (int i= Math.max(y-1,0); i<=Math.min(y+1,board.length-1); i++) {
			//Same as above
			for (int j=Math.max(x-1,0); j<=Math.min(x+1,board[i].length-1); j++) {
				//See if the thing we are checking is alive and also we aren't checking
				//the cell we inputted into method
				if (board[i][j] && !(i == y && j == x)) {
					neighborsTotal = neighborsTotal + 1;
				}
			}
		}
		return neighborsTotal;
	}

	/**
	* (This is the main method of the program that Java calls when Proj5 is ran.)
	* @param args Arguments passed in via console.
	* @throws IOException
	*/
	public static void main(String[] args) throws IOException {
		String fname = "";
		boolean[][] board;
		if (args.length == 1) {
			fname = args[0];
			board = readBoard(fname);
			System.out.print(boardDisplay(board));
			while (true) {
				try {
						Thread.sleep(300);
					}
				catch (InterruptedException e) {}
				board = update(board);
				System.out.print(boardDisplay(board));
			}
		} else {
			System.out.println("Invalid filename. Pleae pass in a single filename after the name of the program.");
		}
	}
}