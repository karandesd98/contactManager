$(document).ready(function() {
	 showAllContacts();
});

function showAllContacts()
{
	$.ajax({
		url: '/user/getAllContact.json',
		type: 'GET',
		data: {
			
		},
		dataType: 'json',
		success: function(data) {
	
	     var boiler=`<table class="table table-hover">
                    <thead>
                    <tr><th colspan="7" class="text-center">My Contacts</th></tr>
                      <tr>
                      <th scope="col">Sr. No.</th>
                      <th scope="col">Name</th>
                      <th scope="col">Image</th>
                      <th scope="col">Nick Name</th>
                      <th scope="col">Email</th>
                      <th scope="col">Phone Number</th>
                      <th scope="col">Work</th>
                      <th scope="col">Action</th>
                     </tr>
                    </thead>`;
             
			if (data.isDataFound == "true") {
				var contact = data.contacts;
				boiler += `<tbody>`;
				
				contact.forEach(function(value,index){
					const{userContactId,name,nickName,EMAIL,phoneNumber,work,desc,imageName}=value;
					
					
					boiler +=`<tr id="userRow_${userContactId}">
					          <td>${++index}</td>
					          <td><lable class="contactName-label"> ${name}</lable></td>`;
					          
					          if(imageName=='')
					         boiler += `<td><img class="myProfile"  src="/images/defaltProfile.jpg" /></td>`;
                              else
					         boiler += `<td><img class="myProfile"  src="/images/${imageName}" /></td>`;
					          
					      boiler += `<td><lable class="nickName-label">${nickName}</lable></td>
					          <td><lable class="EMAIL-label"> ${EMAIL} </lable></td>
					          <td><lable class="phoneNumber-label"> ${phoneNumber} </lable></td>
					          <td><lable class="work-label">  ${work} </lable></td>
					          <td>
					           <div class="dropdown">
                                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">Action</button>
                               <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                 <li><a class="dropdown-item" href="#" onclick="editContact(${userContactId})">Edit</a></li>
                                 <li><a class="dropdown-item" href="#" onclick="deleteContact(${userContactId})">Delete</a></li>
                               </ul>
                             </div>
					      </td>
					     </tr>`;
				});
             
             boiler +=`  </tbody>`;

			}
			else {

			}
			
			boiler +=`</table>`; 
             
			$('#studentContact').html(boiler);
		},
		error: function(request, error) {
			alert("Request 1: " + JSON.stringify(request));
		}
	});	
}

 function deleteContact(userContactId)
 {
	 $.ajax({
		url: '/user/deleteContact.json',
		type: 'GET',
		data: {
			userContactId:userContactId
		},
		dataType: 'json',
		success: function(data) {
			showAllContacts();
			   alert(data.msg);
			},
		error: function(request, error) {
			alert("Request 1: " + JSON.stringify(request));
		}
	});		 
 }
 
 function editContact(userContactId)
 {
	 var contactpersonName = $(`#userRow_${userContactId}`).find('.contactName-label').text();
	 var nickName = $(`#userRow_${userContactId}`).find('.nickName-label').text();
	 var EMAIL = $(`#userRow_${userContactId}`).find('.EMAIL-label').text();
	 var phoneNumber = $(`#userRow_${userContactId}`).find('.phoneNumber-label').text();
	 var work = $(`#userRow_${userContactId}`).find('.work-label').text();
   
   
   var boiler=`<form  method="post">
			
				<div class="mb-3">
						<label for="userName" class="form-label">User Name :</label>
						<input type="text" name="username" class="form-control" id="userName" placeholder="Enter User Name" aria-describedby="emailHelp" value="${contactpersonName}">
				</div>
				
				<div class="mb-3">
						<label for="nickName" class="form-label">Nick Name :</label>
						<input type="text" name="nickName" class="form-control" id="nickName" placeholder="Enter User Name" aria-describedby="emailHelp" value="${nickName}">
				</div>
				
				<div class="mb-3">
						<label for="email" class="form-label">Email :</label>
						<input type="email" name="email" class="form-control" id="email" placeholder="Enter Email Id" aria-describedby="emailHelp" value="${EMAIL}">
				</div>
				
				<div class="mb-3">
						<label for="phoneNumber" class="form-label">Phone Number :</label>
						<input type="text" name="phoneNumber" class="form-control" id="phoneNumber" placeholder="Enter Phone Number" aria-describedby="emailHelp" value="${phoneNumber}">
				</div>
				
				<div class="mb-3">
						<label for="work" class="form-label">Work :</label>
						<input type="text" name="work" class="form-control" id="work" placeholder="Enter Work" aria-describedby="emailHelp" value="${work}">
				</div>
				

				</form>`;
   

            var modelFooter=`<button type="button" class="btn btn-primary" onclick="updateContact(${userContactId})">Save</button>
                             <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`;
   
    $("#editContact").html(boiler);
    $("#modelFooter").html(modelFooter);
    $("#editContactModel").modal('show');
 
 }
 
 
 function updateContact(userContactId)
 {
	 var userName = $("#userName").val();
	var nickName = $("#nickName").val();
	var email = $("#email").val();
	var phoneNumber = $("#phoneNumber").val();
	var work = $("#work").val();
	
	$.ajax({
		url: '/user/updateContact.json',
		type: 'POST',
		data: {
			userName: userName,
			nickName: nickName,
			email: email,
			phoneNumber: phoneNumber,
			work : work,
			userContactId:userContactId
		},
		dataType: 'json',
		success: function(data) {
			showAllContacts();
		    $("#editContactModel").modal('hide');

		},
		error: function(request, error) {
			alert("Request 1: " + JSON.stringify(request));
		}
	});
	
 }