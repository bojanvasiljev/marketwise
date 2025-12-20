import React from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../components/Sidebar';
import { useAuth } from '../auth/useAuth';
import { useNavigate } from 'react-router-dom';

const AppLayout = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  
  return (
    <div className="flex min-h-screen bg-gray-100">
      <Sidebar />

      <div className="flex-1 flex flex-col">
        {/* Header */}
        <header className="bg-white shadow px-8 py-4 flex justify-between items-center">
          <h1 className="text-2xl font-bold text-gray-800">Marketwise</h1>

          {user && (
            <div className="flex items-center gap-4">
              <span className="text-gray-600">{user.name}</span>
              <button
                onClick={() => {
                  logout();
                  navigate('/login');
                }}
                className="text-sm bg-gray-200 px-3 py-1 rounded hover:bg-gray-300"
              >
                Logout
              </button>
            </div>
          )}
        </header>

        {/* Page Content */}
        <main className="flex-1 p-8">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default AppLayout;
