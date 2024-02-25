<template>
    <v-app-bar :elevation="0"  scroll-behavior="hide">
      <v-spacer></v-spacer> <!-- This pushes the content to the right -->
      <v-btn prepend-icon="mdi-home" stacked size="small" @click="navigateFrontPage"></v-btn>
      <v-text>Learniverse WORK IN PROGRESS! Should be on the left?</v-text>
      
      <v-text-field
        hide-details
        append-icon="mdi-magnify"
        single-line></v-text-field>

      <v-btn prepend-icon="mdi-account" stacked size="small" @click="navigateToAccount">View Account</v-btn>
      <v-btn prepend-icon="mdi-heart" stacked size="small" @click="navigateToFavorites">View Favorites</v-btn>
      
    </v-app-bar>
    
  </template>
  
  <script>
  export default {
    name: 'TopToolbar',
    methods: {
      navigateToAccount() {
        this.$router.push('/account');
      },
      navigateToFavorites() {
        this.$router.push('/favorites');
      },
      navigateFrontPage(){
        this.$router.push('/');
      }
    }
  }
  </script>

  <style>
.course-content {
  padding-top: 64px; /* Adjust based on the actual height of your toolbar */
}</style>
  