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
          :api-endpoint="this.$backendUrl + 'admin/users'"
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
import { getCookie } from '../utility/cookieHelper';

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
      const authToken = getCookie('authToken');
      try {
        const response = await axios.get(this.$backendUrl + 'admin/users', {
          headers: { 'Authorization': `Bearer ${authToken}` }
        });
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
      if (this.userToDelete) {
        await this.deleteUser(this.userToDelete);
        this.closeDeleteDialog();
      }
    },
    editUser(user) {
      this.currentUser = user;
    },
    async deleteUser(userId) {
      const authToken = getCookie('authToken');
      try {
        await axios.delete( `${this.$backendUrl}admin/users/${userId}`, {
          headers: { 'Authorization': `Bearer ${authToken}` }
        });
        this.users = this.users.filter(user => user.id !== userId);
      } catch (error) {
        console.error('Failed to delete user:', error);
      }
    },
    handleUserSubmitted() {
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
