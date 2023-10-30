import java.util.*;


public class Level {	
	private Board board;
	private Location winLoc;
	private int numOfMoves;
	private Boolean finished;
	
	//TODO fill out this class with a Level constructor
	//all the other methods necessary and any other instance variables needed
	public Level(int nRows, int nCols) {
		this.board = new Board(nRows, nCols);
		// my car location
		int myCarRow = 2;
		int myCarCol = 0;
		Boolean myCarIsVer = false;
		int myCarLen = 2;
		level1(myCarRow,myCarCol,myCarIsVer,myCarLen);
		if(myCarIsVer) {
			//this.winLoc = new Location(nRows - 1,myCarCol);
			this.winLoc = new Location(nRows - 1,myCarCol);
		}
		else {
			this.winLoc = new Location(myCarRow,nCols - 1);
		}
		this.numOfMoves = 0;
		this.finished = false;
	}
	
	public int getNumOfMoves() {
		return this.numOfMoves;
	}

	public void incNumOfMoves() {
		this.numOfMoves = numOfMoves + 1;
	}

	public Boolean isFinished() {
		setFinished();
		return finished;
	}

	private void setFinished() {
		if(this.board.getVehicleAt(this.winLoc) == null) {
			finished = false;
			return;
		}
		if((this.board.getVehicleAt(this.winLoc)).getVehicleType().toString() == "car") {
			finished = true;
		}
		else {
			finished = false;
		}
	}
	
	public ArrayList<Vehicle> getVehiclesOnBoard() {
		return this.board.getVehiclesOnBoard();
	}
	
	public Location getWinLoc() {
		return winLoc;
	}
	
	public void level1(int myCarRow, int myCarCol, Boolean myCarIsVer, int myCarLen) {
		board.addVehicle(VehicleType.MYCAR, myCarRow, myCarCol, myCarIsVer, myCarLen);
		board.addVehicle(VehicleType.TRUCK, 0, 2, true, 3);
		board.addVehicle(VehicleType.AUTO, 0, 4, false, 2);
		board.addVehicle(VehicleType.AUTO, 1, 4, true, 2);
		board.addVehicle(VehicleType.AUTO, 1, 5, true, 2);
		board.addVehicle(VehicleType.AUTO, 3, 3, true, 2);
		board.addVehicle(VehicleType.AUTO, 4, 4, false, 2);
		
	}
	
	/**
	 * @return the number of columns on the board
	 */
	
	public Vehicle getVehicleAt(Location s) {
		return board.getVehicleAt(s);
	}
	
	public boolean moveVehicleAt(Location start, int numSpaces) {
		return board.moveVehicleAt(start, numSpaces);
	}
	
	public boolean canMoveVehicleAt(Location start, int numSpaces) {
		return board.canMoveAVehicleAt(start, numSpaces);
	}
	
	public int getRows() {
		//TODO: have this return the number of columns in the level
		return board.getNumRows();
	}
	
	public int getColumns() {
		//TODO: have this return the number of columns in the level
		return board.getNumCols();
	}
	
	//Methods already defined for you
	/**
	 * generates the string representation of the level, including the row and column headers to make it look like
	 * a table
	 * 
	 * @return the string representation
	 */
	public String toString() {
		String result = generateColHeader(getColumns());
		result+=addRowHeader(board.toString());
		return result;
	}
	
	/**
	 * This method will add the row information
	 * needed to the board and is used by the toString method
	 * 
	 * @param origBoard the original board without the header information
	 * @return the board with the header information
	 */
	private String addRowHeader(String origBoard) {
		String result = "";
		String[] elems = origBoard.split("\n");
		for(int i = 0; i < elems.length; i++) {
			result += (char)('A' + i) + "|" + elems[i] + "\n"; 
		}
		return result;
	}
	
	/**
	 * This one is responsible for making the row of column numbers at the top and is used by the toString method
	 * 
	 * @param cols the number of columns in the board
	 * @return if the # of columns is five then it would return "12345\n-----\n"
	 */
	private String generateColHeader(int cols) {
		String result = "  ";
		for(int i = 1; i <= cols; i++) {
			result+=i;
		}
		result+="\n  ";
		for(int i = 0; i < cols; i++) {
			result+="-";
		}
		result+="\n";
		return result;
	}
}
