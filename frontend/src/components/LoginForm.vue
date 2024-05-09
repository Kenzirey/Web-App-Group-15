<template>
  <v-container>
    <v-card>
      <v-card-title class="text-h5">Sign In</v-card-title>
      <v-card-text>
        <v-form ref="loginForm" @submit.prevent="login">
          <v-text-field
            label="Email"
            v-model="email"
            required
            autocomplete="username"
            :error-messages="emailErrors"
          ></v-text-field>
          <v-text-field
            label="Password"
            type="password"
            v-model="password"
            required
            autocomplete="current-password"
            :error-messages="passwordErrors"
          ></v-text-field>
          <v-btn type="submit" color="primary" class="mr-4">Sign In</v-btn>
          <v-alert type="error" v-if="errorMessage" class="mt-4">{{ errorMessage }}</v-alert>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-btn text @click="clear">Clear</v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import { store } from '../utility/store';

export default {
  data() {
    return {
      email: '',
      password: '',
      errorMessage: '',
      emailErrors: [],
      passwordErrors: [],
    };
  },
  methods: {
    async login() {
      this.clearErrors();
      console.log("Logging in with:", this.email, this.password);
      try {
        const response = await axios.post(this.$backendUrl + 'authenticate', {
          username: this.email,
          password: this.password
        });

        const decoded = jwtDecode(response.data.jwt);
        store.login(response.data.jwt, decoded.roles);
        console.log('Decoded JWT:', decoded);
        this.$emit('login-success', { user: decoded.sub, roles: decoded.roles });

        if (decoded.roles && decoded.roles.includes('ROLE_ADMIN')) {
          this.$router.push('/admin');
        } else {
          this.$router.push('/');
        }
        if (this.$refs.loginForm) {
          this.$refs.loginForm.reset();
        }
      } catch (error) {
        this.errorMessage = error.response?.data?.message || 'Login failed';
        console.error('Login failed:', this.errorMessage);
        this.$emit('login-failed', this.errorMessage);
      }
    },

    clear() {
      this.email = '';
      this.password = '';
      this.clearErrors();
    },

    clearErrors() {
      this.errorMessage = '';
      this.emailErrors = [];
      this.passwordErrors = [];
    }
  },
};
</script>
