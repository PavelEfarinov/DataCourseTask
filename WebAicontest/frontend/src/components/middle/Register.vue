<template>
    <div class="register form-box">
        <div class="header">Register</div>
        <div class="body">
            <form @submit.prevent="onRegister">
                <div class="field">
                    <div class="name">
                        <label for="login">Login</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login" v-model="login"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="email">Email</label>
                    </div>
                    <div class="value">
                        <input id="email" name="email" v-model="email"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="password">Password</label>
                    </div>
                    <div class="value">
                        <input id="password" type="password" name="password" v-model="password"/>
                    </div>
                </div>

                <div class="error">{{error}}</div>

                <div class="button-field">
                    <input type="submit" value="Register">
                </div>
            </form>
        </div>
    </div>
</template>

<script>
    export default {
        data: function () {
            return {
                login: "",
                email: "",
                password: "",
                error: ""
            }
        },
        name: "Register",
        beforeCreate() {
            this.$root.$on("onRegisterValidationError", (error) => {
                this.error = error;
            });
        },
        beforeMount() {
            this.login = "";
            this.password = "";
            this.error = "";
        }, methods: {
            onRegister: function () {
                this.$root.$emit("onRegister", this.login, this.email, this.password);
            }
        }
    }
</script>

<style scoped>

</style>
