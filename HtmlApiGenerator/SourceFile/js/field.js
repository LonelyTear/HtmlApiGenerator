$(document).ready(function(){
	$(".field").mouseover(function(){
		var v1 = $(this).attr("id");
		//alert( v1 );
		v1 = "#"+v1+v1;
		//alert(v1);
		$(v1).show("slow");
	});
	$(".field").mouseout(function(){
		var v2 = $(this).attr("id");
		//alert( v2 );
		v2 = "#"+v2+v2;
		//alert(v2);
		$(v2).hide("slow"); 
	});
	
	$(".img").mouseover(function(){
		var v2 = $(this).attr("src");
		alert( v2 );
//		$(this).Attr = 
		//alert(v2);
//		$(v2).hide("slow"); 
	});
});