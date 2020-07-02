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
              <v-spacer></v-spacer>
              <v-card-subtitle v-if="currentTopic.isModified">
                modified by admin {{currentTopic.modificationDate | moment("from")}}
              </v-card-subtitle>
            </v-card>
            </td>
          <td v-if="$store.getters.isAdmin">
            <v-icon small class="mr-2" @click="editTopic">
              mdi-pencil
            </v-icon>
                  <v-icon small @click="deleteTopic">
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
          <td align="left">
            <v-card >
            <v-card-text class="text--primary">{{ comment.commentText }}</v-card-text>
            <v-spacer></v-spacer>
            <v-card-subtitle>{{ comment.datetime | moment("from")}}</v-card-subtitle>
            <v-spacer></v-spacer>
            <v-card-subtitle v-if="comment.isModified">
              modified by admin {{comment.modificationDate | moment("from")}}
            </v-card-subtitle>
          </v-card>
          </td>
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
    <v-dialog v-model="updateComment" max-width="500px">
      <v-card>
        <v-card-text>
          <v-container>
            <v-form           ref="formUpdateComment"
                              v-model="valid"
                              lazy-validation>
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field
                          v-model="editedComment.text"
                          label="Comment text"
                          :rules="[rules.required]"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="updateCommentClose">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="changeComment">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="updateTopic" max-width="500px">
      <v-card>
        <v-card-text>
          <v-container>
            <v-form           ref="formUpdateTopic"
                              v-model="valid"
                              lazy-validation>
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field
                          v-model="editedTopic.text"
                          label="Topic text"
                          :rules="[rules.required]"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="updateTopicClose">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="changeTopic">Save</v-btn>
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
import ThemeDataService from "@/services/ThemeDataService";

@Component
export default class Topic extends Vue {
  private currentTopic: any = null;
  private message: any = "";
  private errorDialog = false;
  private updateComment = false;
  private updateTopic = false;
  private editedComment: any = {
    id: null,
    text: ""
  }
  private editedTopic: any = {
    text: ""
  }
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
  validateUpdateComment(): boolean {
    return (this.$refs.formUpdateComment as Vue & { validate: () => boolean }).validate();
  }
  validateUpdateTopic(): boolean {
    return (this.$refs.formUpdateTopic as Vue & { validate: () => boolean }).validate();
  }
  editComment(i: number){
    this.editedComment.id = this.currentTopic.comments[i].id;
    this.editedComment.text = this.currentTopic.comments[i].commentText;
    this.updateComment = true;
  }
  editTopic(){
    this.editedTopic.text = this.currentTopic.description;
    this.updateTopic = true;
  }
  updateCommentClose(){
    this.updateComment = false;
    this.$nextTick(() => {
      this.editedComment.id = "";
      this.editedComment.text = "";
    });
  }
  changeComment(){
    if(!this.validateUpdateComment()){
      return;
    }
    const data = {
      commentText: this.editedComment.text
    };
    const commentId = this.editedComment.id;
    CommentsService.update(commentId, data)
            .then(response => {
              console.log(response.data);
              this.getTopic(this.$route.path);
            })
            .catch((error) => {
              if (error.response) {
                this.message = error.response.data.message;
                this.errorDialog = true;
              }
            });
    this.updateCommentClose();
  }
  changeTopic(){
    if(!this.validateUpdateTopic()){
      return;
    }
    const data = {
      topicDescription: this.editedTopic.text
    };
    TopicService.update(this.$route.path, data)
            .then(response => {
              console.log(response.data);
              this.getTopic(this.$route.path);
            })
            .catch((error) => {
              if (error.response) {
                this.message = error.response.data.message;
                this.errorDialog = true;
              }
            });
    this.updateTopicClose();
  }
  updateTopicClose(){
    this.updateTopic = false;
    this.$nextTick(() => {
      this.editedTopic.text = "";
    });
  }
  deleteComment(i: number){
    const commentId = this.currentTopic.comments[i].id;
    if (confirm("Are you sure you want to delete this comment?")){
      CommentsService.delete(commentId)
              .then((response) => {
                console.log(response.data);
                this.getTopic(this.$route.path);
              })
              .catch((error) => {
                if (error.response) {
                  this.message = error.response.data.message;
                  this.errorDialog = true;
                }
              });
    }
  }
  deleteTopic(){
    if (confirm("Are you sure you want to delete this topic?")){
      TopicService.delete(this.$route.path)
              .then((response) => {
                console.log(response.data);
                this.$router.go(-1);
              })
              .catch((error) => {
                if (error.response) {
                  this.message = error.response.data.message;
                  this.errorDialog = true;
                }
              });
    }
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
