import java.util.ArrayList;
import java.util.Collections;

public class GA {
//	static void calFitness(ArrayList<order> Population, ArrayList<city> cityList) {
//		double recordDistance = Double.POSITIVE_INFINITY;
//		order bestRecord = null;
//		for (int i = 0; i < Population.size(); i++) {
//			double orderDistance = Population.get(i).getTotalDistance();
//			System.out.println("orderDistance: " + orderDistance);
//
//			if (orderDistance < recordDistance) {
//				recordDistance = orderDistance;
//				bestRecord = Population.get(i);
//			}
//		}
//	}

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

	static ArrayList<order> Selection(ArrayList<order> Population) {
		ArrayList<order> selectedPopulation = new ArrayList<order>();
		for (int i = 0; i < Population.size(); i++) {
			selectedPopulation.add(selectByFitness(Population));
		}
		return selectedPopulation;
	}

	static order selectByFitness(ArrayList<order> Population) {
		int index = 0;
		double selectValue = random(1);
		while (selectValue > 0) {
			selectValue = selectValue - Population.get(index).getNormalizedFitness();
			if (selectValue > 0) {
				index++;
			}
		}
		return Population.get(index);
	}

	static ArrayList<order> crossover(ArrayList<order> Population, double CROSSOVER_RATE, ArrayList<city> cityList,
			int MODE) {
		ArrayList<order> newGeneration = new ArrayList<order>();
		for (int i = 0; i < Population.size(); i++) {
			order parentOrder1 = Population.get(i);
			double crossoverProb = random(1);
			if (crossoverProb < CROSSOVER_RATE) {
				order parentOrder2 = Population.get((i + 1) % Population.size());
				newGeneration.add(reproduction(parentOrder1, parentOrder2, cityList, MODE));
			} else {
				newGeneration.add(parentOrder1);
			}
		}
		return newGeneration;
	}

	static order reproduction(order orderA, order orderB, ArrayList<city> cityList, int MODE) {
		ArrayList<Integer> orderArrayA = orderA.getOrder();
		ArrayList<Integer> orderArrayB = orderB.getOrder();
		int startPoint = 0;
		int endPoint = 0;
		
		int sliceStartIndex = 0;
		int ranRange = 0;
		int cutLength = 0;
		if (MODE == 0) {
			sliceStartIndex = (int) Math.round(random(orderArrayA.size() - 1));
			ranRange = orderArrayA.size() - sliceStartIndex;
		} else if (MODE == 1) {
			startPoint = orderArrayA.get(0);
			endPoint = orderArrayA.get(orderArrayA.size()-1);
			sliceStartIndex = (int) Math.round(random(1, orderArrayA.size() - 2));
			ranRange = orderArrayA.size() - sliceStartIndex - 1;
		}
		
		while (cutLength <= 0) {
			cutLength = (int) Math.round((random(1, (ranRange - 1))));
		}

		ArrayList<Integer> crossoverOrder = new ArrayList<Integer>();

		if (MODE == 0) {
			crossoverOrder = new ArrayList<Integer>(orderArrayA.subList(sliceStartIndex, sliceStartIndex + cutLength));
			for (int i = 0; i < orderArrayB.size(); i++) {
				int tempCityValue = orderArrayB.get(i);
				if (!crossoverOrder.contains(tempCityValue)) {
					crossoverOrder.add(tempCityValue);
				}
			}
		}
		else if (MODE == 1) {
			crossoverOrder.add(startPoint);
			ArrayList<Integer> tempSliceOrder = new ArrayList<Integer>(orderArrayA.subList(sliceStartIndex, sliceStartIndex + cutLength));
			System.out.println("endPoint: " + tempSliceOrder);
			crossoverOrder.addAll(tempSliceOrder);
			for (int i = 0; i < orderArrayB.size(); i++) {
				int tempCityValue = orderArrayB.get(i);
				if (!crossoverOrder.contains(tempCityValue) && tempCityValue != endPoint) {
					crossoverOrder.add(tempCityValue);
				}
			}
			crossoverOrder.add(endPoint);
		}

//		System.out.println("orderArrayA: " + orderArrayA);
//		System.out.println("orderArrayB: " + orderArrayB);
//		System.out.println("startIndex: " + sliceStartIndex);
//		System.out.println("EndIndex: " + (sliceStartIndex + cutLength));
//		System.out.println("crossoverOrder: " + crossoverOrder);
//		System.out.println();

		return new order(crossoverOrder, cityList);
	}

	static void mutation(ArrayList<order> Population, ArrayList<city> cityList, double mutaionRate, int MODE) {
		for (int i = 0; i < Population.size(); i++) {
			double mutateProb = random(1);
			if (mutateProb < mutaionRate) {
				order mutatedOrder = mutateOrder(Population.get(i), cityList, MODE);
				Population.set(i, mutatedOrder);
			}
		}
	}

	static order mutateOrder(order order, ArrayList<city> cityList, int MODE) {
		ArrayList<Integer> newOrder = new ArrayList<Integer>(order.getOrder());
		int indexA = 0;
		int indexB = 0;
		while (indexA == indexB) {
			if (MODE == 0) {
				indexA = (int) Math.round(random(newOrder.size() - 1));
				indexB = (int) Math.round(random(newOrder.size() - 1));
			} else if (MODE == 1) {
				indexA = (int) Math.round(random(1, newOrder.size() - 2));
				indexB = (int) Math.round(random(1, newOrder.size() - 2));
			}
		}
		Collections.swap(newOrder, indexA, indexB);
		System.out.println(
				"Mutated from: " + order.getOrder() + " TO " + newOrder + " | Swap[" + indexA + ", " + indexB + "]");
		order mutatedOrder = new order(newOrder, cityList);
		return mutatedOrder;
	}

	static double random(int highRange) {
		return Math.random() * highRange;
	}

	static double random(int lowRange, int highRange) {
		return Math.random() * (highRange - lowRange) + lowRange;
	}

}
