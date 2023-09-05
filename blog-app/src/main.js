// import './assets/main.css'

import {createApp} from 'vue'
import App from './App.vue'
import CKEditor from '@ckeditor/ckeditor5-vue';

createApp(App).use(CKEditor).mount('#app')
