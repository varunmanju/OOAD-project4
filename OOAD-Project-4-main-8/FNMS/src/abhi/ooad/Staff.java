package abhi.ooad;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.Scanner;
import static java.lang.System.in;


public abstract class Staff {
    String name;    // Velma and Shaggy
	Store store;
}

class Clerk extends Staff implements Subscriber {
    int daysWorked;
    String workingAtStore;
    boolean sickToday = false;
    double damageChance;
    Store store;
    int damage=0;
    public Tune tunealgorithm;
    public static ArrayList<Tune> algos = new ArrayList<Tune>() {{
        add(new Haphazard());
        add(new Manual());
        add(new Electronic());
    }};

    Clerk(String name, double damageChance) {
         this.name = name;
         this.damageChance = damageChance;
         daysWorked = 0;
    }

    // notify subscriber & announce
    private void notifyAllSubscribers(String methodName, String message) {
        String newMessage = methodName + " - " + message;
        int day = this.store.today;

        out("Day "+ this.store.today + ": " + newMessage);
        Logger.getInstance().out(newMessage, this.store.storeName, day);
    }

    // notify subscriber & announce v2
    private void notifyAllSubscribers(String methodName, String message, int data, eventType e) {
        String newMessage = methodName + " - " + message;
        int day = this.store.today;
        out("Day "+ this.store.today + ": " + newMessage);

        Logger.getInstance().out(newMessage, this.store.storeName, day);
        Tracker.getInstance(ClerkPool.getInstance().clerkNames).out(this.name, this.store.storeName, e, data);
    }

    void setStoreInstance(Store store) {
        this.store = store;
    }

    void setalgo()
    {
    	int algo= Utility.rndFromRange(0,2);
        this.tunealgorithm=algos.get(algo);
         
    }

    void arriveAtStore() {
        notifyAllSubscribers("ArriveAtStore", this.name + " arrives at " + this.store.storeName + " FNMS Store");
        // have to check for any arriving items slated for this day
        out( this.name + " checking for arriving items.");
        // there's a tricky concurrent removal thing that prevents doing this
        // with a simple for loop - you need to use an iterator
        // https://www.java67.com/2014/03/2-ways-to-remove-elementsobjects-from-ArrayList-java.html#:~:text=There%20are%20two%20ways%20to,i.e.%20remove(Object%20obj).
        Iterator<Item> itr = store.inventory.arrivingItems.iterator();
        int numAdded = 0;
        while (itr.hasNext()) {
            Item item = itr.next();
            if (item.dayArriving == store.today) {
                out( this.name + " putting a " + item.itemType.toString().toLowerCase() + " in inventory.");
                store.inventory.items.add(item);
                numAdded ++;
                itr.remove();
            }
        }
        notifyAllSubscribers("arriveAtStore", "A total of " + numAdded + " items were added to inventory");
    }

    void checkRegister() {
        notifyAllSubscribers("checkRegister", this.name + " finds "+ Utility.asDollar(store.cashRegister)+" in register.");
        if (store.cashRegister<75) {
            out("Cash register is low on funds.");
            this.goToBank();
        }
    }

    void goToBank() {
        out(this.name + " gets money from the bank.");
        store.cashRegister += 1000;
        store.cashFromBank += 1000;
        this.checkRegister();
        notifyAllSubscribers("goToBank", "there is now "+ Utility.asDollar(store.cashRegister)+" in register");
    }

    void doInventory() {
        out(this.name + " is doing inventory.");
        int total_damages=this.tune();
        System.out.println(total_damages);
        for (ItemType type: ItemType.values()) {
            if (type.name().equals("SHIRT") || type.name().equals("BANDANA") || type.name().equals("HAT")) {
                continue;
            }
            int numItems = store.inventory.countByType(store.inventory.items,type);
            out(this.name + " counts "+numItems+" "+type.toString().toLowerCase());
            if (numItems == 0) {
                this.placeAnOrder(type);
            }
        }
        int count = store.inventory.items.size();
        double worth = store.inventory.getValue(store.inventory.items);
        notifyAllSubscribers("doInventory", "There are " + count + " items in the " + this.store.storeName + " store");
        notifyAllSubscribers("doInventory", "There is " + worth + " of items in the " + this.store.storeName + " store");
        notifyAllSubscribers("doInventory", total_damages + " items were damaged during tuning", total_damages, eventType.DAMAGED);
    }

