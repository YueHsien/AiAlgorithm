package aialgorithm;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
public class knapsack01ver {
	static Random rand = new Random();

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		// 背包問題定義:在有限的重量內，拿出價值最高的東西。
		Scanner scn = new Scanner(System.in);
		System.out.println("選擇檔案(輸入1-8)");
		int n=scn.nextInt(); //file index
		System.out.println("run次數");
		int run = scn.nextInt();
		System.out.println("迭代次數");
		int iteration = scn.nextInt();
		int capacity = findcapacity(n,'c');
		int timerun = 0;
		//int weights[] = {23,31,29,44,53,38,63,85,89,82};
		//int profits[] = {92,57,49,68,60,43,67,84,87,72};
		while (timerun < run) {
			int jumpselect=0;
			int weights[] =handletxt(n,'w');
			int profits[] =handletxt(n,'p');
			int optselect[]=handletxt(n,'s');
			int bit[] = select(weights.length, weights, capacity);
			int f1 = calfitness(bit, weights, profits);
			int time = 0;
			//System.out.println("初始結果");
			//System.out.println(Arrays.toString(bit));
			while (time < iteration) {
				int v[] = neibor(bit.length, bit); // 0變1,1變0
				int f2 = calfitness(v, weights, profits);
				int weightv = calweights(v, weights, profits);
				if (f2 > f1 && weightv <= capacity) {
					f1 = f2;
					bit = v;
					jumpselect=0;
				}else{
					jumpselect++;
				}
				if(jumpselect>10){ 
					v = select(weights.length, weights, capacity);
					f2 = calfitness(v, weights, profits);
					weightv = calweights(v, weights, profits);
					if (f2 > f1 && weightv <= capacity) {
						f1 = f2;
						bit = v;
					}
					jumpselect=0;
				}
				time++;
			}
			iteration+=10;
			//System.out.println(iteration+" ");
			System.out.println("該次最好結果");
			System.out.println(Arrays.toString(bit));
			System.out.println("該次最好價值");
			System.out.println(f1);
			System.out.println("實際最好結果");
			System.out.println(Arrays.toString(optselect));
			System.out.println("實際最好價值");
			System.out.println(calfitness(optselect,weights, profits));
			System.out.println();
			//隨機爬山演算法
			timerun++;
		}
	}
	public static int findcapacity(int fileindex,char cpsw) throws NumberFormatException, IOException { // 計算有多少價值
		String filename="knapsackdatasets/";
		filename+="p0"+fileindex+"_"+cpsw+".txt";
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		int capacity=0;
		while (br.ready()) {
			//System.out.println(br.readLine());
			String test="";
			test+=br.readLine();
			test=test.replace(" ","");
			int ss=Integer.parseInt(test);
			capacity=ss;
		}
		return capacity;
	}
	public static int[] select(int n, int[] weights, int capacity) {
		int bit[] = new int[n];
		while (true) {
			int weight = 0;
			for (int i = 0; i < n; i++) {
				int temp = rand.nextInt(2); // 這樣是0-1
				bit[i] = temp;
				if (bit[i] == 1) {
					weight += weights[i];
				}
			}
			if (weight < capacity) {
				break;
			}
		}
		return bit;
	}

	public static int calfitness(int[] bit, int[] weights, int[] profits) { // 計算有多少價值
		int f = 0;
		for (int i = 0; i < bit.length; i++) {
			if (bit[i] == 1) {
				f += profits[i];
			}
		}
		return f;
	}

	public static int[] neibor(int len, int bit[]) {
		int point = rand.nextInt(len);
		int v[] = Arrays.copyOf(bit, bit.length);
		if (v[point] == 1) {
			v[point] = 0;
		} else if (v[point] == 0) {
			v[point] = 1;
		}
		return v;
	}

	public static int calweights(int[] bit, int[] weights, int[] profits) {
		int weigtv = 0;
		for (int i = 0; i < bit.length; i++) {
			if (bit[i] == 1) {
				weigtv += weights[i];
			}
		}
		return weigtv;
	}
	public static int[] handletxt(int fileindex,char cpsw) throws NumberFormatException, IOException { //cpsw
		ArrayList<Integer> array = new ArrayList<Integer>();
		String filename="knapsackdatasets/";
		filename+="p0"+fileindex+"_"+cpsw+".txt";
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			//System.out.println(br.readLine());
			String test="";
			test+=br.readLine();
			test=test.replace(" ","");
			int ss=Integer.parseInt(test);
			array.add(ss);
		}
		int v[]=new int[array.size()];
		for(int i=0;i<array.size();i++){
			v[i]=array.get(i);
		}
		fr.close();
		return v;
	}
}
