var MainModel = {	
	
	displaySuccessAlert : function(text) {
		MainModel.clearErrorAlert();
        $('#mainSuccessAlertText').text(text);
        $('#mainSuccessAlert').show();
        $(document.body).scrollTop($('#mainSuccessAlert').offset().top-70);
    },
    
    clearSuccessAlert : function() {
        $('#mainSuccessAlertText').text('');
        $('#mainSuccessAlert').hide();
    },
    
    displayErrorAlert : function(text) {
    	MainModel.clearSuccessAlert();
        $('#mainErrorAlertText').text(text);
        $('#mainErrorAlert').show();
        $(document.body).scrollTop($('#mainErrorAlert').offset().top-70);
    },
    
    clearErrorAlert : function() {
        $('#mainErrorAlertText').text('');
        $('#mainErrorAlert').hide();
    },
    
    goToElement : function(id) {
    	$('html,body').animate(
			{scrollTop: $("#"+id).offset().top-116},
	       	'slow'
		);
    },
    
    showOverlay : function() {
    	$('#overlay').show();
    },
    
    hideOverlay : function() {
    	$('#overlay').hide();
    },
    
    init : function() {
    	$('#mainSuccessAlert').hide();
    	$('#mainErrorAlert').hide();
    },
    
	selfUrl : function() {
		return "http://"+$.url("hostname")+":"+$.url("port")+$.url("path");
	}
};

var Token = {
		
	validate : function() {
		var token = Token.get();
		var result = (token && !Token.isExpired());
		if (!result) {
			Token.clear();
		}
		return result;
	},
		
	isExpired : function () {
		var token = Token.get();
        return (token && token.expires_at && Token.isBeforeNow());
    },
    
    isBeforeNow : function() {
    	return (Token.get().expires_at < new Date().getTime());
    },

    setExpiresAt : function (token) {
        if (token) {
            var now = new Date();
            var minutes = parseInt(token.expires_in) / 60;
            token.expires_at = new Date(now.getTime() + minutes * 60000).getTime()
        }
    },
		
	set : function(token) {
		Token.setExpiresAt(token);
		$.localStorage.set("token", token);
	},
	
	get : function() {
		return $.localStorage.get("token");
	},
	
	clear : function() {
		$.localStorage.remove("token");
	}
};

var Application = {
	initializeBindings : function() {
		ko.applyBindings({}, document.getElementById("UserLogin"));
		ko.applyBindings({}, document.getElementById("tabs"));
		ko.applyBindings(UsersModel, document.getElementById('NavigationMenu'));
		ko.applyBindings({}, document.getElementById("initializer"));
	},
	
	configureCustomBindings : function() {
		ko.bindingHandlers.date = {
			    update: function(element, valueAccessor, allBindingsAccessor, viewModel) {
			        var value = valueAccessor(),
			            allBindings = allBindingsAccessor();
			        var valueUnwrapped = ko.utils.unwrapObservable(value);
			        var date = new Date(valueUnwrapped);
			        var hours = (date.getHours() < 10 ? "0"+date.getHours() : date.getHours());
			        var minutes = (date.getMinutes() < 10 ? "0"+date.getMinutes() : date.getMinutes());
			        var dateString = (date.getDate() < 10 ? "0"+date.toLocaleDateString() : date.toLocaleDateString());
			        $(element).text(dateString+", "+hours+":"+minutes);
			    }
			}
		
		ko.virtualElements.allowedBindings.date = true;
	},
	
	configureAmd : function() {
		// configure templating engine
		infuser.defaults.templateUrl = 'templates'; 
		infuser.defaults.templateSuffix = '.tmpl.html';
		
		// configure require.js plugins
		requirejs.config({
		    paths: {
		        "text": "/nfs/webjars/requirejs-text/2.0.14/text"
		    }
		});
	},
	
	configureSecurity : function() {
		$.ajaxPrefilter(function( options ) {
		    if (!options.beforeSend && (!options.headers || !options.headers['Authorization'])) {
		        options.beforeSend = function (xhr) { 
		        	if (Token.get()){ 
		        		if (Token.isExpired()) {
		        			xhr.abort();
			        		UsersModel.logout();
		        		} else {
		        			xhr.setRequestHeader('Authorization', 'Bearer '+Token.get().access_token);
		        		}
		        	}
		        }
		    }
		});
	}
};

$(document).ready(function() {
	
	Application.configureAmd();
	Application.configureCustomBindings();
	Application.configureSecurity();
	Application.initializeBindings();
	
	// initialize view
	MainModel.init();
});