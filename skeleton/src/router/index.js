import { createRouter, createWebHistory } from 'vue-router';

//Import the pages as we create them.
import HomePage from '../pages/HomePage.vue';
import CoursePage from '../pages/CoursePage.vue';

//Add the routes / paths to the "pages" here.
const routes = [
  { path: '/', component: HomePage, name: 'Home' },
  { path: '/course', component: CoursePage, name: 'Course' },
  // Define routes for other pages
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
