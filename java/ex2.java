import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class ex2 {
	static Random rand = new Random();
	static int ratel = 3; // ���D�t�v

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
		int limitint = Integer.parseInt(limit, 2);// �N�G�i��Ʀr�ন�Q�i��
		int direction = -1;// �M�w�V�U�άO�U�W
		int sum = 0; // �����`�@�X���íp�⥭��
		while (timerun < run) {
			int time = 0;
			int speed = 100; // �t��
			String bit[] =select(len);
			String v[] = new String[len];
			int f1 = calfitness(len, bit);
			int initial = f1;
			String[] best = new String[len];
			System.out.println("�������檺�H������" + "��" + f1 + "��1");
			System.out.println(Arrays.toString(bit));
			while (time < iteration) {
				v = neibor(len, bit, limitint, direction, speed);
				int f2 = calfitness(v.length, v);
				if (f2 > f1) { // �N��o�Ӥ�V�i�H�~���,direction����
					bit = v;
					best = v;
					f1 = f2;
				} else if (f2 == f1) { // �۵��]����
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
			System.out.println("����run(��" + timerun + "��)��" + f1 + "��1");
			sum += f1 - initial;
			System.out.println(Arrays.toString(best));
			System.out.println();
		}
		System.out.println("�Ӧ��t��k���į७�������" + sum / run + "��1");
	}

	public static String[] select(int n) {
		String[] bit = new String[n];
		for (int i = 0; i < n; i++) {
			int temp = rand.nextInt(2); // �o�ˬO0-1
			bit[i] = Integer.toString(temp);
		}
		return bit;
	}

	public static int calfitness(int len, String bit[]) { // �p�⦳�h�֭�1
		int one = 0;
		for (int i = 0; i < len; i++) {
			if (bit[i].equals("1")) {
				one++;
			}
		}
		return one;
	}

	public static String[] neibor(int len, String bit[], int limitint, int direction, int speed) {
		String a = ""; // �x�s�ثe�Ѫ��G�i��
		String v[] = new String[len];
		for (int i = 0; i < bit.length; i++) {
			a += bit[i];
		}
		int b = Integer.parseInt(a, 2); // �G�i���ন�Q�i��
		int dangle1 = b+(direction*speed); // �o�̷|�v�T��_���d��C
		// int dangle2=b-1;
		if (dangle1 > limitint) {
			dangle1 = limitint;
		}
		String dangle1bin1 = Integer.toBinaryString(dangle1);
		// String dangle1bin2=Integer.toBinaryString(dangle2);
		String buffer = "";
		// �H�U�B�z�r����D......
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
