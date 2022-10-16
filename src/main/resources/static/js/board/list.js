$(document).ready(function () {
    getList(0,15);
});

function getList(page, pageSize) {
    $.ajax({
        type : 'post',
        url : '/board/list',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
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
        listHtml += "<div class='board' data-id='" + this.id + "'>";
        listHtml += "   <div class='content'>";
        listHtml += "       <div class='title'>" + this.title + "</div>";
        listHtml += "       <div class='info'>";
        listHtml += "           <div>" + this.author + "</div>";
        listHtml += "           <div>" + getBoardTime(this.createDate) + "</div>";
        listHtml += "           <div class='view-count'>" + this.view + "</div>";
        listHtml += "       </div>";
        listHtml += "   </div>";
        listHtml += "   <div class='reply'>0</div>";
        listHtml += "</div>";

    });

    $(".board-list").append(listHtml)

    if (data.list.last != true) {
        $(".more-btn").remove();

        var page = Number(data.list.pageable.pageNumber) + 1;
        var pageSize = Number(15);

        var moreBtnHtml = "";
        moreBtnHtml += "<div class='more-btn' onclick='getList(" + page + "," + pageSize + ")'>";
        moreBtnHtml += "<div class='btn_img'></div>";
        moreBtnHtml += "</div>";

        $(".board-list").append(moreBtnHtml);
    } else {
        $(".more-btn").remove();
    }
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