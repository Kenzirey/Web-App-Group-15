<template>
  <v-navigation-drawer app permanent class="admin-navigation">
    <v-list dense>
      <v-list-item link to="/admin/dashboard">
        <v-list-item-content>
          <v-list-item-title>Dashboard</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item link to="/admin/courses">
        <v-list-item-content>
          <v-list-item-title>Courses</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item link to="/admin/users">
        <v-list-item-content>
          <v-list-item-title>Users</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item link to="/admin/settings">
        <v-list-item-content>
          <v-list-item-title>Settings</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: 'AdminNavigation',
};
</script>

<style scoped>
</style>
