package com.example.demo.admincontroller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.adminService.AdminBusinessService;



@RestController
@RequestMapping("admin/business")
public class AdminBussinessController {
 
	  AdminBusinessService adminBusinesService;
	  
	  public AdminBussinessController(AdminBusinessService adminBusinesService) {
		     this.adminBusinesService=adminBusinesService;
	  }
	  
	  @GetMapping("/monthly")
	    public ResponseEntity<?> getMonthlyBusiness(@RequestParam int month, @RequestParam int year) {
	        try {
	            Map<String, Object> businessReport = adminBusinesService.calculateMonthlyBusiness(month, year);
	            return ResponseEntity.status(HttpStatus.OK).body(businessReport);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
	        }
	    }

	    @GetMapping("/daily")
	    public ResponseEntity<?> getDailyBusiness(@RequestParam String date) {
	        try {
	            LocalDate localDate = LocalDate.parse(date);
	            Map<String, Object> businessReport = adminBusinesService.calculateDailyBusiness(localDate);
	            return ResponseEntity.status(HttpStatus.OK).body(businessReport);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
	        }
	    }

	    @GetMapping("/yearly")
	    public ResponseEntity<?> getYearlyBusiness(@RequestParam int year) {
	        try {
	            Map<String, Object> businessReport = adminBusinesService.calculateYearlyBusiness(year);
	            return ResponseEntity.status(HttpStatus.OK).body(businessReport);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
	        }
	    }

	    @GetMapping("/overall")
	    public ResponseEntity<?> getOverallBusiness() {
	        try {
	            Map<String, Object> overallBusiness = adminBusinesService.calculateOverallBusiness();
	            return ResponseEntity.status(HttpStatus.OK).body(overallBusiness);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while calculating overall business");
	        }
	    }

	  
	  
	  
	
	
}
