<template>
  <div>
    <table class="table table-striped">
      <thead>
        <th>Тема</th>
        <th>Автор</th>
        <th>Ответов</th>
        <th>Просмотров</th>
        <th>Дата</th>
      </thead>
      <tbody>
        <tr v-for="(topic, i) in currentTheme.topics" :key="i">
          <td>
            <router-link :to="{ path: currPath + '/topics/' + topic.id }">{{
              topic.name
            }}</router-link>
          </td>
          <td>
            <router-link :to="{ path: '/users/' + topic.topicAuthor.id }">
              {{ topic.topicAuthor.name }}
            </router-link>
          </td>
          <td>{{ topic.comments_count }}</td>
          <td>{{ topic.views }}</td>
          <td>{{ topic.last_comment }}</td>
        </tr>
      </tbody>
    </table>
<!--    <div class="form-group" v-if="this.$store.getters.isAuthenticated">-->
<!--      <label for="name">Topic Name</label>-->
<!--      <input-->
<!--        type="text"-->
<!--        v-model="topic.name"-->
<!--        class="form-control"-->
<!--        id="name"-->
<!--        placeholder="Topic name..."-->
<!--        maxlength="250"-->
<!--        required-->
<!--      />-->
<!--      <label for="description">Topic Description</label>-->
<!--      <input-->
<!--        type="text"-->
<!--        v-model="topic.description"-->
<!--        class="form-control"-->
<!--        id="description"-->
<!--        placeholder="Topic description..."-->
<!--        maxlength="250"-->
<!--        required-->
<!--      />-->
<!--      <b-button variant="success" @click="createTopic">Create Topic </b-button>-->
<!--    </div>-->
    <div v-if="this.$store.getters.isAuthenticated">
    <v-card>
      <v-toolbar
              flat
              color="blue-grey"
              dark
      >
        <v-toolbar-title>New Topic</v-toolbar-title>
      </v-toolbar>
      <v-card-text>
        <v-text-field
                filled label="Title"
                value=""
                required
                v-model="topic.name"
                :counter="255"
        ></v-text-field>

        <v-textarea
                filled
                v-model = "topic.description"
                label="Text"
                required
                value=""
        ></v-textarea>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
                color="success"
                depressed
                @click="createTopic"
        >
          Create Topic
        </v-btn>
      </v-card-actions>
    </v-card>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import ThemeDataService from "../services/ThemeDataService";
import TopicService from "@/services/TopicService";
import { validationMixin } from 'vuelidate'
import { required, maxLength, email } from 'vuelidate/lib/validators'

@Component
export default class ThemeInfo extends Vue {
  private currentTheme: any = null;
  private message = "";
  private currPath = "";

  private topic: any = {
    id: null,
    name: "",
    description: ""
  };

  createTopic() {
    const data = {
      name: this.topic.name,
      description: this.topic.description,
      userName: localStorage.getItem("user-name")
    };
    TopicService.create(data, this.currPath)
      .then(response => {
        console.log(response.data);
        this.topic.name = "";
        this.topic.description = "";
        this.getTheme(this.$route.params.id);
      })
      .catch(e => {
        console.log(e);
      });
  }
  getTheme(id: string) {
    ThemeDataService.get(id)
      .then(response => {
        this.currentTheme = response.data;
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  mounted() {
    this.message = "";
    this.currPath = this.$route.path;
    console.log(this.$route.params.id);
    this.getTheme(this.$route.params.id);
  }
}
</script>

<style scoped></style>
