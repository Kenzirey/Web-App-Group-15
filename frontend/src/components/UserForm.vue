<template>
  <div class="form-container">
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="name">Name:</label>
        <input id="name" type="text" v-model="user.name" required>
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <input id="email" type="email" v-model="user.email" required>
      </div>
      <div class="form-group">
        <label for="role">Role:</label>
        <select id="role" v-model="user.role">
          <option value="admin">Admin</option>
          <option value="user">User</option>
        </select>
      </div>
      <div class="form-group">
        <label for="twoFactorEnabled">2FA Enabled:</label>
        <input type="checkbox" id="twoFactorEnabled" v-model="user.twoFactorEnabled">
      </div>
      <button type="submit">Submit</button>
    </form>
  </div>
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
      default: () => ({ name: '', email: '', role: '', twoFactorEnabled: false })
    }
  },
  data() {
    return {
      user: { ...this.initialUser },
    };
  },
  methods: {
    handleSubmit() {
      this.$emit('user-submitted', this.user);
      this.user = { ...this.initialUser };
    }
  },
};
</script>
