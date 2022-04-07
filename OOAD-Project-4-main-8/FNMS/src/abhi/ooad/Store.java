package abhi.ooad;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Collectors;
import static java.lang.System.in;

public class Store implements Subscriber {
    public Clerk activeClerk;
    public double cashRegister;
    public double cashFromBank;
    public Inventory inventory;
    public int today;
    public String storeName;


    Store(String storeName) {
        // initialize the store's starting inventory
        inventory = new Inventory();
        this.storeName = storeName;

        cashRegister = 0;   // cash register is empty to begin
        cashFromBank = 0;   // no cash from bank yet
    }

    void openToday(int day) {
        today = day;
        out(" ");
        out("Store FNMS " + this.storeName + " opens today, day "+day);
        activeClerk = getValidClerk();
        out(activeClerk.name + " is working today.");
        activeClerk.setStoreInstance(this);
        activeClerk.setalgo();
        activeClerk.arriveAtStore();
        activeClerk.checkRegister();
        activeClerk.doInventory();
        activeClerk.openTheStore();
        activeClerk.cleanTheStore();
        activeClerk.leaveTheStore();
    }
    //The function that the command pattern executes.All th functions that are executed by the command pattern start with a word interactive.
    void interactiveUserClerkName() {
        out("I'm " + activeClerk.name);
    }

    void interactiveUserClerkTime(int day) {
        out("This is day " + day + " and the time is " + java.time.LocalTime.now().toString().substring(0,8));
    }

    void interactiveUserBuy() {
        activeClerk.buyAnItem(1, true);
    }

    void interactiveUserSell() {
        activeClerk.sellAnItem(1, true);
    }

    void interactiveUserGuitarKit() {
        activeClerk.create();
        out(" ");
    }

    void interactiveUserEndOfDay() {
        activeClerk.cleanTheStore();
        activeClerk.leaveTheStore();
    }

    Clerk getValidClerk() {
        ClerkPool clerkPool = ClerkPool.getInstance();
        Clerk clerk = null;
        ArrayList<Clerk> availableClerks = new ArrayList<>();

        for (int i=0; i<clerkPool.clerks.size(); i++) {
            if (clerkPool.clerks.get(i).workingAtStore == null && clerkPool.clerks.get(i).sickToday == false) {
                availableClerks.add(clerkPool.clerks.get(i));
            }
        }

        for (int i = 0; i < availableClerks.size(); i++) {
            if (availableClerks.size() <= 2) {
                clerk = availableClerks.get(0);
            }
            else {
                clerk = availableClerks.get(Utility.rndFromRange(0, availableClerks.size() - 1));
                if (clerk.daysWorked == 3) {
                    out(clerk.name + " cannot work more than 3 days in a row.");
                    availableClerks.remove(clerk);
                    continue;
                }
                if (Utility.rndFromRange(1, 10) == 1) {
                    out(clerk.name + " is sick today.");
                    clerk.sickToday = true;
                    availableClerks.remove(clerk);
                    continue;
                }
            }
            break;
        }

        clerk.daysWorked = clerk.daysWorked + 1;
        clerk.workingAtStore = this.storeName;

        return clerk;
    }

    void closedToday(int day) {
        out("Store is closed today, day "+day);
    }
}
