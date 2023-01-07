const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const bType = urlParams.get('type');

var rightBanner = "";
var isRightShow = "";
var rightIndex = 0;

$(document).ready(function () {
    $(".createDate").html(getBoardTime($(".createDate").html()));

    getList(0);

    isRightShow = $("input[name=rightBannerShow]").val();
    if (isRightShow == 'true') {
        var bannerLink = $("input[name=rightBannerLink]").val();
        var bannerUrl = $("input[name=rightBannerUrl]").val();
        var bannerBoard = $("input[name=rightBannerBoard]").val();
        rightBanner += "<div class='board listBanner' style='background: url(" + bannerUrl + ") no-repeat; background-size: contain; background-position: center;' onclick='clickBanner(\"" + bannerLink + "\", \"" + bannerBoard + "\")'></div>";
    }
});

function goList() {
    location.href = "/board/list?" + makeQueryUrl();
}

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();
    var keyword = $("input[name=keyword]").val();
    var content = $("input[name=content]").val();

    return encodeURI("type=" + bType + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=" + keyword + "&content=" + content);
}

function getBoardTime(timeValue) {
    var dateObj = new Date(timeValue);

    var year = dateObj.getFullYear();
    var month = dateObj.getMonth();
    var date = dateObj.getDate();
    var hours = ('0' + dateObj.getHours()).slice(-2);
    var minutes = ('0' + dateObj.getMinutes()).slice(-2);
    var seconds = ('0' + dateObj.getSeconds()).slice(-2);

    return year + "." + month + "." + date + " " + hours + ":" + minutes;
}

function getList(page) {
    var content = $("input[name=content]").val();
    var keyword = $("input[name=keyword]").val();

    $.ajax({
        type : 'post',
        url : '/board/detailInformList',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            best : best,
            type : bType,
            category : category,
            keyword: keyword,
            content: content,
            pageNum : page
        }),
        success : function (result) {
            setListHtmlForBoard(result);
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
        }
    });
}

function setListHtmlForBoard(data) {
    var listHtml = "";

    $(data.list.content).each(function () {
        listHtml += "<div class='board' data-id='" + this.id + "' onclick='goView(" + this.id + ")'>";
        listHtml += "   <div class='content'>";
        listHtml += "       <div class='title'>" + this.title + "</div>";
        listHtml += "       <div class='info'>";

        if (this.user) {
            listHtml += "           <div><b>" + this.username + "</b></div>";
        } else {
            listHtml += "           <div>" + this.author + "(" + this.ip + ")</div>";
        }

        listHtml += "           <div>" + getBoardTime(this.createDate) + "</div>";
        listHtml += "           <div class='view-count'>" + this.view + "</div>";
        listHtml += "           <div class='likes-count'>" + this.likes + "</div>";
        listHtml += "       </div>";
        listHtml += "   </div>";
        listHtml += "   <div class='reply'>" + this.commentCount + "</div>";
        listHtml += "</div>";

        if (rightIndex == 15 && isRightShow == 'true') {
            listHtml += rightBanner;
            rightIndex=0;
        }

        rightIndex++;
    });

    $(".board-list").append(listHtml);

    //  더보기 버튼
    if (data.list.last != true) {
        $(".more-btn").remove();

        var page = Number(data.list.pageable.pageNumber) + 1;
        var pageSize = Number(15);

        var moreBtnHtml = "";
        moreBtnHtml += "<div class='more-btn' onclick='getList(" + page + ")'>";
        moreBtnHtml += "<div class='btn_img'></div>";
        moreBtnHtml += "</div>";

        $(".board-list").append(moreBtnHtml);
    } else {
        $(".more-btn").remove();
    }
}