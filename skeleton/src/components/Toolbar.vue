<template>
  <v-app-bar :elevation="0"  scroll-behavior="hide" id="app-bar">
    <div id="bar">
      <div id="topbar-left">
        <v-btn prepend-icon="mdi-home" stacked size="small" @click="navigateFrontPage"></v-btn>
        <v-text>Learniverse WORK IN PROGRESS! Should be on the left?</v-text>
      </div>
      
      <v-form v-on:submit.prevent="submitSearch" id="suggestions-container">
        <v-text-field id="search-bar" v-model="search" append-icon="mdi-magnify" @click:append="submitSearch" placeholder="Search..." required hide-details/>
        <div id="search-suggestions-center-align">
          <div id="search-suggestions">
            <div id="courses-suggestions"></div>
            <div id="providers-suggestions"></div>
            <div id="all-suggestions"></div>
          </div>
        </div>
      </v-form>
  
      <!-- Hidden, but kept for future reference -->
      <p style="display: none;" v-for="suggestion in getSearchSuggestions()" :key="suggestion"><b>{{ suggestion }}</b></p>
      <div id="topbar-right">
        <v-btn prepend-icon="mdi-account" stacked size="small" @click="navigateToAccount">View Account</v-btn>
        <v-btn prepend-icon="mdi-heart" stacked size="small" @click="navigateToFavorites">View Favorites</v-btn>
      </div>
    </div>
    
  </v-app-bar>
  
</template>

<script setup>
  import { ref } from "vue";
  
  let search = ref("");
  const suggestions = ["Result 1", "Result 2", "femboys"]; //TODO: Make an API request to get this instead
  function getSearchSuggestions() {
    return suggestions.filter((suggestion) => 
      suggestion.toLowerCase().includes(search.value.toLowerCase())
    );
  }

  function submitSearch() {
    console.log("owo");
  }

</script>

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
  },
  mounted() {
    console.log("owo");
    document.getElementById("search-bar").addEventListener("focus", function() {
      document.getElementById("search-suggestions").style.display = "grid";
    });

    document.addEventListener("click", function(event) {
      const suggestionsContainer = document.getElementById("suggestions-container");
      if (!suggestionsContainer.contains(event.target)) {
        document.getElementById("search-suggestions").style.display = "none";
      }
    });
  }
}
</script>