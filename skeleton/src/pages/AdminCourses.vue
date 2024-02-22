<template>
  <div>
    <h1>Course Management</h1>
    <course-form @course-submitted="addOrUpdateCourse" />
    <course-list :courses="courses" @edit-course="prepareCourseForEdit" @delete-course="deleteCourse" />
  </div>
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
        const response = await axios.get('/admin/courses');
        this.courses = response.data;
      } catch (error) {
        console.error('Failed to fetch courses:', error);
      }
    },
    async addOrUpdateCourse(courseData) {
      const url = this.currentCourse ? `/admin/courses/${this.currentCourse.id}` : '/admin/courses';
      const method = this.currentCourse ? 'put' : 'post';

      try {
        const response = await axios[method](url, courseData);
        console.log(response.data);
        // Refresh the course list
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
        await axios.delete(`/admin/courses/${courseId}`);
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
