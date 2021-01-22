<template>
  <div>
    <h3>Running competitions:</h3>
    <div class="datatable">
      <table>
        <thead>
        <tr>
          <th>Competition name</th>
          <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in competitions" :key="item.id">
          <td><a href="#page=Competition" @click="openCompetitionPage(item)">{{ item.name }}</a></td>
          <td>{{item.description}}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Index",
  data: function () {
    return {
      competitions: []
    }
  },
  beforeMount() {
    axios.get("/api/1/competition/list", {}).then(response => {
      this.competitions = response.data;
    });
  },
  methods: {
    openCompetitionPage: function (page) {
      this.$root.$emit("onOpenCompetitionPage", page);
    }
  }
}
</script>

<style scoped>

</style>