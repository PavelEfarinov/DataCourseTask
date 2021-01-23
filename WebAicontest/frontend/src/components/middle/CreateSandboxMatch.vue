<template>
  <div class="upload form-box">
    <div class="header">Create new match</div>
    <div class="body">
      <form @submit.prevent="onSubmitMatch">
        <div class="line">
          <div class="field-name">
            Choose your solution:
          </div>
          <select class="select-css" v-model="solution">
            <option v-for="sol in solutions" :key="sol.id" v-bind:value="sol.id" @select="setSolution(sol)">
              {{ sol.solutionCompilerLanguage }} |
              {{ sol.fileLocation }} | {{ sol.creationTime }}
            </option>
          </select>
        </div>
        <div class="line">
          <div class="field-name">
          Choose your opponent:
          </div>
          <select class="select-css" v-model="competitor">
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
  .line {
    margin: 5%;
    display: grid;
    grid-template-columns: 50% 50%;
  }
.select-css {
  display: block;
  font-size: 16px;
  font-family: sans-serif;
  font-weight: 700;
  color: #444;
  line-height: 1.3;
  padding: .6em 1.4em .5em .8em;
  /*width: 60%;*/
  /*max-width: 60%; !* useful when width is set to anything other than 100% *!*/
  box-sizing: border-box;
  margin: 0;
  border: 1px solid #aaa;
  box-shadow: 0 1px 0 1px rgba(0,0,0,.04);
  border-radius: .5em;
  -moz-appearance: none;
  -webkit-appearance: none;
  appearance: none;
  background-color: #fff;
}
  .field-name {
    margin-top: 5%;
  }
</style>