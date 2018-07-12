//导航选择器
var url = window.location.href;
if (url.indexOf("newsCategoryManage") > 0 || url.indexOf("newsCategoryEdit") > 0) {
	$("#news").addClass("active");
	$("#newsCategoryManage").addClass("active");
} else if (url.indexOf("newsManage") > 0 || url.indexOf("newsEdit") > 0) {
	$("#news").addClass("active");
	$("#newsManage").addClass("active");
}

