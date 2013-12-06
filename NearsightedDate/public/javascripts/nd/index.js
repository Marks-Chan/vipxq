// JavaScript Document

$(function()
{
	
	//TAB滑动特效
	/*
	a： TAB选项框
	b： TAB内容框
	c：	TAB选项框激活样式
	*/
	var tab_js = function(a,b,c)
	{
			var $tab_P1 = a;
			var $tab_S1 = a.find("li");
			var $tab_P2 = b;
			var $tab_S2 = b.find(".tabContent");
			var $tab_hover = c;
			var $index = 0;
			var _this = this;
			
			function Run(e)
			{
				$tab_S1.removeClass(c);
				$tab_S1.eq(e).addClass(c);
				$tab_S2.hide();
				$tab_S2.eq(e).show();
			}
			
			$tab_S1.hover(function()
			{
				$index = $tab_S1.index($(this)[0]);
				Run($index);
			},function()
			{
				
			});
			Run($index);
	}
	
	/* b1 - 网站公告、最新活动 */
	tab_js($(".LO_1_c_a_a ul.z1"),$(".LO_1_c_a_a"),"hover1");
	/* b1 - 最新活动、新品推荐... */
	tab_js($(".LO_1_c_b ul.z1"),$(".LO_1_c_b"),"hover2");
	/* b2 - */
	tab_js($(".LO_1_d .t1 ul.z1"),$(".LO_1_d .t1"),"hover3");
	tab_js($(".LO_1_d .t2 ul.z1"),$(".LO_1_d .t2"),"hover4");
	tab_js($(".LO_1_d_a ul.z1"),$(".LO_1_d_a"),"hover1");
	/* b3 - */
	tab_js($(".LO_1_d .t3 ul.z1"),$(".LO_1_d .t3"),"hover5");
	/* b4 - */
	tab_js($(".LO_1_d .t4 ul.z1"),$(".LO_1_d .t4"),"hover6");
	var b4_right_js = function()
	{
		function Run(e)
		{
			$(".LO_1_f_a_a dl.each").find("dt span").removeClass("bg1");
			$(".LO_1_f_a_a dl.each").find("dd.s1").hide();
			$(".LO_1_f_a_a dl.each").find("dd.s2").hide();
			$(".LO_1_f_a_a dl.each").find("dd.s3").show();
			$(".LO_1_f_a_a dl.each").eq(e).find("dt span").addClass("bg1");
			$(".LO_1_f_a_a dl.each").eq(e).find("dd.s1").show();
			$(".LO_1_f_a_a dl.each").eq(e).find("dd.s2").show();
			$(".LO_1_f_a_a dl.each").eq(e).find("dd.s3").hide();
		}
		
		$(".LO_1_f_a_a dl.each").hover(function()
		{
			var index = $(".LO_1_f_a_a dl.each").index($(this)[0]);
			Run(index);
		},function()
		{
			
		});
		Run(0);
	}
	b4_right_js();
	
	/* 顶部右边导航JS */
	var topNav = function()
	{
		$(".LO_1_a_a .z2 li.s1").hover(function()
		{
			$(".LO_1_a_a .z2 li.s1 .son").hide();
			$(this).find(".son").fadeIn();
		},function()
		{
			$(".LO_1_a_a .z2 li.s1 .son").hide();
		});	
	}
	topNav();
	
});