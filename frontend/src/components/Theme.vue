<!--<template>-->
<!--    <div class="body">-->
<!--&lt;!&ndash;    <div class="col-md-6">&ndash;&gt;-->
<!--&lt;!&ndash;        <h4>Themes</h4>&ndash;&gt;-->
<!--&lt;!&ndash;        <ul class="list-group">&ndash;&gt;-->
<!--&lt;!&ndash;            <li&ndash;&gt;-->
<!--&lt;!&ndash;                    class="list-group-item"&ndash;&gt;-->
<!--&lt;!&ndash;                    v-for="(theme, index) in themes"&ndash;&gt;-->
<!--&lt;!&ndash;                    :key="index"&ndash;&gt;-->
<!--&lt;!&ndash;            >&ndash;&gt;-->
<!--&lt;!&ndash;                <p hidden>{{ theme.id }}</p>&ndash;&gt;-->
<!--&lt;!&ndash;                <p>{{ theme.name }}</p>&ndash;&gt;-->
<!--&lt;!&ndash;                <b-button variant="success" @click="deleteTheme">Delete Theme&ndash;&gt;-->
<!--&lt;!&ndash;                </b-button>&ndash;&gt;-->
<!--&lt;!&ndash;            </li>&ndash;&gt;-->
<!--&lt;!&ndash;        </ul>&ndash;&gt;-->
<!--&lt;!&ndash;    </div>&ndash;&gt;-->
<!--        <v-layout-->
<!--                column-->
<!--                justify-center-->
<!--                align-center-->
<!--        >-->
<!--            <v-data-table-->
<!--                    :headers="headers"-->
<!--                    :items="themes"-->
<!--                    class="elevation-0"-->
<!--            >-->
<!--                <template slot="items" slot-scope="props">-->
<!--                    <tr>-->
<!--                        <td>{{ props.item.name }}</td>-->
<!--                        <td>{{ props.item.comments_count }}</td>-->
<!--                        <td>-->
<!--                            <v-btn color="error">-->
<!--                                DELETE-->
<!--                            </v-btn>-->
<!--                            <v-btn color="primary">-->
<!--                                EDIT-->
<!--                            </v-btn>-->
<!--                        </td>-->
<!--                    </tr>-->
<!--                </template>-->
<!--            </v-data-table>-->
<!--        </v-layout>-->
<!--    <div class="col-md-6" v-if="this.$store.getters.isAdmin">-->
<!--        <router-link to="/add_theme">-->
<!--            <b-button variant="primary">Add theme</b-button>-->
<!--        </router-link>-->
<!--    </div>-->
<!--    </div>-->
<!--</template>-->

<template>
    <v-data-table
            :headers="this.headers"
            :items="this.themes"
            sort-by="name"
            class="elevation-1"
    >
        <template v-slot:top>
            <v-toolbar flat color="white">
                <v-toolbar-title>My CRUD</v-toolbar-title>
                <v-divider
                        class="mx-4"
                        inset
                        vertical
                ></v-divider>
                <v-spacer></v-spacer>
                <v-dialog v-model="dialog" max-width="500px">
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn
                                color="primary"
                                dark
                                class="mb-2"
                                v-bind="attrs"
                                v-on="on"
                        >New Item</v-btn>
                    </template>
                    <v-card>
                        <v-card-title>
                            <span class="headline">{{ formTitle }}</span>
                        </v-card-title>

                        <v-card-text>
                            <v-container>
                                <v-row>
                                    <v-col cols="12" sm="6" md="4">
                                        <v-text-field v-model="editedItem.name" label="Dessert name"></v-text-field>
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
            <v-icon
                    small
                    class="mr-2"
                    @click="editItem(item)"
            >
                mdi-pencil
            </v-icon>
            <v-icon
                    small
                    @click="deleteItem(item)"
            >
                mdi-delete
            </v-icon>
        </template>
        <template v-slot:no-data>
            <v-btn color="primary" @click="initialize">Reset</v-btn>
        </template>
    </v-data-table>
</template>

<script lang="ts">
    import { Component, Vue } from "vue-property-decorator";
    import ThemeDataService from "../services/ThemeDataService";

    @Component
    export default class Themes extends Vue {
        private headers: any[] = [
            {text: 'Comments count', value: "comments_count" },
            { text: 'id',value: "id"},
            {text: 'Last comment', value: "last_comment"},
            {text: 'Theme Admins', value: "themeAdmins"},
            {text: 'Theme name',value: "name"},
            {text: 'Topics', value: "topics"},
            {text: 'Url', value: "url"},
            { text: 'Action', value: 'action' }
        ]
        dialog = false;
        editedIndex = -1;
        editedItem: any = {
            name: ''
        }
        defaultItem: any = {
            name: ''
        }

        computed: any = {
            formTitle () {
            return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
            }
        }
        watch: any = {
        dialog (val) {
            val || this.close()
            }
        }
        created () {
            this.retrieveThemes()
            console.log(this.themes)
        }

        private themes: any[] = [];
        private theme: any = {
            id: null
        };

        editItem (item) {
            this.editedIndex = this.themes.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialog = true
        }

        deleteItem (item) {
            const index = this.themes.indexOf(item)
            confirm('Are you sure you want to delete this item?') && this.themes.splice(index, 1)
        }

        close () {
            this.dialog = false
            this.$nextTick(() => {
                this.editedItem = Object.assign({}, this.defaultItem)
                this.editedIndex = -1
            })
        }
        save () {
            if (this.editedIndex > -1) {
                Object.assign(this.themes[this.editedIndex], this.editedItem)
            } else {
                this.themes.push(this.editedItem)
            }
            this.close()
        }
        // deleteTheme(){
        //     ThemeDataService.delete(this.theme.id)
        //         .then((response) => {
        //             this.theme.id = null;
        //             console.log(response.data);
        //             this.retrieveThemes();
        //         })
        //         .catch((e) => {
        //             console.log(e);
        //         });
        // }
        retrieveThemes() {
            ThemeDataService.getAll()
                .then((response) => {
                    this.themes = response.data;
                    console.log(response.data);
                })
                .catch((e) => {
                    console.log(e);
                });
        }

        mounted() {
            this.retrieveThemes();
        }
    }
</script>

<style scoped>

</style>