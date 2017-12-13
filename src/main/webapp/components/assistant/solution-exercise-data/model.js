var SolutionExerciseDataModel = function(params){

	var self = this;
	self.exercise = ko.observable().subscribeTo(params.view+"_exercise");
};

$(document).ready(function() {
	ko.components.register('solution-exercise-data', {
	    template: {require: 'text!components/assistant/solution-exercise-data/template.html'},
	    viewModel: SolutionExerciseDataModel
	});
});