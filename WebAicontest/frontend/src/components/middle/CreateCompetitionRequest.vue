<template>
    <form @submit.prevent="submitRequest">
        <h3>
            Send help request for {{ competition.name }}
        </h3>
        <div>
            <label for="request-theme">
                Provide a topic of your request:
            </label>
            <input id="request-theme" class="title" v-model="theme"/>
        </div>
        <div>
            <label for="request-description">
                Detailed description of your problem:
            </label>
            <textarea id="request-description" rows="20" v-model="description"></textarea>
        </div>
        <div class="error">{{error}}</div>
        <div>
            <input type="submit" value="Add"/>
        </div>
    </form>
    <!--    </div>-->
    <!--  </div>-->
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
    label {
        display: block;
        margin-top: 1rem;
    }

    .title, textarea {
        width: 60%;
        box-sizing: border-box;
    }

    input[type='submit'] {
        margin-top: 1rem;
        width: 6rem;
    }

    .error {
        color: var(--error-color);
        margin-left: auto;
    }
</style>