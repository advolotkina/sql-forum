<template>
<!--  <div>-->
<!--    <table class="table table-striped">-->
<!--      <thead>-->
<!--        <th>Группа</th>-->
<!--        <th>Сообщений всего</th>-->
<!--        <th>Последнее сообщение</th>-->
<!--        <th>Модераторы</th>-->
<!--      </thead>-->
<!--      <tbody></tbody>-->
<!--    </table>-->
<!--    <table class="table table-striped" v-for="(group, i) in groups" :key="i">-->
<!--      <tbody>-->
<!--        <tr>-->
<!--          <td colspan="4" align="left">{{ group.name }}</td>-->
<!--        </tr>-->
<!--        <tr v-for="(theme, i) in group.themes" :key="i">-->
<!--          <td>-->
<!--            <router-link :to="{ path: 'themes/' + theme.id }">{{-->
<!--              theme.name-->
<!--            }}</router-link>-->
<!--          </td>-->
<!--          <td>{{ theme.comments_count }}</td>-->
<!--          <td>{{ theme.last_comment }}</td>-->
<!--          <td>{{ theme.themeAdmins }}</td>-->
<!--        </tr>-->
<!--      </tbody>-->
<!--    </table>-->
<!--  </div>-->
  <div id="app">
    <v-app id="inspire">
    <v-card
            min-width="1000"
            class="mx-auto"
    >
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
                Last comment: {{theme.last_comment}}
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
