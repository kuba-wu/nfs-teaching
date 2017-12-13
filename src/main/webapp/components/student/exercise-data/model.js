var ExerciseDataModel = function(params) {
	
	var self = this;
	self.containerId = params.view+"_exerciseData";
	
	self.solution = ko.observable().syncWith(params.view+"_solution", false, true);
	self.system = ko.observable().syncWith(params.view+"_system", false, true);

	self.isActive = ko.computed(function() {
		var exercise = (self.solution() ? self.solution().exercise : null);
		var current = (new Date()).getTime();
		return (exercise && exercise.start < current && exercise.end > current);
	});
	
	self.save = function() {
		MainModel.showOverlay();
		self.solution().system = JSON.parse(self.system().system);
		$.ajax({
			  url: "/nfs/api/solution",
			  type: "POST",
			  data: JSON.stringify(self.solution()),
			  contentType: "application/json",
			  success: function(){
				  MainModel.displaySuccessAlert("Solution saved.");
				  MainModel.hideOverlay();
			  },
			  error: function(xhr){
				  MainModel.displayErrorAlert("Could not save the solution.");
				  MainModel.hideOverlay();
			  }
		});
	};
	
	self.reset = function() {
		MainModel.showOverlay();
		$.get("/nfs/api/solution/"+self.solution().id+"/reset")
		.done(function(solution) {
			
			console.debug("EXERCISE: reset, publishing system.");
			
			self.system({system: JSON.stringify(solution.system), hasStructuralChange : true});
			self.solution(solution);
			
			MainModel.displaySuccessAlert("Solution reset to original values.");
			MainModel.hideOverlay();
		})
		.fail(function() {
			MainModel.displayErrorAlert("Could not reset selected exercise.");
			MainModel.hideOverlay();
		});
	}
};

$(document).ready(function() {
	ko.components.register('exercise-data', {
	    template: {require: 'text!components/student/exercise-data/template.html'},
	    viewModel: ExerciseDataModel
	});
});