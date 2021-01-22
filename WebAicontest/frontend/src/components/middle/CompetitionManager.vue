<template>
  <div>
    <h1>
      {{ competition.name }}
    </h1>
    <h3>Assigned requests:</h3>
    <div v-for="request in assignedRequests" :key="request.id">
      <h4>{{ request.theme + "(" + request.competitionParticipant.baseUserLogin + " | " + request.requestStatus + ")" }}
        <button @click="closeAssignedRequest(request.id)">Close</button>
      </h4>
      Description: {{ request.description }}
    </div>
    <h3>New requests:</h3>
    <div v-for="request in openedRequests" :key="request.id">
      <h4>{{ request.theme + "(" + request.competitionParticipant.baseUserLogin + " | " + request.requestStatus + ")" }}
        <button @click="assignNewRequest(request.id)">Assign to me</button>
      </h4>
      Description: {{ request.description }}
    </div>
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