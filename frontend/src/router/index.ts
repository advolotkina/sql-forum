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
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
