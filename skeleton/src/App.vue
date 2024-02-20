<template>
  <div class="course-content">
    <header>
      <h1>Learniverse Connect</h1>
      <nav>
        <ul>
          <li><router-link to="/">Home</router-link></li>
          <li><router-link to="/course"><v-btn color="primary">Go to Course Page</v-btn></router-link></li>
          <li><router-link to="/about">About Us</router-link></li>
          <li><router-link to="/contact">Contact</router-link></li>
        </ul>
      </nav>
    </header>
    
    <!-- The main content of the page, replaced based on the current route -->
    <router-view id="content"></router-view>
    
    <footer>
      <section id="contact">
        <h2>Contact Us</h2>
        <p>Placeholder text for contact information. This will include how to get in touch with Learniverse Connect.</p>
      </section>
      <p>This website is a result of a university group project performed in the course IDATA2301 Web technologies at NTNU. All the information provided here is a result of imagination. Any resemblance with real companies or products is a coincidence.</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style scoped>
@import './assets/global.css';
</style>

