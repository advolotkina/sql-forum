<template>
    <div div="signin">
        <div class="login-form">
            <b-card
                    title="Login"
                    tag="article"
                    style="max-width: 20rem;"
                    class="mb-2"
            >
                <div>
                    <b-alert
                            :show="dismissCountDown"
                            dismissible
                            variant="danger"
                            @dismissed="dismissCountDown=0"
                            @dismiss-count-down="countDownChanged"
                    > {{ alertMessage }}
                    </b-alert>
                </div>
                <div>
                    <b-form-input type="text" placeholder="Login" v-model="user.login" />
                    <div class="mt-2"></div>

                    <b-form-input type="password" placeholder="Password" v-model="user.password" />
                    <div class="mt-2"></div>
                </div>

                <b-button block v-on:click="authenticateUser" variant="primary">Login</b-button>

                <hr class="my-4" />


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
            password: ""
        };

        authenticateUser() {
            const data = {
                'login': this.user.login,
                'password': this.user.password
            };

            AuthService.authenticate(data)
                .then((response) => {
                    this.$store.dispatch("login", {'role': response.data.role, 'login': response.data.login});
                    this.$router.push('/home');
                })
                .catch((e) => {
                    console.log(e);
                });
        }

    }
</script>

<style scoped>

</style>