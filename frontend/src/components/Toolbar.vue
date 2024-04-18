<template>
  <v-app-bar class="toolbar-app" :elevation="0"  scroll-behavior="hide" id="app-bar">
    <div id="bar">
      <div id="topbar-left">
        <!--Look under! To use vuetify color, just set color to the variable you want to use-->
        <v-btn prepend-icon="mdi-home" stacked size="small" @click="navigateFrontPage">Learniverse Logo Goes Here</v-btn>
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
  /**Test if this is even necessary, but found these online for older browser compabiility for browsers
  also add rgb variable instead? */
  background-image: -webkit-linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo))); /* For Chrome 25 and Safari 6, iOS Safari 6.1 to 6.2 */
  background-image:    -moz-linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo))); /* For Firefox 3.6 to 15 */
  background-image:      -o-linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo))); /* For Opera 11.1 to 12 */
  background-image: linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo)));
}

.v-btn {
  color: rgb(var(--v-theme-background));
}

.v-text {
  color: brown;
  background-color: brown;
}

.v-form {
  /** Kom opp med en bedre kontrast farge, den er litt for mørk kanskje?
  Tror det har noe med search baren ellers.
   */
  color: rgb(var(--v-theme-background));
}

</style>