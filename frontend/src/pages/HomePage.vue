<!-- src/pages/HomePage.vue -->
<template>
  <div>
    <section id="introduction">
      <h2>Welcome to Learniverse Connect</h2>
      <p>Placeholder text for introduction. This section will give an overview of what Learniverse Connect offers.</p>
    </section>

    <!-- Any other sections we want on the homepage -->
    
    <swiper-container style="height:100px;" slides-per-view="5" speed="5000" loop="true" css-mode="true" navigation="true" pagination="true">
      <swiper-slide v-for="n in 20">Slide {{ n }}</swiper-slide>
    </swiper-container>
    <section class="carousel-test">
      
      <v-slide-group style="display: none;" ref="slideGroup" :model-value="slideActive" center-active>
        <v-slide-group-item :ref="n" :key="n" v-for="n in slidesTotal">
          <v-card :class="['bruh', 'test' + (n - 1) % 3]">{{ n - 1 }}</v-card>
        </v-slide-group-item>
      </v-slide-group>

      <!-- Test Section for Carousels-->
      <section>
        <CourseCarousel :title="'Featured Courses'" :courses="testCourses" />
      </section>
      <section>
        <CourseCarousel :title="'Beginner Courses'" :courses="testCourses" difficulty="Beginner" />
      </section>
      <section>
        <CourseCarousel :title="'Advanced Courses'" :courses="testCourses" difficulty="Advanced" />
      </section>
      <section>
        <CourseCarousel :title="'Expert Courses'" :courses="testCourses" difficulty="Expert" />
      </section>
    </section>
  </div>
</template>

<style scoped>
  .carousel-test {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .v-slide-group {
    width: 80vw;
  }

  .bruh {
    width: calc(80vw / 3);
  }

  .test0 {
    background-color: #FF0000;
  }

  .test1 {
    background-color: #00FF00;
  }

  .test2 {
    background-color: #0000FF;
  }
</style>

<script>

/**
 * Carousel component allows us to add multiple carousels with reusable code.
 * We just need to change its category, sale, or its difficulty. We can expand on whatever we want.
 */
import CourseCarousel from '@/components/CourseCarousel.vue';
export default {
  name: 'HomePage',
  components: {
    CourseCarousel
  },
  data() {
    return {
      /**
       * These are purely for testing the carousel component.
       */
      testCourses: [
        { 
          name: 'Microsoft SQL', 
          difficulty: 'Beginner', 
          category: 'SQL', 
          onSale: false,
          image: '/images/AWS.png' // Directly reference images from the public/images folder for testing!
        },
        { 
          name: 'Advanced SQL', 
          difficulty: 'Advanced', 
          category: 'SQL', 
          image: '/images/machine-learning.jpg'
        },
        {
          name: 'SQL Basics', 
          difficulty: 'Beginner', 
          category: 'SQL', 
          image: '/images/SQL-image.jpg'
        }
      ],
      slideActive: 0,
      slidesTotal: 20,
      interval: null
    }
  },
  mounted() {
    const self = this;
    this.interval = setInterval(function() {
      self.slideActive = (self.slideActive % (self.slidesTotal - 2) + 1);
    }, 500);
  },
  unmounted() {
    clearInterval(this.interval);
  }
};
</script>
