$(document).ready(function(){
	$(".button_normal").mouseover(function(){		
	    $(this).css("background-image","url(../images/button_normal_over.png)");	      
	});
	
	$(".button_normal").mouseout(function(){
		$(this).css("background-image","url(../images/button_normal.png)");
	}); 
});