<template>
  <div>
    <h1>
      {{ competition.name }}
    </h1>
    <h3>Your rating: {{participant.ratingElo.rating}}</h3>
    <h3>Placing: {{competitiveResult.position}} of {{competitiveResult.allParticipants}}</h3>
    <h3>Created requests:</h3>
    <article v-for="request in requests" :key="request.id">
      <div class="title">
        {{request.theme}}
      </div>
      <div class="information">
        Status: <b>{{request.requestStatus}}</b>
      </div>
      <div class="body">
        {{ request.description }}
      </div>
    </article>
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
      competitiveResult: null,
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
    axios.get("/api/1/participant/" + this.participant.id + "/competitive-result", {}).then(response => {
      this.competitiveResult = response.data;
    });
  }
}
</script>

<style scoped>

</style>