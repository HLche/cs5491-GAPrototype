import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class mainExecute {

	public static void main(String[] args) {
		setup();
	}

	static void setup() {
		int MODE = 0; // 0: Random Start End, 1: Specific Start End
		int STARTPOINT = 0;
		int ENDPOINT = 0;
		int POP_SIZE = 10;
		int MAX_GENERATION = 2000;
		double MUTATION_RATE = 0.003;
		double CROSSOVER_RATE = 0.8;

		order bestEver = null;
		int bestIteration = 0;
		ArrayList<city> cityList = cityInit();
		ArrayList<order> Population = new ArrayList<order>();
		ArrayList<Integer> initOrder = null;
		/* ================================================================ */
		Scanner scanner = new Scanner(System.in);
		System.out.print("Please select mode for GA: ");
		MODE = scanner.nextInt();
		if (MODE == 1) {
			System.out.print("[MODE " + MODE + "]please select starting point: ");
			STARTPOINT = scanner.nextInt();
			System.out.print("[MODE " + MODE + "]Please select Ending point: ");
			ENDPOINT = scanner.nextInt();
			System.out.println("[MODE " + MODE + "]starting point: " + STARTPOINT + " Ending point: " + ENDPOINT);
		}
		scanner.close();
		/* ================================================================ */
		cityDisplay(cityList);
		/* ================================================================ */
		// initOrder generation
		if (MODE == 0) {
			initOrder = initOrderGenerate(cityList);
		} else if (MODE == 1) {
			initOrder = initOrderGenerate(cityList, STARTPOINT, ENDPOINT);
		} else {
			MODE = 0;
			initOrder = initOrderGenerate(cityList);
		}
		Population = initOrderPopGenerate(initOrder, cityList, POP_SIZE, MODE);
		bestEver = Population.get(0);
		System.out.println("Initial random order:");
		populationDisplay(Population);
		/* ================================================================ */
		System.out.println("-------------------------------------------------");
		for (int evaluation = 0; evaluation < MAX_GENERATION; evaluation++) { // Evaluation loop
			System.out.println("Evaluation: " + evaluation);
			GA.normalizeFitness(Population);

			/* ================================================================ */// Record best result
			for (int i = 0; i < Population.size(); i++) {
				if (bestEver.getFitness() < Population.get(i).getFitness()) {
					bestEver = Population.get(i);
					bestIteration = evaluation;
				}
			}
			/* ================================================================ */

			System.out.println("-------------------------------------------------");
			System.out.println("Selected: ");
			Population = GA.Selection(Population);
			populationDisplay(Population);

			System.out.println("-------------------------------------------------");
			System.out.println("crossover: ");
			Population = GA.crossover(Population, CROSSOVER_RATE, cityList, MODE);
			populationDisplay(Population);

			System.out.println("-------------------------------------------------");
			System.out.println("Mutation (No mutation if empty): ");
			GA.mutation(Population, cityList, MUTATION_RATE, MODE);
			System.out.println("=================================================");
		}
		System.out.println("Best result: " + bestEver.displayInfo() + " From: iteration " + bestIteration);
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

	static void cityDisplay(ArrayList<city> cityList) {
		System.out.println("-------------------------------------------------");
		for (int i = 0; i < cityList.size(); i++) {
			System.out.println(i + ") " + cityList.get(i).displayInfo());
		}
		System.out.println("-------------------------------------------------");
	}

	static ArrayList<order> initOrderPopGenerate(ArrayList<Integer> initOrder, ArrayList<city> cityList, int POP_SIZE,
			int MODE) {
		ArrayList<order> Population = new ArrayList<order>();
		for (int i = 0; i < POP_SIZE; i++) {
			ArrayList<Integer> shuffledOrder = shuffle(initOrder, 20, MODE);
			order tempOrder = new order(shuffledOrder, cityList);
			Population.add(tempOrder);
		}
		return Population;
	}

	static ArrayList<Integer> shuffle(ArrayList<Integer> order, int times, int MODE) {
		ArrayList<Integer> newOrder = new ArrayList<Integer>(order);
		for (int i = 0; i < times; i++) {
			int indexA = 0;
			int indexB = 0;
			if (MODE == 0) {
				indexA = (int) Math.round(GA.random(order.size() - 1));
				indexB = (int) Math.round(GA.random(order.size() - 1));
			} else if (MODE == 1) {
				indexA = (int) Math.round(GA.random(1, order.size() - 2));
				indexB = (int) Math.round(GA.random(1, order.size() - 2));
			}
			Collections.swap(newOrder, indexA, indexB);
		}
		return newOrder;
	}

	static ArrayList<Integer> initOrderGenerate(ArrayList<city> cityList) {
		ArrayList<Integer> initOrder = new ArrayList<Integer>();
		for (int i = 0; i < cityList.size(); i++) {
			initOrder.add(i);
		}
		return initOrder;
	}

	static ArrayList<Integer> initOrderGenerate(ArrayList<city> cityList, int STARTPOINT, int ENDPOINT) {
		ArrayList<Integer> initOrder = new ArrayList<Integer>();
		initOrder.add(STARTPOINT);
		for (int i = 0; i < cityList.size(); i++) {
			if (i != STARTPOINT && i != ENDPOINT) {
				initOrder.add(i);
			}
		}
		initOrder.add(ENDPOINT);
		return initOrder;
	}

	static void populationDisplay(ArrayList<order> Population) {
		for (int i = 0; i < Population.size(); i++) {
			System.out.println(i + ") " + Population.get(i).displayInfo());
		}
	}

}
