import { apiGet } from './client';

export interface MetricDTO {
  id: number;
  title: string;
  value: string;
}

export async function getMetrics(): Promise<MetricDTO[]> {
  // TEMP: mock replacement
  if (import.meta.env.DEV) {
    return Promise.resolve([
      { id: 1, title: 'Total Users', value: '1,234' },
      { id: 2, title: 'Active Users', value: '567' },
      { id: 3, title: 'Revenue', value: '$12,345' },
      { id: 4, title: 'Subscriptions', value: '89' },
    ]);
  }

  return apiGet<MetricDTO[]>('/metrics');
}
