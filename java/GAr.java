package aialgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collections;
import java.util.List;

public class GAr {
	private int population =4;
	private int run;
	private int iteratin;
	private int length;
	private int timerun = 0;
	private int time = 0;
	private String bit[][];
	private ArrayList<Integer> pool;
	private String child[][];
	private int total=0;
	float fitnessp[];

	GAr(int run, int iteration, int length) {
		this.run = run;
		this.iteratin = iteration;
		this.length = length;
	}

	public void onemaxproblem() {
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
		System.out.println("總平均");
		System.out.println(total/run);
	}

	public void init() {
		Random rand = new Random();
		time=0;
		bit = new String[population][length];
		child = new String[population][length];
		pool = new ArrayList();
		fitnessp = new float[bit.length];
		for (int i = 0; i < population; i++) {
			for (int j = 0; j < length; j++) {
				int temp = rand.nextInt(2); // 這樣是0-1
				bit[i][j] = Integer.toString(temp);
			}
		}
	}

	public void renewtime() {
		time++;
	}

	public void renewtimerun() {
		timerun++;
		//System.out.println("疊代" + iteratin + "次");
		/*for(int i=0;i<population;i++){
			System.out.println(Arrays.toString(bit[i]));
		}*/
		float[] best = calfitness(bit);
		//System.out.println("最好值");
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
			String parentf[] = Arrays.copyOf(bit[point], bit[point].length); //
			pool.remove(index);
			index = rand.nextInt(pool.size());
			point = pool.get(index);
			String parents[] = Arrays.copyOf(bit[point], bit[point].length);
			pool.remove(index);
			int crosspoint = rand.nextInt(length);
			for (int i = crosspoint; i < length; i++) {
				String temp = parentf[i];
				parentf[i] = parents[i];
				parents[i] = temp;
			}
			child[childindex] = parentf;
			child[childindex + 1] = parents;
			childindex += 2;
		}
	}
	public void replace2(){
		for(int i=0;i<bit.length;i++){
			bit[i]=child[i];
		}
	}
	public void replace() { // 將表現最好的子代取代表現最差的母帶代
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
		float rate = 0.1f;
		for (int i = 0; i < child.length; i++) {
			float hit = rand.nextFloat();
			if (hit < rate) {
				int point = rand.nextInt(length);
				if (child[i][point].equals("1")) {
					child[i][point] = "0";
				} else if (child[i][point].equals("0")) {
					child[i][point] = "1";
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

	public float[] calfitness(String array[][]) { // 計算有多少個1
		float[] fitness = new float[population];
		for (int i = 0; i < population; i++) {
			int one = 0;
			for (int j = 0; j < length; j++) {
				if (array[i][j].equals("1")) {
					one++;
				}
			}
			fitness[i] = one;
		}
		return fitness;
	}
}
