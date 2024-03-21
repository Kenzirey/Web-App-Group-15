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
                <li @click.native="selectCourseSuggestion($event)" v-for="course in filteredCourses" :key="course.name" :course-id="course.id">{{ course.name }}</li>
              </ul>
            </div>
            <div id="providers-suggestions" class="suggestion-box">
              <h2>Course providers:</h2>
              <ul>
                <li v-for="provider in filteredProviders" :key="provider.name" :provider-id="provider.id">{{ provider.name }}</li>
              </ul>
            </div>
            <div id="all-suggestions" class="suggestion-box">
              <h2>All results:</h2>
              <ul>
                <li v-for="suggestion in getAllSuggestions()" :key="suggestion.name" :suggestion-id="suggestion.id">{{ suggestion.name }}</li>
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

<script>
import { ref } from "vue";

let suggestions_loaded = false;
export default {
  name: 'TopToolbar',
  methods: {
    navigateToAccount() {
      this.$router.push('/account');
    },
    navigateToFavorites() {
      this.$router.push('/favorites');
    },
    navigateFrontPage() {
      this.$router.push('/');
    },
    filterCourses(query) {
      this.filteredCourses = this.courses.filter(course => course.name.toLowerCase().replace(/\s/g, "").includes(query.toLowerCase().replace(/\s/g, "")));
    },
    filterProviders(query) {
      this.filteredProviders = this.providers.filter(provider => provider.name.toLowerCase().replace(/\s/g, "").includes(query.toLowerCase().replace(/\s/g, "")));
    },
    getAllSuggestions() {
      return this.filteredCourses.concat(this.filteredProviders);
    },
    selectCourseSuggestion(clickedSuggestion) {
      const query = new URLSearchParams();
      query.append("id", clickedSuggestion.target.getAttribute("course-id"));
      console.log(clickedSuggestion.target.getAttribute("course-id"));
      if (query.get("id")) {
          location.href = "./course?" + query.toString();
      }
    },
    submitSearch() {
      const query = new URLSearchParams();
      query.append("q", this.search)
      if (query.get("q")) {
          location.href = "./search?" + query.toString();
      }
    }
  },
  watch: {
    search(val) {
      this.filterCourses(val);
      this.filterProviders(val);
    }
  },
  setup() {
    let search = ref("");
    return {search};
  },
  data() {
    return {courses: [], providers: [], filteredCourses: [], filteredProviders: []};
  },
  mounted() {
    const vueComponent = this;
    const backend_base_url = "http://localhost:8080/";

    async function fetchCourses() {
      const response = await fetch(backend_base_url + "courses");
      return await response.json();
    }

    async function fetchProviders() {
      const response = await fetch(backend_base_url + "providers");
      return await response.json();
    }

    document.getElementById("search-bar").addEventListener("focus", function() {
      document.getElementById("search-suggestions").style.display = "grid";
      if (!suggestions_loaded) {
        suggestions_loaded = true;
        fetchCourses().then(data => {
          vueComponent.courses = data.map(course => {return {type: "course", id: course.courseId, name: course.courseName};});
          vueComponent.filterCourses(vueComponent.search);
        });
        fetchProviders().then(data => {
          vueComponent.providers = data.map(provider => {return {type: "provider", id: provider.courseProviderId, name: provider.providerName};});
          vueComponent.filterProviders(vueComponent.search);
        });
      }
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