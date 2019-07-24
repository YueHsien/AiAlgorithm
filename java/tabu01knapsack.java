package aialgorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class tabu01knapsack {
	static Random rand = new Random();
	private int point;
	private String array;
	tabu01knapsack(int point) {
		this.point = point;
	}
	tabu01knapsack() {
		this.point = point;
	}
	tabu01knapsack(String array){
		this.array=array;
	}
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
		int weights[] = handletxt(n, 'w');
		int profits[] = handletxt(n, 'p');
		int optselect[] = handletxt(n, 's');
		int timerun = 0;
		tabu01knapsack point=new tabu01knapsack();
		while (timerun < run) {
			int s[] = Initialization(weights.length, weights, capacity);
			int f1 = Evaluation(s, weights, profits);
			int time = 0;
			String tabulist[] = new String[2];
			//System.out.println("��l���G");
			//System.out.println(Arrays.toString(s));
			while (time < iteration) {
				int v[] = nontabuselect(s.length,s,tabulist,point);//T-E-D
				int f2 = Evaluation(v, weights, profits);
				int weightv = calweights(v, weights, profits);
					if (weightv <= capacity) {
						if(f2>f1){
							f1=f2; //�s�̨θ�
							tabulist = updatetabu(tabulist, point);
							s=v;
							//System.out.println(Arrays.toString(tabulist));
						}else if(f2<f1){
							tabulist = updatetabu(tabulist, point);
							s=v;
							//System.out.println(Arrays.toString(tabulist));
						}
					}
				time++;
			}
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
	public static String[] updatetabu(String tabulist[], tabu01knapsack point) {
		if (tabulist[tabulist.length - 1] != null) { // �N��tabulist���F
			tabulist[0] = null; // ���ª�����
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

	public static int[] Initialization(int n, int[] weights, int capacity) {
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

	public static int[] nontabuselect(int len, int bit[], String tabulist[], tabu01knapsack point) {
		int[] v = Arrays.copyOf(bit, bit.length);
		while (true) {
			point.point = rand.nextInt(len);
			boolean judge = true;
			//System.out.println(point.point);
			for (int i = 0; i < tabulist.length; i++) {
				if(tabulist[i]==null){
					continue;
				}else if (tabulist[i].equals(Integer.toString(point.point))) {
					judge = false;
					break;
				}
			}
			if (judge == true) {
				if(v[point.point]==0){
					v[point.point]=1;
				}else if(v[point.point]==1){
					v[point.point]=0;
				}
				break;
			}
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
