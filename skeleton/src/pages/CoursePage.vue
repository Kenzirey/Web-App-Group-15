<template>
  <v-container>
<div class="table_component" role="region" tabindex="0">
<table>
    <thead>
        <tr>
            <th><h3>{{ course.title }}</h3></th>
            <th><h3>{{ course.courseTitle }}</h3></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Difficulty Level</td>
            <td>Beginner</td>
        </tr>
        <tr>
            <td>Course Session</td>
            <td>Date</td>
        </tr>
        <tr>
            <td>Course Size</td>
            <td>ECTs Credits</td>
        </tr>
        <tr>
            <td>Hours per week</td>
            <td>4</td>
        </tr>
        <tr>
            <td>Related Certifications</td>
            <td>SQL Wizard</td>
        </tr>
        <tr>
            <td>Course Providers</td>
            <td>NTNU</td>
        </tr>
        <tr>
            <td>Course Cost</td>
            <td>$500</td>
        </tr>
        <tr>
        <td colspan="2">{{ course.description }}</td>
    </tr>
    </tbody>
</table>
</div>
  </v-container>
</template>
  
  <script>
  export default {
  name: 'CoursePage',
  data() {
    return {
      course: {
        title: 'Course Title',
        courseTitle: "SQL for Beginners",
        description: 'This is a detailed description of the course. :3'
        + ' in theory, if we actually had content to put here',
      },
    };
  }
}
  </script>

<style>

/*These are just temporary styles, we need to edit this but this is simply for the "functional" aspect
Maybe we can add a picture or something to the right to fill the web page a bit more? */

.table_component {
    overflow: auto;
    width: 100%;
}

.table_component table {
    border: 1px solid #dededf;
    height: 100%;
    width: 100%;
    table-layout: fixed;
    border-collapse: collapse;
    border-spacing: 1px;
    text-align: left;
}

.table_component th {
    border: 1px solid #dededf;
    background-color: #eceff1;
    color: #000000;
    padding: 5px;
}

.table_component td {
    border: 1px solid #dededf;
    background-color: #ffffff;
    color: #000000;
    padding: 5px;
}
</style>