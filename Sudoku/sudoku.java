package sudokuMain;

public class sudoku {
	static int matrix[][];
	private Iterator itr;
	
	public sudoku() {
		matrix = new int[9][9];
		itr = new Iterator();
	}
	
	public boolean put(int arg0, int arg1, int arg2) {
		matrix[arg0][arg1] = arg2;
		return true;
	}

	public int get(int arg0, int arg1) {
		return matrix[arg0][arg1];
	}

	public boolean backtracking() {
		int row = 0, col = 0;
		return solve(row, col);
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

	
	
	
	
	private boolean solve(int row, int col) {
		int i=1; boolean success=false;
		if (get(row,col)>0){
			if (itr.hasNext()){
				itr.next();
				if(solve(itr.currentRow,itr.currentCol)){
					return true;
				} else {
					if (itr.hasPrevious()){
						itr.previous();
					}
					return false;
				}
			}
			return true;
		}
		for (int k=0; k<9; k++){ 													//i goes from 1 to 9, attempting to place a number
			if (rowCheck(row, i) && colCheck(col, i) && clusterCheck(row, col, i)) {//Checks if i can be placed
				put(row, col, i); 													//places i
				success=true;
				break; 																//breaks for loop
			}
			i++; 																	//i is used instead of k because of the while loop below. As k is removed after break above
		}
		if (success){   	// true If current recursion successfully placed an int
			itr.next();
			if (!itr.hasNext()){
				return true;
			}
			
			while (i<10){ 															//preparation for the case that the next position failed
				if (solve(itr.currentRow,itr.currentCol)){							//launches a child on the next gridposition
					return true; 													//if child returns true the solution has been found
				} else {
					i++; 															//if child returns false, parent will increase it's int and try again
					if (rowCheck(row, i) && colCheck(col, i) && clusterCheck(row, col, i)){
						put(row, col, i);
					}		
				}
			}
			i=1;
			put(row, col, 0);
			if (itr.hasPrevious()){
				itr.previous();
			}
			 //If here the child cannot place any number no matter what int the parent holds
		}						//EG the problem is further back
		return false;			//false is returned to parent's parent.		
			
	}
	
	
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
