<template>
  <div>
    <h1>
      {{ competition.name }}
    </h1>
    <h3>Your rating: {{participant.ratings.rating}}</h3>
    <h3>Created requests:</h3>
    <div v-for="request in requests" :key="request.id">
      <h4>{{ request.theme + "(" + request.requestStatus + ")" }}</h4>
      Description: {{ request.description }}
    </div>
    <br/>
    <a href="#page=CreateCompetitionRequest" @click="changePage('CreateCompetitionRequest')"><button>Create new request</button></a>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CompetitionParticipant",
  props: ["participant", "competition"],
  data: function () {
    return {
      requests: [],
    };
  },
  methods:{
    changePage: function (page) {
      this.$root.$emit("onChangePage", page);
    },
  },
  beforeMount() {
    axios.get("/api/1/request/" + this.participant.id + "/created", {}).then(response => {
      this.requests = response.data;
    });
  }
}
</script>

<style scoped>

</style>