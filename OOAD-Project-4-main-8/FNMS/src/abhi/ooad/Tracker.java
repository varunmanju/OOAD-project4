package abhi.ooad;
import java.util.*;

public class Tracker implements Subscriber{
    subscriberType type;
    Hashtable<String, Hashtable<eventType, Integer>> staffDataNorth;
    Hashtable<String, Hashtable<eventType, Integer>> staffDataSouth;
    private static Tracker instance;

    private Tracker(ArrayList<String> names){
        staffDataNorth = new Hashtable<>();
        staffDataSouth = new Hashtable<>();
        type = subscriberType.TRACKER;
        Hashtable<eventType, Integer> inputN;
        Hashtable<eventType, Integer> inputS;
        for(String name: names){
            inputN = new Hashtable<eventType, Integer>();
            inputS = new Hashtable<eventType, Integer>();
            inputN.put(eventType.PURCHASED, 0);
            inputS.put(eventType.PURCHASED, 0);
            inputN.put(eventType.SOLD, 0);
            inputS.put(eventType.SOLD, 0);
            inputN.put(eventType.DAMAGED, 0);
            inputS.put(eventType.DAMAGED, 0);
            staffDataNorth.put(name, inputN);
            staffDataSouth.put(name, inputS);
        }
    }

    public static synchronized Tracker getInstance(ArrayList<String> names){
        if(instance == null){
            instance = new Tracker(names);
        }
        return instance;
    }


    //update clerk data
    public void out(String clerk, String store, eventType e, Integer data){
        if(store.equals("Northside")){
            int newValue = data + staffDataNorth.get(clerk).get(e);
            staffDataNorth.get(clerk).put(e, newValue);
        }
        else{
            int newValue = data + staffDataSouth.get(clerk).get(e);
            staffDataSouth.get(clerk).put(e, newValue);
        }
    }

    //print out table of clerk data
    public void clerkDataSummary(String storeName){
        Hashtable<String, Hashtable<eventType, Integer>> staffData;
        if (storeName.equals("Northside")){
            staffData = staffDataNorth;
        }
        else{
            staffData = staffDataSouth;
        }
        Enumeration<String> names = staffData.keys();
        Object curr;
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("%11s %12s %17s %15s", "Clerk", "Items Sold", "Items Purchased", "Items Damaged");
        System.out.println();
        System.out.println("-------------------------------------------------------------------");
        while(names.hasMoreElements()){
            curr = names.nextElement();
            System.out.printf("%11s %12d %17d %15d", curr, staffData.get(curr).get(eventType.SOLD), staffData.get(curr).get(eventType.PURCHASED), staffData.get(curr).get(eventType.DAMAGED));
            System.out.println();
        }

        System.out.println("-------------------------------------------------------------------");
    }
}