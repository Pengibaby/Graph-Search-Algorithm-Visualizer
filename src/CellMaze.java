/**
 * A class that creates the grid and paints it to a GUI.
 * @author PengJu Chang
 */
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JPanel;

public class CellMaze extends JPanel {
	
	private int speed = 4; //Speed of the repaint.
	private Cell[][] grid; //2D array to represent the grid.
	private int gridSize; //Size of the grid.
	private Cell[][] visitedCells = new Cell[1000][1000]; //A 2D array to store parent cells with coordinates i and j in visitedCells[i][j]
	
	/**
	 * Default constructor.
	 * @param inputSize
	 * 			size of the grid.
	 */
	public CellMaze(int inputSize) {
		setBackground(Color.WHITE); //sets the panel background to be white.
		this.gridSize = inputSize; //sets the size of the grid.
	}
	
	/**
	 * A method that generates the grid with Cells.
	 * @param inputSize
	 * 			size of the grid.
	 */
	public void generateGrid(int inputSize) {
		this.gridSize = inputSize; //sets the size of the grid.
		grid = new Cell[this.gridSize][this.gridSize]; //Initializes the grid to the correct size.
		//Adds Cells to the grid with coordinates i and j.
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				grid[i][j] = new Cell(i, j);
			}
		}
		
		//Sets the visitedCells 2D array to store null initially.
		for(int i = 0; i < 1000; i++) {
			for(int j = 0; j < 1000; j++) {
				visitedCells[i][j] = null;
			}
		}
		
		//Repaints the whole grid on the GUI
		repaint();
	}
	
	/**
	 * A method that sets the neighbours of all cells in the grid.
	 */
	public void setNeighbours() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				//Makes sure the current cell is not a blockade.
				if(grid[i][j].getValue() != '#') {
					//Makes sure the neighbouring cells are not a blockade and is not out of bounds.
					if(i - 1 >= 0 && i - 1 < this.gridSize && grid[i - 1][j].getValue() != '#') {
						grid[i][j].getNeighbours().add(grid[i - 1][j]);
					}
					if(j - 1 >= 0 && j - 1 < this.gridSize && grid[i][j - 1].getValue() != '#') {
						grid[i][j].getNeighbours().add(grid[i][j - 1]);
					}
					if(i + 1 >= 0 && i + 1 < this.gridSize && grid[i + 1][j].getValue() != '#') {
						grid[i][j].getNeighbours().add(grid[i + 1][j]);
					}
					if(j + 1 >= 0 && j + 1 < this.gridSize && grid[i][j + 1].getValue() != '#') {
						grid[i][j].getNeighbours().add(grid[i][j + 1]);
					}
				}
			}
		}
	}
	
	/**
	 * A method that sets the cell at x and y as a blockade.
	 * @param x
	 * 			x coordinate of the cell to be a blockade
	 * @param y
	 * 			y coordinate of the cell to be a blockade.
	 */
	public void setBlocks(int x, int y) {
		//Makes sure that the cell is not a starting position and not an ending position.
		if(grid[x][y].getValue() == 'S' || grid[x][y].getValue() == 'E') {
			return;
		}
		//Set the value to be # which represents a blockade.
		this.grid[x][y].setValue('#');
		repaint();
	}
	
	/**
	 * A method that sets the starting cell for the path.
	 * @param x
	 * 			x coordinate of the starting cell.
	 * @param y
	 * 			y coordinate of the starting cell.
	 * @return the starting Cell object.
	 */
	public Cell setStart(int x, int y) {
		//Makes sure that the cell is not a blockade or an ending cell.
		if(grid[x][y].getValue() == '#' || grid[x][y].getValue() == 'E') {
			return null;
		}
		//Goes through the whole grid and removes existing starting cell.
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if(grid[i][j].getValue() == 'S') {
					grid[i][j].setValue('0');
					break;
				}
			}
		}
		//Set the value to be S which represents the starting position.
		this.grid[x][y].setValue('S');
		repaint();
		return grid[x][y];
	}
	
	/**
	 * A method that sets the ending cell for the path.
	 * @param x
	 * 			x coordinate of the ending cell.
	 * @param y
	 * 			y coordinate of the ending cell.
	 * @return the ending Cell object.
	 */
	public Cell setEnd(int x, int y) {
		//Makes sure that the cell is not a blockade or a starting cell.
		if(grid[x][y].getValue() == '#' || grid[x][y].getValue() == 'S') {
			return null;
		}
		//Goes through the whole grid and removes existing ending cell.
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if(grid[i][j].getValue() == 'E') {
					grid[i][j].setValue('0');
					break;
				}
			}
		}
		//Set the value to be E which represents the starting position.
		this.grid[x][y].setValue('E');
		repaint();
		return grid[x][y];
	}
	
	/**
	 * A method that sets the speed of the repaint.
	 * @param value
	 * 			speed value.
	 */
	public void setSpeed(int value) {
		this.speed = value;
	}
	
	/**
	 * A method that prints out the grid with the current cell values.
	 */
	public void printGrid() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j].getValue() + ", ");
			}
			System.out.println();
		}
	}
	
	/**
	 * A method that performs a breadth first search on the grid given the starting and ending cell coordinates.
	 * @param startX
	 * 			starting cell x coordinate.
	 * @param startY
	 * 			starting cell y coordinate.
	 * @param endX
	 * 			ending cell x coordinate.
	 * @param endY
	 * 			ending cell y coordinate.
	 */
	public void solveBFS(int startX, int startY, int endX, int endY) {
		//Creates a new runnable to be ran in a new Thread.
		Runnable search = () -> {
			Queue<Cell> Q = new LinkedList<Cell>(); //Creates an empty queue.
			Q.add(grid[startX][startY]); //Add the starting cell to the queue.
			grid[startX][startY].setVisited(true); //set starting cell to be visited.
			boolean reachedEnd = false; //not reached the ending cell yet.
			while(Q.size() != 0) {
				Cell temp = Q.remove(); //remove the cell at the front of the queue.
				//Goes through all the cell's neighbours and adds them to the queue and set them as visited if not already.
				for(int i = 0; i < temp.getNeighbours().size(); i++) {
					Cell neighbour = temp.getNeighbours().get(i);
					if(neighbour.getValue() != '#' && neighbour.getVisited() == false && visitedCells[neighbour.getRowX()][neighbour.getColY()] == null) {
						visitedCells[neighbour.getRowX()][neighbour.getColY()] = temp;
						neighbour.setVisited(true);
						Q.add(neighbour);
						
						//repaint the GUI.
						repaint();
					    try {
					        Thread.sleep((100 * 4) / speed); //Divided by the speed, since larger the speed value, faster the speed.
					    } catch (InterruptedException e) {
					        e.printStackTrace();
					    }
					}
				}
				
				//Condition for reaching the end cell.
				if(temp.getRowX() == endX && temp.getColY() == endY) {
					reachedEnd = true;
					System.out.println("End Reached!");
					break;
				}
			}
			
			//Gets the path by getting the parent cell of the current cell each time until start cell is reached. Adds these to an ArrayList.
			if(reachedEnd == false) {
				System.out.println("Path not found!");
				return;
			} else {
				ArrayList<Cell> path = new ArrayList<Cell>();
				path.add(grid[endX][endY]);
				path.add(visitedCells[endX][endY]);
				int endCorX = visitedCells[endX][endY].getRowX();
				int endCorY = visitedCells[endX][endY].getColY();
				while(endCorX != startX || endCorY != startY) {
					Cell temp = visitedCells[endCorX][endCorY];
					path.add(temp);
					endCorX = temp.getRowX();
					endCorY = temp.getColY();
				}
				
				for(int i = path.size() - 1; i >= 0; i--) {
					System.out.println(path.get(i).getRowX() + ", " + path.get(i).getColY());
					grid[path.get(i).getRowX()][path.get(i).getColY()].setValue('1');
					
					repaint();
				    try {
				        Thread.sleep((100 * 4) / speed);
				    } catch (InterruptedException e) {
				        e.printStackTrace();
				    }
				}
			}
		};
		new Thread(search, "BFS").start(); //Creates a new thread and runs the Runnable.
	}
	
	/**
	 * A method that performs a A* search on the grid given the starting and ending cell coordinates.
	 * @param startX
	 * 			starting cell x coordinate.
	 * @param startY
	 * 			starting cell y coordinate.
	 * @param endX
	 * 			ending cell x coordinate.
	 * @param endY
	 * 			ending cell y coordinate.
	 */
	public void solveAStar(int startX, int startY, int endX, int endY) {
		//Creates a new runnable to be ran in a new Thread.
		Runnable search = () -> {
			ArrayList<Cell> not_visited_yet = new ArrayList<Cell>(); //Makes an ArrayList to store cells not yet visited.
			not_visited_yet.add(grid[startX][startY]); //Adds the starting cell to that list.
			boolean endReached = false; //Not yet reached the end.
			while(!not_visited_yet.isEmpty()) {
				Cell smallestF = not_visited_yet.get(0); //Assume the smallest f value cell is the first element.
				int smallestFIndex = 0;
				//Finds the actual cell with the smallest f value.
				for(int i = 0; i < not_visited_yet.size(); i++) {
					if(not_visited_yet.get(i).getfValue() < smallestF.getfValue()) {
						smallestF = not_visited_yet.get(i);
						smallestFIndex = i;
					}
				}
				//Set the smallest f value cell to be visited and remove from the list.
				not_visited_yet.get(smallestFIndex).setVisited(true);
				not_visited_yet.remove(smallestFIndex);
				
				//Ending cell reached condition.
				if(smallestF.getRowX() == endX && smallestF.getColY() == endY) {
					endReached = true;
					System.out.println("End Reached!");
					break;
				}
				
				//For all neighbours of the removed cell, work out the f value and add to list. h value is found by using Euclidean method.
				for(int i = 0; i < smallestF.getNeighbours().size(); i++) {
					Cell neighbour = smallestF.getNeighbours().get(i);
					if(neighbour.getValue() != '#' && neighbour.getVisited() == false && visitedCells[neighbour.getRowX()][neighbour.getColY()] == null) {
						visitedCells[neighbour.getRowX()][neighbour.getColY()] = smallestF;
						neighbour.setgValue(smallestF.getgValue() + 1);
						neighbour.sethValue(Math.sqrt((Math.pow((neighbour.getRowX() - endX), 2) + Math.pow((neighbour.getColY() - endY), 2))));
						neighbour.setfValue(neighbour.getgValue() + neighbour.gethValue());
						
						not_visited_yet.add(neighbour);
						
						repaint();
					    try {
					        Thread.sleep((100 * 4) / speed);
					    } catch (InterruptedException e) {
					        e.printStackTrace();
					    }
					}
				}
			}
			
			//Gets the path by getting the parent cell of the current cell each time until start cell is reached. Adds these to an ArrayList.
			if(endReached == false) {
				System.out.println("Path not found!");
				return;
			} else {
				ArrayList<Cell> path = new ArrayList<Cell>();
				path.add(grid[endX][endY]);
				path.add(visitedCells[endX][endY]);
				int endCorX = visitedCells[endX][endY].getRowX();
				int endCorY = visitedCells[endX][endY].getColY();
				while(endCorX != startX || endCorY != startY) {
					Cell temp = visitedCells[endCorX][endCorY];
					path.add(temp);
					endCorX = temp.getRowX();
					endCorY = temp.getColY();
				}
				
				for(int i = path.size() - 1; i >= 0; i--) {
					System.out.println(path.get(i).getRowX() + ", " + path.get(i).getColY());
					grid[path.get(i).getRowX()][path.get(i).getColY()].setValue('1');
					
					repaint();
				    try {
				        Thread.sleep((100 * 4) / speed);
				    } catch (InterruptedException e) {
				        e.printStackTrace();
				    }
				}
			}
		};
		new Thread(search, "A* Search").start(); //Creates a new thread and runs the Runnable.
	}
	
	/**
	 * A method that performs a Depth first search on the grid given the starting and ending cell coordinates.
	 * @param startX
	 * 			starting cell x coordinate.
	 * @param startY
	 * 			starting cell y coordinate.
	 * @param endX
	 * 			ending cell x coordinate.
	 * @param endY
	 * 			ending cell y coordinate.
	 */
	public void solveDFS(int startX, int startY, int endX, int endY) {
		//Creates a new runnable to be ran in a new Thread.
		Runnable search = () -> {
			Stack<Cell> dfsStack = new Stack<Cell>(); //Creates an empty stack for the dfs.
			dfsStack.push(grid[startX][startY]); //Pushes the starting cell on to the stack.
			boolean reachedEnd = false; //Not reached the ending cell yet.
			while(!dfsStack.empty()) {
				Cell temp = dfsStack.pop(); //Pop the cell at the top of the stack
				temp.setVisited(true); //Set the cell as visited.
				//For all the neighbour cell of that cell, if it has not been visited yet, push on to the stack, and if it doesn't have a parent yet, add the parent.
				for(int i = 0; i < temp.getNeighbours().size(); i++) {
					Cell neighbour = temp.getNeighbours().get(i);
					if(neighbour.getValue() != '#' && neighbour.getVisited() == false) {
						dfsStack.push(neighbour);
						if(visitedCells[neighbour.getRowX()][neighbour.getColY()] == null) {
							visitedCells[neighbour.getRowX()][neighbour.getColY()] = temp;
						}
						
						repaint();
					    try {
					        Thread.sleep((50 * 4) / speed);
					    } catch (InterruptedException e) {
					        e.printStackTrace();
					    }
					}
				}
				
				//Reached the ending cell condition.
				if(temp.getRowX() == endX && temp.getColY() == endY) {
					reachedEnd = true;
					System.out.println("End Reached!");
					break;
				}
			}
			
			//Gets the path by getting the parent cell of the current cell each time until start cell is reached. Adds these to an ArrayList.
			if(reachedEnd == false) {
				System.out.println("Path not found!");
				return;
			} else {
				ArrayList<Cell> path = new ArrayList<Cell>();
				path.add(grid[endX][endY]);
				path.add(visitedCells[endX][endY]);
				int endCorX = visitedCells[endX][endY].getRowX();
				int endCorY = visitedCells[endX][endY].getColY();
				while(endCorX != startX || endCorY != startY) {
					Cell temp = visitedCells[endCorX][endCorY];
					path.add(temp);
					endCorX = temp.getRowX();
					endCorY = temp.getColY();
				}
				
				for(int i = path.size() - 1; i >= 0; i--) {
					System.out.println(path.get(i).getRowX() + ", " + path.get(i).getColY());
					grid[path.get(i).getRowX()][path.get(i).getColY()].setValue('1');
					
					repaint();
				    try {
				        Thread.sleep((50 * 4) / speed);
				    } catch (InterruptedException e) {
				        e.printStackTrace();
				    }
				}
			}
		};
		new Thread(search, "Depth First Search").start(); //Creates a new thread and runs the Runnable.
	}
	
	/**
	 * A method that performs the Dijkstra's algorithm on the grid given the starting and ending cell coordinates.
	 * @param startX
	 * 			starting cell x coordinate.
	 * @param startY
	 * 			starting cell y coordinate.
	 * @param endX
	 * 			ending cell x coordinate.
	 * @param endY
	 * 			ending cell y coordinate.
	 */
	public void solveDijkstra(int startX, int startY, int endX, int endY) {
		//Creates a new runnable to be ran in a new Thread.
		Runnable search = () -> {
			ArrayList<Cell> not_visited_yet = new ArrayList<Cell>(); //Creates a list for cell not yet visited.
			grid[startX][startY].setDijValue(0); //Set the starting cell cost to be 0.
			not_visited_yet.add(grid[startX][startY]); //Add the starting cell to the list.
			boolean endReached = false; //Not yet reached the end.
			while(!not_visited_yet.isEmpty()) {
				//Finds the smallest cost cell in the list and removes it from the list. Sets them as visited.
				Cell smallestDijkstra = not_visited_yet.get(0);
				int smallestDijkstraIndex = 0;
				for(int i = 0; i < not_visited_yet.size(); i++) {
					if(not_visited_yet.get(i).getDijValue() < smallestDijkstra.getDijValue()) {
						smallestDijkstra = not_visited_yet.get(i);
						smallestDijkstraIndex = i;
					}
				}
				not_visited_yet.get(smallestDijkstraIndex).setVisited(true);
				not_visited_yet.remove(smallestDijkstraIndex);
				
				//Ending cell reached condition.
				if(smallestDijkstra.getRowX() == endX && smallestDijkstra.getColY() == endY) {
					endReached = true;
					System.out.println("End Reached!");
					break;
				}
				
				//For all the neighbours of the removed cell, if they have not been visited yet and has no parent, update their cost if it is larger than the parent cost.
				for(int i = 0; i < smallestDijkstra.getNeighbours().size(); i++) {
					Cell neighbour = smallestDijkstra.getNeighbours().get(i);
					if(neighbour.getValue() != '#' && neighbour.getVisited() == false && visitedCells[neighbour.getRowX()][neighbour.getColY()] == null) {
						if(neighbour.getDijValue() > smallestDijkstra.getDijValue() + 1) {
							neighbour.setDijValue(smallestDijkstra.getDijValue() + 1);
							visitedCells[neighbour.getRowX()][neighbour.getColY()] = smallestDijkstra;
						}
						
						not_visited_yet.add(neighbour); //Add the neighour to the list.
						
						repaint();
					    try {
					        Thread.sleep((100 * 4) / speed);
					    } catch (InterruptedException e) {
					        e.printStackTrace();
					    }
					}
				}
			}
			
			//Gets the path by getting the parent cell of the current cell each time until start cell is reached. Adds these to an ArrayList.
			if(endReached == false) {
				System.out.println("Path not found!");
				return;
			} else {
				ArrayList<Cell> path = new ArrayList<Cell>();
				path.add(grid[endX][endY]);
				path.add(visitedCells[endX][endY]);
				int endCorX = visitedCells[endX][endY].getRowX();
				int endCorY = visitedCells[endX][endY].getColY();
				while(endCorX != startX || endCorY != startY) {
					Cell temp = visitedCells[endCorX][endCorY];
					path.add(temp);
					endCorX = temp.getRowX();
					endCorY = temp.getColY();
				}
				
				for(int i = path.size() - 1; i >= 0; i--) {
					System.out.println(path.get(i).getRowX() + ", " + path.get(i).getColY());
					grid[path.get(i).getRowX()][path.get(i).getColY()].setValue('1');
					
					repaint();
				    try {
				        Thread.sleep((100 * 4) / speed);
				    } catch (InterruptedException e) {
				        e.printStackTrace();
				    }
				}
			}
		};
		new Thread(search, "Dijkstra's Search").start(); //Creates a new thread and runs the Runnable.
	}
	
	/**
	 * Overrides the default paintComponent method in JPanel.
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Loops through the whole grid and paints the GUI.
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
            	g.setColor(Color.BLACK);
            	//Uses col for x and row for y, due to a rotation in coordinate system.
                g.drawRect(880 / this.gridSize * col, 800 / this.gridSize * row, 880 / this.gridSize, 800 / this.gridSize);
                if(grid[row][col].getValue() == '#') {
                	g.setColor(Color.BLACK);
                	g.fillRect(880 / this.gridSize * col, 800 / this.gridSize * row, 880 / this.gridSize, 800 / this.gridSize);
                }
                if(grid[row][col].getValue() == 'S') {
                	g.setColor(Color.GREEN);
                	g.fillRect(880 / this.gridSize * col, 800 / this.gridSize * row, 880 / this.gridSize, 800 / this.gridSize);
                }
                if(grid[row][col].getValue() == 'E') {
                	g.setColor(Color.RED);
                	g.fillRect(880 / this.gridSize * col, 800 / this.gridSize * row, 880 / this.gridSize, 800 / this.gridSize);
                }
                if(grid[row][col].getVisited() == true) {
                	g.setColor(Color.YELLOW);
                    g.fillRect(880 / this.gridSize * col, 800 / this.gridSize * row, 880 / this.gridSize, 800 / this.gridSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(880 / this.gridSize * col, 800 / this.gridSize * row, 880 / this.gridSize, 800 / this.gridSize);
                }
                if(grid[row][col].getValue() == '1') {
                	g.setColor(Color.GREEN);
                	g.fillRect(880 / this.gridSize * col, 800 / this.gridSize * row, 880 / this.gridSize, 800 / this.gridSize);
                	g.setColor(Color.BLACK);
                    g.drawRect(880 / this.gridSize * col, 800 / this.gridSize * row, 880 / this.gridSize, 800 / this.gridSize);
                }
            }
        }
    }
}
