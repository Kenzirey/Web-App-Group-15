<template>
  <form @submit.prevent="handleSubmit" class="course-form">
    <div class="form-group">
      <label for="name" class="form-label">Course Name:</label>
      <input id="name" v-model="course.name" class="form-input" required>
    </div>
    <div class="form-group">
      <label for="description" class="form-label">Description:</label>
      <textarea id="description" v-model="course.description" class="form-textarea" required></textarea>
    </div>
    <button type="submit" class="btn-submit">Submit</button>
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

<style scoped>
.course-form {
  max-width: 400px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.form-group {
  margin-bottom: 15px;
}

.form-label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-input,
.form-textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

.btn-submit {
  display: block;
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-submit:hover {
  background-color: #0056b3;
}
</style>
