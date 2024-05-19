import { reactive } from 'vue';
import { setCookie,  deleteCookie } from '../utility/cookieHelper';
import { jwtDecode } from 'jwt-decode';

export const store = reactive({
    user: {
      isLoggedIn: false,
      roles: [],
      name: '',
      email: '',
      id: null
    },
    login(jwt) {
      const decoded = jwtDecode(jwt);

      this.user.isLoggedIn = true;
      this.user.roles = decoded.roles;
      this.user.name = decoded.name;
      this.user.email = decoded.email;
      this.user.id = decoded.userId;
      localStorage.setItem('isLoggedIn', 'true');
      setCookie('authToken', jwt, 1);

      return decoded;
    },
    logout() {
      this.user.isLoggedIn = false;
      this.user.roles = [];
      this.user.name = '';
      this.user.email = '';
      this.user.id = null;
      localStorage.removeItem('isLoggedIn');
      deleteCookie('authToken');
    }
  });