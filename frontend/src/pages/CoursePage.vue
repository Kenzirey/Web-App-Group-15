<template>
  <v-container class="course-page" fluid>
    <div class="grid-container">
      <h3 class="course-title">{{ course.title }}</h3>
      <div class="session-date"><span class="key">Course Session:</span> <span class="value">{{ course.sessionDate }}</span></div>
      <div class="content-container">
        <div class="course-info">
          <div class="info-box-left">
            <div class="info-container" style="display: inline-flex; flex-direction: column;">
              <div class="info-item"><span class="key">Course Providers:</span> <span class="value">NTNU</span></div>
              <div class="info-item"><span class="key">Difficulty Level:</span> <span class="value">Beginner</span></div>
              <div class="info-item"><span class="key">Course Size:</span> <span class="value">ECTs Credits</span></div>
            </div>
          </div>
          <div class="info-item-separator"></div>
          <div class="info-box-right">
            <div class="info-container">
              <div class="info-item"><span class="key">Hours per week:</span> <span class="value">4</span></div>
              <div class="info-item"><span class="key">Related Certifications:</span> <span class="value">SQL Wizard</span></div>
              <div class="info-item"><span class="key">Course Cost:</span> <span class="value">$500</span></div>
            </div>
          </div>
          
        </div>
        <div class="course-image">
          <img src="/images/AWS.png" alt="AWS Course Image">
        </div>
      </div>
      <div class="course-description">{{ course.description }}</div>
    </div>

    <courseCard></courseCard>
  


</v-container>
</template>

<script>

import courseCard from '@/components/InformationCardCourse.vue';

export default {
  name: 'CoursePage',
  components: {
    courseCard
  },
  data() {
    return {
      course: {
          title: 'SQL for Beginners',
          sessionDate: '2024-04-25', // Example date
          description: 'This is a detailed description of the course. :3 in theory, if we actually had content to put here',
      },
    };
  }
}


</script>
  
<style lang="scss" scoped>
  
/* The items, have 2 columns to break up the information
 */
  .session-date {
    text-align: center;
    font-size: 1.2em;
    margin-bottom: 15px;
  }
  .grid-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 20px;
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
    gap: 20px;
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
    margin-right: 8px; /* Adjust the space between key and value */
  }

  .course-title, .course-description {
      margin-top: 5px;
      width: 100%;
      text-align: center;
  }

  .course-image img {
  
    min-width: 30%;
    max-width:50%;
    height: auto;
  }
</style>
    