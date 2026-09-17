package tomeko.hyinfo.event;

//? if ornithe {
/*import java.lang.reflect.Array;
import java.util.function.Function;

public final class Event<T> {
    private final Function<T[], T> invokerFactory;
    private T[] handlers;
    private volatile T invoker;

    @SuppressWarnings("unchecked")
    private Event(Class<T> type, Function<T[], T> invokerFactory) {
        this.invokerFactory = invokerFactory;
        this.handlers = (T[]) Array.newInstance(type, 0);
        update();
    }

    public static <T> Event<T> create(Class<T> type, Function<T[], T> invokerFactory) {
        return new Event<>(type, invokerFactory);
    }

    private void update() {
        this.invoker = invokerFactory.apply(handlers);
    }

    public T invoker() {
        return invoker;
    }

    @SuppressWarnings("unchecked")
    public synchronized void register(T listener) {
        if (listener == null) {
            throw new NullPointerException("listener cannot be null");
        }
        T[] old = handlers;
        T[] merged = (T[]) Array.newInstance(old.getClass().getComponentType(), old.length + 1);
        System.arraycopy(old, 0, merged, 0, old.length);
        merged[old.length] = listener;
        handlers = merged;
        update();
    }
}
*///?}