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
      users: [
        // some test users for visualizing
        { id: 1, name: 'kenzirey', email: 'kenzirey@example.com', role: 'admin', twoFactorEnabled: true },
        { id: 2, name: 'Smiley', email: 'smiley@example.com', role: 'user', twoFactorEnabled: false },
        { id: 3, name: 'Tpp', email: 'Tpp@example.com', role: 'user', twoFactorEnabled: true },
      ],
    };
  },
  methods: {
   async fetchUsers() {
      try {
        const response = await axios.get('/admin/users');
        this.users = response.data;
      } catch (error) {
        console.error('Failed to fetch users:', error);
      }
    },
    editUser(user) {
      // TODO: Handle user edit
    },
    async deleteUser(userId) {
      if (!confirm('Are you sure you want to delete this user?')) {
        return;
      }
      try {
        await axios.delete(`/admin/users/${userId}`);
        this.users = this.users.filter(user => user.id !== userId);
      } catch (error) {
        console.error('Failed to delete user:', error);
      }
    },
  },
  mounted() {
    this.fetchUsers();
  },
};
</script>
