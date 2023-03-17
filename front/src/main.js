import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//引入axios
import axios from 'axios'
import "./axios"
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)
app.config.globalProperties.$axios=axios
app
    .use(store)
    .use(router)
    .use(ElementPlus)
    .mount('#app')
