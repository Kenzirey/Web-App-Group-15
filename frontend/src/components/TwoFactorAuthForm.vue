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
      // TODO: Verify the 2FA token
    }
  }
};
</script>
