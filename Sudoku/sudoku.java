package sudokuMain;


public class sudoku {
	public static int[][] matrix;
	private Iterator itr;
	
	/**
	 * Instantiates an empty sudokumatrix and it's iterator.
	 */
	public sudoku() {
		matrix = new int[9][9];
		itr = new Iterator();
	}
	
	/**
	 Places a number nbr at location row, col in the sudokumatrix.
	 */
	public void put(int row, int col, int nbr) {
		matrix[row][col] = nbr;
	}

	/**
	Returns a Integer from the position row, col in the sudokumatrix.
	*/
	public Integer get(int row, int col) {
		return matrix[row][col];
	}
	
	//Examines the active row for collisions
	private boolean rowCheck(int row, int tryNbr) {
		for (int i = 0; i < 9; i++) {
			if (matrix[row][i] == tryNbr) {
				return false;
			}
		}
		return true;
	}

	//Examines the active column for collisions
	private boolean colCheck(int col, int tryNbr) {
		for (int i = 0; i < 9; i++) {
			if (matrix[i][col] == tryNbr) {
				return false;
			}
		}
		return true;
	}

	//Examines the active cluster for collisions
	private boolean clusterCheck(int row, int col, int tryNbr) {
		row = (row/3)*3; col = (col/3)*3;
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
				if (matrix[row+i][col+k] == tryNbr) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Clears out the matrix and resets the iterator.
	 */
	public void clear(){
		int[][] newMatrix= new int[9][9];
		matrix=newMatrix;
		itr=new Iterator();
	}
	
	/**
	 * Returns a string representation of the matrix. Use for debugging.
	 */
	public String matrixString(){
		StringBuilder sb = new StringBuilder();
		itr = new Iterator();
		int k=0;
		try {
			while (itr.hasNext()){
				if (k==9){
					sb.append("\n");
					k=0;
				}
				k++;
				sb.append(itr.next().toString());
			}
		} catch (ArrayIndexOutOfBoundsException e){	
		}
		String returnString=sb.toString();
		return returnString;
	}

	/**
	 * Backtracks through the sudoku to find the solution. Behaviour undefined for row and col values other than 0.
	 * Warning: The backtracker uses a brute force algorithm, returning false can take a -very- long time. 
	 * Worst case scenario includes 9^81 attempts!
	 */
	public boolean solve(int row, int col) {
		int tryNbr=1; boolean success=false;
		//Handles pre-input values
		if (get(row,col)>0){
			itr.next();
			if (itr.hasNext()){
				if(solve(itr.currentRow,itr.currentCol)){
					return true;
				} else {
					itr.previous();
					return false;
				}
			}
			return true;
		}
		//Attempts to place a number based on collisions
		//i goes from 1 to 9
		for (int k=0; k<9; k++){ 													
			if (rowCheck(row, tryNbr) && colCheck(col, tryNbr) && clusterCheck(row, col, tryNbr)) {
				put(row, col, tryNbr); 													
				success=true;
				break;
			}
			tryNbr++;
		}
		//If an int has been placed and there is a next position it will launch Solve() on the next position
		if (success){
			itr.next();
			if (!itr.hasNext()){
				return true;
			}
			while (tryNbr<10){
				System.out.println(itr.currentRow +""+ itr.currentCol+ " " + tryNbr);
				//If child solve() fails, it will increase parent solve() by 1 and try again
				if (solve(itr.currentRow,itr.currentCol)){
					return true;
				} else {
					tryNbr++;
					if (rowCheck(row, tryNbr) && colCheck(col, tryNbr) && clusterCheck(row, col, tryNbr)){
						put(row, col, tryNbr);
					}		
				}
			}
			//If the solve() reaches here, the child has failed no matter what value the parent holds
			//The parent witll then return false to it's parent that will increase it's number
			itr.previous();
			put(row, col, 0);
			return false;	
		}
		return false;
	}
	

	public boolean solveSane(int row, int col, int tryNbr) {
		boolean success=false;
		//Handles pre-input values
		if (get(row,col)>0){
			itr.next();
			if (itr.hasNext()){
				if(solve(itr.currentRow,itr.currentCol)){
					return true;
				} else {
					itr.previous();
					return false;
				}
			}
			return true;
		}
		//Attempts to place a number based on collisions
		//i goes from 1 to 9
		for (int k=0; k<9; k++){ 													
			if (rowCheck(row, tryNbr) && colCheck(col, tryNbr) && clusterCheck(row, col, tryNbr)) {
				put(row, col, tryNbr); 													
				success=true;
				break;
			}
			tryNbr++;
		}
		//If an int has been placed and there is a next position it will launch Solve() on the next position
		if (success){
			itr.next();
			if (!itr.hasNext()){
				return true;
			}
			if (solve(itr.currentRow,itr.currentCol)){
				return true;
			}
			itr.previous();
			put(row, col, 0);
			return false;	
		}
		return false;
	}
	
	public boolean solveSaneStart(){
		for (int i=1; i<10; i++){
			System.out.println("SolveSane" + i);
			if (solveSane(0,0,i)){
				return true;
			}
			
		}
		return false;
	}
	
	
	
	//Private nestled iterator class.
	private class Iterator implements java.util.Iterator<Integer>{
		int currentRow, currentCol;
		public Iterator() {
			currentRow=0;
			currentCol=0;
		}
		
		public Integer next(){
			int returnInt=matrix[currentRow][currentCol];
			if (currentCol<8){
				currentCol++;
			} else {
				currentRow++;
				currentCol=0;
			}
			return returnInt;
		}
		
		public Integer previous(){
			int returnInt=matrix[currentRow][currentCol];
			if (currentCol>0){
				currentCol--;
			} else {
				currentCol=8;
				currentRow--;
			}
			return returnInt;
		}
		
		public boolean hasPrevious(){
			if (currentRow==0 && currentCol==0){
				return false;
			}
			return true;
		}
		
		public boolean hasNext(){
			if (currentRow==9 && currentCol==0){
				return false;
			}
			return true;
		}
	}	
}