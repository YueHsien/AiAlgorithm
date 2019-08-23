package aialgorithm;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Antv {
	private int run;
	private int iteratin;
	private int length;
	private int file;
	private int best = 0;
	private int timerun = 0;
	private int time = 0;
	private float total = 0;
	private double[][] tsp;
	private double[][] degree;
	private int[][] ant; // 儲存每條螞蟻的路徑
	private int start = 1;// 起始點
	private int sn = 10;// 螞蟻數量
	private int ad = 1;
	private int bd = 11;
	private float p0 = 0.95f;// 衰退速
	int set[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
	float q0 = 0.9f;// acs轉換機率

	Antv(int run, int iteration, int length) {
		this.run = run;
		this.iteratin = iteration;
		this.length = length;
		this.tsp = new double[length][length];
		this.degree = new double[length][length];
		// this.temparature = temparature;
	}

	public void onemaxproblem() throws IOException {
		//tspdataini();
		while (timerun < 30) {
			tspdataini();
			int mint[] = new int[51];
			Scanner scn = new Scanner(System.in);
			int mint2[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
					26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
					51 };
			float minl = callength(mint2);
			int temp[] = new int[51];
			while (time < 500) {
				init();
				selectnode();
				mint = optstep1();
				if (callength(mint) < minl) {
					mint2 = mint;
					minl = callength(mint);
				}
				addconcentration(mint);
				time++;
			}
			System.out.println("最佳路徑=");
			System.out.println(Arrays.toString(mint2));
			System.out.println("最短長度=");
			System.out.println(minl);
			total += minl;
			renew();
		}
		System.out.println("總平均:"+total/30);
		// handploatdata();
	}

	public void addconcentration(int mint[]) {
		float L = callength(mint);
		int v1 = mint[50] - 1;
		int v2 = mint[0] - 1;
		degree[v1][v2] += (1 - p0) * degree[v1][v2] + p0 * (200 / L);
		for (int i = 0; i < mint.length - 1; i++) {
			v1 = mint[i] - 1;
			v2 = mint[i + 1] - 1;
			degree[v1][v2] += (1 - p0) * degree[v1][v2] + p0 * (200 / L);
		}
	}

	public void tspdataini() {
		Scanner scn = new Scanner(System.in);
		tspread2 s = new tspread2();
		tsp = s.tsp3;
		tspread c = new tspread();
		degree = c.degree1;
	}

	public void renew() {
		timerun++;
		time=0;
	}

	public void init() {
		Random rand=new Random();
		ant = new int[sn][length];
		for (int i = 0; i < sn; i++) {
			int n=rand.nextInt(50)+1;
			ant[i][0] = n; // 設定好每隻螞蟻起始點
		}
	}

	public int[] optstep1() {
		double l = 0;
		int temp[][] = new int[sn][51];
		for (int i = 0; i < ant.length; i++) {
			l = callength(ant[i]);
			int at[] = optstep2(ant[i], l);
			temp[i] = at;
		}
		double minl = callength(temp[0]);
		int[] mint = temp[0];
		for (int i = 1; i < temp.length; i++) {
			float tempminl = callength(temp[i]);
			if (tempminl < minl) {
				minl = tempminl;
				mint = temp[i];
			}
		}
		return mint;
	}

	public int[] optstep2(int array[], double l) {
		int select[][] = handcomb(array);
		double minl = l;
		int[] mint = Arrays.copyOf(array, array.length);
		for (int i = 0; i < select.length; i++) {
				int index1 = 0;
				int index2 = 0;
				int t[] = Arrays.copyOf(array, array.length);
				for (int j = 0; j < array.length; j++) {
					if (t[j] == select[i][0]) {
						index1 = j;
					} else if (t[j] == select[i][1]) {
						index2 = j;
					}
					if (index1 != 0 && index2 != 0) {
						int temp = t[index1];
						t[index1] = t[index2];
						t[index2] = temp;
						float tl = callength(t);
						if (tl < minl) {
							// System.out.println(select[i][0]);
							// System.out.println(select[i][1]);
							minl = tl;
							mint = t;
						}
						break;
					}
				}
				// System.out.println("!");
		}
		// System.out.println("!");
		return mint;
	}

	public int[][] handcomb(int array[]) {
		int from[] = array;
		int to[] = new int[from.length];
		String str = "";
		int select[][] = new int[1275][2];
		int first = 0;
		int second = 0;
		int count = 0;
		String ss = comb(from, to, 2, from.length, 2);
		for (int i = 0; i < ss.length(); i++) {
			if (count == 1275) {
				break;
			}
			if (ss.charAt(i) == ' ') {
				int x = Integer.parseInt(str);
				select[first][second] = x;
				if (second == 1) {
					first++;
					second = -1;
					count++;
				}
				second++;
				str = "";
				continue;
			} else {
				str += ss.charAt(i);
			}
		}
		return select;

	}

	public float callength(int array[]) {
		float distance = (float) tsp[array[array.length - 1] - 1][array[0] - 1];
		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1) {
				break;
			} else {
				distance += tsp[array[i] - 1][array[i + 1] - 1];
			}
		}
		return distance;
	}

	public void selectnode() {
		int k = 0;
		while (true) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			int tempindex = 0;
			for (int i = 0; i < set.length; i++) {
				boolean isExist = false;
				for (int j = 0; j < ant[k].length; j++) {
					if (set[i] == ant[k][j]) { // 代表拜訪過了
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					temp.add(set[i]);
				}
			}
		  //System.out.println("!");
			while (!temp.isEmpty()) {
				double temp2[][] = new double[temp.size()][4]; // 從左依序是節點，每個點的機率、每個點相加的機率、max用的分子
				DecimalFormat df = new DecimalFormat("##.00");
				for (int i = 0; i < temp.size(); i++) {
					temp2[i][0] = temp.get(i);
				}
				//System.out.println("!");
				double sump = 0; // 轉換機率的分母總和
				double sump1 = 0; // 加總所有機率值
				int index = 0;
				for (int i = 1; i < length; i++) {
					if (ant[k][i] != 0) {
						index++;
					}
				}
				//System.out.println("!");
				for (int i = 0; i < temp2.length; i++) {
					int v1 = ant[k][index];
					int v2 = (int) temp2[i][0];
					sump += Math.pow(degree[v1 - 1][v2 - 1], ad) * Math.pow((1 / tsp[v1 - 1][v2 - 1]), bd);
				}
				// System.out.println("!");
				for (int i = 0; i < temp2.length; i++) {
					int v1 = ant[k][index];
					int v2 = (int) temp2[i][0];
					double vary = (Math.pow(degree[v1 - 1][v2 - 1], ad) * Math.pow((1 / tsp[v1 - 1][v2 - 1]), bd));
					temp2[i][3] = vary;
					vary = vary / sump;
					temp2[i][1] = Float.parseFloat(df.format(vary));
					sump1 += vary;
					temp2[i][2] = sump1;
				}
				 //System.out.println("!");
				// System.out.println("!");
				Random rand = new Random();
				float q = rand.nextFloat();

				if (q <= q0) {
					long point = handleacs(temp2, k, index);
					for (int i = 0; i < temp.size(); i++) {
						if (temp.get(i) == point) {
							temp.remove(i);
							break;
						}
					}
				} else {
					int point = handlej(temp2, k, index);
					for (int i = 0; i < temp.size(); i++) {
						if (temp.get(i) == point) {
							temp.remove(i);
							break;
						}
					}
				}
			}
			//System.out.println(Arrays.toString(ant[k]));
			k += 1;
			if (k == ant.length) {
				break;
			}
		}
	}

	public long handleacs(double[][] temp2, int k, int index2) {
		double max = temp2[0][3];
		long index = Math.round(temp2[0][0]);
		long returnpoint = Math.round(temp2[0][0]);
		for (int i = 1; i < temp2.length; i++) {
			if (temp2[i][3] > max) {
				max = temp2[i][3];
				index = Math.round(temp2[i][0]);
				returnpoint = Math.round(temp2[i][0]);
			}
		}
		index2++;
		ant[k][index2] = (int) index;
		partrenew(ant[k]);
		return returnpoint;
	}

	public int handlej(double[][] temp2, int k, int index2) {
		Random rand = new Random();
		float dice = rand.nextFloat();
		int returnpoint = 0;
		for (int i = 0; i < temp2.length; i++) {
			if (dice < temp2[i][2]) {
				index2++;
				ant[k][index2] = (int) temp2[i][0];
				returnpoint = (int) Math.round(temp2[i][0]);
				partrenew(ant[k]);
				break;
			}
		}
		return returnpoint;
	}

	public void partrenew(int[] array) {
		int len = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != 0) {
				len++;
			} else {
				break;
			}
		}
		int v1 = array[len - 2] - 1;
		int v2 = array[len - 1] - 1;
		degree[v1][v2] = ((1 - p0) * degree[v1][v2]) + (p0 * 0.0001);
		if (len == 51) {
			degree[array[51 - 1] - 1][array[0] - 1] = ((1 - p0) * degree[array[51 - 1] - 1][array[0] - 1])
					+ (p0 * 0.0001);
		}
	}

	public static String comb(int[] from, int[] to, int len, int m, int n) {
		String result = "";
		if (n == 0) {
			for (int i = 0; i < len; i++) {
				result += to[i] + " ";
			}
			// result += "\n";
		} else {
			to[n - 1] = from[m - 1];

			if (m > n - 1) {
				result = comb(from, to, len, m - 1, n - 1);
			}
			if (m > n) {
				result = comb(from, to, len, m - 1, n) + result;
			}
		}
		return result;
	}
}
