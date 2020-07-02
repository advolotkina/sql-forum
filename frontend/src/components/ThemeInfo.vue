<template>
  <div class="mx-auto">
    <table class="table table-striped">
      <thead>
        <th>Topic name</th>
        <th>Author</th>
        <th>Comments</th>
        <th>Last comment</th>
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
          <td>{{ topic.last_comment | moment("from")}}</td>
        </tr>
      </tbody>
    </table>
    <div v-if="this.$store.getters.isAuthenticated">
      <v-form ref="form"
              v-model="valid"
              lazy-validation
              max-width="500px">
    <v-card>
      <v-toolbar
      >
        <v-toolbar-title>New Topic</v-toolbar-title>
      </v-toolbar>
      <v-card-text>
        <v-text-field
                filled label="Title"
                value=""
                :rules="[rules.required]"
                v-model="topic.name"
                :counter="255"
        ></v-text-field>

        <v-textarea
                filled
                v-model = "topic.description"
                label="Text"
                :rules="[rules.required, rules.min]"
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
import ThemeDataService from "../services/ThemeDataService";
import TopicService from "@/services/TopicService";
import moment from "vue-moment";

@Component
export default class ThemeInfo extends Vue {
  private currentTheme: any = null;
  private currPath = "";
  private message: any = "";
  private errorDialog = false;

  private topic: any = {
    id: null,
    name: "",
    description: ""
  };

  valid = false;
  rules: any =  {
    required: value => !!value || 'Required.',
    min: v => v.length >= 8 || 'Min 8 characters'
  }

  ok(){
    this.message = "";
    this.errorDialog = false;
  }
  validate(): boolean {
    return (this.$refs.form as Vue & { validate: () => boolean }).validate();
  }

  createTopic() {
    if(!this.validate()){
      return;
    }
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
            .catch((error) => {
              if (error.response) {
                this.message = error.response.data.message;
                this.errorDialog = true;
              }
            });
  }
  getTheme(id: string) {
    ThemeDataService.get(id)
      .then(response => {
        this.currentTheme = response.data;
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
    this.currPath = this.$route.path;
    console.log(this.$route.params.id);
    this.getTheme(this.$route.params.id);
  }
}
</script>

<style scoped></style>
