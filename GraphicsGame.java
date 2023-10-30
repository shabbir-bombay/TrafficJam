import acm.program.*;
import acm.graphics.*;

import java.awt.event.MouseEvent;
import java.util.*;

public class GraphicsGame extends GraphicsProgram {
	/**
	 * Here are all of the constants
	 */
	public static final int PROGRAM_WIDTH = 500;
	public static final int PROGRAM_HEIGHT = 500;
	public static final String lABEL_FONT = "Arial-Bold-22";
	public static final String EXIT_SIGN = "EXIT";
	public static final String IMG_FILENAME_PATH = "images/";
	public static final String IMG_EXTENSION = ".png";
	public static final String VERTICAL_IMG_FILENAME = "_vert";
	public static final int NUM_ROWS = 6;
	public static final int NUM_COLS = 6;

	// TODO declare your instance variables here
	private Level level;
	private GLine newLine;
	private GLabel label;
	private GLabel label1;
	private GImage vehicle;
	private GObject toDrag;
	private int lastX;
	private int lastY;
	private Location start;
	private Location end;


	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
	}

	public void run() {
		// TODO write this part, which is like your main function
		drawLevel();
		addMouseListeners();
	}

	private void drawLevel() {
		// TODO write the code to draw the entire level, which should
		// mostly be calls to some of your helper functions.
		level = new Level(NUM_ROWS, NUM_COLS);
		drawGridLines();
		drawCars();
		
		drawWinningTile();
		drawScoreTile();
	}

	/**
	 * This should draw the label EXIT and add it to the space that represents
	 * the winning tile.
	 */
	private void drawWinningTile() {
		double xCord = (level.getWinLoc().getCol()) * cellWidth();
		double yCord = (level.getWinLoc().getRow()) * cellHeight();
		label = new GLabel(EXIT_SIGN, xCord + (cellWidth()/4), yCord + (cellHeight()* .6));
		label.setFont(lABEL_FONT);
		add(label);
	}
	
	private void drawScoreTile() {
		label1 = new GLabel(String.valueOf(level.getNumOfMoves()), 0, cellHeight() * .8);
		label1.setFont(lABEL_FONT);
		label1.scale(2.5);
		add(label1);
	}
	/**
	 * draw the lines of the grid. Test this out and make sure you have it
	 * working first. Should draw the number of grids based on the number of
	 * rows and column in Level
	 */
	private void drawGridLines() {
		//loop for rows
		int xCord = 0;
		int yCord = 0;
		for (int i = 0; i < level.getRows(); i++) {
			xCord += cellHeight();
			newLine = new GLine(0, xCord, PROGRAM_WIDTH, xCord);
			add(newLine);
		}
		//loop for columns
		for (int i = 0; i < level.getColumns(); i++) {
			yCord += cellWidth();
			newLine = new GLine( yCord , 0 , yCord, PROGRAM_HEIGHT);
			add(newLine);
		}
	}

	/**
	 * Maybe given a list of all the cars, you can go through them and call
	 * drawCar on each?
	 */
	private void drawCars() {
		for(int i = 0; i < level.getVehiclesOnBoard().size(); i++) {
			drawCar(level.getVehiclesOnBoard().get(i));
		}
		return;
	}

	/**
	 * Given a vehicle object, which we will call v, use the information from
	 * that vehicle to then create a GImage and add it to the screen. Make sure
	 * to use the constants for the image path ("/images"), the extension ".png"
	 * and the additional suffix to the filename if the object is vertical when
	 * creating your GImage. Also make sure to set the images size according to
	 * the size of your spaces
	 * 
	 * @param v
	 *            the Vehicle object to be drawn
	 */
	private void drawCar(Vehicle v) {
		double xSize = 0;
		double ySize = 0;
		String vehicleFileName = IMG_FILENAME_PATH + v.getVehicleType().toString();
		if(v.isVertical()) {
			vehicleFileName += VERTICAL_IMG_FILENAME;
			xSize = cellWidth();
			ySize = v.getLength() * cellHeight();
		}
		else {
			ySize = cellHeight();
			xSize = v.getLength() * cellWidth();
		}
		vehicleFileName += IMG_EXTENSION;
		
		double xCord = (v.getStartingPos().getCol()) * cellWidth();
		double yCord = (v.getStartingPos().getRow()) * cellHeight();
		
		vehicle = new GImage(vehicleFileName,xCord,yCord);
		vehicle.setSize(xSize, ySize);
		add(vehicle);
	}

	// TODO implement the mouse listeners here

	/**
	 * Given a xy coordinates, return the Vehicle that is currently at those x
	 * and y coordinates, returning null if no Vehicle currently sits at those
	 * coordinates.
	 * 
	 * @param x
	 *            the x coordinate in pixels
	 * @param y
	 *            the y coordinate in pixels
	 * @return the Vehicle object that currently sits at that xy location
	 */
