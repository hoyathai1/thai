var isAjax = false;
var isValidId = false;
var isValidName = false;

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
        location.href="/admin/board/board/list?pageNum=" + page + "&content="+content;
    } else {
        location.href="/admin/board/board/list?pageNum=" + page;
    }
}

function modify() {
    if (isAjax == true) {
        return;
    }
    var id = $("#board-modal").data("id");
    var title = $("input[name=title]").val();
    var type = $(".type-select").val();
    var contents = $("#editor").html();
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
        url : '/admin/board/board/modify',
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
            contentsTxt : contentsTxt,
            type: type
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

function deleteBoard(boardId) {
    $.ajax({
        type : 'post',
        url : '/admin/board/board/delete',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            boardNum : boardId
        }),
        success : function () {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }, error : function () {
        }
    });

    event.stopPropagation();
}

function restoreBoard(boardId) {
    $.ajax({
        type : 'post',
        url : '/admin/board/board/restore',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            boardNum : boardId
        }),
        success : function () {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }, error : function () {
        }
    });

    event.stopPropagation();
}

function getType(category) {
    $.ajax({
        type : 'post',
        url : '/category/type',
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
            $(".type-select").html("");
            var optionHtml = "";

            for (var i = 0; i < data.list.length; i++) {
                optionHtml += "<option value='"+data.list[i].type+"'>" + data.list[i].name + "</option>";
            }

            $(".type-select").append(optionHtml);
        }, error : function () {
        }
    });
}

 function getContent(boardId) {
     $.ajax({
         type : 'post',
         url : '/admin/board/board/detail',
         headers : {
             "Content-Type" : "application/json",
             "X-HTTP-Method-Override" : "POST"
         },
         dataType : 'json',
         async: false,
         data : JSON.stringify({
             boardNum : boardId
         }),
         success : function (data) {
            $("#editor").html(data.contents);
         }, error : function () {
         }
     });
 }


 function getContentForView(boardId) {
     $.ajax({
         type : 'post',
         url : '/admin/board/board/detail',
         headers : {
             "Content-Type" : "application/json",
             "X-HTTP-Method-Override" : "POST"
         },
         dataType : 'json',
         async: false,
         data : JSON.stringify({
             boardNum : boardId
         }),
         success : function (data) {
             $("#view-editor").html(data.contents);
         }, error : function () {
         }
     });
 }

 function view(boardId) {
     var title = $("#title-" + boardId).text();
     $("#view-title").val(title);
     getContentForView(boardId);

     $("#view-modal").css("display", "block");
     $("#view-modal").addClass("show");
 }

function boardModal(boardId, category, type) {
    getType(category);
    getContent(boardId);
    var title = $("#title-" + boardId).text();
    $("input[name=title]").val(title);

    $(".type-select").val(type).prop("selected", true);

    $("#board-modal").css("display", "block");
    $("#board-modal").addClass("show");
    $("#board-modal").data("id", boardId);

    event.stopPropagation();
}

function closeModal() {
    $(".modal").css("display", "none");
    $(".modal").removeClass("show");
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
    if (file.size < 50000) {
        const reader = new FileReader();
        reader.addEventListener('load', function (e) {
            focusEditor();
            document.execCommand('insertImage', false, `${reader.result}`);
        });

        reader.readAsDataURL(file);
    } else if (file.size < 200000) {
        resizeImage(file, 0.9, 850);
    } else if (file.size < 500000) {
        resizeImage(file, 0.9, 850);
    } else if (file.size < 1000000) {
        resizeImage(file, 0.8, 850);
    } else if (file.size < 2000000) {
        resizeImage(file, 0.7, 850);
    } else if (file.size < 5000000) {
        resizeImage(file, 0.6, 850);
    } else {
        resizeImage(file, 0.5, 850);
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
