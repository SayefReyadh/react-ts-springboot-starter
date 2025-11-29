import { useEffect, useState } from 'react'
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
      <h1 style={styles.title}>Student Management System</h1>
      
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
    maxWidth: '1200px',
    margin: '0 auto',
    padding: '24px',
    fontFamily: 'system-ui, sans-serif',
  } as React.CSSProperties,
  title: {
    textAlign: 'center' as const,
    color: '#333',
    marginBottom: '30px',
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
