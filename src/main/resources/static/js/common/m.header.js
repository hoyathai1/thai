$(document).ready(function () {
    hUrlParams = new URL(location.href).searchParams;
    var path = window.location.pathname;
    var hCategory = hUrlParams.get('category');
    var hType = hUrlParams.get('type');
    var hBest = hUrlParams.get('best');

    if(!detectMobileDevice(window.navigator.userAgent)) {


        var hUrl = new URL(location.href);
        location.href="/pc" + hUrl.pathname + hUrl.search;
    }

    if (path != '/board/list') {
        $(".header-gnb").css("display", "none");
        $(".header-category").addClass("bottom-on");
    } else {
        $(".category-btn-register").css("display", "block");
    }

    if (hBest == 'Y') {
        $("#gnb-best").addClass("on");
    } else {
        $("#gnb-" + hType).addClass("on");
    }

    $(".category-log").css({"background":"url(/img/" + hCategory + ".png) no-repeat"});
    $(".category-log").css("background-size", "contain");
    $(".category-log").css("background-color", "transparent");

});

function detectMobileDevice(agent) {
    const mobileRegex = [
        /Android/i,
        /iPhone/i,
        /iPad/i,
        /iPod/i,
        /BlackBerry/i,
        /Windows Phone/i
    ]

    return mobileRegex.some(mobile => agent.match(mobile))
}

function goSignUp() {
    location.href="/signUp?" + makeQueryUrl();
}

function goLogin() {
    location.href="/login";
}

function goLogout() {
    location.href="/logout";
}

function goMain(category_id) {
    location.href="/board/list?type=all&best=N&category=" + category_id + "&pageNum=0";
}

function goMenu() {
    location.href="/menu"
}