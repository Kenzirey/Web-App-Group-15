<template>
    <v-app id ="courseApp" color="background">

      <TopToolbar class="toolbar" />
      <body class="body-content">
        <div class="course-content">
        <!-- The main content of the page, replaced based on the current route -->
        <router-view id="content"></router-view>
      </div>
      </body>
      
      <!--Footer component-->
      <Footer></Footer>
    </v-app>
</template>

<script>
import TopToolbar from './components/Toolbar.vue';
import Footer from './components/Footer.vue';

export default {
  name: 'App',
  components: {
    TopToolbar,
    Footer
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
.body-content {
  margin: 0 auto;
  text-align: center;
}


/* Carousel */
#app .v-carousel {
  width: 100%; /* Make images responsive */
  height: auto;
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
  
}

#contact {
  /* Ensure that the contact section does not take up more space than necessary */
  flex-grow: 1;
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


</style>
