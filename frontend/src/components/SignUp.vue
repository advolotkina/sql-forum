<template>
  <div id="signup" class="mx-auto">
    <div class="login-form">
      <b-card
        title="Register"
        tag="article"
        style="max-width: 20rem;"
        class="mb-2"
      >
        <div>
          <b-alert
            :show="dismissCountDown"
            dismissible
            variant="danger"
            @dismissed="dismissCountDown = 0"
            @dismiss-count-down="countDownChanged"
          >
            {{ alertMessage }}
          </b-alert>
        </div>
        <div>
          <b-alert variant="success" :show="successfullyRegistered">
            <p>You have been successfully registered!</p>
            <p>
              A confirmation email has been sent. Please, click the link in this
              email to verify your email.
            </p>
            <hr />
            <router-link to="/login">
              <b-button variant="primary" class="btn-violet">Login</b-button>
            </router-link>
          </b-alert>
        </div>
        <div>
          <b-form-input type="text" placeholder="Login" v-model="user.login" />
          <div class="mt-2"></div>

          <b-form-input type="text" placeholder="Name" v-model="user.name" />
          <div class="mt-2"></div>

          <b-form-input type="text" placeholder="Email" v-model="user.email" />
          <div class="mt-2"></div>

          <b-form-input
            type="password"
            placeholder="Password"
            v-model="user.password"
          />
          <div class="mt-2"></div>

          <b-form-input
            type="password"
            placeholder="Confirm Password"
            v-model="confirmpassword"
          />

          <div class="mt-2"></div>
        </div>

        <b-button v-on:click="registerUser" variant="primary"
          >Register</b-button
        >
      </b-card>
    </div>
  </div>
</template>

<script lang="ts">
import AuthService from "@/services/AuthService";
import { Component, Vue } from "vue-property-decorator";
@Component
export default class SignIn extends Vue {
  private user: any = {
    id: null,
    login: "",
    password: "",
    name: "",
    email: ""
  };

  registerUser() {
    const data = {
      login: this.user.login,
      password: this.user.password,
      name: this.user.name,
      email: this.user.email
    };

    AuthService.register(data)
      .then(response => {
        console.log(response);
        this.$router.push("/login");
      })
      .catch(e => {
        console.log(e);
      });
  }
}
</script>

<style scoped></style>
