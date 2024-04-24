import { createApp } from 'vue'
import { registerPlugins } from '@/plugins'
import App from './App.vue'
import router from './router';
import vuetify from './plugins/vuetify'

const courseApp = createApp(App)

registerPlugins(courseApp)



courseApp.use(router)
.use(vuetify)
.mount('#app');
