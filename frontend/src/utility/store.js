import { reactive } from 'vue';
import { setCookie,  deleteCookie } from '../utility/cookieHelper';

export const store = reactive({
    user: {
      isLoggedIn: false,
    },
    login(jwt) {
      this.user.isLoggedIn = true;
      localStorage.setItem('isLoggedIn', 'true');
      setCookie('authToken', jwt, 1);
    },
    logout() {
      this.user.isLoggedIn = false;
      localStorage.removeItem('isLoggedIn');
      deleteCookie('authToken');
    }
  });