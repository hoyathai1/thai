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
    <link rel="stylesheet" href="/css/admin/base.css">
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
                        <h1>이미지 관리</h1>
                    </div>
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="/main">Home</a></li>
                            <li class="breadcrumb-item active">이미지 관리</li>
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
                        <div class="card card-primary">
                            <div class="card-header">
                                <h4 class="card-title">이미지 관리</h4>
                            </div>
                            <div class="card-body">
                                <div>
                                    <div class="mb-2">
                                        <a class="btn btn-secondary" href="javascript:javascript:void(0)" onclick="refresh()"> 새로고침 </a>
                                        <div class="float-right">
                                            <%--<select class="custom-select" style="width: auto;" data-sortorder="">--%>
                                                <%--<option value="index"> Sort by Position </option>--%>
                                                <%--<option value="sortData"> Sort by Custom Data </option>--%>
                                            <%--</select>--%>
                                            <div class="btn-group">
                                                <c:if test="${search.pageSize eq 12}">
                                                    <a class="btn btn-primary" href="javascript:void(0)" onclick="changePageSize(12)"> 12개 </a>
                                                    <a class="btn btn-default" href="javascript:void(0)" onclick="changePageSize(24)"> 24개 </a>
                                                    <a class="btn btn-default" href="javascript:void(0)" onclick="changePageSize(48)"> 48개 </a>
                                                </c:if>
                                                <c:if test="${search.pageSize eq 24}">
                                                    <a class="btn btn-default" href="javascript:void(0)" onclick="changePageSize(12)"> 12개 </a>
                                                    <a class="btn btn-primary" href="javascript:void(0)" onclick="changePageSize(24)"> 24개 </a>
                                                    <a class="btn btn-default" href="javascript:void(0)" onclick="changePageSize(48)"> 48개 </a>
                                                </c:if>
                                                <c:if test="${search.pageSize eq 48}">
                                                    <a class="btn btn-default" href="javascript:void(0)" onclick="changePageSize(12)"> 12개 </a>
                                                    <a class="btn btn-default" href="javascript:void(0)" onclick="changePageSize(24)"> 24개 </a>
                                                    <a class="btn btn-primary" href="javascript:void(0)" onclick="changePageSize(48)"> 48개 </a>
                                                </c:if>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <c:forEach items="${list.content}" var="img">
                                        <c:if test="${img.del eq true}">
                                            <div class="col-sm-2" onclick="imageModal(${img.id})">
                                                <a href="javascript:;" >
                                                    <img id="img-${img.id}" src="${img.dir}${img.name}" class="img-fluid mb-2 del">
                                                </a>
                                            </div>
                                        </c:if>
                                        <c:if test="${img.del eq false}">
                                            <div class="col-sm-2" onclick="imageModal(${img.id})">
                                                <a href="javascript:;" >
                                                    <img id="img-${img.id}" src="${img.dir}${img.name}" class="img-fluid mb-2">
                                                </a>
                                            </div>
                                        </c:if>

                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer clearfix">
                            <ul class="pagination pagination-sm m-0 float-right">
                                <c:if test="${not empty list.content}">
                                    <c:forEach var="i" begin="${pageDto.startPage}" end="${pageDto.endPage}" step="1">
                                        <c:if test="${pageDto.curPage eq i}">
                                            <li class="page-item" onclick="gePage(${i});"><a class="page-link" href="javascript:;" onclick="gePage(${i})" style="color:red;">${i+1}</a></li>
                                        </c:if>
                                        <c:if test="${pageDto.curPage ne i}">
                                            <li class="page-item" onclick="gePage(${i});"><a class="page-link" href="javascript:;">${i+1}</a></li>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <div class="modal fade" id="view-modal" style="display: none;" aria-hidden="true" data-id="">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">이미지</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="img-content" id="img-content">
                        <img id="img-detail">
                    </div>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">취소</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="deleteImage()" id="delImgBtn">삭제</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="restoreImage()" id="resImgBtn">복구</button>
                </div>
            </div>
        </div>
    </div>

    <input type="hidden" name="pageNum" value="${search.pageNum}" />
    <input type="hidden" name="pageSize" value="${search.pageSize}" />

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/dist/js/adminlte.js"></script>
    <script type="text/javascript" src="/js/m.base.js"></script>
    <script type="text/javascript" src="/js/admin/image/list.js"></script>
</body>
</html>