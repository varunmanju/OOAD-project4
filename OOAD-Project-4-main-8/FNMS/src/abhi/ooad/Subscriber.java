package abhi.ooad;

enum subscriberType{
    TRACKER, LOGGER
}
enum eventType{
    SOLD, PURCHASED, DAMAGED
}

public interface Subscriber {
    default void out(String msg) {
        System.out.println(msg);
    }
}
