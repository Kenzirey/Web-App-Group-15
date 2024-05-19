<template>
    <v-container>
      <v-form ref="form" @submit.prevent="handleSubmit">
        <v-text-field
          label="Current Password"
          v-model="currentPassword"
          type="password"
          required
        ></v-text-field>
        <v-text-field
          label="New Password"
          v-model="newPassword"
          type="password"
          required
        ></v-text-field>
        <v-btn color="primary" type="submit">Change Password</v-btn>
      </v-form>
  
      <!-- Snackbar for notifications -->
      <v-snackbar v-model="snackbar" bottom right :timeout="3000">
        {{ snackbarText }}
        <v-btn color="red" text @click="snackbar = false">Close</v-btn>
      </v-snackbar>
    </v-container>
  </template>
  
  <script>
  import { getCookie } from '../utility/cookieHelper';
  import { store } from '../utility/store';
  
  export default {
    data() {
      return {
        currentPassword: '',
        newPassword: '',
        snackbar: false,
        snackbarText: '',
      };
    },
    methods: {
      async handleSubmit() {
        try {
          const userId = store.user.id;
  
          if (typeof userId === 'number' && Number.isFinite(userId)) {
            console.log('User ID is a valid number:', userId);
          } else {
            console.error('Invalid user ID. Expected a numeric value:', userId);
            throw new Error('Invalid user ID. Expected a numeric value.');
          }
  
          console.log(`User ID: ${userId}`);
          console.log(`URL: ${this.$backendUrl}users/${userId}/change-password`);
  
          const response = await this.$authFetch(`${this.$backendUrl}users/${userId}/change-password`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
              currentPassword: this.currentPassword,
              newPassword: this.newPassword
            })
          });
  
          if (response && response.ok) {
            this.snackbarText = 'Password updated successfully!';
            this.snackbar = true;
            this.$router.push('/account');
          } else {
            const errorData = await response.json();
            this.snackbarText = 'Failed to change password: ' + errorData.message;
            this.snackbar = true;
          }
        } catch (error) {
          console.error('Error changing password:', error);
          this.snackbarText = 'Failed to change password. Please try again.';
          this.snackbar = true;
        }
      }
    }
  }
  </script>
  