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
                        <h1>계정관리</h1>
                    </div>
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="/main">Home</a></li>
                            <li class="breadcrumb-item active">유저관리</li>
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
                                <h3 class="card-title">유저관리</h3>
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
                                        <th>유저 아이디</th>
                                        <th>닉네임</th>
                                        <th>이메일</th>
                                        <th>IP</th>
                                        <th>생성일</th>
                                        <th>삭제여부</th>
                                        <th>비밀번호</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list.content}" var="user">
                                        <fmt:parseDate value="${user.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                                        <fmt:formatDate pattern="yyyy.MM.dd" value="${ parsedDateTime }" var="userDate"/>
                                        <tr>
                                            <td>${user.userId}</td>
                                            <td>${user.name}</td>
                                            <td>
                                                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal-sm" onclick="modifyEmailModal('${user.userId}','${user.email}')">
                                                보기
                                                </button>
                                            </td>
                                            <td>${user.ip}</td>
                                            <td>${userDate}</td>
                                            <c:if test="${user.del eq false}">
                                                <td>
                                                    <button type="button" class="btn btn btn-danger" data-toggle="modal" data-id="${user.userId}" onclick="deleteModal('${user.userId}')">
                                                        삭제
                                                    </button>
                                                </td>
                                                <td>
                                                    <button type="button" class="btn btn btn-primary" onclick="emailModal('${user.userId}','${user.email}')">
                                                        초기화
                                                    </button>
                                                </td>
                                            </c:if>
                                            <c:if test="${user.del eq true}">
                                                <td>
                                                    <button type="button" class="btn btn btn-primary" data-toggle="modal" data-id="${user.userId}" onclick="restoreEmailModal('${user.userId}')">
                                                    복구
                                                    </button>
                                                </td>
                                                <td>-</td>
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
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <input type="hidden" name="pageNum" value="${search.pageNum}" />

    <div class="modal fade" id="del-modal" style="display: none;" aria-hidden="true" data-id="">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">계정 삭제</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p id="del-content"></p>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">취소</button>
                    <button type="button" class="btn btn-danger" onclick="deleteUser()">삭제</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="email-modal" style="display: none; padding-right: 15px;" aria-modal="true" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">이메일 전송</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p id="email-content"></p>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">Close</button>
                    <button type="button" class="btn btn-primary" onclick="sendTempPwd()" id="sendTempPwdId">전송</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="modEmail-modal" style="display: none; padding-right: 15px;" aria-modal="true" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">이메일 변경</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input class="form-control" name="email" value="">
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">Close</button>
                    <button type="button" class="btn btn-primary" onclick="modifyEmail()">변경</button>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="restore-modal" style="display: none; padding-right: 15px;" aria-modal="true" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">삭제 계정 복구</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p id="restore-content"></p>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">Close</button>
                    <button type="button" class="btn btn-primary" onclick="restoreUser()">변경</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/dist/js/adminlte.js"></script>
    <script type="text/javascript" src="/js/m.base.js"></script>
    <script type="text/javascript" src="/js/admin/account/user/list.js"></script>
</body>
</html>