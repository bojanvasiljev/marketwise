export type UserSettings = {
  theme: 'light' | 'dark';
  notifications: boolean;
};

const API_BASE = 'http://localhost:8080/api'; // replace with real endpoint

export const settingsApi = {
  getSettings: async (): Promise<UserSettings> => {
    const res = await fetch(`${API_BASE}/settings`, {
      credentials: 'include',
    });
    if (!res.ok) throw new Error('Failed to fetch settings');
    return res.json();
  },

  updateSettings: async (settings: UserSettings): Promise<UserSettings> => {
    const res = await fetch(`${API_BASE}/settings`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(settings),
    });
    if (!res.ok) throw new Error('Failed to update settings');
    return res.json();
  },
};
