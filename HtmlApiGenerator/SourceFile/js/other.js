$(document).ready(function(){
	$(".commerical").mouseover(function(){
		$(this).addClass("commericalHover");
	});
	$(".commerical").mouseout(function(){
		$(this).removeClass("commericalHover");
	});
	$(".compulsory").mouseover(function(){
		$(this).addClass("compulsoryHover");
	});
	$(".compulsory").mouseout(function(){
		$(this).removeClass("compulsoryHover");
	});
	
	$(".policyQuery").mouseover(function(){
		$(this).addClass("policyQueryHover");
	});
	$(".policyQuery").mouseout(function(){
		$(this).removeClass("policyQueryHover");
	});
	$(".premiumCalculation").mouseover(function(){
		$(this).addClass("premiumCalculationHover");
	});
	$(".premiumCalculation").mouseout(function(){
		$(this).removeClass("premiumCalculationHover");
	});
	$(".check").mouseover(function(){
		$(this).addClass("checkHover");
	});
	$(".check").mouseout(function(){
		$(this).removeClass("checkHover");
	});
	$(".localTax").mouseover(function(){
		$(this).addClass("localTaxHover");
	});
	$(".localTax").mouseout(function(){
		$(this).removeClass("localTaxHover");
	});
	$(".request").mouseover(function(){
		$(this).addClass("requestHover");
	});
	$(".request").mouseout(function(){
		$(this).removeClass("requestHover");
	});
	$(".response").mouseover(function(){
		$(this).addClass("responseHover");
	});
	$(".response").mouseout(function(){
		$(this).removeClass("responseHover");
	});
});