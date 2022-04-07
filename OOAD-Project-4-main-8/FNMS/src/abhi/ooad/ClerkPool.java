package abhi.ooad;

import java.util.ArrayList;
import java.util.Iterator;

public class ClerkPool {
    private static ClerkPool uniqueInstance;
    ArrayList<Clerk> clerks;
    ArrayList<String> clerkNames;

    private ClerkPool() {
        if (this.clerks == null) {
            this.clerks = new ArrayList<Clerk>();
            this.clerkNames = new ArrayList<String>();
            this.clerks.add(new Clerk("Velma", .05));
            clerkNames.add("Velma");
            this.clerks.add(new Clerk("Shaggy", .20));
            clerkNames.add("Shaggy");
            this.clerks.add(new Clerk("Daphne", .10));
            clerkNames.add("Daphne");
            this.clerks.add(new Clerk("Fred", .15));
            clerkNames.add("Fred");
            this.clerks.add(new Clerk("Scooby", .00));
            clerkNames.add("Scooby");
            this.clerks.add(new Clerk("Abhi", .25));
            clerkNames.add("Abhi");
            this.clerks.add(new Clerk("Alexa", .10));
            clerkNames.add("Alexa");
            this.clerks.add(new Clerk("Varun", .15));
            clerkNames.add("Varun");
        }
    }

    public static ClerkPool getInstance () {
        if (uniqueInstance == null) {
            uniqueInstance = new ClerkPool();
        }
        return uniqueInstance;
    }

    public void endOfDay(Store store_northside, Store store_southside) {
        Iterator<Clerk> itr = clerks.iterator();
        while (itr.hasNext()) {
            Clerk clerk = itr.next();
            if(store_northside.activeClerk == null) {
                clerk.daysWorked = 0;
                clerk.workingAtStore = null;
            }
            else {
                if (store_northside.activeClerk.name == clerk.name || store_southside.activeClerk.name == clerk.name) {
                    //Do not reset daysWorked
                } else {
                    clerk.daysWorked = 0;
                }
                clerk.workingAtStore = null;
            }
        }
    }
}
