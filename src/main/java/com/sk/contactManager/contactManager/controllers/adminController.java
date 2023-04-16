package com.sk.contactManager.contactManager.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sk.contactManager.contactManager.Mangagers.UserManager;
import com.sk.contactManager.contactManager.Models.User;

@Controller
@RequestMapping("/user")
public class adminController {
	
	@Autowired 
	public UserManager userManager;
	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal)
	{
		String userName= principal.getName();
		User user = userManager.getUserByUserName(userName);
		model.addAttribute("user", user);
	}
	
	@GetMapping("/userDashBoard")
	public String signin(Model model,Principal principal)
	{
		String userName= principal.getName();
		User user = userManager.getUserByUserName(userName);
	
		
		
		if (user.getROLE().equalsIgnoreCase("USER")) {
			model.addAttribute("user", user);
			return "userDashBoard";
		} else {
			model.addAttribute("title", "home page");
			return "home";
		}
		
	}
	
	
	@GetMapping("/addContactForm")
	public String addContactForm(Model model,Principal principal)
	{
	model.addAttribute("title", "add contact ");
	return 	"addContactForm";
	}
	
	@GetMapping("/userHome")
	public String userHome(Model model,Principal principal)
	{
	model.addAttribute("title", "Home Page");
	return 	"userHome";
	}
	
	@GetMapping("/viewContact")
	public String viewContact(Model model,Principal principal)
	{
	model.addAttribute("title", "viewContact");
	return 	"viewContact";
	}
	
	@GetMapping("/yourProfile")
	public String yourProfile(Model model,Principal principal)
	{
	model.addAttribute("title", "Your Profile");
	return 	"yourProfile";
	}
	
	@GetMapping("/userSetting")
	public String userSetting(Model model,Principal principal)
	{
	model.addAttribute("title", "Setting");
	return 	"userSetting";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// data processing logic
	
	
	@PostMapping("/saveContact.json")
	@ResponseBody
	public String doRegister(HttpServletRequest req, Principal principal,@RequestParam("imageFile") MultipartFile  file) throws IOException
	{
		
	String userName=req.getParameter("userName")!=null?req.getParameter("userName"):"";
	String nickName=req.getParameter("nickName")!=null?req.getParameter("nickName"):"";
	String email=req.getParameter("email")!=null?req.getParameter("email"):"";
	String 	phoneNumber=req.getParameter("phoneNumber")!=null?req.getParameter("phoneNumber"):"";
	String work=req.getParameter("work")!=null?req.getParameter("work"):"";
	String 	desc=req.getParameter("desc")!=null?req.getParameter("desc"):"";
	String fileName="";
	if(!file.isEmpty())
	{
		fileName=file.getOriginalFilename();
		
	File uploadfile=	new ClassPathResource("static/images").getFile();
    Path path	=Paths.get(uploadfile.getAbsolutePath()+File.separator+fileName);
	Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	}

	
	String userNameLogin= principal.getName();
	User user = userManager.getUserByUserName(userNameLogin);

	 userManager.saveContact(userName,nickName,email,phoneNumber,work,desc,user.getUSER_ID(),fileName);
	
	System.out.println(userName);
	JsonObject jobj=new JsonObject();
	 jobj.addProperty("msg", "Contact Saved Successfully !!!");
	
		return new Gson().toJson(jobj);
	}
	
	@GetMapping("/getAllContact.json")
	@ResponseBody
	public String getAllContact(HttpServletRequest req, Principal principal)
	{
		
		String userNameLogin= principal.getName();
		User user = userManager.getUserByUserName(userNameLogin);
		Integer userId= user.getUSER_ID();
		
	Map<Integer,List<Object[]>> myCoMap=	userManager.getMyAllContact(userId);
	
	JsonObject jobj=new JsonObject();
	if (myCoMap.containsKey(0)) {
		List<Object[]> userContactList = myCoMap.get(0);
		JsonArray jArray=new JsonArray();
		for(Object[] contacts : userContactList)
		{
			Integer userContactId=contacts[0]!=null? Integer.parseInt(contacts[0].toString()) :0;
			String name=contacts[1]!=null?contacts[1].toString():"";
			String nickName=contacts[2]!=null?contacts[2].toString():"";
			String EMAIL=contacts[3]!=null?contacts[3].toString():"";
			String phoneNumber=contacts[4]!=null?contacts[4].toString():"";
			String work=contacts[5]!=null?contacts[5].toString():"";
			String desc=contacts[6]!=null?contacts[6].toString():"";
			String imageName=contacts[8]!=null?contacts[8].toString():"";
			
			JsonObject contact=new JsonObject();
			
			contact.addProperty("userContactId", userContactId);
			contact.addProperty("name", name);
			contact.addProperty("nickName", nickName);
			contact.addProperty("EMAIL", EMAIL);
			contact.addProperty("phoneNumber", phoneNumber);
			contact.addProperty("work", work);
			contact.addProperty("desc", desc);
			contact.addProperty("imageName", imageName);
			jArray.add(contact);
		}
		jobj.addProperty("isDataFound", "true");
		jobj.add("contacts", jArray);
		
	} else {
		jobj.addProperty("isDataFound", "false");
		jobj.addProperty("msg", "welcome sachin in software development business");
	}
		
	return new Gson().toJson(jobj);
	}
	
	@GetMapping("/deleteContact.json")
	@ResponseBody
	public String deleteContact(HttpServletRequest req, Principal principal)
	{
	Integer userContactId=req.getParameter("userContactId")!=null ?Integer.parseInt(req.getParameter("userContactId")) :0;
	Integer res=userManager.deleteContact(userContactId);
	
	JsonObject jobj=new JsonObject();

	if (res > 0) {
		jobj.addProperty("isDataFound", "false");
		jobj.addProperty("msg", "Contact Deleted Successfully.. !!!");
	} else {
		jobj.addProperty("isDataFound", "false");
		jobj.addProperty("msg", "Record not deleted");
	}
	
	return new Gson().toJson(jobj);
	}
	
	
	@PostMapping("/updateContact.json") 
	@ResponseBody
	public String updateContact(HttpServletRequest req, Principal principal)
	{
		
	String userName=req.getParameter("userName")!=null?req.getParameter("userName"):"";
	String nickName=req.getParameter("nickName")!=null?req.getParameter("nickName"):"";
	String email=req.getParameter("email")!=null?req.getParameter("email"):"";
	String 	phoneNumber=req.getParameter("phoneNumber")!=null?req.getParameter("phoneNumber"):"";
	String work=req.getParameter("work")!=null?req.getParameter("work"):"";
	Integer userContactId=req.getParameter("userContactId")!=null?Integer.parseInt(req.getParameter("userContactId")) :0;
	

	 userManager.updateContact(userName,nickName,email,phoneNumber,work,userContactId);
	//  userManager.saveContact(userName,nickName,email,phoneNumber,work,desc,user.getUSER_ID());
	
	System.out.println(userName);
	JsonObject jobj=new JsonObject();
	 jobj.addProperty("msg", "Contact Saved Successfully !!!");
	
		return new Gson().toJson(jobj);
	}
	
	
	
	@RequestMapping(value="/downloadContactTemp.json", method = RequestMethod.GET)
	public @ResponseBody void downloadContactTemp(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		
		//excel creating
		String DATE_FORMAT_NOW = "MMM-dd-yyyy_HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String uniqueTime = sdf.format(cal.getTime()) + ".xls";// for creating
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=StudentModerationMarksEntry" + uniqueTime);
		try{
			ServletOutputStream out = response.getOutputStream();
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet realSheet = workbook.createSheet("Student Moderation Marks List");
			realSheet.setColumnWidth(0, 256 * 25);
			realSheet.setColumnWidth(1, 256 * 25);
			realSheet.setColumnWidth(3, 256 * 25);
			realSheet.setColumnWidth(4, 256 * 25);
			realSheet.setColumnWidth(5, 256 * 25);
			realSheet.setColumnWidth(6, 256 * 25);
			realSheet.setColumnWidth(7, 256 * 25);
			
			org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
			headerFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
			CellStyle style2 = workbook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			style2.setFont(headerFont);
			
			CellStyle style3 = workbook.createCellStyle();
			style3.setAlignment(CellStyle.ALIGN_LEFT);
			style3.setVerticalAlignment(CellStyle.ALIGN_LEFT);
			style3.setFont(headerFont);
			
			CellStyle style1 = workbook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_RIGHT);
			style1.setVerticalAlignment(CellStyle.ALIGN_RIGHT);
			/*style1.setFont(headerFont);*/

			CellStyle style = workbook.createCellStyle();
			CellStyle simpleStyle = workbook.createCellStyle();
			simpleStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			CellStyle avgStyle = workbook.createCellStyle();
			avgStyle.setAlignment(CellStyle.ALIGN_RIGHT);

			CellStyle courseStyle = workbook.createCellStyle();
			org.apache.poi.ss.usermodel.Font avgFont = workbook.createFont();
			avgFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
			avgStyle.setFont(avgFont);
			org.apache.poi.ss.usermodel.Font courseFont = workbook.createFont();
			courseFont.setFontName("Lucida Grande");
			courseFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
			courseStyle.setFont(courseFont);
			courseStyle.setAlignment(CellStyle.ALIGN_LEFT);
			headerFont = workbook.createFont();
			headerFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setFont(headerFont);
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			// ===============================heading
		


			// *****************************************************************************//*
			
			HSSFRow blankRow = realSheet.createRow(2);
			
			HSSFCell blankcell = blankRow.createCell(0);
			blankcell.setCellValue("Sr. No.");
			blankcell.setCellStyle(style);
			
			blankcell = blankRow.createCell(1);
			blankcell.setCellValue("User Name ");
			blankcell.setCellStyle(style);
			
			blankcell = blankRow.createCell(2);
			blankcell.setCellValue("Nick Name");
			blankcell.setCellStyle(style);
			
			blankcell = blankRow.createCell(3);
			blankcell.setCellValue("Email");
			blankcell.setCellStyle(style);
			
			blankcell = blankRow.createCell(4);
			blankcell.setCellValue("Phone Number");
			blankcell.setCellStyle(style);
			
			blankcell = blankRow.createCell(5);
			blankcell.setCellValue("Work ");
			blankcell.setCellStyle(style);
			
			blankcell = blankRow.createCell(6);
			blankcell.setCellValue("Contact Description ");
			blankcell.setCellStyle(style);
			
			workbook.write(out);
			out.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	
	@RequestMapping(value="/uploadUserContact.json", method = RequestMethod.POST)
	public @ResponseBody String uploadUserContact(HttpSession session,HttpServletRequest request,@RequestParam("fileData")CommonsMultipartFile fileData) throws  IOException
	{
		Long fileSize = null;
		boolean fileSizeFlag = true;
		String strFileSize = request.getParameter("fileSize");
		
		if(strFileSize != null){
			fileSize = Long.parseLong(strFileSize);
		}
		if(fileSize != null){
			if(fileData.getSize() > fileSize){
				fileSizeFlag = false;
			}
		}
		if(fileSizeFlag){
	/*		JsonObject jObject = validateStudentAbsoluteModerationProcessMarksExcelSheet(fileData);
			
			String flag = jObject.get("responseFlag").toString();
			JsonArray examInternalExternalMarksIds = new JsonArray();
			flag = flag.substring(1,flag.length() - 1);
			if(!flag.equalsIgnoreCase("0")){
				List<ExamInternalExternalMark> examInternalExternalMarksList = readAbsoluteModerationProcessStudentMarksExcelSheet(fileData,request,session);
			}
			jObject.add("generatedIds", examInternalExternalMarksIds);
	return new Gson().toJson(jObject); 	*/	
		} else {
			JsonObject jObject = new JsonObject();
			jObject.addProperty("responseFlag", "-1");
			jObject.addProperty("responseText", "File size is larger than specified size");
			return new Gson().toJson(jObject);			
		}
		return strFileSize;
	}
	

	@GetMapping("/getUserProfile.json")
	@ResponseBody
	public String getUserProfile(HttpServletRequest req, Principal principal)
	{
		
		String userNameLogin= principal.getName();
		User user = userManager.getUserByUserName(userNameLogin);
		Integer userId= user.getUSER_ID();
		
		JsonObject jobj=new JsonObject();
		jobj.addProperty("UsernName", user.getUSER_NAME());
		jobj.addProperty("email", user.getEMAIL());
		
		return new Gson().toJson(jobj);	

	}
	
	
}
