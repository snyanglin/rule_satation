(function($){
	$.fn.parsexh = function(options){
		var defaults = {
			param:{
				id:null
			},
			xmlURL:null,
			success:function(){}
		};
		var opts = $.extend({},defaults,options);
		
		var $_this = $(this);
		
		opts.xmlURL = opts.xmlURL==""?null:opts.xmlURL;
		$.get(opts.xmlURL,function(data){
			var html = _parse(data);
			//console.log(html[0]);
			$_this.html(html);
			opts.success();
		});
		
		
		/*解析数据*/
		function _parse(data){
			var cons = $(data).find("queryConfiguration");
			if(opts.param){
				if(opts.param.id){		//根据ID来筛选queryConfiguration
					cons = $(data).find("queryConfiguration[id='"+opts.param.id+"']");
				}
			}
			if(cons.length>1){
				console.log("id 非唯一");
				return null;
			}else if(cons.length==0){
				console.log("id 无此ID");
				return null;
			}
			var dom = $('<div></div>');
			cons.each(function(){		//迭代复合条件的queryConfiguration子元素
				var _queryC = $(this);
				
				//各queryConfigureation参数
				var url = _queryC.attr("url") ? _queryC.attr("url"):null,
					id = _queryC.attr("id")	  ? _queryC.attr("id"):null,
					view = _queryC.attr("view") ? _queryC.attr("view"):null;
				
				//使用获取到的参数
				$_this.attr("action",url);
				$_this.attr("id",id);
				
				var _qch = _queryC.children(); 
				_qch.each(function(){		//每个field
					var _field = $(this);
					
					//每个field的各属性
					var fieldName    = _field.attr("fieldName")    ?_field.attr("fieldName")         :null,
						validateType = _field.attr("validateType") ?_field.attr("validateType")      :null,
						fieldLength  = _field.attr("fieldLength")  ?_field.attr("fieldLength")       :null,
						linkType     = _field.attr("linkType")     ?_field.attr("linkType")          :null,
						inputType    = _field.attr("inputType")    ?_field.attr("inputType")         :null,
						fieldId      = _field.attr("fieldId")      ?_field.attr("fieldId")           :null,
						queryInterface = _field.attr("queryInterface")?_field.attr("queryInterface") :null;
					var obj = null;
					if(inputType!=null){
						if(inputType=="text")obj = $('<input class="easyui-validatebox" type="text"/>');
						else if(inputType=="hidden")obj = $('<input type="hidden"/>');
						else if(inputType=="select")obj = $('<select class="easyui-combobox" panelHeight="auto" data-options="required:true"></select>');
						else if(inputType=="button")obj = $('<button></button>');
						else if(inputType=="date")obj = $('<input class="easyui-datebox" type="text"/>');
						if(obj!=null){
							if(fieldLength!=null)obj.attr("maxLength",fieldLength);
							if(fieldId!=null)obj.attr("name",fieldId);
							if(fieldName!=null){
								if(inputType=="button"){
									obj = $("<span class='nameBas'></span>").append(obj.append(fieldName));
								}else{
									if(inputType=="select"){
										var ops = _field.children();
										$.each(ops,function(){
											var cp = $("<option>"+$(this).attr("fieldName")+"</option>");
											cp.attr("value",$(this).attr("optionValue"));
											obj.append(cp);
										});
									}
									obj = $("<span class='nameBas'></span>").append(obj);
									$("<b>"+fieldName+"</b>").prependTo(obj);
								}
							}
							dom.append(obj);
							
						}
					}
					//console.log(_field.attr("validateType")?this:false);
				});
			});
			return dom;
		}
	};
})(jQuery);
