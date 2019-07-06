package aialgorithm;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;

public class ex3 {
	static Random rand = new Random();
	static int ratel = 3; // 跳躍速率

	public static void main(String[] args) {
		String record="";
		// TODO Auto-generated method stub
		Scanner scn = new Scanner(System.in);
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = new Date();
		String strDate = sdFormat.format(date);
		System.out.println(strDate);
		long a = (long) Math.pow(2, 100);
		String c = "";
		String best = Long.toBinaryString(0);
		long f1 = calfitness(best);
		long i = 1;
		while (i < a) {
			c = Long.toBinaryString(i);
			int f2 = calfitness(c);
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
	}

	public static int calfitness(String temp) { // 計算有多少個1
		int number = 0;
		for (int i = 0; i < temp.length(); i++) {
			char s = temp.charAt(i);
			if (s == '1') {
				number++;
			}
		}
		return number;
	}

}