    void placeAnOrder(ItemType type) {
        int numBought = 0;
        out(this.name + " needs to order "+type.toString().toLowerCase());
        // order 3 more of this item type
        // they arrive in 1 to 3 days
        int arrivalDay = Utility.rndFromRange(1,3);
        // check to see if any are in the arriving queue
        int count = store.inventory.countByType(store.inventory.arrivingItems,type);
        if (count>0) {
            out("There is an order coming for " + type.toString().toLowerCase());
        }
        else {
            // order 3 of the missing items if you have the money to buy them
            for (int i = 0; i < 3; i++) {
                Item item = store.inventory.makeNewItemByType(type);
                if (store.cashRegister > item.purchasePrice) {
                    out(this.name + " ordered a " + item.itemType.toString().toLowerCase());
                    item.dayArriving = store.today + arrivalDay;
                    store.inventory.arrivingItems.add(item);
                    numBought ++;
                }
                else {
                    out("Insufficient funds to order this item.");
                }
            }
        }
        notifyAllSubscribers("placeAnOrder", numBought + " items were ordered", numBought, eventType.PURCHASED);
    }

    void openTheStore() {
        int buyers = Utility.rndFromRange(4,10);
        int sellers = Utility.rndFromRange(1,4);
        out(buyers + " buyers, "+sellers+" sellers today.");
        int prevInventory = store.inventory.items.size();
        for (int i = 1; i <= buyers; i++) {
            this.sellAnItem(i, false);
        }
        int numSold = prevInventory - store.inventory.items.size();
        notifyAllSubscribers("openTheStore", numSold + " items were sold", numSold, eventType.SOLD);
        prevInventory = store.inventory.items.size();
        for (int i = 1; i <= sellers; i++) {
            this.buyAnItem(i, false);
        }
        int numBought = (prevInventory - store.inventory.items.size()) * -1;
        notifyAllSubscribers("openTheStore", numBought + " items were purchased", numBought, eventType.PURCHASED);

    }

    void sellAnItem(int customer, boolean interactiveUser) {

        Scanner myObj = new Scanner(in);
        ItemType type;
        String custName = "Buyer "+customer;
        out(this.name+" serving "+custName);

        if(interactiveUser) {
            out("What do you want to buy?");
            type = ItemType.valueOf(myObj.nextLine());
        }
        else {
            type = Utility.randomEnum(ItemType.class);
        }

        out(custName + " wants to buy a "+type.toString().toLowerCase());
        int countInStock = store.inventory.countByType(store.inventory.items, type);
        // if no items - bye
        if (countInStock == 0) {
            out (custName + " doesn't buy, no items in stock.");
        }
        else {
            // pick one of the types of items from inventory
            int pickItemIndex = Utility.rndFromRange(1, countInStock);
            Item item = GetItemFromInventoryByCount(countInStock, type);
            out("Item is " + type.toString().toLowerCase() + " in " + item.condition.toString().toLowerCase() + " condition.");
            // 50% chance to buy at listPrice
            out(this.name + " selling at " + Utility.asDollar(item.listPrice));
            String answer = null;
            if (interactiveUser) {
                out("Do you want to buy?");
                answer = myObj.nextLine();
            }
            if ((interactiveUser == false && Utility.rnd()>.5) || (interactiveUser == true && answer.contentEquals("Yes"))) {
                sellItemtoCustomer(item, custName);
            } else {
                // if not, clerk offers 10% off listPrice
                double newListPrice = item.listPrice * .9;
                out(this.name + " selling at " + Utility.asDollar(newListPrice));
                // now 75% chance of buy
                if (interactiveUser) {
                    out("Do you want to buy?");
                    answer = myObj.nextLine();
                }
                if ((interactiveUser == false && Utility.rnd()>.25) || (interactiveUser == true && answer.contentEquals("Yes"))) {
                    item.listPrice = newListPrice;
                    sellItemtoCustomer(item, custName);
                } else {
                    out(custName + " wouldn't buy item.");
                }
            }
        }
    }

