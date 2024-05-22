import { createApp, ref } from 'vue'
import { registerPlugins } from '@/plugins'
import App from './App.vue'
import router from './router';
import { store } from './utility/store';
import { getCookie } from './utility/cookieHelper';

const courseApp = createApp(App)
courseApp.config.globalProperties.$backendUrl = "http://localhost:8080/";
courseApp.config.globalProperties.$difficulties = ["Beginner", "Intermediate", "Expert"];
courseApp.config.globalProperties.$currencies = ref([]);
courseApp.config.globalProperties.$currency = ref("");
courseApp.config.globalProperties.$authFetch = async function (endpoint, requestData) {
    const jwt = getCookie("authToken");
    let response;
    if (store.user.isLoggedIn && jwt) {
        if (requestData == null) {
            requestData = { headers: {} };
        } else if (requestData.headers == null) {
            requestData.headers = {};
        }
        requestData.headers.Authorization = "Bearer " + jwt;
        response = await fetch(endpoint, requestData);
    } else {
        response = null;
    }
    return response;
}
courseApp.config.globalProperties.$authFetchOrPromptLogin = async function (endpoint, requestData) {
    const response = await this.$authFetch(endpoint, requestData);
    if (response == null || response.status === 403) {
        store.logout();
        this.$router.push("/login");
    }
    return response;
}

registerPlugins(courseApp)



courseApp.use(router)
    .mount('#app');
