import java.util.*;
import java.util.ArrayList;

/**
 * This represents the board.  Really what it is going to do is just have a 2d array of the vehicles
 * (which we'll refer to as grid), and it will be in charge of doing the bounds type checking for doing any of the moves.
 * It will also have a method display board which will give back a string representation of the board
 * 
 * @author Osvaldo
 *
 */

public class Board {
	//ArrayList<Vehicle> allVehicles;
	private ArrayList<Vehicle> allVehicles = new ArrayList<Vehicle>();
	private Vehicle[][] grid;
	private int gridRows;
	private int gridCols;
	
	private Location convertXYToLocation(double x, double y , double w, double h) {
		// TODO write this implementation hint (use helper methods below)
		double cellWidth = w;
		double cellHeight = h;
		
		double last = 0;
		double next = cellWidth;
		int yloc = 0;
		//find row
		while(!(x > last) || !(x <= next)) {
			yloc++;
			last += cellWidth;
			next += cellWidth;
		}
		last = 0;
		next = cellHeight;
		int xloc = 0;
		//find column
		while(!(y > last) || !(y <= next)) {
			xloc++;
			last += cellHeight;
			next += cellHeight;
		}
		System.out.println("r" + xloc + "c" + yloc);
		
		return null;
	}
	
	//TODO Add the other methods that are in the handout, and fill out the rest of this file
	
	/**
	 * Constructor for the board which sets up an empty grid of size rows by columns
	 * Use the first array index as the rows and the second index as the columns
	 * 
	 * @param rows number of rows on the board
	 * @param cols number of columns on the board
	 */
	public Board(int rows, int cols) {
		//TODO finish implementing this constructor
		this.grid = new Vehicle[rows][cols];
		this.gridRows = rows;
		this.gridCols = cols;
	}
	
	/**
	 * @return number of columns the board has
	 */
	public int getNumCols() {
		//TODO change this method, which should return the number of columns the grid has
		return gridCols;
	}

	/**
	 * @return number of rows the board has
	 */
	public int getNumRows() {
		//TODO change this method, which should return the number of rows the grid has
		return gridRows;
	}
	
	/**
	 * Grabs the vehicle present on a particular space if any is there
	 * If a Vehicle occupies three spaces, the same Vehicle pointer should be returned for all three spaces
	 * 
	 * @param s the desired space where you want to look to see if a vehicle is there
	 * @return a pointer to the Vehicle object present on that space, if no Vehicle is present, null is returned
	 */
	public Vehicle getVehicleAt(Location s) {
		//TODO change this method
		int r = s.getRow();
		int c = s.getCol();
		if((r == -1) && (c == -1)) {
			return null;
		}
		if(this.grid[r][c] == null) {
			return null;
		}
//		System.out.println(this.grid[r][c]);
		return this.grid[r][c];
	}

	/**
	 * adds a vehicle to the board. It would be good to do some checks for a legal placement here.
	 * 
	 * @param type type of the vehicle
	 * @param startRow row for location of vehicle's top
	 * @param startCol column for for location of vehicle leftmost space
	 * @param vert true if the vehicle should be vertical
	 * @param length number of spaces the vehicle occupies on the board
	 */
	public void addVehicle(VehicleType type, int startRow, int startCol, boolean vert, int length) {
		//TODO implement this method, which should addAVehicle to the grid
		boolean occupied = false;
		Vehicle car = new Vehicle(type, startRow, startCol, vert, length); 
		Location[] locOn = car.locationsOn();
		for(int i = 0; i < locOn.length; i++) {
			int x = locOn[i].getRow();
			int y = locOn[i].getCol();
			if((x < 0)||(x >= getNumRows()) || (y < 0)||(y >= getNumCols())) {
				return;
			}
			
			if(getVehicleAt(locOn[i]) != null) {
				occupied = true;
				break;
			}
			
		}
		
		if(!occupied) {
			for(int i = 0; i < locOn.length; i++) {
				int x = locOn[i].getRow();
				int y = locOn[i].getCol();
				this.grid[x][y] = car;
			}
			allVehicles.add(car);
			//getVehiclesOnBoard();
		}
		return;
		
	}
	
	public ArrayList<Vehicle> getVehiclesOnBoard() {
		
		return this.allVehicles;
	}

