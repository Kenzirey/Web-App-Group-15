<template>
  <v-app-bar class="toolbar-app" :elevation="0"  scroll-behavior="hide" id="app-bar">
    <div id="bar">
      <div id="topbar-left">
        <v-btn prepend-icon="mdi-home" stacked size="small" @click="navigateFrontPage"></v-btn>
        <v-text>Learniverse</v-text>
      </div>
      
      <v-form v-on:submit.prevent="submitSearch" id="suggestions-container">
        <v-text-field id="search-bar" v-model="search" append-icon="mdi-magnify" @click:append="submitSearch" placeholder="Search..." required autocomplete="off" hide-details/>
        <div id="search-suggestions-center-align">
          <div id="search-suggestions">
            <div id="courses-suggestions" class="suggestion-box">
              <h2>Courses:</h2>
              <ul>
                <li @click.native="completeCourseSuggestion($event)" v-for="course in getCourseSuggestions()" :key="course.name" :course-id="course.id">{{ course.name }}</li>
              </ul>
            </div>
            <div id="providers-suggestions" class="suggestion-box">
              <h2>Course providers:</h2>
              <ul>
                <li v-for="provider in getProviderSuggestions()" :key="provider.name">{{ provider.name }}</li>
              </ul>
            </div>
            <div id="all-suggestions" class="suggestion-box">
              <h2>All results:</h2>
              <ul>
                <li v-for="suggestion in getAllSuggestions()" :key="suggestion.name">{{ suggestion.name }}</li>
              </ul>
            </div>
          </div>
        </div>
      </v-form>
  
      <!-- Hidden, but kept for future reference -->
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
  
  const courses = [{id: 1, name: "Result 1"}, {id: 2, name: "Result 2"}, {id: 3, name: "femboys"}]; //TODO: Make an API request to get this instead
  const providers = [{id: 1, name: "Provider 1"}, {id: 2, name: "Provider 2"}, {id: 3, name: "tomboys"}]; //TODO: Make an API request to get this instead

  function getCourseSuggestions() {
    return courses.filter((course) => 
      course.name.toLowerCase().includes(search.value.toLowerCase())
    );
  }

  function getProviderSuggestions() {
    return providers.filter((provider) =>
      provider.name.toLowerCase().includes(search.value.toLowerCase())
    );
  }

  function getAllSuggestions() {
    return getCourseSuggestions().concat(getProviderSuggestions());
  }

  function completeCourseSuggestion(clickedSuggestion) {
    const query = new URLSearchParams();
    query.append("id", clickedSuggestion.target.getAttribute("course-id"));
    console.log(clickedSuggestion.target.getAttribute("course-id"));
    if (query.get("id")) {
        location.href = "./course?" + query.toString();
    }
  }

  function submitSearch() {
    const query = new URLSearchParams();
    query.append("q", search.value)
    if (query.get("q")) {
        location.href = "./search?" + query.toString();
    }
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