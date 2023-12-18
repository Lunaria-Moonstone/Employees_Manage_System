import { inject } from 'vue'
import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import { type VueCookies } from 'vue-cookies';

// concat routes
// var routes: RouteRecordRaw[] = [];
// routes = routes.concat(commonRoutes);
// routes = routes.concat(adminRoutes);

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      name: 'login',
      path: '/login',
      component: () => import('@/views/authorization/user-log/SignIn.vue'),
      meta: {
        // allow connect without auth
        directConnect: true
      }
    },
    {
      name: 'register',
      path: '/register',
      component: () => import('@/views/authorization/user-log/SignUp.vue'),
      meta: {
        // allow connect without auth
        directConnect: true
      }
    },
    {
      name: 'admin',
      path: '/',
      component: () => import('@/views/manage/UserManager.vue')
    }
  ],
})

router.beforeEach((to, from, next) => {
  // the page can be read without auth
  if (to.meta.directConnect) {
    next();
  } else {
    const $cookies = inject<VueCookies>('$cookies');
    if ($cookies?.get('user')) next()
    else next('/login')
  }
})

export default router
