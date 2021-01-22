<template>
    <div class="upload form-box">
        <div class="header">Upload your solution</div>
        <div class="body">
            Choose a language:
            <select v-model="language">
                <option v-for="lang in languages" v-bind:key="lang">{{lang}}</option>
            </select>
            <form @submit.prevent="onSubmitFile">
                Choose a file:
                <label>
                    <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
                </label>
                <div class="button-field">
                    <input type="submit" value="Submit">
                </div>
                <div class="error">{{error}}</div>
            </form>
        </div>
    </div>
</template>

<script>
    export default {
        props: ['user'],
        name: "UploadSolution",

        data: function () {
            return {
                file: '',
                language: '',
                languages: ['Java', 'C++', 'Python']
            }
        },

        beforeCreate() {
            this.$root.$on("onSubmitError", (error) => {
                this.error = error;
            });
        },

        methods: {
            onSubmitFile: function () {
                this.$root.$emit("onSubmitFile", this.file, this.user, this.language);
            },
            handleFileUpload() {
                this.file = this.$refs.file.files[0];
            }
        }
    }
</script>

<style scoped>

</style>
