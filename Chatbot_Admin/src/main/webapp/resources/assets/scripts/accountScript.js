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

function createNewAccount() {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			if (xmlhttp.responseText.indexOf('success') > 0) {
				swal({title: "Good job!", text: xmlhttp.responseText, type: "success"}, 
						function() {
							setTimeout(function() {
								location.reload();
							}, 500);
							
						}
					);
			} else {
				swal("Sorry!", xmlhttp.responseText, "error");
			}
		}
	}

	xmlhttp.open("POST", "/chatbot_admin/addAccount", true);
	xmlhttp.setRequestHeader("Content-type",
	"application/x-www-form-urlencoded;charset=utf-8");
	xmlhttp.send($("#inputAccount").attr("name") + "=" + $("#inputAccount").val() + "&"
			+ $("#checkbox-isadmin").attr("name") + "=" + $("#checkbox-isadmin").is(':checked') + "&"
			+ $("#token").attr("name") + "=" + $("#token").val());
}

function checkConfirmPassword() {
	if($("#confirm_password").val() != $("#new_password").val()) {
		swal("Oops","Passwords does not match!", "warning");
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

function updateUserInfo() {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			if (xmlhttp.responseText.indexOf('success') > 0) {
				swal({title: "Good job!", text: xmlhttp.responseText, type: "success"}, 
						function() {
					setTimeout(function() {
						location.reload();
					}, 500);
				}
					);
			} else {
				swal("Sorry!", xmlhttp.responseText, "error");
			}
		}
	}

	xmlhttp.open("POST", "/chatbot_admin/updateInfo", true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded;charset=utf-8");
	xmlhttp.send($("#user-phone").attr("name") + "=" + $("#user-phone").val() + "&"
			+ $("#user-email").attr("name") + "=" + $("#user-email").val() + "&"
			+ $("#user-address").attr("name") + "=" + $("#user-address").val() + "&"
			+ $("#token").attr("name") + "=" + $("#token").val());
}