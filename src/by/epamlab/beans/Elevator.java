package by.epamlab.beans;

public class Elevator {
	private final int CAPACITY;
	private final int MAX_FLOOR;
	private volatile boolean isActive = true;
	private boolean toUp = true;
	private volatile int nPerson = 0;
	private int floor = 1;
	private final static String FLOOR_MSG = "Floor: ";

	public Elevator() {
		this(4, 5);
	}

	public Elevator(int capacity, int maxFloor) {
		CAPACITY = capacity;
		MAX_FLOOR = maxFloor;
	}

	public synchronized boolean isActive() {
		return isActive;
	}

	public synchronized void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getMaxFloor() {
		return MAX_FLOOR;
	}

	public synchronized boolean entry(Person person) {
		int from = person.getFrom();
		while (from != floor || nPerson == CAPACITY) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
				notifyAll();
				return false;
			}
		}
		if (toUp == (from < person.getTo())) {
			nPerson++;
			System.out.println("=> " + person + " " + this);
			notifyAll();
			return true;
		}
		notifyAll();
		return false;
	}

	public synchronized boolean exit(Person person) {
		while (person.getTo() != floor) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
				notifyAll();
				return false;
			}
		}
		nPerson--;
		System.out.println("<= " + person + " " + this);
		notifyAll();
		return true;
	}

	public synchronized void use() {
		if (floor < MAX_FLOOR && toUp) {
			floor++;
			if (floor == MAX_FLOOR) {
				toUp = false;
			}
			System.out.println(FLOOR_MSG + floor);
		} else {
			floor--;
			if (floor == 1) {
				toUp = true;
			}
			System.out.println(FLOOR_MSG + floor);
		}
		notifyAll();
	}

	@Override
	public String toString() {
		return "Elevator [toUp=" + toUp + ", nPerson=" + nPerson + ", floor=" + floor + "]";
	}
}
