// import './assets/main.css'

import {createApp} from 'vue'
import {createRouter, createWebHistory} from "vue-router";
import CKEditor from '@ckeditor/ckeditor5-vue';
import App from './App.vue'
import LoginPage from "./pages/LoginPage.vue";
import HomePage from "./pages/HomePage.vue";

const routes = [
    { path: '/login', name: 'login', component: LoginPage },
    { path: '/', name: 'home', component: HomePage },
]

const router = createRouter({
    // 4. Provide the history implementation to use. We are using the hash history for simplicity here.
    history: createWebHistory(),
    routes, // short for `routes: routes`
})

createApp(App)
    .use(CKEditor)
    .use(router)
    .mount('#app')
