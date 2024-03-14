<template>
    <v-container>
      <v-row>
        <v-col cols="12">
          <div class="course information">
            <h3>{{ course.title }}</h3>
            <p>Difficulty Level</p>
            <p>Course Session (date)</p>
            <p>Course size (ECTs credits)</p>
            <p>Hours per week</p>
            <p>Related certifications</p>
            <p>Course Providers</p>
            <p>{{ course.description }}</p>
          </div>
          
          <!-- Add more details about course here, and research v-container, v-col, row etc -->
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script>
  export default {
    name: 'CoursePage',
    data() {
      return {
        course: {
          title: 'Example Course Title',
          description: 'This is a detailed description of the course.',
          // Include more course details here, as well as add the query things.
        }
      };
    }
  }
  </script>