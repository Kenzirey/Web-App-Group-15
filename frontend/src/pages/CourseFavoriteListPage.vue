<template>
  <div class="flex-container">
    <h1>Favorite Courses</h1>
    <p>I pulled Tony's fire alarm again, this time on purpose</p>
    <section class="favorite-items">
      <div class="favorite-course-card" v-for="course in favoriteCourses" :key="course.id">
        <v-card class="card-container">
          <v-card-title class="card-title-container">
            <span class="course-title">{{ course.name }}</span>
            <v-btn class="remove-favorite-button" icon="mdi-heart-off-outline" variant="plain" density="comfortable"
              text @click="removeFromFavorites(course.id)">
            </v-btn>
          </v-card-title>
          <v-card-text>
            {{ course.description }}
          </v-card-text>
        </v-card>
      </div>
    </section>
  </div>
</template>


<script>
export default {
  name: 'CourseFavoriteListPage',
  data() {
    return {
      favoriteCourses: [
        //Add database integration with variables here?
        { id: 1, name: "Vue.js Basics", description: "Learn the basics of Vue.js for building interactive web interfaces." },
        { id: 2, name: "Advanced Vue Techniques", description: "Dive deeper into Vue.js and explore advanced concepts and techniques." },
        { id: 3, name: "Vuetify Essentials", description: "Learn how to build beautiful and functional UIs with Vuetify." }
      ],
    };
  },
  methods: {
    removeFromFavorites(courseId) {
      this.favoriteCourses = this.favoriteCourses.filter(course => course.id !== courseId);
    },
  },
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
