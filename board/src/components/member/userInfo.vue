<template>
    <div class="menu-item" @click="isOpen = !isOpen" >
        <svg viewBox="0 0 1030 638" width="10">
            <path d="M1017 68L541 626q-11 12-26 12t-26-12L13 68Q-3 49 6 24.5T39 0h952q24 0 33 24.5t-7 43.5z" fill="#FFF"></path>
        </svg>
        <transition name="fade" apear>
         <div class="sub-menu" v-if="isOpen">
            <div class="menu-item">
				{{islogin}}
            </div>
        </div>
        </transition>
    </div>	
</template>

<script>
import axios from "axios";

export default {
	name:"userInfo",
	data(){
		return{
			islogin: sessionStorage.login_user,
			isOpen:false
		}
	},
	create(){
		if(this.islogin != null){
			axios
				.post("/getUserDetail",{
					email:this.islogin
				})
				.then(res => {
					console.log("유저 조회 결과 값")
					console.log(res)
				})
				.catch(e =>{
					console.log(e)
				})
		}

	}
}
</script>

<style>
.sub-menu{
	color:white;
}
.menu-item{
	/* position: fixed; */

}
</style>