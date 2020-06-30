<template>
  <div id="app">
    <v-app>
    <div id="nav">
      <router-link to="/">Home | </router-link>
      <router-link to="/themes" v-if="this.$store.getters.isAdmin">Themes [Admin] | </router-link>
      <router-link to="/groups">Themes | </router-link>
      <router-link to="/profile" v-if="this.$store.getters.isAuthenticated">Profile | </router-link>
      <router-link to="/login" v-if="!this.$store.getters.isAuthenticated">Login | </router-link>
      <router-link to="/register" v-if="!this.$store.getters.isAuthenticated">Register | </router-link>
      <a href="#" class="nav-link text-dark" v-if="this.$store.getters.isAuthenticated" v-on:click="logout">Logout </a>
    </div>
    <router-view />
    </v-app>
  </div>
</template>
<script lang="ts">
  import AuthService from "@/services/AuthService";
  import { Component, Vue } from "vue-property-decorator";
  @Component
  export default class App  extends Vue {
    logout(){
      // this.$store.dispatch('logout');
      // this.$router.push('/');
      AuthService.logout().then(response => {
        console.log(response.statusText);
        this.$store.dispatch('logout');
        this.$router.push('/');
      }).catch((e) => {
        console.log(e);
      });
    }
  }
</script>
<style lang="scss">
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }
  #nav {
    padding: 30px;
    a {
      font-weight: bold;
      color: #2c3e50;
      &.router-link-exact-active {
        color: #42b983;
      }
    }
  }
</style>