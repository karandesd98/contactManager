function saveUser()
{
    var name=$("#name").val();
    var password=$("#password").val();
    var email=$("#email").val();
     var about=$("#about").val();

	$.ajax({
		url:'public/doRegister.json',
		type: 'GET',
		data: {
			name: name,
			password: password,
			email:email,
			about:about
		},
		dataType: 'json',
		success: function(data) {
			var boiler=`<h1>${data.msg}</h1>`;
			$('#msg').html(boiler);
			alert('Data: ' + data.msg);
		},
		error: function(request, error) {
			 alert("Request 1: " + JSON.stringify(request));
		}
});
}