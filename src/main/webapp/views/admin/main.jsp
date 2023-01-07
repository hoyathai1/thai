<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>MAIN</title>
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
    <jsp:include page="navi.jsp"></jsp:include>
    <jsp:include page="side-bar.jsp"></jsp:include>

    <div class="content-wrapper" style="min-height: 706px;">
        <div class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1 class="m-0">Dashboard</h1>
                    </div>
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="#">Home</a></li>
                            <li class="breadcrumb-item active">Dashboard v1</li>
                        </ol>
                    </div>
                </div>
            </div>
        </div>

        <section class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-2 col-6">
                        <div class="small-box bg-info">
                            <div class="inner">
                                <h3>${userCnt}</h3>
                                <p>현재 방문자</p>
                            </div>
                            <div class="icon">
                                <i class="ion ion-stats-bars"></i>
                            </div>
                        </div>
                    </div>  <%--.col-lg-2 col-6--%>
                    <div class="col-lg-2 col-6">
                        <div class="small-box bg-info">
                            <div class="inner">
                                <h3>${dto.visitDayTotal}</h3>
                                <p>오늘 방문자</p>
                            </div>
                            <div class="icon">
                                <i class="ion ion-stats-bars"></i>
                            </div>
                        </div>
                    </div>  <%--.col-lg-2 col-6--%>
                    <div class="col-lg-2 col-6">
                        <div class="small-box bg-success">
                            <div class="inner">
                                <h3>${dto.boardCnt}</h3>
                                <p>오늘 새글</p>
                            </div>
                            <div class="icon">
                                <i class="ion ion-stats-bars"></i>
                            </div>
                        </div>
                    </div>  <%--.col-lg-2 col-6--%>
                    <div class="col-lg-2 col-6">
                        <div class="small-box bg-warning">
                            <div class="inner">
                                <h3>${dto.commentCnt}</h3>
                                <p>오늘 새댓글</p>
                            </div>
                            <div class="icon">
                                <i class="ion ion-stats-bars"></i>
                            </div>
                        </div>
                    </div>  <%--.col-lg-2 col-6--%>
                    <div class="col-lg-2 col-6">
                        <div class="small-box bg-info">
                            <div class="inner">
                                <h3>${dto.userCnt}</h3>
                                <p>오늘 가입자</p>
                            </div>
                            <div class="icon">
                                <i class="ion ion-person-add"></i>
                            </div>
                        </div>
                    </div>  <%--.col-lg-2 col-6--%>
                    <div class="col-lg-2 col-6">
                        <div class="small-box bg-danger">
                            <div class="inner">
                                <h3>-</h3>
                                <p>해지 유저</p>
                            </div>
                            <div class="icon">
                                <i class="ion ion-stats-bars"></i>
                            </div>
                        </div>
                    </div>  <%--.col-lg-2 col-6--%>
                </div>  <%--.row--%>

                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-footer">
                            <div class="row">
                                <div class="col-lg-3 col-6">
                                    <div class="description-block border-right">
                                        <span class="description-percentage text-success"><i class="fas fa-caret-up"></i> ${userCnt}</span>
                                        <h5 class="description-header">${total.visitTotal}</h5>
                                        <span class="description-text">전체 방문</span>
                                    </div>
                                </div>  <%--.col-sm-3 col-6--%>
                                <div class="col-lg-3 col-6">
                                    <div class="description-block border-right">
                                        <span class="description-percentage text-success"><i class="fas fa-caret-up"></i> ${dto.boardCnt}</span>
                                        <h5 class="description-header">${total.boardTotal}</h5>
                                        <span class="description-text">전체 게시물</span>
                                    </div>
                                </div>  <%--.col-sm-3 col-6--%>
                                <div class="col-lg-3 col-6">
                                    <div class="description-block border-right">
                                        <span class="description-percentage text-success"><i class="fas fa-caret-up"></i> ${dto.commentCnt}</span>
                                        <h5 class="description-header">${total.commentTotal}</h5>
                                        <span class="description-text">전체 댓글</span>
                                    </div>
                                </div>  <%--.col-sm-3 col-6--%>
                                <div class="col-lg-3 col-6">
                                    <div class="description-block">
                                        <span class="description-percentage text-success"><i class="fas fa-caret-up"></i> ${dto.userCnt}</span>
                                        <h5 class="description-header">${total.userTotal}</h5>
                                        <span class="description-text">전체 유저</span>
                                    </div>
                                </div>  <%--.col-sm-3 col-6--%>
                            </div>  <%--.row--%>
                        </div>
                    </div>
                </div>  <%-- 통계 끝--%>

<%--

                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-header border-0">
                            <div class="d-flex justify-content-between">
                                <h3 class="card-title">주간 통계</h3>
                                <a href="javascript:void(0);">View Report</a>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="d-flex">
                                <p class="d-flex flex-column">
                                    <span class="text-bold text-lg">820</span>
                                    <span>Visitors Over Time</span>
                                </p>
                                <p class="ml-auto d-flex flex-column text-right">
                                    <span class="text-success">
                                    <i class="fas fa-arrow-up"></i> 12.5%
                                    </span>
                                    <span class="text-muted">Since last week</span>
                                </p>
                            </div>

                            <div class="position-relative mb-4"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
                                <canvas id="visitors-chart" height="200" width="500" style="display: block; width: 500px; height: 200px;" class="chartjs-render-monitor"></canvas>
                            </div>
                            <div class="d-flex flex-row justify-content-end">
                                <span class="mr-2">
                                <i class="fas fa-square text-primary"></i> This Week
                                </span>
                                                                <span>
                                <i class="fas fa-square text-gray"></i> Last Week
                                </span>
                            </div>
                        </div>
                    </div>
                </div>  &lt;%&ndash;.그래프1&ndash;%&gt;

                --%>
            </div>
        </section>
    </div>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/dist/js/adminlte.js"></script>
</body>
</html>
