<template>
  <div>
    <h3>게시글 작성</h3>
    <b-input v-model="title" placeholder="제목을 입력해주세요."></b-input>
    <b-form-textarea
        v-model="content"
        placeholder="내용을 입력해 주세요"
        rows="3"
        max-rows="6"
    ></b-form-textarea>
    <br>
    <b-button @click="uploadContent()">저장</b-button>&nbsp;
    <b-button @click="cancle">취소</b-button>
  </div>
</template>

<script>

export default {
  name: 'BoardCreate',
  data() {
    return {
      title: '',
      content: ''
    }
  },
  created() {
  },
  methods: {
    uploadContent() { // 저장
      console.log("here");

      const param = {
        boardTitle:this.title,
        boardContent:this.content

      }
      console.log(param);


      const apiUrl = "/board/boardCreateVue"
      const promise = this.$http
        .post(apiUrl,param)
        .then(()=> {
          this.$router.push({
            path:`/`
          });
      })
      .catch(e => {
        console.log("create error : " + e);
      });
      return promise;
    },
    // updateContent() { // 수정
    //     this.updateObject.title = this.subject;
    //     this.updateObject.context = this.context;
    //     this.$router.push({
    //       path: '/'
    //     })
    // },
    cancle() {
      this.title=''
      this.content=''
    }
    
  }
}
</script>

<style>

</style>