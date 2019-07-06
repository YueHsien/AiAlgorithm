package aialgorithm;

import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class implement {
	static Random rand = new Random();
	static int ratel = 3; // ∏ı≈D≥t≤v

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scn = new Scanner(System.in);
		String s=scn.next();
		if(s.equals("violence")){
			ex3 a=new ex3();
			a.main(args);
		}else if(s.equals("hc")){
			ex4 b=new ex4();
			b.main(args);
		}
	}
}
