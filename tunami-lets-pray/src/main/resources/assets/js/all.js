var url = window.location.href;
if (url.indexOf("lords-prayer") > 0) {
	$("#navbar-collapse a").eq(1).css("color", "#e75854");
} else if(url.indexOf("prophet") > 0) {
	$("#navbar-collapse a").eq(2).css("color", "#e75854");
} else if(url.indexOf("anthem") > 0) {
	$("#navbar-collapse a").eq(3).css("color", "#e75854");
} else if(url.indexOf("holy-orders") > 0) {
	$("#navbar-collapse a").eq(4).css("color", "#e75854");
} else {
	$("#navbar-collapse a:first").css("color", "#e75854");
}
