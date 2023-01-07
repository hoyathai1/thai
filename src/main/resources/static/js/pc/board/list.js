const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const type = urlParams.get('type');

$(document).ready(function () {

    if(detectMobileDevice(window.navigator.userAgent)) {
        var hUrl = new URL(location.href);
        location.href = encodeURI("/board/list" + hUrl.search);
    }

    if (best == 'Y') {
        $("#best").addClass("on");
    } else {
        $("#" + type).addClass("on");
    }

});

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var content = $(".search-input").val();
    var keyword = $('.search-select').val();

    return encodeURI("type=" + type + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&keyword=" + keyword + "&content=" + content);
}

function goCategory(pCategory) {
    location.href="/pc/board/list?type=all&best=&category=" + pCategory + "&pageNum=0&keyword=all&content=";
}

function goType(pType) {
    location.href="/pc/board/list?type=" + pType + "&best=&category=" + category + "&pageNum=0&keyword=all&content=";
}

function goBest() {
    var pageNum = $("input[name=pageNum]").val();

    location.href = encodeURI("/pc/board/list?type=all&best=Y&category=" + category + "&pageNum=" + pageNum + "&keyword=all&content=");
}

function search() {
    var content = $(".search-input").val();

    location.href = encodeURI("/pc/board/list?type=" + type + "&best=&category=" + category + "&pageNum=0&keyword=all&content=" + content);
}

function searchForKeyword() {
    var content = $("#bottomSearch").val();
    var keyword = $(".search-select").val();

    location.href = encodeURI("/pc/board/list?type=" + type + "&best=&category=" + category + "&pageNum=0&keyword=" + keyword + "&content=" + content);
}

function movePage(page) {
    var keyword = $('.search-select').val();
    var content = $('.search-input').val();

    location.href = encodeURI("/pc/board/list?type=" + type + "&best=&category=" + category + "&pageNum=" + page + "&keyword=" + keyword + "&content=" + content);
}

function goNotice() {
    location.href = "/pc/menu/notice?" + makeQueryUrl();
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
