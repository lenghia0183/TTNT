package TSPProblem;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class BranchAndBound {

	private static int[] bestPath; // Chu trình tốt nhất
	private static LocalTime bestEndTime; // Thời điểm kết thúc sớm nhất

	public static void solveTSP(TSPProblem tsp) {

		bestPath = new int[tsp.getN() + 1];
		boolean[] visisted = new boolean[tsp.getN()];
		for (int i = 0; i < tsp.getN(); i++) {
			visisted[i] = false;
		}
		int[] currentPath = new int[tsp.getN() + 1];
		// Lấy địa điểm xuất phát
		int strpoint = Arrays.asList(tsp.getVertexNames()).indexOf(tsp.getStartPoint());
		int crpoint = strpoint;
		// Thêm vào địa điểm xuất phát vào chu trình
		currentPath[0] = crpoint;
		// Đặt điểm xuất phát là đã đi
		visisted[crpoint] = true;

		// Đặt thời gian tính toán = thời gian xuất phát
		LocalTime currTime = tsp.getStartTime();

		// Thêm bestEndTime
		bestEndTime = LocalTime.parse("23:59");

		TSPRecursive(tsp, visisted, currentPath, crpoint, currTime, strpoint, 1);
		
		tsp.Showtheway(bestPath, (int)Duration.between(tsp.getStartTime(), bestEndTime).toMinutes());


	}
			
			
	
	public static void TSPRecursive(TSPProblem tsp, boolean visited[], int[] currentPath, int currpoint,
			LocalTime currTime, int strpoint, int index) {
		for (int i = 0; i < tsp.getN(); i++) {
			// Kiểm tra xem có đường đi giữa 2 điểm không
			if (tsp.getWeightMatrix()[currpoint][i] > 0) {
				// Kiểm tra xem điểm tiếp theo đã đi chưa
				if (!visited[i]) {
					// Kiểm tra xem đoạn đường có phải đoạn đường tắc không
					int delay = TrafficJam.calDelay(tsp, currpoint, i, currTime);
					currTime = currTime.plusMinutes(delay + tsp.getWeightMatrix()[currpoint][i]);
					currentPath[index] = i;
					// Kiểm tra xem giá trị mới có đủ tiềm năng không
					if (currTime.isBefore(bestEndTime)) {
						// Kiểm tra i có phải điểm cuối không
						if (index == tsp.getN() - 1) {
							// Kiểm tra xem nó có đi được về điểm đầu không và ngắn hơn bestEndTime
							// Lưu vào bestPath
							if (tsp.getWeightMatrix()[i][strpoint] > 0) {
								currTime = currTime.plusMinutes(tsp.getWeightMatrix()[i][strpoint]);
								if (currTime.isBefore(bestEndTime)||currTime.equals(bestEndTime)) {
									currentPath[index + 1] = strpoint;
									bestEndTime = currTime;
									bestPath = currentPath.clone();

								}
								currTime = currTime.minusMinutes(tsp.getWeightMatrix()[i][strpoint]);
							}
						} else {
							// Gọi đệ quy
							visited[i] = true;
							TSPRecursive(tsp, visited, currentPath, i, currTime, strpoint, index + 1);
							currTime = currTime.minusMinutes(delay + tsp.getWeightMatrix()[currpoint][i]);
							visited[i] = false;
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		String filename = "D:\\JP301_NguyenHieu\\BuiltG1\\cmsystem\\src\\main\\java\\TSPProblem\\data\\data1.txt";

		try {
			TSPProblem tsp = TSPFileReader.readTSPProblemFromFile(filename);

			solveTSP(tsp);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
}
