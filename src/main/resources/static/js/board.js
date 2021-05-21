/**
 * 
 */
$(document).ready(function () {

	$("#btnWrite").click(function () {
		console.log("im here");
		console.log($("#boardTitle").val());
		console.log($("#boardContent").val());
		console.log($("#writer").val());

		var now = new Date();


		var param = {
			"boardTitle": $("#boardTitle").val(),
			"boardContent": $("#boardContent").val()
		}

		$.ajax({
			url: "/writeProc",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: function (data) {
				if (data != 0) {
					alert("write success");
					location.href = "/board?boardSeq=" + $("#boardSeq").val();
				} else {
					alert("write fali");
					location.href = "/board";
				}
			}
		});
	});


	$("#btnModify").click(function () {
		console.log("hello , modify");
		console.log($("#hiddenSeq").val());

		var param = {
			"boardTitle": $("#boardTitle").val(),
			"boardContent": $("#boardContent").val(),
			"seq": $("#hiddenSeq").val()
		}

		$.ajax({
			url: "/modifyProc",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: function (data) {
				if (data != 0) {
					alert("modify success");
					location.href = "/board/detail?seq=" + $("#hiddenSeq").val();
				} else {
					alert("modify fail");
				}
			}
		});
	});

	$("#btnDelete").click(function () {
		console.log("delete here");

		if (!confirm("정말 게시글을 삭제하시겠습니까?")) {
			return false;
		}
		var param = {
			"seq": $("#hiddenSeq").val()
		}

		$.ajax({
			url: "/delete",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: function (data) {
				if (data == 0) {
					alert("delete success");
					location.href = "/board";
				} else {
					alert(data);
				}
			}
		})
	});


	$("#btnSearch").on("click", function () {
		console.log($("#searchText").val());
		var param = {
			"searchText": $("#searchText").val()
		}

		$.ajax({
			url: "/search",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: function (data) {
				alert(data);
				console.log(data)
			}, error: function (data) {
				console.log("error")
				console.log(data)
			}
		});
	});

	$(document).on("click", ".reCommentBtn", function (e) {
		console.log("대댓글 버튼 id " + e.target.id);

		console.log("----부모 댓글 seq----");
		console.log(getSeq(e));
		console.log("대댓글 내용 " + $("#reCommentContent_" + getSeq(e)).val());

		console.log("레벨 작업 --> " + $("#delete" + getSeq(e)).children(":eq(1)").val());




		if ($('#reCommentContent_' + getSeq(e)).css("display") == 'none') {
			$("#reCommentBtn_" + getSeq(e)).css("display", "block");
			$("#reCommentContent_" + getSeq(e)).css("display", "block");
		} else {
			$("#reCommentBtn_" + getSeq(e)).css("display", "none");
			$("#reCommentContent_" + getSeq(e)).css("display", "none");
		}

		//		지금 달고싶은 댓글의 level은 클릭된 댓글의 level값+1이 되어야함.
		//		각 level & 클릭된 댓글의 level+1 // 
		//		if( ){
		//			
		//		}
		var param = {
			"superCommentId": getSeq(e),
			"commentContent": $("#reCommentContent_" + getSeq(e)).val(),
			"boardNo": $("#hiddenSeq").val(),
			"level": parseInt($("#delete" + getSeq(e)).children(":eq(1)").val()) + 1
		}
		var html = "";
		$.ajax({
			url: "/comment/recomment",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: function (data) {
				console.log("recommentApply success" + data)
				console.log("코멘트 아이디 : " + data.commentId)
				console.log(data.commentContent)
				console.log(data.member.email)
				console.log(data.insDate)
				console.log("level : " + data.level)
				console.log("----- recommentApply end-----")

				var space = "";
				for (var i = 0; i < parseInt(data.level); i++) {
					space += "  ";
				}
				space += "┗";
				space += data.commentContent;

				// html += "<div>"
				// html += "<div id='delete" + data.commentId + "'>"
				// html += "<input type='hidden' id='hiddenCommentId" + data.commentId + "' value='" + data.commentId + "'/>"
				// html += "<input type='hidden' id='hiddenLevel' value='" + data.level + "'/>"
				// html += "<input class='inputStyle' style='border:none;border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;' id='modify" + data.commentId + "'  readonly='true'/> | ";
				// html += "<span>" + data.member.email + "<span/> | ";
				// html += "<span>" + data.insDate.split("+")[0] + "<span/>  |";
				// html += '<button id="btnDelete_' + data.commentId + '" class="commentDeleteBtn">X</button>';
				// html += '<button class="commentModifyBtn" id="commentModifyBtn_' + data.commentId + '">수정</button>';
				// html += '<span>'
				// html += '<button class="reCommentBtnOpen" id="reCommentBtnOpen_' + data.commentId + '">답글</button>'
				// html += '<button class="reCommentBtn" id="reCommentBtn_' + data.commentId + '" style="display:none;">작성 완료</button>';
				// html += '</span>'
				// html += '<input type="text" id="reCommentContent_' + data.commentId + '" style="display:none;"/>'
				// html += "<br>"

				// $("#commentContent").val("");
				// $("#delete" + getSeq(e)).append(html);
				// $("#modify" + data.commentId).attr("value", space);
				// reply(getSeq(e));

				// function reply(p_id) {
				// 	var id = $("input[value=" + p_id + "]").last().val();
				// 	console.log("id : "+ id);
				// 	if (id === undefined) {
				// 		$("#modify" + data.commentId).attr("value", space);
				// 		return;
				// 	}
				// 	reply(id);
				// }




				//				$(".reload").load("commentAreaHtml.html");
				$(".reload").load(location.href="/board/detail?seq="+$("#hiddenSeq").val());

			}, error: function (data) {
				console.log("recommentApply error" + data)
				//				console.log(data)
			}
		});
	});

	$(document).on("click", ".reCommentBtnOpen", function (e) {

		if ($('#reCommentContent_' + getSeq(e)).css("display") == 'none') {
			$("#reCommentBtn_" + getSeq(e)).css("display", "block");
			$("#reCommentContent_" + getSeq(e)).css("display", "block");
		} else {
			$("#reCommentBtn_" + getSeq(e)).css("display", "none");
			$("#reCommentContent_" + getSeq(e)).css("display", "none");
		}
	});

	function getSeq(e) {
		return e.target.id.split("_")[1];
	}

	$(document).on("click", "#commentApplyBtn", function (e) {
		console.log("here");
		console.log($("#commentContent").val());
		console.log($("#hiddenEmail").val());
		console.log($("#hiddenSeq").val());
		var html = "";
		var param = {
			"commentContent": $("#commentContent").val(),
			"boardNo": $("#hiddenSeq").val(),
			"level": 0
		}


		$.ajax({
			url: "/commentApply",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: function (data) {
				//var date = data.insDate;
				console.log("코멘트 아이디 : " + data.commentId)
				console.log(data.commentContent)
				console.log(data.member.email)
				console.log(data.insDate)
				//				html += "<div>"
				html += "<div id='delete" + data.commentId + "'>"
				html += "<input type='hidden' id='hiddenCommentId" + data.commentId + "' value='" + data.commentId + "'/>"
				html += "<input type='hidden' id='hiddenLevel' value='" + data.level + "'/>"
				html += "<input class='inputStyle' style='border:none;border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;' value='" + data.commentContent + "' id='modify" + data.commentId + "'  readonly='true'/> | ";
				html += "<span>" + data.member.email + "<span/> | ";
				html += "<span>" + data.insDate.split("+")[0] + "<span/>  |";
				html += '<button id="btnDelete_' + data.commentId + '" class="commentDeleteBtn">X</button>';
				html += '<button class="commentModifyBtn" id="commentModifyBtn_' + data.commentId + '">수정</button>';
				html += '<span>'
				html += '<button class="reCommentBtnOpen" id="reCommentBtnOpen_' + data.commentId + '">답글</button>'
				html += '<button class="reCommentBtn" id="reCommentBtn_' + data.commentId + '" style="display:none;">작성 완료</button>';
				html += '</span>'
				html += '<input type="text" id="reCommentContent_' + data.commentId + '" style="display:none;"/>'
				html += "<br>"
				//				html += "</div>";

				$("#commentArea").append(html);
				$("#commentContent").val("");

			}, error: function (data) {
				console.log("error")
				console.log(data)
			}
		});
	})

	$(document).on("click", ".commentDeleteBtn", function (e) {
		var i = e.target.id.split("_")[1];
		var param = {
			"commentId": i
		}

		$.ajax({
			url: "/commentDelete",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: function (data) {
				if (data == 0) {
					console.log("commentDelete success")
					$(".reload").load(location.href = "/board/detail?seq=" + $("#hiddenSeq").val());
					//					$("#delete" + i).remove();
				}
			}, error: function (data) {
				if (data == 0) {
					console.log("commentDelete error")
				}
			}
		});
	});

	$(document).on("click", ".commentModifyBtn", function (e) {
		console.log("welcom to modify world");
		var i = e.target.id.split("_")[1];
		var param = {
			"commentId": i,
			"commentContent": $("#modify" + i).val()
		}

		if ($('#modify' + i).attr("readonly") == 'readonly') {
			console.log("readonly is readonly")
			$('#modify' + i).removeAttr("readonly");
			$('#modify' + i).removeClass("inputStyle");
			$('#modify' + i).focus();
		} else {
			console.log("readonly is not readonly");
			$('#modify' + i).attr("readonly", true);
			$('#modify' + i).addClass("inputStyle");
		}

		$.ajax({
			url: "/commentModify",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: function (data) {
				if (data == 0) {
					console.log("commentModify success")
					//					$("#delete" + i).remove();

				}
			}, error: function (data) {
				if (data == 0) {
					console.log("commentModify error")
				}
			}
		});
		console.log($('#modify' + i).attr("readonly"))
		console.log($("#modify" + i).val());
	});




});