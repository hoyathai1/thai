function search(page) {
    var content = $("input[name=table_search]").val();

    if (!isEmpty(content)) {
        location.href="/admin/board/comment/list?page=" + page + "&content="+content;
    } else {
        location.href="/admin/board/comment/list?page=" + page;
    }
}

function deleteComment(commentId) {
    $.ajax({
        type : 'post',
        url : '/admin/board/comment/delete',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            commentNum : commentId
        }),
        success : function () {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }, error : function () {
        }
    });

    event.stopPropagation();
}

function restoreComment(commentId) {
    $.ajax({
        type : 'post',
        url : '/admin/board/comment/restore',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            commentNum : commentId
        }),
        success : function () {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }, error : function () {
        }
    });

    event.stopPropagation();
}

function deleteCommentForView() {
    var boardId = $("#delete-modal").data("board");
    var commentId = $("#delete-modal").data("comment");

    $.ajax({
        type : 'post',
        url : '/admin/board/comment/delete',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            commentNum : commentId
        }),
        success : function () {
            getCommentListForView(boardId);
            closeModalForViewMoal();
        }, error : function () {
        }
    });

    event.stopPropagation();
}

function restoreCommentForView() {
    var boardId = $("#restore-modal").data("board");
    var commentId = $("#restore-modal").data("comment");

    $.ajax({
        type : 'post',
        url : '/admin/board/comment/restore',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            commentNum : commentId
        }),
        success : function () {
            getCommentListForView(boardId);
            closeModalForViewMoal();
        }, error : function () {
        }
    });

    event.stopPropagation();
}

function getContentForView(boardId) {
    $.ajax({
        type : 'post',
        url : '/admin/board/board/detail',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        async: false,
        data : JSON.stringify({
            boardNum : boardId
        }),
        success : function (data) {
            $("#view-title").val(data.title);
            $("#view-editor").html(data.contents);
        }, error : function () {
        }
    });
}

function getCommentListForView(boardId) {
    $.ajax({
        type : 'post',
        url : '/admin/board/comment/commentList',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        async: false,
        data : JSON.stringify({
            boardNum : boardId
        }),
        success : function (data) {
            setListHtml(data);
        }, error : function () {
        }
    });
}

function setListHtml(data) {
    var listHtml = "";

    $(data.list.content).each(function () {


        if (this.del) {
            listHtml += "<div class='comment' onclick='restoreCommentModal(" + this.upper + ", " + this.id+ ")'>";
        } else {
            listHtml += "<div class='comment' onclick='deleteCommentModal(" + this.upper + ", " + this.id+ ")'>";
        }

        if (this.user) {
            listHtml += "   <div class='author'>" + this.author + "</div>";
        } else {
            listHtml += "   <div class='author'>" + this.author + " (" + this.ip + ")</div>";
        }
        listHtml += "   <div class='createDate'>" + getReplyTime(this.createDate) + "</div>";

        if (this.del) {
            listHtml += "   <div class='delete-comment'>삭제됨</div>";
        }

        listHtml += "   <div class='content'>" + this.content + "</div>";

        listHtml += "</div>";

        $(this.children).each(function (e) {
            if (this.del) {
                listHtml += "<div class='comment add' onclick='restoreCommentModal(" + this.upper + ", " + this.id+ ")'>";
            } else {
                listHtml += "<div class='comment add' onclick='deleteCommentModal(" + this.upper + ", " + this.id+ ")'>";
            }

            if (this.user) {
                listHtml += "    <div class='add-ico'></div>"
                listHtml += "   <div class='author add'>" + this.author + "</div>";
            } else {
                listHtml += "    <div class='add-ico'></div>"
                listHtml += "   <div class='author add'>" + this.author + " (" + this.ip + ")</div>";
            }
            listHtml += "   <div class='createDate add'>" + getReplyTime(this.createDate) + "</div>";

            if (this.del) {
                listHtml += "   <div class='delete-comment'>삭제됨</div>";
            }

            listHtml += "   <div class='content add'>" + this.content + "</div>";

            listHtml += "</div>";

        });
    });

    $(".comment-list").html(listHtml);
}

function deleteComment(commentId) {
    
}

function deleteCommentModal(boardId, commentId) {
    $("#delete-modal").data("board", boardId);
    $("#delete-modal").data("comment", commentId);

    $("#delete-modal").css("display", "block");
    $("#delete-modal").addClass("show");
}

function restoreCommentModal(boardId, commentId) {
    $("#restore-modal").data("board", boardId);
    $("#restore-modal").data("comment", commentId);

    $("#restore-modal").css("display", "block");
    $("#restore-modal").addClass("show");
}


function commentModal(commentId) {
    var content = $("#comment-" + commentId).text();
    $("input[name=content]").val(content);

    $(".comment-list").html("");
    $("#comment-modal").data("id", commentId);

    $("#comment-modal").css("display", "block");
    $("#comment-modal").addClass("show");
}

function modify() {
    var id = $("#comment-modal").data("id");
    var content = $("input[name=content]").val();

    $.ajax({
        type : 'post',
        url : '/admin/board/comment/modify',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        async: false,
        data : JSON.stringify({
            id : id,
            content : content
        }),
        success : function (data) {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }, error : function () {
        }
    });
}

function viewModal(boardId) {
    var title = $("#title-" + boardId).text();
    $("#view-title").val(title);
    getContentForView(boardId);
    getCommentListForView(boardId);

    $("#view-modal").css("display", "block");
    $("#view-modal").addClass("show");
}

function closeModal() {
    $(".modal").css("display", "none");
    $(".modal").removeClass("show");
}

function closeModalForViewMoal() {
    $("#restore-modal").css("display", "none");
    $("#restore-modal").removeClass("show");
    $("#delete-modal").css("display", "none");
    $("#delete-modal").removeClass("show");
}

function getReplyTime(timeValue) {
    var dateObj = new Date(timeValue);
    var today = new Date();

    var tYear = today.getFullYear();
    var tMonth = today.getMonth();
    var tDate = today.getDate();
    var todayDate = tYear+tMonth+tDate;

    var year = dateObj.getFullYear();
    var month = dateObj.getMonth();
    var date = dateObj.getDate();
    var hours = ('0' + dateObj.getHours()).slice(-2);
    var minutes = ('0' + dateObj.getMinutes()).slice(-2);
    var seconds = ('0' + dateObj.getSeconds()).slice(-2);
    var boardDate = year+month+date;


    return month + '.' + date + " " + hours + ':' + minutes;
}