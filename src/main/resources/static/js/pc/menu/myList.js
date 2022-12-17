const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const type = urlParams.get('type');

$(document).ready(function () {

    if (best == 'Y') {
        $("#best").addClass("on");
    } else {
        $("#" + type).addClass("on");
    }

});

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();
    var content = $(".search-input").val();
    var keyword = $("input[name=keyword]").val();

    return "type=" + type + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=" + keyword + "&content=" + content;
}

function goCategory(pCategory) {
    var pageSize = $("input[name=pageSize]").val();

    location.href="/pc/board/list?type=all&best=&category=" + pCategory + "&pageNum=0&pageSize=" + pageSize + "&keyword=all&content=";
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
    location.href = "/pc/menu/account?" + makeQueryUrl();
}

function goMyList() {
    location.href = "/pc/menu/myList";
}

function goMyComment() {
    location.href = "/pc/menu/myComment";
}

function goOriginal(boardId) {
    location.href = "/pc/board/view?boardNum=62&index=4&type=all&best=&category=thai&pageNum=0&pageSize=30&keyword=all&content=";
}

function onLikes(val) {

    if ($("input[name=likes]").val() == val) {
        $("input[name=likes]").val(0);
        $(".likes").removeClass("on");
    } else {
        $("input[name=likes]").val(val);
        $(".likes").removeClass("on");
        $("#likes" + val).addClass("on");
    }
}

function onComment(val) {
    if ($("input[name=comment]").val() == val) {
        $("input[name=comment]").val(0);
        $(".comment").removeClass("on");
    } else {
        $("input[name=comment]").val(val);
        $(".comment").removeClass("on");
        $("#comment" + val).addClass("on");
    }

}

function initSearch() {
    $(".likes").removeClass("on");
    $("input[name=likes]").val(0);

    $(".comment").removeClass("on");
    $("input[name=comment]").val(0);
}

function search() {
    var category = $("#category-filter-select option:selected").val();
    var likes = $("input[name=likes]").val();
    var comment = $("input[name=comment]").val();
    var keyword = $("#keyword option:selected").val();
    var content = $(".search-input").val();

    location.href = "/pc/menu/myList?category=" + category + "&likes=" + likes + "&comment=" + comment + "&keyword=" + keyword + "&content=" + content;
}

function movePage(page) {
    var category = $("#category-filter-select option:selected").val();
    var likes = $("input[name=likes]").val();
    var comment = $("input[name=comment]").val();
    var keyword = $("#keyword option:selected").val();
    var content = $(".search-input").val();

    location.href = "/pc/menu/myList?pageNum=" + page + "&category=" + category + "&likes=" + likes + "&comment=" + comment + "&keyword=" + keyword + "&content=" + content;
}

function goView(boardId) {
    var pageNum = $("input[name=pageNum]").val();
    var category = $("#category-filter-select option:selected").val();
    var likes = $("input[name=likes]").val();
    var comment = $("input[name=comment]").val();
    var keyword = $("#keyword option:selected").val();
    var content = $(".search-input").val();

    location.href = "/pc/menu/myView?boardNum=" + boardId + "&pageNum=" + pageNum + "&category=" + category + "&likes=" + likes + "&comment=" + comment + "&keyword=" + keyword + "&content=" + content;
}