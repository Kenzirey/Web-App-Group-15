<template>
  <v-container>
    <div class="user-form-container">
      <v-form ref="form" @submit.prevent="handleSubmit">
        <v-text-field
          label="Name"
          v-model="user.name"
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
          required
          :append-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
          @click:append="togglePasswordVisibility"
        ></v-text-field>

        <v-select
          label="Role"
          :items="['admin', 'user']"
          v-model="user.role"
          required
        ></v-select>

        <v-checkbox
          label="2FA Enabled"
          v-model="user.twoFactorEnabled"
        ></v-checkbox>

        <v-btn color="success" type="submit" class="mt-3">Submit</v-btn>
      </v-form>
    </div> 
  </v-container>
</template>

<style scoped>

@import '../assets/forms.css';

.form-container {
  max-width: 600px;
  margin: 0 auto;
  background: #fff;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
}

input[type="text"],
input[type="email"],
select {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

input[type="checkbox"] {
  margin-top: 0.5rem;
}

button {
  background-color: #5cb85c;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.2s;
}

button:hover {
  background-color: #4cae4c;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>

<script>
export default {
  name: 'UserForm',
  props: {
    initialUser: {
      type: Object,
      default: () => ({ name: '', email: '', role: '', twoFactorEnabled: false, password: '' })
    }
  },
  data() {
    return {
      user: { ...this.initialUser },
      showPassword: false,
    };
  },
  methods: {
    handleSubmit() {
      this.$emit('user-submitted', this.user);
      this.resetForm();
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
    resetForm() {
      this.user = { ...this.initialUser };
      this.showPassword = false;
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