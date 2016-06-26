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
	console.log(user);
	if($("#confirm_password").val() != $("#new_password").val()) {
		swal("Passwords do not match");
		return false;
	} else if ($("#password").val() != user.password) {
		swal("Your password was incorrect.");
		return false;
	}
	return true;
}