<template>
  <div class="upload form-box">
    <div class="header">Upload your solution</div>
    <div class="body">
      Choose a language:
      <select v-model="language">
        <option v-for="lang in languages" v-bind:key="lang">{{ lang }}</option>
      </select>
      <form @submit.prevent="onSubmitFile">
        Choose a file:
        <label>
          <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
        </label>
        <div class="button-field">
          <input type="submit" value="Submit">
        </div>
        <div class="error">{{ error }}</div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: ['participant'],
  name: "UploadSolution",

  data: function () {
    return {
      file: '',
      language: '',
      languages: [],
      error: '',
    }
  },

  beforeMount() {
    this.$root.$on("onSubmitError", (error) => {
      if (!error) {
        this.error = 'An error occurred while sending file. make sure to fill all the fields';
      } else {
        this.error = error;
      }
    });
    axios.get("/api/1/solution/languages", {}).then(response => {
      this.languages = response.data;
    });
  },

  methods: {
    onSubmitFile: function () {
      this.$root.$emit("onSubmitFile", this.file, this.participant, this.language);
    },
    handleFileUpload() {
      this.file = this.$refs.file.files[0];
    }
  }
}
</script>

<style scoped>

</style>
