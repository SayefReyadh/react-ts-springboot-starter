import { useState, useEffect, useRef } from 'react'

interface Event {
  id: string
  data: string
  timestamp: string
}

export default function SSEDemo() {
  const [connected, setConnected] = useState(false)
  const [events, setEvents] = useState<Event[]>([])
  const [counterValue, setCounterValue] = useState(0)
  const [progressValue, setProgressValue] = useState(0)
  const [progressTaskId, setProgressTaskId] = useState('')
  const [isProcessing, setIsProcessing] = useState(false)
  const eventSourceRef = useRef<EventSource | null>(null)
  const counterSourceRef = useRef<EventSource | null>(null)
  const progressSourceRef = useRef<EventSource | null>(null)
  const eventsEndRef = useRef<HTMLDivElement>(null)

  const scrollToBottom = () => {
    eventsEndRef.current?.scrollIntoView({ behavior: 'smooth' })
  }

  useEffect(() => {
    scrollToBottom()
  }, [events])

  useEffect(() => {
    return () => {
      // Cleanup on unmount
      if (eventSourceRef.current) {
        eventSourceRef.current.close()
      }
      if (counterSourceRef.current) {
        counterSourceRef.current.close()
      }
      if (progressSourceRef.current) {
        progressSourceRef.current.close()
      }
    }
  }, [])

  const connectToStream = () => {
    if (eventSourceRef.current) {
      eventSourceRef.current.close()
    }

    const eventSource = new EventSource('/api/sse/stream')

    eventSource.onopen = () => {
      console.log('SSE Connection opened')
      setConnected(true)
      addEvent('System', 'Connected to SSE stream', 'connection')
    }

    eventSource.onmessage = (event) => {
      console.log('Received:', event.data)
      addEvent('Server', event.data, 'message')
    }

    eventSource.onerror = (error) => {
      console.error('SSE Error:', error)
      addEvent('System', 'Connection error occurred', 'error')
      setConnected(false)
    }

    eventSourceRef.current = eventSource
  }

  const disconnectFromStream = () => {
    if (eventSourceRef.current) {
      eventSourceRef.current.close()
      eventSourceRef.current = null
      setConnected(false)
      addEvent('System', 'Disconnected from SSE stream', 'connection')
    }
  }

  const addEvent = (source: string, data: string, type: string = 'message') => {
    const event: Event = {
      id: Date.now().toString(),
      data: `[${source}] ${data}`,
      timestamp: new Date().toISOString(),
    }
    setEvents((prev) => [...prev, event])
  }

  const startCounter = () => {
    if (counterSourceRef.current) {
      counterSourceRef.current.close()
    }

    const counterSource = new EventSource('/api/sse/counter')

    counterSource.onmessage = (event) => {
      const value = parseInt(event.data)
      setCounterValue(value)
      if (value >= 10) {
        counterSource.close()
        counterSourceRef.current = null
      }
    }

    counterSource.onerror = () => {
      counterSource.close()
      counterSourceRef.current = null
    }

    counterSourceRef.current = counterSource
    setCounterValue(0)
  }

  const startProgress = async () => {
    setIsProcessing(true)
    setProgressValue(0)

    try {
      // Start processing task
      const response = await fetch('/api/sse/process-data', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ items: ['Item1', 'Item2', 'Item3', 'Item4', 'Item5'] }),
      })

      const data = await response.json()
      const taskId = data.taskId

      setProgressTaskId(taskId)

      // Connect to progress stream
      if (progressSourceRef.current) {
        progressSourceRef.current.close()
      }

      const progressSource = new EventSource(`/api/sse/progress/${taskId}`)

      progressSource.addEventListener('progress', (event) => {
        const data = JSON.parse(event.data)
        setProgressValue(data.progress)

        if (data.progress >= 100) {
          setTimeout(() => {
            progressSource.close()
            progressSourceRef.current = null
            setIsProcessing(false)
          }, 1000)
        }
      })

      progressSource.addEventListener('complete', (event) => {
        console.log('Processing complete:', event.data)
        progressSource.close()
        progressSourceRef.current = null
        setIsProcessing(false)
      })

      progressSource.onerror = () => {
        progressSource.close()
        progressSourceRef.current = null
        setIsProcessing(false)
      }

      progressSourceRef.current = progressSource
    } catch (error) {
      console.error('Failed to start progress:', error)
      setIsProcessing(false)
    }
  }

  const sendBroadcast = async () => {
    const message = prompt('Enter broadcast message:')
    if (!message) return

    try {
      await fetch('/api/sse/broadcast', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message }),
      })
      addEvent('You', `Broadcast sent: ${message}`, 'broadcast')
    } catch (error) {
      console.error('Failed to send broadcast:', error)
      addEvent('System', 'Failed to send broadcast', 'error')
    }
  }

  const clearEvents = () => {
    setEvents([])
  }

  return (
    <div style={{ padding: '20px', maxWidth: '1200px', margin: '0 auto' }}>
      <h1>📡 Server-Sent Events (SSE) Demo</h1>
      <p style={{ color: '#666' }}>
        Real-time server-to-client streaming using Spring Boot SSE
      </p>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px', marginTop: '20px' }}>
        {/* Main Stream */}
        <div style={{ border: '1px solid #ddd', borderRadius: '8px', padding: '20px' }}>
          <h3>Main Event Stream</h3>
          <div style={{ marginBottom: '15px', display: 'flex', gap: '10px' }}>
            {!connected ? (
              <button
                onClick={connectToStream}
                style={{
                  padding: '10px 20px',
                  backgroundColor: '#4CAF50',
                  color: 'white',
                  border: 'none',
                  borderRadius: '4px',
                  cursor: 'pointer',
                  fontSize: '14px',
                }}
              >
                Connect to Stream
              </button>
            ) : (
              <>
                <button
                  onClick={disconnectFromStream}
                  style={{
                    padding: '10px 20px',
                    backgroundColor: '#f44336',
                    color: 'white',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                    fontSize: '14px',
                  }}
                >
                  Disconnect
                </button>
                <button
                  onClick={sendBroadcast}
                  style={{
                    padding: '10px 20px',
                    backgroundColor: '#2196F3',
                    color: 'white',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                    fontSize: '14px',
                  }}
                >
                  Send Broadcast
                </button>
              </>
            )}
            <button
              onClick={clearEvents}
              style={{
                padding: '10px 20px',
                backgroundColor: '#757575',
                color: 'white',
                border: 'none',
                borderRadius: '4px',
                cursor: 'pointer',
                fontSize: '14px',
              }}
            >
              Clear
            </button>
          </div>

          <div
            style={{
              border: '1px solid #ddd',
              borderRadius: '4px',
              padding: '15px',
              height: '300px',
              overflowY: 'auto',
              backgroundColor: '#f9f9f9',
              fontFamily: 'monospace',
              fontSize: '13px',
            }}
          >
            {events.length === 0 ? (
              <div style={{ color: '#999', textAlign: 'center', marginTop: '50px' }}>
                No events yet. Connect to start receiving events.
              </div>
            ) : (
              events.map((event) => (
                <div key={event.id} style={{ marginBottom: '8px', padding: '5px', borderRadius: '3px', backgroundColor: 'white' }}>
                  <div style={{ fontSize: '11px', color: '#666' }}>
                    {new Date(event.timestamp).toLocaleTimeString()}
                  </div>
                  <div>{event.data}</div>
                </div>
              ))
            )}
            <div ref={eventsEndRef} />
          </div>

          <div style={{ marginTop: '10px', fontSize: '14px', color: connected ? '#4CAF50' : '#f44336' }}>
            Status: {connected ? '🟢 Connected' : '🔴 Disconnected'}
          </div>
        </div>

        {/* Counter & Progress */}
        <div>
          {/* Counter Demo */}
          <div style={{ border: '1px solid #ddd', borderRadius: '8px', padding: '20px', marginBottom: '20px' }}>
            <h3>Counter Demo</h3>
            <p style={{ fontSize: '14px', color: '#666' }}>
              Receives numbers 0-10 from server every second
            </p>
            <div
              style={{
                fontSize: '48px',
                fontWeight: 'bold',
                textAlign: 'center',
                margin: '20px 0',
                color: '#2196F3',
              }}
            >
              {counterValue}
            </div>
            <button
              onClick={startCounter}
              disabled={counterSourceRef.current !== null}
              style={{
                width: '100%',
                padding: '10px',
                backgroundColor: counterSourceRef.current ? '#ccc' : '#2196F3',
                color: 'white',
                border: 'none',
                borderRadius: '4px',
                cursor: counterSourceRef.current ? 'not-allowed' : 'pointer',
                fontSize: '14px',
              }}
            >
              {counterSourceRef.current ? 'Counting...' : 'Start Counter'}
            </button>
          </div>

          {/* Progress Demo */}
          <div style={{ border: '1px solid #ddd', borderRadius: '8px', padding: '20px' }}>
            <h3>Progress Tracking</h3>
            <p style={{ fontSize: '14px', color: '#666' }}>
              Monitor long-running task progress
            </p>
            <div style={{ margin: '20px 0' }}>
              <div
                style={{
                  width: '100%',
                  height: '30px',
                  backgroundColor: '#e0e0e0',
                  borderRadius: '15px',
                  overflow: 'hidden',
                }}
              >
                <div
                  style={{
                    width: `${progressValue}%`,
                    height: '100%',
                    backgroundColor: progressValue === 100 ? '#4CAF50' : '#2196F3',
                    transition: 'width 0.3s ease',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    color: 'white',
                    fontWeight: 'bold',
                    fontSize: '14px',
                  }}
                >
                  {progressValue > 10 && `${progressValue}%`}
                </div>
              </div>
              <div style={{ textAlign: 'center', marginTop: '10px', fontSize: '18px', fontWeight: 'bold' }}>
                {progressValue}% Complete
              </div>
              {progressTaskId && (
                <div style={{ textAlign: 'center', fontSize: '12px', color: '#666', marginTop: '5px' }}>
                  Task ID: {progressTaskId}
                </div>
              )}
            </div>
            <button
              onClick={startProgress}
              disabled={isProcessing}
              style={{
                width: '100%',
                padding: '10px',
                backgroundColor: isProcessing ? '#ccc' : '#FF9800',
                color: 'white',
                border: 'none',
                borderRadius: '4px',
                cursor: isProcessing ? 'not-allowed' : 'pointer',
                fontSize: '14px',
              }}
            >
              {isProcessing ? 'Processing...' : 'Start Process'}
            </button>
          </div>
        </div>
      </div>

      <div style={{ marginTop: '30px', padding: '15px', backgroundColor: '#e8f5e9', borderRadius: '8px' }}>
        <h4>💡 Technical Details:</h4>
        <ul style={{ fontSize: '14px', lineHeight: '1.8' }}>
          <li><strong>Protocol:</strong> Server-Sent Events (SSE) over HTTP</li>
          <li><strong>Backend:</strong> Spring Boot SseEmitter</li>
          <li><strong>Endpoints:</strong> /stream (continuous), /counter (demo), /progress/:taskId (tracking)</li>
          <li><strong>Use Cases:</strong> Live updates, notifications, progress tracking, real-time dashboards</li>
          <li><strong>Advantages:</strong> Automatic reconnection, simple HTTP, server-to-client only</li>
        </ul>
      </div>
    </div>
  )
}
