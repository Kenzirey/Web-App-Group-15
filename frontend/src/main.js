import { createApp } from 'vue'
import { registerPlugins } from '@/plugins'
import App from './App.vue'
import router from './router';

const courseApp = createApp(App)
courseApp.config.globalProperties.$backendUrl = "http://localhost:8080/";

registerPlugins(courseApp)



courseApp.use(router)
.mount('#app');
