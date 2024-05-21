<template>
  <v-container fluid class="pa-0 fill-height">
    <v-row class="fill-height" no-gutters>
      <v-col cols="12">

        <!-- Admin Dashboard Title -->
        <v-row class="title-container">
          <v-col>
            <h1 class="title">Admin Dashboard</h1>
          </v-col>
        </v-row>

        <!-- Navigation Items -->
        <v-container>
          <v-row class="navigation-container">
            <v-btn text @click="goToDashboard">Dashboard</v-btn>
            <v-btn text @click="goToCourses">Courses</v-btn>
            <v-btn text @click="goToUsers">Users</v-btn>
            <v-btn text @click="goToSettings">Settings</v-btn>
          </v-row>

          <!-- Summary Cards -->
          <v-row class="summary-cards-container">
            <v-col cols="12" sm="4" v-for="item in summaryCards" :key="item.title">
              <v-card outlined class="summary-card" @click="item.action">
                <v-icon class="summary-card-icon">{{ item.icon }}</v-icon>
                <div class="summary-card-title">{{ item.title }}</div>
                <div class="summary-card-value">{{ item.value }}</div>
              </v-card>
            </v-col>
          </v-row>

          <!-- Growth Chart -->
          <v-row>
            <v-col>
              <v-card outlined class="growth-chart-card">
                <v-card-title class="growth-chart-title">Growth Chart</v-card-title>
                <v-container>
                  <canvas ref="growthChart"></canvas>
                </v-container>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
import { Chart, registerables } from 'chart.js';
import { getCookie } from '../utility/cookieHelper';
Chart.register(...registerables);

export default {
  name: 'AdminDashboard',
  data() {
    return {
      totalCourses: 0,
      totalUsers: 0,
      totalUsersWith2FA: 0,
      summaryCards: [
        {
          title: 'Total Courses',
          value: this.totalCourses,
          icon: 'mdi-book-open-page-variant',
          action: this.goToCourses
        },
        {
          title: 'Total Users',
          value: this.totalUsers,
          icon: 'mdi-account-circle',
          action: this.goToUsers
        },
        {
          title: 'Users with 2FA',
          value: this.totalUsersWith2FA,
          icon: 'mdi-shield-key',
          action: this.goTo2FAUsers
        }
      ],
      chartData: {
        labels: [],
        datasets: [
          {
            label: 'Total Users',
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255, 99, 132, 1)',
            data: [],
          },
          {
            label: 'Users with 2FA',
            backgroundColor: 'rgba(54, 162, 235, 0.2)',
            borderColor: 'rgba(54, 162, 235, 1)',
            data: [],
          },
          {
            label: 'Total Courses',
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            data: [],
          },
        ],
      },
    };
  },
  async created() {
    try {
      await this.fetchSummaryData();
    } catch (error) {
      console.error('Failed to fetch summary data:', error);
    }
  },
  mounted() {
    this.$nextTick(this.initChart);
  },
  methods: {
    async fetchSummaryData() {
      const authToken = getCookie('authToken');
      const authConfig = {
        headers: { 'Authorization': `Bearer ${authToken}` }
      };

      try {
        const responses = await Promise.all([
          axios.get(this.$backendUrl + 'courses/count', authConfig),
          axios.get(this.$backendUrl + 'users/count', authConfig),
          axios.get(this.$backendUrl + 'users/count/2fa', authConfig)
        ]);

        this.totalCourses = responses[0].data;
        this.totalUsers = responses[1].data;
        this.totalUsersWith2FA = responses[2].data;

        this.summaryCards[0].value = this.totalCourses;
        this.summaryCards[1].value = this.totalUsers;
        this.summaryCards[2].value = this.totalUsersWith2FA;

        this.chartData.labels = ['New Label 1', 'New Label 2', 'New Label 3', 'New Label 4'];

        this.chartData.datasets.forEach(dataset => {
          switch (dataset.label) {
            case 'Total Users':
              dataset.data = [this.totalUsers,];
              break;
            case 'Users with 2FA':
              dataset.data = [this.totalUsersWith2FA,];
              break;
            case 'Total Courses':
              dataset.data = [this.totalCourses,];
              break;
          }
        });

        if (this.chartInstance) {
          this.chartInstance.update();
        }
      } catch (error) {
        console.error('Failed to fetch summary data:', error);
      }
    },

    initChart() {
      const ctx = this.$refs.growthChart.getContext('2d');
      this.chartInstance = new Chart(ctx, {
        type: 'line',
        data: this.chartData,
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          },
          maintainAspectRatio: false
        }
      });
    },
    goToDashboard() {
      this.$router.push('/admin');
    },
    goToCourses() {
      this.$router.push('/admin/courses');
    },
    goToUsers() {
      this.$router.push('/admin/users');
    },
    goToSettings() {
      this.$router.push('/settings');
    },
    goTo2FAUsers() {
      this.$router.push('/users/2fa');
    },
  },
};
</script>

<style scoped lang="scss">
.navigation-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  gap: 8px;
}

.title-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.title {
  font-size: 30px;
  margin: 14px 0;
  text-align: center;
}

.summary-cards-container {
  display: flex;
  justify-content: center;
}

.summary-card {
  padding: 16px;
  text-align: center;
}

.summary-card-icon {
  font-size: 32px;
  color: rgb(var(--v-theme-gradiantOne));
}

.summary-card-title {
  margin: 10px 0;
  font-size: 16px;
}

.summary-card-value {
  font-size: 20px;
}

.growth-chart-card {
  margin-top: 20px;
}

.growth-chart-title {
  padding: 16px;
  font-size: 20px;
}

.v-btn {
  background-image: linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo)));
	color: rgb(var(--v-theme-background));
}
</style>
