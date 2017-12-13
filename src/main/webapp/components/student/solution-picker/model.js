var SolutionPickerModel = function(params) {
	
	var self = this;
	
	self.solution = ko.observable().publishOn(params.view+"_solution", true);
	self.system = ko.observable().publishOn(params.view+"_system", true);
	
	ko.postbox.subscribe(params.view+"_exercise", function(exercise) {
		self.selectExercise(exercise);
	});	
	
	self.selectExercise = function(exercise) {
		
		MainModel.showOverlay();
		$.get("/nfs/api/solution/user/current/exercise/"+exercise.id)
		.done(function(solution) {
			
			console.debug("SOLPICK: loaded solution, publishing system.")
			self.solution(solution);
			self.system({system : JSON.stringify(solution.system), hasStructuralChange : true});
			MainModel.hideOverlay();
		})
		.fail(function() {
			MainModel.displayErrorAlert("Could not load selected solution.");
			MainModel.hideOverlay();
		});
	};
};

$(document).ready(function() {
	ko.components.register('solution-picker', {
	    template: {require: 'text!components/student/solution-picker/template.html'},
	    viewModel: SolutionPickerModel
	});
});