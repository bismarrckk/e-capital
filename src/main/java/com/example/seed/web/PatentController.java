package com.example.seed.web;

import java.io.IOException;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.Patent;
import com.example.seed.service.BusinessLineService;
import com.example.seed.service.PatentService;
import com.example.seed.util.FileUploadUtil;
import com.example.seed.web.dto.PatentDto;

@Controller
@RequestMapping("/patents")
public class PatentController {
	@Autowired
	PatentService patentService;
	@Autowired
	BusinessLineService lineService;
	@Autowired
	ModelMapper mapper;
	
	@GetMapping("/create/{id}")
	public String createForm(Model model,@PathVariable(name="id") long id,RedirectAttributes attributes) {
		PatentDto patentDto=new PatentDto();
		BusinessLine businessLine=lineService.getLineById(id);
		Patent patent=patentService.getPatentByLine(businessLine);
		if(patent != null) {
			attributes.addFlashAttribute("message", "A patent for the selected business Line exists!");
			return "redirect:/businessLine";
		}
		long lineId=businessLine.getId();
		model.addAttribute("lineId", lineId);
		model.addAttribute("patent", patentDto);
		return "frontend/patent";
	}
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("patent") PatentDto patentDto,BindingResult bindingResult,
			@RequestParam("pdfFile") MultipartFile pdfFile,HttpServletRequest request,Model model,RedirectAttributes attributes) {
		long patentId=patentDto.getBusinessLine().getId();
		long businesslineId=patentDto.getBusinessLine().getId();
		if(!patentService.validateImageFile(pdfFile)) {
			
			if(patentId<1) {
				model.addAttribute("message", "Please choose a pdf file");
				return "frontend/patent";
			}
			attributes.addFlashAttribute("message", "Please choose a pdf file");
			return "redirect:/patents/edit/"+businesslineId;
		
		}
		String fileName = StringUtils.cleanPath(pdfFile.getOriginalFilename());
		String cleanFileName=fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
		long lineId=Long.parseLong(request.getParameter("businessLine"));
		BusinessLine line=lineService.getLineById(lineId);
		patentDto.setBusinessLine(line);
		
		String userHome="user.home";
		String path=System.getProperty(userHome);
		String uploadDir=path + "/uploads/";
		try {
			FileUploadUtil.saveFile(uploadDir, cleanFileName, pdfFile);
		} catch (IOException e) {
			attributes.addFlashAttribute("message", e);
			if(patentId<1) {
				return "frontend/patent";
			}else {
				return "redirect:/patents/edit/"+businesslineId;
			}
		}
		patentDto.setImage(cleanFileName);
		Patent patent=mapper.map(patentDto, Patent.class);
		patentService.save(patent);
		if(patentId<1) {
			attributes.addFlashAttribute("message","Patent saved successfylly!!" );
		}else {
			attributes.addFlashAttribute("message","Patent updated successfully!!" );
		}
		
		return"redirect:/businessLine";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable(name="id") long id,Model model,RedirectAttributes attributes) {
		try {
			BusinessLine line=lineService.getLineById(id);
			long lineId=line.getId();
			Patent patent=patentService.getPatentByLine(line);
			if(patent==null) {
				attributes.addFlashAttribute("message", "Patent not available,please register one");
				return "redirect:/businessLine";
			}
			model.addAttribute("lineId", lineId);
			model.addAttribute("patent", patent);
			return "frontend/patent";
		}catch(RuntimeException ex) {
			attributes.addFlashAttribute("message", ex);
			return "redirect:/businessLine";
		}
	}
}
