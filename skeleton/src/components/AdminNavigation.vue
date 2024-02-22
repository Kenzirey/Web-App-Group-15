<template>
  <nav>
    <ul>
      <li><router-link to="/admin/courses">Courses</router-link></li>
      <li><router-link to="/admin/users">Users</router-link></li>
      <li><router-link to="/admin/settings">Settings</router-link></li>
      <!-- ... other links -->
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'AdminNavigation',
};
</script>

<style scoped>
nav ul {
  list-style-type: none;
  padding: 0;
}

nav li {
  padding: 8px;
  margin-bottom: 5px;
}

nav li a {
  text-decoration: none;
  color: #333;
}

</style>
