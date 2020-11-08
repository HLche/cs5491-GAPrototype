import java.util.ArrayList;

public class order {
	ArrayList<Integer> orderArray=new ArrayList<Integer>();
	double totalDistance;	
	double fitness;
	
	order(ArrayList<Integer> orderArray, ArrayList<city> cityList){
		this.orderArray=orderArray;
		this.totalDistance=calcDistance(cityList);
	}
	
	ArrayList<Integer> getOrder(){
		ArrayList<Integer> tempOrderArray = new ArrayList<Integer>(orderArray);
		return tempOrderArray;
	}
	
	void setOrder(ArrayList<Integer> orderArray) {
		this.orderArray=orderArray;
	}
	
	double getTotalDistance() {
		return this.totalDistance;
	}
	
	double getFitness() {
		return this.fitness;
	}
	
	void setFitness(double fitness) {
		this.fitness=fitness;
	}
	
	double calcDistance(ArrayList<city> cityList) {
		double sum = 0;
		for(int i=0; i< orderArray.size()-1 ; i++) {
			city currentCity=cityList.get(orderArray.get(i));
			city nextCity=cityList.get(orderArray.get(i+1));
			double tempDistanceX = Math.pow((currentCity.getPosX()-nextCity.getPosX()), 2);
			double tempDistanceY = Math.pow((currentCity.getPosY()-nextCity.getPosY()), 2);
			double tempDistance = Math.sqrt(tempDistanceX+tempDistanceY);
			sum = sum+tempDistance;
		}
		return sum;
	}
	
	String displayInfo() {
		return "Order: "+orderArray+" Distance: "+ totalDistance + " Fitness: "+fitness;
	}
}
