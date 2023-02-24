import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [{
    /* path: '/home',
    iconClass: 'fa fa-users',
    component: () => import('@/components/Home.vue'),
    children: [{
      path: '/home/student',
      name:'StudentList',
      iconClass: 'fa fa-list',
      component: ()=> import ('@/components/students/StudentList.vue')
    },{
      path: '/home/info',
      name:'InfoList',
      iconClass: 'fa fa-list',
      component: ()=> import ('@/components/students/InfoList.vue')
    }]
  },{ */
    path: '/',
    redirect: '/login',
    component: () => import('@/pages/Login.vue')
  },{
    path: '/login',
    name: 'Login',
    component: () => import('@/pages/Login.vue')
  },{
    path: '/home',
    redirect:'/home/info',
    component: ()=>import('@/components/Home.vue'),
    children: [{
      path: 'info',
      index: 'info',
      iconClass: 'el-icon-location',
      component: ()=> import('@/pages/InfoPage.vue')
    }, {
      path: 'consumer',
      index: 'consumer',
      iconClass: 'el-icon-document',
      component: ()=> import('@/pages/ConsumerPage.vue')
    },{
      path: 'singer',
      index: 'singer',
      iconClass: 'el-icon-document',
      component: ()=> import('@/pages/SingerPage.vue')
    },{
      path: 'song',
      index: 'song',
      iconClass: 'el-icon-document',
      component: ()=> import('@/pages/SongPage.vue')
    },{
      path: 'songlist',
      index: 'songlist',
      iconClass: 'el-icon-document',
      component: ()=> import('@/pages/SongListPage.vue')
    },{
      path: 'songlistdetail',
      index: 'songlistdetail',
      iconClass: 'el-icon-document',
      component: ()=> import('@/pages/SongListDetailPage.vue')
    }]
  }/* {
    path: '/about',
    name: 'about',
    component: () => import('../views/AboutView.vue')
  },{
    path: '*',
    name: 'NotFound',
    component: () => import('@/views/404NotFound.vue')
  } */
]

export default new VueRouter({
  mode: 'history',
  routes
})
