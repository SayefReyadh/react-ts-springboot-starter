import { useEffect, useState } from 'react'

function App() {
  const [message, setMessage] = useState<string>('Loading...')

  useEffect(() => {
    // Use environment variable for API URL in production, empty string uses Vite proxy in dev
    const apiUrl = import.meta.env.VITE_API_URL || ''

    fetch(`${apiUrl}/api/hello`)
      .then(res => res.json())
      .then(data => setMessage(data.message))
      .catch(err => setMessage('Error: ' + err.message))
  }, [])

  return (
    <div style={{ padding: '24px', fontFamily: 'system-ui, sans-serif' }}>
      <h1>React + TypeScript + Spring Boot</h1>
      <p>Backend says: <strong>{message}</strong></p>
    </div>
  )
}

export default App
