import { useState } from "react";

interface TaskResult {
  taskId: string;
  status: string;
  result?: any;
  timestamp: string;
}

export default function AsyncDemo() {
  const [results, setResults] = useState<TaskResult[]>([]);
  const [loading, setLoading] = useState<{ [key: string]: boolean }>({});
  const [threadPoolInfo, setThreadPoolInfo] = useState<any>(null);
  const [scheduledInfo, setScheduledInfo] = useState<any>(null);

  const addResult = (result: TaskResult) => {
    setResults((prev) => [result, ...prev]);
  };

  const fireAndForget = async () => {
    const key = "fireAndForget";
    setLoading((prev) => ({ ...prev, [key]: true }));
    try {
      const response = await fetch("/api/async/fire-and-forget", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ taskName: "Demo Task" }),
      });
      const data = await response.json();
      addResult({
        taskId: "fire-and-forget",
        status: data.status,
        result: data,
        timestamp: new Date().toISOString(),
      });
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setLoading((prev) => ({ ...prev, [key]: false }));
    }
  };

  const asyncWithResult = async () => {
    const key = "withResult";
    setLoading((prev) => ({ ...prev, [key]: true }));
    try {
      const response = await fetch("/api/async/with-result", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ taskName: "Result Task", durationSeconds: 2 }),
      });
      const data = await response.json();
      addResult({
        taskId: "with-result",
        status: "completed",
        result: data,
        timestamp: new Date().toISOString(),
      });
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setLoading((prev) => ({ ...prev, [key]: false }));
    }
  };

  const parallelProcessing = async () => {
    const key = "parallel";
    setLoading((prev) => ({ ...prev, [key]: true }));
    try {
      const response = await fetch("/api/async/parallel?taskCount=5", {
        method: "POST",
      });
      const data = await response.json();
      addResult({
        taskId: "parallel",
        status: "completed",
        result: data,
        timestamp: new Date().toISOString(),
      });
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setLoading((prev) => ({ ...prev, [key]: false }));
    }
  };

  const processData = async () => {
    const key = "processData";
    setLoading((prev) => ({ ...prev, [key]: true }));
    try {
      const response = await fetch("/api/async/process-data", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify([
          "Record-1",
          "Record-2",
          "Record-3",
          "Record-4",
          "Record-5",
        ]),
      });
      const data = await response.json();
      addResult({
        taskId: "process-data",
        status: "completed",
        result: data,
        timestamp: new Date().toISOString(),
      });
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setLoading((prev) => ({ ...prev, [key]: false }));
    }
  };

  const getThreadPoolInfo = async () => {
    try {
      const response = await fetch(
        "/api/examples/multithreading/thread-pool-info"
      );
      const data = await response.json();
      setThreadPoolInfo(data);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const getScheduledInfo = async () => {
    try {
      const response = await fetch(
        "/api/examples/multithreading/scheduled-info"
      );
      const data = await response.json();
      setScheduledInfo(data);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div style={{ padding: "20px", maxWidth: "1400px", margin: "0 auto" }}>
      <h1>⚡ Spring Boot Async & Threading Demo</h1>
      <p style={{ color: "#666" }}>
        Modern async patterns using @Async, @Scheduled, and CompletableFuture
      </p>

      <div
        style={{
          display: "grid",
          gridTemplateColumns: "1fr 1fr",
          gap: "20px",
          marginTop: "20px",
        }}
      >
        {/* Actions */}
        <div>
          <div
            style={{
              border: "1px solid #ddd",
              borderRadius: "8px",
              padding: "20px",
              marginBottom: "20px",
            }}
          >
            <h3>@Async Patterns</h3>
            <div
              style={{ display: "flex", flexDirection: "column", gap: "10px" }}
            >
              <button
                onClick={fireAndForget}
                disabled={loading.fireAndForget}
                style={{
                  padding: "12px",
                  backgroundColor: loading.fireAndForget ? "#ccc" : "#4CAF50",
                  color: "white",
                  border: "none",
                  borderRadius: "4px",
                  cursor: loading.fireAndForget ? "not-allowed" : "pointer",
                  fontSize: "14px",
                }}
              >
                {loading.fireAndForget
                  ? "⏳ Processing..."
                  : "🚀 Fire and Forget"}
              </button>

              <button
                onClick={asyncWithResult}
                disabled={loading.withResult}
                style={{
                  padding: "12px",
                  backgroundColor: loading.withResult ? "#ccc" : "#2196F3",
                  color: "white",
                  border: "none",
                  borderRadius: "4px",
                  cursor: loading.withResult ? "not-allowed" : "pointer",
                  fontSize: "14px",
                }}
              >
                {loading.withResult ? "⏳ Waiting..." : "📥 Async with Result"}
              </button>

              <button
                onClick={parallelProcessing}
                disabled={loading.parallel}
                style={{
                  padding: "12px",
                  backgroundColor: loading.parallel ? "#ccc" : "#FF9800",
                  color: "white",
                  border: "none",
                  borderRadius: "4px",
                  cursor: loading.parallel ? "not-allowed" : "pointer",
                  fontSize: "14px",
                }}
              >
                {loading.parallel
                  ? "⏳ Processing..."
                  : "⚡ Parallel Processing"}
              </button>

              <button
                onClick={processData}
                disabled={loading.processData}
                style={{
                  padding: "12px",
                  backgroundColor: loading.processData ? "#ccc" : "#9C27B0",
                  color: "white",
                  border: "none",
                  borderRadius: "4px",
                  cursor: loading.processData ? "not-allowed" : "pointer",
                  fontSize: "14px",
                }}
              >
                {loading.processData
                  ? "⏳ Processing..."
                  : "📊 Process Data Async"}
              </button>
            </div>
          </div>

          <div
            style={{
              border: "1px solid #ddd",
              borderRadius: "8px",
              padding: "20px",
            }}
          >
            <h3>System Info</h3>
            <div
              style={{ display: "flex", flexDirection: "column", gap: "10px" }}
            >
              <button
                onClick={getThreadPoolInfo}
                style={{
                  padding: "12px",
                  backgroundColor: "#607D8B",
                  color: "white",
                  border: "none",
                  borderRadius: "4px",
                  cursor: "pointer",
                  fontSize: "14px",
                }}
              >
                📊 Thread Pool Info
              </button>

              <button
                onClick={getScheduledInfo}
                style={{
                  padding: "12px",
                  backgroundColor: "#795548",
                  color: "white",
                  border: "none",
                  borderRadius: "4px",
                  cursor: "pointer",
                  fontSize: "14px",
                }}
              >
                ⏰ Scheduled Tasks Info
              </button>
            </div>

            {threadPoolInfo && (
              <div
                style={{
                  marginTop: "15px",
                  padding: "10px",
                  backgroundColor: "#f5f5f5",
                  borderRadius: "4px",
                  fontSize: "13px",
                }}
              >
                <strong>Thread Pool:</strong>
                <div>Core Size: {threadPoolInfo.corePoolSize}</div>
                <div>Max Size: {threadPoolInfo.maxPoolSize}</div>
                <div>Active: {threadPoolInfo.activeCount}</div>
                <div>Queue Size: {threadPoolInfo.queueSize}</div>
              </div>
            )}

            {scheduledInfo && (
              <div
                style={{
                  marginTop: "15px",
                  padding: "10px",
                  backgroundColor: "#fff3e0",
                  borderRadius: "4px",
                  fontSize: "13px",
                }}
              >
                <strong>Scheduled Tasks:</strong>
                {scheduledInfo.tasks?.map((task: any, idx: number) => (
                  <div key={idx} style={{ marginTop: "5px" }}>
                    <div>
                      <strong>{task.name}</strong>
                    </div>
                    <div style={{ fontSize: "12px", color: "#666" }}>
                      {task.schedule} - Executed: {task.executionCount} times
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>

        {/* Results */}
        <div
          style={{
            border: "1px solid #ddd",
            borderRadius: "8px",
            padding: "20px",
          }}
        >
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              marginBottom: "15px",
            }}
          >
            <h3 style={{ margin: 0 }}>Results</h3>
            <button
              onClick={() => setResults([])}
              style={{
                padding: "6px 15px",
                backgroundColor: "#757575",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: "pointer",
                fontSize: "12px",
              }}
            >
              Clear
            </button>
          </div>

          <div
            style={{
              maxHeight: "600px",
              overflowY: "auto",
              display: "flex",
              flexDirection: "column",
              gap: "10px",
            }}
          >
            {results.length === 0 ? (
              <div
                style={{ textAlign: "center", color: "#999", padding: "40px" }}
              >
                No results yet. Try running some async operations!
              </div>
            ) : (
              results.map((result, idx) => (
                <div
                  key={idx}
                  style={{
                    padding: "15px",
                    backgroundColor: "#f9f9f9",
                    borderRadius: "4px",
                    border: "1px solid #e0e0e0",
                  }}
                >
                  <div
                    style={{
                      display: "flex",
                      justifyContent: "space-between",
                      marginBottom: "8px",
                    }}
                  >
                    <strong style={{ color: "#2196F3" }}>
                      {result.taskId}
                    </strong>
                    <span style={{ fontSize: "12px", color: "#666" }}>
                      {new Date(result.timestamp).toLocaleTimeString()}
                    </span>
                  </div>
                  <div
                    style={{
                      padding: "10px",
                      backgroundColor: "white",
                      borderRadius: "4px",
                      fontFamily: "monospace",
                      fontSize: "12px",
                      whiteSpace: "pre-wrap",
                      wordBreak: "break-word",
                      maxHeight: "200px",
                      overflowY: "auto",
                    }}
                  >
                    {JSON.stringify(result.result, null, 2)}
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>

      <div
        style={{
          marginTop: "30px",
          padding: "15px",
          backgroundColor: "#e3f2fd",
          borderRadius: "8px",
        }}
      >
        <h4>💡 Spring Boot Async Patterns:</h4>
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "1fr 1fr",
            gap: "15px",
            fontSize: "14px",
          }}
        >
          <div>
            <strong>🚀 Fire and Forget (@Async void)</strong>
            <p style={{ margin: "5px 0", color: "#666" }}>
              Execute task asynchronously without waiting for result. Perfect
              for logging, notifications, emails.
            </p>
          </div>
          <div>
            <strong>📥 Async with Result (CompletableFuture)</strong>
            <p style={{ margin: "5px 0", color: "#666" }}>
              Execute async and return result when done. Use for API calls, data
              processing.
            </p>
          </div>
          <div>
            <strong>⚡ Parallel Processing (CompletableFuture.allOf)</strong>
            <p style={{ margin: "5px 0", color: "#666" }}>
              Run multiple tasks concurrently and combine results. Great for
              batch operations.
            </p>
          </div>
          <div>
            <strong>⏰ Scheduled Tasks (@Scheduled)</strong>
            <p style={{ margin: "5px 0", color: "#666" }}>
              Cron-based recurring tasks. Use for cleanup, reports, monitoring.
            </p>
          </div>
        </div>
      </div>

      <div
        style={{
          marginTop: "15px",
          padding: "15px",
          backgroundColor: "#fff3e0",
          borderRadius: "8px",
        }}
      >
        <h4>⚙️ Configuration:</h4>
        <ul style={{ fontSize: "14px", lineHeight: "1.8", margin: "10px 0" }}>
          <li>
            <strong>@EnableAsync:</strong> Enables async method execution
          </li>
          <li>
            <strong>ThreadPoolTaskExecutor:</strong> Core: 5, Max: 10, Queue:
            100
          </li>
          <li>
            <strong>@Async:</strong> Methods run in separate threads from
            configured pool
          </li>
          <li>
            <strong>CompletableFuture:</strong> Handles async results and
            composition
          </li>
        </ul>
      </div>
    </div>
  );
}
