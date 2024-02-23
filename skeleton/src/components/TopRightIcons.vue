<template>
    <v-app-bar app fixed dense>
      <v-spacer></v-spacer> <!-- This pushes the content to the right -->
      <v-btn prepend-icon="mdi-account" stacked size="small" @click="navigateToAccount">View Account</v-btn>
      <v-btn prepend-icon="mdi-heart" stacked size="small" @click="navigateToFavorites">View Favorites</v-btn>
      
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
  