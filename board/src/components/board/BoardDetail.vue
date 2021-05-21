<template>
  <div>
    <table class="tbl-row">
      <colgroup>
        <col class="col-row-title" />
        <col />
        <col class="col-row-title" />
        <col />
      </colgroup>
      <tbody>
        <tr>
          <th scope="row">제목</th>
          <td colspan="3">{{ boardDetail.boardTitle }}</td>
        </tr>
        <tr>
          <th scope="row">내용</th>
          <td colspan="3">
            <div class="board-content">{{ boardDetail.boardContent }}</div>
          </td>
        </tr>
        <tr>
          <th scope="row">등록자</th>
          <td>{{ boardDetail.writerName }}</td>
          <th scope="row">등록일</th>
          <td>{{ boardDetail.insDate }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
// import data from "@/data";
// import CommentList from "./CommentList";

export default {
  name: "BoardDetail",
  props: [],
  data() {
    return {
      // seq : this.$route.params.seq,
      boardDetail: {}
    };
  },
  mounted() {},
  created() {

      const apiUrl = "/detailVue/" + this.$route.params.seq;
      const promise = this.$http
        .get(apiUrl)
        .then(solve => {
          // console.log(solve);
          // console.log(solve.body);
          this.boardDetail = solve.body
          console.log(this.boardDetail);

          // this.$router.push({
          //   path:`/detailVue/${this.seq}`
          // });
          // return this.boardDetail;
          // this.$router.go(this.$router.currentRoute);
        })
        .catch(e => {
          console.log("detail error : " + e);
        });
      return promise;
    

  },

  // watch:{
  //   '$route':'setBoardDetail'
  // },

  methods: {
    // async setBoardDetail(seq) {
    //    this.callBoardDetail(seq);
    // },

  },
  components: {
    // CommentList
    // BoardDetail
  }
  // resetDetail(){
  //   this.boardDetail = {};
  // },
};
</script>
<style scoped>
.content-detail-content-info {
  border: 1px solid black;
  display: flex;
  justify-content: space-between;
}

.content-detail-content-info-left {
  width: 720px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
}

.content-detail-content-info-right {
  width: 300px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1rem;
}

.content-detail-content {
  border: 1px solid black;
  margin-top: 1rem;
  padding-top: 1rem;
  min-height: 720px;
}

.content-detail-button {
  border: 1px solid black;
  margin-top: 1rem;
  padding: 2rem;
}

.content-detail-comment {
  border: 1px solid black;
  margin-top: 1rem;
  padding: 2rem;
}
</style>