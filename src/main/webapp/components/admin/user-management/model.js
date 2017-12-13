var UserManagementModel = function(params){
	
	var self = this;
	
	self.usersChanged = ko.observable().publishOn("global"+"_users-changed");
	self.user = ko.observable().subscribeTo(params.view+"_selected-user");
	ko.postbox.subscribe(params.view+"_selected-user", function(user) {
		self.selectedRoles(user.roles);
		self.changePassword(false);
	});
	
	self.allRoles = ["ROLE_ADMIN", "ROLE_STUDENT", "ROLE_ASSISTANT"];
	self.selectedRoles = ko.observable();
	
	self.changePassword = ko.observable(false);
	self.password = ko.observable();
	
	self.remove = function() {
		MainModel.showOverlay();
		$.ajax({
			  url: "/nfs/api/users/",
			  type: "DELETE",
			  data: self.user().login,
			  success: function() {
				  self.user(null);
				  self.usersChanged(new Date());
				  
				  MainModel.displaySuccessAlert("User removed.");
				  MainModel.hideOverlay();
			  },
			  error: function(xhr){
				  MainModel.displayErrorAlert("Could not remove user.");
				  MainModel.hideOverlay();
			  }
		});
	};
	
	self.save = function() {
		MainModel.showOverlay();
		var user = self.user();
		user.roles = self.selectedRoles();
		user.password = self.password();
		$.ajax({
			  url: "/nfs/api/users",
			  type: "PUT",
			  data: JSON.stringify(user),
			  contentType: "application/json",
			  success: function() {
				  self.usersChanged(new Date());
				  MainModel.displaySuccessAlert("User updated.");
				  MainModel.hideOverlay();
			  },
			  error: function(xhr){
				  MainModel.displayErrorAlert("Could not update user.");
				  MainModel.hideOverlay();
			  }
		});
	};
};

$(document).ready(function() {
	ko.components.register('user-management', {
	    template: {require: 'text!components/admin/user-management/template.html'},
	    viewModel: UserManagementModel
	});
});