<template>
    <v-container class="course-page">
      <div class="grid-course-container">
        <h3 class="course-title">{{ course.title }}</h3>
        <div class="content-container">
          <div class="course-info">
            <div class="info-item"><span class="key">Course Session:</span> <span class="value">Date</span></div>
            <div class="info-item"><span class="key">Course Providers:</span> <span class="value">NTNU</span></div>
            <div class="info-item"><span class="key">Difficulty Level:</span> <span class="value">Beginner</span></div>
            <div class="info-item"><span class="key">Course Size:</span> <span class="value">ECTs Credits</span></div>
            <div class="info-item"><span class="key">Hours per week:</span> <span class="value">4</span></div>
            <div class="info-item"><span class="key">Related Certifications:</span> <span class="value">SQL Wizard</span></div>
            <div class="info-item"><span class="key">Course Cost:</span> <span class="value">$500</span></div>
            
          </div>
          <div class="course-image">
            <img src="/images/AWS.png" alt="AWS Course Image">
          </div>
        </div>
        <div class="course-description">{{ course.description }}</div>
      </div>
    </v-container>
  </template>
  
  <script>
  export default {
    name: 'CoursePage',
    data() {
      return {
        course: {
            //This will be replaced with variables, where variables are fetched from database.
          title: 'SQL for Beginners',
          description: 'This is a detailed description of the course. :3 in theory, if we actually had content to put here',
        },
      };
    }
  }
  </script>
  
  <style lang="scss" scoped>

.grid-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.course-title {
  text-align: center;
  font-size: 1.5em; 
  padding-bottom: 40px;
}

.content-container {
  display: grid;
  grid-template-columns: 3fr 2fr;
  width: 100%;
  gap: 20px;
}

.course-info {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.info-item {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.key {
  font-weight: bold;
  margin-right: 8px; /* Adjust the space between key and value */
}

.course-description {
  margin-top: 20px; /* Add spacing if needed */
}

.course-title, .course-description {
    margin-top: 20px;
    width: 100%;
    text-align: center;
}

.course-image img {
  width: 100%; /* Ensure the image fills the container */
  height: auto;
}
  </style>
  