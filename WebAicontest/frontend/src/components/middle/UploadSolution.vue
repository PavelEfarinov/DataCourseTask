<template>
    <div class="upload form-box">
        <div class="header">Upload your solution</div>
        <div class="body">
            <div class="line">
                Choose a language:
                <select v-model="language" class="select-css">
                    <option v-for="lang in languages" v-bind:key="lang">{{ lang }}</option>
                </select>
            </div>
            <form @submit.prevent="onSubmitFile">
                <div class="line">
                    <label for="file">
                        Choose a file:
                    </label>
                    <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>

                </div>
                <div class="button-field">
                    <div class="error">{{ error }}</div>
                    <input class="submit-button" type="submit" value="Submit">
                </div>
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
    .line {
        display: grid;
        grid-template-columns: 37% 63%;
        margin: 1rem;
    }

    .error {
        font-size: small;
        color: var(--error-color);
        margin-left: 1rem;
    }

    .button-field {
        margin: 1rem;
        display: grid;
        grid-template-columns: 35% 35% 30%;
    }

    .submit-button {
        margin: 1%;
        border-radius: 12px;
        background-color: var(--datatable-color);
        border: none;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        color: black;
        transition-duration: 0.4s;

    }

    .submit-button:hover {
        background-color: var(--border-color); /* Green */
        color: white;
    }


    .select-css {
        display: block;
        font-size: 16px;
        font-weight: 700;
        color: #444;
        line-height: 1.3;
        box-sizing: border-box;
        margin: 0;
        border: 1px solid #aaa;
        border-radius: .5em;
        -moz-appearance: none;
        background-color: #fff;
    }

</style>