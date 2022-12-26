import Vue from 'vue'
import VueRouter from 'vue-router'
import home from "../views/home.vue"
import login from '../views/login.vue'
import profile from '../views/profile.vue'
import admin_estate_list from "../views/admin_estate_list.vue";
import admin_category_list from "../views/admin_category_list.vue";
import estate_detail from "../views/estate_detail.vue";
import admin_user_list from "../views/admin_user_list.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: home
  },
  {
    path: '/login',
    name: 'login',
    component: login
  },
  {
    path: '/profile',
    name: 'profile',
    component: profile
  },
  {
    path: "/estates/:id/detail",
    name: "estate_detail",
    component: estate_detail,
  },

  //Admin Auth routes
  {
    path: "/admin/estates",
    name: 'admin_estate_list',
    component: admin_estate_list,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/categories",
    name: 'admin_category_list',
    component: admin_category_list,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/users",
    name: 'admin_user_list',
    component: admin_user_list,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // }
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  let loginUser = router.app.$store.getters.loginUser;
  let isLogin = router.app.$store.getters.isLogin;

  if (to.meta.requiresAuth && !isLogin) {
    next({ path: "/" });
  } else if (to.meta.requiresAuth && to.meta.requiresAdmin && loginUser.role != 'admin') {
    next({ path: "/" });
  } else {
    next();
  }
})

export default router;
