$(function() {

	// 模拟文件上传按钮的点击事件
	function mockclickupload() {
		$("#fileupload_input").click();
	}

	// 监听“上传照片”按钮的点击事件
	$("#upload_btn").on("click", function() {
				mockclickupload();
			});

	// 提交表单
	function submitForm() {
		if (!$("#description").val().trim()) {
			alert("请填写个人简介");
			return;
		}

		if (!$("#qq").val().trim() && !$("#weixin").val().trim()) {
			alert("请至少填写QQ或者微信中的一项");
			return;
		}

		var vals = $(" :input").serialize();
		console.log(vals);

		var table_items = $("#basic_info_table :input");
		for (var index = 0; index < table_items.length; index++) {
			if (!$(table_items[index]).val()) {
				alert("请填写" + $(table_items[index]).attr("alias"));
				return;
			}
		}

		$.post("/user/add_user", vals, function(data) {
					alert('success');
					location.href = "user/show";
				}, "json");
	}

	// 监听“保存”按钮的点击事件
	$("a.btn1.fr").on("click", function() {
				submitForm();
			});

	$("#fileupload_input").fileupload({
		url : "/user/upload_pic",// 文件上传地址，当然也可以直接写在input的data-url属性内
		formData : {
			user_id : "user_id"
		},// 如果需要额外添加参数可以在这里添加
		done : function(e, result) {
			// done方法就是上传完毕的回调函数，其他回调函数可以自行查看api
			// 注意result要和jquery的ajax的data参数区分，这个对象包含了整个请求信息
			// 返回的数据在result.result中，假设我们服务器返回了一个json对象
			console.log(JSON.stringify(result.result));
			var data = result.result;
			if (data.success) {
				alert("上传成功");

				var photoDiv = "<img src=";
				photoDiv += '"';
				photoDiv += data.photo_url;
				photoDiv += '"';
				photoDiv += ' width="200px" height="250px" style="padding:5px;" />';

				if ($("#upload_picture")) {
					$("#upload_picture").remove();
				}
				$("#picture_wall").append(photoDiv);
			}

		}
	});

	$(function() {

				$("#birthday").datepicker({
					changeMonth : true,
					changeYear : true,

					// minDate : "-38Y",
					// maxDate : "-18Y",

					yearRange : '-40:-18',

					defaultDate : "-28Y",

					monthNamesShort : ["一月", "二月", "三月", "四月", "五月", "六月",
							"七月", "八月", "九月", "十月", "十一月", "十二月"],

					dayNamesShort : ["日", "一", "二", "三", "四", "五", "六"],
					dayNamesMin : ["日", "一", "二", "三", "四", "五", "六"],

					regional : "zh-TW",
					dateFormat : "yy年mm月dd日"
				});

			});

});