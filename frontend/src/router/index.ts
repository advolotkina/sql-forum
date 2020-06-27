import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Home",
    component: () =>
        import("../components/Home.vue")
  },
  {
    path: '/login',
    name: 'SignIn',
    component: () =>
        import("../components/SignIn.vue")
  },
  {
    path: "/themes",
    name: "Theme",
    component: () =>
      import("../components/Theme.vue")
  },
  {
    path: '/register',
    name: 'SignUp',
    component: () =>
        import("../components/SignUp.vue")
  },
  {
    path: '/add_theme',
    name: 'AddTheme',
    component: () =>
        import("../components/AddTheme.vue")
  },
  {
    path: '/groups',
    name: 'Groups',
    component: () =>
        import("../components/Groups.vue")
  },
  {
    path: "/themes/:id",
    name: "ThemeInfo",
    component: () =>
        import("../components/ThemeInfo.vue")
  },
  { path: "/themes/:id/topics/:id",
    name: "Topic",
    component: () =>
        import("../components/Topic.vue")
  },
  {
    path: "/users/:id",
    name: "UserProfile",
    component: () =>
        import("../components/UserProfile.vue")
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
