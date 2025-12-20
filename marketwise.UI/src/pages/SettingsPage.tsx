import React, { useState } from 'react';
import { settingsApi } from '../api/settingsApi';
import type { UserSettings } from '../api/settingsApi';

const SettingsPage = () => {
  const [theme, setTheme] = useState<'light' | 'dark'>('light');
  const [notifications, setNotifications] = useState(true);

  const handleThemeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setTheme(e.target.value as 'light' | 'dark');
  };

  const handleNotificationsChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNotifications(e.target.checked);
  };

  const handleSave = () => {
    alert('Settings saved!');
    // In future: save to API or localStorage
  };

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">Settings</h2>

      <div className="bg-white p-6 rounded shadow-md max-w-md">
        <div className="mb-4">
          <label className="block font-semibold mb-1">Theme</label>
          <select
            value={theme}
            onChange={handleThemeChange}
            className="border px-3 py-2 rounded w-full"
          >
            <option value="light">Light</option>
            <option value="dark">Dark</option>
          </select>
        </div>

        <div className="mb-4">
          <label className="flex items-center gap-2">
            <input
              type="checkbox"
              checked={notifications}
              onChange={handleNotificationsChange}
            />
            Enable notifications
          </label>
        </div>

        <button
          onClick={handleSave}
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
        >
          Save Settings
        </button>
      </div>
    </div>
  );
};

export default SettingsPage;
