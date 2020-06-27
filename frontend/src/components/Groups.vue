<template>
    <div>
        <table class="table table-striped">
            <thead>
            <th>Группа</th>
            <th>Сообщений всего</th>
            <th>Последнее сообщение</th>
            <th>Модераторы</th>
            </thead>
            <tbody>
            </tbody>
        </table>
        <table class="table table-striped" v-for="(group, i) in groups" :key="i">
            <tbody>
            <tr>
                <td colspan="4" align="left">{{ group.name}}</td>
            </tr>
            <tr v-for="(theme,i) in group.themes" :key="i">
                <td><router-link :to="{ path: 'themes/' + theme.id }">{{theme.name}}</router-link></td>
                <td>{{theme.comments_count}}</td>
                <td>{{theme.last_comment}}</td>
                <td>{{theme.themeAdmins}}</td>
            </tr>
            </tbody>
        </table>
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
                .then((response) => {
                    this.groups = response.data;
                    console.log(response.data);
                })
                .catch((e) => {
                    console.log(e);
                });
        }

        mounted() {
            this.retrieveGroups();
        }
    }
</script>

<style scoped>

</style>