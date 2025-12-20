export type RevenueMetric = {
  id: string;
  name: string;
  value: string;
};

export const revenueApi = {
  getRevenueMetrics: async (): Promise<RevenueMetric[]> => {
    await new Promise((res) => setTimeout(res, 500)); // simulate delay
    return [
      { id: '1', name: 'Total Revenue', value: '$120,000' },
      { id: '2', name: 'Monthly Revenue', value: '$10,500' },
      { id: '3', name: 'Active Subscriptions', value: '450' },
    ];
  },
};
