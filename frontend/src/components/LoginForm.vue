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
          ></v-text-field>
          <v-text-field
            label="Password"
            type="password"
            v-model="password"
            required
            autocomplete="current-password"
          ></v-text-field>
          <v-btn type="submit" color="primary" class="mr-4">Sign In</v-btn>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-btn text @click="clear">Clear</v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>

export default {
  data() {
    return {
      email: '',
      password: '',
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('/auth/authenticate', {
          username: this.email,
          password: this.password
        });
        setCookie('authToken', response.data.jwt, 1);

        const decoded = jwtDecode(response.data.jwt);
        const roles = decoded.roles;

        if (roles && roles.includes('admin')) {
          this.$router.push('/AdminDashboard');
        } else {
          this.$router.push('/Home');
        }
      } catch (error) {
        console.error('Login failed:', error.response.data);
      }
    },
    clear() {
      this.email = '';
      this.password = '';
      if (this.$refs.loginForm) {
        this.$refs.loginForm.reset();
      }
    },
  },
};
</script>