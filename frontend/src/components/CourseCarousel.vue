<template>
  <v-card>
    <v-card-title>{{ title }}</v-card-title>

    
    <!-- Hiding delimeter makes it look more clean -->
    <v-carousel style="display: none;" hide-delimiter-background	cycle multiple="true" class="carousel-multiple" :interval="15000" >
      <v-carousel-item 
        v-for="(course, index) in filteredCourses"
        :key="index">
        <v-img :src="course.image"></v-img>
        <div class="course-name">{{ course.name }}</div>
      </v-carousel-item>
    </v-carousel>

    <swiper-container @swiperprogress="test" ref="carousel" slides-per-view="2" speed="5000" loop="true" css-mode="true" navigation="true" pagination="true">
      <swiper-slide v-for="(course, index) in ensureAtLeastThree(filteredCourses)">
        <div style="padding-bottom: 8%;">
          <v-img :src="course.image" aspect-ratio="1.333" cover></v-img>
          <div class="course-name">{{ course.name }}</div>
        </div>
      </swiper-slide>
    </swiper-container>
  </v-card>
  
</template>

<style scoped>
  swiper-container {
    width: calc(50vw + 100px);
  }
</style>

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
    },
    methods: {
      ensureAtLeastThree(courses) {
        if (courses.length < 3) {
          for (let course of [...courses, ...courses]) {
            courses.push(course);
          }
        }
        return courses;
      },
      test() {
        console.log("owo");
      }
    },
    data() {
      return {
        interval: null
      }
    },
    mounted() {
      const self = this;
      this.interval = setInterval(function() {
        self.$refs.carousel.swiper.slideNext();
      }, 5000);
    }
  }
</script>
