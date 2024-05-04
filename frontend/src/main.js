import { createApp } from 'vue'
import { registerPlugins } from '@/plugins'
import App from './App.vue'
import router from './router';

const courseApp = createApp(App)

registerPlugins(courseApp)



courseApp.use(router)
.mount('#app');
