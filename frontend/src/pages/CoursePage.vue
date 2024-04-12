<template>
  <v-container color ="background">
<v-table class="CoursePage_table">
    <thead>
        <tr>
            <td class="course-title" colspan="2"><h3>{{course.title}}</h3></td>
        </tr>
    </thead>
    <tbody>
        <div class="course_table_content">
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
        </div>
    
        <tr>
        <td colspan="2">{{ course.description }}</td>
    </tr>
    </tbody>
    
</v-table>
  </v-container>
</template>
  
<script>
  export default {
  name: 'CoursePage',
  data() {
    return {
      course: {
        title: 'SQL for Beginners',
        description: 'This is a detailed description of the course. :3'
        + ' in theory, if we actually had content to put here',
      },
    };
  }
}
</script>

<style scoped>

/*These are just temporary styles, we need to edit this but this is simply for the "functional" aspect
Maybe we can add a picture or something to the right to fill the web page a bit more? */

.table_component table {
    flex: auto;
    table-layout: fixed;
    border-collapse: collapse;
    border-spacing: 1px;
    text-align: center;
}


img {
    float: right;
    margin: 10 px;
}

.table_component {
    overflow: auto;

}

.course_table_content td {
    width: 25vh;
}

.course_table_content td {
    border: 1px solid #dededf;
    /*background-color: #ffffff;
    color: #000000;*/
    padding: 10px;
}

thead {
    padding: 20px;
    font-size: x-large;
}




h3 {
    text-align: center;
}


</style>