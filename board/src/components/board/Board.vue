<template>
	<div class="board">
		<BoardList
			ref="boardList"
			:gridHeader="gridHeader"
			:boardList="boardList"
		/>
		<b-button to="/boardCreateVue" variant="outline-primary">글쓰기</b-button>
		
	</div>
</template>

<script>
import 'tui-grid/dist/tui-grid.css'
import BoardList from '@/components/board/BoardList.vue'


export default {
	name: 'Board',
	components:{
		BoardList
	},
	data(){
		return {
			boardList: []
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
		async callBoardList() {
			const apiUrl = "/boardVue"

			await this.$http
				.post(apiUrl)
				.then((solve) => {
					this.boardList = solve.body;
					// console.log("solve : "+solve.bodyText)
					return solve;
				})
				.catch((e) => {
					console.log("here not good "+e);
				});
			// console.log("here good : "+this.boardList);
		}
	}
}
</script>

<style scoped>
.board{
	text-align: center;
}
</style>