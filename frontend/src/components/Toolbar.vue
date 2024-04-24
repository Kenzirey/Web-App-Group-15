<template>
  <v-app-bar class="toolbar-app" :elevation="0"  scroll-behavior="hide" id="app-bar">
    <div id="bar">
      <div id="topbar-left">
        <!--Look under! To use vuetify color, just set color to the variable you want to use-->
        <v-btn size="large" @click="navigateFrontPage">Learniverse</v-btn>
      </div>
      
      <v-form v-on:submit.prevent="submitSearch(null, false)" id="suggestions-container">
        <button style="display: none;"></button> <!-- Prevents other buttons from being invoked as the submit when clicking enter -->
        <v-text-field id="search-bar" v-model="search" append-icon="mdi-magnify" @click:append="submitSearch(null, false)" placeholder="Search..." required autocomplete="off" hide-details/>
        <div id="search-suggestions-center-align">
          <div id="search-suggestions">
            <div id="courses-suggestions" class="suggestion-box">
              <h2>Courses:</h2>
              <ul>
                <li @click="selectSuggestion(course)" v-for="course in filteredCourses" :key="course.name"><a>{{ course.name }}</a></li>
              </ul>
              <button @click.prevent="submitSearch('courses')">More courses...</button>
            </div>
            <div id="providers-suggestions" class="suggestion-box">
              <h2>Course providers:</h2>
              <ul>
                <li @click="selectSuggestion(provider)" v-for="provider in filteredProviders" :key="provider.name"><a>{{ provider.name }}</a></li>
              </ul>
              <button @click.prevent="submitSearch('providers')">More providers...</button>
            </div>
            <div id="all-suggestions" class="suggestion-box">
              <h2>All results:</h2>
              <ul>
                <li @click="selectSuggestion(suggestion)" v-for="suggestion in getAllSuggestions()" :key="suggestion.name"><a>{{ suggestion.name }} [{{ suggestion.type }}]</a></li>
              </ul>
              <button @click.prevent="submitSearch()">More results...</button>
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
import { useRoute, useRouter } from "vue-router";

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
    searchify(str) {
      return str.toLowerCase().replace(/\s/g, "");
    },
    filterCourses(query) {
      this.filteredCourses = this.courses.filter(course => this.searchify(course.name).includes(this.searchify(query)));
    },
    filterProviders(query) {
      this.filteredProviders = this.providers.filter(provider => this.searchify(provider.name).includes(this.searchify(query)));
    },
    getAllSuggestions() {
      return this.filteredCourses.concat(this.filteredProviders);
    },
    selectSuggestion(suggestion) {
      if (suggestion.type == "course") {
        const query = new URLSearchParams();
        query.append("id", suggestion.id);
        if (query.get("id")) {
            location.href = "./course?" + query.toString();
        }
      } else if (suggestion.type == "provider") {
        if (suggestion.url) {
          location.href = suggestion.url;
        }
      }
    },
    submitSearch(type, allow_empty_query=true) {
      const params = new URLSearchParams();
      if (type) {
        params.append("type", type);
      }
      params.append("q", this.search);
      if (allow_empty_query || (params.get("q") && this.searchify(params.get("q")))) {
        location.href = "./search?" + params.toString();
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
          vueComponent.providers = data.map(provider => {return {type: "provider", id: provider.courseProviderId, name: provider.providerName, url: provider.url};});
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
    
    const route = useRoute();
    useRouter().isReady().then(function() {
      if (route.query.q) {
        vueComponent.search = route.query.q;
      }
    })
  }
}
</script>

<style lang="scss" scoped>
#app-bar {
  /* Was not necessary to keep all the moz, opera etc as it is all supported by default now. */
  background-image: linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo)));
}

.v-btn {
  color: rgb(var(--v-theme-background));
}

.v-form {
  color: rgb(var(--v-theme-background));
}

.card {
  padding: 2em;
}

#content {
  flex-grow: 1;
}

#suggestions-container {
  position: relative;
  justify-content: center;
}

#topbar-left {
  justify-content: flex-start;
  gap: 14px 26px;
}

#topbar-right {
  justify-content: flex-end;
  gap: 10px 20px;
}

/* Search bar */
#suggestions-container, #topbar-left, #topbar-right {
  display: flex;
  align-items: center;
}

#bar {
  display: grid;
  grid-auto-columns: minmax(0, 1fr);
  grid-auto-flow: column;
  width: 100%;
}

#app-bar {
  overflow: visible;
}

#search-suggestions-center-align {
  position: absolute;
  left: 50%;
  top: 100%;
  width: 0;
}

#search-suggestions {
  /*background-color: #dfdfdf;*/
  border-radius: 30px;
  border-style: solid;
  border-width: 5px;
  position: relative;
  display: none;
  height: 40vw;
  max-height: 80vh;
  width: 40vw;
  left: -20vw;
  grid-template-columns: 50% 50%;
  grid-template-rows: 50% 50%;
  overflow: auto;
}

.suggestion-box {
  border-style: solid;
  display: inline-flex;
  flex-direction: column;
  overflow-wrap: break-word;
  word-break: break-word;
  text-align: left;
  padding: 10px;
}

.suggestion-box ul {
  flex-grow: 1;
}

.suggestion-box li {
  margin-left: 15px;
}

.suggestion-box a:hover {
  cursor: pointer;
}

#courses-suggestions {
  border-width: 0 2.5px 2.5px 0;
  grid-column-start: 1;
  grid-column-end: 1;
  grid-row-start: 1;
  grid-row-end: 1;
}

#providers-suggestions {
  border-width: 2.5px 2.5px 0 0;
  grid-column-start: 1;
  grid-column-end: 1;
  grid-row-start: 2;
  grid-row-end: 2;
}

#all-suggestions {
  border-width: 0 0 0 2.5px;
  grid-column-start: 2;
  grid-column-end: 2;
  grid-row-start: 1;
  grid-row-end: 3;
}

nav ul {
  list-style-type: none;
  padding: 0;
}

nav ul li {
  display: inline;
  margin-right: 10px;
}

a {
  font-weight: 500;
  padding: 10%;
  text-decoration: inherit;
}
</style>