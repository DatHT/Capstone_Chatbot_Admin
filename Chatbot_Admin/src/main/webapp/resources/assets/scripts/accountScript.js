/**
 * 
 */
$(document).ready(function(){
	$("#data-table-basic").bootgrid({
		css: {
				icon: 'zmdi icon',
				iconColumns: 'zmdi-view-module',
				iconDown: 'zmdi-expand-more',
				iconRefresh: 'zmdi-refresh',
				iconUp: 'zmdi-expand-less'
			},
	});
});

function checkConfirmPassword() {
	if($("#confirm_password").val() != $("#new_password").val()) {
		swal("Passwords do not match");
	} else {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				if (xmlhttp.responseText.indexOf('success') > 0) {
					swal("Good job!", xmlhttp.responseText, "success");
					closeModalDialog();
				} else {
					swal("Sorry!", xmlhttp.responseText, "error");
				}
			}
		}

		xmlhttp.open("POST", "/chatbot_admin/changePassword", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded;charset=utf-8");
		xmlhttp.send("password=" + $("#password").val() + "&"
				+ "new_password=" + $("#new_password").val() + "&"
				+ $("#token").attr("name") + "=" + $("#token").val());
	}
}