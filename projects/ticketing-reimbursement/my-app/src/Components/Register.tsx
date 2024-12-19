import React, { FormEvent, useContext, useState } from 'react'
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { AuthContext } from './ReducerUserContext';
import axios from 'axios';
import UserInput from './FormInput/UserInput';

export default function Register() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  const context = useContext(AuthContext);
  const navigate = useNavigate(); // Initialize useNavigate



  if (!context) {
    throw new Error('AuthContext must be used within an AuthProvider');
  }

  const { state, dispatch } = context;

   // If the user is already logged in, redirect them to their dashboard
   if (state.user) {
    const redirectPath = state.user.role === 'employee' ? '/employee-dashboard' : '/manager-dashboard';
    return <Navigate to={redirectPath} replace />;
  }

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    setError('');
    setSuccess(false);

    if (!username || !password) {
      setError('All fields are required');
      return;
    }

    try {
      const response = await axios.post('http://localhost:8080/register', {
        username,
        password,
        role: 'employee',
      });

      console.log('Registration successful:', response.data);
      setSuccess(true);
      dispatch({ type: 'LOGIN', payload: { username, password, role: 'employee' } });

      // Navigate to the login page after successful registration
    } catch (error: any) {
      console.error('Error during registration:', error);

      if (error.response && error.response.status === 400) {
        setError('Username already exists. Please try a different one.');
      } else {
        setError('An unexpected error occurred. Please try again later.');
      }
    }
  }

  return (
    <div className="container mt-5">
    
      {error && (
        <div className="mb-3">
          
          <div
            className="alert alert-danger"
            role="alert"
            style={{ padding: '10px', fontWeight: 'bold', fontSize: '14px' }}
          >
            {error}
          </div>
        </div>
      )}
      <UserInput
        username={username}
        setUsername={setUsername}
        password={password}
        setPassword={setPassword}
        handleSubmit={handleSubmit}
        title = "Register"
      />
      <div className="mt-3">
        <p>
          Don't have an account?{' '}
          <Link to="/login" className="text-primary">
            Login here
          </Link>
        </p>
      </div>
    </div>
   
  );
}
