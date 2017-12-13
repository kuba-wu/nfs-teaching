var ExerciseManagementModel = function(params){

	var self = this;
	
	self.exercise = ko.observable().syncWith(params.view+"_exercise");
	self.exercises = ko.observableArray().subscribeTo(params.view+"_exercises");
	
	self.users = ko.observableArray().subscribeTo(params.view+"_selected-users");
	self.dateStart = ko.observable();
	self.dateEnd = ko.observable();
	
	self.tasks = ko.observableArray();
	self.selectedTaskId = ko.observable();
	self.selectedTask = function() {
	       return ko.utils.arrayFirst(self.tasks(), function(item) {
	           return self.selectedTaskId() == item.id;
	       });
	   };
	
	ko.postbox.subscribe(params.view+"_exercise", function(exercise) {
		if (exercise) {
			if (exercise.task) {
				self.selectedTaskId(exercise.task.id);
			}
			self.dateStart(new Date(exercise.start));
			self.dateEnd(new Date(exercise.end));
		}
	});

	self.exercisesChanged = ko.observable().publishOn("global"+"_exercises-changed");
	
	ko.postbox.subscribe("global"+"_tasks-changed", function(ts) {
		self.loadAllTasks();
	});
	
	self.focusSearch = function() {
		$("#"+params.view+"_exercise-search").focus();
	};
	
	self.remove = function() {
		MainModel.showOverlay();
		var exercise = self.exercise();
		$.ajax({
			  url: "/nfs/api/exercises/"+exercise.id,
			  type: "DELETE",
			  success: function() {
				  self.exercise(null);
				  
				  self.exercisesChanged(new Date());
				  MainModel.displaySuccessAlert("Exercise removed.");
				  MainModel.hideOverlay();
			  },
			  error: function(xhr){
				  MainModel.displayErrorAlert("Could not remove exercise.");
				  MainModel.hideOverlay();
			  }
		});
	};
	
	self.save = function() {
		MainModel.showOverlay();
		var exercise = self.exercise();
		exercise.assignees = self.users();
		exercise.task = self.selectedTask();
		exercise.start = self.dateStart();
		exercise.end = self.dateEnd();
		$.ajax({
			  url: "/nfs/api/exercises",
			  type: "POST",
			  data: JSON.stringify(exercise),
			  contentType: "application/json",
			  success: function() {
				  self.exercisesChanged(new Date());
				  MainModel.displaySuccessAlert("Exercise saved");
				  MainModel.hideOverlay();
			  },
			  error: function(xhr){
				  MainModel.displayErrorAlert("Could not save exercise.");
				  MainModel.hideOverlay();
			  }
		});
	};
	
	self.createNew = function() {
		var now = new Date();
		self.exercise({
			start: now.getTime(), 
			end: now.getTime()+24*60*60*1000,
			assignees: []});
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
	ko.components.register('exercise-management', {
	    template: {require: 'text!components/assistant/exercise-management/template.html'},
	    viewModel: ExerciseManagementModel
	});
});