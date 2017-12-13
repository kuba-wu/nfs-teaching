var ExercisesModel = function(params){

	var self = this;
	
	self.containerId = params.view+"_student-exercises";
	
	self.show = function()  {
		$('#'+self.containerId).collapse('show');
	};
		
	self.initialized = false;
	
	ko.postbox.subscribe(params.view+"_system", function(system){
		self.systemSubmitted();
	});
	
	self.systemSubmitted = function() {
		
		if (!self.initialized) {
			self.initialized = true;
			self.show();
		}
	};
};

$(document).ready(function() {
	ko.components.register('exercises', {
	    template: {require: 'text!components/student/exercises/template.html'},
	    viewModel: ExercisesModel
	});
});