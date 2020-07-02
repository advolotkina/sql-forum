<template>
  <div id="signin" class="mx-auto">
    <div class="login-form">
      <b-card
        title="Login"
        tag="article"
        style="max-width: 20rem;"
        class="mb-2"
      >
        <div>
          <b-form-input type="text" placeholder="Login" v-model="user.login" />
          <div class="mt-2"></div>

          <b-form-input
            type="password"
            placeholder="Password"
            v-model="user.password"
          />
          <div class="mt-2"></div>
        </div>

        <b-button block v-on:click="authenticateUser" variant="primary"
          >Login</b-button
        >

        <hr class="my-4" />
      </b-card>
    </div>
  <v-dialog v-model="errorDialog" max-width="500px">
    <v-card>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" sm="6" md="4">
              <v-textarea v-text="message"></v-textarea>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="ok">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
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
    password: ""
  };
  private message: any = "";
  private errorDialog = false;

  ok(){
    this.message = "";
    this.errorDialog = false;
  }

  authenticateUser() {
    const data = {
      login: this.user.login,
      password: this.user.password
    };

    AuthService.authenticate(data)
      .then(response => {
        this.$store.dispatch("login", {
          role: response.data.role,
          login: response.data.login,
          id: response.data.id
        });
        this.$router.push("/groups");
      })
       .catch((error) => {
              if (error.response) {
                this.message = error.response.data.message;
                this.errorDialog = true;
              }
            });
  }
}
</script>

<style scoped></style>
