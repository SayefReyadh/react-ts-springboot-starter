import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Student } from '../types/Student';
import { studentApi } from '../services/studentApi';

const StudentDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [student, setStudent] = useState<Student | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>('');

  useEffect(() => {
    const fetchStudent = async () => {
      if (!id) return;
      
      try {
        setLoading(true);
        const data = await studentApi.getStudentById(Number(id));
        setStudent(data);
        setError('');
      } catch (err) {
        setError('Failed to load student details');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchStudent();
  }, [id]);

  const handleBack = () => {
    navigate('/');
  };

  if (loading) {
    return (
      <div style={styles.container}>
        <div style={styles.loading}>Loading student details...</div>
      </div>
    );
  }

  if (error || !student) {
    return (
      <div style={styles.container}>
        <div style={styles.error}>{error || 'Student not found'}</div>
        <button onClick={handleBack} style={styles.backButton}>
          Back to List
        </button>
      </div>
    );
  }

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2>Student Details</h2>
        <div style={styles.detailsContainer}>
          <div style={styles.detailRow}>
            <span style={styles.label}>ID:</span>
            <span style={styles.value}>{student.id}</span>
          </div>
          <div style={styles.detailRow}>
            <span style={styles.label}>Name:</span>
            <span style={styles.value}>{student.name}</span>
          </div>
          <div style={styles.detailRow}>
            <span style={styles.label}>Email:</span>
            <span style={styles.value}>{student.email}</span>
          </div>
          <div style={styles.detailRow}>
            <span style={styles.label}>University ID:</span>
            <span style={styles.value}>{student.universityId}</span>
          </div>
        </div>
        <button onClick={handleBack} style={styles.backButton}>
          Back to List
        </button>
      </div>
    </div>
  );
};

const styles = {
  container: {
    maxWidth: '800px',
    margin: '0 auto',
    padding: '24px',
    fontFamily: 'system-ui, sans-serif',
  } as React.CSSProperties,
  card: {
    backgroundColor: 'white',
    padding: '30px',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
  } as React.CSSProperties,
  detailsContainer: {
    marginTop: '20px',
    marginBottom: '20px',
  } as React.CSSProperties,
  detailRow: {
    display: 'flex',
    padding: '12px 0',
    borderBottom: '1px solid #eee',
  } as React.CSSProperties,
  label: {
    fontWeight: 'bold' as const,
    width: '150px',
    color: '#555',
    fontSize: '16px',
  } as React.CSSProperties,
  value: {
    flex: 1,
    color: '#333',
    fontSize: '16px',
  } as React.CSSProperties,
  backButton: {
    padding: '10px 20px',
    backgroundColor: '#2196F3',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '14px',
    width: '100%',
    marginTop: '10px',
  } as React.CSSProperties,
  loading: {
    textAlign: 'center' as const,
    padding: '40px',
    fontSize: '16px',
    color: '#666',
  } as React.CSSProperties,
  error: {
    backgroundColor: '#ffebee',
    color: '#c62828',
    padding: '12px',
    borderRadius: '4px',
    marginBottom: '20px',
    textAlign: 'center' as const,
  } as React.CSSProperties,
};

export default StudentDetails;
