<template>
  <div class="middle">
    <main>
      <Index v-if="page === 'Index'"/>
      <Enter v-if="page === 'Enter'"/>
      <Register v-if="page === 'Register'"/>
      <MyProfile v-if="page === 'MyProfile'" :user="user"/>
      <UploadSolution v-if="page === 'UploadSolution'" :participant="participant"/>
      <MySolutions v-if="page === 'MySolutions'" :user="user"/>
      <Competition v-if="page === 'Competition'" :user="user" :competition="competition"/>
      <CompetitionParticipant v-if="page === 'CompetitionParticipant'" :participant="participant" :competition="competition"/>
      <CreateCompetitionRequest v-if="page === 'CreateCompetitionRequest'" :participant="participant" :competition="competition"/>
      <CompetitionManager v-if="page === 'CompetitionManager'" :manager="manager" :competition="competition"/>
      <ManageCompetitionStorages v-if="page === 'ManageCompetitionEquipment'" :manager="manager" :competition="competition"/>
      <CreateSandboxMatch v-if="page === 'CreateSandboxMatch'" :participant="participant" :competition="competition"/>
    </main>
  </div>
</template>
<script>
import Index from './middle/Index';
import Enter from './middle/Enter';
import Register from './middle/Register';
import MyProfile from './middle/MyProfile';
import UploadSolution from './middle/UploadSolution';
import MySolutions from './middle/MySolutions';
import Competition from "@/components/middle/Competition";
import CompetitionParticipant from "@/components/middle/CompetitionParticipant";
import CreateCompetitionRequest from "@/components/middle/CreateCompetitionRequest";
import axios from "axios";
import CompetitionManager from "@/components/middle/CompetitionManager";
import ManageCompetitionStorages from "@/components/middle/ManageCompetitionStorages";
import CreateSandboxMatch from "@/components/middle/CreateSandboxMatch";

export default {
  name: "Middle",
  props: ["user"],
  data: function () {
    return {
      page: "Index",
      participant: null,
      competition: null,
      manager: null,
    }
  },
  computed: {
    // lazy data stuff
  },
  components: {
    CreateSandboxMatch,
    ManageCompetitionStorages,
    CompetitionManager,
    CompetitionParticipant,
    Competition,
    Index,
    Enter,
    Register,
    MyProfile,
    UploadSolution,
    MySolutions,
    CreateCompetitionRequest,
  }, beforeCreate() {
    this.$root.$on("onChangePage", (page) => {
      if (page === "CompetitionParticipant") {
        axios.get("/api/1/participant/" + this.competition.id + "/" + this.user.login + "/id", {}).then(response => {
          this.participant = response.data;
          this.page = page;
        });
      } else if (page === "CompetitionManager" || page === "ManageCompetitionStorages") {
        axios.get("/api/1/manager/" + this.competition.id + "/" + this.user.login + "/id", {}).then(response => {
          this.manager = response.data;
          this.page = page;
        }).catch(error => {
          this.$root.$emit("onEnterCompetitionManagerError", error.response.data);
        });
      } else {
        this.page = page;
      }
    });
    this.$root.$on("onOpenCompetitionPage", (competition) => {
      this.$root.$emit("onChangePage", "Competition");
      this.competition = competition;
    });
  }
}
</script>

<style scoped>

</style>
