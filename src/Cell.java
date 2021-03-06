/**
 * A class that represents a cell in the grid.
 * @author PengJu Chang
 */
import java.util.ArrayList;

public class Cell {
	
	private int rowX; //X coordinates of the cell in the grid.
	private int colY; //Y coordinates of the cell in the grid.
	private boolean visited; //cell is visited or not.
	private char value; //Value of the cell: 0 means normal cell, S means start, E means end, # means blockades and 1 to represent path cell.
	private double fValue; //f value for A* search.
	private double gValue; //g value for A* search.
	private double hValue; //h value for A* search.
	private int dijValue; //Total cost of path so far for Dijkstra's algorithm..
	private ArrayList<Cell> neighbourList; //A list of neighbouring cells that are not blockades.
	
	/**
	 * Default constructor
	 */
	public Cell() {
		this.rowX = 0;
		this.colY = 0;
		this.visited = false;
		this.value = '0';
		this.fValue = 0;
		this.gValue = 0;
		this.hValue = 0;
		this.dijValue = Integer.MAX_VALUE;
		this.neighbourList = new ArrayList<Cell>();
	}
	
	/**
	 * Secondary constructor
	 * @param i
	 * 			x coordinate.
	 * @param j
	 * 			y coordinate.
	 */
	public Cell(int i, int j) {
		this.rowX = i;
		this.colY = j;
		this.visited = false;
		this.value = '0';
		this.fValue = 0;
		this.gValue = 0;
		this.hValue = 0;
		this.dijValue = Integer.MAX_VALUE;
		this.neighbourList = new ArrayList<Cell>();
	}
	
	/**
	 * A method that returns the x coordinates of the cell.
	 * @return x coordinates of the cell.
	 */
	public int getRowX() {
		return this.rowX;
	}

	/**
	 * A method that returns the y coordinates of the cell.
	 * @return y coordinates of the cell.
	 */
	public int getColY() {
		return this.colY;
	}
	
	/**
	 * A method that returns the boolean value of whether of not the cell has been visited.
	 * @return boolean value of whether of not the cell has been visited.
	 */
	public boolean getVisited() {
		return this.visited;
	}
	
	/**
	 * A method that returns the value representation of the type of cell. 
	 * @return value representation of the type of cell.
	 */
	public char getValue() {
		return this.value;
	}
	
	/**
	 * A method that returns the f value of the cell for A* search.
	 * @return f value.
	 */
	public double getfValue() {
		return this.fValue;
	}
	
	/**
	 * A method that returns the g value of the cell for A* search.
	 * @return g value.
	 */
	public double getgValue() {
		return this.gValue;
	}
	
	/**
	 * A method that returns the h value of the cell for A* search.
	 * @return h value.
	 */
	public double gethValue() {
		return this.hValue;
	}
	
	/**
	 * A method that returns the cost so far of the cell for Dijkstra's algorithm.
	 * @return cost so far of the cell for Dijkstra's algorithm.
	 */
	public int getDijValue() {
		return this.dijValue;
	}
	
	/**
	 * A method that returns the list of neighbours of the cell.
	 * @return list of neighbours fo the cell.
	 */
	public ArrayList<Cell> getNeighbours(){
		return this.neighbourList;
	}
	
	/**
	 * A method that sets the visited status of the cell.
	 * @param visit
	 * 			visited status of the cell.
	 */
	public void setVisited(boolean visit) {
		this.visited = visit;
	}
	
	/**
	 * A method that sets the value representation of the type of cell.
	 * @param newValue
	 * 			value representation of the type of cell.
	 */
	public void setValue(char newValue) {
		this.value = newValue;
	}
	
	/**
	 * A method that sets the f value of the cell for A* search.
	 * @param f
	 * 			f value.
	 */
	public void setfValue(double f) {
		this.fValue = f;
	}
	
	/**
	 * A method that sets the g value of the cell for A* search.
	 * @param g
	 * 			g value.
	 */
	public void setgValue(double g) {
		this.gValue = g;
	}
	
	/**
	 * A method that sets the h value of the cell for A* search.
	 * @param h
	 * 			h value.
	 */
	public void sethValue(double h) {
		this.hValue = h;
	}
	
	/**
	 * A method that sets the cost so far of the cell for Dijkstra's algorithm.
	 * @param newDijValue
	 * 			cost so far of the cell for Dijkstra's algorithm.
	 */
	public void setDijValue(int newDijValue) {
		this.dijValue = newDijValue;
	}
}
