import React, { useEffect, useState } from 'react';
import { revenueApi } from '../api/revenueApi';
import type { RevenueMetric } from '../api/revenueApi';

const RevenuePage = () => {
  const [metrics, setMetrics] = useState<RevenueMetric[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchMetrics = async () => {
      setLoading(true);
      const data = await revenueApi.getRevenueMetrics();
      setMetrics(data);
      setLoading(false);
    };
    fetchMetrics();
  }, []);

  if (loading) return <div>Loading revenue metrics...</div>;

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">Revenue</h2>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        {metrics.map((m) => (
          <div key={m.id} className="bg-white p-4 shadow rounded">
            <h3 className="text-lg font-semibold">{m.name}</h3>
            <p className="text-2xl font-bold">{m.value}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default RevenuePage;
