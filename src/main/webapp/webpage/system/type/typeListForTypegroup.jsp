<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="typeValueList" pageSize="20" pagination="true" title="common.type.list" checkbox="true"
                    actionUrl="systemController.do?typeGrid&typegroupid=${typegroupid}" idField="id"
                    queryMode="group">
            <t:dgCol title="common.code" field="id" hidden="true" width="120"></t:dgCol>
            <t:dgCol title="common.type.name" field="typename" width="200"></t:dgCol>
            <t:dgCol title="common.type.code" field="typecode" width="150"></t:dgCol>
            <c:if test="${param.typegroupname eq '颜色'}">
                <t:dgCol title="色号" field="remark" width="150"></t:dgCol>
            </c:if>
            <t:dgCol title="common.operation" field="opt" width="150"></t:dgCol>
            <c:choose>
                <c:when test="${param.typegroupname eq '机构类型' || param.typegroupname eq '短信类别' || param.typegroupname eq '数据源类型'
                                     || param.typegroupname eq '开发权限'  || param.typegroupname eq '数据表'  || param.typegroupname eq '规则条件'
                                     || param.typegroupname eq '校验规则'  || param.typegroupname eq '图表类型'  || param.typegroupname eq '表单分类'
                                     }"></c:when>
                <c:otherwise>
                    <t:dgDelOpt url="systemController.do?delType&id={id}" title="common.delete" urlclass="ace_button"  urlfont="fa-trash-o"></t:dgDelOpt>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${param.typegroupname eq '机构类型' || param.typegroupname eq '短信类别' || param.typegroupname eq '数据源类型'
                                     || param.typegroupname eq '开发权限'  || param.typegroupname eq '数据表'  || param.typegroupname eq '规则条件'
                                     || param.typegroupname eq '校验规则'  || param.typegroupname eq '图表类型'  || param.typegroupname eq '表单分类'}"></c:when>
                <c:otherwise>
                    <t:dgToolBar title="common.add.param" langArg="common.type" icon="icon-add" url="systemController.do?addorupdateType&typegroupname=${param.typegroupname}&typegroupid=${typegroupid}" funname="add"></t:dgToolBar>
                    <t:dgToolBar title="common.edit.param" langArg="common.type" icon="icon-edit" url="systemController.do?addorupdateType&typegroupname=${param.typegroupname}&typegroupid=${typegroupid}" funname="update"></t:dgToolBar>
                    <t:dgToolBar title="删除"  icon="icon-remove" url="systemController.do?delBatchType" funname="deleteALLSelect"></t:dgToolBar>
                </c:otherwise>
            </c:choose>

        </t:datagrid>
    </div>
</div>
<script>
    function addType(title,addurl,gname,width,height) {
        alert($("#id").val());
        add(title,addurl,gname,width,height);
    }
</script>

