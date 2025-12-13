import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Student } from '../types/Student'
import { studentApi } from '../services/studentApi'
import StudentForm from '../components/StudentForm'
import StudentList from '../components/StudentList'

function HomePage() {
  const [students, setStudents] = useState<Student[]>([])
  const [selectedStudent, setSelectedStudent] = useState<Student | null>(null)
  const [loading, setLoading] = useState<boolean>(true)
  const [error, setError] = useState<string>('')

  useEffect(() => {
    loadStudents()
  }, [])

  const loadStudents = async () => {
    try {
      setLoading(true)
      setError('')
      const data = await studentApi.getAllStudents()
      setStudents(data)
    } catch (err) {
      setError('Failed to load students')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  const handleCreateStudent = async (student: Omit<Student, 'id'>) => {
    try {
      await studentApi.createStudent(student)
      loadStudents()
      setError('')
    } catch (err) {
      setError('Failed to create student')
      console.error(err)
    }
  }

  const handleUpdateStudent = async (student: Omit<Student, 'id'>) => {
    if (selectedStudent?.id) {
      try {
        await studentApi.updateStudent(selectedStudent.id, student)
        loadStudents()
        setSelectedStudent(null)
        setError('')
      } catch (err) {
        setError('Failed to update student')
        console.error(err)
      }
    }
  }

  const handleDeleteStudent = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this student?')) {
      try {
        await studentApi.deleteStudent(id)
        loadStudents()
        setError('')
      } catch (err) {
        setError('Failed to delete student')
        console.error(err)
      }
    }
  }

  const handleEdit = (student: Student) => {
    setSelectedStudent(student)
  }

  const handleCancelEdit = () => {
    setSelectedStudent(null)
  }

  return (
    <div style={styles.container}>
      <div style={styles.heroSection}>
        <h1 style={styles.heroTitle}>🚀 Spring Boot + React Starter</h1>
        <p style={styles.heroSubtitle}>
          Full-stack application with modern async patterns, WebSocket, and SSE demos
        </p>
        
        <div style={styles.demoCards}>
          <Link to="/websocket" style={styles.demoCard}>
            <div style={styles.demoIcon}>🔌</div>
            <h3 style={styles.demoCardTitle}>WebSocket Demo</h3>
            <p style={styles.demoCardDesc}>Real-time bidirectional chat with STOMP protocol</p>
          </Link>
          
          <Link to="/sse" style={styles.demoCard}>
            <div style={styles.demoIcon}>📡</div>
            <h3 style={styles.demoCardTitle}>SSE Demo</h3>
            <p style={styles.demoCardDesc}>Server-sent events for live updates and progress tracking</p>
          </Link>
          
          <Link to="/async" style={styles.demoCard}>
            <div style={styles.demoIcon}>⚡</div>
            <h3 style={styles.demoCardTitle}>Async Demo</h3>
            <p style={styles.demoCardDesc}>@Async, @Scheduled, and CompletableFuture patterns</p>
          </Link>
        </div>
      </div>

      <h2 style={styles.title}>Student Management System</h2>
      
      {error && <div style={styles.error}>{error}</div>}
      
      <StudentForm
        student={selectedStudent}
        onSubmit={selectedStudent ? handleUpdateStudent : handleCreateStudent}
        onCancel={handleCancelEdit}
      />
      
      {loading ? (
        <div style={styles.loading}>Loading students...</div>
      ) : (
        <StudentList
          students={students}
          onEdit={handleEdit}
          onDelete={handleDeleteStudent}
        />
      )}
    </div>
  )
}

const styles = {
  container: {
    maxWidth: '1400px',
    margin: '0 auto',
    padding: '24px',
    fontFamily: 'system-ui, sans-serif',
  } as React.CSSProperties,
  heroSection: {
    textAlign: 'center' as const,
    padding: '40px 20px',
    background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    borderRadius: '12px',
    marginBottom: '40px',
    color: 'white',
  } as React.CSSProperties,
  heroTitle: {
    fontSize: '42px',
    fontWeight: 'bold',
    margin: '0 0 15px 0',
  } as React.CSSProperties,
  heroSubtitle: {
    fontSize: '18px',
    opacity: 0.9,
    margin: '0 0 30px 0',
  } as React.CSSProperties,
  demoCards: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
    gap: '20px',
    marginTop: '30px',
  } as React.CSSProperties,
  demoCard: {
    backgroundColor: 'white',
    padding: '30px 20px',
    borderRadius: '8px',
    textDecoration: 'none',
    color: '#333',
    transition: 'transform 0.3s, box-shadow 0.3s',
    cursor: 'pointer',
    boxShadow: '0 4px 6px rgba(0,0,0,0.1)',
  } as React.CSSProperties,
  demoIcon: {
    fontSize: '48px',
    marginBottom: '15px',
  } as React.CSSProperties,
  demoCardTitle: {
    fontSize: '20px',
    fontWeight: 'bold',
    margin: '0 0 10px 0',
    color: '#2c3e50',
  } as React.CSSProperties,
  demoCardDesc: {
    fontSize: '14px',
    color: '#7f8c8d',
    margin: 0,
    lineHeight: '1.5',
  } as React.CSSProperties,
  title: {
    textAlign: 'center' as const,
    color: '#333',
    marginBottom: '30px',
    marginTop: '20px',
  } as React.CSSProperties,
  error: {
    backgroundColor: '#ffebee',
    color: '#c62828',
    padding: '12px',
    borderRadius: '4px',
    marginBottom: '20px',
    textAlign: 'center' as const,
  } as React.CSSProperties,
  loading: {
    textAlign: 'center' as const,
    padding: '20px',
    fontSize: '16px',
    color: '#666',
  } as React.CSSProperties,
}

export default HomePage
