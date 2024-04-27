<template>
  <v-container fluid class="pa-0 fill-height">
    <v-row class="fill-height" no-gutters>
      <v-col cols="12" md="3" lg="2" class="pa-4">
        <AdminNavigation />
      </v-col>

      <v-col cols="12" md="9" lg="10">
        <v-container>
          <v-row>
            <v-col>
              <h1 class="text-h4 my-4 text-center">Admin Dashboard</h1>
            </v-col>
          </v-row>

          <v-row justify="center">
            <v-col cols="12" sm="4" v-for="item in summaryCards" :key="item.title">
              <v-card outlined class="pa-4 text-center" @click="item.action">
                <v-icon large color="blue darken-2">{{ item.icon }}</v-icon>
                <div class="my-2 subtitle-2">{{ item.title }}</div>
                <div class="title">{{ item.value }}</div>
              </v-card>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-card outlined>
                <v-card-title class="text-h5 pa-4">Growth Chart</v-card-title>
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
      axios.get('http://localhost:8082/admin/courses/count', authConfig),
      axios.get('http://localhost:8082/admin/users/count', authConfig),
      axios.get('http://localhost:8082/admin/users/count/2fa', authConfig)
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
    goToCourses() {
      this.$router.push('/courses');
    },
    goToUsers() {
      this.$router.push('/users');
    },
    goTo2FAUsers() {
      this.$router.push('/users/2fa');
    },
  },
};
</script>