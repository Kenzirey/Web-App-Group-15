<template>
  <div class="flex-container">
    <h1>Favorite Courses</h1>
    <p>I pulled Tony's fire alarm again, this time on purpose</p>
    <section class="favorite-items">
      <div class="favorite-course-card" v-for="course in favoriteCourses" :key="course.id">
        <v-card class="favorite-card-container">
          <v-card-title class="card-title-container">
            <span class="course-title">{{ course.title }}</span>
            <v-btn class="remove-favorite-button" icon="mdi-heart-off-outline" variant="plain" density="comfortable"
              text @click="removeFromFavorites(course.id)">
            </v-btn>
          </v-card-title>
          <v-card-text class="course-description">
            {{ course.shortDescription || course.description }}
          </v-card-text>
        </v-card>
      </div>
    </section>
  </div>
</template>


<script>
//ChatGPT helped with the json.stringify and filters.
export default {
  name: 'CourseFavoriteListPage',
  data() {
    return {
      favoriteCourses: []
    };
  },
  created() {
    this.loadFavorites();
  },
  methods: {
    loadFavorites() {
      this.favoriteCourses = JSON.parse(localStorage.getItem('favoriteCourses')) || [];
    },
    removeFromFavorites(courseId) {
      this.favoriteCourses = this.favoriteCourses.filter(course => course.id !== courseId);
      localStorage.setItem('favoriteCourses', JSON.stringify(this.favoriteCourses));
    }
  },
  watch: {
    favoriteCourses(newVal, oldVal) {
      // This ensures any change in favorites updates local storage and keeps UI in sync.
      // Temporary, until we set up a database connection.
      localStorage.setItem('favoriteCourses', JSON.stringify(newVal));
    }
  }
};

</script>

<style scoped lang="scss">
.card-title-container {
  display: grid;
  grid-template-columns: 1fr auto;
  align-items: center;
  position: relative;
  /* Needed to position the button absolutely within the container */
}

.course-title {
  grid-column: 1 / -1;
  text-align: center;
  z-index: 1;
  /* In case of button overlapping, changed z-index */
}

/* Chatgpt helped with this CSS */
.remove-favorite-button {
  position: absolute;
  right: 0;
  top: 50%;
  /* Center vertically */
  transform: translateY(-50%);
  z-index: 2;
}

.favorite-items {
  margin-top: 20px;
}

.favorite-course-card {
  margin-bottom: 20px;
}
</style>
