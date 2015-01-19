package test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class TestTopK {

	public static void main(String[] args) {

		int max = (int)6e7;

		ArrayList list = new ArrayList(max);
		PriorityQueue heap = new PriorityQueue(max);

		for (int i = 0; i < max; i++) {
			list.add(new Float((float) Math.random()));

		}

		
		Instant start, end;
		start = Instant.now();
		heap.addAll(list);

		for (int i = 0; i < 10; i++) {
			System.out.println(heap.poll());
		}

		// timer
		end = Instant.now();
		System.out.println("Initialization period: " + Duration.between(start, end)); // prints
																																									// PT1M3.553S
		start = Instant.now(); // reset timer
		
		List l= com.google.common.collect.Ordering.natural()
    .leastOf(list, 10);
		
		for (int i = 0; i < 10; i++) {
			System.out.println(l.get(i));
		}

		// timer
		end = Instant.now();
		System.out.println("Initialization period: " + Duration.between(start, end)); // prints
																																									// PT1M3.553S
		start = Instant.now(); // reset timer

	}
}
