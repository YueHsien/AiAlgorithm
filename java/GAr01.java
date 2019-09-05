package aialgorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collections;
import java.util.List;

public class GAr01 {
	private int population =16;
	private int run;
	private int iteratin;
	private int length;
	private int timerun = 0;
	private int time = 0;
	private int bit[][];
	private ArrayList<Integer> pool;
	private int child[][];
	private int total=0;
	float fitnessp[];
	private int[] weights;
	private int[] profits;
	private int[] optselect;
	private int capacity;
	private int file;

	GAr01(int file, int run, int iteration) throws NumberFormatException, IOException {
		this.run=run;
		this.iteratin=iteration;
		this.file=file;
		this.weights=handletxt(file, 'w');
		this.profits=handletxt(file, 'p');
		this.optselect = handletxt(file, 's');
		this.capacity=findcapacity(file, 'c');
		this.length=weights.length;
	}

	public void knapsack() {
		while (timerun < run) {
			init();
			while (time < iteratin) {
				fitnessp = calfitness(bit);
				wheel();
				crossover();
				mutation();
				replace();
				renewtime();
				
			}
			renewtimerun();
		}
		System.out.println("羆キА");
		System.out.println(total/run);
	}

	public void init() {
		Random rand = new Random();
		time=0;
		int[][] s=new int[population][length];
		bit = new int[population][length];
		child = new int[population][length];
		pool = new ArrayList();
		fitnessp = new float[bit.length];
			for (int i = 0; i < population; i++) {
				while(true){
					int weight = 0;
					for (int j = 0; j < length; j++) {
						int temp = rand.nextInt(2); // 硂妓琌0-1
						s[i][j] = temp;
						if(s[i][j]==1){
							weight+=weights[j];
						}
					}
					if (weight <= capacity) {
						bit[i]=s[i];
						break;
					}
				}
				//System.out.println(Arrays.toString(bit[i]));
			}
	}

	public void renewtime() {
		time++;
	}

	public void renewtimerun() {
		timerun++;
		//System.out.println("舼" + iteratin + "Ω");
		/*for(int i=0;i<population;i++){
			System.out.println(Arrays.toString(bit[i]));
		}
		System.out.println();*/
		float[] best = calfitness(bit);
		//System.out.println("程");
		//System.out.println(best[0]);
		total+=best[0];
		//iteratin += 20;
	}

	public void crossover() {
		Random rand = new Random();
		int childindex = 0;
		while (!pool.isEmpty()) {
			int index = rand.nextInt(pool.size());
			int point = pool.get(index);
			int[] parentf = Arrays.copyOf(bit[point], bit[point].length); //
			pool.remove(index);
			index = rand.nextInt(pool.size());
			point = pool.get(index);
			int[] parents = Arrays.copyOf(bit[point], bit[point].length);
			pool.remove(index);
			while(true){
				int crosspoint = rand.nextInt(length);
				for (int i = crosspoint; i < length; i++) {
					int temp = parentf[i];
					parentf[i] = parents[i];
					parents[i] = temp;
				}
				int w1=0;
				int w2=0;
				for(int i=0;i<parentf.length;i++){
					if(parentf[i]==1){
						w1+=weights[i];
					}
				}
				for(int i=0;i<parents.length;i++){
					if(parents[i]==1){
						w2+=weights[i];
					}
				}
				if(w1<=capacity && w2<=capacity){
					child[childindex] = parentf;
					child[childindex + 1] = parents;
					childindex += 2;
					break;
				}
			}
		}
	}
	public void replace2(){
		for(int i=0;i<bit.length;i++){
			bit[i]=child[i];
		}
	}
	public void replace() { // 盢瞷程瞷程畉ダ盿
		float[] fitnessc = new float[child.length];
		fitnessc = calfitness(child);
		float max = 0;
		int childindex = 0;
		for (int i = 0; i < fitnessc.length; i++) {
			if (fitnessc[i] > max) {
				max = fitnessc[i];
				childindex = i;
			}
		}
		float[] fitnessp = new float[child.length];
		fitnessp = calfitness(bit);
		float min = fitnessp[0];
		int bitindex = 0;
		for (int i = 1; i < fitnessp.length; i++) {
			if (fitnessp[i] < min) {
				min = fitnessp[i];
				bitindex = i;
			}
		}
		bit[bitindex] = child[childindex];
	}

	public void mutation() {
		Random rand = new Random();
		for (int i = 0; i < child.length; i++) {
			float rate = 0.3f;
			float hit = rand.nextFloat();
			if (hit < rate) {
				while(true){
					int w=0;
					int point = rand.nextInt(length);
					if (child[i][point]==1) {
						child[i][point] = 0;
						for(int j=0;j<length;j++){
							if(child[i][j]==1){
								w+=weights[j];
							}
						}
						if(w<=capacity){
							break;
						}else{
							child[i][point]=1;
						}
					} else if (child[i][point]==0) {
						child[i][point] = 1;
						for(int j=0;j<length;j++){
							if(child[i][j]==1){
								w+=weights[j];
							}
						}
						if(w<=capacity){
							break;
						}else{
							child[i][point]=0;
						}
					}
				}
			}
		}
	}

	public void wheel() {
		Random rand = new Random();
		float sum = 0;
		float temp = 0;
		for (int i = 0; i < population; i++) {
			sum += fitnessp[i];
		}
		float[] probability = new float[population];
		for (int i = 0; i < probability.length; i++) {
			temp += fitnessp[i] / sum;
			float aa = (float) (Math.round(temp / .01) * .01);
			probability[i] = aa;
		}
		// System.out.println(Arrays.toString(probability));
		for (int i = 0; i < population; i++) {
			float hit = rand.nextFloat();
			for (int j = 0; j < bit.length; j++) {
				if (hit < probability[j]) {
					pool.add(j);
					break;
				}
			}
		}
	}

	public float[] calfitness(int[][] array) { // 璸衡Τぶ基
		float[] fitness = new float[population];
		//System.out.println(array.length);
		for (int i = 0; i < population; i++) {
			int f = 0;
			for(int j=0;j<array[0].length;j++){
				if(array[i][j]==1){
					f += profits[j];
				}
			}
			fitness[i]=f;
		}
		//System.out.println(Arrays.toString(fitness));
		return fitness;
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
	public static int calweights(int[] bit, int[] weights, int[] profits) {
		int weigtv = 0;
		for (int i = 0; i < bit.length; i++) {
			if (bit[i] == 1) {
				weigtv += weights[i];
			}
		}
		return weigtv;
	}
	public int findcapacity(int fileindex, char cpsw) throws NumberFormatException, IOException { // 璸衡Τぶ基
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
}
