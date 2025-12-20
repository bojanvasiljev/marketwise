export type User = {
  id: string;
  name: string;
  email: string;
  role: 'admin' | 'user';
};

export const usersApi = {
  getUsers: async (): Promise<User[]> => {
    // simulate network delay
    await new Promise((res) => setTimeout(res, 500));

    return [
      { id: '1', name: 'Bojan Vasiljev', email: 'bojan@marketwise.com', role: 'admin' },
      { id: '2', name: 'Mila Vasiljev', email: 'mila@marketwise.com', role: 'user' },
      { id: '3', name: 'Anastasia Vasiljev', email: 'anastasia@marketwise.com', role: 'user' },
    ];
  },

  updateUser: async (updatedUser: User): Promise<User> => {
    await new Promise((res) => setTimeout(res, 300));
    return updatedUser; // In real API, would persist changes
  },

  deleteUser: async (id: string): Promise<void> => {
    await new Promise((res) => setTimeout(res, 300));
    return;
  },
};
