<template>
  <form @submit.prevent="handleSubmit" class="course-form">
    <div class="form-group">
      <label for="name" class="form-label">Course Name:</label>
      <input id="name" v-model="course.courseName" class="form-input" required>
    </div>
    <div class="form-group">
      <label for="description" class="form-label">Description:</label>
      <textarea id="description" v-model="course.courseDescription" class="form-textarea" required></textarea>
    </div>
    <div class="form-group">
      <label for="startDate" class="form-label">Start Date:</label>
      <input type="date" id="startDate" v-model="course.startDate" class="form-input" required>
    </div>
    <div class="form-group">
      <label for="endDate" class="form-label">End Date:</label>
      <input type="date" id="endDate" v-model="course.endDate" class="form-input" required>
    </div>
    <div class="form-group">
      <label for="credits" class="form-label">Credits:</label>
      <input type="number" id="credits" v-model="course.courseCredits" class="form-input" required>
    </div>
    <div class="form-group">
      <label for="hoursPerWeek" class="form-label">Hours per Week:</label>
      <input type="number" id="hoursPerWeek" v-model="course.hoursPerWeek" class="form-input" required>
    </div>
    <div class="form-group">
      <label for="difficultyLevel" class="form-label">Difficulty Level:</label>
      <input id="difficultyLevel" v-model="course.difficultyLevel" class="form-input" required>
    </div>
    <div class="form-group">
      <label for="relatedCertification" class="form-label">Related Certification:</label>
      <input id="relatedCertification" v-model="course.relatedCertification" class="form-input">
    </div>
    <div class="form-group">
      <label for="category" class="form-label">Category:</label>
      <select id="category" v-model="selectedCategories" class="form-input" multiple required>
        <option value="" disabled>Select categories</option>
        <option v-for="category in categories" :key="category.id" :value="category.id">{{ category.name }}</option>
      </select>
    </div>
    <div class="form-group">
      <label for="image" class="form-label">Upload Image:</label>
      <input id="image" type="file" @change="handleImageUpload" class="form-input" accept="image/*" ref="imageInput">
    </div>
    <button type="submit" class="btn-submit">Submit</button>
  </form>
</template>

<script>
import { getCookie } from '../utility/cookieHelper';

export default {
  name: 'CourseForm',
  props: {
    initialCourse: Object,
  },
  data() {
    return {
      course: { ...this.initialCourse },
      imageFile: null,
      categories: [],
      selectedCategories: this.initialCourse.categoryIds || [],
    };
  },
  methods: {
    handleImageUpload(event) {
      this.imageFile = event.target.files[0];
    },
    async handleSubmit() {
      if (!this.isFormValid) {
        alert('Please fill all the fields and upload an image.');
        return;
      }
      const formData = new FormData();
      this.fillFormData(formData);

      const endpoint = this.course.courseId ? `courses/${this.course.courseId}` : 'courses';
      const url = this.$backendUrl + endpoint;
      const method = this.course.courseId ? 'PUT' : 'POST';

      try {
        const response = await this.$authFetchOrPromptLogin(url, {
          method,
          body: formData,
        });

        if (!response.ok) {
          throw new Error(`Failed to add/update course: ${response.statusText}`);
        }

        this.$emit('course-submitted', formData);
        this.resetForm();
        this.fetchCourses();
      } catch (error) {
        console.error('Failed to add/update course:', error);
      }
    },
    fillFormData(formData) {
      formData.append('courseName', this.course.courseName);
      formData.append('courseDescription', this.course.courseDescription);
      formData.append('startDate', this.course.startDate);
      formData.append('endDate', this.course.endDate);
      formData.append('courseCredits', this.course.courseCredits);
      formData.append('hoursPerWeek', this.course.hoursPerWeek);
      formData.append('difficultyLevel', this.course.difficultyLevel);
      formData.append('relatedCertification', this.course.relatedCertification);
      formData.append('categoryIds', JSON.stringify(this.selectedCategories));
      if (this.imageFile) {
        formData.append('image', this.imageFile);
      }
    },
    resetForm() {
      this.course = { ...this.initialCourse };
      this.imageFile = null;
      this.selectedCategories = this.initialCourse.categoryIds || [];
      if (this.$refs.imageInput) {
        this.$refs.imageInput.value = '';
      }
    },
    async fetchCategories() {
      const endpoint = 'categories';
      const url = this.$backendUrl + endpoint;

      try {
        const response = await this.$authFetchOrPromptLogin(url, {
          method: 'GET',
        });

        if (!response.ok) {
          throw new Error(`Failed to fetch categories: ${response.statusText}`);
        }

        const data = await response.json();
        console.log('Fetched categories:', data);

        if (Array.isArray(data)) {
          this.categories = data;
        } else {
          throw new Error('Unexpected response format for categories');
        }
      } catch (error) {
        console.error('Failed to fetch categories:', error);
      }
    },
    async fetchCourses() {
      const endpoint = 'courses';
      const url = this.$backendUrl + endpoint;

      try {
        const response = await this.$authFetchOrPromptLogin(url, {
          method: 'GET',
        });

        if (!response.ok) {
          throw new Error(`Failed to fetch courses: ${response.statusText}`);
        }

        const data = await response.json();
        console.log('Fetched courses:', data);

        if (Array.isArray(data)) {
          this.courses = data;
        } else {
          throw new Error('Unexpected response format for courses');
        }
      } catch (error) {
        console.error('Failed to fetch courses:', error);
      }
    }
  },
  computed: {
    isFormValid() {
      return this.course.courseName && this.course.courseDescription && this.course.startDate &&
             this.course.endDate && this.course.courseCredits && this.course.hoursPerWeek &&
             this.course.difficultyLevel && (this.imageFile || !this.initialCourse);
    }
  },
  mounted() {
    this.fetchCategories();
  }
};
</script>

<style scoped>
</style>
