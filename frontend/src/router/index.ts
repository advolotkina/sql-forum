import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import store from "../store/index";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Home",
    component: () => import("../components/Home.vue")
  },
  {
    path: "/login",
    name: "SignIn",
    component: () => import("../components/SignIn.vue")
  },
  {
    path: "/themes",
    name: "Theme",
    component: () => import("../components/Theme.vue"),
    meta: {
      permission: 'admin',
    },
  },
  {
    path: "/register",
    name: "SignUp",
    component: () => import("../components/SignUp.vue")
  },
  // {
  //   path: "/add_theme",
  //   name: "AddTheme",
  //   component: () => import("../components/AddTheme.vue")
  // },
  {
    path: "/groups",
    name: "Groups",
    component: () => import("../components/Groups.vue")
  },
  {
    path: "/themes/:id",
    name: "ThemeInfo",
    component: () => import("../components/ThemeInfo.vue")
  },
  {
    path: "/themes/:id/topics/:id",
    name: "Topic",
    component: () => import("../components/Topic.vue")
  },
  {
    path: "/users/:id",
    name: "UserProfile",
    component: () => import("../components/UserInfo.vue"),

  },
  {
    path: "/profile",
    name: "Profile",
    component: () => import("../components/UserProfile.vue"),
    meta: {
      permission: 'user',
    },
  },
  {
    path: "/change_profile",
    name: "ChangeProfile",
    component: () => import("../components/ChangeUserProfile.vue"),
    meta: {
      permission: 'user',
    },
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(route => route.meta.permission === 'admin')) {
    if (store.getters.isAdmin){
      next();
    }else{
      next({ path: '/' });
    }
  }
  if (to.matched.some(route => route.meta.permission === 'user')) {
    if(store.getters.isAuthenticated){
      next();
    }else{
      next({ path: '/login' });
    }
  }
  next();
});
export default router;
