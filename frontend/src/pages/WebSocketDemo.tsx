import { useState, useEffect, useRef } from "react";
import { Client, IMessage } from "@stomp/stompjs";
import SockJS from "sockjs-client";

interface Message {
  from: string;
  content: string;
  timestamp: string;
  type: "chat" | "join" | "leave" | "private";
}

export default function WebSocketDemo() {
  const [connected, setConnected] = useState(false);
  const [username, setUsername] = useState("");
  const [messages, setMessages] = useState<Message[]>([]);
  const [inputMessage, setInputMessage] = useState("");
  const [privateUser, setPrivateUser] = useState("");
  const [privateMessage, setPrivateMessage] = useState("");
  const clientRef = useRef<Client | null>(null);
  const messagesEndRef = useRef<HTMLDivElement>(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const connect = () => {
    if (!username.trim()) {
      alert("Please enter a username");
      return;
    }

    const socket = new SockJS("/ws");
    const stompClient = new Client({
      webSocketFactory: () => socket as any,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    stompClient.onConnect = () => {
      console.log("Connected to WebSocket");
      setConnected(true);

      // Subscribe to public chat
      stompClient.subscribe("/topic/public", (message: IMessage) => {
        const msg = JSON.parse(message.body);
        addMessage({
          from: msg.from,
          content: msg.content,
          timestamp: msg.timestamp || new Date().toISOString(),
          type: msg.type || "chat",
        });
      });

      // Subscribe to private messages
      stompClient.subscribe(
        `/queue/private/${username}`,
        (message: IMessage) => {
          const msg = JSON.parse(message.body);
          addMessage({
            from: msg.from,
            content: msg.content,
            timestamp: msg.timestamp || new Date().toISOString(),
            type: "private",
          });
        }
      );

      // Subscribe to user events
      stompClient.subscribe("/topic/users", (message: IMessage) => {
        const event = JSON.parse(message.body);
        addMessage({
          from: "System",
          content: `${event.username} ${
            event.type === "JOIN" ? "joined" : "left"
          } the chat`,
          timestamp: event.timestamp || new Date().toISOString(),
          type: event.type === "JOIN" ? "join" : "leave",
        });
      });

      // Send join message
      stompClient.publish({
        destination: "/app/join",
        body: JSON.stringify({ username }),
      });
    };

    stompClient.onStompError = (frame) => {
      console.error("Broker reported error: " + frame.headers["message"]);
      console.error("Additional details: " + frame.body);
    };

    stompClient.activate();
    clientRef.current = stompClient;
  };

  const disconnect = () => {
    if (clientRef.current) {
      // Send leave message
      clientRef.current.publish({
        destination: "/app/leave",
        body: JSON.stringify({ username }),
      });

      clientRef.current.deactivate();
      clientRef.current = null;
      setConnected(false);
      setMessages([]);
    }
  };

  const addMessage = (msg: Message) => {
    setMessages((prev) => [...prev, msg]);
  };

  const sendMessage = () => {
    if (!inputMessage.trim() || !clientRef.current) return;

    clientRef.current.publish({
      destination: "/app/chat",
      body: JSON.stringify({
        sender: username,
        content: inputMessage,
      }),
    });

    setInputMessage("");
  };

  const sendPrivateMessage = () => {
    if (!privateMessage.trim() || !privateUser.trim() || !clientRef.current)
      return;

    clientRef.current.publish({
      destination: "/app/private",
      body: JSON.stringify({
        sender: username,
        recipient: privateUser,
        content: privateMessage,
      }),
    });

    addMessage({
      from: `You (to ${privateUser})`,
      content: privateMessage,
      timestamp: new Date().toISOString(),
      type: "private",
    });

    setPrivateMessage("");
  };

  const handleKeyPress = (e: React.KeyboardEvent, action: () => void) => {
    if (e.key === "Enter") {
      action();
    }
  };

  return (
    <div style={{ padding: "20px", maxWidth: "1200px", margin: "0 auto" }}>
      <h1>🔌 WebSocket Chat Demo</h1>
      <p style={{ color: "#666" }}>
        Real-time bidirectional communication using Spring Boot WebSocket +
        STOMP
      </p>

      {!connected ? (
        <div
          style={{
            marginTop: "20px",
            padding: "20px",
            border: "1px solid #ddd",
            borderRadius: "8px",
          }}
        >
          <h3>Connect to Chat</h3>
          <div style={{ display: "flex", gap: "10px", alignItems: "center" }}>
            <input
              type="text"
              placeholder="Enter username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              onKeyPress={(e) => handleKeyPress(e, connect)}
              style={{
                padding: "10px",
                flex: 1,
                fontSize: "16px",
                borderRadius: "4px",
                border: "1px solid #ccc",
              }}
            />
            <button
              onClick={connect}
              style={{
                padding: "10px 30px",
                fontSize: "16px",
                backgroundColor: "#4CAF50",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: "pointer",
              }}
            >
              Connect
            </button>
          </div>
        </div>
      ) : (
        <div style={{ marginTop: "20px" }}>
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              marginBottom: "15px",
            }}
          >
            <span
              style={{ fontSize: "18px", fontWeight: "bold", color: "#4CAF50" }}
            >
              ✅ Connected as: {username}
            </span>
            <button
              onClick={disconnect}
              style={{
                padding: "8px 20px",
                fontSize: "14px",
                backgroundColor: "#f44336",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: "pointer",
              }}
            >
              Disconnect
            </button>
          </div>

          <div
            style={{
              display: "grid",
              gridTemplateColumns: "2fr 1fr",
              gap: "20px",
            }}
          >
            {/* Public Chat */}
            <div>
              <h3>Public Chat</h3>
              <div
                style={{
                  border: "1px solid #ddd",
                  borderRadius: "8px",
                  padding: "15px",
                  height: "400px",
                  overflowY: "auto",
                  backgroundColor: "#f9f9f9",
                  marginBottom: "10px",
                }}
              >
                {messages.map((msg, idx) => (
                  <div
                    key={idx}
                    style={{
                      marginBottom: "10px",
                      padding: "8px",
                      borderRadius: "4px",
                      backgroundColor:
                        msg.type === "private"
                          ? "#fff3cd"
                          : msg.type === "join"
                          ? "#d4edda"
                          : msg.type === "leave"
                          ? "#f8d7da"
                          : "white",
                    }}
                  >
                    <div style={{ fontSize: "12px", color: "#666" }}>
                      {new Date(msg.timestamp).toLocaleTimeString()}
                    </div>
                    <div>
                      <strong>{msg.from}:</strong> {msg.content}
                    </div>
                  </div>
                ))}
                <div ref={messagesEndRef} />
              </div>

              <div style={{ display: "flex", gap: "10px" }}>
                <input
                  type="text"
                  placeholder="Type a message..."
                  value={inputMessage}
                  onChange={(e) => setInputMessage(e.target.value)}
                  onKeyPress={(e) => handleKeyPress(e, sendMessage)}
                  style={{
                    flex: 1,
                    padding: "10px",
                    fontSize: "14px",
                    borderRadius: "4px",
                    border: "1px solid #ccc",
                  }}
                />
                <button
                  onClick={sendMessage}
                  style={{
                    padding: "10px 30px",
                    fontSize: "14px",
                    backgroundColor: "#2196F3",
                    color: "white",
                    border: "none",
                    borderRadius: "4px",
                    cursor: "pointer",
                  }}
                >
                  Send
                </button>
              </div>
            </div>

            {/* Private Messages */}
            <div>
              <h3>Private Message</h3>
              <div
                style={{
                  border: "1px solid #ddd",
                  borderRadius: "8px",
                  padding: "15px",
                }}
              >
                <input
                  type="text"
                  placeholder="Recipient username"
                  value={privateUser}
                  onChange={(e) => setPrivateUser(e.target.value)}
                  style={{
                    width: "100%",
                    padding: "10px",
                    fontSize: "14px",
                    borderRadius: "4px",
                    border: "1px solid #ccc",
                    marginBottom: "10px",
                  }}
                />
                <textarea
                  placeholder="Private message..."
                  value={privateMessage}
                  onChange={(e) => setPrivateMessage(e.target.value)}
                  style={{
                    width: "100%",
                    padding: "10px",
                    fontSize: "14px",
                    borderRadius: "4px",
                    border: "1px solid #ccc",
                    marginBottom: "10px",
                    resize: "vertical",
                    minHeight: "100px",
                  }}
                />
                <button
                  onClick={sendPrivateMessage}
                  style={{
                    width: "100%",
                    padding: "10px",
                    fontSize: "14px",
                    backgroundColor: "#FF9800",
                    color: "white",
                    border: "none",
                    borderRadius: "4px",
                    cursor: "pointer",
                  }}
                >
                  Send Private
                </button>
              </div>

              <div
                style={{
                  marginTop: "20px",
                  padding: "15px",
                  backgroundColor: "#f0f0f0",
                  borderRadius: "8px",
                }}
              >
                <h4>Features:</h4>
                <ul style={{ fontSize: "14px", lineHeight: "1.6" }}>
                  <li>Public chat broadcast</li>
                  <li>Private messaging</li>
                  <li>User join/leave notifications</li>
                  <li>Auto-reconnect on disconnect</li>
                  <li>STOMP over WebSocket</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      )}

      <div
        style={{
          marginTop: "30px",
          padding: "15px",
          backgroundColor: "#e3f2fd",
          borderRadius: "8px",
        }}
      >
        <h4>💡 Technical Details:</h4>
        <ul style={{ fontSize: "14px", lineHeight: "1.8" }}>
          <li>
            <strong>Protocol:</strong> STOMP over WebSocket with SockJS fallback
          </li>
          <li>
            <strong>Backend:</strong> Spring Boot with
            @EnableWebSocketMessageBroker
          </li>
          <li>
            <strong>Endpoints:</strong> /app/chat.send (public),
            /app/chat.private (private), /topic/public (subscribe)
          </li>
          <li>
            <strong>Features:</strong> Bidirectional communication, message
            broadcasting, private queues
          </li>
        </ul>
      </div>
    </div>
  );
}
