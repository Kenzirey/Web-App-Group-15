<template>
  <div class="course-list">
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        @input="filterList"
        placeholder="Search"
        class="search-input"
      />
    </div>
    <h2>Courses</h2>
    <table class="course-table">
      <thead>
        <tr>
          <th>Name</th>
          <th>Description</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in filteredCourses" :key="course.id">
          <td>{{ course.name }}</td>
          <td>{{ course.description }}</td>
          <td>
            <button @click="onEditCourse(course)" class="btn-edit">Edit</button>
            <button @click="onDeleteCourse(course.id)" class="btn-delete">Delete</button>
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
        course.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
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
  --btn-primary-bg: #007bff;
  --btn-primary-bg-hover: #0056b3;
  --btn-danger-bg: #dc3545;
  --btn-danger-bg-hover: #bd2130;
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

.btn-edit, .btn-delete {
  padding: 8px 16px;
  margin-right: 10px;
  border: none;
  border-radius: 4px;
  background-color: var(--btn-primary-bg);
  color: #fff;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-delete {
  background-color: var(--btn-danger-bg);
}

.btn-edit:hover, .btn-delete:hover {
  background-color: var(--btn-primary-bg-hover);
}

.btn-delete:hover {
  background-color: var(--btn-danger-bg-hover);
}
</style>
