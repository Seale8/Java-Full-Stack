import React, { FormEvent, useContext, useState } from 'react';
import { AuthContext } from './ReducerUserContext';
import UserInput from './FormInput/UserInput';
import UserInfo from './FormInput/UserInfo';
import axios from 'axios';
import { Link, Navigate, useNavigate } from 'react-router-dom';

export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
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

    // Reset error before new attempt
    setError('');

    if (!username || !password) {
        setError('Username and password are required');
        return;
    }

    try {
        const response = await axios.post('http://localhost:8080/login', {
            username,
            password,
        });

        const account = response.data; // Account object returned from Spring
        console.log('API Response:', account);

        // Dispatch the user data to the global context
        dispatch({ type: 'LOGIN', payload: { username: account.username, password: account.password, role: account.role,accountId: account.accountId}});

        navigate(account.role === 'manager' ? '/manager-dashboard' : '/employee-dashboard');

    } catch (error: any) {
        console.error('Error caught:', error);

        // Specific error handling
        if (error.message.includes('ERR_CONNECTION_REFUSED')) {
            setError('Unable to connect to the server. Please try again later.');
        } else if (error.response && error.response.status === 401) {
            setError(error.response.data);
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
        title = "Login"
      />
      <div className="mt-3">
        <p>
          Don't have an account?{' '}
          <Link to="/register" className="text-primary">
            Register here
          </Link>
        </p>
      </div>
    </div>
  );
}
