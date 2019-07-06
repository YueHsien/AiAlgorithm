package aialgorithm;

import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class ex3 {
	static Random rand = new Random();
	static int ratel = 3; // 跳躍速率

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scn = new Scanner(System.in);
		int run = scn.nextInt();
		int iteration = scn.nextInt();
		int len = scn.nextInt();
		int timerun = 0;
		while (timerun < run) {
			int time = 0;
			String bit[] =select(len);
			String v[] = new String[len];
			int f1 = calfitness(len, bit);
			int initial = f1;
			String[] best = new String[len];
			//System.out.println("此次執行的隨機個體" + "有" + f1 + "個1");
			//System.out.println(Arrays.toString(bit));
			while (time < iteration) {
				v = neibor(len, bit);
				int f2 = calfitness(v.length, v);
				if (f2 > f1) { 
					bit = v;
					best = v;
					f1 = f2;
				}
				time++;
			}
			timerun++;
			iteration+=20;
			System.out.print(iteration+" ");
			System.out.print(f1);
			System.out.println();
		}
	}

	public static String[] select(int n) {
		String[] bit = new String[n];
		for (int i = 0; i < n; i++) {
			int temp = rand.nextInt(2); // 這樣是0-1
			bit[i] = Integer.toString(temp);
		}
		return bit;
	}

	public static int calfitness(int len, String bit[]) { // 計算有多少個1
		int one = 0;
		for (int i = 0; i < len; i++) {
			if (bit[i].equals("1")) {
				one++;
			}
		}
		return one;
	}

	public static String[] neibor(int len, String bit[]) {
		int point=rand.nextInt(len);
		String v[] = Arrays.copyOf(bit, bit.length);
		if(v[point].equals("0")){
			v[point]="1";
		}
		else if(v[point].equals("1")){
			v[point]="0";
		}
		return v;
	}
}
