package aialgorithm;

import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class SA {
	static Random rand = new Random();
	static int ratel = 3; // 跳躍速率

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scn = new Scanner(System.in);
		int run = scn.nextInt();
		int iteration = scn.nextInt();
		int len = scn.nextInt();
		int timerun = 0;
		//System.out.println("初始解");
		//System.out.println(Arrays.toString(s));
		while (timerun < run) {
			float temparature = 50;
			String[] s = select(len);
			int f1 = Evaluation(len, s);
			int time = 0;
			while (time < iteration) {
				String[] v = Transition(len, s);
				int f2 = Evaluation(len, v);
				float r = rand.nextFloat();
				float pa = (float) Math.exp((f2 - f1) / temparature);
				if (r <= pa) {
					s = v;
					f1 = f2;
				}
				time++;
				temparature = (float) (0.95 * temparature);
			}
			timerun++;
			System.out.print(iteration+" ");
			System.out.println(f1);
			iteration+=20;
			//System.out.println(Arrays.toString(s));
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

	public static int Evaluation(int len, String bit[]) { // 計算有多少個1
		int one = 0;
		for (int i = 0; i < len; i++) {
			if (bit[i].equals("1")) {
				one++;
			}
		}
		return one;
	}

	public static String[] Transition(int len, String bit[]) {
		int point = rand.nextInt(len);
		String v[] = Arrays.copyOf(bit, bit.length);
		if (v[point].equals("0")) {
			v[point] = "1";
		} else if (v[point].equals("1")) {
			v[point] = "0";
		}
		return v;
	}

}
