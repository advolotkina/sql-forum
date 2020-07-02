<template>
    <v-container>
        <v-row dense>
            <v-col cols="12">
                <v-card class="mx-auto"
                        max-width="500"
                        @click="changeAvatar">
                <v-avatar
                        class="profile"
                        color="grey"
                        size="164"
                        tile
                >
                    <v-img v-if="currentUser.userPic !== ''" :src="currentUser.userPic" ></v-img>
                    <v-img v-else src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQXrIBnP4Ppijsc0z3IRH6A6mQEs0OSaVlmFw&usqp=CAU" ></v-img>
                </v-avatar>
                    <v-card-text>
                        Click to change image.
                    </v-card-text>
                </v-card>
            </v-col>
            <v-col cols="12">
                <v-card class="mx-auto"
                        max-width="500">
                    <v-card-text>
                        <p class="text--primary">
                            {{currentUser.name}}
                        </p>
                        <p>Name</p>
                    </v-card-text>
                </v-card>
            </v-col>
            <v-col cols="12">
                <v-card class="mx-auto"
                        max-width="500">
                    <v-card-text>
                        <p class="text--primary">
                            {{currentUser.login}}
                        </p>
                        <p>Login</p>
                    </v-card-text>
                </v-card>
            </v-col>
            <v-col cols="12">
                <v-card class="mx-auto"
                        max-width="500">
                    <v-card-text>
                        <p class="text--primary">
                            {{currentUser.email}}
                        </p>
                        <p>Email</p>
                    </v-card-text>
                </v-card>
            </v-col>
            <v-col cols="12">
                <v-card class="mx-auto"
                        max-width="500">
                    <v-card-text>
                        <p class="text--primary">
                            {{currentUser.registrationDate | moment("from")}}
                        </p>
                        <p>Registered</p>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
        <v-dialog v-model="dialog" max-width="500px">
            <v-card>
                <v-card-text>
                    <v-container>
                        <v-form           ref="form"
                                          v-model="valid"
                                          lazy-validation>
                        <v-row>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field
                                        :counter="255"
                                        v-model="editedItem.name"
                                        label="User name"
                                        :rules="nameRules"
                                ></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field
                                        :counter="255"
                                        :rules="nameRules"
                                        v-model="editedItem.login"
                                        label="User login"
                                ></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field
                                        :counter="255"
                                        :rules="emailRules"
                                        v-model="editedItem.email"
                                        label="User email"
                                ></v-text-field>
                            </v-col>
                        </v-row>
                        </v-form>
                    </v-container>
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                    <v-btn color="blue darken-1" text @click="save">Save</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-dialog v-model="avatarDialog" max-width="500px">
            <v-card>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col cols="12" sm="6" md="4">
                                <v-file-input
                                        v-model="userPic"
                                        label="New profile image"
                                        filled
                                        :rules="fileRules"
                                        prepend-icon="mdi-camera"
                                        accept="image/*"
                                ></v-file-input>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="closeAvatarDialog">Cancel</v-btn>
                    <v-btn color="blue darken-1" text @click="changePhoto">Save</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-btn @click="editItem" color="primary">Change</v-btn>
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
    </v-container>
</template>

<script lang="ts">
    import { Component, Vue } from "vue-property-decorator";
    import UserService from "../services/UserService";
    import ImageUploadService from "@/services/ImageUploadService";
    import moment from 'vue-moment';
    import store from "../store/index";

    @Component
    export default class UserProfile extends Vue {
        private currentUser: any = null;
        dialog = false;
        avatarDialog = false;
        userPic = null;
        valid =  false;
        editedItem: any = {
            name: "",
            login: "",
            email: ""
        };
        defaultItem: any = {
            name: "",
            login: "",
            email: ""
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
        fileRules: any = [
            (v: any) => !!v || 'File required'
        ]
        private message: any = "";
        private errorDialog = false;

        ok(){
            this.message = "";
            this.errorDialog = false;
        }
        editItem() {
            this.editedItem.name = this.currentUser.name;
            this.editedItem.email = this.currentUser.email;
            this.editedItem.login = this.currentUser.login;
            this.dialog = true;
        }
        close() {
            this.dialog = false;
            this.$nextTick(() => {
                this.editedItem = Object.assign({}, this.defaultItem);
            });
        }
        closeAvatarDialog(){
            this.avatarDialog = false;
            this.$nextTick(() => {
                this.userPic = null;
            });
        }
        validate(): boolean {
            return (this.$refs.form as Vue & { validate: () => boolean }).validate();
        }
        save(){
            if(!this.validate()){
                return;
            }
                const data: any = {};
                if (this.editedItem.name !== this.currentUser.name){
                    data.name = this.editedItem.name;
                }
                if(this.editedItem.login !== this.currentUser.login){
                    data.login = this.editedItem.login;
                }
                if(this.editedItem.email !== this.currentUser.email){
                    data.email = this.editedItem.email;
                }
                if(data == null){
                    this.message = "No data has been changed.";
                    this.errorDialog = true;
                    return;
                }
                UserService.update(this.$store.state.id, data)
                    .then(response => {
                        this.currentUser.name = response.data.name;
                        this.currentUser.login = response.data.login;
                        localStorage.setItem("user-name", response.data.login);
                        this.currentUser.email = response.data.email;
                        console.log(response.data);
                        this.close();
                    })
                    .catch((error) => {
                        if (error.response) {
                            this.message = error.response.data.message;
                            this.errorDialog = true;
                        }
                    });
        }
        changePhoto(){
            if(this.userPic == null){
                this.message = "No file selected";
                this.errorDialog = true;
                return;
            }
            ImageUploadService.upload(this.userPic)
                .then(response => {
                    const data = {
                        userPic: response.data.url
                    };
                    UserService.update(this.$store.state.id, data)
                        .then(response => {
                            this.currentUser.userPic = response.data.userPic;
                            console.log(response.data);
                        })
                .catch((error) => {
                        if (error.response) {
                            this.message = error.response.data.message;
                            this.errorDialog = true;
                        }
                    });
                    })
                .catch((error) => {
                    if (error.response) {
                        this.message = "Upload image error, please try again.";
                        this.errorDialog = true;
                    }
                });
            this.closeAvatarDialog();
        }

        changeAvatar(){
            this.avatarDialog = true;
        }
        getUser(id: string) {
            UserService.get(id)
                .then(response => {
                    this.currentUser = response.data;
                    console.log(response.data);
                })
                .catch((error) => {
                    if (error.response) {
                        this.message = error.response.data.message;
                        this.errorDialog = true;
                    }
                });
        }

        mounted() {
            this.getUser(store.getters.getId);
        }
    }
</script>

<style scoped>

</style>