import { useEffect, useState } from 'react';
import ChartCard from '../components/ChartCard';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  BarChart,
  Bar,
} from 'recharts';

import { getMetrics } from '../api/metrics.api';
import type { MetricDTO } from '../api/metrics.api';
import { userGrowthData, revenueData } from '../data/mockCharts';

const Dashboard = () => {
  // 🔹 1️⃣ COMPONENT LOGIC (STATE + EFFECTS)

  const [metrics, setMetrics] = useState<MetricDTO[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getMetrics()
      .then(setMetrics)
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  // 🔹 2️⃣ JSX (WHAT RENDERS ON SCREEN)
  return (
    <>
      {/* Metric Cards */}
      {loading ? (
        <div className="text-gray-500 mb-6">Loading metrics...</div>
      ) : (
        <section className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
          {metrics.map((metric) => (
            <div key={metric.id} className="bg-white shadow-md rounded-lg p-6">
              <h2 className="text-lg font-semibold text-gray-600">
                {metric.title}
              </h2>
              <p className="text-3xl font-bold mt-3">{metric.value}</p>
            </div>
          ))}
        </section>
      )}

      {/* Charts */}
      <section className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <ChartCard title="User Growth">
          <ResponsiveContainer width="100%" height={250}>
            <LineChart data={userGrowthData}>
              <XAxis dataKey="month" />
              <YAxis />
              <Tooltip />
              <Line type="monotone" dataKey="users" strokeWidth={3} />
            </LineChart>
          </ResponsiveContainer>
        </ChartCard>

        <ChartCard title="Revenue">
          <ResponsiveContainer width="100%" height={250}>
            <BarChart data={revenueData}>
              <XAxis dataKey="month" />
              <YAxis />
              <Tooltip />
              <Bar dataKey="revenue" />
            </BarChart>
          </ResponsiveContainer>
        </ChartCard>
      </section>
    </>
  );
};

export default Dashboard;
