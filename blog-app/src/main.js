// import './assets/main.css'

import {createApp} from 'vue'
import {createRouter, createWebHistory} from "vue-router";
import CKEditor from '@ckeditor/ckeditor5-vue';
import App from './App.vue'
import LoginPage from "./pages/LoginPage.vue";
import HomePage from "./pages/HomePage.vue";
import Post from "@/design/Post.vue";
import ImagePage from "@/pages/ImagePage.vue";

const routes = [
    { path: '/', name: 'home', component: HomePage },
    { path: '/login', name: 'login', component: LoginPage },
    { path: '/design', name: 'design', component: Post },
    {path: '/image', name: 'image', component: ImagePage},
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
