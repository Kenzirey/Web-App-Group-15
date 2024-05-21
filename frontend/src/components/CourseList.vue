<template>
  <div class="course-list">
    <div class="search-container">
      <input type="text" v-model="searchQuery" @input="filterList" placeholder="Search" class="search-input" />
    </div>
    <h2>Courses</h2>
    <table class="course-table">
      <thead>
        <tr>
          <th>Course Name</th>
          <th>Description</th>
          <th>Difficulty</th>
          <th>Start Date</th>
          <th>End Date</th>
          <th>Credits</th>
          <th>Hours/Week</th>
          <th>Certification</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in filteredCourses" :key="course.courseId">
          <td>{{ course.courseName }}</td>
          <td>{{ course.courseDescription }}</td>
          <td>{{ course.difficultyLevel }}</td>
          <td>{{ formatDate(course.startDate) }}</td>
          <td>{{ formatDate(course.endDate) }}</td>
          <td>{{ course.courseCredits }}</td>
          <td>{{ course.hoursPerWeek }}</td>
          <td>{{ course.relatedCertification }}</td>
          <td>
            <v-btn color="primary" @click="onEditCourse(course)">Edit</v-btn>
            <v-btn color="error" @click="onDeleteCourse(course.courseId)">Delete</v-btn>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'CourseList',
  props: {
    courses: Array,
  },
  data() {
    return {
      searchQuery: '',
    };
  },
  computed: {
    filteredCourses() {
      return this.courses.filter(course =>
        course.courseName.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    formatDate(date) {
      return new Date(date).toLocaleDateString();
    },
    onEditCourse(course) {
      this.$emit('edit-course', course);
    },
    onDeleteCourse(courseId) {
      this.$emit('delete-course', courseId);
    },
    filterList() {
      this.$emit('filter-courses', this.searchQuery);
    },
  },
};
</script>

<style scoped>
:root {
  --border-color: #ddd;
}

.course-list {
  margin-top: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.course-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th, td {
  border: 1px solid var(--border-color);
  padding: 12px;
  text-align: left;
}

.search-container {
  margin-bottom: 20px;
}

.search-input {
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ccc;
  font-size: 16px;
}

.v-btn {
  padding: 8px 16px;
  margin-right: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}
</style>
