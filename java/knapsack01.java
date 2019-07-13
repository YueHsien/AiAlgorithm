package aialgorithm;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
public class knapsack01 {
	static Random rand = new Random();
	private int f1;
	private int[] array;
	knapsack01(int f1){
		this.f1=f1;
	}
	knapsack01(int array[]){
		this.array=array;
	}
	static int n=2;	//更改該行改變檔案
	static knapsack01 weights=new knapsack01(handletxt(n,'w'));
	static knapsack01 profits=new knapsack01(handletxt(n,'p'));
	static knapsack01 optselect=new knapsack01(handletxt(n,'s'));
	static knapsack01 capacity=new knapsack01(findcapacity(n,'c'));
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		// 背包問題定義:在有限的重量內，拿出價值最高的東西。
		Scanner scn = new Scanner(System.in);
		System.out.println("run次數");
		int run = scn.nextInt();
		System.out.println("迭代次數");
		int iteration = scn.nextInt();
		int timerun = 0;
		while (timerun < run) {
			//knapsack01 weights=new knapsack01(handletxt(n,'w'));
			int s[] = Initialization(weights.array.length, weights.array, capacity.f1); //I
			knapsack01 f1=new knapsack01(Evaluation(s,profits.array)); //E
			int time = 0;
			System.out.println("初始結果");
			System.out.println(Arrays.toString(s));
			while (time < iteration) {
				int v[] = Transition(s); //T
				int f2 = Evaluation(v,profits.array);//E
				s=Determination(s,v,f1,f2);//D
				time++;
			}
			System.out.println("該次最好結果");
			System.out.println(Arrays.toString(s));
			System.out.println("該次最好價值");
			System.out.println(f1.f1);
			System.out.println("實際最好結果");
			System.out.println(Arrays.toString(optselect.array));
			System.out.println("實際最好價值");
			System.out.println(Evaluation(optselect.array,profits.array));
			System.out.println();
			timerun++;
		}
	}
	public static int[] Determination(int s[],int v[],knapsack01 f1,int f2){
		int weightv = 0;
		for (int i = 0; i < v.length; i++) {
			if (v[i] == 1) {
				weightv += weights.array[i];
			}
		}
		if (f2 > f1.f1 && weightv <= capacity.f1) {
			f1.f1=f2;
			s = v;
		}
		return s;
	}
	public static int findcapacity(int fileindex,char cpsw){ // 計算有多少價值
		String filename="knapsackdatasets/";
		filename+="p0"+fileindex+"_"+cpsw+".txt";
		FileReader fr = null;
		try {
			fr = new FileReader(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		int capacity=0;
		try {
			while (br.ready()) {
				//System.out.println(br.readLine());
				String test="";
				test+=br.readLine();
				test=test.replace(" ","");
				int ss=Integer.parseInt(test);
				capacity=ss;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return capacity;
	}
	public static int[] Initialization(int n, int[] weights, int capacity) {
		int s[] = new int[n];
		while (true) {
			int weight = 0;
			for (int i = 0; i < n; i++) {
				int temp = rand.nextInt(2); // 這樣是0-1
				s[i] = temp;
				if (s[i] == 1) {
					weight += weights[i];
				}
			}
			if (weight < capacity) {
				break;
			}
		}
		return s;
	}

	public static int Evaluation(int[] s,int[] profits) { // 計算有多少價值
		int f = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] == 1) {
				f += profits[i];
			}
		}
		return f;
	}

	public static int[] Transition(int s[]) {
		int point = rand.nextInt(s.length);
		int v[] = Arrays.copyOf(s, s.length);
		if (v[point] == 1) {
			v[point] = 0;
		} else if (v[point] == 0) {
			v[point] = 1;
		}
		return v;
	}

	public static int calweights(int[] s, int[] weights, int[] profits) {
		int weigtv = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] == 1) {
				weigtv += weights[i];
			}
		}
		return weigtv;
	}
	public static int[] handletxt(int fileindex,char cpsw) { //cpsw
		ArrayList<Integer> array = new ArrayList<Integer>();
		String filename="knapsackdatasets/";
		filename+="p0"+fileindex+"_"+cpsw+".txt";
		FileReader fr = null;
		try {
			fr = new FileReader(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		try {
			while (br.ready()) {
				//System.out.println(br.readLine());
				String test="";
				test+=br.readLine();
				test=test.replace(" ","");
				int ss=Integer.parseInt(test);
				array.add(ss);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int v[]=new int[array.size()];
		for(int i=0;i<array.size();i++){
			v[i]=array.get(i);
		}
		try {
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
	}
}
