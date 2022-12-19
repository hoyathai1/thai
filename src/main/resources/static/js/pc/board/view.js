const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const type = urlParams.get('type');

var isRunning = false;
var currentCommentPage = 0;

$(document).ready(function () {

    if(detectMobileDevice(window.navigator.userAgent)) {
        var hUrl = new URL(location.href);
        location.href = "/board/view" + hUrl.search;
    }

    if (best == 'Y') {
        $("#best").addClass("on");
    } else {
        $("#" + type).addClass("on");
    }

    $(".createDate").html(getBoardTime($(".createDate").html()));

    getCommentList(currentCommentPage);

    getList(0);
});

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var content = $(".search-input").val();
    var keyword = $("input[name=keyword]").val();

    return "type=" + type + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&keyword=" + keyword + "&content=" + content;
}

function goSignUp() {
    location.href="/pc/signUp?" + makeQueryUrl();
}

function goLogin() {
    location.href="/pc/login";
}

function goLogout() {
    location.href="/logout";
}

function goMyAccount() {
    location.href = "/pc/menu/account";
}

function goMyList() {
    location.href = "/pc/menu/myList?pageNum=0";
}

function goBookmark() {
    location.href = "/pc/menu/myBookmark?pageNum=0";
}

function goMyComment() {
    location.href = "/pc/menu/myComment?pageNum=0";
}

function goCategory(pCategory) {
    location.href="/pc/board/list?type=all&best=&category=" + pCategory + "&pageNum=0&keyword=all&content=";
}

function goType(pType) {
    location.href="/pc/board/list?type=" + pType + "&best=&category=" + category + "&pageNum=0&keyword=all&content=";
}

function goBest() {
    var pageNum = $("input[name=pageNum]").val();

    location.href="/pc/board/list?type=all&best=Y&category=" + category + "&pageNum=" + pageNum + "&keyword=all&content=";
}

function search() {
    var content = $(".search-input").val();

    location.href = "/pc/board/list?type=" + type + "&best=&category=" + category + "&pageNum=0&keyword=all&content=" + content;
}

function goList() {
    location.href = "/pc/board/list?" + makeQueryUrl();
}

function goRegister() {
    location.href = "/pc/board/register?" + makeQueryUrl();
}

function moveScrollToComment() {
    var paramCommentId = urlParams.get('commentId');
    if (!isEmpty(paramCommentId)) {
        urlParams.delete("commentId")
        var byId = 'comment_' + paramCommentId;
        const moveComment = document.getElementById(byId);
        window.scrollBy({top: moveComment.getBoundingClientRect().top, behavior: 'smooth'});

        var bc = $("#" + byId).css("background-color");
        $("#" + byId).css("background-color", "#FD8B8B");

        setTimeout(function () {
            $("#" + byId).css("background-color", bc);
        }, 3000);
    }
}

function likesBtn() {
    if (isRunning == true) {
        return;
    }

    var boardNum = $("input[name=boardNum]").val();

    $.ajax({
        type : 'post',
        url : '/board/likes',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            boardId : boardNum
        }),
        success : function (data) {
            $(".likes-cnt").text(data.likes);
            $(".view-recom").text(data.likes);

            if (data.isLikes) {
                $(".like-ico").addClass("on");
            } else {
                $(".like-ico").removeClass("on")
            }
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}


function getCommentList(page) {
    var boardNum = $("input[name=boardNum]").val();

    if (page < 0) {
        page = 0;
    }

    $.ajax({
        type : 'post',
        url : '/pc/board/search/comment',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            boardNum : boardNum,
            pageNum : page,
            pageSize : 20
        }),
        success : function (result) {
            setListHtml(result);
            moveScrollToComment();
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");

        },
        complete : function () {
            $(".div_load_image").css("display", "none");
        }
    });
}

