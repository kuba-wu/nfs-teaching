var TaskManagementModel = function(params){

	var self = this;
	
	self.system = ko.observable().syncWith(params.view+"_system", false, true);
	
	self.exercisesChanged = ko.observable().publishOn("global"+"_exercises-changed", false);
	self.tasksChanged = ko.observable().publishOn("global"+"_tasks-changed", false);
	
	self.task = ko.observable().subscribeTo(params.view+"_task");
	ko.postbox.subscribe(params.view+"_task", function(task) {
		if (task) {
			self.setSystem(task.system);
		}
	});
	
	self.setSystem = function(system) {
		
		console.debug("TASKMAN: publishing system");
		self.system({system : JSON.stringify(system), hasStructuralChange : true});
	}
	
	self.remove = function() {
		MainModel.showOverlay();
		$.ajax({
			  url: "/nfs/api/task/"+self.task().id,
			  type: "DELETE",
			  success: function() {
				  self.task(null);
				  
				  self.tasksChanged(new Date());
				  self.exercisesChanged(new Date());
				  
				  MainModel.displaySuccessAlert("Task removed.");
				  MainModel.hideOverlay();
			  },
			  error: function(xhr){
				  MainModel.displayErrorAlert("Could not remove task.");
				  MainModel.hideOverlay();
			  }
		});
	};
	
	self.save = function() {
		MainModel.showOverlay();
		var task = self.task();
		task.system = JSON.parse(self.system().system);
		$.ajax({
			  url: "/nfs/api/task",
			  type: "POST",
			  data: JSON.stringify(task),
			  contentType: "application/json",
			  success: function() {
				  
				  self.tasksChanged(new Date());
				  self.exercisesChanged(new Date());
				  
				  MainModel.displaySuccessAlert("Task saved");
				  MainModel.hideOverlay();
			  },
			  error: function(xhr){
				  MainModel.displayErrorAlert("Could not save task.");
				  MainModel.hideOverlay();
			  }
		});
	};
	
	self.createNew = function() {
		
		var newSystem = {
				id: null,
				components : [],
				receptors : [],
				init : {}
		};
		
		self.task({
			name: "New Task", 
			description: "Please add some...",
			public: true,
			system: newSystem
		});
		self.setSystem(newSystem);
	};
};

$(document).ready(function() {
	ko.components.register('task-management', {
	    template: {require: 'text!components/assistant/task-management/template.html'},
	    viewModel: TaskManagementModel
	});
});