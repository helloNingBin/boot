<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="/tld/c.tld"%>
<%@ taglib prefix="fn" uri="/tld/fn.tld"%>
<%@ taglib prefix="fmt" uri="/tld/fmt.tld"%>
<%
    String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
    <%@include file="/WEB-INF/jsp/common_language.jsp"%>
    <meta charset="utf-8">
    <title>
        <c:choose>
            <c:when test="${order.orderType =='N'||order.orderType =='V'}">
                ${ur_.getKey('订单预约成功', 37)}
            </c:when>
            <c:when test="${order.orderType =='Q' }">
                ${ur_.getKey('领取成功', 37)}
            </c:when>
            <c:when test="${order.orderType =='i' }">
                ${ur_.getKey('金币充值成功', 37)}
            </c:when>
            <c:when test="${order.orderType =='W' }">
                ${ur_.getKey('金币充值成功', 37)}
            </c:when>
            <c:when test="${order.orderType =='R' }">
                ${ur_.getKey('充值成功', 37)}
            </c:when>
            <c:when test="${order.payment == '34'}">
                <%-- eft支付 --%>
                SUCCESSFULLY
            </c:when>
            <c:when test="${not empty deviceOrder}">
                订单详情
            </c:when>
            <c:otherwise>
                ${ur_.getKey('订单支付成功', 37)}
            </c:otherwise>
        </c:choose>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" id="viewport">
    <meta name="format-detection" content="telephone=no,email=no">
    <link type="text/css" rel="stylesheet" href="css/membercenter/base.css">
    <link type="text/css" rel="stylesheet" href="css/membercenter/popup.css"/>
    <link type="text/css" rel="stylesheet" href="css/order/orderCompletion.css">
    <link type="text/css" rel="stylesheet" href="css/concessions/redPacket.css">
    <link type="text/css" rel="stylesheet" href="<%=path %>/css/tabs.css">
    <link rel="stylesheet" href="css/mobile.css">
    <link href="fonts/style.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="js/ainatec.js"></script>
    <script type="text/javascript" src="js/jockey.js"></script>
    <script type="text/javascript" src="js/membercenter/index.js"></script>
    <style type="text/css">
        .paysuccesswindow_popup{
            width: 100%;
            height: 100%;
            position: fixed;
            left: 0;
            top: 0;
            z-index: 100001;
        }
        .paysuccesswindow_btn{
            width: 1.4rem;
            height: 1.4rem;
            background: #dbdbdb url(images/newRegister_close_btn.png)center center no-repeat;
            background-size: 0.8rem;
            border-radius: 50%;
            position: absolute;
            right: -0.7rem;
            top: -0.7rem;
        }
        .paySuccessWindow {
            display: flex;
            height: 100%;
            align-items: center;
        }
        .recomm_popup_img {
            position: relative;
            width: 90%;
            margin: 0 auto;
        }
        .seller-state_fail{
            background:#fff;
        }
        .sconth2 {
            font-size: .72rem;
            color: #4d4d4d;
            padding: 0.9rem .6rem 1rem;
        }
        .state-cont_fail{
            padding-top: 0.5rem!important;
        }
        .header2{background-color:#${sessionScope.titleBar.backgroundColor}; }
        .header2 .header_title{ color:#${sessionScope.titleBar.fontColor};}
        .irregularity2 {font-weight: normal;border-radius: 10px;border: 1px solid #${sessionScope.titleBar.backgroundColor};display: inline-block;overflow: hidden;font-size: 0.55rem;position: relative;margin-top: 6px;margin-bottom: -6px;background-color:#${sessionScope.titleBar.backgroundColor};box-sizing: border-box;}
        .irregularity2 .leftDiv {padding-left: 5px;float: left;background-color: #${sessionScope.titleBar.backgroundColor};color: #ffffff;}
        .irregularity2 .rightDiv {padding: 0 5px;float: right;color: #${sessionScope.titleBar.backgroundColor};background-color: #ffffff;}
        <c:if test="${isInstallment}">
        /*--分期付款特殊样式--*/
        .goods_infor .goods_pirce{font-size: 0.58rem; color: black;}
        .goods_infor .price_num{font-size: 0.58rem; color: black;}
        .goods_infor .fold{font-size: 0.48rem; color: black;}
        </c:if>
        <c:if test="${order.payment == '34'}">
        .seller-state .state-cont {text-align: left; padding-top: 2.5rem;padding-left: 10px;}
        </c:if>
        <c:if test="${order.payment == '32' || order.payment == '33' || order.payment == '34'}">
        /*-- 挖贝篮蓝色图片 --*/
        .seller-state{background-image: url('images/order/pay_bg2.png'); background-repeat: no-repeat;}
        </c:if>
    </style>
    <c:if test="${isAuction=='1'}">
        <style>
            .userInfo_modu{
                border-top: 1px solid #E6E6E6;
                padding: 0.64rem 0;
                margin: 0 0.56rem;
            }
            .user_img{
                width: 1.35rem;
                height: 1.35rem;
                border-radius: 50%;
                overflow: hidden;
                float: left;
                margin: 0.2rem 0;
                /* background: url(头像链接) 100% no-repeat; */
                /* background-size: 100% 100%;	 */
            }

            .user_name{
                color: #333333;
                font-size: 0.64rem;
                padding-left: 1.6rem;
                line-height: 0.9rem;
            }
            .user_details{
                color: #999999;
                font-size: 0.53rem;
                padding-left: 1.6rem;
                line-height: 0.7rem;
            }
            .comm_list{
                border-radius: 0.4rem;
                overflow: hidden;
            }
            .bg_div{
                background-color: #F6F6F6;
                padding-top: 0.64rem;
            }
            .comm_list{
                background-color:#FFF;
                box-shadow:0px 0px 9px 1px rgba(0, 0, 0, 0.06);
                border-radius: 0.2rem;
            }
            .comm_font {
                font-size: 0.7rem;
                margin: 0.64rem 0;
                padding: 0 0.56rem;
                box-sizing: border-box;
                word-break: break-all;
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                /* height: auto !important; */
                height:1.76rem;
            }
            .price span {
                height: 0.69rem;
                width: 1.39rem;
                display: inline-block;
                font-size: 0.53rem;
                color: #FFF;
                background-color: #F49C00;
                line-height: 0.69rem;
                text-align: center;
                position: absolute;
                top: 0;
                bottom: 0;
                margin: auto;
            }
            .comm_price .price {
                color: #333333;
                font-size: 0.8rem;
                position: relative;
                float: none;
                line-height:0.8rem;
            }
            .comm_price .price strong{
                margin-left: 1.69rem;
            }
            .comm_price .price_out {
                color: #999;
                font-size: 0.64rem;
                padding-bottom: 0.5rem;
                float: none;
                line-height:0.64rem;
                padding-top:0.64rem;
                text-decoration:inherit !important;
            }
            .comm_price{
                padding:0 0.56rem;
            }
            .user_details{
                font-size: 0.64rem;
            }
        </style>
    </c:if>
</head>

<c:if test="${payment == '4'}">
    <script>
        $(function(){
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if(isAndroid){
                Jockey.send("hideBackButton", {});
            }else if(isiOS){
                window.webkit.messageHandlers.hideBackButton_iOS.postMessage({});
            }
        })
    </script>
</c:if>
<c:if test="${isTimeProduct=='1' }">
    <script>
        var timer;var num=5;
        $(function(){
            $('body').css('overflow','hidden');
            $('body').bind("touchmove",function(e){ e.preventDefault(); });
            $('#hy_timeProduct_popup,.hy_popup_bg').addClass('current');
            timer = setInterval(count,1000);
        })
        function cancelTime(){
            clearInterval(timer);
            $('body').css('overflow','auto');
            $('body').unbind("touchmove");
            $('.hy_popup_box,.hy_popup_bg').removeClass('current');
        }
        function count(){
            if(num<=0){
                window.location.href="<%=path %>/mz/publishInfo/list.do?pv=1";
            }else{
                num--;
                $("#timeSpan").text(num+"秒后将自动返回观看。")
            }
        }
    </script>
</c:if>
<script type="text/javascript">
    $(function(){
        if(${not empty paySuccessWindow && paySuccessWindow.status == 1}){
            showpaySuccessWindow();
        }
    })

    function showpaySuccessWindow(){
        var popupClass = $('.paysuccesswindow_popup');
        if($(".paysuccesswindow_popup").length >0){
            $('body').css('overflow','hidden');
            $('body').bind("touchmove",function(e){ e.preventDefault(); });
            $('.hy_popup_bg01,.paysuccesswindow_popup').css('display','block');
            $('.paysuccesswindow_popup .paysuccesswindow_btn').click(function(e) {
                $('body').css('overflow','auto');
                $('body').unbind("touchmove");
                $('.hy_popup_bg01,.paysuccesswindow_popup').css('display','none');
            });
        }
    }
</script>
<script type="text/javascript">
    $(function(){
        var pt='${pt}';
        var pttimer;
        if(pt && pt=='wxh5'){
            var orderNo='${orderNo}';
            var orderType='${order.orderType}';
            pttimer=setInterval(function(){
                var datas={"orderNo":orderNo,'orgId':'${orgid}'};
                $.ajax({
                    url:"weiXinOrderQuery.do",
                    type:"post",
                    data:datas,
                    dataType:"json",
                    success:function(data){
                        if(data.payState){
                            removeFailClass();
                            var h="恭喜您支付成功";
                            var sub="您的包裹正在整装待发";
                            if(orderType=='N'){
                                sub="已安排客服联系您，请留意来电，谢谢。";
                            }else if(orderType=='i'||orderType=='W'){
                                sub="请注意查收";
                            }
                            $(".h").html('<i class="h_icon"></i>'+h);
                            $(".sub").html(sub);
                            clearInterval(pttimer);
                        }else{
                            addFailClass();
                            $(".h").html("尚未支付成功");
                            $(".sub").html("");

                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        $(".h").html("支付出错，请稍后重试");
                    }
                });
            },1000);
        }else{
            $(".h").html("恭喜您支付成功");
        }
    });
    function addFailClass(){
        $(".seller-state").addClass("seller-state_fail");
        $(".state-cont").addClass("state-cont_fail");
        $(".h").addClass("sconth2");
        $("#fail_img").show();
        $(".h_icon").hide();
    }
    function removeFailClass(){
        $(".seller-state").removeClass("seller-state_fail");
        $(".state-cont").removeClass("state-cont_fail");
        $(".h").removeClass("sconth2");
        $("#fail_img").hide();
        $(".h_icon").show();
    }
</script>
<body>
<!----------标题分区---------->
<div class="header2" data-role="header" data-role="none">
    <div class="header_con">
        <a href="javascript:history.go(-1)" title="" class="header_back icon-arrowLf" data-rel="back" data-role="none"></a>
        <div class="header_title" data-role="none">
            <c:choose>
                <c:when test="${order.orderType =='N'||order.orderType =='V' }"><!-- 不需要支付的预约订单 -->
                    <div class="title">${ur_.getKey('订单预约成功', 37)}</div>
                </c:when>
                <c:when test="${order.orderType =='Q' }">
                    <div class="title">${ur_.getKey('领取成功', 37)}</div>
                </c:when>
                <c:when test="${order.orderType =='i' }">
                    <div class="title">${ur_.getKey('金币充值成功', 37)}</div>
                </c:when>
                <c:when test="${order.orderType =='W' }">
                    <div class="title">${ur_.getKey('金币充值成功', 37)}</div>
                </c:when>
                <c:when test="${order.orderType =='R' }">
                    <div class="title">${ur_.getKey('充值成功', 37)}</div>
                </c:when>
                <c:when test="${order.payment == '34'}">
                    eft支付
                    <div class="title">SUCCESSFULLY</div>
                </c:when>
                <c:otherwise>
                    <div class="title">交易详情</div>
                </c:otherwise>
            </c:choose>
        </div>
        <a href="navbar?opt=initIndex&indexId=${indexId}&orgid=${orgid}&typeId=4&parCode=${parCode}" title="" class="header_home icon-home"></a>
    </div>
</div>
<%-- <header class="header">
    <a href="javascript:history.go(-1)">
    	<div class="return_icon"></div>
    </a>
    <c:choose>
   		<c:when test="${order.orderType =='N'||order.orderType =='V' }"><!-- 不需要支付的预约订单 -->
   			<div class="title">${ur_.getKey('订单预约成功', 37)}</div>
   		</c:when>
   		<c:when test="${order.orderType =='Q' }">
   			<div class="title">${ur_.getKey('领取成功', 37)}</div>
   		</c:when>
   		<c:when test="${order.orderType =='i' }">
   			<div class="title">${ur_.getKey('金币充值成功', 37)}</div>
   		</c:when>
   		<c:when test="${order.orderType =='W' }">
   			<div class="title">${ur_.getKey('金币充值成功', 37)}</div>
   		</c:when>
   		<c:when test="${order.orderType =='R' }">
   			<div class="title">${ur_.getKey('充值成功', 37)}</div>
   		</c:when>
   		<c:when test="${order.payment == '34'}">
   			eft支付
   			<div class="title">SUCCESSFULLY</div>
   		</c:when>
   		<c:otherwise>
   			<div class="title">交易详情</div>
    	</c:otherwise>
    </c:choose>

    <a href="navbar?opt=initIndex&indexId=${indexId}&orgid=${orgid}&typeId=4&parCode=${parCode}" class="home_icon"></a>
</header> --%>
<input type="hidden" id="openId"  value="${openId}" />
<input type="hidden" id="orgid"  value="${orgid}" />
<!--填充header浮动的空白-->
<!-- <div class="hy_header_H"></div> -->
<section class="rp_pay_modu">
    <div class="seller-state">
        <div class="state-cont">
            <div align="center" style="display:none;" id="fail_img">
                <img alt="" src="images/order/pay_fail.png" width="19%" height="19%">
            </div>
            <c:choose>
                <c:when test="${order.orderType =='N'||order.orderType =='V'}"><!-- 不需要支付的预约订单 -->
                    <p class="h"><i class="h_icon"></i>${ur_.getKey('恭喜您预约成功', 37)}</p>
                    <p class="sub" style="">${ur_.getKey('已安排客服联系您，请留意来电，谢谢。', 37)}</p>
                </c:when>
                <c:when test="${order.orderType=='R'}"><!-- 充值订单 -->
                    <p class="h"><i class="h_icon"></i>${ur_.getKey('恭喜您支付成功', 37)}</p>
                    <p class="sub" style="">${ur_.getKey('请在会员中心查看', 37)}</p>
                </c:when>
                <c:when test="${order.payment == '34'}">
                    <%-- eft支付 --%>
                    <div class="title">SUCCESSFULLY</div>
                </c:when>
                <c:when test="${not empty deviceOrder}">
                    <div align="center" style="display:none;" id="fail_img">
                        <img alt="" src="images/order/pay_fail.png" width="19%" height="19%">
                    </div>
                    <p class="h"><i class="h_icon"></i>${ur_.getKey('恭喜您支付成功', 37)}</p>
                    <p class="sub" style="">${ur_.getKey('请在会员中心查看', 37)}</p>
                </c:when>
                <c:otherwise>
                    <p class="h"><i class="h_icon"></i>正在处理，请稍后...</p>
                    <c:if test="${order.orderType !='E' && order.orderType !='R'}">
                        <c:choose>
                            <c:when test="${specialType == '1'}"><!-- 需要支付的预约订单 -->
                                <p class="sub" style="">${ur_.getKey('已安排客服联系您，请留意来电，谢谢。', 37)}</p>
                            </c:when>
                            <c:when test="${order.orderType=='i' || order.orderType =='W' }">
                                <p class="sub" style="">${ur_.getKey('请注意查收', 37)}</p>
                            </c:when>
                            <c:when test="${!empty serviceStores }"></c:when>
                            <c:otherwise>
                                <p class="sub" style="">${ur_.getKey('您的包裹正整装待发', 37)}</p>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</section>
<c:if test="${order.orderType !='R'&&order.orderType !='i'&&order.orderType !='W'&&receiveType!=0&&isTimeProduct!='1'}">
    <section class="rp_address_modu" style="padding: 1.2rem .6rem 0.7rem;">
        <div class="module address" id="address6">
            <div class="o-t-contmsg">
                <div class="cont">
                    <c:choose>
                        <c:when test="${order.orderType=='E' || order.orderType=='N'||order.orderType=='V'}"><!-- 电子票  预约 -->
                            <c:if test="${!empty order.phone}">
                                <h5 class="">
                                    <div>${ur_.getKey('联系电话：', 37)}${order.phone }</div>
                                </h5>
                            </c:if>
                            <c:if test="${!empty order.address}">
                                <div class="submsg">${ur_.getKey('联系地址：', 37)}${order.address }   </div>
                            </c:if>
                        </c:when>
                        <c:when test="${order.orderType=='R'}"><!-- 充值 -->
                        </c:when>
                        <c:when test="${!empty serviceStores }">
                            <h5 class="">
                                <div>${ur_.getKey('自提人：', 37)}${order.consignee }</div><div>${order.phone } </div>
                            </h5>
                            <div class="submsg">${ur_.getKey('自提地址：', 37)}${warehouse.province }${warehouse.city }${warehouse.district }${warehouse.street }   </div>
                        </c:when>
                        <c:otherwise>
                            <h5 class="">
                                <div>${ur_.getKey('收货人：', 37)}${order.consignee }</div><div>${order.phone } </div>
                            </h5>
                            <div class="submsg">${ur_.getKey('收货地址：', 37)}${order.address }   </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </section>
</c:if>
<section class="rp_amount_modu" style="position: relative;">
    <c:choose>
        <c:when test="${specialType=='2' }">
            <h5>
                    ${ur_.getKey('订单', 37)}${integralName}：<span>${order.totalAmount }</span><span style="font-size: 0.76rem;padding: 0.16rem;">${integralName}</span>
            </h5>
        </c:when>
        <c:when test="${(order.orderType !='N'&&order.orderType !='V') || !empty order.totalAmount}">
            <h5>
                <c:choose>
                    <c:when test="${order.orderType=='R' }">
                        ${ur_.getKey('充值金额：', 37)}
                    </c:when>
                    <c:otherwise>
                        ${ur_.getKey('订单金额：', 37)}
                    </c:otherwise>
                </c:choose>
                <span>${orgBasicDataCurrency}</span>
                <c:choose>
                    <c:when test="${order.orderType=='Y' }"><span>${order.balance }</span></c:when>
                    <c:when test="${! empty order.earnestMoney && order.earnestMoney >0}"><span>${order.earnestMoney }</span></c:when>
                    <c:when test="${isECards=='1'}"> <span>${cardsTotalAmount }</span></c:when>
                    <c:otherwise><span>${order.totalAmount }</span></c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${order.payment=='34'}">
                        <span style="position: absolute;right:1.5rem;font-size: 0.72rem;">EFT<i class="explain_icon1" style="position: absolute;margin-left: .2rem;top: 50%;transform: translate(0,-50%);width: 1rem;height: 1rem;background: url('images/explain_icon1.png') no-repeat;background-size: 100%;" onclick="openEftDetails()"></i></span>
                    </c:when>
                </c:choose>
            </h5>
        </c:when>
    </c:choose>
    <div class="rp_amount_btn">
        <c:choose>
            <c:when test="${order.orderType=='J' }">
                <a href="queryDealerOrderDetails.do?webIndexId=${indexId}&orgid=${orgid}&orderNo=${order.orderNo}&memberId=${memberId}&parCode=${parCode}" class="fl">${ur_.getKey('查看订单', 37)}</a>
            </c:when>
            <c:when test="${specialType=='2' }"><!-- 积分订单 -->
                <a href="exchangeHistoryInfo.do?id=${order.id}" class="fl">${ur_.getKey('查看订单', 37)}</a>
            </c:when>
            <c:when test="${order.orderType=='G' }"><!-- 拼团订单 -->
                <a href="groupBuyRecordDetail.do?id=${order.id}" class="fl">${ur_.getKey('查看订单', 37)}</a>
            </c:when>
            <c:when test="${order.orderType=='ZC' }"><!-- 众筹订单 -->
                <a href="crowdFundingRecordDetail.do?id=${order.id}" class="fl">${ur_.getKey('查看订单', 37)}</a>
            </c:when>
            <c:when test="${order.orderType=='Q' }"><!-- 中奖领取订单 -->
                <a href="zjItems.do?id=${order.id}" class="fl">${ur_.getKey('查看订单', 37)}</a>
            </c:when>
            <c:when test="${order.orderType=='N'}"><!-- 预约订单 -->
                <a href="recordDetails.do?id=${order.id}&recordNo=${orderNo}&orgId=${orgid}&memeberId=${order.memberId}" class="fl">${ur_.getKey('查看订单', 37)}</a>
            </c:when>
            <c:when test="${order.orderType=='Y' }"><!-- 预售订单 -->
                <a href="queryOrderDetails.do?webIndexId=${indexId}&orgid=${orgid}&orderNo=${order.orderNo}&memberId=${memberId}&parCode=${parCode}" class="fl">${ur_.getKey('查看订单', 37)}</a>
            </c:when>
            <c:when test="${order.orderType=='V' }"><!-- 双虹预约订单 -->
                <a href="getShOrderRecordDetails.do?id=${order.id}&recordNo=${order.recordNo}&orgId=${orgid}" class="fl">${ur_.getKey('查看订单', 37)}</a>
            </c:when>
            <c:when test="${order.orderType=='i' }"><!-- 猪罗纪充值金币订单 -->
                <a href="zljInvestManager.do?menuType=1&orgid=${orgid}" class="fl">${ur_.getKey('查看金币', 37)}</a>
            </c:when>
            <c:when test="${order.orderType=='W' }"><!--金币充值订单 -->
                <a href="getMyMillionCoupon.do?memberId=${order.memberId}&orgid=${orgid}" class="fl">${ur_.getKey('查看金币', 37)}</a>
            </c:when>
            <c:when test="${isECards=='1'&&receiveType==0}"><!--直接生成电子贺卡方式，显示电子贺卡中心 -->
                <a  href="findAllECards.do?type=111&memberId=${order.memberId}&webIndexId=${indexId}&orgid=${orgid}&parCode=${parCode}" class="fl">${ur_.getKey('电子贺卡中心', 37)}</a>
                <a  href="findECardsOrder.do?memberId=${order.memberId}&payState=0&webIndexId=${indexId}&orgid=${orgid}&parCode=${parCode}" class="fl">${ur_.getKey('电子贺卡订单', 37)}</a>
            </c:when>
            <c:when test="${isECards=='1'&&receiveType!=0}"><!--其他电子贺卡方式-->
                <a href="findECardsOrder.do?memberId=${order.memberId}&payState=0&webIndexId=${indexId}&orgid=${orgid}&parCode=${parCode}" class="fl">${ur_.getKey('电子贺卡订单', 37)}</a>
            </c:when>
            <c:when test="${order.orderType=='R' }"><!--会员充值 -->
                <!-- 不显示按钮 -->
            </c:when>
            <c:otherwise>
                <a href="queryOrderDetails.do?webIndexId=${indexId}&orgid=${orgid}&orderNo=${orderNo}&memberId=${memberId}&parCode=${parCode}" class="fl">${ur_.getKey('查看订单', 37)}</a>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${order.orderType=='R'}"><!--会员充值 -->
                <!-- 不显示按钮 -->
            </c:when>
            <c:when test="${videoProId != null && videoProId != ''}"><!-- 付费视频 -->
                <a href="toProductBuy.do?orgid=${orgid}&type=1&indexId=${indexId}&id=${videoProId}&parCode=${parCode}" class="fr">观看视频</a>
            </c:when>
            <c:when test="${isTimeProduct=='1'}">
                <a href="<%=path %>/mz/publishInfo/list.do?pv=1" class="fr">${ur_.getKey('返回发现首页', 37)}</a>
            </c:when>
            <c:otherwise>
                <a href="navbar?opt=initIndex&indexId=${indexId}&orgid=${orgid}&typeId=4&parCode=${parCode}" class="fr">${ur_.getKey('返回商城', 37)}</a>
            </c:otherwise>
        </c:choose>
    </div>
</section>
<c:if test="${list != null && list.size()>0 || (isECards!='1' && isAuction!='1')}">
    <c:choose>
        <c:when test="${isECards=='1'}">
            <!--电子贺卡-->
            <section class="rp_hotShop_modu">
                <h5 ><i class="like"></i><span>${ur_.getKey('热销贺卡', 37)}</span></h5>
                <div class="goods_ul">
                    <c:forEach items="${list}" var="row" varStatus="rows">
                        <a href="toProductBuy.do?orgid=${orgid}&type=1&indexId=${indexId}&id=${row.id}&isEcardsProduct=1" class="goods_list">
                            <div class="goods_img" style="width: 100%;height: 100%;">
                                <img src="${row.pictrueUrl}">
                            </div>
                            <div class="goods_name">
                                <span>${row.name}</span>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </section>
        </c:when>
        <c:when test="${isAuction=='1' }">
            <section class="rp_hotShop_modu">
                <h5 ><i class="like"></i><span>${ur_.getKey('热销拍拍品', 37)}</span></h5>
                <div class="goods_ul">
                    <c:forEach items="${list}" var="row" varStatus="rows">
                        <a href="toProductBuy.do?orgid=${orgid}&type=1&indexId=${indexId}&id=${row.id}&isAuction=1&auctionId=${row.auctionId}" class="goods_list">
                            <div class="goods_img" style="width: 100%;height: 100%;">
                                <img src="${row.firstImgUrl}">
                            </div>
                            <div class="goods_name">
                                <span>${row.name}</span>
                            </div>
                            <div class="comm_price">
                                <p class="price"><span>拍价</span><strong class="present">${orgBasicDataCurrency}${row.price}</strong></p>
                                <p class="price_out">零售价：${orgBasicDataCurrency}${row.martPrice }</p>
                            </div>
                            <c:if test="${!empty row.auctionId }">
                                <div class="userInfo_modu">
                                    <c:choose>
                                        <c:when test="${!empty row.videoUrl && row.videoUrl != '' }">
                                            <div class="user_img" style="background: url(${row.videoUrl}) 100% no-repeat;background-size: 100% 100%;">
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="user_img" style="background: url(images/membercenter/user_icon02.jpg) 100% no-repeat;background-size: 100% 100%;">
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="user_name">
                                        <c:choose>
                                            <c:when test="${!empty row.videoName}">${row.videoName}</c:when>
                                            <c:otherwise>**</c:otherwise>
                                        </c:choose>

                                    </div>
                                    <div class="user_details">
                                        <fmt:formatDate value="${row.approveTime}" type="date" pattern="yyyy-MM-dd "/>&nbsp;发布
                                    </div>
                                </div>
                            </c:if>
                                <%--  <c:if test="${isInstallment}"><p style="color: red; font-size: 0.69rem;"><span style="font-size: 0.88rem;">${orgBasicDataCurrency} <strong><fmt:formatNumber value="${row.price / 12}" pattern="#0.00" /></strong></span> x 12期</p></c:if> --%>
                                <%--  <div class="goods_infor" style="padding-left: 0px;">
                                     <span class="goods_pirce">
                                         <span class="price_num">
                                         <c:if test="${isInstallment}">商城价：</c:if>${orgBasicDataCurrency} ${row.price}
                                         </span>
                                     </span>
                                     <span class="cost_price"><c:if test="${row.martPrice != null && row.martPrice>0}">${orgBasicDataCurrency} ${row.martPrice}</c:if></span>
                                 </div> --%>
                        </a>
                    </c:forEach>
                </div>
            </section>
        </c:when>
        <c:otherwise>
            <!--热销商品-->
            <section class="rp_hotShop_modu">
                <h5 ><i class="like"></i><span>${ur_.getKey('热销商品', 37)}</span></h5>
                    <%--
                                    <div class="goods_ul">
                                        <c:forEach items="${list}" var="row" varStatus="rows">
                                            <a href="toProductBuy.do?orgid=${orgid}&type=1&indexId=${indexId}&id=${row.id}" class="goods_list">
                                                <div class="goods_img" style="width: 100%;height: 100%;">
                                                    <img src="${row.firstImgUrl}">
                                                </div>
                                                <div class="goods_name">
                                                    <span>${row.name}</span>
                                                </div>
                                                <c:if test="${isInstallment}"><p style="color: red; font-size: 0.69rem;"><span style="font-size: 0.88rem;">${orgBasicDataCurrency} <strong><fmt:formatNumber value="${row.price / 12}" pattern="#0.00" /></strong></span> x 12期</p></c:if>
                                                <div class="goods_infor" style="padding-left: 0px;">
                                                    <span class="goods_pirce">
                                                        <span class="price_num">
                                                        <c:if test="${isInstallment}">商城价：</c:if>${orgBasicDataCurrency} ${row.price}
                                                        </span>
                                                    </span>
                                                    <span class="cost_price"><c:if test="${row.martPrice != null && row.martPrice>0}">${orgBasicDataCurrency} ${row.martPrice}</c:if></span>
                                                </div>
                                            </a>
                                        </c:forEach>
                                    </div>
                                --%>
                    <%-- <%@include file="/WEB-INF/jsp/tabs.jsp" %> --%>
                <%@include file="../website/module/waterfall_recomment.jsp" %>
            </section>
        </c:otherwise>
    </c:choose>

</c:if>

<c:if test="${shareRedPackRecord != null }">
    <!--分享红包-->
    <a href="shareRedPack.do?webIndexId=${indexId}&recordId=${shareRedPackRecord.id}&orgid=${orgid}&parCode=${parCode}">
        <div class="redPacket bounce"></div>
    </a>
</c:if>
<c:if test="${not empty paySuccessWindow && paySuccessWindow.status == 1}">
    <!-- 推广活动弹窗 开始-->
    <div class="paysuccesswindow_popup" style="display: none">
        <div class="paySuccessWindow">
            <div class="recomm_popup_img paySuccessWindow_div">
                <a class="paySuccessWindow_a" href="${paySuccessWindow.imgString}"> <img src="${paySuccessWindow.imgUrl}" /></a>
                <i class="paysuccesswindow_btn"></i>
            </div>
        </div>
    </div>
    <!-- 推广活动弹窗 结束-->
</c:if>
<!-- E 单行文本提示框 -->
<div id="loadingcss3" class="fixedloading"><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i></div>
<div class="fixedloadingbg"></div>
<c:if test="${navbarList != null && fn:length(navbarList)>0 && language eq 0}">
    <div class="clear" style="height:52px;"></div>
    <jsp:include page="/WEB-INF/jsp/website/navbar/navbar.jsp" />
</c:if>
<!-- 弹出提示框遮挡层 -->
<div id="hy_popup_bg" class="hy_popup_bg"></div>
<!-- 弹出提示框,确认是和否类型 -->
<div id="hy_timeProduct_popup" class="hy_popup_box hy_oper_popup">
    <div class="hy_popup_centent">
        <span id='timeSpan'>${ur_.getKey("5秒后将自动返回观看。", 13)}</span>
    </div>
    <div class="hy_popup_foot">
        <a href="<%=path %>/mz/publishInfo/list.do?pv=1" class="hy_popup_btn hy_sure_btn">${ur_.getKey("立即跳转", 13)}</a>
        <a href="javascript:cancelTime();" class="hy_popup_btn">${ur_.getKey("取消", 13)}</a>
    </div>
    <input type="hidden"  id="signId"/>
    <input type="hidden"  id="isSpiltOrder"/>
</div>

<%@include file="/WEB-INF/jsp/popPrompt.jsp"%>

<script type="text/javascript">
    function openEftDetails(){
        var url = "getPaymentDetails.do?orgId=${orgid}&payment=${order.payment}";
        window.location.href=url;
    }
</script>
</body>
</html>