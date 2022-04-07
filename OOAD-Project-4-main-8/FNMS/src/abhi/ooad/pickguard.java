package abhi.ooad;

import java.util.Random;

public abstract class pickguard{
	public abstract int getprice();
	public abstract int getRandomInteger(int lowerBound, int upperBound);
	
}
class PickguardA extends pickguard {
public int price;
public Random rand;
public int getRandomInteger (int lowerBound, int upperBound) {
    return this.rand.nextInt((upperBound - lowerBound) + 1) + lowerBound;
}
public PickguardA()
{
	this.rand=new Random();
	this.price=getRandomInteger(1,50);
	
}
public int getprice() {
	return this.price;
	
}
}

class PickguardB extends pickguard {
public int price;
public Random rand;
public int getRandomInteger (int lowerBound, int upperBound) {
    return this.rand.nextInt((upperBound - lowerBound) + 1) + lowerBound;
}
public PickguardB()
{
	this.rand=new Random();
	this.price=getRandomInteger(1,50);
	
}
public int getprice() {
	return this.price;
	
}
}