<template>
  <div>
    <h3>로그인</h3>
    <!-- veux 이전 버전 -->
    <!-- <b-input type="text" v-model="email" autocomplete="email" placeholder="아이디를 입력해주세요."></b-input>
    <b-input type="text" v-model="password" autocomplete="password" placeholder="패스워드를 입력해주세요."></b-input>&nbsp; -->

    <!-- vuex 이후 -->
    <b-input type="text" v-model="userInfo.email" autocomplete="loginId" placeholder="아이디를 입력해주세요."></b-input>
    <b-input type="text" v-model="userInfo.password" autocomplete="loginId" placeholder="패스워드를 입력해주세요."></b-input>&nbsp;

    <div v-if="islogin == null || islogin ==''">
      <b-button type="submit" @click="login">로그인</b-button>&nbsp;
    </div>
    <!-- <div v-if="islogin !=''">
      <b-button id="logout" @click="logout">로그아웃</b-button>&nbsp;
    </div> -->
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";

const storage = window.sessionStorage;

export default {
  name: "login",
  data() {
    return {
      // email: "",
      // password: "",
      // message: "로그인 해주세요",
      // status: "",
      // token: "",
      // info: "",
      // userInfo:{},
      islogin: sessionStorage.login_user
    };
  },
  computed: {
    ...mapState({
      userInfo: state => state.login.userInfo,
      // login: (state) => state.login

    }),
    ...mapGetters({
    })
  },
  create() {
  },
  methods: {
    ...mapActions({
      handleLogin:"handleLogin"
    }),

    setInfo(status, token, info) {
      this.status = status;
      this.token = token;
      this.info = info;
    },

    // vuex 적용 이전 버전
    // login() {
    //   console.log("login try");
    //   storage.setItem("jwt-auth-token", "");
    //   storage.setItem("login_user", "");
    //   axios
    //     .post("/loginVue", {
    //       email: this.email,
    //       password: this.password
    //     })
    //     .then(res => {
    //       console.log("여기는?");
    //       if (res.data.status) {
    //         this.message = res.data.data.email + " 로 로그인 되었습니다.";
    //         console.log(res.headers["jwt-auth-token"]);
    //         this.setInfo(
    //           "성공",
    //           res.headers["jwt-auth-token"],
    //           JSON.stringify(res.data.data)
    //         );
    //         storage.setItem("jwt-auth-token", res.headers["jwt-auth-token"]);
    //         storage.setItem("login_user", res.data.data.email);
		// 	// this.$router.push("/");
		// 	this.$parent.reload();
    //       } else {
    //         this.setInfo("", "", "");
    //         this.message = "로그인 해주세요";
    //         alert("입력정보를 확인해주세요");
    //       }
    //       this.islogin = "yes";
    //     })
    //     .catch(e => {
    //       this.setInfo("실패", "", JSON.stringify(e.response || e.message));
    //     });
    // },

    //vuex 적용 버전
    login() {
      console.log("login try");
      storage.setItem("jwt-auth-token", "");
      storage.setItem("login_user", "");


      let result = this.handleLogin(this.userInfo);
      result.then((res)=>{
        if(res.data.status){
          console.log("여기는?");
          if (res.data.status) {
            this.message = res.data.data.email + " 로 로그인 되었습니다.";
            console.log(res.headers["jwt-auth-token"]);
            this.setInfo(
              "성공",
              res.headers["jwt-auth-token"],
              JSON.stringify(res.data.data)
            );
            storage.setItem("jwt-auth-token", res.headers["jwt-auth-token"]);
            storage.setItem("login_user", this.userInfo.email);
            this.$parent.reload();
          }else {
            this.setInfo("", "", "");
            this.message = "로그인 해주세요";
            alert("입력정보를 확인해주세요");
          }
          this.islogin = "yes";
        }
      }).catch((e)=>{
        this.setInfo("실패", "", JSON.stringify(e.response || e.message));

      })
    },


    logout() {
      storage.setItem("jwt-auth-token", "");
      storage.setItem("login_user", "");
      this.message = "로그인 해주세요..";
      this.setInfo("로그아웃 성공", "", "");
    },
    init() {
      if (storage.getItem("jwt-auth-token")) {
        this.message = storage.getItem("login_user");
      } else {
        storage.setItem("jwt-auth-token", "");
      }
    }
  },
  mounted() {
    console.log("ID");
    console.log(sessionStorage.login_user);

    this.init();
  }
};
</script>

<style>
</style>