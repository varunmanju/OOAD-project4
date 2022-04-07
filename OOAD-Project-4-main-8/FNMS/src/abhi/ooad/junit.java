package abhi.ooad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class junit {

	Clerk s;

	public junit() throws NoSuchFieldException {
        this.s = new Clerk("Shaggy",0.10);
        this.s.setStoreInstance(new Store("North Side"));
	}

    @Test
    @DisplayName("Inventory Initialization")
    void inventoryInitialization(){
        assertTrue(this.s.store.inventory.items.size() == 63);
    
    }

    @Test
    @DisplayName("Bank Visit")
   void gotobank() {
        double amount = this.s.store.cashRegister;
            this.s.goToBank();
            assertTrue(this.s.store.cashRegister == amount + 1000);
        }
    
    @Test
    @DisplayName("Add item to inventory")
    void addItem() {
    	int inventorysize = this.s.store.inventory.items.size();
        this.s.store.inventory.items.add(this.s.store.inventory.makeNewItemByType(ItemType.PAPERSCORE));
        
        assertTrue(this.s.store.inventory.items.size() == inventorysize+1);
       
    } 
    @Test
    @DisplayName("Sell to Customer")
    void sellToCustomer() throws NoSuchFieldException {
        Map<String, List<Item>> inv = new HashMap<>();
        int invBefore = 0;
        int invAfter = 0;
        double cashRegister = this.s.store.cashRegister;

        this.s.store.activeClerk = this.s.store.getValidClerk();
        assertTrue(this.s.store.inventory.soldItems.isEmpty());
        
       invBefore += this.s.store.inventory.items.size();
        
        this.s.sellAnItem(1, false);
      
       invAfter += this.s.store.inventory.items.size();
        
        if (invAfter == invBefore - 1) {
            assertTrue(!this.s.store.inventory.soldItems.isEmpty());
            assertTrue(cashRegister + this.s.store.inventory.soldItems.get(0).salePrice == this.s.store.cashRegister);
        }
      
    }
    @Test
    @DisplayName("Buy from Customer")
    void buyFromCustomer() throws NoSuchFieldException {
        Map<String, List<Item>> inv = new HashMap<>();
        int invBefore = 0;
        int invAfter = 0;
        double cashRegister = this.s.store.cashRegister;

        this.s.store.activeClerk = this.s.store.getValidClerk();
        assertTrue(this.s.store.inventory.items.size() ==63);
        invBefore += this.s.store.inventory.items.size();
        this.s.buyAnItem(2, false);
        invAfter += this.s.store.inventory.items.size();
        if(invAfter == invBefore+1) {
            assertTrue((this.s.store.inventory.items.size()==62));
            assertTrue(cashRegister>this.s.store.cashRegister);
        }
    }
    @Test
    @DisplayName("Inventory populate for abstract guitar factory")
    void abstractguitar() throws NoSuchFieldException {
        initialize inv=new initialize();
        inv.populate("Southside");
        inv.populate("Northside");
        assertTrue(inv.b1.size()==3);
        		
    }
    @Test
    @DisplayName("Damage an item where the item price is reduced")
    void damage() throws NoSuchFieldException {
    	Item i=new GigBag();
    	double listprice=i.listPrice;
        i.condition = Condition.EXCELLENT;
    	i.damageAnItem(i);
    	assertTrue(i.listPrice<=listprice);
    }
    @Test
    @DisplayName("Check the item count of a particular type in the arraylist")
    void counttype() throws NoSuchFieldException {
    	ArrayList<Item> list = new ArrayList<Item>();
    	list.add(new GigBag());
    	list.add(new Guitar());
    	list.add(new GigBag());
    	Inventory i=new Inventory();
    	assertTrue(i.countByType(list, ItemType.GIGBAG)==2) ;
    	 
    }
    @Test
    @DisplayName("Get value of items in the inventory")
    void totalprice() throws NoSuchFieldException {
    	ArrayList<Item> list = new ArrayList<Item>();
    	list.add(new GigBag());
    	list.add(new Guitar());
    	list.add(new GigBag());
    	Inventory i=new Inventory();
    	int purchaseprice=0;
    	for(Item it: list) {
    		purchaseprice+=it.purchasePrice;
    	}
    	assertTrue(i.getValue(list)==purchaseprice) ;
    }

    @Test
    @DisplayName("Initialize Clerk Pool - Singleton")
    void clerkPool() {
        ClerkPool instance1 = ClerkPool.getInstance();
        ClerkPool instance2 = ClerkPool.getInstance();
        ClerkPool instance3 = ClerkPool.getInstance();
        assertTrue(instance1 == instance2 && instance2 == instance3);
    }

    @Test
    @DisplayName("Get Valid Clerk")
    void getValidClerk() {
        Store store_northside = new Store("Northside");
        Store store_southside = new Store("Southside");
        System.out.println(store_northside.getValidClerk().name);
        System.out.println(store_southside.getValidClerk().name);
        assert(store_northside.getValidClerk().name != store_southside.getValidClerk().name);
    }

    @Test
    @DisplayName("Store closed - Sunday")
    void closedToday() {
        Simulation sim = new Simulation();
        sim.weekDay = Simulation.Weekday.SUNDAY;
        sim.startDay(1);
        assert(sim.store_northside.activeClerk == null && sim.store_southside.activeClerk == null);
    }

    @Test
    @DisplayName("Utility Random Function")
    void rndFromRange() {
        for (int i = 0; i < 10; i++) {
            assert(Utility.rndFromRange(0, 3) >= 0 && Utility.rndFromRange(0, 3) <= 3);
        }
    }

    @Test
    @DisplayName("Utility Dollar Function")
    void asDollar() {
        assert(Utility.asDollar(5.00).substring(0, 1).contentEquals("$"));
    }

    @Test
    @DisplayName("Utility Enum Function")
    void randomEnum() {
        assert(Utility.randomEnum(ItemType.class) != null);
    }
}