    // things we need to do when an item is sold
    void sellItemtoCustomer(Item item,String custName) {
        String itemName = item.itemType.toString().toLowerCase();
        String price = Utility.asDollar(item.listPrice);
        out (this.name + " is selling "+ itemName + " for " + price +" to "+custName);
        // when sold - move item to sold items with daySold and salePrice noted
        out ( "inventory count: "+store.inventory.items.size());
        store.inventory.items.remove(item);
        out ( "inventory count: "+store.inventory.items.size());
        item.salePrice = item.listPrice;
        item.daySold = store.today;
        store.inventory.soldItems.add(item);
        // money for item goes to register
        store.cashRegister += item.listPrice;
    }

    // find a selected item of a certain type from the items
    Item GetItemFromInventoryByCount(int countInStock, ItemType type) {
        int count = 0;
        for(Item item: store.inventory.items) {
            if (item.itemType == type) {
                count += 1;
                if (count == countInStock) return item;
            }
        }
        return null;
    }

    void buyAnItem(int customer, boolean interactiveUser) {
        Scanner myObj = new Scanner(in);
        ItemType type = null;

        String custName = "Seller "+customer;
        out(this.name+" serving "+custName);

        if(interactiveUser == true) {
            out("What do you want to sell?");
            type = ItemType.valueOf(myObj.nextLine());
        }
        else {
            type = Utility.randomEnum(ItemType.class);
        }
        out(custName + " wants to sell a "+type.toString().toLowerCase());
        Item item = store.inventory.makeNewItemByType(type);
        // clerk will determine new or used, condition, purchase price (based on condition)
        // we'll take the random isNew, condition from the generated item
        out("Item is "+type.toString().toLowerCase()+" in "+item.condition.toString().toLowerCase()+" condition.");
        item.purchasePrice = getPurchasePriceByCondition(item.condition);
        // seller has 50% chance of selling
        out (this.name+" offers "+Utility.asDollar(item.purchasePrice));
        String answer = null;
        if (interactiveUser == true) {
            out("Do you want to sell?");
            answer = myObj.nextLine();
        }
        if ((interactiveUser == false && Utility.rnd()>.5) || (interactiveUser == true && answer.contentEquals("Yes"))) {
            buyItemFromCustomer(item, custName);
        }
        else {
            // if not, clerk will add 10% to purchasePrice
            item.purchasePrice += item.purchasePrice * .10;
            out (this.name+" offers "+Utility.asDollar(item.purchasePrice));
            // seller has 75% chance of selling
            if (interactiveUser == true) {
                out("Do you want to sell?");
                answer = myObj.nextLine();
            }
            if ((interactiveUser == false && Utility.rnd()>.25) || (interactiveUser == true && answer.contentEquals("Yes"))) {
                buyItemFromCustomer(item, custName);
            }
            else {
                out(custName + " wouldn't sell item.");
            }
        }
    }

    void buyItemFromCustomer(Item item, String custName) {
        String itemName = item.itemType.toString().toLowerCase();
        String price = Utility.asDollar(item.purchasePrice);
        out (this.name + " is buying "+ itemName + " for " + price +" from "+custName);
        if (store.cashRegister>item.purchasePrice) {
            store.cashRegister -= item.purchasePrice;
            item.listPrice = 2 * item.purchasePrice;
            item.dayArriving = store.today;
            store.inventory.items.add(item);
        }
        else {
            out(this.name + "cannot buy item, register only has "+Utility.asDollar(store.cashRegister));
        }
    }


    double getPurchasePriceByCondition(Condition condition) {
        int lowPrice = 2*condition.level;
        int highPrice = 10*condition.level;
        return (double) Utility.rndFromRange(lowPrice,highPrice);
    }


