			
$(function() {
	$("#html_pagination").html(html_pagination);
	$("#html_pagination_").html(html_pagination);

	$('#footable-res2').footable();
});


function bindingPagination(url) {
	$("#btn-search").on("click", function() {
		postPagination("/media/" + url + "_0_" + $("[name='size']").val());
	});

	$("#go1").on("click", function() {
		postPagination("/media/" + url + "_" + $("#to_page1").val() + "_" + $("[name='size']").val());
	});

	$("#go2").on("click", function() {
		postPagination("/media/" + url + "_" + $("#to_page2").val() + "_" + $("[name='size']").val());
	});	
}

function postPagination(url) {
	$("#form-pagination").attr("action", url);
	$("#form-pagination").submit();
}

function remark(type) {
	$("[name='btn-remark']").on("click", function(){
		var trigger = $(this);
		var id = trigger.siblings(":eq(0)").val();
		var remark = trigger.siblings(":eq(1)").val();
		var remark_ = trigger.siblings(":eq(2)").val();
	
		$.ajax({
			url: "/media/ajaxRemark",
			data: JSON.stringify({
			id: parseInt(id),
			type: type,
			remark: remark,
			remark_ : remark_
			}),
			dataType: "json",
			type: "POST",
			contentType: "application/json",
			success: function (data) {
				var dialog;
				if(data.verify) {
					dialog = trigger.parent().siblings(".alert-success")
				} else {
					dialog = trigger.parent().siblings(".alert-danger")
				}
				dialog.children("strong").html(data.message);
				dialog.show();
				setTimeout(function(){ dialog.hide(); }, 2000);
			}
		}); 
	});
}

var galley = document.getElementById('galley');

var viewer = new Viewer(galley, {
	url : 'data-original',
	toolbar : {
		oneToOne : true,

		prev : function() {
			viewer.prev(true);
		},

		play : true,

		next : function() {
			viewer.next(true);
		},
	},
});

