package abhi.ooad;

import java.util.Random;

public abstract class pickups{
	public abstract int getprice();
	public abstract int getRandomInteger(int lowerBound, int upperBound);
	
}
class PickupsA extends pickups {
public int price;
public Random rand;
public int getRandomInteger (int lowerBound, int upperBound) {
    return this.rand.nextInt((upperBound - lowerBound) + 1) + lowerBound;
}
public PickupsA()
{
	this.rand=new Random();
	this.price=getRandomInteger(1,50);
	
}
public int getprice() {
	return this.price;
	
}
}

class PickupsB extends pickups {
public int price;
public Random rand;
public int getRandomInteger (int lowerBound, int upperBound) {
    return this.rand.nextInt((upperBound - lowerBound) + 1) + lowerBound;
}
public PickupsB()
{
	this.rand=new Random();
	this.price=getRandomInteger(1,50);
	
}
public int getprice() {
	return this.price;
	
}
}
