package test;

import map.*;
import java.util.Random;

public class TestUseSimpleHashMap {

	public static void main(String[] args) {
		SimpleHashMap<Integer, Integer> test = new SimpleHashMap<Integer, Integer>();
		Random rand = new Random();
		for (int k=0; k<10; k++){
			int i=rand.nextInt(100);
			test.put(-i, -i); //test.put(i, i);
		}
		System.out.print(test.show());
		 
			

	}

}
