<!-- src/pages/HomePage.vue -->
<template>
  <div class="home-page-container">
    <section id="introduction">
      <h2>Welcome to Learniverse Connect</h2>
      <p>Placeholder text for introduction. This section will give an overview of what Learniverse Connect offers.</p>
    </section>

    <!-- Any other sections we want on the homepage -->
    <section class="Carousel test">
      <CourseCarousel :courses="testCourses" difficulty="Sale"/>
      <CourseCarousel :courses="testCourses" difficulty="Beginner"/>
      <CourseCarousel :courses="testCourses" difficulty="Advanced"/>
      <CourseCarousel :courses="testCourses" difficulty="Expert"/>
    </section>
  </div>
</template>

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
          category: [{id: 1, name: "SQL"}, {id: 2, name: "Java"}, {id: 3, name: "JPA"}],
          sale: 0.1,
          image: '/images/AWS.png', // Directly reference images from the public/images folder for testing!
          alt:'Amazon Web Service Image',
          id: 1
        }, {
          name: 'Advanced SQL',
          difficulty: 'Advanced',
          category: [{id: 1, name: "SQL"}, {id: 2, name: "Java"}, {id: 4, name: "JDBC"}],
          sale: 0.3,
          image: '/images/machine-learning.jpg',
          alt:'Machine-Learning',
          id: 2
        }, {
          name: 'SQL Basics',
          difficulty: 'Beginner',
          category: [{id: 1, name: "SQL"}],
          sale: 0,
          image: '/images/SQL-image.jpg',
          alt:'SQL Basics Image',
          id: 3
        }
      ]
    }
  }
};
</script>

<style scoped>
  .carousel-test {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .home-page-container {
    align-items: center;
    display: flex;
    flex-direction: column;
  }


/* start of medium tablet styles, if we want it */
@media screen and (max-width: 767px) {
  #introduction {
   margin: auto 4px auto 4px;
  }

}

/* start of phone styles */
@media screen and (max-width: 479px) {
  #introduction {
   margin: auto 4px auto 4px;
  }
  p {
    margin-top: 5px;
  }

}

</style>
