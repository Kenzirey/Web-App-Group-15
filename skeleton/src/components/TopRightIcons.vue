<template>
    <v-app-bar app fixed dense>
      <v-spacer></v-spacer> <!-- This pushes the content to the right -->
      <v-btn icon="mdi-account" size="large" @click="navigateToAccount"></v-btn>
    <v-btn icon="mdi-heart" size="large" @click="navigateToFavorites"></v-btn>
      
    </v-app-bar>
  </template>
  
  <script>
  export default {
    name: 'TopRightIcons',
    methods: {
      navigateToAccount() {
        this.$router.push('/account');
      },
      navigateToFavorites() {
        this.$router.push('/favorites');
      }
    }
  }
  </script>
  