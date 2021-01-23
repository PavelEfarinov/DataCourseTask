<template>
  <div class="upload form-box">
    <div class="header">Create new match</div>
    <div class="body">
      <form @submit.prevent="onSubmitMatch">
        <div class="line">
          Choose your solution:
          <select v-model="solution">
            <option v-for="sol in solutions" :key="sol.id" v-bind:value="sol.id" @select="setSolution(sol)">
              {{ sol.solutionCompilerLanguage }} |
              {{ sol.fileLocation }} | {{ sol.creationTime }}
            </option>
          </select>
        </div>
        <div class="line">
          Choose your opponent:
          <select v-model="competitor">
            <option v-for="competitorItem in competitors" :key="competitorItem.id" v-bind:value="competitorItem.id">
              {{ competitorItem.baseUserLogin }} | {{competitorItem.ratingElo.rating}}
            </option>
          </select>
        </div>
        <div class="button-field">
          <input type="submit" value="Create match">
        </div>
        <div class="error">{{ error }}</div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CreateSandboxMatch",
  props: ['participant', 'competition'],
  data: function () {
    return {
      solutions: [],
      solution: '',
      maps: [],
      map: '',
      competitors: [],
      competitor: [],
      competitorSolutions: [],
      competitorSolution: '',
      error: '',
    }
  },
  methods:
      {
        onSubmitMatch: function () {
          alert(this.solution);
        },
        setSolution: function (solution) {
          this.solution = solution;
        }
      },
  beforeMount() {
    axios.get("/api/1/solution/" + this.participant.id + "/all", {}).then(response => {
      this.solutions = response.data;
    });
    axios.get("/api/1/participant/competition/" + this.competition.id + "/all-participants", {}).then(response => {
      this.competitors = response.data;
    });
  }
}
</script>

<style scoped>

</style>