    void cleanTheStore() {
        out(this.name + " is cleaning up the store.");
        if (Utility.rnd()>this.damageChance) {
            out(this.name + " doesn't break anything.");
        }
        else if (store.inventory.items.size() > 0) {
            // reduce the condition for a random item
            int pickItemIndex = Utility.rndFromRange(0,store.inventory.items.size()-1);
            Item item = store.inventory.items.get(pickItemIndex);
            notifyAllSubscribers("cleanTheStore", this.name + " breaks something!", 1, eventType.DAMAGED);
            if(item.condition.level > 1) {
                item.damageAnItem(item);
            }
            else {
                // take the item off the main inventory and put it on the broken items ArrayList
                store.inventory.discardedItems.add(item);
                store.inventory.items.remove(item);
            }
        }
        else {
            out(this.name + " nothing to break. Inventory has no items.");
        }
    }
    void leaveTheStore() {
        notifyAllSubscribers("leaveTheStore", this.name + " locks up the " + this.store.storeName + " store and leaves.");
        ClerkPool clerkPool = ClerkPool.getInstance();
        Iterator<Clerk> itr = clerkPool.clerks.iterator();
        while (itr.hasNext()) {
            Clerk clerk = itr.next();
            if (clerk.workingAtStore == store.storeName)
                clerk.workingAtStore = null;
        }
        out("");
        Tracker.getInstance(ClerkPool.getInstance().clerkNames).clerkDataSummary(store.storeName);
    }

    public int dotuning(Item obj,int idx){
        return this.tunealgorithm.tuning(obj,idx,this);
    }

     private int tune() {
	  	ArrayList<Item> items = (ArrayList<Item>)store.inventory.items.clone();
    	int dam;
    	this.damage=0;
    	 System.out.println(""+this.name);
    	 
    	  for (Itemtype2 type: Itemtype2.values()) {
    		 
               int numItems = store.inventory.countByType2(items,type);
               
               if(numItems>0) {
            	   int count = 0;
            	   for(Item item: items) {
            		   
            		   if(item.itemType.getName()==type.getName()) {
            			   count += 1;
            			   dam = this.dotuning(item,count);
            			   this.damage+=dam;
            		   }
            	   }
               }
    	  }
    	  return damage;
  }

  public class AbstractguitarkitA extends Abstractguitarkit{
  	
