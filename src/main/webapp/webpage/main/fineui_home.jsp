<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    <link href="metro/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="metro/assets/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" />
    <link href="metro/assets/bootstrap/css/bootstrap-fileupload.css" rel="stylesheet" />
    <link href="metro/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link href="metro/css//style.css" rel="stylesheet" />
    <link href="metro/css//style-responsive.css" rel="stylesheet" />
    <link href="metro/css//style-default.css" rel="stylesheet" id="style_color" />

</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
<!-- BEGIN HEADER -->

<!-- END HEADER -->
<!-- BEGIN CONTAINER -->
<div id="container" style="border: 1px solid #ddd">
    <!-- BEGIN SIDEBAR -->

    <!-- END SIDEBAR -->
    <!-- BEGIN PAGE -->
    <div id="main-content">
        <!-- BEGIN PAGE CONTAINER-->
        <div class="container-fluid">
            <div class="widget-body">
                <!--BEGIN METRO STATES-->
                <div class="metro-nav">
                    <div class="metro-nav-block nav-block-orange">
                        <a data-original-title="" href="#">
                            <i class="icon-tasks"></i>
                            <div class="info">0</div>
                            <div class="status">库存预警</div>
                        </a>
                    </div>
                    <div class="metro-nav-block nav-block-red">
                        <a data-original-title="" href="#">
                            <i class="icon-lightbulb"></i>
                            <div class="info">0</div>
                            <div class="status">保质期预警</div>
                        </a>
                    </div>
                    <div class="metro-nav-block nav-light-green">
                        <a data-original-title="" href="#">
                            <i class="icon-shopping-cart"></i>
                            <div class="info">+0</div>
                            <div class="status">未发订单</div>
                        </a>
                    </div>
                    <div class="metro-nav-block nav-light-blue">
                        <a data-original-title="" href="#">
                            <i class="icon-comments-alt"></i>
                            <div class="info">0</div>
                            <div class="status">未审核销售单</div>
                        </a>
                    </div>
                    <div class="metro-nav-block nav-block-blue">
                        <a data-original-title="" href="#">
                            <i class="icon-eye-open"></i>
                            <div class="info">0</div>
                            <div class="status">未审核入库单</div>
                        </a>
                    </div>

                </div>
                <div class="clearfix"></div>
            </div>

            <%--<div style="width:100%;border: 1px solid #ddd"></div>--%>

            <div class="row-fluid">
                <div class="span12">
                    <!-- BEGIN CUSTOM BUTTONS WITH ICONS PORTLET-->
                    <h4>常用功能</h4>
                    <div class="row-fluid">
                        <a class="icon-btn span2" href="#">
                            <i class="icon-shopping-cart"></i>
                            <div>销售开单</div>
                            <span class="badge badge-important">2</span>
                        </a>
                        <a class="icon-btn span2" href="#">
                            <i class="icon-search"></i>
                            <div>销售单查询</div>
                            <span class="badge badge-info">21</span>
                        </a>
                        <a class="icon-btn span2" href="#">
                            <i class="icon-edit"></i>
                            <div>进货管理</div>
                            <span class="badge badge-success">4</span>
                        </a>
                        <a class="icon-btn span2" href="#">
                            <i class="icon-search"></i>
                            <div>库存查询</div>
                        </a>
                        <a class="icon-btn span2" href="#">
                            <i class="icon-calendar"></i>
                            <div>客户往来账</div>
                        </a>
                        <a class="icon-btn span2" href="#">
                            <i class="icon-calendar"></i>
                            <div>供应商往来账</div>
                            <span class="badge badge-success">4</span>
                        </a>

                    </div>
                    <div class="row-fluid">
                        <a class="icon-btn span2" href="#">
                            <i class="icon-bullhorn"></i>
                            <div>应收账款</div>
                            <span class="badge badge-important">3</span>
                        </a>
                        <a class="icon-btn span2" href="#">
                            <i class="icon-hand-right"></i>
                            <div>应付账款</div>
                        </a>

                        <a class="icon-btn span2" href="#">
                            <i class="icon-plus"></i>
                            <div>日常收入</div>
                        </a>
                        <a class="icon-btn span2" href="#">
                            <i class="icon-minus"></i>
                            <div>日常支出</div>
                            <span class="badge badge-info">21</span>
                        </a>
                        <a class="icon-btn span2" onclick="window.parent.openTab('参数设置','emkSysParamController.do?list');">
                            <i class="icon-gear"></i>
                            <div>参数设置</div>
                        </a>
                        <a class="icon-btn span2" onclick="window.parent.openTab('数据字典','systemController.do?typeGroupList');">
                            <i class="icon-list-alt"></i>
                            <div>字典管理</div>
                        </a>
                    </div>

                </div>
                <span class="space20">&nbsp;</span>
            </div>

        </div>


        <!-- END PAGE CONTENT-->
    </div>
    <!-- END PAGE CONTAINER-->
</div>
<!-- BEGIN JAVASCRIPTS -->
<!-- Load javascripts at bottom, this will reduce page load time -->
<script src="metro/js/jquery-1.8.3.min.js"></script>
<script src="metro/js/jquery.nicescroll.js" type="text/javascript"></script>
<script src="metro/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="metro/js/jquery.scrollTo.min.js"></script>

<!-- ie8 fixes -->
<!--[if lt IE 9]>
<script src="metro/js/excanvas.js"></script>
<script src="metro/js/respond.js"></script>
<![endif]-->

<!--common script for all pages-->
<script src="metro/js/common-scripts.js"></script>

<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
<script>
    $(function(){
        var height =window.top.document.body.offsetHeight-170;
        $("#main-content").css("height",height);
    });


</script>
</html>
