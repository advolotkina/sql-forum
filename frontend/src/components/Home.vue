<template>
  <div div="home">
      <v-container fluid>
      <template slot="header">Forum</template>

      <template slot="lead">
        Training project
      </template>

      <hr class="my-4" />

      <p v-if="!this.$store.getters.isAuthenticated">
        Login or register
      </p>

<!--      <router-link to="/login" v-if="!this.$store.getters.isAuthenticated">-->
<!--        <b-button>Login</b-button>-->
<!--      </router-link>-->
        <v-btn to="/login" v-if="!this.$store.getters.isAuthenticated">Login</v-btn>
        <v-btn to="/register" v-if="!this.$store.getters.isAuthenticated">Register</v-btn>

<!--      <router-link to="/register" v-if="!this.$store.getters.isAuthenticated">-->
<!--        <b-button>Register</b-button>-->
<!--      </router-link>-->
        </v-container>
  </div>
</template>

<script lang="ts">
export default {
  name: "Home"
};
</script>

<style scoped></style>
