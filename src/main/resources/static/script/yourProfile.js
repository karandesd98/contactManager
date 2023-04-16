$(document).ready(function() {
	 showMyProfile();
});


function showMyProfile()
{
  // swal("Good job!", "You clicked the button!", "success");
  $.ajax({
		url: '/user/getUserProfile.json',
		type: 'GET',
		data: {
			
		},
		dataType: 'json',
		success: function(data) {
	
	var myProfile =`<table class="table table-success table-striped text-center">
                  
                   <tr >
                   <th scope="row" colspan="2">   <lord-icon src="https://cdn.lordicon.com/jnlncdtk.json" trigger="hover" style="width:250px;height:250px"></lord-icon></th>
                   </tr>
                  
                  <tr >
                      <th scope="row" colspan="2">${data.UsernName}</th>
                   </tr>
                  
                   <tr>
                      <th scope="row">User Id</th>
                      <td>juno@990900</td>
                   </tr>
                   
                   <tr>
                      <th scope="row">Email</th>
                      <td>${data.email}</td>
                   </tr>
                   
                   <tr>
                      <th scope="row">Role</th>
                      <td>Usr</td>
                   </tr>
                   
                   <tr>
                      <th scope="row">Account Active</th>
                      <td>Yse</td>
                   </tr>
                   
                   <tr>
                      <th scope="row">About You</th>
                      <td>You are normal user...</td>
                   </tr>
                  </table>`;
             $('#myProfile').html(myProfile);

		},
		error: function(request, error) {
			alert("Request 1: " + JSON.stringify(request));
		}
	});	
  
  
  }

