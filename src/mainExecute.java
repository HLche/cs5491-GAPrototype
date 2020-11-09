import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class mainExecute {

	public static void main(String[] args) {
		setup();
	}

	static void setup() {
		int POP_SIZE = 10;
		int MAX_ITERATION = 100000;
		double MUTATION_RATE = 0.003;
		double CROSSOVER_RATE = 0.8;
		double CHRMOSOME_LENGTH = 30;
		double MAX_GENERATION = 50;

		ArrayList<city> cityList = cityInit();
		ArrayList<order> Population = new ArrayList<order>();
		/* ================================================================ */
		ArrayList<Integer> initOrder = new ArrayList<Integer>();
		for(int i=0; i<cityList.size();i++) {
			initOrder.add(i);
		}
		/* ================================================================ */
		order bestEver = null;
		int bestIteration = 0;
		
		/* ================================================================ */
		System.out.println("-------------------------------------------------");
		for (int i = 0; i < cityList.size(); i++) {
			System.out.println(i + ") " + cityList.get(i).displayInfo());
		}
		System.out.println("-------------------------------------------------");
		/* ================================================================ */
		System.out.println("Initial random order:");
		for (int i = 0; i < POP_SIZE; i++) {
			ArrayList<Integer> shuffledOrder = shuffle(initOrder, 10);
			order tempOrder = new order(shuffledOrder, cityList);
			System.out.println(i + ") " + tempOrder.displayInfo());
			Population.add(tempOrder);
		}
		System.out.println("-------------------------------------------------");
		for (int evaluation = 0; evaluation < MAX_ITERATION; evaluation++) { // Evaluation loop
			System.out.println("Evaluation: " + evaluation);
			GA.normalizeFitness(Population);

			for (int i = 0; i < Population.size(); i++) {
				if (bestEver == null) {
					bestEver = Population.get(i);
				} else {
					if (bestEver.getFitness() < Population.get(i).getFitness()) {
						bestEver = Population.get(i);
						bestIteration = evaluation;
					}
				}
			}
			System.out.println("-------------------------------------------------");
			System.out.println("New Generation: ");
			Population = GA.nextGeneration(Population, CROSSOVER_RATE, cityList);
			
			populationDisplay(Population);
			
			System.out.println("-------------------------------------------------");
			System.out.println("Mutation (No mutation if empty): ");
			for (int i = 0; i < Population.size(); i++) {
				order mutatedOrder = GA.mutation(Population.get(i), cityList, MUTATION_RATE);
				Population.set(i, mutatedOrder);
			}
			System.out.println("=================================================");
		}
		System.out.println("Best result: "+bestEver.displayInfo()+" From: iteration "+bestIteration);
	}

	static ArrayList<city> cityInit() {
		ArrayList<city> cityList = new ArrayList<city>();
		cityList.add(new city("city 0", 0.3642, 0.7770));
		cityList.add(new city("city 1", 0.7185, 0.8312));
		cityList.add(new city("city 2", 0.0986, 0.5891));
		cityList.add(new city("city 3", 0.2954, 0.9606));
		cityList.add(new city("city 4", 0.5951, 0.4647));
		cityList.add(new city("city 5", 0.6697, 0.7657));
		cityList.add(new city("city 6", 0.4353, 0.1709));
		cityList.add(new city("city 7", 0.2131, 0.8349));
		cityList.add(new city("city 8", 0.3479, 0.6984));
		cityList.add(new city("city 9", 0.4516, 0.0488));
		return cityList;
	}

	static ArrayList<Integer> shuffle(ArrayList<Integer> order, int times) {
		ArrayList<Integer> newOrder = new ArrayList<Integer>(order);
		for (int i = 0; i < times; i++) {
			int indexA = (int) (Math.random() * order.size());
			int indexB = (int) (Math.random() * order.size());
			Collections.swap(newOrder, indexA, indexB);
		}
		return newOrder;
	}
	
	static void populationDisplay(ArrayList<order> Population) {
		for (int i = 0; i < Population.size(); i++) {
			System.out.println(i + ") " + Population.get(i).displayInfo());
		}
	}

}
