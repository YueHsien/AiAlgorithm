import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class ex2 {
	static Random rand = new Random();
	static int ratel = 3; // 跳躍速率

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scn = new Scanner(System.in);
		int run = scn.nextInt();
		int iteration = scn.nextInt();
		int len = scn.nextInt();
		int timerun = 0;
		String limit = "";
		for (int i = 0; i < len; i++) {
			limit += "1";
		}
		int limitint = Integer.parseInt(limit, 2);// 將二進位數字轉成十進位
		int direction = -1;// 決定向下或是下上
		int sum = 0; // 紀錄總共幾次並計算平均
		while (timerun < run) {
			int time = 0;
			int speed = 100; // 速度
			String bit[] =select(len);
			String v[] = new String[len];
			int f1 = calfitness(len, bit);
			int initial = f1;
			String[] best = new String[len];
			System.out.println("此次執行的隨機個體" + "有" + f1 + "個1");
			System.out.println(Arrays.toString(bit));
			while (time < iteration) {
				v = neibor(len, bit, limitint, direction, speed);
				int f2 = calfitness(v.length, v);
				if (f2 > f1) { // 代表這個方向可以繼續找,direction不變
					bit = v;
					best = v;
					f1 = f2;
				} else if (f2 == f1) { // 相等也接受
					bit = v;
					best = v;
					f1 = f2;
				} else {
					if (direction == 1) {
						direction = -1;
					} else if (direction == -1) {
						direction = 1;
					}

				}
				time++;
				speed = (int) (speed * 0.7);
			}
			timerun++;
			System.out.println("此次run(第" + timerun + "次)有" + f1 + "個1");
			sum += f1 - initial;
			System.out.println(Arrays.toString(best));
			System.out.println();
		}
		System.out.println("該次演算法的效能平均為找到" + sum / run + "個1");
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

	public static String[] neibor(int len, String bit[], int limitint, int direction, int speed) {
		String a = ""; // 儲存目前解的二進位
		String v[] = new String[len];
		for (int i = 0; i < bit.length; i++) {
			a += bit[i];
		}
		int b = Integer.parseInt(a, 2); // 二進位轉成十進位
		int dangle1 = b+(direction*speed); // 這裡會影響到震盪範圍。
		// int dangle2=b-1;
		if (dangle1 > limitint) {
			dangle1 = limitint;
		}
		String dangle1bin1 = Integer.toBinaryString(dangle1);
		// String dangle1bin2=Integer.toBinaryString(dangle2);
		String buffer = "";
		// 以下處理字串問題......
		for (int i = dangle1bin1.length() - 1; i >= 0; i--) {
			buffer += dangle1bin1.charAt(i);
		}
		for (int i = buffer.length(); i < len; i++) {
			buffer += 0;
		}
		String buffer2 = "";
		for (int i = buffer.length() - 1; i >= 0; i--) {
			buffer2 += buffer.charAt(i);
		}
		for (int i = 0; i < len; i++) {
			String ss = String.valueOf(buffer2.charAt(i));
			v[i] = ss;
		}
		return v;
	}
}
