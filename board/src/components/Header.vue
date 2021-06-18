<template>
  <div id="header">
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand to="/">Vue.js 게시판</b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <div v-if="islogin==null || islogin ==''">
            <b-nav-item to="/login">로그인</b-nav-item>
          </div>
          <!-- <div v-if="islogin !='' && islogin!=null">
            <b-nav-item id="logout" @click="logout">로그아웃</b-nav-item>&nbsp;
          </div> -->
          <div v-if="islogin ==null || islogin==''">
            <b-nav-item to="/signUp">회원가입</b-nav-item>
          </div>

          <div v-if="islogin !=null ||islogin != ''">
            <b-nav-item to="/auth">권한관리</b-nav-item>
          </div>
          <b-nav-item to="/board">자유게시판</b-nav-item>
          <!-- <b-nav-item to="/">??</b-nav-item> -->
          <b-nav-item title="userInfo" class="userId" v-if="islogin != '' && islogin!=null" @mouseover="mouseOver">
            {{islogin}}님 
          <svg viewBox="0 0 1030 638" width="10">
            <path d="M1017 68L541 626q-11 12-26 12t-26-12L13 68Q-3 49 6 24.5T39 0h952q24 0 33 24.5t-7 43.5z" fill="#FFF"></path>
          </svg>

          <!-- <userInfo class="userInfo" title="userInfo"/> -->
          </b-nav-item>


        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <div class="userInfo" v-if="seen" @mouseleave="userInfoMouseLeave">
      <div>{{id}}</div>
      <div>{{name}}</div>
      <b-button id="modifyInfo" pill  @click="showModal">프로필 변경</b-button>
      <b-button id="logout" pill  @click="logout" >로그아웃</b-button>
    </div>

  <b-modal ref="modifyInfoModal" hide-footer>
    <template>
      <div class="d-block">
        <h4>회원정보 수정</h4>
        <table class="tbl-row">
      <colgroup>
        <col class="col-row-title" />
        <col />
        <col class="col-row-title" />
        <col />
      </colgroup>
      <tbody>
        <tr>
          <th scope="row">ID</th>
          <td colspan="3">
            <b-form-input v-model="id" disabled></b-form-input>
          </td>
        </tr>
        <tr>
          <th scope="row">이름</th>
          <td colspan="3" class="board-content">
            <b-form-input v-model="name" ></b-form-input>
          </td>
        </tr>
      </tbody>
        </table>
      </div>
    <b-button class="mt-3" block @click="modifyInfo">수정 완료</b-button>

    </template>
  </b-modal>      
  </div>




</template>

<script>
// import userInfo from "@/components/userInfo";
import axios from "axios";
const storage = window.sessionStorage;

export default {
  nama: "Header",
  data() {
    return {
      islogin: sessionStorage.login_user,
      seen : false,
      name:"",
      id:"",
    
    };
  },
  computed:{
  },
  created(){
    this.getUserInfo()
  },
  methods: {
    logout() {
      storage.setItem("jwt-auth-token", "");
      storage.setItem("login_user", "");
      this.message = "로그인 해주세요..";
      // this.setInfo("로그아웃 성공", "", "");
      // this.$router.push('/');
      this.$router.go(0);
    },

    mouseOver(){
      console.log("mouseOver");
      this.seen = !this.seen
    },
    userInfoMouseLeave(){
      this.seen = !this.seen
    },
    // mouseLeave(){
    //   this.seen = !this.seen
    // }
    getUserInfo(){
      if(this.islogin != null){
      console.log("getUserInfo")
      console.log(this.islogin)
			axios
			.post("/getUserDetail",{
					email:this.islogin
			})
			.then(res => {
				console.log("유저 조회 결과 값")
				console.log(res)
        this.name = res.data.name
        this.id = res.data.email
			})
			.catch(e =>{
			console.log(e)
			})
		}

    },
    showModal(){
      this.$refs["modifyInfoModal"].show();
    }
    ,
    modifyInfo(){
      axios
      .post("/api/modifyUserInfo",{
        email:this.id,
        name:this.name
      }).then({
      }).catch(e=>{
        console.log(e)
      })
      this.$refs["modifyInfoModal"].hide();
    },
    init(){
      if (storage.getItem("jwt-auth-token")) {
        this.message = storage.getItem("login_user");
      } else {
        storage.setItem("jwt-auth-token", "");
      }
    }
  },
  components:{
    // userInfo
  },
  mounted(){
    this.init();

  }

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
  position: absolute;
  margin: 0px 0 0 230px;
  z-index: 999;
  width: 190px;
  height: 200px;
  background-color: #212529;
  color:white;
  /* opacity: .9;   */
}

#modifyInfo{
  margin: 50px 0px 0px 0px;
}
#logout{
  margin: 10px 0px 0px 0px;
  width: 110px;
  border: 0;
}
</style>