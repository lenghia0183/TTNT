package TSPProblem;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class TSPFileReader {
	public static TSPProblem readTSPProblemFromFile(String filename) throws IOException {
		BufferedReader reader = null;
		try {
			// Mở tệp tin để đọc
			reader = new BufferedReader(new FileReader(filename));
			// Đọc số đỉnh từ tệp tin
			int n = Integer.parseInt(reader.readLine());

			// Đọc tên các đỉnh từ tệp và lưu vào mảng
			String[] vertexNames = reader.readLine().split(" ");

			int[][] weightMatrix = new int[n][n];

			for (int i = 0; i < n; i++) {
				String[] row = reader.readLine().split(" ");
				for (int j = 0; j < n; j++) {
					weightMatrix[i][j] = Integer.parseInt(row[j]);
				}
			}

			// Đọc điểm bắt đầu và lưu vào biến poinStart
			String startPoint = reader.readLine();

			// Lấy từ file
			LocalTime startTime = LocalTime.parse(reader.readLine());

			ArrayList<TrafficJam> trafficJams = new ArrayList<>();
			String line = reader.readLine();
			while (line != null && !line.equalsIgnoreCase("")) {
				String[] parts = line.split(" ");

				String segment = parts[0];

				LocalTime jamStartTime = LocalTime.parse(parts[1]);

				LocalTime jamEndTime = LocalTime.parse(parts[3]);

				int delay = Integer.parseInt(parts[4]);

				trafficJams.add(new TrafficJam(segment, jamStartTime, jamEndTime, delay));

				line = reader.readLine();
			}

			// tạo và trả về một đối tượng TSPProblem chứa các thông tin đã đọc
			return new TSPProblem(n, vertexNames, weightMatrix, trafficJams, startPoint, startTime);

		} finally {
			// Đóng tệp tin sau khi đọc hoặc khi có lỗi đọc tệp tin
			if (reader != null) {
				reader.close();
			}
		}

	}



}
