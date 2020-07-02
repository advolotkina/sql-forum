<template>
  <div id="data-app">
  <v-data-table
    :headers="this.headers"
    :items="this.themes"
    sort-by="name"
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar flat color="white">
        <v-toolbar-title>Read and edit themes</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.name"
                      label="Theme name"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="save">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon small class="mr-2" @click="editItem(item)">
        mdi-pencil
      </v-icon>
<!--      <v-icon small @click="deleteItem(item)">-->
<!--        mdi-delete-->
<!--      </v-icon>-->
    </template>
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize">Reset</v-btn>
    </template>
  </v-data-table>
      <v-btn color="primary" to="/add_theme">New Theme</v-btn>
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

@Component
export default class Themes extends Vue {
  private message: any = "";
  private errorDialog = false;
  private headers: any[] = [
    { text: "Comments count", value: "comments_count" },
    { text: "id", value: "id" },
    { text: "Last comment", value: "last_comment" },
    { text: "Theme name", value: "name" },
    { text: "Action", value: "actions" , sortable: false}
  ];
  dialog = false;
  editedIndex = -1;
  editedItem: any = {
    name: ""
  };
  defaultItem: any = {
    name: ""
  };
  ok(){
    this.message = "";
    this.errorDialog = false;
  }
  computed: any = {
    formTitle() {
      return this.editedIndex === -1 ? "New Item" : "Edit Item";
    }
  };
  watch: any = {
    dialog(val) {
      val || this.close();
    }
  };

  private themes: any[] = [];
  private theme: any = {
    id: null
  };

  editItem(item) {
    this.editedIndex = this.themes.indexOf(item);
    this.editedItem = Object.assign({}, item);
    this.dialog = true;
  }

  deleteItem(item) {
    const index = this.themes.indexOf(item);
    if (confirm("Are you sure you want to delete this item?")){
      console.log(item.id);
      ThemeDataService.delete(item.id)
              .then((response) => {
                console.log(response.data);
                this.themes.splice(index, 1);
              })
              .catch((e) => {
                console.log(e);
              });
    }
  }

  close() {
    this.dialog = false;
    this.$nextTick(() => {
      this.editedItem = Object.assign({}, this.defaultItem);
      this.editedIndex = -1;
    });
  }
  save() {
    const data = {
      name: this.editedItem.name,
      groupId: 0
    };
    if (this.editedIndex > -1) {
      ThemeDataService.update(this.editedItem.id, data)
              .then(response => {
                console.log(response.data);
                this.retrieveThemes();
              })
              .catch((error) => {
                if (error.response) {
                  this.message = error.response.data.message;
                  this.errorDialog = true;
                }
              });
    }

    this.close();
  }

  retrieveThemes() {
    ThemeDataService.getAll()
      .then(response => {
        this.themes = response.data;
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  mounted() {
    this.retrieveThemes();
  }
}
</script>

<style scoped></style>
