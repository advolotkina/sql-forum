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
        <v-toolbar-title>Add and edit themes</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on"
              >New Item</v-btn
            >
          </template>
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
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import ThemeDataService from "../services/ThemeDataService";
import TopicService from "@/services/TopicService";

@Component
export default class Themes extends Vue {
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
  created() {
    this.retrieveThemes();
    console.log(this.themes);
  }

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
      console.log(item.id)
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
      groupId: 2,
    };
    if (this.editedIndex > -1) {
      ThemeDataService.update(this.editedItem.id, data)
              .then(response => {
                console.log(response.data);
                Object.assign(this.themes[this.editedIndex], this.editedItem);
              })
              .catch(e => {
                console.log(e);
              });
    } else {
      
      ThemeDataService.create(data)
              .then(response => {
                console.log(response.data);
              })
              .catch(e => {
                console.log(e);
              });
      this.themes.push(this.editedItem);
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