function setListHtml(data) {
    var listHtml = "";
    var isLogin = $("input[name=isLogin]").val();
    var loginId = $("input[name=loginId]").val();

    $(data.list.content).each(function () {
        listHtml += "<div class='comment' id='comment_" + this.id + "' data-id='" + this.id + "' onclick='setCommentChidrenForm(" + this.id + ")'>";
        listHtml += "<div class='comment-info'>";
        if (this.user) {
            if (isLogin === 'true' && this.userId == loginId) {
                // 로그인 = 등록인
                listHtml += "   <div class='author'>" + this.author + "</div>";
                listHtml += "   <div class='right-area'>";
                listHtml += "       <div class='createDate'>" + getReplyTime(this.createDate) + "</div>";
                listHtml += "       <div class='x-btn' onclick='deleteCommentBtnByOwner(" + this.id + ")'></div>";
                listHtml += "   </div>";
            } else {
                // 유저 등록
                listHtml += "   <div class='author'>" + this.author + "</div>";
                listHtml += "   <div class='right-area'>";
                listHtml += "       <div class='createDate'>" + getReplyTime(this.createDate) + "</div>";
                listHtml += "   </div>";

            }
        } else {
            // 비회원 등록
            if (isLogin === 'true') {
                listHtml += "   <div class='author'>" + this.author + " (" + this.ip + ")</div>";
                listHtml += "   <div class='right-area'>";
                listHtml += "       <div class='createDate'>" + getReplyTime(this.createDate) + "</div>";
                listHtml += "   </div>";
            } else {
                // listHtml += "   <div class='x-btn' onclick='deleteCommentBtn(" + this.id + ")'></div>";
                listHtml += "   <div class='author'>" + this.author + " (" + this.ip + ")</div>";
                listHtml += "   <div class='right-area'>";
                listHtml += "       <div class='createDate'>" + getReplyTime(this.createDate) + "</div>";
                listHtml += "       <div class='x-btn' onclick='deleteCommentBtn(" + this.id + ")'></div>";
                listHtml += "   </div>";
            }
        }

        listHtml += "   </div>";
        listHtml += "   <div class='content'>" + this.content + "</div>";
        listHtml += "</div>";

        $(this.children).each(function (e) {
            listHtml += "<div class='comment add' id='comment_" + this.id + "'>";
            listHtml += "<div class='comment-info add'>";

            if (this.user) {
                if (isLogin === 'true' && this.userId == loginId) {
                    // 로그인 = 등록인
                    listHtml += "   <div class='left-area'>";
                    listHtml += "       <div class='comment-ico'></div>";
                    listHtml += "       <div class='author add'>" + this.author + "</div>";
                    listHtml += "   </div>";
                    listHtml += "   <div class='right-area'>";
                    listHtml += "       <div class='createDate add'>" + getReplyTime(this.createDate) + "</div>";
                    listHtml += "       <div class='x-btn'></div>";
                    listHtml += "   </div>";

                } else {
                    // 유저 등록
                    listHtml += "   <div class='left-area'>";
                    listHtml += "       <div class='comment-ico'></div>";
                    listHtml += "       <div class='author add'>" + this.author + "</div>";
                    listHtml += "   </div>";
                    listHtml += "   <div class='right-area'>";
                    listHtml += "       <div class='createDate add'>" + getReplyTime(this.createDate) + "</div>";
                    listHtml += "   </div>";
                }
            } else {
                // 비회원 등록
                if (isLogin === 'true') {
                    listHtml += "   <div class='left-area'>";
                    listHtml += "       <div class='comment-ico'></div>";
                    listHtml += "       <div class='author add'>" + this.author + " (" + this.ip + ")</div>";
                    listHtml += "   </div>";
                    listHtml += "   <div class='right-area'>";
                    listHtml += "       <div class='createDate add'>" + getReplyTime(this.createDate) + "</div>";
                    listHtml += "   </div>";
                } else {
                    listHtml += "   <div class='left-area'>";
                    listHtml += "       <div class='comment-ico'></div>";
                    listHtml += "       <div class='author add'>" + this.author + " (" + this.ip + ")</div>";
                    listHtml += "   </div>";
                    listHtml += "   <div class='right-area'>";
                    listHtml += "       <div class='createDate add'>" + getReplyTime(this.createDate) + "</div>";
                    listHtml += "       <div class='x-btn'></div>";
                    listHtml += "   </div>";
                }
            }
            listHtml += "   </div>";
            listHtml += "   <div class='content add'>" + this.content + "</div>";
            listHtml += "</div>";

        });
    });

    $(".comment-list").html(listHtml);

    var total_comment_cnt = data.totalCount;
    $(".comment-cnt").html(total_comment_cnt);
    $(".comment-count").text(total_comment_cnt);

    // comment paging
    var pageHtml = "";
    for (var i=data.pageDto.startPage; i < data.pageDto.endPage+1; i++) {
        if (data.pageDto.curPage == i) {
            pageHtml += "<div class='on' onclick='getCommentList(" + i + ")' data-page='" + i + "'>" + Number(i + 1) + "</div>";
        } else {
            pageHtml += "<div onclick='getCommentList(" + i + ")'>" + Number(i + 1) + "</div>";
        }
    }

    $("input[name=commentPage]").val(data.pageDto.endPage);

    $(".comment-paging").html(pageHtml);
}

