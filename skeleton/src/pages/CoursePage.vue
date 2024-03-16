<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <div class="course-information">
          <h3>{{ course.title }}</h3>
          <v-list dense>
            <v-list-item v-for="(detail, index) in courseDetails" :key="index">
              <v-list-item-content>
                <!--Title is the primary heading, just title, while the subtitle is the contents (secondary)-->
                <v-list-item-title>{{ detail.title }}</v-list-item-title>
                <v-list-item-subtitle>{{ detail.value }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <div>{{ course.description }}</div>
        </div>
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
        description: 'This is a detailed description of the course. :3'
        + ' in theory, if we actually had content to put here',
      },
      /**
       * Test data, until we connect to a database with queries and the like.
       */
      courseDetails: [
        { title: 'Difficulty Level', value: 'Beginner' }, 
        { title: 'Course Session', value: 'Spring 2021' },
        { title: 'Course Size', value: '20 ECTs credits' },
        { title: 'Hours per Week', value: '4' },
        { title: 'Related Certifications', value: 'Certification XYZ' },
        //Thinking we should probably add more content to the right? and then have description on the bottom?
        { title: 'Course Providers', value: 'NTNU' },
        { title: 'Course Cost', value: '500'},
      ]
    };
  }
}
  </script>