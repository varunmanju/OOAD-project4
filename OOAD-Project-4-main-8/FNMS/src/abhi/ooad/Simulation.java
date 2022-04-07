package abhi.ooad;

import java.util.ArrayList;
import java.util.Scanner;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

import static java.lang.System.in;


// top level object to run the simulation
public class Simulation implements Subscriber {
    Store store_northside;
    Store store_southside;
    int dayCounter;
    Weekday weekDay;
    ArrayList<Double> xData = new ArrayList<>();
	ArrayList<Integer> yData = new ArrayList<>();
	ArrayList<Double> xData1 = new ArrayList<>();
	ArrayList<Double> xData2 = new ArrayList<>();
	ArrayList<Double> xData3 = new ArrayList<>();
	ArrayList<Double> xData4 = new ArrayList<>();
	ArrayList<Integer> xData5 = new ArrayList<>();
	ArrayList<Integer> xData6 = new ArrayList<>();
	ArrayList<Integer> xData7 = new ArrayList<>();
	ArrayList<Integer> xData8 = new ArrayList<>();
	ArrayList<Integer> xData9 = new ArrayList<>();
	ArrayList<Integer> xData10 = new ArrayList<>();
	//Graph the sales of the total register value everyday and item sales everyday.
    public void graph(ArrayList<Integer> yData,ArrayList<Double> xData1,ArrayList<Double> xData2,String storename) {
    	final XYChart chart = new XYChartBuilder().width(600).height(400).title("Value of item in store " + storename).xAxisTitle("Total cost").yAxisTitle("Day number").build();

    	

    	// Series
    	chart.addSeries("Total register", yData, xData1);
    	chart.addSeries("Item sales", yData, xData2);
    	// Schedule a job for the event-dispatching thread:
    	// creating and showing this application's GUI.
    	new SwingWrapper(chart).displayChart();
    	
    }
    public void graph1(ArrayList<Integer> yData,ArrayList<Integer> xData1,ArrayList<Integer> xData2,ArrayList<Integer> xData3,String storename) {
    	final XYChart chart = new XYChartBuilder().width(600).height(400).title("Count of item in store " + storename).xAxisTitle("Total count").yAxisTitle("Day number").build();

    	

    	// Series
    	chart.addSeries("Total count of items in inventory", yData, xData1);
    	chart.addSeries("Damaged items", yData, xData2);
    	chart.addSeries("Items sold", yData, xData3);
    	// Schedule a job for the event-dispatching thread:
    	// creating and showing this application's GUI.
    	new SwingWrapper(chart).displayChart();
    	
    }
    // enum for Weekdays
    // next implementation from
    // https://stackoverflow.com/questions/17006239/whats-the-best-way-to-implement-next-and-previous-on-an-enum-type
    public enum Weekday {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
        private static final Weekday[] vals = values();
        public Weekday next() {
            return vals[(this.ordinal()+1) % vals.length];
        }
    }

    Simulation() {
        Logger.getInstance().deleteOldFiles(50, "Northside");
        Logger.getInstance().deleteOldFiles(50, "Southside");
        weekDay = Weekday.MONDAY;   //set the starting day
        dayCounter = 0;
        store_northside = new Store("Northside");
        store_southside = new Store("Southside");
    }

    void startSim(int days) {
        for (int day = 1; day <= days; day++) {
            out(" ");
            out("*** Simulation day "+day+" ***");
            startDay(day);
        }
	//The interactive mode where the user interacts with the clerk.This is where command pattern is used.
        Scanner myObj = new Scanner(in);
        out("Choose a store: 1->FNMS Northside    2->FNMS Southside");
        int storeNum = myObj.nextInt();
        Store store = (storeNum == 1) ? store_northside : store_southside;
        out("Welcome to Store FNMS " + store.storeName);
        while(true) {
            myObj = new Scanner(in);
            String userInput = myObj.nextLine();
            if (userInput.contains("Name")) {
                store.interactiveUserClerkName();
            } else if (userInput.contains("Time")) {
                store.interactiveUserClerkTime(days + 1);
            } else if (userInput.contains("Sell")) {
                store.interactiveUserBuy();
            } else if (userInput.contains("Buy")) {
                store.interactiveUserSell();
            } else if (userInput.contains("Guitar Kit")) {
                store.interactiveUserGuitarKit();
            } else if (userInput.contains("Toggle")) {
                store = (store.storeName == "Southside") ? store_northside : store_southside;
                out("Welcome to Store FNMS " + store.storeName);
            } else {
                break;
            }
        }
        store_northside.interactiveUserEndOfDay();
        store_southside.interactiveUserEndOfDay();
        summary();
    }

