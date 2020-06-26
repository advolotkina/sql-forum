<template>
    <div class="col-md-6">
        <h4>Themes</h4>
        <ul class="list-group">
            <li
                    class="list-group-item"
                    v-for="(tutorial, index) in themes"
                    :key="index"
            >
                <p>{{ tutorial.name }}</p>
                <p>{{ tutorial.comments_count }}</p>
                <p>{{ tutorial.last_comment }}</p>
            </li>
        </ul>
    </div>
</template>

<script lang="ts">
    import { Component, Vue } from "vue-property-decorator";
    import ThemeDataService from "../services/ThemeDataService";

    @Component
    export default class ThemesList extends Vue {
        private themes: any[] = [];

        retrieveTutorials() {
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
            this.retrieveTutorials();
        }
    }
</script>

<style scoped>

</style>