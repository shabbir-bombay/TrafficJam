/**
 * Simple class that represents a row and a column, with simple getters and setters for both
 * @author Osvaldo
 */

public class Location {
	//TODO Put your instance variables here
	private int Row;
	private int Col;
	/**
	 * The constructor that will set up the object to store a row and column
	 * 
	 * @param row
	 * @param col
	 */
	public Location(int row, int col) {
		//TODO: this is the constructor, you'll need to fill this in
		Row = row;
		Col = col;
	}
	
	public int getRow() {
		return Row;
	}

	public void setRow(int lRow) {
		this.Row = lRow;
	}

	public int getCol() {
		return Col;
	}

	public void setCol(int lCol) {
		this.Col = lCol;
	}
	
	@Override
	public String toString(){
		return "r"+Row+""+"c"+Col;
	}
	
	//Small test code to put in Location.java to check to see if your class works 
//	public static void main(String[] args) {
//		Location one = new Location(3, 4); 
//		Location two = new Location(1, 6);
//	  
//		two.setRow(two.getRow()+1);
//		two.setCol(two.getCol()-1);
//		System.out.println("one r: " + one.getRow() + ", c: " + one.getCol()); System.out.println("two r: " + two.getRow() + ", c: " + two.getCol());
//	}

}
