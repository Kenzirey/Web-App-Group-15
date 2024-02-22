<template>
  <div>
    <h1>Admin Dashboard</h1>
    <div>
      <p>Total courses: {{ totalCourses }}</p>
      <p>Total users: {{ totalUsers }}</p>
      <!-- Other summary information -->
    </div>
    <!-- Other dashboard widgets or components -->
  </div>
</template>

<script>
export default {
  name: 'AdminDashboard',
  data() {
    return {
      totalCourses: 0,
      totalUsers: 0,
      // Other data
    };
  },
  // Fetch data when component is created
  created() {
    this.fetchSummaryData();
  },
  methods: {
    fetchSummaryData() {
      // TODO: Fetch  data from the API
    },
  },
};
</script>
