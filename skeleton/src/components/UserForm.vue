<template>
  <form @submit.prevent="handleSubmit">
    <div>
      <label for="name">Name:</label>
      <input id="name" v-model="user.name" required>
    </div>
    <div>
      <label for="email">Email:</label>
      <input id="email" type="email" v-model="user.email" required>
    </div>
    <div>
      <label for="role">Role:</label>
      <select id="role" v-model="user.role">
        <option value="admin">Admin</option>
        <option value="user">User</option>
      </select>
    </div>
    <button type="submit">Submit</button>
  </form>
</template>


<style>
  @import '../assets/forms.css';
</style>


<script>
export default {
  name: 'UserForm',
  props: {
    initialUser: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      user: this.initialUser,
    };
  },
  methods: {
    handleSubmit() {
      this.$emit('user-submitted', this.user);
      this.user = { name: '', email: '', role: '' };
    }
  },
};
</script>
