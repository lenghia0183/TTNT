package TSPProblem;

import java.time.LocalTime;
import java.util.ArrayList;

public class TSPProblem {
	private int n; // Số lượng đỉnh
	private String[] vertexNames; // Tên các đỉnh
	private int[][] weightMatrix;// Ma trận trọng số thời gian
	private ArrayList<TrafficJam> trafficJams; // Danh sách đoạn đường bị tắc
	private String startPoint;// Địa điểm xuất phát
	private LocalTime startTime;// Thời gian xuất phát

	public TSPProblem(int n, String[] vertexNames, int[][] weightMatrix, ArrayList<TrafficJam> trafficJams,
			String startPoint, LocalTime startTime) {
		this.n = n;
		this.vertexNames = vertexNames;
		this.weightMatrix = weightMatrix;
		this.trafficJams = trafficJams;
		this.startPoint = startPoint;
		this.startTime = startTime;
	}

	public int getN() {
		return n;
	}

	public String[] getVertexNames() {
		return vertexNames;
	}

	public int[][] getWeightMatrix() {
		return weightMatrix;
	}

	public ArrayList<TrafficJam> getTrafficJams() {
		return trafficJams;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public LocalTime getStartTime() {
		return startTime;
	}
	
	public void Showtheway(int[] a, int totalTime) {
		for(int i=0; i<a.length-1;i++) {
			System.out.print(getVertexNames()[a[i]]+"->");
		}
		System.out.println(getVertexNames()[a[a.length-1]]);
		System.out.println("Tổng thời gian đi được: "+totalTime);
	}
	
	public void Showtheway(ArrayList<Integer> a, int totalTime) {
		for(int i=0; i<a.size()-1;i++) {
			System.out.print(getVertexNames()[a.get(i)]+"->");
		}
		System.out.println(getVertexNames()[a.get(a.size()-1)]);
		System.out.println("Tổng thời gian đi được: "+totalTime);
	}
	
}
