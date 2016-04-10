import java.util.Random;

import by.epamlab.beans.Elevator;
import by.epamlab.beans.House;
import by.epamlab.beans.Person;

public class Runner {

	private static int getFloor(int range) {
		return (new Random()).nextInt(range) + 1;
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Elevator elevator = new Elevator();
		new Thread(new House(elevator)).start();
		final int MAX_FLOOR = elevator.getMaxFloor();
		// creat 10 person with own attributes
		final int PERSON_AMOUNT = 10;
		Person[] persons = new Person[PERSON_AMOUNT];
		try {
			for (int i = 0; i < PERSON_AMOUNT; i++) {
				int from = getFloor(MAX_FLOOR);
				int to = getFloor(MAX_FLOOR);
				if (from == to) {
					to = (to == MAX_FLOOR) ? --to : ++to;
				}
				Thread.currentThread().sleep((int) (Math.random() * 500));
				persons[i] = new Person(elevator, from, to);
				System.out.println(persons[i]);
			}
			for (Person person : persons) {
				person.getThread().join();
			}
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		elevator.setActive(false);
	}
}
