<template>
  <div class="mx-auto">
    <h4>{{ currentTopic.name }}</h4>
    <v-table class="table table-striped">
      <thead>
        <th>Topic creator</th>
        <th>Description</th>
        <th>Date</th>
        <th v-if="$store.getters.isAdmin">Change or delete</th>
      </thead>
      <tbody>
        <tr>
          <td>
            <router-link
              :to="{ path: '/users/' + currentTopic.topicAuthor.id }"
            >
              {{ currentTopic.topicAuthor.name }}
            </router-link>
          </td>
          <td align="left">{{ currentTopic.description }}</td>
          <td>{{ currentTopic.creation_datetime }}</td>
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
    </v-table>
    <table class="table table-striped">
      <thead>
        <th>Author</th>
        <th>Comment</th>
        <th>Date</th>
        <th v-if="$store.getters.isAdmin">Change or delete</th>
      </thead>
      <tbody>
        <tr v-for="(comment, i) in currentTopic.comments" :key="i">
          <td>
            <router-link :to="{ path: '/users/' + comment.commentAuthor.id }">
              {{ comment.commentAuthor.name }}
            </router-link>
          </td>
          <td>{{ comment.comment_text }}</td>
          <td>{{ comment.datetime | moment("from")}}</td>
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
    </table>
    <div class="form-group" v-if="$store.getters.isAuthenticated">
      <input
        type="text"
        v-model="comment.text"
        class="form-control"
        placeholder="Leave a comment..."
        maxlength="250"
        required
        @keyup.enter="submitComment"
      />
      <b-button variant="success" @click="submitComment">Send </b-button>
    </div>
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
  private message = "";
  private comment: any = {
    id: null,
    text: "",
    username: ""
  };
  editComment(i: number){
    console.log(i);
  }
  deleteComment(i: number){
    console.log(i);
  }
  submitComment() {
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
      .catch(e => {
        console.log(e);
      });
  }
  getTopic(id: string) {
    TopicService.get(id)
      .then(response => {
        this.currentTopic = response.data;
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  mounted() {
    this.message = "";
    this.getTopic(this.$route.path);
  }
}
</script>

<style scoped></style>
