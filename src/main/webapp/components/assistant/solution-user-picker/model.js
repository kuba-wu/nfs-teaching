var SolutionUserPickerModel = function(params) {
	
	var self = this;
	
	self.solution = ko.observable().publishOn(params.view+"_solution", true);
	self.system = ko.observable().publishOn(params.view+"_system", true);
	
	self.solutions = ko.observableArray();
	
	ko.postbox.subscribe(params.view+"_exercise", function(exercise) {
		self.selectExercise(exercise);
	});	
	
	self.selectSolution = function(solution) {
		
		console.debug("SOLUSERPICK: selected solution, publishing system");
		self.system({system: JSON.stringify(solution.system), hasStructuralChange : true});
		self.solution(solution);
	}
	
	self.selectExercise = function(exercise) {
		
		MainModel.showOverlay();
		$.get("/nfs/api/solution/user/any/exercise/"+exercise.id)
		.done(function(solutions) {
			
			self.solutions(solutions);
			self.solution(null);
			MainModel.hideOverlay();
		})
		.fail(function() {
			MainModel.displayErrorAlert("Could not load all solutions for the exercise.");
			MainModel.hideOverlay();
		});
	};
};

$(document).ready(function() {
	ko.components.register('solution-user-picker', {
	    template: {require: 'text!components/assistant/solution-user-picker/template.html'},
	    viewModel: SolutionUserPickerModel
	});
});