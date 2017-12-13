var UserSearchModel = function(params){
	
	var self = this;
	
	self.users = ko.observableArray();
	self.selectedUser = ko.observable().publishOn(params.view+"_selected-user", true);
	
	self.query = ko.observable('');
	self.filteredUsers = ko.computed(function() {
        return ko.utils.arrayFilter(self.users(), function(user) {
            return (user.login.toLowerCase().indexOf(self.query().toLowerCase()) >= 0);
        });
    });
		
	self.loadAllUsers = function() {
		$.get("/nfs/api/users")
			.done(function(users) {
				self.users(users);
			})
			.fail(function() {
				MainModel.displayErrorAlert("Could not load all users.");
			});;
	};
	
	ko.postbox.subscribe("global"+"_users-changed", function(ts) {
		if (UsersModel.hasRole("ROLE_ADMIN")) {
			self.loadAllUsers();
		};
	});
	
	ko.postbox.subscribe("user", function(loggedIn) {
		if (UsersModel.hasRole("ROLE_ADMIN")) {
			self.loadAllUsers();
		};
	}, true);
	
	self.select = function(user) {
		self.selectedUser(user);
	};
};

$(document).ready(function() {
	ko.components.register('user-search', {
	    template: {require: 'text!components/admin/user-search/template.html'},
	    viewModel: UserSearchModel
	});
});