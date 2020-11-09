import java.util.ArrayList;

public class order {
	private ArrayList<Integer> orderArray = new ArrayList<Integer>();
	private double totalDistance;
	private double fitness;
	private double normalizedFitness;

	order(ArrayList<Integer> orderArray, ArrayList<city> cityList) {
		this.orderArray = orderArray;
		double tempDistance = calcDistance(cityList);
		this.totalDistance = tempDistance;
		this.fitness = fitnessFunction(tempDistance);
	}

	ArrayList<Integer> getOrder() {
		ArrayList<Integer> tempOrderArray = new ArrayList<Integer>(orderArray);
		return tempOrderArray;
	}

	void setOrder(ArrayList<Integer> orderArray) {
		this.orderArray = orderArray;
	}

	double getTotalDistance() {
		return this.totalDistance;
	}

	double getNormalizedFitness() {
		return this.normalizedFitness;
	}

	void setNormalizedFitness(double fitness) {
		this.normalizedFitness = fitness;
	}

	double getFitness() {
		return this.fitness;
	}

	double fitnessFunction(double distance) {
		return 1 / (distance);
	}

	double calcDistance(ArrayList<city> cityList) {
		double sum = 0;
		for (int i = 0; i < orderArray.size() - 1; i++) {
			city currentCity = cityList.get(orderArray.get(i));
			city nextCity = cityList.get(orderArray.get(i + 1));
			double tempDistanceX = Math.pow((currentCity.getPosX() - nextCity.getPosX()), 2);
			double tempDistanceY = Math.pow((currentCity.getPosY() - nextCity.getPosY()), 2);
			double tempDistance = Math.sqrt(tempDistanceX + tempDistanceY);
			sum = sum + tempDistance;
		}
		return sum;
	}

	String displayInfo() {
		return "\tOrder: " + orderArray + "\tDistance: " + totalDistance + "\t Fitness: " + fitness;
	}
}
