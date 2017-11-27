$(document).ready(function() {
	// $('.error').hide();
	$('#btnSubmit').click(function(event) {

		var name = $('#computerName').val();
		var introduced = $('#introduced').val();
		var discontinued = $('#discontinued').val();
		var error = false;
		
		if (name.length > 3) {
			$("#computerName").removeClass("error");
		}
		else {
			$("#computerName").addClass("error");
			error = true;
		}
		
		if (isValidDate(introduced)) {
			$("#introduced").removeClass("error");
			// $('.error').hide();
		} else {
			$("#introduced").addClass("error");

//			$("#nameError").html("Name should contain atleast 5 characters");
			
//			$( "<div class=\"alert alert-warning\">Test</div>" ).insertAfter("#introduced" )
//			// $("#introducedError").addClass("alert alert-danger");
			// $("#introducedError").attr("align", "left")

			// $('.error').show();
			error = true;
		}
		
		if (isValidDate(discontinued)) {
			$("#discontinued").removeClass("error");
		} else {
			$("#discontinued").addClass("error");
			error = true;
		}
		
		if (error)
		{
			event.preventDefault();
		}
	});
});

function isValidDate(dateString) {
	if (dateString == null || dateString == "") {
		return true;
	}
	var regEx = /^\d{2}-\d{2}-\d{4}$/;
	if (!dateString.match(regEx))
		return false; // Invalid format

	var date = dateString.split("-");
    var d = parseInt(date[0], 10),
        m = parseInt(date[1], 10),
        y = parseInt(date[2], 10);
    
	var d = new Date(y, m - 1, d);
	
	if (!d.getTime())
		return false; // Invalid date (or this could be epoch)
	return true;
}