//	private Vehicle getVehicleFromXY(double x, double y) {
//		// TODO fix this implementation
//		Location tempLoc = convertXYToLocation(x,y);
//		Vehicle tempVehi = level.getVehicleAt(tempLoc);
////		System.out.println(calculateNumSpacesMoved());
////		System.out.println(level.canMoveVehicleAt(start, calculateNumSpacesMoved()));
//		return tempVehi;
//	}
	
	private void isFinished() {
//		boolean isFin = level.isFinished();
		if(level.isFinished()) {
			removeAll();
			label.setLabel("Congratulation Brother, you did it");
			label.setLocation(PROGRAM_WIDTH*.16, PROGRAM_HEIGHT*.4);
			add(label);
			label1.setLabel("See you soon, Bye Bye");
			label1.setLocation(PROGRAM_WIDTH*.2, PROGRAM_HEIGHT*.5);
			label1.scale(0.5);
			add(label1);
			GLabel score = new GLabel("You took " + 
			String.valueOf(level.getNumOfMoves()) + " moves",PROGRAM_WIDTH*.3, PROGRAM_HEIGHT*.6);
			score.setFont(lABEL_FONT);
			add(score);
		
		}
	}
	
	private void moveVehicle() {
		
//		System.out.println(calculateNumSpacesMoved());
//		System.out.println(level.canMoveVehicleAt(start, calculateNumSpacesMoved()));
		int numSpace = calculateNumSpacesMoved();
		if(level.canMoveVehicleAt(start, numSpace) && (numSpace != 0)) {
			if(level.getVehicleAt(start).isVertical()) {
				int difX = level.getVehicleAt(start).getStartingPos().getRow() - start.getRow();
				toDrag.setLocation((start.getCol()) * cellWidth(),(start.getRow() + difX + 
						numSpace) * cellHeight());
			}
			else {
				int difY = level.getVehicleAt(start).getStartingPos().getCol() - start.getCol();
				toDrag.setLocation((start.getCol() + difY + numSpace) * cellWidth(),
						(start.getRow()) * cellHeight());
			}
			level.moveVehicleAt(start, numSpace);
			level.incNumOfMoves();
			label1.setLabel(String.valueOf(level.getNumOfMoves()));
			
//			System.out.println(level.getNumOfMoves());
		}
		else {
			toDrag.setLocation((level.getVehicleAt(start).getStartingPos().getCol()) 
					* cellWidth(),(level.getVehicleAt(start).getStartingPos().getRow())
					* cellHeight());
		}
	}

	/**
	 * This is a useful helper function to help you calculate the number of
	 * spaces that a vehicle moved while dragging so that you can then send that
	 * information over as numSpacesMoved to that particular Vehicle object.
	 * 
	 * @return the number of spaces that were moved
	 */
	private int calculateNumSpacesMoved() {
		int numSpaceMoved;
		int diffX = end.getRow() - start.getRow();
		int diffY = end.getCol() - start.getCol();
		if(level.getVehicleAt(start) == null) {
			return 0;
		}
		if(level.getVehicleAt(start).isVertical()) {
			numSpaceMoved = diffX;
		}
		else {
			numSpaceMoved = diffY;
		}
		System.out.println("Number of spaced moved " + numSpaceMoved);
		System.out.println("Data about the vehicle clicked \n" + level.getVehicleAt(start));
		
		return numSpaceMoved;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	    //code to tell the computer what to do when the mouse is pressed
//		System.out.println("clicked");	
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	    //code to tell the computer what to do when the mouse is pressed
//		System.out.println("moved");	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		toDrag = getElementAt(e.getX(), e.getY());
		if(!(toDrag instanceof GImage)){ 
			toDrag = null;
			return;}
		lastX = e.getX();
		lastY = e.getY();
		start = convertXYToLocation(lastX,lastY);
//		System.out.println(level.getVehicleAt(start));
//		System.out.println(toDrag.getLocation());
//		System.out.println("pressed");
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(toDrag != null) {
			if(!(toDrag instanceof GImage)){ return;}
			double y = (toDrag.getLocation().getY() - lastY) + (e.getY() - lastY);
			double x = (toDrag.getLocation().getX() - lastX) + (e.getX() - lastX);
			
			if(!level.getVehicleAt(start).isVertical()) {
				y = (toDrag.getLocation().getY() - lastY);
			}
			else if(level.getVehicleAt(start).isVertical() == true){
				x = (toDrag.getLocation().getX() - lastX);
			}
			
			toDrag.setLocation(lastX+ x, lastY + y);
			lastX = e.getX();
			lastY = e.getY();
//			System.out.println("dragged");
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (toDrag == null) {return;}
		end = convertXYToLocation(e.getX(),e.getY());
//		System.out.println(calculateNumSpacesMoved());
			if(e.getX() < 1 || e.getY() < 1) {
				end = convertXYToLocation(e.getX() + 1,e.getY() + 1);
				toDrag.setLocation((level.getVehicleAt(start).getStartingPos().getCol()) 
						* cellWidth(),(level.getVehicleAt(start).getStartingPos().getRow())
						* cellHeight());
//				System.out.println("exit up");
				return;
			}
			else if(e.getX() >  PROGRAM_WIDTH || e.getY() > PROGRAM_HEIGHT){
				end = convertXYToLocation(e.getX() - 1,e.getY() - 1);
				toDrag.setLocation((level.getVehicleAt(start).getStartingPos().getCol()) 
						* cellWidth(),(level.getVehicleAt(start).getStartingPos().getRow())
						* cellHeight());
//				System.out.println("exit down");
				return;
			}
			
		moveVehicle();
		isFinished();
//		System.out.println(level);
//		System.out.println("released");
		
	}
	
//	@Override
//	public void mouseExited(MouseEvent e) {
//		System.out.println("Exited");
////		if((toDrag instanceof GImage)){
////			if(e.getX() < 1 || e.getY() < 1) {
////				end = convertXYToLocation(e.getX() + 1,e.getY() + 1);
////			}
////			else {
////				end = convertXYToLocation(e.getX() - 1,e.getY() - 1);
////			}
////			
////		}
//		toDrag.setLocation((level.getVehicleAt(start).getStartingPos().getCol()) 
//				* cellWidth(),(level.getVehicleAt(start).getStartingPos().getRow())
//				* cellHeight());
//	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	
	/**
	 * Another helper function/method meant to return the location given an x and y
	 * coordinate system. Use this to help you write getVehicleFromXY
	 * 
	 * @param x
	 *            x-coordinate (in pixels)
	 * @param y
	 *            y-coordinate (in pixels)
	 * @return the Location associated with that x and y
	 */
	private Location convertXYToLocation(double x, double y) {
		// TODO write this implementation hint (use helper methods below)
		Location converted = new Location(-1,-1);
		if((x > 0) && (y > 0)) {
			double last = 0;
			double next = cellWidth();
			int yloc = 0;
			//find row
			while(!(x > last) || !(x <= next)) {
				yloc++;
				last += cellWidth();
				next += cellWidth();
			}
			last = 0;
			next = cellHeight();
			int xloc = 0;
			//find column
			while(!(y > last) || !(y <= next)) {
				xloc++;
				last += cellHeight();
				next += cellHeight();
			}
	//		System.out.println("x " + xloc + ", y " + yloc);
			converted = new Location(xloc,yloc);
		}
		
		
		return converted;
	}

	
	/**
	 * 
	 * @return the width (in pixels) of a single cell in the grid
	 */
	private double cellWidth() {
		return PROGRAM_WIDTH/level.getColumns();
	}

	/**
	 * 
	 * @return the height in pixels of a single cell in the grid
	 */
	private double cellHeight() {
		return PROGRAM_HEIGHT/level.getRows();
	}
	
	public static void main(String[] args) {
		new GraphicsGame().start();
		
	}
}
