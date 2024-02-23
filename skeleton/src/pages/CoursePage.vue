<template>
    <v-container>
      <v-row>
        <v-col cols="12">
          <h1>{{ course.title }}</h1>
          <p>{{ course.description }}</p>
          <h2><v-textarea label="Label">Test</v-textarea></h2>
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