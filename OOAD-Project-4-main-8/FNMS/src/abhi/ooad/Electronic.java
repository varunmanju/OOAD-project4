package abhi.ooad;

import static java.lang.System.out;

import java.util.Random;
public class Electronic implements Tune{
	private Random rand;
	public Store store1;

    @Override
	public int tuning(Item obj,int idx,Clerk s)
	{
			
			this.rand = new Random();
			
			if (obj.name.equals("Record Player") || obj.name.equals("CD Player") || obj.name.equals("MP3 Player")|| obj.name.equals("Cassette Player"))
			{
				
			if(obj.equalized==false) {
			
				obj.equalized=true;
			
				System.out.println("doInventory"+ obj.name + " "+ idx +" is tuned from false to true" + " Electronically");
			
			}
			}
			if(obj.name.equals("Guitar") || obj.name.equals("Bass") || obj.name.equals("Mandolin"))
			{
				
				if(obj.isTuned ==false) {
				
					obj.isTuned=true;
				
					
				}
				System.out.println("doInventory"+ obj.name + " "+ idx +" is tuned from false to true" + " Electronically");
			}
			if(obj.name.equals("Flute") || obj.name.equals("Harmonica") || obj.name.equals("Saxophone"))
			{
				
				if(obj.adjusted==false) {
				
					obj.adjusted=true;
					
					
				}
				System.out.println("doInventory"+ obj.name + " "+ idx +" is tuned from false to true" + " Electronically");
			}
		
			return 0;
		}
    
	}


