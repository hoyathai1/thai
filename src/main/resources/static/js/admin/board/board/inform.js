var isAjax = false;

const editor = document.getElementById('editor');
const btnBold = document.getElementById('btn-bold');
const btnItalic = document.getElementById('btn-italic');
const btnUnderline = document.getElementById('btn-underline');
const btnStrike = document.getElementById('btn-strike');
const btnImage = document.getElementById('btn-img');
const imageSelector = document.getElementById('img-selector');

function search(page) {
    var content = $("input[name=table_search]").val();

    if (!isEmpty(content)) {
        location.href="/admin/board/inform/list?pageNum=" + page + "&content="+content;
    } else {
        location.href="/admin/board/inform/list?pageNum=" + page;
    }
}

function focusEditor() {
    editor.focus({preventScroll: true});
}
btnBold.addEventListener('click', function () {
    setStyle('bold');
});

btnItalic.addEventListener('click', function () {
    setStyle('italic');
});

btnUnderline.addEventListener('click', function () {
    setStyle('underline');
});

btnStrike.addEventListener('click', function () {
    setStyle('strikeThrough')
});

btnImage.addEventListener('click', function () {
    imageSelector.click();
});

imageSelector.addEventListener('change', function (e) {
    const files = e.target.files;
    var size = files[0].size || files[0].fileSize;

    if (!!files) {
        for (var i=0; i < files.length; i++) {
            insertImageDate(files[i]);
        }
    }
});

function insertImageDate(file) {
    const reader = new FileReader();

    if (file.size < 200000) {
        reader.addEventListener('load', function (e) {
            focusEditor();
            document.execCommand('insertImage', false, `${reader.result}`);
        });

        reader.readAsDataURL(file);
    } else {
        reader.onload = (base64) => {
            const image = new Image();

            image.src = base64.target.result;

            image.onload = (e) => {
                const $canvas = document.createElement('canvas');
                const ctx = $canvas.getContext('2d');

                $canvas.width = e.target.width;
                $canvas.height = e.target.height;

                ctx.drawImage(e.target, 0, 0);

                // 용량이 줄어든 base64 이미지
                focusEditor();
                document.execCommand('insertImage', false, $canvas.toDataURL('image/jpeg', 0.2));
            }

        };

        reader.readAsDataURL(file);
    }
}

function setStyle(style) {
    document.execCommand(style);
    focusEditor();
    checkStyle();
}

function isStyle(style) {
    return document.queryCommandState(style);
}

function checkStyle() {
    if (isStyle('bold')) {
        btnBold.classList.add('active');
    } else {
        btnBold.classList.remove('active');
    }
    if (isStyle('italic')) {
        btnItalic.classList.add('active');
    } else {
        btnItalic.classList.remove('active');
    }
    if (isStyle('underline')) {
        btnUnderline.classList.add('active');
    } else {
        btnUnderline.classList.remove('active');
    }
    if (isStyle('strikeThrough')) {
        btnStrike.classList.add('active');
    } else {
        btnStrike.classList.remove('active');
    }
}

