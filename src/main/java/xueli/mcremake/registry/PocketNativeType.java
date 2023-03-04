package xueli.mcremake.registry;

public @interface PocketNativeType {
	String version() default "0.1.1";
	String[] value();
}
