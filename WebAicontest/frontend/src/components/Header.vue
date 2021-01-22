<!--suppress HtmlUnknownAnchorTarget -->
<template>
  <header>
    <!--        TODO: check / redirect -->
    <a href="#page=Index" @click="changePage('Index')"><img src="../assets/img/cow_pic.jpg" alt="AI-Contest"
                                                                       title="AI-Contest"/></a>

    <div class="enter-or-register-box">
      <template v-if="user">
        {{ user.login }}
        |
        <a href="#page=Logout" @click="logout">Logout</a>
      </template>
      <template v-else>
        <a href="#page=Enter" @click="changePage('Enter')">Enter</a>
        |
        <a href="#page=Register" @click="changePage('Register')">Register</a>
      </template>
    </div>
    <nav>
      <ul>
        <li><a href="#page=Index" @click="changePage('Index')">Home</a></li>
        <li v-if="user">
          <a href="#page=MyProfile" @click="changePage('MyProfile')">My profile</a>
        </li>
        <li v-if="user">
          <a href="#page=UploadSolution" @click="changePage('UploadSolution')">Upload solution</a>
        </li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  props: ['user'],
  name: "Header",
  beforeCreate() {
    this.$root.$on("onEnterSuccess", () => {
      this.changePage('Index');
    });
    this.$root.$on("onRegisterSuccess", () => {
      this.changePage('Enter');
    });
    this.$root.$on("onSubmitRequestSuccess", () => {
      this.changePage('CompetitionParticipant');
    });
  },
  methods: {
    changePage: function (page) {
      this.$root.$emit("onChangePage", page);
    }, logout: function () {
      this.$root.$emit("onLogout");
      this.changePage('Index');
    }
  }
}
</script>

<style scoped>
header .languages a {
  margin-left: 0.25rem;
}
</style>
