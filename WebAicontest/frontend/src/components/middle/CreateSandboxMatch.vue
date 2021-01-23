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
          <select class="select-css" v-model="competitor" @change="getCompetitorSolutions(competitor)">
            <option v-for="competitorItem in competitors" :key="competitorItem.id" v-bind:value="competitorItem">
              {{ competitorItem.baseUserLogin }} | {{ competitorItem.ratingElo.rating }}
            </option>
          </select>
        </div>
        <div class="line" v-if="competitor">
          <div class="field-name">
            Choose your opponent's solution:
          </div>
          <select class="select-css" v-model="competitorSolution">
            <option v-for="opponentSolution in competitorSolutions" :key="opponentSolution.id"
                    v-bind:value="opponentSolution.id">
              {{ opponentSolution.solutionCompilerLanguage }} |
              {{ opponentSolution.fileLocation }} | {{ opponentSolution.creationTime }}
            </option>
          </select>
        </div>
        <div class="button-field" v-if="competitorSolution">
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
      solution: null,
      maps: [],
      map: null,
      competitors: [],
      competitor: null,
      competitorSolutions: [],
      competitorSolution: null,
      error: '',
    }
  },
  methods:
      {
        onSubmitMatch: function () {
          axios.post("/api/1/match/create-match", {
            creatorParticipantId: this.participant.id,
            opponentParticipantId: this.competitor.id,
            creatorSolutionId: this.solution,
            opponentSolutionId: this.competitorSolution,
          }).then(this.changePage("CompetitionParticipant"));
        },
        getCompetitorSolutions: function (participant) {
          this.competitorSolution = null;
          axios.get("/api/1/solution/" + participant.id + "/all", {}).then(response => {
            this.competitorSolutions = response.data;
          });
        }
        ,
        getParticipantSolutions: function (participant) {
          if (participant) {
            axios.get("/api/1/solution/" + participant.id + "/all", {}).then(response => {
              this.solutions = response.data;
            });
          }
        }
        ,
        changePage: function (page) {
          this.$root.$emit("onChangePage", page);
        }
      },
  beforeMount() {
    this.getParticipantSolutions(this.participant);
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
  box-shadow: 0 1px 0 1px rgba(0, 0, 0, .04);
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
