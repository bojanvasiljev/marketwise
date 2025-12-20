import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from './useAuth';

type ProtectedRouteProps = {
  roles?: string[];
  children: React.ReactNode;
};

const ProtectedRoute = ({ roles, children }: ProtectedRouteProps) => {
  const { user } = useAuth();

  if (!user) return <Navigate to="/login" replace />;

  if (roles && !roles.includes(user.role)) {
    return (
      <div className="p-8 text-red-500">
        <h2 className="text-xl font-bold mb-2">Access Denied</h2>
        <p>You do not have permission to view this page.</p>
      </div>
    );
  }

  return <>{children}</>;
};

export default ProtectedRoute;
