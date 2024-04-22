<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <h1 class="text-h4 my-4">User Management</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <user-form 
          :initial-user="currentUser"
          :api-endpoint="'http://localhost:8082/admin/users'"
          @user-submitted="fetchUsers" />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <user-list :users="users" @edit-user="editUser" @prepare-delete-user="prepareDeleteUser" />
      </v-col>
    </v-row>
    <v-dialog v-model="deleteDialog" persistent max-width="300px">
      <v-card>
        <v-card-title class="headline">Delete user</v-card-title>
        <v-card-text>Are you sure you want to delete this user?</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="closeDeleteDialog">Cancel</v-btn>
          <v-btn color="red darken-1" text @click="confirmDeleteUser">Confirm</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import axios from 'axios';
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
      currentUser: null,
      deleteDialog: false,
      userToDelete: null,
    };
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get('http://localhost:8082/admin/users');
        this.users = response.data;
      } catch (error) {
        console.error('Failed to fetch users:', error);
      }
    },
    prepareDeleteUser(userId) {
      this.userToDelete = userId;
      this.deleteDialog = true;
    },
    closeDeleteDialog() {
      this.deleteDialog = false;
      this.userToDelete = null;
    },
    async confirmDeleteUser() {
      await this.deleteUser(this.userToDelete);
      this.closeDeleteDialog();
    },
    editUser(user) {
      this.currentUser = user;
    },
    async deleteUser(userId) {
      try {
        await axios.delete(`/admin/users/${userId}`);
        this.users = this.users.filter(user => user.id !== userId);
      } catch (error) {
        console.error('Failed to delete user:', error);
      }
    },
    handleUserSubmitted(responseData) {
      this.fetchUsers();
      this.currentUser = null;
    },
    handleSubmissionFailed(error) {
      console.error('User submission failed:', error);
    }
  },
  mounted() {
    this.fetchUsers();
  },
};
</script>
