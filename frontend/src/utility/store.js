import { reactive } from 'vue';
import { setCookie,  deleteCookie } from '../utility/cookieHelper';
import { jwtDecode } from 'jwt-decode';

export const store = reactive({
    user: {
      isLoggedIn: false,
      roles: []
    },
    login(jwt) {
      const decoded = jwtDecode(jwt);

      this.user.isLoggedIn = true;
      this.user.roles = decoded.roles;
      localStorage.setItem('isLoggedIn', 'true');
      setCookie('authToken', jwt, 1);

      return decoded;
    },
    logout() {
      this.user.isLoggedIn = false;
      this.user.roles = [];
      localStorage.removeItem('isLoggedIn');
      deleteCookie('authToken');
    }
  });