	/**
	 * This method moves the vehicle at a certain location a specific number of spaces and updates the board's grid to reflect it
	 * 
	 * @param start the starting location of the vehicle in question
	 * @param numSpaces the number of spaces to be moved by the vehicle (can be positive or negative)
	 * @return whether or not the move actually happened
	 */
	public boolean moveVehicleAt(Location start, int numSpaces) {
		//TODO change this method to implementing moving a vehicle that is on a certain row/column a certain number of spaces
		if(canMoveAVehicleAt(start,numSpaces) == true) {
			int r = start.getRow();
			int c = start.getCol();
			Vehicle car = this.grid[r][c];
			Location[] locOn = car.locationsOn();
			Location potMove = car.potentialMove(numSpaces);
			r = potMove.getRow();
			c = potMove.getCol();
			
			for(int i = 0; i < locOn.length; i++) {
				int x = locOn[i].getRow();
				int y = locOn[i].getCol();
				this.grid[x][y] = null;
			}
			addVehicle(car.getVehicleType(), r, c, car.isVertical(), car.getLength());
			return true;
		}
		return false;
	}
	
	/**
	 * This method just checks to see if the vehicle on a certain location can move a specific number of spaces, though
	 * it will not move the vehicle.  You should use this when you wish to move or want to see if you can
	 * move a vehicle numSpaces without going out of bounds or hitting another vehicle
	 * 
	 * @param start the starting row/column of the vehicle in question
	 * @param numSpaces number of spaces to be moved by the vehicle (positive or negative)
	 * @return whether or not the move is possible
	 */
	public boolean canMoveAVehicleAt(Location start, int numSpaces) {
		int r = start.getRow();
		int c = start.getCol();
		Vehicle car = this.grid[r][c];
		if(car == null) {
			return false;
		}
		Location[] locPath = car.locationsPath(numSpaces);
		
	
		//boolean occupied = false;
		for(int i = 0; i < locPath.length; i++) {
			int x = locPath[i].getRow();
			int y = locPath[i].getCol();
			//outofbound check
			if((x < 0)||(x >= getNumRows()) || (y < 0)||(y >= getNumCols())) {
				return false;
			}
			
			if(getVehicleAt(locPath[i]) != null) {
				return false;
			}
			
		}
		return true;
	}
	
	
	// This method helps create a string version of the board
	// You do not need to call this at all, just let it be
	public String toString() {
		return BoardConverter.createString(this);
	}
	
	/* Testing methods down here for testing the board 
	 * make sure you run the board and it works before you write the rest of the program! */
	
	public static void main(String[] args) {
		
		
		
		Board b = new Board(5, 5);
		
		b.convertXYToLocation(25,25,100,100);
		b.convertXYToLocation(175,225,100,100);
		b.convertXYToLocation(425,50,100,100);
		b.convertXYToLocation(225,225,50,150);
		b.convertXYToLocation(225,225,150,50);
		b.convertXYToLocation(125,225,150,50);
		b.convertXYToLocation(125,225,50,150);
		b.convertXYToLocation(225,125,50,150);

		
		
//		b.addVehicle(VehicleType.MYCAR, 1, 0, false, 2);
//		b.addVehicle(VehicleType.TRUCK, 0, 2, true, 3);
//		b.addVehicle(VehicleType.AUTO, 3, 3, true, 2);
//		b.addVehicle(VehicleType.AUTO, 0, 3, true, 2);
//		for(Vehicle k : b.allVehicles) {
//			System.out.println(k.toString());
//			System.out.println("");
//		}
//		System.out.println(b);
//		testCanMove(b);
//		testMoving(b);
//		System.out.println(b);
		
	}
	
	public static void testMoving(Board b) {
		System.out.println("just moving some stuff around");
		b.moveVehicleAt(new Location(1, 2), 1);
		b.moveVehicleAt(new Location(1, 2), 1);
		b.moveVehicleAt(new Location(1, 1), 1);
	}
	
	public static void testCanMove(Board b) {
		System.out.println("Ok, now testing some moves...");
		System.out.println("These should all be true");
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(0, 2), 2));
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(1, 2), 2));
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(2, 2), 2));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(3, 3), -1));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(4, 3), -1));
		
		System.out.println("\nAnd these should all be false");
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(3, 2), 2));
		System.out.println("Moving the car into truck " + b.canMoveAVehicleAt(new Location(1, 0), 1));
		System.out.println("Moving the car into truck " + b.canMoveAVehicleAt(new Location(1, 0), 2));
		System.out.println("Moving nothing at all " + b.canMoveAVehicleAt(new Location(4, 4), -1));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(3, 3), -2));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(4, 3), -2));
		System.out.println("Moving upper auto up " + b.canMoveAVehicleAt(new Location(0, 3), -1));
		System.out.println("Moving upper auto up " + b.canMoveAVehicleAt(new Location(1, 3), -1));
		
	}
}
