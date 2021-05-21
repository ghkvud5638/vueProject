<template>
  <div>
	<h3>로그인</h3>
	<b-input type="text" v-model="username" autocomplete="username" placeholder="아이디를 입력해주세요."></b-input>
	<b-input type="text" v-model="password" autocomplete="password" placeholder="패스워드를 입력해주세요."></b-input>
	&nbsp;
    <b-button type="button" @click="login">로그인</b-button>&nbsp;


  </div>

</template>

<script>


export default {
	name:"login",
	data(){
		return {
			username:'',
			password:''
		}
	},
  	computed: {
  	},
	create() {
		this.$store.commit('logout');
	},
	methods:{

	login:function(){
		console.log("로그인");
		const param ={
			username:this.username,
			password:this.password
		}

		this.$axios.post(`${this.$store.state.host}/loginVue`, {param})
			.then(res=>{
				console.log("결과");
				console.log(res)
				this.$router.push("/");
			})
			// const apiUrl = "/loginVue";
			// const promise = this.$http
			// .post(apiUrl,param)
			// .then(() => {
			// 	this.$router.push({
			// 		path:`/`
			// 	});
			// })
			// .catch(e => {
			// console.log("login error : " + e);
			// });
			// return promise;
		
	}
}}
</script>

<style>

</style>