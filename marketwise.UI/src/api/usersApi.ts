export type User = {
  id: string;
  name: string;
  email: string;
  role: 'admin' | 'user';
};

const API_BASE = 'http://localhost:8080/api'; // replace with real endpoint

export const usersApi = {
  getUsers: async (): Promise<User[]> => {
    const res = await fetch(`${API_BASE}/users`, {
      credentials: 'include', // include cookies if needed
    });
    if (!res.ok) throw new Error('Failed to fetch users');
    return res.json();
  },

  updateUser: async (user: User): Promise<User> => {
    const res = await fetch(`${API_BASE}/users/${user.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(user),
    });
    if (!res.ok) throw new Error('Failed to update user');
    return res.json();
  },

  deleteUser: async (id: string): Promise<void> => {
    const res = await fetch(`${API_BASE}/users/${id}`, {
      method: 'DELETE',
      credentials: 'include',
    });
    if (!res.ok) throw new Error('Failed to delete user');
  },
};
