import { createRouter, createWebHistory } from 'vue-router';

//Import the pages as we create them.
import HomePage from '../pages/HomePage.vue';
import CoursePage from '../pages/CoursePage.vue';
import ContactPage from '../pages/ContactPage.vue';
import AboutPage from '../pages/AboutPage.vue';
import AccountPage from '../pages/AccountPage.vue';
import CourseFavoriteListPage from '../pages/CourseFavoriteListPage.vue';
import AdminDashboard from '../pages/AdminDashboard.vue';
import AdminCourses from '../pages/AdminCourses.vue';
import AdminUsers from '../pages/AdminUsers.vue';
import FormsPage from '../pages/FormsPage.vue';
import SubmitPage from '../pages/SubmitPage';
import SearchResults from '@/pages/SearchResults.vue';
import FavoritePage from '../pages/FavoritePage.vue';



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
  { path: '/forms', component: FormsPage, name: 'Forms' },
  { path: '/submit', component: SubmitPage, name: 'Submit' },
  { path: '/search', component: SearchResults, name: 'SearchResults'},
  { path: '/favorite', component: FavoritePage, name: 'Favorite' }

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
