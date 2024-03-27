<template>
  <v-card color="background">
    <v-card-title class="text--primary">{{ title }}</v-card-title>
    <!-- Hiding delimeter makes it look more clean -->
    <v-carousel hide-delimiter-background	cycle multiple="true" class="carousel-multiple" :interval="15000" >
      <v-carousel-item color="background"
        v-for="(course, index) in filteredCourses"
        :key="index">
        <v-img color ="background" :src="course.image"></v-img>
        <div class="course-name">{{ course.name }}</div>
      </v-carousel-item>
    </v-carousel>
  </v-card>
</template>
  
  <script>
  export default {

    /**
     * In vue, props are custom attributes one can register to a component. 
     * When a value is passed to a prop attribute, it comes a property on that component instance.
     * We do this as we want to be able to filter what courses are added into the carousel, per "category" 
     * or by "sale".
     * These become basically "props" for our filter criteria.
     */
    props: {
      title: String,
      courses: Array,
      difficulty: String, 
      category: String, 
      onSale: Boolean,
    },
    computed: {
      filteredCourses() {
        /**
         * Filters the courses, can filter based on only difficulty, category OR both.
         * Easily expandable by just creating a new const and following the pattern.
         */
        return this.courses.filter(course => {
          const matchesDifficulty = this.difficulty ? course.difficulty === this.difficulty : true;
          const matchesCategory = this.category ? course.category === this.category : true;
          const matchesSale = this.onSale ? course.onSale === this.onSale : true;
          return matchesDifficulty && matchesCategory && matchesSale;1
        });
      }
    }
  }
  </script>
