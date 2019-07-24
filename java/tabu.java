package aialgorithm;

import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class tabu {
	static Random rand = new Random();
	static int ratel = 3; // 跳躍速率
	private int point;

	tabu(int point) {
		this.point = point;
	}

	tabu() {
		this.point = point;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scn = new Scanner(System.in);
		int run = scn.nextInt();
		int iteration = scn.nextInt();
		int len = scn.nextInt();

		int timerun = 0;
		tabu point = new tabu();
		while (timerun < run) {
			String[] s = select(len);
			int f1 = Evaluation(len, s);
			//System.out.println("初始解");
			//System.out.println(Arrays.toString(s));
			int time = 0;
			String tabulist[] = new String[50];
			while (time < iteration) {
				String[] v = nontabuselect(len, s, tabulist, point);
				int f2 = Evaluation(len, v);
				if (f2 > f1) {
					s = v;
					f1 = f2;
					tabulist = updatetabu(tabulist, point);
				}
				time++;
			}
			timerun++;
			System.out.print(iteration + " ");
			System.out.println(f1);
			iteration +=20;
			//System.out.println(Arrays.toString(s));
		}
	}

	public static String[] updatetabu(String tabulist[], tabu point) {
		if (tabulist[tabulist.length - 1] != null) { // 代表tabulist滿了
			tabulist[0] = null; // 最舊的移除
			for (int i = 1; i < tabulist.length; i++) {
				tabulist[i - 1] = tabulist[i];
			}
			tabulist[tabulist.length - 1] = null;
		}
		for (int i = 0; i < tabulist.length; i++) {
			if (tabulist[i] == null) {
				tabulist[i] = Integer.toString(point.point);
				break;
			}
		}
		return tabulist;
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

	public static String[] nontabuselect(int len, String bit[], String tabulist[], tabu point) {
		String v[] = Arrays.copyOf(bit, bit.length);
		while (true) {
			point.point = rand.nextInt(len);
			boolean judge = true;
			for (int i = 0; i < tabulist.length; i++) {
				if (tabulist[i] == Integer.toString(point.point)) {
					judge = false;
					break;
				}
			}
			if (judge == true) {
				if(v[point.point].equals("0")){
					v[point.point]="1";
				}else if(v[point.point].equals("1")){
					v[point.point]="0";
				}
				break;
			}
		}
		return v;
	}

}
