<template>
    <v-app id ="courseApp" color="background">
      <TopToolbar class="toolbar" />
      <div class="course-content">
        
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
</template>

<script>
import TopToolbar from './components/Toolbar.vue';

export default {
  name: 'App',
  components: {
    TopToolbar
  },
};
</script>

<style lang="scss">


:root {
  font-family: Inter, system-ui, Avenir, Helvetica, Arial, sans-serif;
  line-height: 1.5;
  font-weight: 400;

  font-synthesis: none;
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

/* Inspector complained about duplicate ID of "app" due to v-app inherently having an id named app
But noticed a bug on favorites and account page if I didn't target both
That is where app + courseApp comes from. */
#app, #courseApp {
  max-width: 1280px;
  margin: 0 auto;
  padding: 2vh 2rem;
  text-align: center;
}


/* Carousel */
#app .v-carousel {
  width: 100%; /* Make images responsive */
  height: auto;
}

.test {
  padding-bottom: 50px; /* Adjust the value as needed */
}

/* Course content, i.e. everything except the toolbar(topbar) */
.course-content {
  min-height: 95vh; /* Ensure minimum height */
  display: flex;
  flex-direction: column;
  padding-top: 30px;
}

#content h2 {
  margin-top: 20px;
}





html, body {
  height: 100%;
  margin: 0;
  place-items: center;
  /*As this area is not a vuetify component, added color here */
  background-color: rgb(var(--v-theme-background))
}

#contact {
  /* Ensure that the contact section does not take up more space than necessary */
  flex-grow: 1;
}

.footer-container > p {
  flex-basis: 100%; /* Ensure the paragraph takes the full width when wrapping */
  text-align: center; /* Center-align the text of the paragraph */
}


button {
  border-radius: 8px;
  border: 1px solid transparent;
  padding: 0.6em 1.2em;
  font-size: 1em;
  font-weight: 500;
  font-family: inherit;
  cursor: pointer;
  transition: border-color 0.25s;

  &:hover {
    background-color: rgb(var(--v-theme-buttonHover))
  }
  &:focus,button:focus-visible {
    outline: 4px auto -webkit-focus-ring-color;
  }
}


h1 {
  font-size: 3.2em;
  line-height: 1.1;
}

/* Footer */
.footer-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
}

.footer-links {
  display: flex;
  width: 100%;
  justify-content: center; 
  gap: 20px; 
}
</style>
