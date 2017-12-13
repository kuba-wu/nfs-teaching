var UserSelectorModel = function(params){

	var self = this;
	
	self.usersId = params.view+"_assigned-users"
	
	self.assignedUsersFrom =  "#"+self.usersId;;
	self.assignedUsersTo = "#"+self.usersId+"_to";
	
	self.exercise = ko.observable().subscribeTo(params.view+"_exercise");
	self.selectedUsers = ko.observableArray().publishOn(params.view+"_selected-users", true);
	
	ko.postbox.subscribe("global"+"_users-changed", function(ts) {
		if (self.exercise()) {
			self.populateAssignedFrom(self.exercise().assignees);
		}
	});
	
	ko.postbox.subscribe(params.view+"_exercise", function(exercise) {
		if (exercise) {
			self.populateAssignedFrom(exercise.assignees);
			self.populateAssignedTo(exercise.assignees);
			self.createMultiselect();
		}
	});
		
	self.createMultiselect = function() {
		$("[name='user-search']").remove();
		$(self.assignedUsersFrom).multiselect({
			search: {
				left: '<input type="text" name="user-search" class="form-control" placeholder="Search..." />',
				right: '<input type="text" name="user-search" class="form-control" placeholder="Search..." />'
			},
			afterMoveToRight: self.usersSelected,
			afterMoveToLeft: self.usersUnselected
		});
	}
	
	self.populateAssignedTo = function(users) {
		
		$(self.assignedUsersTo).empty();
		$.each(users, function (index, user) {
			self.addToAssignedTo(user);
		});
	};
	
	self.optionToUser = function(option) {
		return {login: option.value};
	}
	
	self.addToAssignedTo = function(user) {
		$(self.assignedUsersTo).append($('<option>', {
		    value: user.login,
		    text: user.login
		}));
		self.selectedUsers.push({login: user.login});
	};
	
	self.usersSelected = function(left, right, options) {
		if (options.value) {
			self.selectedUsers.push(self.optionToUser(options));
		} else {
			$.each(options, function (index, option) {
				if ($.isNumeric(index)) {
					self.selectedUsers.push(self.optionToUser(option));
				}
			});
		}
	};
	
	self.usersUnselected = function(left, right, options) {
		if (options.value) {
			self.selectedUsers.remove(function(item) {return item.login == options.value;});
		} else {
			$.each(options, function (index, option) {
				if ($.isNumeric(index)) {
					self.selectedUsers.remove(function(item) {return item.login == option.value;});
				}
			});
		}
	};
	
	self.isUserAssigned = function(user, assigned) {
		for (var index=0; index < assigned.length; index++) {
			var assignee = assigned[index];
			if (user.login == assignee.login) {
				return true;
			}
		}
		return false;
	};
	
	self.populateAssignedFrom = function(assignedUsers) {
		MainModel.showOverlay();
		$.get("/nfs/api/users")
			.done(function(users) {
				$(self.assignedUsersFrom).empty();
				$.each(users, function(index, user) {
					if (!self.isUserAssigned(user, assignedUsers)) {
						$(self.assignedUsersFrom).append($('<option>', {
						    value: user.login,
						    text: user.login
						}));
					}
				});
			})
			.fail(function() {
				MainModel.displayErrorAlert("Could not load all users.");
			})
			.always(function() {
				MainModel.hideOverlay();
			});
	};
};

$(document).ready(function() {
	ko.components.register('user-selector', {
	    template: {require: 'text!components/assistant/user-selector/template.html'},
	    viewModel: UserSelectorModel
	});
});