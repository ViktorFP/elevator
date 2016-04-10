package by.epamlab.beans;

public class Person implements Runnable {
	private int id;
	private Elevator elevator;
	private int from;
	private int to;
	private Thread thread;
	private static volatile int count = 0;

	public Person(Elevator elevator, int from, int to) {
		super();
		this.elevator = elevator;
		this.from = from;
		this.to = to;
		id = ++count;
		thread = new Thread(this);
		thread.start();
	}

	public Thread getThread() {
		return thread;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", from=" + from + ", to=" + to + "]";
	}

	@Override
	public void run() {
		if (from != to) {
			while (!elevator.entry(this));
			while(!elevator.exit(this));
		}		
	}
}
