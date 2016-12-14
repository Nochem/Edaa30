package sudokuMain;

public class testsudoku {


	public static void main(String[] args) {
		sudoku sd = new sudoku();
		/*
		sd.put(0, 0, 1);
		sd.put(0, 1, 2);
		sd.put(0, 2, 3);
		sd.put(1, 0, 4);
		sd.put(1, 1, 5);
		sd.put(1, 2, 6);
		sd.put(2, 0, 7);
		sd.put(2, 1, 8);
		//sd.put(8, 2, 9);// this causes LONG runtime!
		*/
		boolean ts =sd.solve(0,0);
		if (!ts){
			System.out.println("HEre");
		}
		System.out.println(sd.matrixString());
	}

}
