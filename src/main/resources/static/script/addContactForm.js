
 $(document).ready(function(){
        	
           new AjaxUpload('contactUpload',{
                action : 'uploadUserContact.json',
                name : 'fileData',
             //   size:AllowedFileSize.EXCELSHEET_UPLOAD,
                data:{},
                onSubmit : function(file, extension) {
                	 $('#intExtUploadProgressBar').attr('value','');
              		$('#intExtUploadProgressBar').text(0);
                    if (extension == 'xls') {                 
                        this.setData({
                          //  fileSize:AllowedFileSize.EXCELSHEET_UPLOAD
                        });
                        return true;
                    } else {
                        jAlert('File type is not valid!');
                        return false;
                    }
                   
                },
                onComplete : function(file, response) {

                     var jObject = eval('('+response+')');
                    var array = new Array();
                    if(jObject["responseFlag"] != '0'){
                        $('#responseData').html('<div class="alert alert-success"><button type="button" class="close"  data-dismiss="alert">X</button><i class = "icon-ok"></i>&nbsp;&nbsp;File has been uploaded successfully.</div>');
                        getCustomAlertMessage("alert_update");
                       
                   	 $('#intExtUploadProgressBar').attr('value','100');
             		$('#intExtUploadProgressBar').text(100);
                    } else
                    	if(jObject["responseFlag"] == '1'){
                    		$('#responseData').show();
                    		$('#responseData').html('<div class="alert alert-success"><button type="button" class="close"  data-dismiss="alert">X</button><i class = "icon-ok"></i>&nbsp;&nbsp;File has been uploaded successfully.</div>');
                    		$('#errorResponseData').hide();

                    		setTimeout(function(){
                    			$('#responseData').hide();
                    		},20000); 
                    		getCustomAlertMessage("alert_update");
                    	//	showStudentIntExtMaks(subjectCode,subjectName,noOfStudent,examScheduleTimeTableDetailsId,universitySubjectId,instituteCourseSrethId,semesterId);
                    		$('#intExtUploadProgressBar').val(100);
                    	} else if(jObject["responseFlag"] == '0'){
                    		$('#responseData').hide();
                    		$('#errorResponseData').show();
                    		var boiler = '<div  class="alert alert-danger"><button type="button" class="close"  data-dismiss="alert">X</button><strong>Uploading failed. </strong><br>';
                    		var errorList  = jObject['ErrorList'];
                    		boiler+='<table>';
                    		if(errorList != undefined){
                    			for(var i = 0 ; i < errorList.length ; i++){
                    				boiler += '<tr><td style="font:13px/16px Lucida Grande, Lucida Sans, Arial, sans-serif; "><span  alert alert-error style="font:13px/16px Lucida Grande, Lucida Sans, Arial, sans-serif;">'+errorList[i]["ErrorName"]+'&nbsp;&nbsp;</span></td></tr>';
                    			}
                    			boiler+='</table>';
                    		}
                    		boiler += '</div>';
                    		$('#errorResponseData').html(boiler);
                    		$('#intExtUploadProgressBar').val(50);
                    		
                    		
                    	}
                    $('#intExtUploadProgressBar').val('');
                    
                }
            });
        });







function saveContact()
{
	
	var userName = $("#userName").val();
	var nickName = $("#nickName").val();
	var email = $("#email").val();
	var phoneNumber = $("#phoneNumber").val();
	var work = $("#work").val();
	var desc = $("#desc").val();
	
	var file = $('#imageUpload')[0].files[0];
	var formData = new FormData();
	formData.append('imageFile', file);
	formData.append('userName', userName);
	formData.append('nickName', nickName);
	formData.append('email', email);
	formData.append('phoneNumber', phoneNumber);
	formData.append('work', work);
	formData.append('desc', desc);
	
	$.ajax({
		url: '/user/saveContact.json',
		type: 'POST',
		data: formData,        
        contentType: false,   
        processData: false,
		success: function(data) {
			swal("Good job!", "Contact Added...", "success");
		},
		error: function(request, error) {
			alert("Request 1: " + JSON.stringify(request));
		}
	});
	

}

function uploadExcel()
{
var allReadyBind= $('#uploadContatDiv').html();	

var	boiler = '<br><br><table id = "uploadExcelForAbsoluteMarks" class = "table table-bordered" style="display:none;">'+
										  '<tr><td width = "100%" colspan = "2"><div class="alert alert-info" style="color:black">'+
										  '<strong>Upload</strong> Multiple Contacts. To download the template &nbsp;<span><a href = "javascript:void(0);" onclick ="downloadContactTemp()" >click here</a></span></div><br/>'+
										  '<input type="file" id ="contactUpload" name = "fileData"><br/><progress id="intExtUploadProgressBar" value="1" max="100"></progress>'+
										  '<div id = "responseData"></div><div id = "errorResponseData"></div>'+
										  '<div id = "responseDataforMarksEntry"></div>'+
										  '<div id = "errorResponseDataforMarksEntry"></div></td></tr></table>';
	
	if(allReadyBind=='')
	$('#uploadContatDiv').html(boiler);
	
 $('#uploadExcelForAbsoluteMarks').toggle(1000);
									 									  
}

function downloadContactTemp(){
	window.location.replace('downloadContactTemp.json');
}