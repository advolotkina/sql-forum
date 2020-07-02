<template>
  <div id="signup" class="mx-auto">
    <v-container>
<!--    <div class="login-form">-->
<!--      <b-card-->
<!--        title="Register"-->
<!--        tag="article"-->
<!--        style="max-width: 20rem;"-->
<!--        class="mb-2"-->
<!--      >-->
<!--        <div>-->
<!--          <b-alert-->
<!--            :show="dismissCountDown"-->
<!--            dismissible-->
<!--            variant="danger"-->
<!--            @dismissed="dismissCountDown = 0"-->
<!--            @dismiss-count-down="countDownChanged"-->
<!--          >-->
<!--            {{ alertMessage }}-->
<!--          </b-alert>-->
<!--        </div>-->
<!--        <div>-->
<!--          <b-alert variant="success" :show="successfullyRegistered">-->
<!--            <p>You have been successfully registered!</p>-->
<!--            <p>-->
<!--              A confirmation email has been sent. Please, click the link in this-->
<!--              email to verify your email.-->
<!--            </p>-->
<!--            <hr />-->
<!--            <router-link to="/login">-->
<!--              <b-button variant="primary" class="btn-violet">Login</b-button>-->
<!--            </router-link>-->
<!--          </b-alert>-->
<!--        </div>-->
<!--        <div>-->
<!--          <b-form-input type="text" placeholder="Login" v-model="user.login" />-->
<!--          <div class="mt-2"></div>-->

<!--          <b-form-input type="text" placeholder="Name" v-model="user.name" />-->
<!--          <div class="mt-2"></div>-->

<!--          <b-form-input type="text" placeholder="Email" v-model="user.email" />-->
<!--          <div class="mt-2"></div>-->

<!--          <b-form-input-->
<!--            type="password"-->
<!--            placeholder="Password"-->
<!--            v-model="user.password"-->
<!--          />-->
<!--          <div class="mt-2"></div>-->

<!--          <b-form-input-->
<!--            type="password"-->
<!--            placeholder="Confirm Password"-->
<!--            v-model="confirmpassword"-->
<!--          />-->

<!--          <div class="mt-2"></div>-->
<!--        </div>-->

<!--        <b-button v-on:click="registerUser" variant="primary"-->
<!--          >Register</b-button-->
<!--        >-->
<!--      </b-card>-->
<!--    </div>-->
    <v-form  ref="form"
             v-model="valid"
             lazy-validation
             max-width="500px">
      <v-row>
        <v-col cols="12">
          <v-text-field
                  :counter="255"
                  v-model="user.name"
                  label="User name"
                  :rules="nameRules"
                  single-line
                  outlined
          ></v-text-field>
        </v-col>
        <v-col cols="12">
          <v-text-field
                  :counter="255"
                  :rules="nameRules"
                  v-model="user.login"
                  label="User login"
                  single-line
                  outlined
          ></v-text-field>
        </v-col>
        <v-col cols="12">
          <v-text-field
                  :counter="255"
                  :rules="emailRules"
                  v-model="user.email"
                  label="User email"
                  single-line
                  outlined
          ></v-text-field>
        </v-col>
        <v-col cols="12">
          <v-text-field
                  :append-icon="show3 ? 'mdi-eye' : 'mdi-eye-off'"
                  :rules="[passwordRules.required, passwordRules.min]"
                  :type="show3 ? 'text' : 'password'"
                  name="input-10-2"
                  label="Password"
                  v-model="user.password"
                  hint="At least 8 characters"
                  value=""
                  single-line
                  outlined
                  class="input-group--focused"
                  @click:append="show3 = !show3"
          ></v-text-field>
        </v-col>
        <v-col cols="12">
          <v-text-field
                  :append-icon="show4 ? 'mdi-eye' : 'mdi-eye-off'"
                  :rules="[passwordRules.required, passwordRules.passwordMatch]"
                  :type="show4 ? 'text' : 'password'"
                  name="input-10-2"
                  label="Password again"
                  hint="At least 8 characters"
                  v-model="user.passwordAgain"
                  value=""
                  single-line
                  outlined
                  error
                  @click:append="show4 = !show4"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-btn @click="registerUser">Register</v-btn>
    </v-form>
    </v-container>
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
  valid =  false;
  show3 = false;
  show4 = false;
  private user: any = {
    id: null,
    login: "",
    password: "",
    name: "",
    email: "",
    passwordAgain: ""
  };
  nameRules: any = [
    (v: any) => !!v || 'Name is required',
    v => (v && v.length <= 255) || 'Name must be less than 255 characters',
  ];
  emailRules: any =  [
    (v: any) => !!v || 'E-mail is required',
    v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
    v => (v && v.length <= 255) || 'Email must be less than 255 characters'
  ];
  passwordRules: any =  {
    required: value => !!value || 'Required.',
    min: v => v.length >= 8 || 'Min 8 characters',
    passwordMatch: () => (this.user.password === this.user.passwordAgain) || 'Passwords don\'t match',
  }

  private message: any = "";
  private errorDialog = false;

  ok(){
    this.message = "";
    this.errorDialog = false;
  }

  validate(): boolean {
    return (this.$refs.form as Vue & { validate: () => boolean }).validate();
  }

  registerUser() {
    if (!this.validate()){
      return;
    }
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
