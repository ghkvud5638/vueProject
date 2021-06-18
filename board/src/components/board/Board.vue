<template>
	<div class="board">

		<BoardList
			ref="boardList"
			:gridHeader="gridHeader"
			:boardList="boardList"
		/>
    <b-form-input v-model="searchText" id="searchText"/>
    <b-button @click="search" class="mt-3" variant="outline-primary">검색</b-button>
	<b-button  class="mt-3" to="/boardCreateVue" variant="outline-primary">글쓰기</b-button>
	</div>
</template>

<script>
import 'tui-grid/dist/tui-grid.css'
import BoardList from '@/components/board/BoardList.vue'
import axios from "axios";

export default {
	name: 'Board',
	components:{
		BoardList
	},
	data(){
		return {
			boardList: [],
			searchText:""
		}
	},
	created() {
		this.setBoardInfo()
	},
	computed: {
		gridHeader() {
			return [
				{
					header:"번호",
					name:"seq"
				},
				{
					header:"제목",
					name:"boardTitle"
				},
				{
					header:"등록자",
					name:"writerName"
				},
				{
					header:"등록일",
					name:"insDate" 

				},
				{
					header:"조회수",
					name:"boardHits"
				}
			]
		}
	},
	methods: {
		// 게시판 정보 세팅
		setBoardInfo() {
			this.callBoardList();
		},

		// 게시판 목록 API 조회
		async callBoardList() { // async 현재 사용할 함수를 비동기로 처리하겠다
			const apiUrl = "/boardVue"

			await this.$http // await 비동기로 순차 처리하기 위해서 수행할 API에 붙이는 선언자
				.post(apiUrl)
				.then((solve) => {
					this.boardList = solve.body;
					return solve;
				})
				.catch((e) => {
					console.log(e);
				});
		}
		,
		search(){
			console.log(this.searchText)

			axios.post("/boardVue",{
				searchText:this.searchText
			}

			).then(res=>{
				console.log(res)
			})
			.catch()
		}
	}
}
</script>

<style scoped>
.board{
	text-align: center;
}
</style>