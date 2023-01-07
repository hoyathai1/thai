<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>관리자</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome Icons -->
    <link rel="stylesheet" href="/plugins/fontawesome-free/css/all.min.css">
    <!-- IonIcons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/dist/css/adminlte.min.css">
    <link rel="stylesheet" href="/css/admin/editorForm.css">
    <style>
        .div_load_image {
            display: none;
            position: fixed;
            transform: translate(-50%, -50%);
            top: 50%;
            left: 50%;
            z-index: 9999;
        }
    </style>
</head>
<body class="wrapper">
<jsp:include page="/views/admin/navi.jsp"></jsp:include>
<jsp:include page="/views/admin/side-bar.jsp"></jsp:include>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate pattern="yyyyMMdd" value="${ now }" var="today" />

<div class="div_load_image">
    <img src="/img/loading.gif" style="width:100px; height:100px;">
</div>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>게시판</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="/main">Home</a></li>
                        <li class="breadcrumb-item active">게시판 관리</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header">
                            <h3 class="card-title">게시판 관리</h3>
                            <div class="card-tools">
                                <div class="input-group input-group-sm" style="width: 150px;">
                                    <input type="text" name="table_search" class="form-control float-right" placeholder="Search" value="${search.content}">
                                    <div class="input-group-append">
                                        <button type="submit" class="btn btn-default" onclick="search(0)">
                                            <i class="fas fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="card-body table-responsive p-0">
                            <table class="table table-hover text-nowrap">
                                <thead>
                                <tr>
                                    <th>번호</th>
                                    <th>카테고리</th>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>생성일</th>
                                    <th>수정</th>
                                    <th>삭제</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list.content}" var="inform">
                                    <fmt:parseDate value="${inform.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                                    <fmt:formatDate pattern="yyyy.MM.dd" value="${ parsedDateTime }" var="informDate"/>
                                    <tr>
                                        <td>${inform.id}</td>
                                        <td>${inform.category}</td>
                                        <td class="col-4 text-wrap" id="title-${inform.id}">${inform.title}</td>
                                        <td>${inform.username}</td>
                                        <td>${informDate}</td>
                                        <td>
                                            <button type="button" class="btn btn-info" data-toggle="modal" data-target="#modal-sm" onclick="modifyModal(${inform.id})">
                                                수정
                                            </button>
                                        </td>
                                        <c:if test="${inform.del eq true}">
                                            <td>
                                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal-sm" onclick="restoreInform(${inform.id})">
                                                    복구
                                                </button>
                                            </td>
                                        </c:if>
                                        <c:if test="${inform.del eq false}">
                                            <td>
                                                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modal-sm" onclick="deleteInform(${inform.id})">
                                                    삭제
                                                </button>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="card-footer clearfix">
                            <ul class="pagination pagination-sm m-0 float-right">
                                <c:if test="${not empty list.content}">
                                    <c:forEach var="i" begin="${pageDto.startPage}" end="${pageDto.endPage}" step="1">
                                        <c:if test="${pageDto.curPage eq i}">
                                            <li class="page-item" onclick="search(${i});"><a class="page-link" href="javascript:;" onclick="search(${i})" style="color:red;">${i+1}</a></li>
                                        </c:if>
                                        <c:if test="${pageDto.curPage ne i}">
                                            <li class="page-item" onclick="search(${i});"><a class="page-link" href="javascript:;">${i+1}</a></li>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <button type="button" class="btn btn btn-info float-right" onclick="registerModal()">
                        공지 추가
                    </button>
                </div>
            </div>
        </div>
    </section>
</div>

<input type="hidden" name="pageNum" value="${search.pageNum}" />

<div class="modal fade" id="register-modal" style="display: none;" aria-hidden="true" data-id="">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">게시 공지 등록</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="category">
                    <label>카테고리</label>
                    <select class="type-select" name="c-type" id="boardCategory" onchange="getBoardTypeList()">
                    </select>
                </div>
                <div class="title">
                    <input class="ipt register" type="text" maxlength="40" id="title" name="title" autocomplete="off" placeholder="제목">
                </div>
                <div class="editor-menu">
                    <button class="btn" id="btn-bold">
                        <b>B</b>
                    </button>
                    <button class="btn" id="btn-italic">
                        <i>I</i>
                    </button>
                    <button class="btn" id="btn-underline">
                        <u>U</u>
                    </button>
                    <button class="btn" id="btn-strike">
                        <s>S</s>
                    </button>
                    <input id="img-selector" type="file" accept="image/*" multiple />
                    <button class="btn" id="btn-img">
                        이미지
                    </button>
                </div>
                <div class="content">
                    <div class="text-box" id="editor" contenteditable="true" placeholder="내용"></div>
                </div>
            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">취소</button>
                <button id="register-btn" type="button" class="btn btn-primary" onclick="btnRegister()">등록</button>
                <button id="modify-btn" type="button" class="btn btn-primary" onclick="btnModify()">수정</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="view-modal" style="display: none;" aria-hidden="true" data-id="">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">게시 공지 글</h4>
                <button type="button" class="close" 지data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="category">
                    <label>카테고리</label>
                    <select class="type-select" name="c-type" onchange="getBoardTypeList()">
                    </select>
                </div>
                <div class="title">
                    <input class="ipt" type="text" maxlength="40" id="view-title" name="view-title" autocomplete="off" placeholder="제목" readonly>
                </div>
                <div class="content">
                    <div class="text-box" id="view-editor" contenteditable="true" placeholder="내용" readonly=""></div>
                </div>
            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">취소</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/dist/js/adminlte.js"></script>
<script type="text/javascript" src="/js/admin/banner/board/list.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>
</body>
</html>