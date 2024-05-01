<template>
  <div class="grid-container">
    <h3 class="course-title">{{ course.title }}</h3>
    <div class="session-date"><span class="key">Course Session:</span> <span class="value">{{ course.sessionDate
        }}</span></div>

    <div class="content-container">
      <div class="course-info">
        <div class="info-box-left">
          <div class="info-container">
            <div class="info-item"><span class="key">Difficulty Level:</span> <span
                class="value">{{ course.level }}</span></div>
            <div class="info-item"><span class="key">Course Size:</span> <span class="value">{{ course.credits }}</span>
            </div>
            <div class="info-item" v-for="(provider, index) in course.providers" :key="index">
              <span class="key">Provider:</span>
              <span class="value">{{ provider.name }}</span>
            </div>


          </div>
        </div>
        <div class="info-item-separator"></div>
        <div class="info-box-right">
          <div class="info-container">
            <div class="info-item"><span class="key">Hours per week:</span> <span class="value">{{ course.hours }}</span>
            </div>
            <div class="info-item"><span class="key">Related Certifications:</span> <span
                class="value">{{ course.certification }}</span></div>
            <div class="info-item" v-for="(provider, index) in course.providers" :key="'cost' + index">
              <span class="key">Cost:</span>
              <span class="value">${{ provider.cost }}</span>
            </div>

          </div>

        </div>

      </div>
      <v-btn text="Order Course" type="apply" href="/forms" variant="outlined"></v-btn>
      <v-btn text="Toggle Favorite" @click="toggleFavorite" :disabled="isFavorite" variant="outlined">
        {{ isFavorite ? 'Remove from Favorites' : 'Add to Favorites' }}
      </v-btn>
      <div class="course-image">
        <img src="/images/AWS.png" alt="AWS Course Image">
      </div>
    </div>
    <div class="course-description">{{ course.description }}</div>
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
        certification: 'SQL Wizard',
        description: 'This is a detailed description of the course. Trust me bro',
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
/* The items, have 2 columns to break up the information
 */
.session-date {
  text-align: center;
  font-size: 1.2em;
}

.grid-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.course-title {
  text-align: center;
  font-size: 1.5em;
}

.content-container {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.course-info {
  display: grid;
  gap: 10px;
  width: 100%;
  grid-template-columns: minmax(0, 1fr) calc(10vw - 80px) minmax(0, 1fr);
}

.info-item-separator {
  grid-row: 1;
}

.info-box-left {
  display: flex;
  grid-row: 1;
  justify-content: end;
}

.info-box-right {
  display: flex;
  grid-row: 1;
  justify-content: start;
}

.info-container {
  display: inline-flex;
  flex-direction: column;
}

.info-item {
  display: flex;
  justify-content: flex-start;
}

.key {
  font-weight: bold;
  margin-right: 8px;
  /* Adjust the space between key and value */
}

.course-title,
.course-description {
  margin-top: 5px;
  width: 100%;
  text-align: center;
}

.course-image img {

  min-width: 30%;
  max-width: 50%;
  height: auto;
}
</style>