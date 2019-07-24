package aialgorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SA01knapsack {
	static Random rand = new Random();

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		// �I�]���D�w�q:�b���������q���A���X���ȳ̰����F��C
		Scanner scn = new Scanner(System.in);
		System.out.println("����ɮ�(��J1-8)");
		int n = scn.nextInt(); // file index
		System.out.println("run����");
		int run = scn.nextInt();
		System.out.println("���N����");
		int iteration = scn.nextInt();
		int capacity = findcapacity(n, 'c');
		int timerun = 0;
		while (timerun < run) {
			float temparature =1000;
			int weights[] = handletxt(n, 'w');
			int profits[] = handletxt(n, 'p');
			int optselect[] = handletxt(n, 's');
			int s[] = select(weights.length, weights, capacity);
			int f1 = Evaluation(s, weights, profits);
			int time = 0;
			//System.out.println("��l���G");
			//System.out.println(Arrays.toString(s));
			while (time<iteration) {
				int v[] = neighborselect(s.length, s); // 0��1,1��0
				int f2 = Evaluation(v, weights, profits);
				int weightv = calweights(v, weights, profits);
					if (weightv <= capacity) {
						if(f2>f1){
							f1=f2;
							s=v;
						}else if(f2<f1){
							float pa = (float) Math.exp((f2 - f1) / temparature);
							float r = rand.nextFloat();
							if(r<pa){
								s=v;
								//f1=f2;
							}
							//temparature = (float) (0.95 * temparature);
						}
					}
				time++;
			}
			//temparature = (float) (0.95 * temparature);
			//System.out.println("�ū�"+" "+temparature);
			System.out.print(iteration+" ");
			//System.out.println("�Ӧ��̦n���G");
			//System.out.println(Arrays.toString(s));
			//System.out.println("�Ӧ��̦n����");
			System.out.print(f1+" ");
			//System.out.println("��ڳ̦n���G");
			//System.out.println(Arrays.toString(optselect));
			//System.out.println("��ڳ̦n����");
			System.out.println(Evaluation(optselect, weights, profits));
			System.out.println();
			iteration +=20;
			timerun++;
		}
	}

	public static int findcapacity(int fileindex, char cpsw) throws NumberFormatException, IOException { // �p�⦳�h�ֻ���
		String filename = "knapsackdatasets/";
		filename += "p0" + fileindex + "_" + cpsw + ".txt";
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		int capacity = 0;
		while (br.ready()) {
			// System.out.println(br.readLine());
			String test = "";
			test += br.readLine();
			test = test.replace(" ", "");
			int ss = Integer.parseInt(test);
			capacity = ss;
		}
		return capacity;
	}

	public static int[] select(int n, int[] weights, int capacity) {
		int bit[] = new int[n];
		while (true) {
			int weight = 0;
			for (int i = 0; i < n; i++) {
				int temp = rand.nextInt(2); // �o�ˬO0-1
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

	public static int Evaluation(int[] bit, int[] weights, int[] profits) { // �p�⦳�h�ֻ���
		int f = 0;
		for (int i = 0; i < bit.length; i++) {
			if (bit[i] == 1) {
				f += profits[i];
			}
		}
		return f;
	}

	public static int[] neighborselect(int len, int bit[]) {
		int point = rand.nextInt(len);
		int v[] = Arrays.copyOf(bit, bit.length);
		if (v[point] == 1) {
			v[point] = 0;
		} else if (v[point] == 0) {
			v[point] = 1;
		}
		/*int point2 = rand.nextInt(len);
		if (v[point2] == 1) {
			v[point2] = 0;
		} else if (v[point2] == 0) {
			v[point2] = 1;
		}*/
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

	public static int[] handletxt(int fileindex, char cpsw) throws NumberFormatException, IOException { // cpsw
		ArrayList<Integer> array = new ArrayList<Integer>();
		String filename = "knapsackdatasets/";
		filename += "p0" + fileindex + "_" + cpsw + ".txt";
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			// System.out.println(br.readLine());
			String test = "";
			test += br.readLine();
			test = test.replace(" ", "");
			int ss = Integer.parseInt(test);
			array.add(ss);
		}
		int v[] = new int[array.size()];
		for (int i = 0; i < array.size(); i++) {
			v[i] = array.get(i);
		}
		fr.close();
		return v;
	}
}
