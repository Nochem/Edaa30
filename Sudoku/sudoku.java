package sudokuMain;

public class sudoku {
	static int matrix[][];
	private Iterator itr;
	
	public sudoku() {
		matrix = new int[9][9];
		itr = new Iterator();
	}
	
	public boolean put(int arg0, int arg1, int arg2) {
		if (matrix[arg0][arg1] == 0) {
			matrix[arg0][arg1] = arg2;
			return true;
		}
		return false;
	}

	public int get(int arg0, int arg1) {
		return matrix[arg0][arg1];
	}

	public boolean backtracking() {
		int row = 0, col = 0, tryNbr = 1;
		return selectNbrRecursive(row, col);
	}

	private boolean rowCheck(int row, int tryNbr) {
		for (int i = 0; i < 9; i++) {
			if (matrix[row][i] == tryNbr) {
				return false;
			}
		}
		return true;
	}

	private boolean colCheck(int col, int tryNbr) {
		for (int i = 0; i < 9; i++) {
			if (matrix[i][col] == tryNbr) {
				return false;
			}
		}
		return true;
	}

	private boolean clusterCheck(int row, int col, int tryNbr) {
		int modRow = row%3, modCol = col%3, startRow=row-modRow, startCol=col-modCol;
		for (int i = startRow; i < (startRow + 3); i++) {
			for (int k = startCol; k < (startCol + 3); k++) {
				if (matrix[i][k] == tryNbr) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public String matrixString(){
		StringBuilder sb = new StringBuilder();
		itr = new Iterator();
		while (itr.hasNext()){
			sb.append(itr.next().toString()+ "\n");
		}
		String returnString=sb.toString();
		return returnString;
	}

	
	
	
	
	private boolean selectNbrRecursive(int row, int col) {
		int i=1;
		boolean success=false;
		for (int k=0; k<9; k++){ //i goes from 1 to 9, attempting to place a number
			if (rowCheck(row, i) && colCheck(col, i) && clusterCheck(row, col, i)) {//Checks if i can be placed
				put(row, col, i); //places i
				success=true;
				break; //breaks for loop
			}
			i++; //i is used instead of k because of the while loop below. As k is removed after break above
		}
		if (success){   // true If current recursion successfully placed an int
			if (!itr.hasNext()){
				return true;
			}
			itr.next();
			while (i<9){ //preparation for the case that the next position failed
				if (selectNbrRecursive(itr.currentRow,itr.currentCol)){//launches a child on the next gridposition
					return true; //if child returns true the solution has been found
				}
				i++; //if child returns false, parent will increase it's int and try again
			}
			return false;  //If here the child cannot place any number no matter what int the parent holds
		}							//EG the problem is further back
		return false;			//false is returned to parent's parent.		
	}			
	
	
	
	private class Iterator implements java.util.Iterator<Integer>{
		int currentRow, currentCol;
		
		public Iterator() {
			currentRow=0;
			currentCol=0;
		}

		public Integer next(){
			if (currentCol<8){
				currentCol++;
			} else {
				currentRow++;
				currentCol=0;
			}
			return matrix[currentRow][currentCol];
		}
		
		public boolean hasNext(){
			if (currentRow>8 && currentRow>8){
				return false;
			}
			return true;
		}
	}	
}
