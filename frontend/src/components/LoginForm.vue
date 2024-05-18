<template>
  <v-container class="login-container">
    <v-card class="login-card">
      <v-card-title class="login-title">Sign In</v-card-title>
      <v-card-text>
        <v-form ref="loginForm" @submit.prevent="login">
          <v-text-field
            label="Email"
            v-model="email"
            required
            autocomplete="username"
            :error-messages="emailErrors"
            class="login-input"
          ></v-text-field>
          <v-text-field
            label="Password"
            type="password"
            v-model="password"
            required
            autocomplete="current-password"
            :error-messages="passwordErrors"
            class="login-input"
          ></v-text-field>
          <v-btn type="submit" class="login-button">Sign In</v-btn>
          <v-alert type="error" v-if="errorMessage" class="login-alert">{{ errorMessage }}</v-alert>
        </v-form>
      </v-card-text>
      <v-card-actions class="login-actions">
        <v-btn text class="login-clear" @click="clear">Clear</v-btn>
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
        const decoded = store.login(response.data.jwt);
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

<style scoped lang="scss">

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
  width: 60vh;
}

.login-card {
  width: 100%;
  max-width: 600px;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.1);
}

.login-title {
  color: #333;
  text-align: center;
  font-size: 30px;
  margin-bottom: 5px;
}

.login-input {
  margin-bottom: 16px;
}


.login-alert {
  margin-top: 30x;
}

.login-actions {
  display: flex;
  justify-content: center;
}

.v-card-text {
  background-color: white;
  color: black;
}

.v-btn {
  background-image: linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo)));
	color: rgb(var(--v-theme-background));
}
</style>
