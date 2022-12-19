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
        location.href="/admin/board/notice/list?page=" + page + "&content="+content;
    } else {
        location.href="/admin/board/notice/list?page=" + page;
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

function modify() {
    var id = $("#notice-modal").data("id");
    var title = $("input[name=title]").val();
    var contents = $("#editor").html();
    var category = $(".register-select").val();
    var type = $(".type-select").val();
    var contentsTxt = "";

    if (isEmpty(title) || lengthCheckUnder(title, 1)) {
        alert("제목을 입력해주세요.");
        return;
    }

    if (isEmpty(contents) || lengthCheckUnder(title, 1)) {
        alert("내을 입력해주세요.");
        return;
    }

    contentsTxt = $("#editor").text();

    $.ajax({
        type : 'post',
        url : '/admin/board/notice/modify',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            id : id,
            title : title,
            type : type,
            contents : contents,
            contentsTxt : contentsTxt,
            category: category
        }),
        success : function () {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }, error : function () {
        }
    });
}

function register() {
    var title = $("input[name=title]").val();
    var type = $(".type-select").val();
    var contents = $("#editor").html();
    var category = $(".register-select").val();
    var contentsTxt = "";

    if (isEmpty(title) || lengthCheckUnder(title, 1)) {
        alert("제목을 입력해주세요.")
        return;
    }

    if (isEmpty(contents) || lengthCheckUnder(title, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    contentsTxt = $("#editor").text();

    $.ajax({
        type : 'post',
        url : '/admin/board/notice/register',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            title : title,
            type : type,
            contents : contents,
            contentsTxt : contentsTxt,
            category: category
        }),
        success : function () {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }, error : function () {
        }
    });
}

function active(id, active) {
    $.ajax({
        type : 'post',
        url : '/admin/board/notice/active',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            id : id,
            del: active
        }),
        success : function () {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }, error : function () {
        }
    });
}

function modifyModal(id) {
    $("#notice-modal").css("display", "block");
    $("#notice-modal").addClass("show");

    $("#notice-modal").data("id", id);

    var titleTxt = $("#notice-title-" + id).text();
    $("input[name=title]").val(titleTxt);

    var categoryId = $("#notice-category-" + id).data("id");
    $(".register-select").val(categoryId).prop("selected", true);

    var typeId = $("#notice-type-" + id).data("id");
    $(".type-select").val(typeId).prop("selected", true);

    var contentHtml = $("#notice-content-" + id).html();
    $("#editor").html(contentHtml);

    $("#modify-btn").css("display", "block");
    $("#register-btn").css("display", "none");

    // event.stopPropagation();
}

function registerModal() {
    $("#notice-modal").css("display", "block");
    $("#notice-modal").addClass("show");

    $("#notice-modal").data("id", "");

    $("input[name=title]").val("");
    $(".register-select option:eq(0)").prop("selected", true);
    $(".type-select option:eq(0)").prop("selected", true);
    $("#editor").html("");

    $("#modify-btn").css("display", "none");
    $("#register-btn").css("display", "block");
    // event.stopPropagation();
}

function closeModal() {
    $(".modal").css("display", "none");
    $(".modal").removeClass("show");
}