  	initialize item=new initialize();
  	public AbstractguitarkitA() {
  		
  		item.populate("Southside");
  	}
  	public GuitarKit createGuitar() {
  		int price=0;
  		bridge b = null;
  		pickguard g = null;
  		pickups p = null;
  		knobset k = null;
  		covers c = null;
  		neck n = null;
  		System.out.println("Pickup A price "+ item.p1.get(0).getprice());
  		System.out.println("Pickup B price "+ item.p1.get(1).getprice());
  		System.out.println("Pickup C price "+ item.p1.get(2).getprice());
  		System.out.println("enter the choice Southside items for pickups Pickup A or Pickup B or Pickup C");
  		
  		Scanner scan = new Scanner(System.in);
  		String s = scan.nextLine();
  		if(s.equals("Pickup A")) {
  		 p=item.p1.get(0);
  		price+=p.getprice();
  	}
  		if(s.equals("Pickup B")) {
  			 p=	item.p1.get(1);
  			price+=p.getprice();
      	}
  		if(s.equals("Pickup C")) {
  			p=item.p1.get(2);
      		price+=p.getprice();
      	}
  		System.out.println("Knobset A price "+ item.k1.get(0).getprice());
  		System.out.println("Knobset B price "+ item.k1.get(1).getprice());
  		System.out.println("Knobset C price "+ item.k1.get(2).getprice());
  		System.out.println("enter the choice Southside items for knobset Knobset A or Knobset B or Knobset C");
  		Scanner scan1 = new Scanner(System.in);
  		String s1 = scan1.nextLine();
  		if(s1.equals("Knobset A")) {
  			 k=item.k1.get(0);
  			price+=k.getprice();
  		}
  		if(s1.equals("Knobset B")) {
  			 k=item.k1.get(1);
  			price+=k.getprice();
      	}
  		if(s1.equals("Knobset C")) {
  			 k=item.k1.get(2);
  			price+=k.getprice();
      		}
  		System.out.println("Covers A price "+ item.c1.get(0).getprice());
  		System.out.println("Covers B price "+ item.c1.get(1).getprice());
  		System.out.println("Covers C price "+ item.c1.get(2).getprice());
  		System.out.println("enter the choice Southside items for Covers Covers A or Covers B or Covers C");
  		Scanner scan2 = new Scanner(System.in);
  		String s2 = scan2.nextLine();
  		if(s2.equals("Covers A")) {
  			 c=item.c1.get(0);
  			price+=c.getprice();
  		}
  		if(s2.equals("Covers B")) {
      		 c=item.c1.get(1);
      		price+=c.getprice();
      	}
  		if(s2.equals("Covers C")) {
  			 c=item.c1.get(2);
  			price+=c.getprice();
      		}
  		System.out.println("Neck A price "+ item.n1.get(0).getprice());
  		System.out.println("Neck B price "+ item.n1.get(1).getprice());
  		System.out.println("Neck C price "+ item.n1.get(2).getprice());
  		System.out.println("enter the choice Southside items for Neck Neck A or Neck B or Neck C");
  		Scanner scan3 = new Scanner(System.in);
  		String s3 = scan3.nextLine();
  		if(s3.equals("Neck A")) {
  			 n=item.n1.get(0);
  			price+=n.getprice();
  		}
  		if(s3.equals("Neck B")) {
  			 n=item.n1.get(1);
  			price+=n.getprice();
      	}
  		if(s3.equals("Neck C")) {
  			 n=item.n1.get(2);
  			price+=n.getprice();
      		}
  		System.out.println("Pickguard A price "+ item.g1.get(0).getprice());
  		System.out.println("Pickguard B price "+ item.g1.get(1).getprice());
  		System.out.println("Pickguard C price "+ item.g1.get(2).getprice());
  		System.out.println("enter the choice Southside items for Pickguard Pickguard A or Pickguard B or Pickguard C");
  		Scanner scan4 = new Scanner(System.in);
  		String s4 = scan4.nextLine();
  		if(s4.equals("Pickguard A")) {
  			g=item.g1.get(0);
  			price+=g.getprice();
  		}
  		if(s4.equals("Pickguard B")) {
  			 g=item.g1.get(1);
  			price+=g.getprice();
      	}
  		if(s4.equals("Pickguard C")) {
  			 g=item.g1.get(2);
  			price+=g.getprice();
      		}
  		System.out.println("Bridge A price "+ item.b1.get(0).getprice());
  		System.out.println("Bridge B price "+ item.b1.get(1).getprice());
  		System.out.println("Bridge C price "+ item.b1.get(2).getprice());
  		System.out.println("enter the choice Southside items for Bridge Bridge A or Bridge B or Bridge C");
  		Scanner scan5 = new Scanner(System.in);
  		String s5 = scan5.nextLine();
  		if(s5.equals("Bridge A")) {
  			b=item.b1.get(0);
  			price+=b.getprice();
  		}
  		if(s5.equals("Bridge B")) {
  			b=item.b1.get(1);
  			price+=b.getprice();
  			}
  		if(s5.equals("Bridge C")) {
  			b=item.b1.get(2);
  			price+=b.getprice();
      		}
  		
  		
			GuitarKit guitar=new GuitarKit("Guitar",price,b,k,c,n,g,p);
  		return guitar;
  }
  }
  public class AbstractguitarkitB extends Abstractguitarkit{
  	initialize item=new initialize();
  	public AbstractguitarkitB() {
  		
  		item.populate("Northside");
  	}
  	public GuitarKit createGuitar() {
  		int price=0;
  		bridge b = null;
  		pickguard g = null;
  		pickups p = null;
  		knobset k = null;
  		covers c = null;
  		neck n = null;
  		System.out.println("enter the choice Northside items for pickups Pickup A or Pickup B or Pickup C");
  		Scanner scan = new Scanner(System.in);
  		System.out.println("Pickup A price "+ item.p2.get(0).getprice());
  		System.out.println("Pickup B price "+ item.p2.get(1).getprice());
  		System.out.println("Pickup C price "+ item.p2.get(2).getprice());
  		String s = scan.nextLine();
  		if(s.equals("Pickup A")) {
  		 p=item.p2.get(0);
  		price+=p.getprice();
  	}
  		if(s.equals("Pickup B")) {
  			 p=	item.p2.get(1);
  			price+=p.getprice();
      	}
  		if(s.equals("Pickup C")) {
  			p=item.p2.get(2);
      		price+=p.getprice();
      	}
  		System.out.println("enter the choice Northside items for knobset Knobset A or Knobset B or Knobset C");
  		System.out.println("Knobset A price "+ item.k2.get(0).getprice());
  		System.out.println("Knobset B price "+ item.k2.get(1).getprice());
  		System.out.println("Knobset C price "+ item.k2.get(2).getprice());
  		Scanner scan1 = new Scanner(System.in);
  		String s1 = scan1.nextLine();
  		if(s1.equals("Knobset A")) {
  			 k=item.k2.get(0);
  			price+=k.getprice();
  		}
  		if(s1.equals("Knobset B")) {
  			 k=item.k2.get(1);
  			price+=k.getprice();
      	}
  		if(s1.equals("Knobset C")) {
  			 k=item.k2.get(2);
  			price+=k.getprice();
      		}
  		System.out.println("enter the choice Northside items for Covers Covers A or Covers B or Covers C");
  		System.out.println("Covers A price "+ item.c2.get(0).getprice());
  		System.out.println("Covers B price "+ item.c2.get(1).getprice());
  		System.out.println("Covers C price "+ item.c2.get(2).getprice());
  		Scanner scan2 = new Scanner(System.in);
  		String s2 = scan2.nextLine();
  		if(s2.equals("Covers A")) {
  			 c=item.c2.get(0);
  			price+=c.getprice();
  		}
  		if(s2.equals("Covers B")) {
      		 c=item.c2.get(1);
      		price+=c.getprice();
      	}
  		if(s2.equals("Covers C")) {
  			 c=item.c2.get(2);
  			price+=c.getprice();
      		}
  		System.out.println("enter the choice Northside items for Neck Neck A or Neck B or Neck C");
  		System.out.println("Neck A price "+ item.n2.get(0).getprice());
  		System.out.println("Neck B price "+ item.n2.get(1).getprice());
  		System.out.println("Neck C price "+ item.n2.get(2).getprice());
  		Scanner scan3 = new Scanner(System.in);
  		String s3 = scan3.nextLine();
  		if(s3.equals("Neck A")) {
  			 n=item.n2.get(0);
  			price+=n.getprice();
  		}
  		if(s3.equals("Neck B")) {
  			 n=item.n2.get(1);
  			price+=n.getprice();
      	}
  		if(s3.equals("Neck C")) {
  			 n=item.n2.get(2);
  			price+=n.getprice();
      		}
  		System.out.println("enter the choice Northside items for Pickguard Pickguard A or Pickguard B or Pickguard C");
  		System.out.println("Pickguard A price "+ item.g2.get(0).getprice());
  		System.out.println("Pickguard B price "+ item.g2.get(1).getprice());
  		System.out.println("Pickguard C price "+ item.g2.get(2).getprice());
  		Scanner scan4 = new Scanner(System.in);
  		String s4 = scan4.nextLine();
  		if(s4.equals("Pickguard A")) {
  			g=item.g2.get(0);
  			price+=g.getprice();
  		}
  		if(s4.equals("Pickguard B")) {
  			 g=item.g2.get(1);
  			price+=g.getprice();
      	}
  		if(s4.equals("Pickguard C")) {
  			 g=item.g2.get(2);
  			price+=g.getprice();
      		}
  		System.out.println("enter the choice Northside items for Bridge Bridge A or Bridge B or Bridge C");
  		System.out.println("Bridge A price "+ item.b2.get(0).getprice());
  		System.out.println("Bridge B price "+ item.b2.get(1).getprice());
  		System.out.println("Bridge C price "+ item.b2.get(2).getprice());
  		Scanner scan5 = new Scanner(System.in);
  		String s5 = scan5.nextLine();
  		if(s5.equals("Bridge A")) {
  			b=item.b2.get(0);
  			price+=b.getprice();
  		}
  		if(s5.equals("Bridge B")) {
  			b=item.b2.get(1);
  			price+=b.getprice();
  			}
  		if(s5.equals("Bridge C")) {
  			b=item.b2.get(2);
  			price+=b.getprice();
      		}
  		
  		
			GuitarKit guitar=new GuitarKit("Guitar",price,b,k,c,n,g,p);
  		return guitar;
  }
  }
  public Item client_code(Abstractguitarkit g) {
  		Item guitar=g.createGuitar();
        store.inventory.soldItems.add(guitar);
        return guitar;
  }
  public void create(){
        Item guitar;
        if(this.store.storeName.equals("Southside")) {
            System.out.println("Southside uses factory A");
            guitar = client_code(new AbstractguitarkitA());
        }
        else {
            guitar = client_code(new AbstractguitarkitB());
        }
  	    out("Customer bought the Guitar kit for price: " + guitar.purchasePrice);
  }
}
        
