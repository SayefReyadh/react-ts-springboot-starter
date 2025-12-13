import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
  useLocation,
} from "react-router-dom";
import HomePage from "./pages/HomePage";
import StudentDetails from "./components/StudentDetails";
import WebSocketDemo from "./pages/WebSocketDemo";
import SSEDemo from "./pages/SSEDemo";
import AsyncDemo from "./pages/AsyncDemo";
import ModernFeaturesDemo from "./pages/ModernFeaturesDemo";

function Navigation() {
  const location = useLocation();

  const navStyle = {
    padding: "15px 30px",
    backgroundColor: "#2c3e50",
    display: "flex",
    gap: "20px",
    alignItems: "center",
    boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
  };

  const linkStyle = (path: string) => ({
    color: location.pathname === path ? "#fff" : "#bdc3c7",
    textDecoration: "none",
    padding: "8px 16px",
    borderRadius: "4px",
    backgroundColor: location.pathname === path ? "#34495e" : "transparent",
    transition: "all 0.3s",
    fontSize: "14px",
    fontWeight: location.pathname === path ? "bold" : "normal",
  });

  const titleStyle = {
    color: "#ecf0f1",
    fontSize: "20px",
    fontWeight: "bold",
    marginRight: "30px",
  };

  return (
    <nav style={navStyle}>
      <span style={titleStyle}>🚀 Spring Boot + React Demo</span>
      <Link to="/" style={linkStyle("/")}>
        🏠 Home
      </Link>
      <Link to="/modern" style={linkStyle("/modern")}>
        ✨ Modern Features
      </Link>
      <Link to="/websocket" style={linkStyle("/websocket")}>
        🔌 WebSocket
      </Link>
      <Link to="/sse" style={linkStyle("/sse")}>
        📡 SSE
      </Link>
      <Link to="/async" style={linkStyle("/async")}>
        ⚡ Async/Threading
      </Link>
    </nav>
  );
}

function App() {
  return (
    <Router>
      <Navigation />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/student/:id" element={<StudentDetails />} />
        <Route path="/modern" element={<ModernFeaturesDemo />} />
        <Route path="/websocket" element={<WebSocketDemo />} />
        <Route path="/sse" element={<SSEDemo />} />
        <Route path="/async" element={<AsyncDemo />} />
      </Routes>
    </Router>
  );
}

export default App;
