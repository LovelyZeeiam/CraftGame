package xueli.game2.resource;

import java.util.function.Supplier;

/**
 * We get the target resource just like a ticket
 */
public interface ReloadableResourceTicket<T> extends Supplier<T> {
}
