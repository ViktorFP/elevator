package by.epamlab.beans;

public class House implements Runnable {
	private Elevator elevator;

	public House(Elevator elevator) {
		this.elevator = elevator;
	}

	@Override
	public void run() {
		while (elevator.isActive()) {
			elevator.use();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