    void startDay(int day) {
        if (weekDay == Weekday.SUNDAY) {
            store_northside.closedToday(day);
            store_southside.closedToday(day);
            yData.add(day);
            xData1.add(store_southside.cashRegister);
            xData2.add(store_southside.inventory.getValue(store_southside.inventory.soldItems));
            xData3.add(store_northside.cashRegister);
            xData4.add(store_northside.inventory.getValue(store_northside.inventory.soldItems));
            xData5.add(store_southside.inventory.countitems(store_southside.inventory.items));
            xData6.add(store_southside.inventory.countitems(store_southside.inventory.discardedItems));
            xData7.add(store_southside.inventory.countitems(store_southside.inventory.soldItems));
            xData8.add(store_northside.inventory.countitems(store_northside.inventory.items));
            xData9.add(store_northside.inventory.countitems(store_northside.inventory.discardedItems));
            xData10.add(store_northside.inventory.countitems(store_northside.inventory.soldItems));
            
        }
        else {
            store_northside.openToday(day);
            store_southside.openToday(day);
            yData.add(day);
            xData1.add(store_southside.cashRegister);
            xData2.add(store_southside.inventory.getValue(store_southside.inventory.soldItems));
            xData3.add(store_northside.cashRegister);
            xData4.add(store_northside.inventory.getValue(store_northside.inventory.soldItems));
            xData5.add(store_southside.inventory.countitems(store_southside.inventory.items));
            xData6.add(store_southside.inventory.countitems(store_southside.inventory.discardedItems));
            xData7.add(store_southside.inventory.countitems(store_southside.inventory.soldItems));
            xData8.add(store_northside.inventory.countitems(store_northside.inventory.items));
            xData9.add(store_northside.inventory.countitems(store_northside.inventory.discardedItems));
            xData10.add(store_northside.inventory.countitems(store_northside.inventory.soldItems));
        }
        weekDay = weekDay.next();
        ClerkPool.getInstance().endOfDay(store_northside, store_southside);
    }

    void summary() {
    	
        out("##########################################################");
        out("                          Summary:                        ");
        out("##########################################################");
        //Northside store summary
        out("Northside Store summary");
        out("Items In Inventory:");
        for(Item i: store_northside.inventory.items){
        	if(i.name1!=null && i.name1.equals("Guitar")) {
        		out(i.name1);
        		continue;
        	}
            out(i.itemType.getName());
        }
        out("Total Inventory Value: " + store_northside.inventory.getValue(store_northside.inventory.items));
        out("");
        out("Items Sold During Simulation:");
        for(Item i: store_northside.inventory.soldItems){
        	if(i.name1!=null && i.name1.equals("Guitar")) {
        		out("Name: " + i.name1 + " Day Sold: " + i.daySold + " Sale Price: " + i.salePrice);
        		continue;
        	}
            out("Name: " + i.itemType.getName() + " Day Sold: " + i.daySold + " Sale Price: " + i.salePrice);
        }
        out("Total Sales Value: " + store_northside.inventory.getValue(store_northside.inventory.soldItems));
        out("");
        out("Total Value In Cash Register: " + store_northside.cashRegister);
        out("Total $ Added from goToBank: " + store_northside.cashFromBank);
        
        //Southside store summary
        out("Southside Store summary");
        out("Items In Inventory:");
        for(Item i: store_southside.inventory.items){
        	if(i.name1!=null && i.name1.equals("Guitar")) {
        		out(i.name1);
        		continue;
        	}
            out(i.itemType.getName());
        }
        out("Total Inventory Value: " + store_southside.inventory.getValue(store_southside.inventory.items));
        out("");
        out("Items Sold During Simulation:");
        for(Item i: store_southside.inventory.soldItems){
        	if(i.name1!=null && i.name1.equals("Guitar")) {
        		out("Name: " + i.name1 + " Day Sold: " + i.daySold + " Sale Price: " + i.salePrice);
        		continue;
        	}
        	
            out("Name: " + i.itemType.getName() + " Day Sold: " + i.daySold + " Sale Price: " + i.salePrice);
        }
        out("Total Sales Value: " + store_southside.inventory.getValue(store_southside.inventory.soldItems));
        out("");
        out("Total Value In Cash Register: " + store_southside.cashRegister);
        out("Total $ Added from goToBank: " + store_southside.cashFromBank);
       //For Southside store graph total register vale and items sold everyday
        graph(yData,xData1,xData2,"Southside");
      //For Northside store graph total register vale and items sold everyday
        graph(yData,xData3,xData4,"Northside");
        //For Southside store get items count in inventory,items damaged and items sold
        graph1(yData,xData5,xData6,xData7,"Southside");
        //For Northsidestore get items count in inventory,items damaged and items sold
        graph1(yData,xData8,xData9,xData10,"Northside");
    }
}
