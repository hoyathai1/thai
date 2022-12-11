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
</head>
<body class="wrapper">
    <jsp:include page="/views/admin/navi.jsp"></jsp:include>
    <jsp:include page="/views/admin/side-bar.jsp"></jsp:include>

    <jsp:useBean id="now" class="java.util.Date" />
    <fmt:formatDate pattern="yyyyMMdd" value="${ now }" var="today" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1>댓글관리</h1>
                    </div>
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="/main">Home</a></li>
                            <li class="breadcrumb-item active">댓글 관리</li>
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
                                <h3 class="card-title">댓글 관리</h3>
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
                                        <th>내용</th>
                                        <th>작성자</th>
                                        <th>댓글개수</th>
                                        <th>생성일</th>
                                        <th>게시글</th>
                                        <th>수정</th>
                                        <th>삭제</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list.content}" var="comment">
                                        <fmt:parseDate value="${comment.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                                        <fmt:formatDate pattern="yyyy.MM.dd" value="${ parsedDateTime }" var="commentDate"/>
                                        <tr>
                                            <td class="col-6" id="comment-${comment.id}">${comment.content}</td>
                                            <c:if test="${comment.user eq true}">
                                                <td>${comment.username}</td>
                                            </c:if>
                                            <c:if test="${comment.user eq false}">
                                                <td>${comment.author}(${comment.ip})</td>
                                            </c:if>
                                            <td>${comment.commentTotalCount}</td>
                                            <td>${commentDate}</td>
                                            <td>
                                                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#modal-sm" onclick="viewModal(${comment.boardId})">
                                                    보기
                                                </button>
                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#modal-sm" onclick="commentModal(${comment.id})">
                                                    수정
                                                </button>
                                            </td>
                                            <c:if test="${comment.del eq true}">
                                                <td>
                                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal-sm" onclick="restoreComment(${comment.id})">
                                                        복구
                                                    </button>
                                                </td>
                                            </c:if>
                                            <c:if test="${comment.del eq false}">
                                                <td>
                                                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modal-sm" onclick="deleteComment(${comment.id})">
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
            </div>
        </section>
    </div>

    <input type="hidden" name="pageNum" value="${search.pageNum}" />

    <div class="modal fade" id="comment-modal" style="display: none;" aria-hidden="true" data-id="">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">게시판</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input class="form-control" name="content" value="">
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">취소</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="modify()">수정</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="view-modal" style="display: none;" aria-hidden="true" data-id="">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">게시판</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="title">
                        <input class="ipt" type="text" maxlength="40" id="view-title" name="view-title" autocomplete="off" placeholder="제목" readonly>
                    </div>
                    <div class="content">
                        <div class="text-box comment-box" id="view-editor" placeholder="내용" readonly></div>
                    </div>
                    <div class="comment">
                        <div class="comment-list">
                        </div>
                    </div>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">취소</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="delete-modal" style="display: none;" aria-hidden="true" data-id="">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">댓글 삭제</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModalForViewMoal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>댓글을 삭제하기겠습니까?</p>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModalForViewMoal()">취소</button>
                    <button type="button" class="btn btn-primary" onclick="deleteCommentForView()">삭제</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="restore-modal" style="display: none;" aria-hidden="true" data-board="" data-comment="">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">댓글 복구</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModalForViewMoal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>댓글을 복구하기겠습니까?</p>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModalForViewMoal()">취소</button>
                    <button type="button" class="btn btn-primary" onclick="restoreCommentForView()">복구</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/dist/js/adminlte.js"></script>
    <script type="text/javascript" src="/js/m.base.js"></script>
    <script type="text/javascript" src="/js/admin/board/comment/list.js"></script>
</body>
</html>