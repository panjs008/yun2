<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<style>
    .table-c table{border-right:1px solid #ddd;border-bottom:1px solid #ddd}
    .table-c table td{border-left:1px solid #ddd;border-top:1px solid #ddd;height: 36px;}
</style>
<div style="padding: 3px; height: 0px; width:100%;margin-bottom:4px " class="datagrid-toolbar"></div>
<strong>账单汇总</strong>
<div class="table-c" style="margin-top:5px;">
    <table width="70%" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="60" >订单总金额</td>
            <td align="center"  width="60">叫布费用</td>
            <td align="center"  width="60">其他物料费用</td>
            <td align="center"  width="60">发货总金额</td>
            <td align="center"  width="60">退货总金额</td>
            <td align="center"  width="60">付款总金额</td>
            <td align="center"  width="60">未付款</td>

        </tr>
        <tbody id="add_jeecgOrderProduct_table">
            <tr>
                <td align="center">${orderMoney}</td>
                <td align="center">${setjbMoney}</td>
                <td align="center">${wlMoney}</td>
                <td align="center">${fhMoney}</td>
                <td align="center">${thMoney}</td>
                <td align="center">${fkMoney}</td>
                <td align="center">${wfk}</td>

            </tr>
        </tbody>
    </table>
</div>
<div style="padding: 3px; height: 0px; width:100%;margin-bottom:4px " class="datagrid-toolbar"></div>
<strong>付款信息</strong>
<div class="table-c" style="margin-top:5px;">
    <table width="70%" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center" width="30" >序号</td>
            <td align="center"  width="120" >订单号</td>
            <td align="center"  width="60">日期</td>
            <td align="center"  width="60"  >金额</td>
            <td align="center"  width="60"  >方式</td>
            <td align="center"  width="60"  >备注</td>
            <td align="center"  width="60"  >图片凭证</td>
            <td align="center"  width="60"  >状态</td>
        </tr>
        <tbody id="add_jeecgOrderProduct_table">
        <c:if test="${fn:length(payEntityList)  > 0 }">
            <c:forEach items="${payEntityList}" var="poVal" varStatus="status">
                <tr>
                    <td align="center" width="30">${status.index+1}</td>
                    <td align="center">${poVal.orderNo}</td>
                    <td align="center">${poVal.payDate}</td>
                    <td align="center">${poVal.money}</td>
                    <td align="center">
                        <c:if test="${poVal.type eq 'zfb'}">支付宝</c:if>
                        <c:if test="${poVal.type eq 'xj'}">现金</c:if>
                        <c:if test="${poVal.type eq 'wx'}">微信</c:if>
                        <c:if test="${poVal.type eq 'yhzz'}">银行转账</c:if>
                    </td>
                    <td align="center">${poVal.remark}</td>
                    <td align="center">
                        <c:if test="${poVal.fileNameUrl ne '' && poVal.fileNameUrl ne null}">
                            <input class="btn" type="button" value="查看" onclick="findDetail('${webRoot}/imp/outorder/${poVal.saveFileName }')" style="background:#18a689 none repeat scroll 0 0;height:28px;width:50px !important;border-radius:5px;color: #fff;" >
                        </c:if>
                    </td>
                    <td align="center">
                        <c:if test="${poVal.state eq '1'}"><font color="blue">提交</font></c:if>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>

