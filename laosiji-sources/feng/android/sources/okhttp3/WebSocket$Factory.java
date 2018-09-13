package okhttp3;

public interface WebSocket$Factory {
    WebSocket newWebSocket(Request request, WebSocketListener webSocketListener);
}
