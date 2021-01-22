<template>
  <div>
    <h1>
      {{ competition.name }}
    </h1>
    <h3>Assigned requests:</h3>
    <article v-for="request in assignedRequests" :key="request.id">
      <div class="title">
        {{request.theme}}
      </div>
      <div class="information">
        By {{request.competitionParticipant.baseUserLogin}}, status: <b>{{request.requestStatus}}</b>
        <button @click="closeAssignedRequest(request.id)">Close</button>
      </div>
      <div class="body">
        {{ request.description }}
      </div>
    </article>

    <h3>New requests:</h3>
    <article v-for="request in openedRequests" :key="request.id">
      <div class="title">
        {{request.theme}}
      </div>
      <div class="information">
        By {{request.competitionParticipant.baseUserLogin}}, status: <b>{{request.requestStatus}}</b>
        <button @click="assignNewRequest(request.id)">Assign to me</button>
      </div>
      <div class="body">
        {{ request.description }}
      </div>
    </article>
    <button>Create rating round</button>
    <a href="#page=ManageCompetitionEquipment" @click="changePage('ManageCompetitionEquipment')"><button>Manage equipment</button></a>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CompetitionManager",
  props: ["manager", "competition"],
  data: function () {
    return {
      openedRequests: [],
      assignedRequests: [],
    };
  },
  methods: {
    changePage: function (page) {
      this.$root.$emit("onChangePage", page);
    },
    assignNewRequest: function (request) {
      axios.post("/api/1/request/assign", {
        managerId: this.manager,
        requestId: request,
      }).then(this.reloadRequests);
    },
    closeAssignedRequest: function (request) {
      axios.post("/api/1/request/close", {
        requestId: request,
      }).then(this.reloadRequests);
    },
    getAssignedRequests: function () {
      axios.get("/api/1/request/" + this.manager + "/assigned", {}).then(response => {
        this.assignedRequests = response.data;
      });
    },
    getOpenedRequests: function () {
      axios.get("/api/1/request/" + this.competition.id + "/opened", {}).then(response => {
        this.openedRequests = response.data;
      });
    },
    reloadRequests: function () {
      this.getAssignedRequests();
      this.getOpenedRequests();
    }
  },
  beforeMount() {
    this.reloadRequests();
  },
}
</script>

<style scoped>

</style>