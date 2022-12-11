function search(page, pageSize) {
    location.href="/admin/image/list?pageNum=" + page + "&pageSize=" + pageSize;
}

function gePage(page) {
    var pageSize = $("input[name=pageSize]").val();
    location.href="/admin/image/list?pageNum=" + page + "&pageSize=" + pageSize;
}

function refresh() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();

    location.href="/admin/image/list?pageNum=" + pageNum + "&pageSize=" + pageSize;
}

function changePageSize(pageSize) {
    location.href="/admin/image/list?pageNum=0&pageSize=" + pageSize;
}

function imageModal(id) {
    $("#view-modal").data("id", id);

    var imgSrc = $("#img-" + id)[0].src;
    $("#img-detail").attr("src", imgSrc);

    if ($("#img-" + id).hasClass("del")) {
        $("#delImgBtn").css("display", "none");
        $("#resImgBtn").css("display", "block");
    } else {
        $("#delImgBtn").css("display", "block");
        $("#resImgBtn").css("display", "none");
    }

    $(".modal").css("display", "block");
    $(".modal").addClass("show");

    event.preventDefault();
}

function closeModal() {
    $(".modal").css("display", "none");
    $(".modal").removeClass("show");
}

function deleteImage() {
    var id = $("#view-modal").data("id");

    $.ajax({
        type : 'post',
        url : '/admin/image/delete',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            imageId : id
        }),
        success : function () {
            $("#img-" + id).addClass("del");
            closeModal();
        }, error : function () {
        }
    });
}

function restoreImage() {
    var id = $("#view-modal").data("id");

    $.ajax({
        type : 'post',
        url : '/admin/image/restore',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            imageId : id
        }),
        success : function () {
            // var pageNum = $("input[name=pageNum]").val();
            // var pageSize = $("input[name=pageSize]").val();
            // search(pageNum, pageSize);

            $("#img-" + id).removeClass("del");
            closeModal();
        }, error : function () {
        }
    });
}

