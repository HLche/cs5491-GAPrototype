
public class city {
	private String cityName;
	private double positionX;
	private double positionY;
	
	city(String cityName, double X, double Y){
		this.cityName=cityName;
		this.positionX=X;
		this.positionY=Y;
	}
	
	String getCityName(){
		return cityName;
	}
	
	double getPosX(){
		return positionX;
	}
	double getPosY(){
		return positionY;
	}

	String displayInfo() {
		return "City Name: "+cityName+" positionX: "+positionX+" positionY: "+positionY;
	}
}
