<template>
  <div class="mx-2">
    <h4>Topic name: {{ currentTopic.name }}</h4>
    <v-simple-table style="width: 100%">
      <colgroup>
        <col span="1" style="width: 15%;">
        <col span="1" style="width: 80%;">
        <col span="1" style="width: 5%;">
      </colgroup>
      <thead>
      <tr>
        <th>Topic creator</th>
        <th>Description</th>
<!--        <th>Date</th>-->
        <th class="text-left" v-if="$store.getters.isAdmin">Change or delete</th>
      </tr>
      </thead>
      <tbody>
        <tr>
          <td>
            <v-card     class="mx-auto"
                        max-width="344">
              <router-link
                      :to="{ path: '/users/' + currentTopic.topicAuthor.id }"
              >{{ currentTopic.topicAuthor.name }}
              </router-link>
              <v-spacer></v-spacer>
              <v-avatar
                      class="profile"
                      color="grey"
                      size="70"
                      tile
              >
                <v-img v-if="currentTopic.topicAuthor.user_pic !== ''" :src="currentTopic.topicAuthor.user_pic" ></v-img>
                <v-img v-else src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQXrIBnP4Ppijsc0z3IRH6A6mQEs0OSaVlmFw&usqp=CAU" ></v-img>
              </v-avatar>
            </v-card>

          </td>
          <td align="left">
            <v-card>
              <v-card-title>{{ currentTopic.description }}</v-card-title>
              <v-spacer></v-spacer>
              <v-card-subtitle>{{ currentTopic.creation_datetime | moment("from")}}</v-card-subtitle>
            </v-card>
            </td>
<!--          <td>{{ currentTopic.creation_datetime | moment("from")}}</td>-->
          <td v-if="$store.getters.isAdmin">
            <v-icon small class="mr-2" @click="editItem(currentTopic)">
              mdi-pencil
            </v-icon>
                  <v-icon small @click="deleteItem(currentTopic)">
                    mdi-delete
                  </v-icon>
          </td>
        </tr>
      </tbody>
    </v-simple-table>
    <v-simple-table style="width: 100%">
      <colgroup>
        <col span="1" style="width: 15%;">
        <col span="1" style="width: 80%;">
        <col span="1" style="width: 5%;">
      </colgroup>
      <thead>
      <tr>
        <th>Author</th>
        <th>Comment</th>
<!--        <th>Date</th>-->
        <th class="text-left" v-if="$store.getters.isAdmin">Change or delete</th>
      </tr>
      </thead>
      <tbody>
        <tr v-for="(comment, i) in currentTopic.comments" :key="i">
          <td>
            <v-card     class="mx-auto"
                        max-width="344">
              <router-link :to="{ path: '/users/' + comment.commentAuthor.id }">
                {{ comment.commentAuthor.name }}
              </router-link>
              <v-spacer></v-spacer>
              <v-avatar
                      class="profile"
                      color="grey"
                      size="70"
                      tile
              >
                <v-img v-if="comment.commentAuthor.user_pic !== ''" :src="comment.commentAuthor.user_pic" ></v-img>
                <v-img v-else src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQXrIBnP4Ppijsc0z3IRH6A6mQEs0OSaVlmFw&usqp=CAU" ></v-img>
              </v-avatar>
            </v-card>
          </td>
<!--          <td>{{ comment.comment_text }}</td>-->
          <td align="left">
            <v-card >
            <v-card-text class="text--primary">{{ comment.comment_text }}</v-card-text>
            <v-spacer></v-spacer>
            <v-card-subtitle>{{ comment.datetime | moment("from")}}</v-card-subtitle>
          </v-card>
          </td>
<!--          <td>{{ comment.datetime | moment("from")}}</td>-->
          <td v-if="$store.getters.isAdmin">
            <v-icon small class="mr-2" @click="editComment(i)">
              mdi-pencil
            </v-icon>
            <v-icon small @click="deleteComment(i)">
              mdi-delete
            </v-icon>
          </td>
        </tr>
      </tbody>
    </v-simple-table>
    <div class="form-group" v-if="$store.getters.isAuthenticated">
      <v-form ref="form"
              v-model="valid"
              lazy-validation
              max-width="500px">
        <v-text-field
                placeholder="Leave a comment"
                value=""
                outlined
                :rules="[rules.required]"
                v-model="comment.text"
                @keyup.enter="submitComment"
        ></v-text-field>
<!--      <input-->
<!--        type="text"-->
<!--        v-model="comment.text"-->
<!--        class="form-control"-->
<!--        placeholder="Leave a comment..."-->
<!--        maxlength="250"-->
<!--        required-->
<!--        @keyup.enter="submitComment"-->
<!--      />-->
      <v-btn @click="submitComment">Send </v-btn>
      </v-form>
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
import { Component, Vue } from "vue-property-decorator";
import TopicService from "../services/TopicService";
import CommentsService from "@/services/CommentsService";
import moment from "vue-moment";

@Component
export default class Topic extends Vue {
  private currentTopic: any = null;
  private message: any = "";
  private errorDialog = false;
  private comment: any = {
    id: null,
    text: "",
    username: ""
  };
  valid = false;
  rules: any =  {
    required: value => !!value || 'Required.'
  }

  ok(){
    this.message = "";
    this.errorDialog = false;
  }
  validate(): boolean {
    return (this.$refs.form as Vue & { validate: () => boolean }).validate();
  }
  editComment(i: number){
    console.log(i);
  }
  deleteComment(i: number){
    console.log(i);
  }
  submitComment() {
    if(!this.validate()){
      return;
    }
    const data = {
      commentText: this.comment.text,
      userName: localStorage.getItem("user-name")
    };
    CommentsService.create(data, this.$route.path)
      .then(response => {
        console.log(response.data);
        this.comment.text = "";
        this.getTopic(this.$route.path);
      })
            .catch((error) => {
              if (error.response) {
                this.message = error.response.data.message;
                this.errorDialog = true;
              }
            });
  }
  getTopic(id: string) {
    TopicService.get(id)
      .then(response => {
        this.currentTopic = response.data;
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
    this.getTopic(this.$route.path);
  }
}
</script>

<style scoped>
  v-simple-table {
    width: 100%;
    border: 1px solid #000;
  }

</style>
