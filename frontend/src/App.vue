<template>
  <div id="app">
    <v-app id="main app">
      <TopToolbar class="toolbar" />
      <div class="course-content">
        <header>
          <h1><router-link to="/">Learniverse Connect</router-link></h1>
          <nav>
            <ul>
              <li><router-link to="/course"><v-btn color="primary">Go to Course Page</v-btn></router-link></li>
            </ul>
          </nav>
        </header>
        
        <!-- The main content of the page, replaced based on the current route -->
        <router-view id="content"></router-view>
        
        
        <footer class="footer-container">
          <div class="footer-links">
            <h2><router-link to="/about">About Us</router-link></h2>
            <h2><router-link to="/contact">Contact Us</router-link></h2>
          </div>
        <p>This website is a result of a university group project performed in the course IDATA2301 Web technologies at NTNU. All the information provided here is a result of imagination. Any resemblance with real companies or products is a coincidence.</p>
</footer>
      </div>
    </v-app>
  </div>
</template>

<script>
import TopToolbar from './components/Toolbar.vue';

// import function to register Swiper custom elements
import { register } from 'swiper/element/bundle';
// register Swiper custom elements
register();


export default {
  name: 'App',
  components: {
    TopToolbar
  }
};
</script>

<style>
.footer-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
}

#contact {
  /* Ensure that the contact section does not take up more space than necessary */
  flex-grow: 1;
}

.footer-container > p {
  flex-basis: 100%; /* Ensure the paragraph takes the full width when wrapping */
  text-align: center; /* Center-align the text of the paragraph */
}
</style>
