<!-- src/pages/HomePage.vue -->
<template>
    <div>
      <section id="introduction">
        <h2>Welcome to Learniverse Connect</h2>
        <p>Placeholder text for introduction. This section will give an overview of what Learniverse Connect offers.</p>
      </section>
  
      <!-- Any other sections we want on the homepage -->
      
      <section class="Carousel test">
       
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
    ]
      }
    }
  };
  </script>
  
  <style>
  </style>
  