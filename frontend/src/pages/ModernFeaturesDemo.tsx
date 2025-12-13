import { useState, useEffect, useRef } from "react";
import SockJS from "sockjs-client";
import { Client, Message } from "@stomp/stompjs";

interface AsyncResult {
  status: string;
  message: string;
  concept?: string;
  note?: string;
  results?: string[];
  totalTimeMs?: number;
}

interface SSEMessage {
  timestamp: string;
  message: string;
  type: string;
}

interface WebSocketMessage {
  sender: string;
  content: string;
  timestamp?: string;
}

export default function ModernFeaturesDemo() {
  // Async State
  const [asyncResult, setAsyncResult] = useState<AsyncResult | null>(null);
  const [asyncLoading, setAsyncLoading] = useState(false);

  // SSE State
  const [sseMessages, setSseMessages] = useState<SSEMessage[]>([]);
  const [sseConnected, setSseConnected] = useState(false);
  const eventSourceRef = useRef<EventSource | null>(null);

  // WebSocket State
  const [wsMessages, setWsMessages] = useState<WebSocketMessage[]>([]);
  const [wsConnected, setWsConnected] = useState(false);
  const [wsMessage, setWsMessage] = useState("");
  const [wsUsername, setWsUsername] = useState(
    "User" + Math.floor(Math.random() * 1000)
  );
  const stompClientRef = useRef<Client | null>(null);

  // Async Operations
  const testFireAndForget = async () => {
    setAsyncLoading(true);
    try {
      const response = await fetch(
        "http://localhost:8080/api/async/fire-and-forget",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ taskName: "FrontendTask" }),
        }
      );
      const data = await response.json();
      setAsyncResult(data);
    } catch (error) {
      console.error("Async error:", error);
    } finally {
      setAsyncLoading(false);
    }
  };

  const testParallelProcessing = async () => {
    setAsyncLoading(true);
    try {
      const response = await fetch(
        "http://localhost:8080/api/async/parallel?taskCount=5",
        {
          method: "POST",
        }
      );
      const data = await response.json();
      setAsyncResult(data);
    } catch (error) {
      console.error("Async error:", error);
    } finally {
      setAsyncLoading(false);
    }
  };

  // SSE Operations
  const connectSSE = () => {
    const clientId = "client-" + Math.random().toString(36).substr(2, 9);
    const eventSource = new EventSource(
      `http://localhost:8080/api/sse/stream?clientId=${clientId}`
    );

    eventSource.addEventListener("connected", (e) => {
      console.log("SSE Connected:", e.data);
      setSseConnected(true);
      setSseMessages((prev) => [
        ...prev,
        {
          timestamp: new Date().toLocaleTimeString(),
          message: "Connected to SSE stream",
          type: "connected",
        },
      ]);
    });

    eventSource.addEventListener("broadcast", (e) => {
      const data = JSON.parse(e.data);
      setSseMessages((prev) => [
        ...prev,
        {
          timestamp: new Date().toLocaleTimeString(),
          message: data.message,
          type: "broadcast",
        },
      ]);
    });

    eventSource.onerror = () => {
      console.error("SSE Error");
      setSseConnected(false);
    };

    eventSourceRef.current = eventSource;
  };

  const disconnectSSE = () => {
    if (eventSourceRef.current) {
      eventSourceRef.current.close();
      eventSourceRef.current = null;
      setSseConnected(false);
      setSseMessages((prev) => [
        ...prev,
        {
          timestamp: new Date().toLocaleTimeString(),
          message: "Disconnected from SSE",
          type: "disconnect",
        },
      ]);
    }
  };

  const testSSECounter = async () => {
    setSseMessages([]);
    const clientId = "counter-" + Math.random().toString(36).substr(2, 9);
    const eventSource = new EventSource(
      `http://localhost:8080/api/sse/counter?duration=10`
    );

    eventSource.onmessage = (e) => {
      setSseMessages((prev) => [
        ...prev,
        {
          timestamp: new Date().toLocaleTimeString(),
          message: e.data,
          type: "counter",
        },
      ]);
    };

    eventSource.onerror = () => {
      eventSource.close();
    };

    // Auto close after 12 seconds
    setTimeout(() => eventSource.close(), 12000);
  };

  const broadcastSSE = async () => {
    try {
      await fetch("http://localhost:8080/api/sse/broadcast", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ message: "Hello from React Frontend!" }),
      });
    } catch (error) {
      console.error("SSE broadcast error:", error);
    }
  };

  // WebSocket Operations
  const connectWebSocket = () => {
    const socket = new SockJS("http://localhost:8080/ws");
    const client = new Client({
      webSocketFactory: () => socket as any,
      debug: (str) => console.log(str),
      onConnect: () => {
        console.log("WebSocket Connected");
        setWsConnected(true);
        setWsMessages((prev) => [
          ...prev,
          {
            sender: "System",
            content: "Connected to WebSocket",
            timestamp: new Date().toLocaleTimeString(),
          },
        ]);

        // Subscribe to public messages
        client.subscribe("/topic/messages", (message: Message) => {
          const msg = JSON.parse(message.body);
          setWsMessages((prev) => [
            ...prev,
            {
              ...msg,
              timestamp: new Date().toLocaleTimeString(),
            },
          ]);
        });
      },
      onStompError: (frame) => {
        console.error("WebSocket error:", frame);
        setWsConnected(false);
      },
    });

    client.activate();
    stompClientRef.current = client;
  };

  const disconnectWebSocket = () => {
    if (stompClientRef.current) {
      stompClientRef.current.deactivate();
      stompClientRef.current = null;
      setWsConnected(false);
      setWsMessages((prev) => [
        ...prev,
        {
          sender: "System",
          content: "Disconnected from WebSocket",
          timestamp: new Date().toLocaleTimeString(),
        },
      ]);
    }
  };

  const sendWebSocketMessage = () => {
    if (stompClientRef.current && wsMessage.trim()) {
      stompClientRef.current.publish({
        destination: "/app/chat",
        body: JSON.stringify({
          sender: wsUsername,
          content: wsMessage,
        }),
      });
      setWsMessage("");
    }
  };

  const broadcastWebSocket = async () => {
    try {
      await fetch("http://localhost:8080/api/websocket/broadcast", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ message: "Server broadcast from React!" }),
      });
    } catch (error) {
      console.error("WebSocket broadcast error:", error);
    }
  };

  // Cleanup
  useEffect(() => {
    return () => {
      disconnectSSE();
      disconnectWebSocket();
    };
  }, []);

  return (
    <div style={{ padding: "20px", maxWidth: "1400px", margin: "0 auto" }}>
      <h1>🚀 Modern Spring Boot Features Demo</h1>
      <p style={{ color: "#666", marginBottom: "30px" }}>
        Production-ready alternatives to manual threading and raw sockets
      </p>

      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(400px, 1fr))",
          gap: "20px",
        }}
      >
        {/* Async Section */}
        <div
          style={{
            border: "2px solid #4CAF50",
            borderRadius: "8px",
            padding: "20px",
            backgroundColor: "#f9fff9",
          }}
        >
          <h2>⚡ @Async Annotation</h2>
          <p style={{ fontSize: "14px", color: "#666" }}>
            Modern threading without manual thread management
          </p>

          <div style={{ marginTop: "15px" }}>
            <button
              onClick={testFireAndForget}
              disabled={asyncLoading}
              style={{
                padding: "10px 20px",
                marginRight: "10px",
                backgroundColor: "#4CAF50",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: asyncLoading ? "not-allowed" : "pointer",
              }}
            >
              Fire & Forget
            </button>

            <button
              onClick={testParallelProcessing}
              disabled={asyncLoading}
              style={{
                padding: "10px 20px",
                backgroundColor: "#2196F3",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: asyncLoading ? "not-allowed" : "pointer",
              }}
            >
              Parallel (5 tasks)
            </button>
          </div>

          {asyncResult && (
            <div
              style={{
                marginTop: "15px",
                padding: "15px",
                backgroundColor: "white",
                borderRadius: "4px",
                border: "1px solid #ddd",
              }}
            >
              <div>
                <strong>Status:</strong> {asyncResult.status}
              </div>
              <div>
                <strong>Message:</strong> {asyncResult.message}
              </div>
              {asyncResult.concept && (
                <div
                  style={{ fontSize: "12px", color: "#666", marginTop: "8px" }}
                >
                  {asyncResult.concept}
                </div>
              )}
              {asyncResult.totalTimeMs && (
                <div>
                  <strong>Time:</strong> {asyncResult.totalTimeMs}ms
                </div>
              )}
              {asyncResult.results && (
                <div style={{ marginTop: "10px" }}>
                  <strong>Results:</strong>
                  <ul style={{ fontSize: "12px", marginTop: "5px" }}>
                    {asyncResult.results.map((result, idx) => (
                      <li key={idx}>{result}</li>
                    ))}
                  </ul>
                </div>
              )}
            </div>
          )}
        </div>

        {/* SSE Section */}
        <div
          style={{
            border: "2px solid #FF9800",
            borderRadius: "8px",
            padding: "20px",
            backgroundColor: "#fff9f0",
          }}
        >
          <h2>📡 Server-Sent Events</h2>
          <p style={{ fontSize: "14px", color: "#666" }}>
            One-way server-to-client streaming
          </p>

          <div style={{ marginTop: "15px" }}>
            <button
              onClick={sseConnected ? disconnectSSE : connectSSE}
              style={{
                padding: "10px 20px",
                marginRight: "10px",
                backgroundColor: sseConnected ? "#f44336" : "#FF9800",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: "pointer",
              }}
            >
              {sseConnected ? "Disconnect" : "Connect"}
            </button>

            <button
              onClick={testSSECounter}
              style={{
                padding: "10px 20px",
                marginRight: "10px",
                backgroundColor: "#9C27B0",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: "pointer",
              }}
            >
              Counter (10s)
            </button>

            <button
              onClick={broadcastSSE}
              disabled={!sseConnected}
              style={{
                padding: "10px 20px",
                backgroundColor: "#00BCD4",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: sseConnected ? "pointer" : "not-allowed",
                opacity: sseConnected ? 1 : 0.5,
              }}
            >
              Broadcast
            </button>
          </div>

          <div
            style={{
              marginTop: "15px",
              height: "300px",
              overflowY: "auto",
              backgroundColor: "white",
              border: "1px solid #ddd",
              borderRadius: "4px",
              padding: "10px",
            }}
          >
            {sseMessages.length === 0 ? (
              <div
                style={{
                  color: "#999",
                  textAlign: "center",
                  marginTop: "50px",
                }}
              >
                No messages yet. Connect to start receiving.
              </div>
            ) : (
              sseMessages.map((msg, idx) => (
                <div
                  key={idx}
                  style={{
                    marginBottom: "8px",
                    padding: "8px",
                    backgroundColor:
                      msg.type === "connected"
                        ? "#e3f2fd"
                        : msg.type === "counter"
                        ? "#f3e5f5"
                        : "#fff3e0",
                    borderRadius: "4px",
                    fontSize: "13px",
                  }}
                >
                  <span style={{ fontWeight: "bold" }}>[{msg.timestamp}]</span>{" "}
                  {msg.message}
                </div>
              ))
            )}
          </div>
        </div>

        {/* WebSocket Section */}
        <div
          style={{
            border: "2px solid #2196F3",
            borderRadius: "8px",
            padding: "20px",
            backgroundColor: "#f0f8ff",
          }}
        >
          <h2>🔌 WebSocket (STOMP)</h2>
          <p style={{ fontSize: "14px", color: "#666" }}>
            Bidirectional real-time communication
          </p>

          <div style={{ marginTop: "15px" }}>
            <input
              type="text"
              placeholder="Your username"
              value={wsUsername}
              onChange={(e) => setWsUsername(e.target.value)}
              style={{
                padding: "8px",
                marginRight: "10px",
                borderRadius: "4px",
                border: "1px solid #ddd",
                width: "150px",
              }}
            />
            <button
              onClick={wsConnected ? disconnectWebSocket : connectWebSocket}
              style={{
                padding: "10px 20px",
                backgroundColor: wsConnected ? "#f44336" : "#2196F3",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: "pointer",
              }}
            >
              {wsConnected ? "Disconnect" : "Connect"}
            </button>
          </div>

          <div
            style={{
              marginTop: "15px",
              height: "250px",
              overflowY: "auto",
              backgroundColor: "white",
              border: "1px solid #ddd",
              borderRadius: "4px",
              padding: "10px",
            }}
          >
            {wsMessages.length === 0 ? (
              <div
                style={{
                  color: "#999",
                  textAlign: "center",
                  marginTop: "50px",
                }}
              >
                No messages yet. Connect and send a message.
              </div>
            ) : (
              wsMessages.map((msg, idx) => (
                <div
                  key={idx}
                  style={{
                    marginBottom: "8px",
                    padding: "8px",
                    backgroundColor:
                      msg.sender === "System" ? "#e3f2fd" : "#e8f5e9",
                    borderRadius: "4px",
                    fontSize: "13px",
                  }}
                >
                  <span style={{ fontWeight: "bold", color: "#2196F3" }}>
                    {msg.sender}
                  </span>
                  {msg.timestamp && (
                    <span style={{ fontSize: "11px", color: "#999" }}>
                      {" "}
                      [{msg.timestamp}]
                    </span>
                  )}
                  <div>{msg.content}</div>
                </div>
              ))
            )}
          </div>

          <div style={{ marginTop: "15px", display: "flex" }}>
            <input
              type="text"
              placeholder="Type a message..."
              value={wsMessage}
              onChange={(e) => setWsMessage(e.target.value)}
              onKeyPress={(e) => e.key === "Enter" && sendWebSocketMessage()}
              disabled={!wsConnected}
              style={{
                flex: 1,
                padding: "10px",
                borderRadius: "4px",
                border: "1px solid #ddd",
                marginRight: "10px",
              }}
            />
            <button
              onClick={sendWebSocketMessage}
              disabled={!wsConnected || !wsMessage.trim()}
              style={{
                padding: "10px 20px",
                backgroundColor: "#4CAF50",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor:
                  wsConnected && wsMessage.trim() ? "pointer" : "not-allowed",
                opacity: wsConnected && wsMessage.trim() ? 1 : 0.5,
              }}
            >
              Send
            </button>
          </div>

          <button
            onClick={broadcastWebSocket}
            style={{
              marginTop: "10px",
              padding: "8px 16px",
              backgroundColor: "#9C27B0",
              color: "white",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
              fontSize: "12px",
            }}
          >
            Server Broadcast via API
          </button>
        </div>
      </div>

      {/* Info Section */}
      <div
        style={{
          marginTop: "30px",
          padding: "20px",
          backgroundColor: "#f5f5f5",
          borderRadius: "8px",
          border: "1px solid #ddd",
        }}
      >
        <h3>💡 About Modern Spring Boot Features</h3>
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(auto-fit, minmax(300px, 1fr))",
            gap: "15px",
            marginTop: "15px",
          }}
        >
          <div>
            <strong>@Async:</strong> Declarative async processing without manual
            thread management. Perfect for background tasks, parallel
            processing, and non-blocking operations.
          </div>
          <div>
            <strong>SSE:</strong> Server-Sent Events for server-to-client
            streaming. Ideal for live updates, notifications, progress tracking,
            and dashboards.
          </div>
          <div>
            <strong>WebSocket:</strong> Full-duplex bidirectional communication.
            Best for chat apps, real-time collaboration, live notifications, and
            multiplayer games.
          </div>
        </div>
      </div>
    </div>
  );
}
