<template>
  <div>
    <h1>User Management</h1>
    <user-form @user-submitted="fetchUsers" />
    <user-list :users="users" @edit-user="editUser" @delete-user="deleteUser" />
  </div>
</template>

<script>
import UserForm from '@/components/UserForm.vue';
import UserList from '@/components/UserList.vue';

export default {
  name: 'AdminUsers',
  components: {
    UserForm,
    UserList,
  },
  data() {
    return {
      users: [],
    };
  },
  methods: {
    fetchUsers() {
      // TODO: Fetch the users from the API
    },
    editUser(user) {
      // TODO: Handle user edit
    },
    deleteUser(userId) {
      // TODO: Handle user deletion
    },
  },
  mounted() {
    this.fetchUsers();
  },
};
</script>
