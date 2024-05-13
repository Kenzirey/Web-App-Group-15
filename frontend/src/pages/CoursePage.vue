<template>
  <!--Content wrapper-->
  <div class="course-container">
    <h2 class="course-title">{{ course.title }}</h2>
        <!-- Info Container for all course details -->
        <section class="info-container">
          <div class="info-item">
            <em class="key">Course Session:</em>
            <span class="value">{{ course.sessionDate }}</span>
          </div>
          <div class="info-item">
            <em class="key">Difficulty Level:</em>
            <span class="value">{{ course.level }}</span>
          </div>
          <div class="info-item">
            <em class="key">Course Size:</em>
            <span class="value">{{ course.credits }}</span>
          </div>
          <div class="info-item">
            <em class="key">Hours per week:</em>
            <span class="value">{{ course.hours }}</span>
          </div>
          <div class="info-item">
            <em class="key">Related Certifications:</em>
            <span class="value">{{ course.certification }}</span>
          </div>
          <!-- Provider and Cost -->
          <div v-for="(provider, index) in course.providers" :key="index" class="info-item">
            <em class="key">Provider:</em>
            <span class="value">{{ provider.name }}</span>
            <em class="key">Cost:</em>
            <span class="value">${{ provider.cost }}</span>
          </div>
        </section>
        <div class="info-buttons">
          <nav>
          <v-btn aria-label="Order Course" prepend-icon="mdi-cart-check" text="Order Course" type="apply" href="/forms"
            variant="outlined"></v-btn>
          </nav>
          <v-btn aria-label="Add to Favorites" @click="toggleFavorite" :disabled="isFavorite"
            :prepend-icon="isFavorite ? 'mdi-heart-off-outline' : 'mdi-heart'">
            {{ isFavorite ? 'Remove Favorite' : 'Add to Favorites' }}
          </v-btn>
        </div>
        <figure class="course-image">
          <img :src=course.image alt={{course.imageText}}>
          <figcaption>{{ course.imageText }}</figcaption>
        </figure>
        <p class="course-description">
          Course description goes here
        </p>
    </div>
</template>


<script>
//TODO: remove the console debugging lines before deploying our project.
export default {
  name: 'CoursePage',
  data() {
    return {
      course: {
        id: 1, //the id should be fetched via database?
        title: 'SQL for Beginners',
        sessionDate: '2024-04-25',
        level: 'Beginner',
        credits: '4 ECT Credits',
        hours: 4,
        certification: 'SQL Wizard Long Wizard This is the Longest',
        description: 'This is a detailed description of the course. Trust me bro',
        image: "/images/AWS.png",
        imageText: "AWS Course Image",
        providers: [
          { name: 'NTNU', cost: 500 },
          { name: 'UiO', cost: 501 }
        ]
      },
      favoriteCourses: this.loadFavorites()  // Load favorites right into the data property.
    };
  },
  computed: {
    isFavorite() {
      const favorite = this.favoriteCourses.some(course => course.id === this.course.id);
      console.log("Is Favorite Check: ", favorite);  // Debugging step, remove before deploying.
      return favorite;
    }
  },
  methods: {
    toggleFavorite() {
      console.log("Before Toggle: ", this.favoriteCourses);  // Debug before the operation, remove before deploying.
      if (this.isFavorite) {
        this.favoriteCourses = this.favoriteCourses.filter(course => course.id !== this.course.id);
      } else {
        this.favoriteCourses.push({ ...this.course }); // Spread operator ensures reactivity and a fresh object
      }
      this.saveFavorites();
      console.log("After Toggle: ", this.favoriteCourses);  // Debug after the operation, remove before deploying.
    },
    saveFavorites() {
      localStorage.setItem('favoriteCourses', JSON.stringify(this.favoriteCourses));
      console.log("Favorites Saved: ", localStorage.getItem('favoriteCourses'));  // Confirm what is saved, remove before deploying
    },
    loadFavorites() {
      const favorites = JSON.parse(localStorage.getItem('favoriteCourses')) || [];
      console.log("Loaded Favorites: ", favorites);  // Debugging output on load, remove before deploying
      return favorites;
    }
  },
  created() {
    console.log("Component Created, Favorites Loaded: ", this.favoriteCourses);  // Initial load check
  }
}

</script>

<style lang="scss" scoped>
.course-image img {
  /* To reduce the size of the image */
  max-width: 60%;
  height: auto;
  display: block;
  margin: 8px auto;
}

/* Adjustments specifically for mobile devices */
@media screen and (max-width: 600px) {
  .course-title {
    font-size: 1.5em;
  }

  .info-item {
    flex-direction: row;
  }

  .course-image img {
    max-width: 80%;
  }

  /* For readability on mobile, as it would be too close to the edges otherwise. */
  .course-container {
    margin: 15px 10px 15px 10px;
  }

  .info-buttons .v-btn {
    margin: 2px 4px;
  }
}

.info-buttons {
    display: flex;
    justify-content: center;
    align-items: center;
    max-width: 80%;
    margin: 0 auto;
    margin-top: 10px;
    flex-wrap: wrap;
  }

.course-title {
  text-align: center;
  font-size: 1.7em;
  margin-bottom: 10px;
}

.info-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start; //Left align text for readability. (Dyslexia)
  justify-content: center;
  max-width: 450px;
  margin: 0 auto;
}

.info-item {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-bottom: 5px;
  text-align: left;
}


.key,
.value {
  margin-right: 8px;
}

.key {
  font-weight: bold;
  white-space: nowrap;
}


.v-btn {
  background-image: linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo)));
  color: rgb(var(--v-theme-background));
}
</style>