import { createApp } from 'vue'
import './assets/global.css';
import { registerPlugins } from '@/plugins'
import App from './App.vue'
import router from './router';
import vuetify from './plugins/vuetify'

const app = createApp(App)

registerPlugins(app)



app.use(router)
.use(vuetify)
.mount('#app');
