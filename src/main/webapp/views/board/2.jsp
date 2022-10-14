<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
</head>
<body>
    <style>
        .container {
            padding-left: 10px;
        }
        ul {
            list-style:none;
            padding-left: 0px;
        }
        li {

        }
        .board {
            overflow: auto;
            border-bottom: 1px solid #bcbcbc;
        }
        .title{
            font-size: 14px;
        }
        .nickName {

        }
        .nickName:after {
            content: ' | ';
        }
        .reg-date {

        }
        .reg-date:after {
            content: ' | ';
        }
        .view-count {

        }
        .reply-cnt {

        }
        .sub-content {

        }
        .sub-content > li {
            float: left;
            padding: 1px;
            font-size: 12px;
        }
    </style>
    <div class="container">
       <ul class="board-list">
           <li class="board">
               <div class="">
                   <div class="title">여행을 마무리하며</div>
                   <ul class="sub-content">
                       <li class="nickName">박푸박</li>
                       <li class="reg-date">등록일</li>
                       <li class="view-count">40</li>
                   </ul>xx
               </div>
               <div class="">4</div>
           </li>
           <li class="board">
               <div>
                   <div class="title">형님들 물이 안나와서 오늘 자체 새장국 하겠습니다..</div>
                   <ul class="sub-content">
                       <li class="nickName">꾸웨엥</li>
                       <li class="reg-date">등록일</li>
                       <li class="view-count">9</li>
                   </ul>
               </div>
               <div>1</div>
           </li>

           <li>
               <div>
                   <div class="title">여행을 마무리하며</div>
                   <ul class="sub-content">
                       <li class="nickName">박푸박</li>
                       <li class="reg-date">등록일</li>
                       <li class="view-count">40</li>
                   </ul>
               </div>
               <div class="">4</div>
           </li>
           <li>
               <div>1</div>
               <div>2</div>
           </li>

       </ul>
    </div>

</body>
</html>
