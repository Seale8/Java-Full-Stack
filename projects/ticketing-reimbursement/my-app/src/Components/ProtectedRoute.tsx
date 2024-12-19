import { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { AuthContext } from './ReducerUserContext';

interface ProtectedRouteProps {
    allowedRoles: ('employee' | 'manager')[];
}

export function ProtectedRoute({ allowedRoles }: ProtectedRouteProps) {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error('AuthContext must be used within an AuthProvider');
    }

    const { state } = context;

    // Debugging: Check the current user's role
    console.log('Current user:', state.user);

    // If no user is logged in, redirect to login
    if (!state.user) {
        console.log('No user found, redirecting to login');
        return <Navigate to="/login" replace />;
    }
    if (!allowedRoles.includes(state.user.role)) {
        const redirectPath = state.user.role === 'employee' ? '/employee-dashboard' : '/manager-dashboard';
        console.log(`Redirecting unauthorized user to their dashboard: ${redirectPath}`);
        return <Navigate to={redirectPath} replace />;
    }
  

    // Allow access
    return <Outlet />;
}
