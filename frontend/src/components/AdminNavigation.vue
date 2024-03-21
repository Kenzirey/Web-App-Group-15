<template>
  <div class="admin-navigation">
    <ul>
      <li><router-link to="/admin/dashboard">Dashboard</router-link></li>
      <li><router-link to="/admin/courses">Courses</router-link></li>
      <li><router-link to="/admin/users">Users</router-link></li>
      <li><router-link to="/admin/settings">Settings</router-link></li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'AdminNavigation',
};
</script>

<style scoped>
.admin-navigation {
  background: #f4f4f4;
  padding: 20px;
  width: 200px;
}

.admin-navigation ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.admin-navigation li a {
  text-decoration: none;
  color: #333;
  display: block;
  padding: 10px;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.admin-navigation li a:hover {
  background-color: #ddd;
}
</style>
