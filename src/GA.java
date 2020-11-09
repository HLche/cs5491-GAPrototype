import java.util.ArrayList;
import java.util.Collections;

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
			double tempFitness = Population.get(i).getFitness();
			fitnessSum += tempFitness;
		}
		for (int i = 0; i < Population.size(); i++) { // normalize fitness
			double tempFitness = Population.get(i).getFitness();
			double normalizedFitness = tempFitness / fitnessSum;
			Population.get(i).setNormalizedFitness(normalizedFitness);
		}
	}

	static ArrayList<order> nextGeneration(ArrayList<order> Population, double CROSSOVER_RATE, ArrayList<city> cityList) {
		ArrayList<order> newGeneration = new ArrayList<order>();
		for (int i = 0; i < Population.size(); i++) {
			double crossoverProb = Math.random();
			if (crossoverProb < CROSSOVER_RATE) {
				order parentOrder1 = selectByFitness(Population);
				order parentOrder2 = selectByFitness(Population);
				newGeneration.add(crossover(parentOrder1, parentOrder2, cityList));
			} else {
				newGeneration.add(selectByFitness(Population));
			}

		}
		return newGeneration;
	}

	static order selectByFitness(ArrayList<order> Population) {
		int index = 0;
		double selectValue = Math.random();
		while (selectValue > 0) {
			selectValue = selectValue - Population.get(index).getNormalizedFitness();
			if (selectValue > 0) {
				index++;
			}
		}
		return Population.get(index);
	}

	static order mutation(order order, ArrayList<city> cityList, double mutaionRate) {
		double mutateProb = Math.random();
		if (mutateProb < mutaionRate) {
			ArrayList<Integer> newOrder = new ArrayList<Integer>(order.getOrder());
			int indexA = 0;
			int indexB = 0;
			while (indexA == indexB) {
				indexA = (int) (Math.random() * newOrder.size());
				indexB = (int) (Math.random() * newOrder.size());
			}
			Collections.swap(newOrder, indexA, indexB);
			System.out.println("Mutated from: " + order.getOrder() + " TO " + newOrder + " | Swap[" + indexA + ", "
					+ indexB + "]");
			order tempOrder = new order(newOrder, cityList);
			return tempOrder;
		} else {
			return order;
		}
	}

	static order crossover(order orderA, order orderB, ArrayList<city> cityList) {
		ArrayList<Integer> orderArrayA = orderA.getOrder();
		ArrayList<Integer> orderArrayB = orderB.getOrder();
	
		int startIndex = (int) (Math.random() * orderArrayA.size());
		int ranRange = orderArrayA.size() - startIndex;
		int cutLength = (int) Math.round(((Math.random() * (ranRange - 1)) + 1));
		ArrayList<Integer> crossoverOrder = new ArrayList<Integer>(
				orderArrayA.subList(startIndex, startIndex + cutLength));

		for (int i = 0; i < orderArrayB.size(); i++) {
			int tempCityValue = orderArrayB.get(i);
			if(!crossoverOrder.contains(tempCityValue)) {
				crossoverOrder.add(tempCityValue);
			}
		}
		return new order(crossoverOrder, cityList);
	}
}
