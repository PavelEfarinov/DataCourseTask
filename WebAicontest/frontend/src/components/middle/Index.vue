<template>
  <div>
    There are your competitions, fucker!
    <div>
      <ol v-for="item in competitions" :key="item.id">
        <a href="#page=Competition" @click="openCompetitionPage(item)">{{ item.name }}</a>
        {{item.description}}
      </ol>
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