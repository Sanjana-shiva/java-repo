package Java_model;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		List<Household> households = Arrays.asList(
		new Household("House-A", 10.0, 6.0),
		new Household("House-B", 4.0, 8.0),
		new Household("House-C", 5.0, 3.0),
		new Household("House-D", 3.0, 5.5)
		);

	    List<Trade> trades = new ArrayList<>();

	    for (Household seller : households) {
	        if (!seller.hasSurplus()) continue;

	        for (Household buyer : households) {
	            if (!buyer.hasDeficit()) continue;

	            double transferable = Math.min(seller.getSurplus(), -buyer.getSurplus());

	            if (transferable > 0) {
	                trades.add(new Trade(seller, buyer, transferable));
	                seller.energyProduced -= transferable;
	                buyer.energyProduced += transferable;
	            }
	        }
	    }

	    System.out.println("Trade Summary:");
	    for (Trade t : trades) {
	        System.out.println(t);
	    }

	    System.out.println("\nFinal Household States:");
	    for (Household h : households) {
	        System.out.println(h);
	    }
	}


}
class Household {
String name;
double energyProduced;
double energyConsumed;

public Household(String name, double produced, double consumed) {
    this.name = name;
    this.energyProduced = produced;
    this.energyConsumed = consumed;
}

public double getSurplus() {
    return energyProduced - energyConsumed;
}

public boolean hasSurplus() {
    return getSurplus() > 0;
}

public boolean hasDeficit() {
    return getSurplus() < 0;
}

public void tradeEnergy(double amount) {
    energyProduced -= amount;
    energyConsumed += amount;
}

@Override
public String toString() {
    return name + " [Produced: " + energyProduced + " kWh, Consumed: " + energyConsumed +
            " kWh, Net: " + String.format("%.2f", getSurplus()) + " kWh]";
}
}

class Trade {
Household seller;
Household buyer;
double energyAmount;

public Trade(Household seller, Household buyer, double amount) {
    this.seller = seller;
    this.buyer = buyer;
    this.energyAmount = amount;
}

@Override
public String toString() {
    return seller.name + " sold " + energyAmount + " kWh to " + buyer.name;
}
}