function btnRegister() {
    if (isAjax == true) {
        return;
    }

    var title = $("input[name=title]").val();
    var contents = $("#editor").html();
    var cType = $("select[name=c-type]").val();
    var bType = $("select[name=b-type]").val();
    var contentsTxt = "";

    if (isEmpty(title) || lengthCheckUnder(title, 1)) {
        alert("제목을 입력해주세요.")
        return;
    }

    if (isEmpty(contents) || lengthCheckUnder(title, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    if ($("#editor img").length > 10) {
        alert("이미지는 최대 10개까지 등록 가능합니다.")
        return;
    }

    contentsTxt = $("#editor").text();

    isAjax = true;
    imgToFile();
    contents = $("#editor").html();

    $.ajax({
        type : 'post',
        url : '/admin/board/inform/register',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        async: false,
        data : JSON.stringify({
            title: title,
            contents : contents,
            category: cType,
            type: bType
        }),
        success : function (data) {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        },
        error : function () {

        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isAjax = false;
        }
    });
}

function btnModify() {
    if (isAjax == true) {
        return;
    }

    var id = $("#register-modal").data("id");
    var title = $("input[name=title]").val();
    var contents = $("#editor").html();
    var cType = $("select[name=c-type]").val();
    var bType = $("select[name=b-type]").val();
    var contentsTxt = "";

    if (isEmpty(title) || lengthCheckUnder(title, 1)) {
        alert("제목을 입력해주세요.")
        return;
    }

    if (isEmpty(contents) || lengthCheckUnder(title, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    if ($("#editor img").length > 10) {
        alert("이미지는 최대 10개까지 등록 가능합니다.")
        return;
    }

    contentsTxt = $("#editor").text();

    isAjax = true;
    imgToFile();
    contents = $("#editor").html();

    $.ajax({
        type : 'post',
        url : '/admin/board/inform/modify',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        async: false,
        data : JSON.stringify({
            id : id,
            title: title,
            contents : contents,
            category: cType,
            type: bType
        }),
        success : function (data) {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        },
        error : function () {

        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isAjax = false;
        }
    });
}

function imgToFile() {
    var formData = new FormData();
    var img = $("#editor img");

    // 업로드 이미지 없으면
    if (img.length == 0) {
        return;
    }

    $(".div_load_image").css("display", "block");

    var modifyImgCount = 0;
    for (var i = 0; i < img.length; i++) {
        if ('uploaded' == $("#editor img")[i].className) {
            var imgArr = $("#editor img")[i].src.split("/");
            var imgName = imgArr[imgArr.length-1];
            $("#editor img")[i].src = imgName;
        } else {
            var bstr = atob(img[i].src.split(",")[1]);
            var n = bstr.length;
            var u8arr = new Uint8Array(n);

            while(n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }

            var fileName = getFileNameDate() + ".jpg"
            var file = new File([u8arr], fileName, {type:"image/jpeg"});

            formData.append("uploadFile", file);

            $("#editor img")[i].src = fileName;
            $("#editor img")[i].className="uploaded";
            modifyImgCount += 1;
        }
    }

    if (modifyImgCount > 0) {
        $.ajax({
            type : 'post',
            url : '/board/upload',
            async: false,
            processData: false,
            contentType: false,
            dataType : 'text',
            data : formData,
            success : function () {

            },
            beforeSend : function () {

            }
        });
    }
}

function getFileNameDate() {
    var today = new Date();

    var tYear = today.getFullYear().toString();
    var tMonth = today.getMonth().toString();
    var tDate = today.getHours().toString()
    var tHour = today.getDay().toString();
    var tMin = today.getMinutes().toString();
    var tSec = today.getSeconds().toString();
    var tMil = today.getMilliseconds().toString()
    var todayDate = tYear + tMonth + tDate + tHour + tMin + tSec + tMil;

    return todayDate;
}

function getCategoryList() {
    $.ajax({
        type : 'post',
        url : '/admin/board/inform/category',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        async: false,
        data : JSON.stringify({

        }),
        success : function (data) {
            var categoryHtml = "";

            for (var i=0;i<data.list.length;i++) {
                categoryHtml += "<option value='" + data.list[i].id + "'>" + data.list[i].name + "</option>";
            }

            $("select[name=c-type]").html(categoryHtml);
        },
        error : function () {

        },
        complete : function () {
        }
    });
}

function getBoardTypeList() {
    var category = $("select[name=c-type]").val();
    $.ajax({
        type : 'post',
        url : '/admin/board/inform/type',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        async: false,
        data : JSON.stringify({
            category : category
        }),
        success : function (data) {
            var typeHtml = "";

            for (var i=0;i<data.list.length;i++) {
                typeHtml += "<option value='" + data.list[i].type + "'>" + data.list[i].name + "</option>";
            }

            $("select[name=b-type]").html(typeHtml);
        },
        error : function () {

        },
        complete : function () {
        }
    });
}

function deleteInform(id) {
    $.ajax({
        type : 'post',
        url : '/admin/board/inform/delete',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            boardNum : id
        }),
        success : function () {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }, error : function () {
        }
    });

    event.stopPropagation();
}

function restoreInform(id) {
    $.ajax({
        type : 'post',
        url : '/admin/board/inform/restore',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            boardNum : id
        }),
        success : function () {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }, error : function () {
        }
    });

    event.stopPropagation();
}

function getDetail(id) {
    $.ajax({
        type : 'post',
        url : '/admin/board/inform/detail',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        async: false,
        data : JSON.stringify({
            boardNum : id
        }),
        success : function (data) {
            getCategoryList();
            $("select[name=c-type]").val(data.inform.category).prop("selected", true);
            getBoardTypeList();
            $("select[name=b-type]").val(data.inform.type).prop("selected", true);
            $("input[name=view-tiyle]").val(data.inform.title);
            $("#view-editor").html(data.inform.contents);
        },
        error : function () {

        },
        complete : function () {
        }
    });
}

function getDetailForModify(id) {
    $.ajax({
        type : 'post',
        url : '/admin/board/inform/detail',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        async: false,
        data : JSON.stringify({
            boardNum : id
        }),
        success : function (data) {
            getCategoryList();
            $("select[name=c-type]").val(data.inform.category).prop("selected", true);
            getBoardTypeList();
            $("select[name=b-type]").val(data.inform.type).prop("selected", true);

            $("input[name=title]").val(data.inform.title);
            $("#editor").html(data.inform.contents);
        },
        error : function () {

        },
        complete : function () {
        }
    });
}

function initModal() {
    $("select[name=c-type]").html("");
    $("select[name=b-type]").html("");
    $("input[name=title]").val("");
    $("#editor").html("");
}

function closeModal() {
    $(".modal").css("display", "none");
    $(".modal").removeClass("show");
}

function registerModal() {
    initModal();

    $("#register-btn").css("display", "block");
    $("#modify-btn").css("display", "none");

    getCategoryList();
    getBoardTypeList();
    $("#register-modal").css("display", "block");
    $("#register-modal").addClass("show");
}

function viewModal(id) {
    initModal();

    getDetail(id);
    $("#view-modal").css("display", "block");
    $("#view-modal").addClass("show");
}

function modifyModal(id) {
    initModal();

    $("#modify-btn").css("display", "block");
    $("#register-btn").css("display", "none");

    getDetailForModify(id);

    $("#register-modal").data("id", id);
    $("#register-modal").css("display", "block");
    $("#register-modal").addClass("show");
    event.stopPropagation();
}