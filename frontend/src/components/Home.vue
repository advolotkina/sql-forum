<template>
  <div div="home">
    <b-jumbotron>
      <template slot="header">Forum</template>

      <template slot="lead">
        Training project
      </template>

      <hr class="my-4" />

      <p v-if="!this.$store.getters.isAuthenticated">
        Login or register
      </p>

      <router-link to="/login" v-if="!this.$store.getters.isAuthenticated">
        <b-button>Login</b-button>
      </router-link>

      <router-link to="/register" v-if="!this.$store.getters.isAuthenticated">
        <b-button>Register</b-button>
      </router-link>
    </b-jumbotron>
  </div>
</template>

<script lang="ts">
export default {
  name: "Home"
};
</script>

<style scoped></style>
