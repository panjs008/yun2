<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
    function gridLoadSuccess(data) {
        var num = 6,num2 = 3,rowsLen = data.rows.length;
        for(var i=0; i<rowsLen/num; i++){
            $('#hmStaffList').datagrid('mergeCells',{
                index:i*num,
                field:'deptName',
                rowspan:num
            });
        }
    }
</script>
<div id="main_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="hmStaffList" checkbox="false" pagination="false" onClick="queryDetail" sortName="deptCode,workCode" sortOrder="asc" pageSize="20" fitColumns="false" title="" actionUrl="hmStaffController.do?listAllByJdbc" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="部门" rowspan="2"  query="true" field="deptName"  queryMode="single"  width="85"></t:dgCol>
            <t:dgCol title="部门"  hidden="true" field="deptCode"  queryMode="single"  width="90"></t:dgCol>
            <t:dgCol title="人数" rowspan="2"  formatterjs="setTotal0" field="bmrs"  queryMode="single"  width="45"></t:dgCol>
           <%-- <t:dgCol title="车间"  query="true" field="workName"  queryMode="single"  width="100"></t:dgCol>
            <t:dgCol title="人数"  formatterjs="setTotal" field="createName"  queryMode="single"  width="50"></t:dgCol>--%>
            <t:dgCol title="各个车间" colspan="${fn:length(orgList)}" newColumn="true"   queryMode="single"  width="120"></t:dgCol>
            <c:forEach var="list" items="${orgList}" varStatus="status">
                <t:dgCol title="${list.departname}" formatterjs="setPoint"  field="${list.orgCode}" queryMode="single"  width="44" ></t:dgCol>
            </c:forEach>
        </t:datagrid>
    </div>
</div>
<div id="tempSearchColums" style="display: none;width:100px;">
    <div name="searchColums">
        <table align="right" style="margin-right: 10px;margin-top: 5px;">
            <tr>

                <td>
                    &nbsp;&nbsp;<font color="blue">总人数：${rs}</font>&nbsp;&nbsp;
                </td>

            </tr>
        </table>

    </div>
</div>
<div id="southD" region="south" border="false" style="height:235px;width:100%;overflow: hidden;" id="southDiv">
    <t:tabs id="rkglTabs" iframe="false" tabPosition="top" fit="false">
        <t:tab title="明细分析" id="mxID"  heigth="100%" width="100%" icon="" iframe="hmStaffController.do?forAnsily"></t:tab>
    </t:tabs>
</div>
<script src = "webpage/com/hm/rsgl/staff/hmStaffList.js"></script>
<script type="text/javascript">
    var zrsP = 0;
    $(document).ready(function(){
        /*$("#hmStaffListtb").find("select[name='month']").empty();
        var html = '<option value="0">一年以上三年以下</option>';
        html += '<option value="1">三年以上至5年以下</option>';
        html += '<option value="2">5年以上</option>';
        $("#hmStaffListtb").find("select[name='month']").append(html);*/
        /*$("#hmStaffListtb").find("div[name='searchColums']").find("form#hmStaffListForm").append($("#tempSearchColums div[name='searchColums']").html());
        $("#tempSearchColums").html('');*/
    });
    function setPoint(val,row,index){
        if(val != null && val != ''  && val != '0'){
            return parseInt(val);
        }else{
            return '';
        }
    }
    function setTotal0(val,row,index){
        if(val == 'bmrs'){
            return  '<font color="blue">${rs}</font>';
        }else{
            if(val != null && val != ''  && val != '0'){
                return '<font color="blue">'+parseInt(val)+'</font>';
            }else{
                return '';
            }
        }
    }
    function setTotal(val,row,index){
        var total = parseInt(row.A01A02A01A01)+parseInt(row.A01A02A01A02)+parseInt(row.A01A02A02A01);
        total += parseInt(row.A01A02A03A01)+parseInt(row.A01A02A03A02)+parseInt(row.A01A02A03A03);
        total += parseInt(row.A01A02A04A01)+parseInt(row.A01A02A04A02)+parseInt(row.A01A02A04A03);
        total += parseInt(row.A01A02A04A04)+parseInt(row.A01A02A04A05)+parseInt(row.A01A02A04A06);
        total += parseInt(row.A01A02A04A07)+parseInt(row.A01A02A04A08)+parseInt(row.A01A02A05A01);
        total += parseInt(row.A01A02A05A02)+parseInt(row.A01A02A05A03)+parseInt(row.A01A02A06A01);
        total += parseInt(row.A01A02A06A02)+parseInt(row.A01A02A07A01)+parseInt(row.A01A02A07A02);
        total += parseInt(row.A01A02A08A01)+parseInt(row.A01A03A01A01)+parseInt(row.A01A03A02A01);
        total += parseInt(row.A01A03A03A01)+parseInt(row.A01A03A04A01)+parseInt(row.A01A03A04A02);
        total += parseInt(row.A01A03A05A01)+parseInt(row.A01A04A01A01)+parseInt(row.A01A04A02A01);
        total += parseInt(row.A01A04A03A01)+parseInt(row.A01A05A01A01)+parseInt(row.A01A06A01A01);

        total += parseInt(row.A01A06A02A01)+parseInt(row.A01A06A03A01)+parseInt(row.A01A06A03A02);
        total += parseInt(row.A01A06A04A01)+parseInt(row.A01A07A01A01)+parseInt(row.A01A08A01A01);
        total += parseInt(row.A01A08A02A01)+parseInt(row.A01A08A03A01)+parseInt(row.A01A09A01A01);

        return '<font color="blue">'+total+'</font>';
    }


    function queryDetail(){
        $('#hmStaffList').datagrid('unselectAll');
        $('#hmStaffList').datagrid({
            onClickRow: function(rowIndex, rowData){
                var url = "hmStaffController.do?forAnsily&deptCode=" + rowData.deptCode;
                if (rowData) {
                    //url += '&brandName=' + rowData.brandCode;
                    $("iframe").attr("src",url);
                    $("iframe").attr("scrolling","auto");
                }else{
                }
            }
        });
    }
</script>