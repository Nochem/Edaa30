package sudokuMain;

public class testsudoku {


	public static void main(String[] args) {
		sudoku sd = new sudoku();
		sd.backtracking();
		System.out.println(sd.matrixString());
	}

}
