package aialgorithm;

import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;
public class ex4 {
	static Random rand = new Random();
	static int ratel = 3; // 跳躍速率

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scn = new Scanner(System.in);
		long a=(long) Math.pow(2,100);
		String c="";
		String best=Long.toBinaryString(0);
		int f1=calfitness(best);
		long i=1;
		long test=(long) Math.pow(2,50);
		System.out.println(test/2);
		while(i<a){
			c=Long.toBinaryString(i);
			int f2=calfitness(c);
			if(f2>f1){
				f1=f2;
				best=c;
				
				System.out.println(i);
				System.out.println(best);
				System.out.println(f1);
				System.out.println();
			}
			i++;
		}
	}

	public static int calfitness(String temp) { // 計算有多少個1
		int number=0;
		for(int i=0;i<temp.length();i++){
			char s=temp.charAt(i);
			if(s=='1'){
				number++;
			}
		}
		return number;
	}

}
