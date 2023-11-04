package TSPProblem;

import java.io.IOException;
import java.time.*;
import java.util.*;

public class GeneticAlgorithm {

	private static int individual = 50; // Số cá thể
	private static ArrayList<Integer> result = new ArrayList<>();// 1 chu trình
	private static ArrayList<ArrayList<Integer>> results = new ArrayList<>(individual); // Chuỗi các chu trình
	private static ArrayList<Integer> Fitness = new ArrayList<>(individual);// Tổng thời gian đi được
	private static Random ran = new Random();

	public static void InitialPopulation(TSPProblem tsp) {
		for (int i = 0; i < individual; i++) {
			result.clear();
			result.add(Arrays.asList(tsp.getVertexNames()).indexOf(tsp.getStartPoint()));// Thêm địa điểm đầu
			for (int k = 1; k < tsp.getN(); k++) {
				result.add(k, ran.nextInt(tsp.getN()));// Thêm địa điểm tiếp theo
				while (result.indexOf(result.get(k)) != k) { // Check điểm đang xét đã xuất hiện chưa nếu có thì đổi
					result.set(k, ran.nextInt(tsp.getN()));
				}
			}
			result.add(tsp.getN(), result.get(0));// Thêm địa điểm cuối
			results.add(new ArrayList<>(result));// Thêm chu trình mới vào chuỗi chu trình
		}
	}

	public static void EvulationFitness(TSPProblem tsp) { // Tính tổng thời gian di chuyển
		Fitness.clear();
		for (int i = 0; i < individual; i++) {
			LocalTime fitness1 = tsp.getStartTime();
			int fitness2 = 0;
			ArrayList<Integer> result = results.get(i);
			// Tính cho n+1 điểm
			for (int k = 0; k < tsp.getN(); k++) {
				// Kiểm tra xem có đường đi giữa 2 điểm không
				if (tsp.getWeightMatrix()[result.get(k)][result.get(k + 1)] > 0) {
					// Kiểm tra xem đoạn đường đó có thuộc diện tắc đường trong đối tượng traffijam
					int delay = TrafficJam.calDelay(tsp, result.get(k), result.get(k + 1), fitness1);
					fitness2 += tsp.getWeightMatrix()[result.get(k)][result.get(k + 1)] + delay;
					fitness1 = fitness1.plusMinutes(tsp.getWeightMatrix()[result.get(k)][result.get(k + 1)] + delay);

				} else {
					fitness2 += 120;
				}
			}
			Fitness.add(fitness2);
		}
	}

	public static void Selection() {
		ArrayList<Integer> temp = (ArrayList<Integer>) Fitness.clone();

		Collections.sort(temp);

		int mark = temp.get(individual * 80 / 100);// Chọn làm mốc thời gian
		int check = individual;
		for (int i = 0; i < check; i++) {

			while (Fitness.get(i) > mark) {

				results.remove(i);
				Fitness.remove(i);
				if (i >= results.size())
					i--;
				check--;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void Hybridize(TSPProblem tsp) {
		while (results.size() != individual) {
			ArrayList<Integer> dad = (ArrayList<Integer>) results.get(ran.nextInt(results.size())).clone();
			ArrayList<Integer> mom = (ArrayList<Integer>) results.get(ran.nextInt(results.size())).clone();

			int cut = ran.nextInt((tsp.getN() - 1)) + 1;
			result.clear();

			result.add(dad.get(0));
			// Lấy từ điểm cut đến điểm n-2
			for (int i = cut; i < tsp.getN(); i++) {
				result.add(dad.get(i));
			}
			// Chọn dần từ đầu đến cuối chu trình mom nạp dần các điểm vào chu trình result
			for (int i = 1; i < tsp.getN(); i++) {

				if (result.indexOf(mom.get(i)) != -1) { // Cần kiểm tra lại điều kiện
					continue;
				} else {
					result.add(mom.get(i));
				}
			}
			result.add(dad.get(0));
			results.add(new ArrayList<>(result));
		}
	}

	public static void Mutate(TSPProblem tsp) {
		int pos = ran.nextInt(results.size());
		result = results.get(pos);
		int count = ran.nextInt((tsp.getN() - 1)) + 1;
		while (count > 0) {
			int p1 = ran.nextInt((tsp.getN() - 1)) + 1;
			int p2 = ran.nextInt((tsp.getN() - 1)) + 1;

			int temp = result.get(p1);
			result.set(p1, result.get(p2));
			result.set(p2, temp);

			count--;
		}
		results.set(pos, (result));
	}

	@SuppressWarnings("unchecked")
	public static void Print(TSPProblem tsp) {
		ArrayList<Integer> temp = (ArrayList<Integer>) Fitness.clone();
		Collections.sort(temp);
		int best = temp.get(0);
		for (int i = 0; i < individual; i++) {  
			if (Fitness.get(i) == best)

				tsp.Showtheway(results.get(i), best);
		}

	}

	public static void main(String[] args) throws IOException {
		String filename = "D:\\JP301_NguyenHieu\\BuiltG1\\cmsystem\\src\\main\\java\\TSPProblem\\data\\data1.txt";
		TSPProblem tsp = TSPFileReader.readTSPProblemFromFile(filename);
		InitialPopulation(tsp);
		for (int i = 0; i < 100; i++) {
			EvulationFitness(tsp);
			Selection();//xóa bớt phần tử cá thể = 40 fitness = 40
			Hybridize(tsp);//cá thể =50 fitness =40
			Mutate(tsp);//cá thể =50 fitness =40
		}
		EvulationFitness(tsp);
		Print(tsp);
	}

}
