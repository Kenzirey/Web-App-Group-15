import { createApp } from 'vue'
import './assets/global.css';
import App from './App.vue'
import router from './router';
import vuetify from './plugins/vuetify'




createApp(App).use(router).use(vuetify).mount('#app');
