var paramnameAry="";
var paramclassAry="";
var parambzAry="";

function addParam(){
		var paramTable=$("#paramTable");
		var str="<tr><td><input type=\"text\"name=\"paramname\" value=\"\" maxlength=\"100\" class=\"form-control\"  /></td>"+
			"<td><input type=\"text\" name=\"paramclass\" value=\"\" maxlength=\"100\" class=\"form-control\"  /></td>"+
			"<td><input type=\"text\" name=\"parambz\" value=\"\" maxlength=\"100\" class=\"form-control\"  /></td>"+
			"<td><button type=\"button\" class=\"btn btn-default\" onclick=\"delParam(this)\">删除</button></td></tr>";
		paramTable.append(str);
}
	
function delParam(obj){
		var aTag=$(obj);
		aTag.parent().parent().remove();
}
	
function getParam(){
		var paramTable=$("#paramTable");
		var trAry=paramTable.find("tr");
		for(var i=1;i<trAry.length;i++){
			paramnameAry  +=$(trAry[i]).find("input[name='paramname']").val()+">>";
			paramclassAry +=$(trAry[i]).find("input[name='paramclass']").val()+">>";
			parambzAry    +=$(trAry[i]).find("input[name='parambz']").val()+">>";
		}			
}