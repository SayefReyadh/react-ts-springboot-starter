import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Student } from '../types/Student';

interface StudentListProps {
  students: Student[];
  onEdit: (student: Student) => void;
  onDelete: (id: number) => void;
}

const StudentList: React.FC<StudentListProps> = ({ students, onEdit, onDelete }) => {
  const navigate = useNavigate();

  if (students.length === 0) {
    return <div style={styles.emptyMessage}>No students found. Add your first student!</div>;
  }

  const handleView = (id: number) => {
    navigate(`/student/${id}`);
  };

  return (
    <div style={styles.container}>
      <h2>Student List</h2>
      <table style={styles.table}>
        <thead>
          <tr>
            <th style={styles.th}>ID</th>
            <th style={styles.th}>Name</th>
            <th style={styles.th}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {students.map((student) => (
            <tr key={student.id} style={styles.tr}>
              <td style={styles.td}>{student.id}</td>
              <td style={styles.td}>{student.name}</td>
              <td style={styles.td}>
                <div style={styles.actionButtons}>
                  <button
                    onClick={() => student.id && handleView(student.id)}
                    style={styles.viewButton}
                  >
                    View
                  </button>
                  <button
                    onClick={() => onEdit(student)}
                    style={styles.editButton}
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => student.id && onDelete(student.id)}
                    style={styles.deleteButton}
                  >
                    Delete
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

const styles = {
  container: {
    marginTop: '20px',
  } as React.CSSProperties,
  emptyMessage: {
    textAlign: 'center' as const,
    padding: '20px',
    color: '#666',
    fontSize: '16px',
  } as React.CSSProperties,
  table: {
    width: '100%',
    borderCollapse: 'collapse' as const,
    backgroundColor: 'white',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
  } as React.CSSProperties,
  th: {
    backgroundColor: '#4CAF50',
    color: 'white',
    padding: '12px',
    textAlign: 'left' as const,
    fontWeight: 'bold' as const,
  } as React.CSSProperties,
  tr: {
    borderBottom: '1px solid #ddd',
  } as React.CSSProperties,
  td: {
    padding: '12px',
  } as React.CSSProperties,
  actionButtons: {
    display: 'flex',
    gap: '8px',
  } as React.CSSProperties,
  viewButton: {
    padding: '6px 12px',
    backgroundColor: '#2196F3',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '12px',
  } as React.CSSProperties,
  editButton: {
    padding: '6px 12px',
    backgroundColor: '#ff9800',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '12px',
  } as React.CSSProperties,
  deleteButton: {
    padding: '6px 12px',
    backgroundColor: '#f44336',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '12px',
  } as React.CSSProperties,
};

export default StudentList;
