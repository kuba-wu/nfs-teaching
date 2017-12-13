var SolutionDataModel = function(params){

	var self = this;
	self.solution = ko.observable().subscribeTo(params.view+"_solution");
};

$(document).ready(function() {
	ko.components.register('solution-data', {
	    template: {require: 'text!components/assistant/solution-data/template.html'},
	    viewModel: SolutionDataModel
	});
});