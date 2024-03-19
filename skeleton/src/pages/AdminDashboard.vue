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

    </div>
  </div>
</template>


<script>
export default {
  name: 'AdminDashboard',
  data() {
    return {
      totalCourses: 0,
      totalUsers: 0,
    };
  },
  created() {
    this.fetchSummaryData();
  },
  methods: {
    async fetchSummaryData() {
      try {
        const coursesResponse = await axios.get('/admin/courses/summary');
        const usersResponse = await axios.get('/admin/users/summary');
        this.totalCourses = coursesResponse.data.total;
        this.totalUsers = usersResponse.data.total;
      } catch (error) {
        console.error('Failed to fetch summary data:', error);
        // toast notification ?
      }
    },
  },
};
</script>

<style scoped>
.admin-container {
  display: flex;
}

.admin-header {
  padding: 20px 0;
  border-bottom: 2px solid #eee;
  margin-bottom: 20px;
}

.admin-content {
  padding: 20px;
  width: 100%;
  background-color: #f9f9f9; 
}

.summary-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}

.card {
  background: #fff;
  border: 1px solid #d3d3d3;
  border-radius: 10px;
  padding: 15px;
  cursor: pointer;
  transition: box-shadow 0.3s ease;
}

.card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card-icon {
  color: #4a90e2;
  margin-bottom: 10px;
}

.card-title {
  margin: 0;
  color: #333;
  font-size: 18px;
}

.card-value {
  color: #333;
  font-size: 36px;
  font-weight: bold;
}

.admin-navigation {
  min-width: 200px;
  height: 100vh;
  border-right: 1px solid #d3d3d3;
}

</style>