package abhi.ooad;

import java.util.Random;

public abstract class knobset{
	public abstract int getprice();
	public abstract int getRandomInteger(int lowerBound, int upperBound);
	
}
class KnobsetA extends knobset {
public int price;
public Random rand;
public int getRandomInteger (int lowerBound, int upperBound) {
    return this.rand.nextInt((upperBound - lowerBound) + 1) + lowerBound;
}
public KnobsetA()
{
	this.rand=new Random();
	this.price=getRandomInteger(1,50);
	
}
public int getprice() {
	return this.price;
	
}
}

class KnobsetB extends knobset {
public int price;
public Random rand;
public int getRandomInteger (int lowerBound, int upperBound) {
    return this.rand.nextInt((upperBound - lowerBound) + 1) + lowerBound;
}
public KnobsetB()
{
	this.rand=new Random();
	this.price=getRandomInteger(1,50);
	
}
public int getprice() {
	return this.price;
	
}
}