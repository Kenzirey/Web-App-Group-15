<template>
  <div class="admin-container">
    <AdminNavigation />
    <div class="admin-content">
      <header class="admin-header">
        <h1>Admin Dashboard</h1>
      </header>
      <div class="summary-cards">
        <div class="card total-courses" @click="goToCourses">
          <div class="card-icon"><i class="fas fa-book"></i></div>
          <div class="card-details">
            <p class="card-title">Total Courses</p>
            <p class="card-value">{{ totalCourses }}</p>
          </div>
        </div>
        <div class="card total-users" @click="goToUsers">
          <div class="card-icon"><i class="fas fa-user"></i></div>
          <div class="card-details">
            <p class="card-title">Total Users</p>
            <p class="card-value">{{ totalUsers }}</p>
          </div>
        </div>
        <div class="card total-users-2fa" @click="goTo2FAUsers">
          <div class="card-icon"><i class="fas fa-shield-alt"></i></div>
          <div class="card-details">
            <p class="card-title">Users with 2FA</p>
            <p class="card-value">{{ totalUsersWith2FA }}</p>
          </div>
        </div>
      </div>
      <div class="chart-container">
        <canvas id="growthChart"></canvas>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

export default {
  name: 'AdminDashboard',
  data() {
    return {
      totalCourses: 0,
      totalUsers: 0,
      totalUsersWith2FA: 0,
      chartData: {
        labels: ['January', 'February', 'March', 'April'], // Placeholder labels
        datasets: [
          {
            label: 'Total Users',
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255, 99, 132, 1)',
            data: [50, 150, 100, 200], // Placeholder data
          },
          {
            label: 'Users with 2FA',
            backgroundColor: 'rgba(54, 162, 235, 0.2)',
            borderColor: 'rgba(54, 162, 235, 1)',
            data: [30, 90, 70, 150], // Placeholder data
          },
          {
            label: 'Total Courses',
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            data: [4, 6, 8, 10], // Placeholder data
          },
        ],
      },
    };
  },
  created() {
    this.fetchSummaryData();
  },
  mounted() {
    this.initChart();
  },
  methods: {
    async fetchSummaryData() {

      this.initChart();
    },
    initChart() {
      const ctx = document.getElementById('growthChart').getContext('2d');
      new Chart(ctx, {
        type: 'line',
        data: this.chartData,
        options: {
          scales: {
            y: {
              beginAtZero: true,
            },
          },
          maintainAspectRatio: false,
        },
      });
    },
    goToCourses() {
    },
    goToUsers() {
    },
    goTo2FAUsers() {
    },
  },
};
</script>

<style scoped>
.admin-container {
  display: flex;
}

.admin-header {
  text-align: center;
  padding: 20px 0;
  border-bottom: 2px solid #eee;
  margin-bottom: 20px;
}

.admin-content {
  flex-grow: 1;
  padding: 20px;
  background-color: #f9f9f9;
}

.summary-cards {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.card {
  background: #fff;
  border: 1px solid #d3d3d3;
  border-radius: 10px;
  padding: 15px;
  cursor: pointer;
  transition: box-shadow 0.3s ease;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card-icon {
  color: #4a90e2;
  font-size: 2rem;
}

.card-title {
  margin: 10px 0 5px;
  color: #333;
  font-size: 1rem;
}

.card-value {
  color: #333;
  font-size: 2rem;
  font-weight: bold;
}

.chart-container {
  background: #fff;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  margin-top: 20px;
}


#growthChart {
  width: 100% !important;
  height: 40vh !important;
}
</style>
