import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [{
  path: '/',
  redirect: '/home',
  component: () => import('@/pages/Login.vue')
}, {
  path: '/home',
  component: () => import('@/pages/Home.vue'),
  children: []
}, {
  path: '/singer',
  name: 'singer',
  component: () => import('@/pages/Singer.vue')
}, {
  path: '/songlist',
  name: 'songlist',
  component: () => import('@/pages/SongList.vue')
}, {
  path: '/mymusic',
  name: 'mymusic',
  component: () => import('@/pages/MyMusic.vue')
}, {
  path: '/search',
  name: 'search',
  component: () => import('@/pages/Search.vue')
}, {
  path: '/login',
  name: 'login',
  component: () => import('@/pages/SignIn.vue')
}, {
  path: '/setting',
  name: 'setting',
  component: () => import('@/pages/UserSetting.vue')
},{
  path: '/singer-album',
  name: 'singerAlbum',
  component: () => import('@/pages/SingerAlbum.vue')
},{
  path: '/song-list-album',
  name: 'songListAlbum',
  component: () => import('@/pages/SongListAlbum.vue')
}, {
  path: '*',
  name: 'NotFound',
  component: () => import('@/pages/404NotFound.vue')
}]

/* const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
} */

const router = new VueRouter({
  mode: 'history',
  routes
})

//全局前置守卫
router.beforeEach((to, from, next) => {
  next()
})

//全局解析守卫

//全局后置钩子
router.afterEach((to, from) => {
    // this.$store.commit('SET_ACTIVE_NAV', '')
})

//全局

export default router