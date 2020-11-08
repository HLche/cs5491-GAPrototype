
public class DNA {
	char[] genes;
	
	float fitness;
	
	DNA(int num){
		genes = new char[num];
		for (int i=0;i<genes.length;i++) {
			genes[i] = (char) random(32,128);  // Pick from range [32-128] of chars
		}
	}
	
	String getPhrase() {
		return new String(genes);
	}
	
	DNA crossover(DNA partner) {
		// A new child
		DNA child = new DNA(genes.length);
		int midpoint = (int)(random(genes.length)); // Pick a midpoint
		// Half from one, half from the other
		for (int i = 0; i < genes.length; i++) {
			if (i > midpoint) child.genes[i] = genes[i];
			else              child.genes[i] = partner.genes[i];
		}
		return child;
	}
	void mutate(double mutationRate) {
		for (int i = 0; i < genes.length; i++) {
			if (random(1) < mutationRate) {
				genes[i] = (char) random(32,128);
			}
		}
	}
	
	double random (int highRange) {
		return Math.random()*highRange;
	}
	double random (int lowRange, int highRange) {
		return Math.random()*(highRange-lowRange)+lowRange;
	}
}
