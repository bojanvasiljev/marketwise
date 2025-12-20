import React from 'react';
import { NavLink } from 'react-router-dom';
import { useAuth } from '../auth/useAuth';

const Sidebar = () => {
  const { user } = useAuth();

  const links = [
    { name: 'Dashboard', path: '/' },
    ...(user?.role === 'admin' ? [{ name: 'Users', path: '/users' }] : []),
    { name: 'Revenue', path: '/revenue' },
    { name: 'Settings', path: '/settings' },
  ];

  return (
    <aside className="w-64 bg-white shadow-md min-h-screen p-6 hidden md:block">
      <h2 className="text-2xl font-bold mb-6">Marketwise</h2>
      <nav className="flex flex-col gap-3">
        {links.map((link) => (
          <NavLink
            key={link.name}
            to={link.path}
            className={({ isActive }) =>
              `px-4 py-2 rounded hover:bg-gray-100 ${
                isActive ? 'bg-gray-200 font-semibold' : ''
              }`
            }
          >
            {link.name}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
};

export default Sidebar;
