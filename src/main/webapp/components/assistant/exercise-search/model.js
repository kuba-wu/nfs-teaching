var ExerciseSearchModel = function(params) {
	
	var self = this;

	self.containerId = params.view+"_exercise-search";
	
	self.tasks = ko.observableArray();
	self.searchTask = ko.observable();
	self.searchDateStart = ko.observable(null);
	self.searchDateEnd = ko.observable(null);
	
	ko.postbox.subscribe("global"+"_tasks-changed", function(ts) {
		self.loadAllTasks();
	});
	ko.postbox.subscribe("global"+"_exercises-changed", function(ts) {
		self.search();
	});
	
	self.exercises = ko.observable().publishOn(params.view+"_exercises", true);	

	self.search = function() {
		
		var filter = {
				taskId : self.searchTask().id,
				start : self.searchDateStart(),
				end : self.searchDateEnd()
		};
		MainModel.showOverlay();
		$.ajax({
			  type: "POST",
			  url: "/nfs/api/exercises/filter",
			  data: JSON.stringify(filter),
			  contentType : "application/json"
		})
		.done(function(exercises) {
			self.exercises(exercises);
		})
		.fail(function() {
			MainModel.displayErrorAlert("Could not perform exercise search.");
		})
		.always(function() {
			MainModel.hideOverlay();
		});
	};
	
	self.loadAllTasks = function() {
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
		self.loadAllTasks();
	}, true);
};

$(document).ready(function() {
	ko.components.register('exercise-search', {
	    template: {require: 'text!components/assistant/exercise-search/template.html'},
	    viewModel: ExerciseSearchModel
	});
});