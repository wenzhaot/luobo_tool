package anet.channel;

/* compiled from: Taobao */
public class NoAvailStrategyException extends Exception {
    private SessionRequest a;

    public NoAvailStrategyException(SessionRequest sessionRequest) {
        this.a = sessionRequest;
    }

    public String toString() {
        return "No Available Strategy" + super.toString();
    }
}
