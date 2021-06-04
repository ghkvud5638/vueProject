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
          <td colspan="3" class="board-content">
            <div>{{ boardDetail.boardContent }}</div>
          </td>
        </tr>
        <tr>
          <th scope="row" class="writerName">등록자</th>
          <td>{{ boardDetail.writerName }}</td>
        </tr>
        <tr>
          <th scope="row">등록일</th>
          <td>{{ boardDetail.insDate }}</td>

        </tr>
      </tbody>
    </table>

    <b-modal ref="modifyModal" hide-footer>
      <template>
        <div class="d-block">
          <h4>Modify</h4>
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
                <td colspan="3">
                  <b-form-input v-model="boardDetail.boardTitle"></b-form-input>
                </td>
              </tr>
              <tr>
                <th scope="row">내용</th>
                <td colspan="3">
                  <div class="board-content">
                    <b-form-input v-model="boardDetail.boardContent"></b-form-input>
                  </div>
                </td>
              </tr>
              <tr>
                <th scope="row">등록자</th>
                <td>
                  <b-form-input v-model="boardDetail.writerName" readonly></b-form-input>
                </td>
                <th scope="row">등록일</th>
                <td>
                  <b-form-input v-model="boardDetail.insDate" readonly></b-form-input>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </template>
      <b-button class="mt-3" variant="outline-danger" block @click="modify">수정 완료</b-button>
    </b-modal>
    <div v-if="email == login_user">
      <b-button id="show-btn" @click="showModal">수정</b-button>
      <b-button type="button" @click="deleteBoard">삭제</b-button>&nbsp;
    </div>
  </div>
</template>

<script>
// import CommentList from "./CommentList";
import axios from "axios";

export default {
  name: "BoardDetail",
  props: [],
  data() {
    return {
      boardDetail: {},
      login_user:sessionStorage.login_user,
      email:""
    };
  },
  mounted() {},
  created() {
    const apiUrl = "/detailVue/" + this.$route.params.seq;
    const promise = this.$http
      .get(apiUrl)
      .then(solve => {
        this.boardDetail = solve.body;
        this.email = this.boardDetail.writer.email

        console.log("---email---")
        console.log(this.email)
      })
      .catch(e => {
        console.log("detail error : " + e);
      });
    return promise;
  },
  methods: {
    showModal() {
      this.$refs["modifyModal"].show();
    },
    modify() {
      axios
        .post("/board/modifyBoard", {
          seq: this.$route.params.seq,
          boardTitle: this.boardDetail.boardTitle,
          boardContent: this.boardDetail.boardContent,
          writerName: this.boardDetail.writerName,
          insDate: this.boardDetail.insDate
        })
        .then(() => {
          console.log("/board/modify");
        });
      this.$refs["modifyModal"].hide();
    },

    deleteBoard() {
      axios
        .post("/board/deleteBoard", {
          seq: this.$route.params.seq
        })
        .then(() => {
          console.log("delete");
          this.$router.push("/");
        });
    }
  },
  components: {
    // CommentList
  }
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
.board-content{
  height: 100px;
  text-align: center;
  margin-left: auto; margin-right: auto;
}
.writerName{
  height: 30px;
}
</style>