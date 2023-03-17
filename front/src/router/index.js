import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },{
    path: '/admin',
    name: 'admin',
    component:() => import('../views/admin')
  },{
    path: '/common',
    name: 'common',
    component:() => import('../views/common')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
