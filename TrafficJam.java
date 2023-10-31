package TSPProblem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Stack;

public class TrafficJam {
	private String Segment; // Cạnh (đoạn đường)
	private LocalTime startTime; // Thời gian bắt đầu sk tắc
	private LocalTime endTime; // Thời gian kết thúc sk tắc
	private int Delay; // Độ chậm trễ

	public TrafficJam(String Segment, LocalTime startTime, LocalTime endTime, int Delay) {
		this.Segment = Segment;
		this.startTime = startTime;
		this.endTime = endTime;
		this.Delay = Delay;
	}
	public String getSegment() {
		return Segment;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public int getDelay() {
		return Delay;
	}

	public static int calDelay(TSPProblem tsp, int currpoint, int nextpoint, LocalTime currTime) {
		int delay = 0;
		for (TrafficJam tj : tsp.getTrafficJams()) {
			if (tj.getSegment().equals(tsp.getVertexNames()[currpoint] + tsp.getVertexNames()[nextpoint])) {
				// Kiểm tra xem thời gian đi từ điểm đầu đã nằm trong khoảng thời gian tắc chưa
				// nếu chưa sẽ cộng vào thời gian di chuyển bth và kiểm tra với đỉnh cuối
				if (currTime.isBefore(tj.getEndTime())) {
					if (currTime.plusMinutes(tsp.getWeightMatrix()[currpoint][nextpoint]).isAfter(tj.getStartTime())) {
						delay = tj.getDelay();
					}
				}
			}
		}
		return delay;
	}

}
