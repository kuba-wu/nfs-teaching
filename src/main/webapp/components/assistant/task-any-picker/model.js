var TaskAnyPickerModel = function(params) {
	
	var self = this;
	
	self.name = params.name;
	
	self.tasks = ko.observableArray();
	self.task = ko.observable().publishOn(params.view+"_task", true, function() {return false;});
	
	self.selectTask = function(task) {
		self.task(task);
	};
	
	self.loadTasks = function() {
		if (UsersModel.hasRole("ROLE_ASSISTANT")) {
			$.get("/nfs/api/task")
			.done(function(tasks) {
				self.tasks(tasks);
			})
			.fail(function() {
				MainModel.displayErrorAlert("Could not load available tasks.");
			});
		}
	};
	
	ko.postbox.subscribe("user", function(user) {
		self.loadTasks();
	}, true);
	
	ko.postbox.subscribe("global"+"_tasks-changed", function(ts) {
		self.loadTasks();
	});
};

$(document).ready(function() {
	ko.components.register('task-any-picker', {
	    template: {require: 'text!components/assistant/task-any-picker/template.html'},
	    viewModel: TaskAnyPickerModel
	});
});