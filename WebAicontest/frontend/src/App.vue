<template>
  <!--suppress HtmlUnknownTag -->
  <body id="app">
  <Header :user="user"/>
  <Middle :user="user"/>
  <Footer/>
  </body>
</template>

<script>
import Header from './components/Header'
import Middle from './components/Middle'
import Footer from './components/Footer'
import axios from 'axios'

export default {
  name: 'app',
  data: function () {
    return {
      user: null,
      posts: []
    }
  },
  components: {
    Header,
    Middle,
    Footer
  }, beforeCreate() {
    this.$root.$on("onLogout", () => {
      localStorage.removeItem("jwt");
      this.user = null;
    });

    this.$root.$on("onJwt", (jwt, enter) => {
      axios.defaults.headers = {
        Authorization: "Bearer " + jwt
      };

      axios.get("/api/1/users/authorized").then(response => {
        this.user = response.data;
        if (enter) {
          this.$root.$emit("onEnterSuccess");
        }
      });
    });

    this.$root.$on("onEnter", (login, password) => {
      axios.post("/api/1/jwt", {
        login: login,
        password: password
      }).then(response => {
        localStorage.setItem("jwt", response.data);
        this.$root.$emit("onJwt", response.data, true);
      }).catch(error => {
        this.$root.$emit("onEnterValidationError", error.response.data);
      });
    });

    this.$root.$on("onSubmitFile", (file, participant, language) => {
      let formData = new FormData();
      formData.append('file', file);
      formData.append('participant', participant.id);
      formData.append('language', language);
      axios.post("api/1/solution/submit", formData,
          {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }).then(response => {
        if (response.status === 200) {
          this.$root.$emit("onSubmitSuccess");
        }
      }).catch(error => {
        if (!error.response.data.message) {
          this.$root.$emit("onSubmitError", error.response.data);
        } else {
          this.$root.$emit("onSubmitError", error.response.data.message);
        }
      });
    });

    this.$root.$on("onRegister", (login, email, password) => {
      if (!login || login.length > 16 || login.length < 3 || !login.match(/[a-zA-Z0-9]+/)) {
        this.$root.$emit("onRegisterValidationError", "Login is invalid");
      } else if (!password || password.length < 5 || password.length > 30) {
        this.$root.$emit("onRegisterValidationError", "Password is invalid");
      } else {
        axios.post("/api/1/register", {
          login: login,
          email: email,
          password: password
        }).then(response => {
          if (response.status === 200) {
            this.$root.$emit("onRegisterSuccess");
          }
        }).catch(error => {
          this.$root.$emit("onRegisterValidationError", error.response.data);
        });
      }
    });
  }, beforeMount() {
    if (localStorage.getItem("jwt") && !this.user) {
      this.$root.$emit("onJwt", localStorage.getItem("jwt"), true);
    }
  }
}
</script>

<style>
</style>
