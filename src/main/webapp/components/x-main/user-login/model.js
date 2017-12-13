var UsersModel = {

	user : ko.observable().publishOn("user", false, function() {return false;}),
	isUserPassword : ko.observable(false),
	hasRole : function(role) {
		var user = (UsersModel ? UsersModel.user() : null);
		return (user && ($.inArray(role, user.roles) != -1));
	},
	
	getLoggedInUser : function() {
		$.get("/nfs/api/users/current")
		.done(function(user) {
			
			UsersModel.user(user);
			MainModel.clearErrorAlert();
		})
		.fail(function() {
			// no user - remove session leftovers
			Token.clear();
			MainModel.displayErrorAlert("Could not get user data.");
		})
		.always(function() {
			MainModel.hideOverlay();
		});
	},

	restoreSession : function() {
		if (Token.validate()) {
			UsersModel.getLoggedInUser();
		}
	},
	
	requestToken : function(requestData) {

			MainModel.showOverlay();
			
			$.ajax({
			    url: '/nfs/api/oauth/token',
			    type: 'post',
			    data: requestData,
			    headers: {
			    	"Content-Type": "application/x-www-form-urlencoded",
	                "Accept": "application/json",
	                "Authorization": "Basic bmZzLWFwcDpteVNlY3JldE9BdXRoU2VjcmV0"
			    },
			    dataType: 'json',
			    success: function (token) {
			    	Token.set(token);
			    	UsersModel.getLoggedInUser();
			    },
			    error: function() {
			    	MainModel.displayErrorAlert("Could not login user.");
			    	MainModel.hideOverlay();
			    }
			});
	},
	
	login : function() {
		var login = $("#user").val();
		var password = $("#password").val();
		var data = "username=" + login + "&password=" + password + "&grant_type=password&scope=read%20write";
		UsersModel.requestToken(data);
	},
	
	logout : function() {
		Token.clear();
		UsersModel.isUserPassword(false);
		UsersModel.user(null);
		MainModel.clearErrorAlert();
		window.location.href = MainModel.selfUrl();
	},
	
	selectUserPassMode : function() {
		UsersModel.isUserPassword(true);
	},
	
	goToSSOAccountLogin : function() {
		UsersModel.isUserPassword(false);
		window.location.href = "https://login.uj.edu.pl/uj_ldap/login?service="+MainModel.selfUrl()	;
	},
	
	performSamlAuthentication : function() {
		var token = $.url('?ticket');
		if (token) {
			var data = "token="+ token + "&grant_type=password&scope=read%20write";
			UsersModel.requestToken(data);
		}
	},
	
	init : function() {
		UsersModel.performSamlAuthentication();
		UsersModel.restoreSession();
	}
};

$(document).ready(function() {
	ko.components.register('user-login', {
	    template : {require: 'text!components/x-main/user-login/template.html'},
	    viewModel : {instance : UsersModel}
	});
});