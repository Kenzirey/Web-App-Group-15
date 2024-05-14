import { createRouter, createWebHistory } from 'vue-router';
import { store} from '../utility/store';

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
import LoginForm from '@/components/LoginForm.vue';




//Add the routes / paths to the "pages" here.
const routes = [
  { path: '/', component: HomePage, name: 'Home' },
  { path: '/course/:id', component: CoursePage, name: 'Course' },
  { path: '/contact', component: ContactPage, name: 'Contact'},
  { path: '/about', component: AboutPage, name: 'About' },
  { path: '/account', component: AccountPage, name: 'Account', meta: { requiresAuth: true } },
  { path: '/favorites', component: CourseFavoriteListPage, name: 'Favorites', meta: { requiresAuth: true } },
  { path: '/admin', component: AdminDashboard, name: 'AdminDashboard', meta: { requiresAuth: true, roles: ['admin'] } },
  { path: '/admin/courses', component: AdminCourses, name: 'AdminCourses', meta: { requiresAuth: true, roles: ['admin'] } },
  { path: '/admin/users', component: AdminUsers, name: 'AdminUsers', meta: { requiresAuth: true, roles: ['admin'] } },
  { path: '/forms', component: FormsPage, name: 'Forms' },
  { path: '/search', component: SearchResults, name: 'SearchResults'},
  { path: '/login', component: LoginForm, name: 'Login', meta: { requiresAuth: false } },
  { path: '/submit', component: SubmitPage, name: 'Submit' }

  // Define routes for other pages
];

/**
 * Creates a router instance, to navigate the Vue application.  
 */
const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Navigation guard
router.beforeEach((to, from, next) => {
  const isLoggedIn = store.user.isLoggedIn;
  const userRoles = store.user.roles;

  if (to.matched.some(record => record.meta.requiresAuth) && !isLoggedIn) {
    next({ name: 'Login' });
  } else if (isLoggedIn) {
    if (userRoles.includes('ROLE_ADMIN') && !to.path.startsWith('/admin')) {
      next({ path: '/admin' });
    } else if (!userRoles.includes('ROLE_ADMIN') && to.path.startsWith('/admin')) {
      next({ path: '/' });
    } else {
      next();
    }
  } else {
    next();
  }
});



export default router;
