var AddUserModel = function(params){

	var self = this;
	
	self.newUser = ko.observable();
	self.usersChanged = ko.observable().publishOn("global"+"_users-changed", false);
	
	self.addNewUser = function() {
		MainModel.showOverlay();
		$.ajax({
			  url: "/nfs/api/users",
			  type: "POST",
			  data: self.newUser(),
			  contentType: "text/plain",
			  success: function(user) {
				  
				  self.usersChanged(new Date());
				  MainModel.displaySuccessAlert("New user "+user.login+" created.");
				  MainModel.hideOverlay();
			  },
			  error: function(xhr){
				  MainModel.displayErrorAlert("Could not create new user.");
				  MainModel.hideOverlay();
			  }
		});
	}
};

$(document).ready(function() {
	ko.components.register('add-user', {
	    template: {require: 'text!components/shared/add-user/template.html'},
	    viewModel: AddUserModel
	});
});