import { useEffect, useState } from 'react';
import { usersApi } from '../api/usersApi';
import type { User } from '../api/usersApi';

export const useUsers = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    usersApi.getUsers().then((data) => {
      setUsers(data);
      setLoading(false);
    });
  }, []);

  return { users, loading };
};
