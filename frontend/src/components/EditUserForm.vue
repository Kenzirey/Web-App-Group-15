<template>
  <v-container>
    <div class="user-form-container">
      <v-form ref="form" @submit.prevent="handleSubmit">
        <v-text-field
          label="Username"
          v-model="user.username"
          required
        ></v-text-field>

        <v-text-field
          label="Email"
          type="email"
          v-model="user.email"
          required
        ></v-text-field>

        <v-text-field
          label="Password"
          :type="showPassword ? 'text' : 'password'"
          v-model="user.password"
          :append-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
          @click:append="togglePasswordVisibility"
        ></v-text-field>

        <v-select
          label="Role"
          :items="roles"
          v-model="user.role"
          required
        ></v-select>

        <v-checkbox
          label="2FA Enabled"
          v-model="user.twoFactorEnabled"
        ></v-checkbox>

        <v-btn color="success" type="submit" class="mt-3">Submit</v-btn>
        <v-btn color="secondary" class="mt-3" @click="$emit('close-form')">Cancel</v-btn>
      </v-form>
    </div> 
  </v-container>
</template>

<script>
import axios from 'axios';
import { getCookie } from '../utility/cookieHelper';

export default {
  name: 'EditUserForm',
  props: {
    initialUser: {
      type: Object,
      required: true
    },
    apiEndpoint: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      user: { ...this.initialUser },
      showPassword: false,
      roles: ['ROLE_ADMIN', 'ROLE_USER']
    };
  },
  watch: {
    initialUser: {
      handler(newVal) {
        this.user = { ...newVal };
      },
      deep: true
    }
  },
  methods: {
  async handleSubmit() {
    try {
      const authToken = getCookie('authToken');
      const updatedUser = {
        username: this.user.username,
        password: this.user.password,
        active: this.user.active,
        roles: [this.user.role],
        twoFactorSecret: this.user.twoFactorSecret,
        twoFactorEnabled: this.user.twoFactorEnabled,
      };
      const response = await axios.put(this.apiEndpoint, updatedUser, {
        headers: { 'Authorization': `Bearer ${authToken}` }
      });
      this.$emit('user-updated', response.data); 
      this.resetForm();
    } catch (error) {
      console.error('Error updating user:', error);
    }
  },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
    resetForm() {
      this.user = { ...this.initialUser };
      this.showPassword = false;
      if (this.$refs.form) {
        this.$refs.form.resetValidation();
      }
    }
  },
};
</script>

<style scoped>
.user-form-container {
  max-width: 600px;
  margin: 20px auto;
}
</style>
