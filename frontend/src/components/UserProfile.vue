<template>
    <v-container>
        <v-row dense>
            <v-col cols="12">
                <v-card class="mx-auto"
                        max-width="500">
                <v-avatar
                        class="profile"
                        color="grey"
                        size="164"
                        tile
                >
                    <v-img v-if="currentUser.user_pic !== ''" v-model="currentUser.user_pic" ></v-img>
                    <v-img v-else src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQXrIBnP4Ppijsc0z3IRH6A6mQEs0OSaVlmFw&usqp=CAU" ></v-img>
                </v-avatar>
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
                            {{currentUser.registration_date}}
                        </p>
                        <p>Registration Date</p>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
        <v-btn :to="{ path: '/change_profile'}" color="primary">Change</v-btn>
    </v-container>
</template>

<script lang="ts">
    import { Component, Vue } from "vue-property-decorator";
    import UserService from "../services/UserService";
    import store from "../store/index";

    @Component
    export default class UserProfile extends Vue {
        private currentUser: any = null;

        getUser(id: string) {
            UserService.get(id)
                .then(response => {
                    this.currentUser = response.data;
                    console.log(response.data);
                })
                .catch(e => {
                    console.log(e);
                });
        }

        mounted() {
            this.getUser(store.getters.getId);
        }
    }
</script>

<style scoped>

</style>