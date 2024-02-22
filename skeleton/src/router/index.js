import { createRouter, createWebHistory } from 'vue-router';

//Import the pages as we create them.
import HomePage from '../pages/HomePage.vue';
import CoursePage from '../pages/CoursePage.vue';
import ContactPage from '../pages/ContactPage.vue';
import AboutPage from '../pages/AboutPage.vue';
import AccountPage from '../pages/AccountPage.vue';
import CourseFavoriteListPage from '../pages/CourseFavoriteListPage.vue';


//Add the routes / paths to the "pages" here.
const routes = [
  { path: '/', component: HomePage, name: 'Home' },
  { path: '/course', component: CoursePage, name: 'Course' },
  { path: '/contact', component: ContactPage, name: 'Contact'},
  { path: '/about', component: AboutPage, name: 'About' },
  { path: '/account', component: AccountPage, name: 'Account' },
  { path: '/favorites', component: CourseFavoriteListPage, name: 'Favorites'},
  { path: '/admin', component: AdminDashboard, name: 'AdminDashboard' },
  { path: '/admin/courses', component: AdminCourses, name: 'AdminCourses' },
  { path: '/admin/users', component: AdminUsers, name: 'AdminUsers' },
  // Define routes for other pages
];

/**
 * Creates a router instance, to navigate the Vue application.  
 */
const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
