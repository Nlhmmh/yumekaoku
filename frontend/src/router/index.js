import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store";
import home from "../views/home.vue";
import estate_detail from "../views/estate_detail.vue";
import categorized_estates from "../views/categorized_estates.vue";
import login from "../views/login.vue";
import register from "../views/register.vue";
import profile from "../views/profile.vue";
import appointments from "../views/appointments.vue";
import admin_estate_list from "../views/admin_estate_list.vue";
import admin_category_list from "../views/admin_category_list.vue";
import admin_estate_create from "../views/admin_estate_create.vue";
import admin_estate_update from "../views/admin_estate_update.vue";
import admin_users from "../views/admin_users.vue";
import admin_appointments from "../views/admin_appointments.vue";
import admin_profile from "../views/admin_profile.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: home,
  },
  {
    path: "/login",
    name: "login",
    component: login,
  },
  {
    path: "/register",
    name: "register",
    component: register,
  },
  {
    path: "/profile",
    name: "profile",
    component: profile,
  },
  {
    path: "/appointments",
    name: "appointments",
    component: appointments,
  },
  {
    path: "/estates/:id",
    name: "estate_detail",
    component: estate_detail,
  },
  {
    path: "/categories/:categoryId/estates",
    name: "categorized_estates",
    component: categorized_estates,
  },

  // Admin Auth routes
  {
    path: "/admin/estates",
    name: "admin_estate_list",
    component: admin_estate_list,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/estate/create",
    name: "admin_estate_create",
    component: admin_estate_create,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/estate/:id/update",
    name: "admin_estate_update",
    component: admin_estate_update,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/categories",
    name: "admin_category_list",
    component: admin_category_list,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/users",
    name: "admin_users",
    component: admin_users,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/appointments",
    name: "admin_appointments",
    component: admin_appointments,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/profile",
    name: "admin_profile",
    component: admin_profile,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  // Login User From Vuex
  let loginUser = router.app.$store.getters.loginUser;
  // IsLogin From Vuex
  let isLogin = router.app.$store.getters.isLogin;

  // to -> Screen To Go
  // Need to be login
  if (to.meta.requiresAuth == true) {
    // If not login -> Go to Home Page
    if (!isLogin) {
      next({ path: "/" });
      return;
    }

    // Need to be admin
    if (to.meta.requiresAdmin == true) {
      // If loginUser role is not admin -> Go to Home Page
      if (loginUser.role != "admin") {
        next({ path: "/" });
        return;
      }
    }
  }

  // If All Okay
  next();
});

export default router;
