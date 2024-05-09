import { reactive } from 'vue';
import { setCookie,  deleteCookie } from '../utility/cookieHelper';

export const store = reactive({
    user: {
      isLoggedIn: false,
      roles: []
    },
    login(jwt, roles = []) {
      this.user.isLoggedIn = true;
      this.user.roles = roles;
      localStorage.setItem('isLoggedIn', 'true');
      setCookie('authToken', jwt, 1);
    },
    logout() {
      this.user.isLoggedIn = false;
      this.user.roles = [];
      localStorage.removeItem('isLoggedIn');
      deleteCookie('authToken');
    }
  });