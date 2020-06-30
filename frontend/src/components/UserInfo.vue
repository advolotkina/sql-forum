<template>
  <div>
    {{ currentUser.name }}
    {{ currentUser.email }}
    {{ currentUser.registration_date }}
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import UserService from "../services/UserService";
@Component
export default class Topic extends Vue {
  private currentUser: any = null;
  private message = "";

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
    this.message = "";
    this.getUser(this.$route.params.id);
  }
}
</script>

<style scoped></style>
