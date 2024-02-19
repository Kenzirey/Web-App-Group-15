<template>
  <div class="course-content">
    <header>
      <h1>Learniverse Connect</h1>
      <nav>
        <ul>
          <li><router-link to="/">Home</router-link></li>
          <li><router-link to="/courses">Courses</router-link></li>
          <li><router-link to="/about">About Us</router-link></li>
          <li><router-link to="/contact">Contact</router-link></li>
        </ul>
      </nav>
    </header>
    
    <main>
      <section id="introduction">
        <h2>Welcome to Learniverse Connect</h2>
        <p>Placeholder text for introduction. This section will give an overview of what Learniverse Connect offers.</p>
      </section>
      
      <section id="courses">
        <h2>Our Courses</h2>
        <div v-for="category in courseCategories" :key="category.name" class="course-category">
          <h3>{{ category.name }}</h3>
          <ul>
            <li v-for="course in category.courses" :key="course.id">
              {{ course.title }} - {{ course.description }}
            </li>
          </ul>
        </div>
      </section>
      
      <section id="contact">
        <h2>Contact Us</h2>
        <p>Placeholder text for contact information. This will include how to get in touch with Learniverse Connect.</p>
      </section>
    </main>
    
    <footer>
      <p>This website is a result of a university group project performed in the course IDATA2301 Web technologies at NTNU. All the information provided here is a result of imagination. Any resemblance with real companies or products is a coincidence.</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'CourseContentPage',
  data() {
    return {
      courseCategories: [
        {
          name: 'Information Technology',
          courses: [
            { id: 1, title: 'Real-Time Programming in Java', description: 'Placeholder for course description' },
            // Additional courses
          ],
        },
        // Additional categories
      ],
    };
  },
};
</script>

<style scoped>
/* Add your styles here */
</style>

