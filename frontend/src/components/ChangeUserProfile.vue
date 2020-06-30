<template>
    <v-form class="mx-auto">
        <v-text-field
                v-model="newUserInfo.name"
                :error-messages="nameErrors"
                :counter="255"
                label="Name"
                @input="$v.name.$touch()"
                @blur="$v.name.$touch()"
        ></v-text-field>
        <v-text-field
                v-model="newUserInfo.email"
                :error-messages="emailErrors"
                label="Email"
                @input="$v.email.$touch()"
                @blur="$v.email.$touch()"
        ></v-text-field>
        <v-text-field
                v-model="newUserInfo.login"
                :error-messages="nameErrors"
                label="Login"
                @input="$v.email.$touch()"
                @blur="$v.email.$touch()"
        ></v-text-field>
        <v-file-input
                v-model="newUserInfo.userPic"
                label="Profile image"
                filled
                prepend-icon="mdi-camera"
                accept="image/*"
        ></v-file-input>
        <v-btn class="mr-4" @click="submit">submit</v-btn>
        <v-btn @click="clear">clear</v-btn>
    </v-form>
</template>

<script lang="ts">
    import { Component, Vue } from "vue-property-decorator";
    import { validationMixin } from 'vuelidate'
    import { required, maxLength, email } from 'vuelidate/lib/validators'
    import UserService from "@/services/UserService";
    import ImageUploadService from "@/services/ImageUploadService";

    @Component
    export default class UserPersonalAccount extends Vue {
        private userInfo: any = null;
        private newUserInfo: any = {
            name: "",
            login: "",
            email: "",
            userPic: ""
        };
        getUserInfo(id: string) {
            UserService.get(id)
                .then(response => {
                    this.userInfo = response.data;
                    console.log(response.data);
                })
                .catch(e => {
                    console.log(e);
                });
        }
        submit(){
            ImageUploadService.upload(this.newUserInfo.userPic)
                .then(response => {
                    console.log(response.data);
                })
                .catch(e => {
                    console.log(e);
                });

        }
        // submit(){
        //     const data = {
        //         name: this.newUserInfo.name,
        //         login: this.newUserInfo.login,
        //         email: this.newUserInfo.email,
        //     };
        //     UserService.update(this.$store.state.id, data)
        //         .then(response => {
        //             this.userInfo = response.data;
        //             console.log(response.data);
        //             this.$router.push("/profile");
        //         })
        //         .catch(e => {
        //             console.log(e);
        //         });
        // }
        mounted() {
            this.getUserInfo(this.$store.state.id);
        }
    }
</script>

<style scoped>

</style>