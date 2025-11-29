import { Student } from '../types/Student';

const API_BASE_URL = '/api/students';

export const studentApi = {
  // Get all students
  getAllStudents: async (): Promise<Student[]> => {
    const response = await fetch(API_BASE_URL);
    if (!response.ok) {
      throw new Error('Failed to fetch students');
    }
    return response.json();
  },

  // Get student by ID
  getStudentById: async (id: number): Promise<Student> => {
    const response = await fetch(`${API_BASE_URL}/${id}`);
    if (!response.ok) {
      throw new Error('Failed to fetch student');
    }
    return response.json();
  },

  // Create new student
  createStudent: async (student: Omit<Student, 'id'>): Promise<Student> => {
    const response = await fetch(API_BASE_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(student),
    });
    if (!response.ok) {
      throw new Error('Failed to create student');
    }
    return response.json();
  },

  // Update student
  updateStudent: async (id: number, student: Omit<Student, 'id'>): Promise<Student> => {
    const response = await fetch(`${API_BASE_URL}/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(student),
    });
    if (!response.ok) {
      throw new Error('Failed to update student');
    }
    return response.json();
  },

  // Delete student
  deleteStudent: async (id: number): Promise<void> => {
    const response = await fetch(`${API_BASE_URL}/${id}`, {
      method: 'DELETE',
    });
    if (!response.ok) {
      throw new Error('Failed to delete student');
    }
  },
};
