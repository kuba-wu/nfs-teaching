var ExercisePickerModel = function(params) {
	
	var self = this;
	self.url = "/nfs/api/exercises"+params.url;
	self.name = params.name;
	self.passive = (params.passive ? true : false);
	
	self.exercises = ko.observable().subscribeTo(params.view+"_exercises");	
	self.exercise = ko.observable().publishOn(params.view+"_exercise", true, function() {return false;});
	
	self.selectExercise = function(exercise) {
		self.exercise(exercise);
	};
	
	self.loadExercises = function(url) {
		$.get(url, function(exercises) {
			self.exercises(exercises);
		});
	};
	
	if (!self.passive) {
		ko.postbox.subscribe("global"+"_exercises-changed", function(changed) {
			self.loadExercises(self.url);
		});
		
		ko.postbox.subscribe("user", function(user) {
			if (user != null) {
				self.loadExercises(self.url);
			}
		}, true);
	};
};

$(document).ready(function() {
	ko.components.register('exercise-picker', {
	    template: {require: 'text!components/shared/exercise-picker/template.html'},
	    viewModel: ExercisePickerModel
	});
});