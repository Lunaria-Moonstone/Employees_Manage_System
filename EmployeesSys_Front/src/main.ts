import '@varlet/ui/es/style';
import '@/assets/global-styles.scss';

import { createApp } from 'vue';
import Varlet from '@varlet/ui';
import VueCookie from 'vue-cookies';
// import VueSession from 'vue-session';

import App from '@/App.vue';
import router from './router';

const app = createApp(App);

// global variable
app.config.globalProperties.$isVisible = false;

app.use(Varlet);
app.use(VueCookie);
// app.use(VueSession);
app.use(router);

app.mount('#app');
