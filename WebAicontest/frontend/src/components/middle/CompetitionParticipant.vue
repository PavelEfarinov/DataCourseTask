<template>
  <div>
    <h1>
      {{ competition.name }}
    </h1>
    <h3>Your rating: {{ participant.ratingElo.rating }}</h3>
    <h3>Placing: {{ competitiveResult.position }} of {{ competitiveResult.allParticipants }}</h3>
    <a href="#page=UploadSolution" @click="changePage('UploadSolution')">Upload solution</a>
    <div class="datatable">
      <div class="caption">Submitted solutions:</div>
      <table>
        <thead>
        <tr>
          <th>File Name</th>
          <th>Language</th>
          <th>Submission Time</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="solution in solutions" :key="solution.id">
          <td>{{ solution.fileLocation }}</td>
          <td>{{ solution.solutionCompilerLanguage }}</td>
          <td>{{ solution.creationTime }}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <a href="#page=CreateSandboxMatch" @click="changePage('CreateSandboxMatch')">Create new sandbox match</a>
    <div class="datatable">
      <div class="caption">Your matches:</div>
      <table>
        <thead>
        <tr>
          <th>Winner</th>
          <th>Match type</th>
          <th>Participants</th>
          <th>Start time</th>
          <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="match in matches" :key="match.id">
          <td v-if="match.matchResult.winnerParticipant">{{ match.matchResult.winnerParticipant.baseUserLogin }}</td>
          <td v-else>There is no winner, waiting for the match to finish</td>
          <td v-if="match.sandboxMatch">Sandbox match</td>
          <td v-else>Rating match</td>
          <td>
            <p v-for="userResult in match.matchResult.userResults" :key="userResult.id">
              {{ userResult.participant.baseUserLogin }} | {{ userResult.result }}
            </p>
          </td>
          <td>{{ match.startTime }}</td>
          <td>{{ match.matchStatus }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <h3>Created requests:</h3>
    <article v-for="request in requests" :key="request.id">
      <div class="title">
        {{ request.theme }}
      </div>
      <div class="information">
        Status: <b>{{ request.requestStatus }}</b>
      </div>
      <div class="body">
        {{ request.description }}
      </div>
    </article>
    <br/>
    <a href="#page=CreateCompetitionRequest" @click="changePage('CreateCompetitionRequest')">
      <button>Create new request</button>
    </a>
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
      solutions: [],
      matches: [],
      competitiveResult: null,
    };
  },
  methods: {
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
    axios.get("/api/1/solution/" + this.participant.id + "/all", {}).then(response => {
      this.solutions = response.data;
    });
    axios.get("/api/1/match/participant/" + this.participant.id + "/all-matches", {}).then(response => {
      this.matches = response.data;
    });
  }
}
</script>

<style scoped>

</style>