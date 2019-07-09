import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class ex1 {
	static int n = 5;
	static Random rand = new Random();
	static String bit[][] = select(n);// ���H���D��V����
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scn = new Scanner(System.in);
		System.out.println("��l�H���s��");
		for(int i=0;i<n;i++){
			System.out.println(Arrays.toString(bit[i]));
		}
		System.out.println("��l��=");
		System.out.println(Arrays.toString(calfitness(n)));
		System.out.println("�p��y�{");
		for(int i=0;i<500;i++){
			/*System.out.println("��"+i+"�N");
			for(int j=0;j<n;j++){
				System.out.println(Arrays.toString(bit[j]));
			}*/
			int index[] = calfitness(n); // �p��A�X��
			System.out.println("�A�X��"+Arrays.toString(index));
			int first = findmax(index);
			int second = findsecmax(n, first, index);
			int point = 2;
			String[] child1 = crossover1(first, second, point);// ����l�N1
			String[] child2 = crossover2(first, second, point);// ����l�N1
			System.out.println("�l�N1"+Arrays.toString(child1));
			System.out.println("�l�N2"+Arrays.toString(child2));
			mutation(child1, child2);
			replace(index,child1,child2);
			System.out.println();
		}
		System.out.println("�g�L��]�t��k�᪺��");
		for(int i=0;i<n;i++){
			System.out.println(Arrays.toString(bit[0]));
		}
		int finac[]=calfitness(n);
		System.out.println("�̨θ�="+Arrays.toString(calfitness(n)));
	}

	public static int findmax(int index[]) { // �^�ǳ̤j��index
		int max = index[0];
		int index2 = 0;
		for (int i = 0; i < n; i++) {
			if (index[i] > max) {
				max = index[i];
				index2 = i;
			}
		}
		return index2;
	}

	public static int findsecmax(int n, int first, int indexforsec[]) { // �������̤j����
		int temp[] = Arrays.copyOf(indexforsec, indexforsec.length);
		temp[first] = temp[temp.length - 1];
		temp = Arrays.copyOf(temp, temp.length - 1);
		int max = temp[0];
		int index2 = 0;
		for (int i = 0; i < n - 1; i++) {
			if (temp[i] > max) {
				max = temp[i];
				index2 = i;
			}
		}
		return index2;
	}

	public static String[][] select(int n) {
		String[][] bit = new String[n][100];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 100; j++) {
				int temp = rand.nextInt(2); // �o�ˬO0-1
					float hit = rand.nextFloat();
					bit[i][j]=Integer.toString(temp);
			}
		}
		return bit;
	}

	public static int[] calfitness(int n) { // �p�⦳�h�֭�1
		int index[] = new int[n];
		for (int i = 0; i < n; i++) {
			int one = 0;
			for (int j = 0; j < 100; j++) {
				if (bit[i][j].equals("1")) {
					one++;
				}
			}
			index[i] = one;
		}
		return index;
	}

	public static String[] crossover1(int first, int second, int point) { // ����t�@�Ӥl�N
		String parentf[] = Arrays.copyOf(bit[first], bit[first].length);
		String parents[] = Arrays.copyOf(bit[second], bit[second].length);
		for (int i = 0; i < point; i++) {
			parentf[i] = parents[i];
		}
		return parentf;
	}

	public static String[] crossover2(int first, int second, int point) { // ����t�@�Ӥl�N
		String parentf[] = Arrays.copyOf(bit[first], bit[first].length);
		String parents[] = Arrays.copyOf(bit[second], bit[second].length);
		for (int i = 0; i < point; i++) {
			parents[i] = parentf[i];
		}
		return parents;
	}

	public static void mutation(String child1[], String child2[]) {
		float rate =0.1f;// ���ܲv
		float hit = rand.nextFloat();
		if (hit < rate) {
			int point = rand.nextInt(100);
			if (child1[point].equals("1")) {
				child1[point] = Integer.toString(0);
			} else {
				child1[point] = Integer.toString(1);
			}
		}
		hit = rand.nextFloat();
		if (hit < rate) {
			int point = rand.nextInt(100);
			if (child2[point].equals("1")) {
				child2[point] = Integer.toString(0);
			} else {
				child2[point] = Integer.toString(1);
			}
		}
	}

	public static void replace(int index[],String child1[],String child2[]) { // �N��l�N���N���餤�̮t�����
		int fmin1 = index[0];
		int fmin2 = index[1];
		int fmin1index=0;
		int fmin2index=0;
		for (int i = 0; i < n; i++) {
			if (index[i] < fmin1) {
				fmin1 = index[i];
				fmin1index=i;
			}
		}
		for (int i = 0; i < n; i++) {
			if (index[i] < fmin2 && index[i]!=fmin1) {
				fmin2 = index[i];
				fmin2index=i;
			}
		}
		bit[fmin1index]=child1;
		bit[fmin2index]=child2;
		/*System.out.println("�l�N");
		System.out.println(Arrays.toString(child1));
		System.out.println(Arrays.toString(child2));
		System.out.println("���N");
		System.out.println(Arrays.toString(bit[fmin1index]));
		System.out.println(Arrays.toString(bit[fmin2index]));
		System.out.println("������");
		System.out.println(Arrays.toString(bit[fmin1index]));
		System.out.println(Arrays.toString(bit[fmin2index]));*/
	}
}
