var SolutionReviewModel = function(params){

	var self = this;
		
	self.exercises = ko.observableArray().subscribeTo(params.view+"_exercises");
	self.solution = ko.observable().subscribeTo(params.view+"_solution");
	
	self.focusSearch = function() {
		$("#"+params.view+"_exercise-search").focus();
	};
};

$(document).ready(function() {
	ko.components.register('solution-review', {
	    template: {require: 'text!components/assistant/solution-review/template.html'},
	    viewModel: SolutionReviewModel
	});
});