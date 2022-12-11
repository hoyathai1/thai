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
    var keyword = $('.search-select').val();

    return "type=" + type + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=" + keyword + "&content=" + content;
}

function goCategory(pCategory) {
    var pageSize = $("input[name=pageSize]").val();

    location.href="/pc/board/list?type=all&best=&category=" + pCategory + "&pageNum=0&pageSize=" + pageSize + "&keyword=all&content=";
}

function goType(pType) {
    var pageSize = $("input[name=pageSize]").val();

    location.href="/pc/board/list?type=" + pType + "&best=&category=" + category + "&pageNum=0&pageSize=" + pageSize + "&keyword=all&content=";
}

function goBest() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();

    location.href="/pc/board/list?type=all&best=Y&category=" + category + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=all&content=";
}

function search() {
    var content = $(".search-input").val();

    location.href = "/pc/board/list?type=" + type + "&best=&category=" + category + "&pageNum=0&keyword=all&content=" + content;
}

function searchForKeyword() {
    var content = $("#bottomSearch").val();
    var keyword = $(".search-select").val();

    location.href = "/pc/board/list?type=" + type + "&best=&category=" + category + "&pageNum=0&keyword=" + keyword + "&content=" + content;
}

function movePage(page) {
    var pageSize = $("input[name=pageSize]").val();
    var keyword = $('.search-select').val();
    var content = $('.search-input').val();

    location.href = "/pc/board/list?type=" + type + "&best=&category=" + category + "&pageNum=" + page + "&pageSize=" + pageSize + "&keyword=" + keyword + "&content=" + content;
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

function goView(id) {
    location.href = "/pc/board/view?boardNum=" + id + "&" + makeQueryUrl();
}

function goinform(id) {
    location.href = "/pc/board/inform?boardNum=" + id + "&" + makeQueryUrl();
}

function goRegister() {
    location.href = "/pc/board/register?" + makeQueryUrl();
}