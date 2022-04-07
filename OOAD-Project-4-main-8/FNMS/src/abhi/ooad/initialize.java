package abhi.ooad;

import java.util.ArrayList;

public class initialize {
		public ArrayList<bridge> b1=new ArrayList<>();
	    public ArrayList<bridge> b2=new ArrayList<>();
	    public ArrayList<knobset> k1=new ArrayList<>();
	    public ArrayList<knobset> k2=new ArrayList<>();
	    public ArrayList<covers> c1=new ArrayList<>();
	    public ArrayList<covers> c2=new ArrayList<>();
	    public ArrayList<neck> n1=new ArrayList<>();
	    public ArrayList<neck> n2=new ArrayList<>();
	    public ArrayList<pickguard> g1=new ArrayList<>();
	    public ArrayList<pickguard> g2=new ArrayList<>();
	    public ArrayList<pickups> p1=new ArrayList<>();
	    public ArrayList<pickups> p2=new ArrayList<>();
	    public void populate(String name) {
	    	if(name.equals("Southside")) {
	    		for(int i=0;i<3;i++) {
	    			p1.add(new PickupsA());
	    		}
	    		for(int i=0;i<3;i++) {
	    			g1.add(new PickguardA());
	    		}
	    		for(int i=0;i<3;i++) {
	    			n1.add(new NeckA());
	    		}
	    		for(int i=0;i<3;i++) {
	    			c1.add(new CoversA());
	    		}
	    		for(int i=0;i<3;i++) {
	    			k1.add(new KnobsetA());
	    		}
	    		for(int i=0;i<3;i++) {
	    			b1.add(new BridgeA());
	    		}
	    	}
	    	if(name.equals("Northside")) {
	    		for(int i=0;i<3;i++) {
	    			p2.add(new PickupsB());
	    		}
	    		for(int i=0;i<3;i++) {
	    			g2.add(new PickguardB());
	    		}
	    		for(int i=0;i<3;i++) {
	    			n2.add(new NeckB());
	    		}
	    		for(int i=0;i<3;i++) {
	    			c2.add(new CoversB());
	    		}
	    		for(int i=0;i<3;i++) {
	    			k2.add(new KnobsetB());
	    		}
	    		for(int i=0;i<3;i++) {
	    			b2.add(new BridgeB());
	    		}
	    	}
	    	
	    }
}
