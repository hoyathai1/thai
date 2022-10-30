$(document).ready(function () {
    var pageNum = $("input[name=pageNum]").val();
    var keyword = $(".search-select").val();
    var content = $(".search-input").val();
    getList(keyword, content, pageNum,15);
});

function getList(keyword, content, page, pageSize) {
    $.ajax({
        type : 'post',
        url : '/board/list',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            type : "thai",
            keyword: keyword,
            content: content,
            pageNum : page,
            pageSize : pageSize
        }),
        success : function (result) {
            setListHtml(result);
        }
    });
}

function setListHtml(data) {
    var listHtml = "";

    $(data.list.content).each(function () {
        listHtml += "<div class='board' data-id='" + this.id + "' onclick='goView(" + this.id + ")'>";
        listHtml += "   <div class='content'>";
        listHtml += "       <div class='title'>" + this.title + "</div>";
        listHtml += "       <div class='info'>";
        listHtml += "           <div>" + this.author + "</div>";
        listHtml += "           <div>" + getBoardTime(this.createDate) + "</div>";
        listHtml += "           <div class='view-count'>" + this.view + "</div>";
        listHtml += "       </div>";
        listHtml += "   </div>";
        listHtml += "   <div class='reply'>" + this.commentCount + "</div>";
        listHtml += "</div>";

    });

    $(".board-list").append(listHtml)

    //  더보기 버튼
    if (data.list.last != true) {
        $(".more-btn").remove();

        var page = Number(data.list.pageable.pageNumber) + 1;
        var pageSize = Number(15);

        var moreBtnHtml = "";
        moreBtnHtml += "<div class='more-btn' onclick='getList(\"" + data.search.keyword + "\",\"" + data.search.content + "\", " + page + "," + pageSize + ")'>";
        moreBtnHtml += "<div class='btn_img'></div>";
        moreBtnHtml += "</div>";

        $(".board-list").append(moreBtnHtml);
    } else {
        $(".more-btn").remove();
    }

    // 페이징 버튼
    setPagingBtn(data)

    // 검색창 값 세팅
    setSearch(data);
}

function setSearch(data) {
    var content = data.search.content;
    if (!(typeof content == "undefined" || content == "" || content == null)) {
        $(".search-select").val(data.search.keyword).prop("selected", true);
        $(".search-input").val(data.search.content);
    }
}

function setPagingBtn(data) {
    var pageHtml = "";

    // prev
    pageHtml += "<div class='page-prev' onclick='movePage(\"prev\", " + data.pageDto.startPage + ")'>";
    pageHtml += "<";
    pageHtml += "</div>";

    // page
    pageHtml += "<div class='page-number'>";
    for(var i = data.pageDto.startPage ; i < data.pageDto.endPage+1 ; i++) {
        if (data.pageDto.curPage == i) {
            pageHtml += "<div class='on' onclick='movePage(\"page\", " + i + ")'>" + Number(i + 1) + "</div>";
        } else {
            pageHtml += "<div onclick='movePage(\"page\", " + i + ")'>" + Number(i + 1) + "</div>";
        }
    }
    pageHtml += "</div>";

    // next
    pageHtml += "<div class='page-next' onclick='movePage(\"next\", " + data.pageDto.endPage + ")'>";
    pageHtml += ">";
    pageHtml += "</div>";

    // move
    pageHtml += "<div class='page-move'>";
    pageHtml += ">>";
    pageHtml += "</div>";


    $(".board-paging").html(pageHtml);
}

function movePage(type, page) {
    var pageSize = Number(15);
    var keyword = $('.search-select').val();
    var content = $('.search-input').val();

    init();
    getList(keyword, content, page, pageSize);

    $('input[name=pageNum]').val(page);
}

function search() {
    var keyword = $(".search-select").val();
    var content = $(".search-input").val();

    location.href = "/board/list?pageNum=0&keyword=" + keyword + "&content=" + content;
}

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var keyword = $(".search-select").val();
    var content = $(".search-input").val();

    return "pageNum=" + pageNum + "&keyword=" + keyword + "&content=" + content;
}

function goView(id) {
    location.href = "/board/view?boardNum=" + id + "&" + makeQueryUrl();
}

function goRegister() {
    location.href = "/board/register?" + makeQueryUrl();
}

function init() {
    $(".board-list").html("");
    $(".board-paging").html("");
}

function getBoardTime(timeValue) {
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

    if (todayDate == boardDate) {
        return hours + ':' + minutes;
    } else {
        return month + '.' + date;
    }
}
