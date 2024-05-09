<template>
  <v-form @submit.prevent="submitToken">
    <v-text-field
      label="2FA Token"
      v-model="token"
      placeholder="Enter 2FA Token"
      required
      autocomplete="off"
    ></v-text-field>
    <v-btn color="primary" type="submit">Verify</v-btn>
  </v-form>
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
      try {
        const response = await axios.post('/auth/verify-2fa', { token: this.token });
        alert('2FA Verification Successful: ' + response.data);
      } catch (error) {
        alert('2FA Verification Failed: ' + (error.response.data || 'something doesnt work'));
      }
    }
  }
};
</script>
