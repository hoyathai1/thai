const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const type = urlParams.get('type');

var isRunning = false;
var currentCommentPage = 0;

$(document).ready(function () {

    if(detectMobileDevice(window.navigator.userAgent)) {
        var hUrl = new URL(location.href);
        location.href = encodeURI("/board/inform" + hUrl.search);
    }

    if (best == 'Y') {
        $("#best").addClass("on");
    } else {
        $("#" + type).addClass("on");
    }

    $(".createDate").html(getBoardTime($(".createDate").html()));

    getList(0);
});

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var content = $(".search-input").val();
    var keyword = $("input[name=keyword]").val();

    return encodeURI("type=" + type + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&keyword=" + keyword + "&content=" + content);
}

function goNotice() {
    location.href = "/pc/menu/notice?" + makeQueryUrl();
}

function goSignUp() {
    location.href = "/pc/signUp?" + makeQueryUrl();
}

function goLogin() {
    location.href = "/pc/login";
}

function goLogout() {
    location.href = "/logout";
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
    location.href = encodeURI("/pc/board/list?type=all&best=&category=" + pCategory + "&pageNum=0&keyword=all&content=");
}

function goType(pType) {
    location.href = encodeURI("/pc/board/list?type=" + pType + "&best=&category=" + category + "&pageNum=0&keyword=all&content=");
}

function goBest() {
    var pageNum = $("input[name=pageNum]").val();

    location.href = encodeURI("/pc/board/list?type=all&best=Y&category=" + category + "&pageNum=" + pageNum + "&keyword=all&content=");
}

function search() {
    var content = $(".search-input").val();

    location.href = encodeURI("/pc/board/list?type=" + type + "&best=&category=" + category + "&pageNum=0&keyword=all&content=" + content);
}

function goList() {
    location.href = "/pc/board/list?" + makeQueryUrl();
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
        url : '/pc/board/informList',
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