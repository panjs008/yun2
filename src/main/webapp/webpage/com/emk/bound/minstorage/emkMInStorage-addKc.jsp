<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>录入库存</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <%@include file="/context/header2ForCol.jsp"%>
    <%--<script src="${webRoot}/context/positionSelect.js"></script>--%>

    <script type="text/javascript">
        //编写自定义JS代码

    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkMInStorageController.do?doUpdateForKc" tiptype="1">
    <input id="emkMInStorageId" name="emkMInStorageId" type="hidden" value="${emkMInStoragePage.id }"/>
    <input id="rkNo" name="rkNo" type="hidden" value="KC000001"/>

    <input id="storageId" name="storageId" type="hidden" value="${emkMInStoragePage.storageId }"/>
    <input id="flag" name="flag" type="hidden" value="2"/>
    <table id="headTb" style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <td align="right" width="15%">
            <label class="Validform_label">
                仓库:
            </label>
        </td>
        <td class="value" >
            <select class="form-control select2" id="storageNameId" datatype="*">
                <option value=''>请选择</option>
            </select>

            <input id="a01a03a11" name="a01a03a11" type="hidden" value="${(ROLE.rolecode eq 'admin' || ROLE.rolecode eq 'useradmin') ? emkMInStoragePage.a01a03a11:CUR_USER.storageId}" style="width: 150px" class="inputxt"  ignore="ignore" />
            <input id="storageName" name="storageName" type="hidden" value="${(ROLE.rolecode eq 'admin' || ROLE.rolecode eq 'useradmin') ? emkMInStoragePage.storageName:CUR_USER.storageName}"/>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">仓库</label>
        </td>
    </table>
    <div id="detailId" style="overflow-x:hidden;overflow-y:hidden;;"></div>

</t:formvalid>
</body>
<script src="${webRoot}/context/storageForCol.js"></script>
<script>
    $(document).ready(function(){
        $("#detailId").load("emkMInStorageController.do?orderMxLitsForKc&inStorageId=${emkMInStoragePage.id}");
    });

</script>
</html>
