export interface Metric {
  id: number;
  title: string;
  value: string;
}

export const mockMetrics: Metric[] = [
  { id: 1, title: 'Total Users', value: '1,234' },
  { id: 2, title: 'Active Users', value: '567' },
  { id: 3, title: 'Revenue', value: '$12,345' },
  { id: 4, title: 'Subscriptions', value: '89' },
];
