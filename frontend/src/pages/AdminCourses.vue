<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <h1 class="text-h4 my-4">Course Management</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <course-form @course-submitted="addOrUpdateCourse" />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <course-list :courses="courses" @edit-course="prepareCourseForEdit" @delete-course="deleteCourse" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
import CourseForm from '@/components/CourseForm.vue';
import CourseList from '@/components/CourseList.vue';

export default {
  name: 'AdminCourses',
  components: {
    CourseForm,
    CourseList,
  },
  data() {
    return {
      courses: [],
      currentCourse: null,
    };
  },
  methods: {
    async fetchCourses() {
      try {
        const response = await axios.get('/courses');
        this.courses = response.data;
      } catch (error) {
        console.error('Failed to fetch courses:', error);
      }
    },
    async addOrUpdateCourse(courseData) {
      const url = this.currentCourse ? `/courses/${this.currentCourse.id}` : '/courses';
      const method = this.currentCourse ? 'put' : 'post';

      const formData = new FormData();
      for (const key in courseData) {
        formData.append(key, courseData[key]);
      }
      if (courseData.image) {
        formData.append('image', courseData.image);
      }

      try {
        await axios[method](url, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        this.fetchCourses();
      } catch (error) {
        console.error('Failed to add/update course:', error);
      }
      this.currentCourse = null;
    },
    prepareCourseForEdit(course) {
      this.currentCourse = course;
    },
    async deleteCourse(courseId) {
      if (!confirm('Are you sure you want to delete this course?')) {
        return;
      }

      try {
        await axios.delete(`/courses/${courseId}`);
        this.courses = this.courses.filter(course => course.id !== courseId);
      } catch (error) {
        console.error('Failed to delete course:', error);
      }
    },
  },
  mounted() {
    this.fetchCourses();
  },
};
</script>
