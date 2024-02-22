<template>
  <div>
    <h2>Courses</h2>
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Description</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in courses" :key="course.id">
          <td>{{ course.name }}</td>
          <td>{{ course.description }}</td>
          <td>
            <button @click="onEditCourse(course)">Edit</button>
            <button @click="onDeleteCourse(course.id)">Delete</button>
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
  methods: {
    onEditCourse(course) {
      this.$emit('edit-course', course);
    },
    onDeleteCourse(courseId) {
      // Emit an event to the parent component
      this.$emit('delete-course', courseId);
    },
  },
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

</style>
