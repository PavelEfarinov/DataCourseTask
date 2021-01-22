<template>
  <div><h1>
    {{ competition.name }}
  </h1>
    <div class="upload form-box">
      <div class="header">Send help request</div>
      <div class="body">
        <form @submit.prevent="submitRequest">
          Provide a topic of your request:
          <label>
            <input v-model="theme" type="text" id="request-theme"/>
          </label>
          <label>
            Detailed description of your problem:
            <textarea v-model="description" id="request-description"/>
          </label>

          <div class="button-field">
            <input type="submit" value="Submit">
          </div>
          <div class="error">{{ error }}</div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CreateCompetitionRequest",
  props: ['participant', 'competition'],
  data: function () {
    return {
      theme: "",
      description: "",
      error: null,
    }
  },
  methods: {
    submitRequest() {
      axios.post("/api/1/request/" + this.participant.id + "/new", {
        theme: this.theme,
        description: this.description,
      }, {}).then(response => {
        if (response.status === 200) {
          this.$root.$emit("onSubmitRequestSuccess");
        }
      }).catch(error => {
        this.error = error.response.data;
      });
    }
  },
}
</script>

<style scoped>

</style>