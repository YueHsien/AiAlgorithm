
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class implement {
	static Random rand = new Random();
	static int ratel = 3; // 跳躍速率

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scn = new Scanner(System.in);
		String algo=scn.next();
		if(algo.equals("es")){
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date date = new Date();
			String record="";
			String strDate = sdFormat.format(date);
			System.out.println(strDate);
			long a = (long) Math.pow(2, 100);
			String c = "";
			String best = Long.toBinaryString(0);
			long f1 = calfitness2(best);
			long i = 1;
			while (i < a) {
				c = Long.toBinaryString(i);
				int f2 = calfitness2(c);
				if (f2 > f1) {
					f1 = f2;
					best = c;
					Date date2 = new Date();
					String strDate2 = sdFormat.format(date2);
					System.out.println(strDate2);
					System.out.println(i);
					System.out.println(best);
					System.out.println(f1);
					record+="在時間"+strDate2;
					record+="已經跑到第"+i+"數並且已經計算出"+f1+"個1\n";
					try {
						FileWriter fw = new FileWriter("record.txt");
						fw.write(record);
						fw.flush();
						fw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				i++;
			}
		}else if(algo.equals("hc")){
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
				System.out.println("此次執行的隨機個體" + "有" + f1 + "個1");
				System.out.println(Arrays.toString(bit));
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
				//iteration+=20;
				System.out.println("最好結果");
				System.out.println(Arrays.toString(best));
				System.out.print(iteration+" ");
				System.out.print(f1);
				System.out.println();
			}
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
	public static int calfitness2(String temp) { // 計算有多少個1
		int number = 0;
		for (int i = 0; i < temp.length(); i++) {
			char s = temp.charAt(i);
			if (s == '1') {
				number++;
			}
		}
		return number;
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
