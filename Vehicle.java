public class Vehicle {
	// TODO You'll need to fill in this entire file
	private Location startingPos;
	private int length;
	private boolean  isVertical;
	private VehicleType type;
	/**
	 * @return the type associated with this particular vehicle
	 */
	
	
	public VehicleType getVehicleType() {
		// TODO change this implementation so that you return the vehicles
		// actual type, which should be stored in a variable
		// . Right now it only returns the type mycar
			return type;
	}

	public Vehicle(VehicleType type, int row, int col, boolean isVertical, int length) {
		this.startingPos = new Location(row, col);
		this.length = length;
		this.isVertical = isVertical;
		this.type = type;
	}

	public Location getStartingPos() {
		return startingPos;
	}

	public void setStartingRow(int row) {
		this.startingPos.setRow(row);
	}
	
	public void setStartingCol(int col) {
		this.startingPos.setCol(col);
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isVertical() {
		return isVertical;
	}

//	public void setVertical(boolean isVertical) {
//		this.isVertical = isVertical;
//	}

	public void setType(VehicleType type) {
		this.type = type;
	}
	
	public Location potentialMove(int numOfSpaces) {
		int y = this.startingPos.getCol();
		int x = this.startingPos.getRow();
		
		if(!this.isVertical) {
			y = y + numOfSpaces;
		}
		else {
			x = x + numOfSpaces;
		}
		
		return new Location(x,y);
	}
	
	public void move(int numOfSpaces) {
		int y = this.startingPos.getCol();
		int x = this.startingPos.getRow();
		
		if(!this.isVertical) {
			this.setStartingCol(y + numOfSpaces);
		}
		else {
			this.setStartingRow(numOfSpaces + x);
		}
		
		return;
	}

	/**
	 * Provides an array of Locations that indicate where a particular Vehicle
	 * would be on top of, calculated from the Vehicle's starting space (see narration)
	 * 
	 * @return the array of Spaces occupied by that particular Vehicles
	 */
	public Location[] locationsOn() {
		// TODO change this implementation so that you return the correct spaces
		int x = this.startingPos.getRow();
		int y = this.startingPos.getCol();
		int len = this.length;
		
		int i = len - 1;
		
		Location cordsUsed[] = new Location[len] ;
		for(int j = i; j >= 0; j--, i--) {
			if (!this.isVertical) {
				cordsUsed[i] = new Location(x,y+i);
			}
			else {
				cordsUsed[i] = new Location(x+i, y);
			}
		}
		
		return cordsUsed;
	}

	/**
	 * Calculates an array of the locations that would be traveled if a vehicle
	 * were to move a certain number of path, which represents the path taken
	 * 
	 * @param numSpaces
	 *            The number of spaces to move (can be negative or positive)
	 * @return The array of Locations that would need to be checked for Vehicles
	 */
	public Location[] locationsPath(int numSpaces) {
		// TODO change this implementation so that you return the correct space
		
		int x = this.startingPos.getRow();
		int y = this.startingPos.getCol();
		int len = this.length-1;
		
		int i = numSpaces;
		if (numSpaces > 0) {
			if(this.isVertical) {
				x = x + len;
			}
			else {
				y = y + len;
			}
			
		}
		else {
			if(this.isVertical) {
				x = x -1;
			}
			else {
				y = y -1;
			}
			i= i + 1;
		}
		
		Location cordsWillUsed[] = new Location[Math.abs(numSpaces)];
		for(int j = Math.abs(numSpaces); j > 0; j--, i--) {
			if (!this.isVertical) {
				if(numSpaces > 0) {
					cordsWillUsed[j-1] = new Location(x,y + i);
				}
				else {
					cordsWillUsed[j-1] = new Location(x,y--);
				}
				
			}
			else {
				if (numSpaces > 0) {
					cordsWillUsed[j-1] = new Location(x + i,y);
				}
				else {
					cordsWillUsed[j-1] = new Location(x--,y);
				}
				
			}
		}	
		
		return cordsWillUsed;
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String carDetails = "";
		carDetails = "Car Type - "+ type.toString() +"\n" + "Starting Location (" + 
		this.getStartingPos().toString() +") \n" + "isVertical - " + this.isVertical() + "\n" ;
		
		return carDetails;
	}

	// prints out more legibly the row & columns for an array of locations 
	public static void printLocations(Location[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.print("r" + arr[i].getRow() + "c" + arr[i].getCol() + "; "); 
		}
		System.out.println(); 
	}
	
	public static void main(String[] args) {
		//Vehicle someTruck = new Vehicle(VehicleType.TRUCK, 1, 1, true, 3);
		Vehicle someAuto = new Vehicle(VehicleType.AUTO, 0, 0, false, 2); 
		Vehicle someCar = new Vehicle(VehicleType.AUTO, 0, 0, false, 2);
//		System.out.println("This next test is for locationsOn: "); 
//		System.out.println("vert truck at r1c1 should give you r1c1; r2c1; r3c1 as "
//				+ "the locations its on top of...does it?"); 
//		printLocations(someTruck.locationsOn());
//		System.out.println("horiz auto at r2c2 should give you r2c2; r2c3 as the"
//				+ " locations its on top of...does it?"); 
		printLocations(someAuto.locationsPath(-4));
		printLocations(someCar.locationsPath(4));
//		System.out.println("if we were to move horiz auto -2 it should give you"
//				+ " at least r2c0; r2c1; it may also add r2c2; r2c3 to its "
//				+ "answer...does it?"); 
//		printLocations(someAuto.locationsPath(-2));
//		
//		System.out.println("");
//		System.out.println(someTruck.toString());
//		System.out.println(someAuto.toString());
//		
//		System.out.println(someAuto.potentialMove(3).toString());
//		someAuto.move(3);
//		System.out.println("");
//		System.out.println(someAuto.toString());
//		
//		printLocations(someAuto.locationsPath(-5));
//		printLocations(someAuto.locationsPath(2));
		//ADD SOME MOVE AND POTENTIALMOVE TEST CODE BELOW THIS LINE
	}
}
