package life;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import processing.core.PApplet;

/*

	Represents a Game Of Life grid.

	Coded by: Lily Collins
	Modified on: 12/6/19

*/

public class Life {
	
	public boolean[][] gameData;
	public String filename;
	

	// Constructs an empty grid
	public Life() {
		gameData = new boolean[20][20];
	}

	// Constructs the grid defined in the file specified
	public Life(String filename) {
		gameData = new boolean[20][20];
		this.filename = filename;
		readData(filename, gameData);
	}

	// Runs a single turn of the Game Of Life
	public void step() {
		ArrayList<Point> toTrue = new ArrayList<Point>();
		ArrayList<Point> toFalse = new ArrayList<Point>();
		
		for(int r = 0; r < 20; r++) {
			for(int c = 0; c < 20; c++) {
				int count = getNeighborCount(r, c);
				if(count == 3)
					toTrue.add(new Point(r, c));//gameData[r][c] = true;
				if(count > 3)
					toFalse.add(new Point(r, c));//gameData[r][c] = false;
				if(count < 2)
					toFalse.add(new Point(r, c));//gameData[r][c] = false;
			}
		}
		for(Point p : toTrue)
			gameData[(int) p.getX()][(int) p.getY()] = true;
		
		for(Point p : toFalse)
			gameData[(int) p.getX()][(int) p.getY()] = false;
	}
	
	// Runs n turns of the Game Of Life
	public void step(int n) {
		for(int i = 0; i < n; i++)
			step();
	}
	
	// Formats this Life grid as a String to be printed (one call to this method returns the whole multi-line grid)
	public String toString() {
		String str = "";
		for(int r = 0; r < 20; r++) {
			for(int c = 0; c < 20; c++) {
				if(gameData[r][c])
					str += "*";
				else
					str += " ";
				
			}
			str += "\n";
		}
		return str;
	}
	
	//gets the number of living neighbors a space has
	public int getNeighborCount(int row, int col) {
		int count = 0;
		for(int r = row -1; r <= row + 1; r++) {
			
			for(int c = col -1; c <= col +1; c++) {
				
				if(isValid(r, c)) {
					if (gameData[r][c] && (r != row || c != col)) {
						count++;
					}
				}
			}
		}
		return count;
	}
	
	//determines if a space is on the grid
	public boolean isValid(int r, int c) {
		if(r < 0 || r >= 20)
			return false;
		if(c < 0 || c >= 20)
			return false;
		return true;
	}

	// Reads in array data from a simple text file containing asterisks (*)
	public void readData (String filename, boolean[][] gameData) {
		File dataFile = new File(filename);

		if (dataFile.exists()) {
			int count = 0;

			FileReader reader = null;
			Scanner in = null;
			try {
					reader = new FileReader(dataFile);
					in = new Scanner(reader);

					while (in.hasNext()) {
						String line = in.nextLine();
						for(int i = 0; i < line.length(); i++)
							if (count < gameData.length && i < gameData[count].length && line.charAt(i)=='*')
								gameData[count][i] = true;

						count++;
					}
			} catch (IOException ex) {
				throw new IllegalArgumentException("Data file " + filename + " cannot be read.");
			} finally {
				if (in != null)
					in.close();
			}
			
		} else {
			throw new IllegalArgumentException("Data file " + filename + " does not exist.");
		}
    }
	
	
	
	/**
	 * Optionally, complete this method to draw the grid on a PApplet.
	 * 
	 * @param marker The PApplet used for drawing.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 */
	public void draw(PApplet marker, float x, float y, float width, float height) {
		float w = width/20;
		float h = height/20;
		for(int r = 0; r < 20; r++) {
			for(int c = 0; c < 20; c++) {
				if(gameData[r][c])
					marker.fill(255, 255, 0);
				else
					marker.fill(100);
				
				marker.rect(x + c*w, y + r*h, w, h);
			}
		}
	}
	
	/**
	 * Optionally, complete this method to determine which element of the grid matches with a
	 * particular pixel coordinate.
	 * 
	 * @param p A Point object representing a graphical pixel coordinate.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 * @return A Point object representing a coordinate within the game of life grid.
	 */
	public Point clickToIndex(Point p, float x, float y, float width, float height) {
		float w = width/20;
		float h = height/20;
		for(int r = 0; r < 20; r++) {
			for(int c = 0; c < 20; c++) {
				Rectangle rect = new Rectangle((int)(x + c*w), (int)(y + r*h), (int) w, (int) h);
				if(rect.contains(p))
					return new Point(r, c);
			}
		}
		return null;
	}
	
	/**
	 * Optionally, complete this method to toggle a cell in the game of life grid
	 * between alive and dead.
	 * 
	 * @param i The x coordinate of the cell in the grid.
	 * @param j The y coordinate of the cell in the grid.
	 */
	public void toggleCell(int i, int j) {
		gameData[i][j] = !gameData[i][j];
	}
	
	public int getNumInRow(int row) {
		int count = 0;
		for(int i = 0; i < 20; i++) {
			if(gameData[row - 1][i])
				count++;
		}
		return count;
	}
	
	public int getNumInCol(int col) {
		int count = 0;
		for(int i = 0; i < 20; i++) {
			if(gameData[i][col - 1])
				count++;
		}
		return count;
	}
	
	public int getNumOfCells() {
		int count = 0;
		for(int r = 0; r < 20; r++) {
			for(int c = 0; c < 20; c++) {
				if(gameData[r][c])
					count++;
			}
		}
		return count;
	}
	
	public boolean[][] getGameData() {
		return gameData;
	}
	
	public String getFilename() {
		return filename;
	}

	
	
}