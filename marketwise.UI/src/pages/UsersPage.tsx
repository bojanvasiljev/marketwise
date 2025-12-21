import React, { useEffect, useState } from 'react';
import { usersApi } from '../api/usersApi';

type User = {
  id: number;
  username: string;
  email: string;
  passwordHash: string;
  createDate: string;
};

const UsersPage = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        setLoading(true);
        const data: User[] = await usersApi.getUsers();

        if (!Array.isArray(data)) {
          console.error('API did not return an array', data);
          setUsers([]);
          return;
        }

        setUsers(data);
      } catch (err) {
        console.error(err);
        setUsers([]);
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  if (loading) return <div>Loading users...</div>;

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">Users</h2>
      {users.length === 0 ? (
        <div>No users found</div>
      ) : (
        <table className="min-w-full bg-white shadow rounded">
          <thead>
            <tr>
              <th className="px-6 py-3 border-b">ID</th>
              <th className="px-6 py-3 border-b">Username</th>
              <th className="px-6 py-3 border-b">Email</th>
              <th className="px-6 py-3 border-b">Created At</th>
            </tr>
          </thead>
          <tbody>
            {users.map(user => (
              <tr key={user.id}>
                <td className="px-6 py-4 border-b">{user.id}</td>
                <td className="px-6 py-4 border-b">{user.username}</td>
                <td className="px-6 py-4 border-b">{user.email}</td>
                <td className="px-6 py-4 border-b">{new Date(user.createDate).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default UsersPage;
