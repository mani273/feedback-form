//addquestion.html adding question
$(document).ready(function() {
	$('#submit1').click(function() {

		alert("Triggered")
		var question = {
			text1: $("#text1").val(),
			batch: $("#batch").val()
		};
		alert(JSON.stringify(question))


		$.ajax({
			type: 'post',
			url: '/question',
			contentType: "application/json",
			data: JSON.stringify(question),
			dataType: 'text',
			success: function(response) {
				location.href = response
			}
		});
	});
});




//java.html
$(document).ready(function() {
	$('#submit3').click(function() {
		alert("Triggered")
		var javaresponse = {
			name: $("#name").val(),
			phoneNumber: $("#phoneNumber").val(),
			email: $("#email").val(),
			q1: $("#select1").val(),
			q2: $("#select2").val(),
			q3: $("#select3").val(),
			q4: $("#select4").val(),
			q5: $("#select5").val(),
		};
		
		$.ajax({
			type: 'post',
			url: `/data/api/submit`,
			contenttype: 'application/json',
			data: {uu:JSON.stringify(javaresponse)},
			datatype: 'text',
			success: function(response) {
				location.href = response
			}
		});
	});
});






  $(document).ready(function() {
	$('#click').click(function() {
		alert("Triggered")
		var javadata = {
			text: $("#text1").val(),
			
		};
		
		$.ajax({
			type: 'post',
			url: `/data/api/javadata`,
			contenttype: 'application/json',
			data: {uu:JSON.stringify(javadata)},
			datatype: 'text',
			success: function(response) {
				location.href = response
			}
		});
	});
});
