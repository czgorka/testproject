package com.test.baselab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RPNEvaluatorTest {

	@Test
	void testWrongParams() {
		assertThrows(NullPointerException.class, () -> { RPNEvaluator.evaluate(null); });
		assertThrows(IllegalArgumentException.class, () -> { RPNEvaluator.evaluate(""); });
		assertThrows(IllegalArgumentException.class, () -> { RPNEvaluator.evaluate("abc"); });
		assertThrows(IllegalArgumentException.class, () -> { RPNEvaluator.evaluate("1 a +"); });
		assertThrows(IllegalArgumentException.class, () -> { RPNEvaluator.evaluate("1.2 2 +"); });
		assertThrows(IllegalStateException.class, () -> { RPNEvaluator.evaluate("1 2 5"); });
	}
	
	@Test
	void testSimple() {
		assertEquals(5L, Long.parseLong(RPNEvaluator.evaluate("2 3 +")));
		assertEquals(4L, Long.parseLong(RPNEvaluator.evaluate("1 3 +")));
		assertEquals(2L, Long.parseLong(RPNEvaluator.evaluate("5 3 -")));
		assertEquals(8L, Long.parseLong(RPNEvaluator.evaluate("2 4 *")));
		assertEquals(3L, Long.parseLong(RPNEvaluator.evaluate("6 2 /")));
	}

	@Test
	void testComplex() {
		assertEquals(8L, Long.parseLong(RPNEvaluator.evaluate("1 3 + 5 3 - *")));
	}
	
	@Test
	void testTernaryOperator() {
		assertEquals(7L, Long.parseLong(RPNEvaluator.evaluate("1 3 + 5 3 - * 7 6 if")));
	}
	
	@Test
	void testMax() {
		assertEquals(8L, Long.parseLong(RPNEvaluator.evaluate("1 3 + 5 3 - * 7 6 max")));
	}
}
