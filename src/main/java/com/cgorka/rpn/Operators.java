package com.cgorka.rpn;

import static java.util.Comparator.naturalOrder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public enum Operators {
	PLUS("+", 2) { Long apply(List<Long> args) { return args.get(0) + args.get(1); } },
	MINUS("-", 2) { Long apply(List<Long> args) { return args.get(0) - args.get(1); } },
	MULTIPLY("*", 2) { Long apply(List<Long> args) { return args.get(0) * args.get(1); } },
	DIVIDE("/", 2) { Long apply(List<Long> args) { return args.get(0) / args.get(1); } },
	TERNARY("if", 3) { Long apply(List<Long> args) { return args.get(0) != 0 ? args.get(1) : args.get(2); } },
	MAX("max", -1) { Long apply(List<Long> args) { return args.stream().max(naturalOrder()).get(); } };
	
	private final String symbol;
	private final int argsSize;
	
	Operators(String c, int numOfArgs) {
		this.symbol = c;
		this.argsSize = numOfArgs;
	}
	
	abstract Long apply(List<Long> args);
	
	public Long calculate(List<Long> args) {
		Objects.requireNonNull(args);
		if ((argsSize == -1 && args.isEmpty()) || argsSize > args.size()) {
			throw new IllegalArgumentException();
		}
		
		return apply(args);
	}
	
	public int getArgsSize() {
		return argsSize;
	}
	
	@Override
	public String toString() {
		return symbol;
	}

	public static Optional<Operators> of(String name) {
		return Stream.of(values()).filter(x -> x.symbol.equals(name)).findFirst();
	}
}
