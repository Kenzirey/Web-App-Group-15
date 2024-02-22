<template>
  <form @submit.prevent="handleSubmit">
    <div>
      <label for="name">Course Name:</label>
      <input id="name" v-model="course.name" required>
    </div>
    <div>
      <label for="description">Description:</label>
      <textarea id="description" v-model="course.description" required></textarea>
    </div>
    <button type="submit">Submit</button>
  </form>
</template>

<script>
export default {
  name: 'CourseForm',
  props: {
    initialCourse: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      course: this.initialCourse,
    };
  },
  methods: {
    handleSubmit() {
      this.$emit('course-submitted', this.course);
      this.course = { name: '', description: '' };
    }
  },
};
</script>
