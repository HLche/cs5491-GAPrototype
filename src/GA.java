import java.util.ArrayList;

public class GA {
	static void calFitness(ArrayList<order> Population, ArrayList<city> cityList) {
		double recordDistance = Double.POSITIVE_INFINITY;
		order bestRecord = null;
		for (int i = 0; i < Population.size(); i++) {
			double orderDistance = Population.get(i).getTotalDistance();
			System.out.println("orderDistance: " + orderDistance);

			if (orderDistance < recordDistance) {
				recordDistance = orderDistance;
				bestRecord = Population.get(i);
			}
		}
	}

	static void normalizeFitness(ArrayList<order> Population) {
		double fitnessSum = 0;
		for (int i = 0; i < Population.size(); i++) { // get total fitness sum
			double tempFitness = fitnessFunction(Population.get(i).getTotalDistance());
			fitnessSum += tempFitness;
		}
		for (int i = 0; i < Population.size(); i++) { // normalize fitness
			double tempFitness = fitnessFunction(Population.get(i).getTotalDistance());
			double normalizedFitness = tempFitness / fitnessSum;
			Population.get(i).setFitness(normalizedFitness);
		}
	}

	static double fitnessFunction(double distance) {
		return 1 / (distance);
	}
	
	static void nextGeneration(ArrayList<order> Population) {
		ArrayList<order> newGeneration = new ArrayList<order>(Population);
		
	}
}
