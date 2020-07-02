<template>
  <div id="app" class="mx-2">
    <v-app id="inspire">
    <v-card>
      <v-toolbar
              color="teal"
              dark
      >
        <v-toolbar-title>Themes</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>

      <v-list>
        <v-list-group
                v-for="group in groups"
                :key="group.name"
                no-action
        >
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title v-text="group.name"></v-list-item-title>
            </v-list-item-content>
          </template>

          <v-list-item
                  v-for="theme in group.themes"
                  :key="theme.name"
                  :to="{ path: 'themes/' + theme.id }"
          >
            <v-list-item-content>
              <v-list-item-title v-text="theme.name"></v-list-item-title>
              <v-list-item-subtitle>
                Comments count: {{theme.comments_count}}
              </v-list-item-subtitle>
              <v-list-item-subtitle>
                Last comment: {{theme.last_comment | moment("from")}}
              </v-list-item-subtitle>
<!--              <v-card>-->
<!--                <v-card-title v-text="theme.name"></v-card-title>-->
<!--                <v-card-text>-->
<!--                  <p>Comments count: {{theme.comments_count}}</p>-->
<!--                  <p>Last comment: {{theme.last_comment}}</p>-->
<!--                </v-card-text>-->
<!--              </v-card>-->
<!--              <v-list-item-title v-text="theme.name"></v-list-item-title>-->
<!--              <v-list-item-subtitle v-text="theme.comments_count">-->
<!--                fff</v-list-item-subtitle>-->
<!--              <v-list-item-subtitle v-text="theme.last_comment"></v-list-item-subtitle>-->
            </v-list-item-content>
          </v-list-item>
        </v-list-group>
      </v-list>
    </v-card>
    </v-app>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import ThemeDataService from "../services/ThemeDataService";
import moment from "vue-moment";

@Component
export default class Groups extends Vue {
  private groups: any[] = [];

  retrieveGroups() {
    ThemeDataService.getGroups()
      .then(response => {
        this.groups = response.data;
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  mounted() {
    this.retrieveGroups();
  }
}
</script>

<style scoped></style>
