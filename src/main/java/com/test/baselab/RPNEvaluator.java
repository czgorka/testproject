package com.test.baselab;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RPNEvaluator {

	private static final String OPERATORS = Stream.of(Operators.values())
			.map(Operators::toString)
			.map(o -> Pattern.quote(o))
			.collect(Collectors.joining("|"));

	private static final Pattern PATTERN = Pattern.compile("\\d+(\\s+(\\d+|" + OPERATORS + "))+");
	
	public static String evaluate(String expression) {
		validate(expression);
		
		return String.valueOf(evaluate(expression.split("\\s")));
	}

	private static void validate(String expression) {
		Objects.requireNonNull(expression);
		
		Matcher matcher = PATTERN.matcher(expression);
		if (!matcher.matches()) {
			throw new IllegalArgumentException();
		}
	}
	
	private static long evaluate(String[] elements) {
		LinkedList<Long> stack = new LinkedList<>();
		
		for (String elem : elements) {
			Optional<Operators> value = Operators.of(elem);
			if (value.isPresent()) {
				Operators operator = value.get();

				int argsSize = operator.getArgsSize() != -1 ? operator.getArgsSize() : stack.size();
				if (stack.isEmpty() || argsSize > stack.size()) {
					throw new IllegalStateException();
				}
				
				List<Long> args = new ArrayList<>();
				for (int i = 0; i < argsSize; i++) {
					args.add(0, stack.pop());
				};
				
				stack.push(operator.evaluate(args));
			} else {
				stack.push(Long.parseLong(elem));
			}
		}
		
		if (stack.size() > 1) {
			throw new IllegalStateException();
		}
		
		return stack.pop();
	}
}
