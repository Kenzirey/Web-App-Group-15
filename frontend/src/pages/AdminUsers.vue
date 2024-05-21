<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <h1 class="text-h4 my-4">
          {{ currentUser ? `Editing User: ${currentUser.username}` : 'Add New User' }}
        </h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <user-form 
          v-if="!currentUser"
          :initial-user="newUser"
          :api-endpoint="this.$backendUrl + 'users'"
          @user-submitted="handleUserSubmitted" />
        <edit-user-form
          v-else
          :initial-user="currentUser"
          :api-endpoint="this.$backendUrl + 'users/' + currentUser.id"
          @user-updated="handleUserUpdated"
          @close-form="stopEditing" />
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
    <v-snackbar v-model="snackbar" bottom right :timeout="3000">
      {{ snackbarText }}
      <v-btn color="red" text @click="snackbar = false">Close</v-btn>
    </v-snackbar>
  </v-container>
</template>

<script>
import axios from 'axios';
import UserForm from '@/components/UserForm.vue';
import EditUserForm from '@/components/EditUserForm.vue';
import UserList from '@/components/UserList.vue';
import { getCookie } from '../utility/cookieHelper';

export default {
  name: 'AdminUsers',
  components: {
    UserForm,
    EditUserForm,
    UserList,
  },
  data() {
    return {
      users: [],
      newUser: { username: '', email: '', role: '', twoFactorEnabled: false, password: '' },
      currentUser: null,
      deleteDialog: false,
      userToDelete: null,
      snackbar: false,
      snackbarText: '',
    };
  },
  methods: {
    async fetchUsers() {
      const authToken = getCookie('authToken');
      try {
        const response = await axios.get(this.$backendUrl + 'users', {
          headers: { 'Authorization': `Bearer ${authToken}` }
        });
        this.users = response.data.map(user => ({
          ...user,
          email: user.username,
          role: user.roles.length > 0 ? user.roles[0].name : ''
        }));
      } catch (error) {
        console.error('Failed to fetch users:', error);
      }
    },
    editUser(user) {
      this.currentUser = { ...user };
      this.snackbarText = `Editing ${user.username}`;
      this.snackbar = true;
    },
    handleUserUpdated(updatedUser) {
      this.fetchUsers();
      this.stopEditing();
    },
    stopEditing() {
      this.currentUser = null;
      this.snackbarText = 'Back to adding new users';
      this.snackbar = true;
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
    async deleteUser(userId) {
      const authToken = getCookie('authToken');
      try {
        await axios.delete(`${this.$backendUrl}users/${userId}`, {
          headers: { 'Authorization': `Bearer ${authToken}` }
        });
        this.users = this.users.filter(user => user.id !== userId);
      } catch (error) {
        console.error('Failed to delete user:', error);
      }
    },
    handleUserSubmitted() {
      this.fetchUsers();
      this.stopEditing();
    },
  },
  mounted() {
    this.fetchUsers();
  },
};
</script>
