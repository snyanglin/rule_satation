function init(){
	//点击事件
	$(".menu").click(function(){
		$(".menu").removeClass("menu_click");
	    $(this).addClass("menu_click");	      
	});
	
	$(".menu").mouseover(function(){		
	    $(this).addClass("menu_mouseover");	      
	});
	
	$(".menu").mouseout(function(){
		$(this).removeClass("menu_mouseover");	         
	}); 
}

var main_center = window.parent.frames["main_center"];

function menuClick(url){
	main_center.location=url;
}