<template>
  <div>
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
              <v-img v-if="currentUser.userPic !== ''" :src="currentUser.userPic" ></v-img>
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
import { Component, Vue } from "vue-property-decorator";
import UserService from "../services/UserService";
@Component
export default class Topic extends Vue {
  private currentUser: any = null;

  private message: any = "";
  private errorDialog = false;

  ok(){
    this.message = "";
    this.errorDialog = false;
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
    this.message = "";
    this.getUser(this.$route.params.id);
  }
}
</script>

<style scoped></style>
