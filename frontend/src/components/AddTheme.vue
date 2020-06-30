<template>
  <div class="submit-form">
    <div v-if="!submitted">
      <div class="form-group">
        <label for="name">Name</label>
        <input
          type="text"
          class="form-control"
          id="name"
          required
          v-model="theme.name"
          name="title"
        />
        <select v-model="groupId">
          <option
            v-for="(currGroup, index) in groups"
            :key="index"
            :value="currGroup.id"
          >
            {{ currGroup.name }}
          </option>
        </select>
      </div>

      <button @click="saveTheme" class="btn btn-success">Submit</button>
    </div>

    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" @click="newTheme">Add</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import ThemeDataService from "../services/ThemeDataService";

@Component
export default class AddTheme extends Vue {
  private groups: any[] = [];
  private theme: any = {
    id: null,
    name: "",
    published: false
  };
  private groupId = 0;
  private submitted = false;

  saveTheme() {
    const data = {
      name: this.theme.name,
      groupId: this.groupId
    };

    ThemeDataService.create(data)
      .then(response => {
        console.log(response.data);
        this.submitted = true;
      })
      .catch(e => {
        console.log(e);
      });
  }

  newTheme() {
    this.submitted = false;
    this.theme = {};
  }

  loadGroups() {
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
    this.loadGroups();
  }
}
</script>

<style scoped>
.submit-form {
  max-width: 300px;
  margin: auto;
}
</style>
