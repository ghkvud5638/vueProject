<template>
  <div>
	<h3>회원가입</h3>
	<b-input v-model="email" placeholder="아이디를 입력해주세요."></b-input>
	<b-input v-model="password" placeholder="패스워드를 입력해주세요."></b-input>
	<b-input v-model="name" placeholder="이름을 입력해주세요."></b-input>
	&nbsp;
    <b-button @click="signup()">회원가입</b-button>&nbsp;


  </div>

</template>

<script>
export default {
	data(){
		return {
			email:'',
      password:'',
      name:''
		}
	},
	create() {
		
	},
	methods:{
		signup(){
			console.log("signup");
			const param ={
				email : this.email,
				password : this.password,
				name : this.name
			}
			console.log(param);
			const apiUrl = "/signUp";
			const promise = this.$http
			.post(apiUrl,param)
			.then(() => {
				this.$router.push({
					path:`/`
				});
			})
			.catch(e => {
			console.log("detail error : " + e);
			});
			return promise;
		
		}
	}
}
</script>

<style>

</style>