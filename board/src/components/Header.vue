<template>
  <div id="header">
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand to="/">Vue.js 게시판</b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <div v-if="islogin == ''">
            <b-nav-item to="/login">로그인</b-nav-item>
          </div>
          <div v-if="islogin !=''">
            <b-nav-item id="logout" @click="logout">로그아웃</b-nav-item>&nbsp;
          </div>
          <b-nav-item to="/signUp">회원가입</b-nav-item>
          <b-nav-item to="/board">자유게시판</b-nav-item>
          <b-nav-item to="/">??</b-nav-item>
          <b-nav-item title="userInfo" class="userId" v-if="islogin != ''">
          {{islogin}}님 
          </b-nav-item>
        <userInfo class="userInfo" title="userInfo"/>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>

  </div>
        
</template>

<script>
import userInfo from "@/components/userInfo";

const storage = window.sessionStorage;

export default {
  nama: "Header",
  data() {
    return {
      islogin: sessionStorage.login_user
    };
  },
  computed:{
  },
  methods: {
    logout() {
      storage.setItem("jwt-auth-token", "");
      storage.setItem("login_user", "");
      this.message = "로그인 해주세요..";
      // this.setInfo("로그아웃 성공", "", "");
      // this.$router.push('/');
      this.$router.go(0);
    }
  },
  components:{
    userInfo
  },

};
</script>
<style>
#header {
  background-color: #111;
}
.userId {
  color: lightgray;
}
.userInfo{
  
}
</style>