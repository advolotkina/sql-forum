<template>
  <div class="submit-form">
    <div v-if="!submitted">
      <div class="form-group">
        <v-form ref="form"
                v-model="valid"
                lazy-validation
                max-width="500px">
          <v-text-field
                  filled label="Theme name"
                  value=""
                  :rules="[rules.required]"
                  v-model="theme.name"
                  :counter="255"
          ></v-text-field>
          <v-select v-model="groupId"
                    :rules="[rules.required]"
                    :items="groups"
                    item-text="name"
                    item-value="id">
<!--            <option-->
<!--                    v-for="(currGroup, index) in groups"-->
<!--                    :key="index"-->
<!--                    :value="currGroup.id"-->
<!--            >-->
<!--              {{ currGroup.name }}-->
<!--            </option>-->
          </v-select>
<!--          <select v-model="groupId">-->
<!--          <option-->
<!--            v-for="(currGroup, index) in groups"-->
<!--            :key="index"-->
<!--            :value="currGroup.id"-->
<!--          >-->
<!--            {{ currGroup.name }}-->
<!--          </option>-->
<!--        </select>-->
        </v-form>
      </div>

      <button @click="saveTheme" class="btn btn-success">Submit</button>
    </div>

    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" @click="newTheme">Add</button>
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

@Component
export default class AddTheme extends Vue {
  private groups: any[] = [];
  private message: any = "";
  private errorDialog = false;
  private groupId = 1;
  private theme: any = {
    id: null,
    name: "",
    published: false
  };
  private submitted = false;
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

  saveTheme() {
    if(!this.validate()){
      return;
    }
    const data = {
      name: this.theme.name,
      groupId: this.groupId
    };

    ThemeDataService.create(data)
      .then(response => {
        console.log(response.data);
        this.submitted = true;
      })
        .catch((error) => {
            if (error.response) {
                this.message = error.response.data.message;
                this.errorDialog = true;
            }
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
