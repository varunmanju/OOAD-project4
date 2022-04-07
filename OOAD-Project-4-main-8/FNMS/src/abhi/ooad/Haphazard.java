package abhi.ooad;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Haphazard implements Tune{
	public int damages=0;
	
	public Random rand;
	
	private int getRandomInteger (int lowerBound, int upperBound) {
	    return this.rand.nextInt((upperBound - lowerBound) + 1) + lowerBound;
	}


    private void cleanTheStore(Item obj,Clerk s,int idx){
    
                
        	 	 System.out.println(s.name + " breaks " + obj.name +" " + idx);
				 if(obj.condition.level > 1) {
		                obj.damageAnItem(obj);
		               System.out.println("Item list prize is reduced");
		            }
		            else {
		            	System.out.println(obj.name + "removed from the inventory");
		                s.store.inventory.discardedItems.add(obj);
		                s.store.inventory.items.remove(obj);
		                
		            }

        
    }
  


	@Override
	public int tuning(Item obj,int idx,Clerk s)
	
	{	
		//GUITAR ("Guitar"), BASS ("Bass"), MANDOLIN ("Mandolin"), FLUTE ("Flute"), HARMONICA ("Harmonica"), CASSETTE ("Cassette"), RECORDPLAYER ("Record Player"), CDPLAYER ("CD Player"), MP3PLAYER ("MP3 Player"), CASSETTEPLAYER ("Cassette Player"), SAXOPHONE ("Saxophone");
		damages=0;
		this.rand = new Random();
		
		if(getRandomInteger(1, 100) <= 50) {
			if (obj.name.equals("Record Player") || obj.name.equals("CD Player") || obj.name.equals("MP3 Player")|| obj.name.equals("Cassette Player"))
			{
			
			if(obj.equalized==true) {
				
				obj.equalized=false;
				
				if(getRandomInteger(1, 100) <= 10)
				{
				
				cleanTheStore(obj,s,idx);
				damages+=1;
				}
			}
			}
			if(obj.name.equals("Guitar") || obj.name.equals("Bass") || obj.name.equals("Mandolin"))
			{
				
				if(obj.isTuned==true) {
					
					obj.isTuned=false;
					
					if(getRandomInteger(1, 100) <= 10)
					{
					
						cleanTheStore(obj,s,idx);
						damages+=1;
					}
				}
			}
			if(obj.name.equals("Flute") || obj.name.equals("Harmonica") || obj.name.equals("Saxophone"))
			{	
				if(obj.adjusted==true) {
					
					obj.adjusted=false;
					
					if(getRandomInteger(1, 100) <= 10)
					{
					
						cleanTheStore(obj,s,idx);
						damages+=1;
					}
				}
				
			}
			System.out.println("doInventory" + obj.name +" "+ idx + " is tuned from true to false" + " in a Haphazard fashion");
		}
		if(getRandomInteger(1, 100) <= 50) {
			if (obj.name.equals("Record Player") || obj.name.equals("CD Player") || obj.name.equals("MP3 Player")|| obj.name.equals("Cassette Player"))
			{
			
			if(obj.equalized==false) {
				
				obj.equalized=true;
				
				
			}
		
			}
			if(obj.name.equals("Guitar") || obj.name.equals("Bass") || obj.name.equals("Mandolin"))
			{
				
				if(obj.isTuned==false) {
					
					obj.isTuned=true;
					
				
				}
			
			}
			if(obj.name.equals("Flute") || obj.name.equals("Harmonica") ||obj.name.equals("Saxophone"))
			{	
				if(obj.adjusted==false) {
					
					obj.adjusted=true;
					
				
				}
			
			}
			System.out.println("doInventory" + obj.name + " "+ idx + " is tuned from false to true" + " in a Haphazard fashion");
		}
	return damages;
	}
	
	
}