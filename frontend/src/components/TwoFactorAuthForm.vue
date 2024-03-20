<template>
    <form @submit.prevent="submitToken">
      <input type="text" v-model="token" placeholder="2FA Token" required>
      <button type="submit">Verify</button>
    </form>
  </template>
  
  <script>
  export default {
    data() {
      return {
        token: ''
      };
    },
    methods: {
      async submitToken() {
        // TODO: Verify the 2FA token
      }
    }
  };
  </script>
  