function modifyBtn() {
    openModal('비밀번호를 입력하세요.', 'type3', function () {
        if (isRunning == true) {
            return;
        }

        var boardNum = $("input[name=boardNum]").val();
        var password = $("input[name=modal-password]").val();

        if (isEmpty(password)) {
            modalClose();

            alert("비밀번호를 입력해주세요.");
            return;
        }

        $.ajax({
            type : 'post',
            url : '/pc/board/check',
            headers : {
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : 'json',
            data : JSON.stringify({
                boardNum : boardNum,
                password : password,
                type: "modify"
            }),
            success : function (data) {
                if (data.result) {
                    location.href=data.redirect + "&" + makeQueryUrl();
                } else {
                    modalClose();
                    alert ("비밀번호가 틀렸습니다.");
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
            }
        });
    });
}

function modifyBtnByOwner() {
    openModal('수정 하시겠습니까?', 'type2', function () {
        if (isRunning == true) {
            return;
        }

        var boardNum = $("input[name=boardNum]").val();

        $.ajax({
            type : 'post',
            url : '/pc/board/modifyByOwner',
            headers : {
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : 'json',
            async: false,
            data : JSON.stringify({
                boardNum : boardNum
            }),
            success : function (data) {
                if (data.result) {
                    location.href=data.redirect + "&" + makeQueryUrl();
                } else {
                    modalClose();
                    alert ("비밀번호가 틀렸습니다.");
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
            }

        });
    });
}

/**
 * 북마크
 */
function setBookMark() {
    var boardNum = $("input[name=boardNum]").val();

    if (isRunning == true) {
        return;
    }

    $.ajax({
        type : 'post',
        url : '/pc/board/bookmark',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        async: false,
        data : JSON.stringify({
            board_id : boardNum
        }),
        success : function (data) {
            if (data.result) {
                if ($(".bookmark-ico").hasClass("off")) {
                    $(".bookmark-ico").removeClass("off");
                    $(".bookmark-ico").addClass("on");

                    openModal("해당 게시글을 즐겨찾기에 저장했습니다.", "type1", null);
                } else {
                    $(".bookmark-ico").removeClass("on");
                    $(".bookmark-ico").addClass("off");

                    openModal("해당 게시글을 즐겨찾기에서 삭제했습니다.", "type1", null);
                }
            }
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}

function deleteBtn() {
    openModal('비밀번호를 입력하세요.', 'type3', function () {
        if (isRunning == true) {
            return;
        }

        var boardNum = $("input[name=boardNum]").val();
        var password = $("input[name=modal-password]").val();

        if (isEmpty(password)) {
            modalClose();

            alert("비밀번호를 입력해주세요.");
            return;
        }

        $.ajax({
            type : 'post',
            url : '/board/check',
            headers : {
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : 'json',
            data : JSON.stringify({
                boardNum : boardNum,
                password : password,
                type: "delete"
            }),
            success : function (data) {
                if (data.result) {
                    goList();
                } else {
                    modalClose();
                    alert ("비밀번호가 틀렸습니다.");
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
            }

        });
    });
}

function deleteBtnByOwner() {
    openModal('삭제를 하시겠습니까?', 'type2', function () {
        if (isRunning == true) {
            return;
        }

        var boardNum = $("input[name=boardNum]").val();

        $.ajax({
            type : 'post',
            url : '/board/deleteByOwner',
            headers : {
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : 'json',
            data : JSON.stringify({
                boardNum : boardNum,
                type: "delete"
            }),
            success : function (data) {
                if (data.result) {
                    goList();
                } else {
                    modalClose();
                    alert ("비밀번호가 틀렸습니다.");
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
            }
        });
    });
}

/**
 * URL 공유 버튼
 */
function shareBtn() {
    var boardNum = $("input[name=boardNum]").val();
    var category = $("input[name=category]").val();
    var url = '';
    var textarea = document.createElement("textarea");
    document.body.appendChild(textarea);
    url = window.document.location.origin + "/board/view?boardNum=" + boardNum + "&type=all&best=&category=" + category + "&pageNum=0&keyword=all&content=";
    textarea.value = url;
    textarea.select();
    document.execCommand("copy");
    document.body.removeChild(textarea);
    openModal("URL이 복사되었습니다.", "type1", null);
}

/**
 * 댓글 등록 - 비회원
 */
function registerComment () {
    if (isRunning == true) {
        return;
    }

    var boardId = $("input[name=boardNum]").val();
    var nickname = $("input[name=nickname]").val();
    var commentPassword = $("input[name=commentPassword]").val();
    var commentContent = $("textarea[name=commentContent]").val();

    if (isEmpty(nickname) || lengthCheckUnder(nickname, 1)) {
        alert("닉네임을 입력해주세요.")
        return;
    }

    if (isEmpty(commentPassword)) {
        alert("비밀번호를 입력해주세요.")
        return;
    }

    if (lengthCheckUnder(commentPassword, 4)) {
        alert("비밀번호를 최소 4자리 이상 입력하셔야 합니다. 쉬운 비밀번호는 타인이 수정 또는 삭제하기 쉬우니, 어려운 비밀번호를 입력해 주세요.");
        return;
    }

    if (isEmpty(commentContent) || lengthCheckUnder(commentContent, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    $.ajax({
        type : 'post',
        url : '/pc/board/save/comment',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            author : nickname,
            password : commentPassword,
            content : commentContent,
            boardId : boardId
        }),
        success : function () {
            var commentPage = $("input[name=commentPage]").val();
            getCommentList(commentPage);

            $("input[name=nickname]").val("");
            $("input[name=commentPassword]").val("");
            $("textarea[name=commentContent]").val("");
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}

/**
 * 댓글 등록 - 회원
 */
function registerCommentByOwner () {
    if (isRunning == true) {
        return;
    }

    var boardId = $("input[name=boardNum]").val();
    var commentContent = $("textarea[name=commentContent]").val();

    if (isEmpty(commentContent) || lengthCheckUnder(commentContent, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    $.ajax({
        type : 'post',
        url : '/pc/board/save/comment',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            content : commentContent,
            boardId : boardId
        }),
        success : function () {
            var commentPage = $("input[name=commentPage]").val();
            getCommentList(commentPage);

            $("textarea[name=commentContent]").val("");
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}

function registerCommentToComment (parent_id) {
    if (isRunning == true) {
        return;
    }

    var boardId = $("input[name=boardNum]").val();
    var nickname = $("input[name=add-nickname]").val();
    var commentPassword = $("input[name=add-password]").val();
    var commentContent = $("textarea[name=add-commentContent]").val();

    if (isEmpty(nickname) || lengthCheckUnder(nickname, 1)) {
        alert("닉네임을 입력해주세요.")
        return;
    }

    if (isEmpty(commentPassword)) {
        alert("비밀번호를 입력해주세요.")
        return;
    }

    if (lengthCheckUnder(commentPassword, 4)) {
        alert("비밀번호를 최소 4자리 이상 입력하셔야 합니다. 쉬운 비밀번호는 타인이 수정 또는 삭제하기 쉬우니, 어려운 비밀번호를 입력해 주세요.");
        return;
    }

    if (isEmpty(commentContent) || lengthCheckUnder(commentContent, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    $.ajax({
        type : 'post',
        url : '/pc/board/save/comment',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            author : nickname,
            password : commentPassword,
            content : commentContent,
            boardId : boardId,
            parentId : parent_id
        }),
        success : function () {
            var commentPage = $(".comment-paging .on").data("page");
            getCommentList(commentPage);

            $(".add-comment-form").remove();
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}

function registerCommentToCommentByUser (parent_id) {
    if (isRunning == true) {
        return;
    }

    var boardId = $("input[name=boardNum]").val();
    var commentContent = $("textarea[name=add-commentContent]").val();
    var isLogin = $("input[name=isLogin]").val();

    if (isLogin === 'false') {
        alert("잘못된접근입니다.")
        return;
    }

    if (isEmpty(commentContent) || lengthCheckUnder(commentContent, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    $.ajax({
        type : 'post',
        url : '/pc/board/save/comment',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            content : commentContent,
            boardId : boardId,
            parentId : parent_id
        }),
        success : function () {
            var commentPage = $(".comment-paging .on").data("page");
            getCommentList(commentPage);

            $(".add-comment-form").remove();
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}

/**
 * 대 댓글 화면 추가
 * @param id (댓글 아이디)
 */
function setCommentChidrenForm(id) {
    if (id == $(".add-comment-form").data("id")) {
        $(".add-comment-form").remove();
        return;
    }

    $(".add-comment-form").remove();

    var isLogin = $("input[name=isLogin]").val();
    var username = $("input[name=loginName]").val();
    var strHtml = "";

    strHtml += "<div class='add-comment-form' data-id='" + id + "'>";
    if (isLogin === 'false') {
        strHtml += "    <div class='ipt'>";
        strHtml += "        <input type='text' class='ipt' name='add-nickname' placeholder='닉네임'> ";
        strHtml += "        <input type='password' class='ipt' name='add-password' placeholder='비밀번호'>";
        strHtml += "    </div>";
    } else {
        strHtml += "    <div class='ipt'>";
        strHtml += "        <input type='text' class='comment-user' placeholder='닉네임' value='" + username + "' readonly>";
        strHtml += "    </div>";
    }
    strHtml += "    <div class='comment-textarea'>";
    strHtml += "        <textarea name='add-commentContent' maxlength='200' placeholder='내용'></textarea>";
    strHtml += "    </div>";
    strHtml += "    <div class='btn register'>";
    if (isLogin === 'false') {
        strHtml += "        <button class='register-btn' onclick='registerCommentToComment(" + id + ")'>등록</button>";
    } else {
        // 로그인 버튼
        strHtml += "        <button class='register-btn' onclick='registerCommentToCommentByUser(" + id + ")'>등록</button>";
    }
    strHtml += "    </div>";
    strHtml += "</div>";

    $("#comment_" + id).after(strHtml);
}

function deleteCommentBtn(commentNum) {
    event.stopPropagation();

    openModal('비밀번호를 입력하세요.', 'type3', function () {
        if (isRunning == true) {
            return;
        }

        var password = $("input[name=modal-password]").val();

        if (isEmpty(password)) {
            modalClose();

            alert("비밀번호를 입력해주세요.");
            return;
        }

        $.ajax({
            type: 'post',
            url: '/pc/board/delete/comment',
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                commentNum: commentNum,
                password: password
            }),
            success: function (data) {
                if (data) {
                    var commentPage = $(".comment-paging .on").data("page");
                    getCommentList(commentPage);
                    modalClose();
                } else {
                    modalClose();

                    alert("비밀번호가 틀립니다.");
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
            }
        });
    });
}

function deleteCommentBtnByOwner(commentNum) {
    event.stopPropagation();

    openModal('댓글을 삭제하시겠습니까?', 'type2', function () {
        if (isRunning == true) {
            return;
        }

        $.ajax({
            type : 'post',
            url : '/pc/board/delete/comment',
            headers : {
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : 'json',
            async: false,
            data : JSON.stringify({
                commentNum : commentNum
            }),
            success : function (data) {
                if (data) {
                    var commentPage = $(".comment-paging .on").data("page");
                    getCommentList(commentPage);
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
                modalClose();
            }
        });
    });
}

function getBoardTime(timeValue) {
    var dateObj = new Date(timeValue);

    var year = dateObj.getFullYear();
    var month = dateObj.getMonth() + 1;
    var date = dateObj.getDate();
    var hours = ('0' + dateObj.getHours()).slice(-2);
    var minutes = ('0' + dateObj.getMinutes()).slice(-2);
    var seconds = ('0' + dateObj.getSeconds()).slice(-2);

    return year + "." + month + "." + date + " " + hours + ":" + minutes;
}

function getReplyTime(timeValue) {
    var dateObj = new Date(timeValue);
    var today = new Date();

    var tYear = today.getFullYear();
    var tMonth = today.getMonth() + 1;
    var tDate = today.getDate();
    var todayDate = tYear+tMonth+tDate;

    var year = dateObj.getFullYear();
    var month = dateObj.getMonth() + 1;
    var date = dateObj.getDate();
    var hours = ('0' + dateObj.getHours()).slice(-2);
    var minutes = ('0' + dateObj.getMinutes()).slice(-2);
    var seconds = ('0' + dateObj.getSeconds()).slice(-2);
    var boardDate = year+month+date;


    return month + '.' + date + " " + hours + ':' + minutes;
}

function getList(page) {
    var boardNum = $("input[name=boardNum]").val();
    var content = $("input[name=content]").val();
    var keyword = $("input[name=keyword]").val();

    $.ajax({
        type : 'post',
        url : '/pc/board/list',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            boardNum : boardNum,
            best : best,
            type : type,
            category : category,
            keyword: keyword,
            content: content,
            pageNum : page
        }),
        success : function (result) {
            setListHtmlIndex(result);
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
        }
    });
}

function setListHtmlIndex(data) {
    var boardNum = $("input[name=boardNum]").val();
    var listHtml = "";

    $(data.list.content).each(function () {
        if (this.id == boardNum) {
            listHtml += "<tr class='on'>";
        } else {
            listHtml += "<tr>";
        }

        listHtml += "   <td class='type'>" + this.typeName + "</td>";
        listHtml += "   <td class='title' onclick='goView(" + this.id + ")'>";
        listHtml += "       " + this.title + "<div class='comment-cnt'>" + this.commentCount + "</div>"
        listHtml += "   </td>";

        if (this.user) {
            listHtml += "   <td class='author'>" + this.username + "</td>";
        } else {
            listHtml += "   <td class='author'>" + this.author + "(" + this.ip + ")</td>";
        }

        listHtml += "   <td class='boardDate'>" + getBoardTime(this.createDate) + "</td>";
        listHtml += "   <td class='board-view'>" + this.view + "</td>";
        listHtml += "   <td class='likes'>" + this.likes + "</td>";

        listHtml += "</tr>";
    });

    $(".board-list").html(listHtml);

    setPagingBtn(data);
}

function setPagingBtn(data) {
    var pageHtml = "";
    pageHtml += "<ul>";

    // prev
    if (data.pageDto.startPage == 0) {
        pageHtml += "<li class='page-prev' onclick='getList(" + data.pageDto.startPage + ")'>&laquo;</li>"
    } else {
        pageHtml += "<li class='page-prev' onclick='getList(" + Number(data.pageDto.startPage) - 1 + ")'>&laquo;</li>"
    }

    // page
    for(var i = data.pageDto.startPage ; i < data.pageDto.endPage+1 ; i++) {
        if (data.pageDto.curPage == i) {
            pageHtml += "<li class='page active'>" + (i+1) + "</li>";
            $("input[name=subPageNum]").val(i);
        } else {
            pageHtml += "<li class='page' onclick='getList(" + i + ")'>"+ (i+1) + "</li>";
        }
    }

    // next
    if (data.pageDto.endPage == 0) {
        pageHtml += "<li class='page-next' onclick='getList(" + data.pageDto.endPage + ")'>&raquo;</li>";
    } else {
        pageHtml += "<li class='page-next' onclick='getList(" + Number(data.pageDto.endPage) + ")'>&raquo;</li>";
    }

    pageHtml += "</ul>";

    $(".paging").html(pageHtml);
}

function goView(id) {
    location.href = "/pc/board/view?boardNum=" + id + "&" + makeQueryUrl();
}