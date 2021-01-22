<template>
  <div>
    <h1>
      {{ competition.name }}
    </h1>
    {{ competition.description }}<br/>
    <h3>Starts at:</h3> {{ competition.startDate }}
    <h3>Ends at:</h3>
    <div v-if="competition.endDate">{{ competition.endDate }}</div>
    <div v-else>This competition does not have proper end date.</div>
    <br/>
    <div v-if="user">
      <a href="#page=CompetitionParticipant" @click="changePage('CompetitionParticipant')"><button>Take part in the competition</button></a>
      <a href="#page=CompetitionManager" @click="changePage('CompetitionManager')"><button>Manage the competition</button></a>
    </div>
    <div class="error">{{error}}</div>
    <div class="datatable">
      <div class="caption">TOP-10 players</div>
      <table>
        <thead>
        <tr>
          <th>Login</th>
          <th>Rating</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="player in players" :key="player.baseUserLogin">
          <td>{{player.baseUserLogin}}</td>
          <td>{{player.ratingElo.rating}}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <h3>TOP-10 players:</h3>
    <div v-for="player in players" :key="player.baseUserLogin">
      {{ player.baseUserLogin }} - {{ player.ratingElo.rating }}
    </div>
    <h3>Rating rounds:</h3>
    <div v-for="round in rounds" :key="round.id">
      Rating round will occur at: {{ round.startTime }}
    </div>
    <h3>Sponsored by:</h3>
    <div v-for="sponsor in organizers" :key="sponsor.id">
      {{ sponsor.name }}
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Competition",
  props: ["user", "competition"],
  data: function () {
    return {
      organizers: [],
      players: [],
      rounds: [],
      error: null
    };
  },
  methods:{
    changePage: function (page) {
      this.$root.$emit("onChangePage", page);
    },
  },
  beforeMount() {
    axios.get("/api/1/competition/" + this.competition.id + "/sponsors", {}).then(response => {
      this.organizers = response.data;
    });
    axios.get("/api/1/competition/" + this.competition.id + "/top", {}).then(response => {
      this.players = response.data;
    });
    axios.get("/api/1/competition/" + this.competition.id + "/rounds", {}).then(response => {
      this.rounds = response.data;
    });
    this.$root.$on("onEnterCompetitionManagerError", (error) => {
      this.error = error;
    });
  }
}
</script>

<style scoped>

</style>