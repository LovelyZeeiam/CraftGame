package xueli.craftgame.entity;

import java.util.function.Function;

public class Attribute<T> {
	
	private final T initialValue;
	
	private Function<T, T> modifier;
	private T value;
	
	public Attribute(T initialValue) {
		this.initialValue = initialValue;
	}
	
	public T getInitialValue() {
		return initialValue;
	}
	
	public void setModifier(Function<T, T> modifier) {
		this.modifier = modifier;
		
		if(modifier == null)
			this.value = initialValue;
		else
			this.value = modifier.apply(initialValue);
		
	}
	
	public Function<T, T> getModifier() {
		return modifier;
	}
	
	public T getValue() {
		return value;
	}
	
}
