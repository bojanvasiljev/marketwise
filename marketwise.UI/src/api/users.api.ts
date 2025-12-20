import { apiGet } from './client';

export interface UserDTO {
  id: string;
  name: string;
  email: string;
  status: 'active' | 'inactive';
}

export async function getUsers(): Promise<UserDTO[]> {
  return apiGet<UserDTO[]>('/users');
}
