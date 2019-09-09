package com.healthcare.integrator1.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.healthcare.command.ProductCommand;
import com.healthcare.model.Customer;
import com.healthcare.model.Product;
import com.healthcare.model.Sales;
import com.healthcare.model.Transaction;
import com.healthcare.service.PharmacyService;
import com.healthcare.to.CustomerTO;
import com.healthcare.to.ProductTO;

@Controller
public class PharmacyController {
	@Autowired
	PharmacyService pharmacyService;
	
	
	@RequestMapping("/getAllProductName")
	public @ResponseBody List<String> getAllProductName() {
		List<String> list = pharmacyService.getAllProductName();
		return list;
	}
	
	@RequestMapping("/show_product") 
	public String showProduct(Model model) {
		ProductCommand command = new ProductCommand();
		model.addAttribute("productCommand",command);
		List<Product> list = pharmacyService.getAllProduct();
		model.addAttribute("product_details",list);
		return "pharmacy/product";
		}
	
	@RequestMapping("/show_sales_report") 
	public String showReport() {
		return "pharmacy/sales_report";
		}
	
	
	  @SuppressWarnings("unchecked")
	@RequestMapping(value="/get_transaction_by_date_json",method=RequestMethod.POST)
	  public @ResponseBody List<Map<Object,Object>> getTransactionByDateJson(@RequestParam String from,@RequestParam String to) throws ParseException {
		  	
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date fromDate = sdf1.parse(from);
			java.util.Date toDate = sdf1.parse(to);
			
			List<Transaction> list =  pharmacyService.getTransactionByDate(fromDate,toDate);
			Collections.sort(list);
			List<Map<Object,Object>> list1 = new ArrayList<>();
			
			for(int i=0;i<list.size();i++) {
			Map<Object,Object> map = new HashMap<>();
			map.put(LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getTransactionDate()) ),list.get(i).getBillAmount());
			list1.add(map);
		
			}
			
		  return list1 ;
	  }
	  
	  @RequestMapping(value="/get_transaction_by_date",method=RequestMethod.POST)
	  public String getTransactionByDate(@RequestParam("from") String from,@RequestParam("to") String to,Model model) throws ParseException {
		  	
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date fromDate = sdf1.parse(from);
			java.util.Date toDate = sdf1.parse(to);
			
			
		 List<Transaction> list =  pharmacyService.getTransactionByDate(fromDate,toDate);
		  model.addAttribute("transaction_details",list); 
		  return "pharmacy/sales_report";
	  }
	 
	
	@RequestMapping("/show_sale_customer")
	public String showSales() {
		return "pharmacy/sales_customer";
		}
	
	@RequestMapping("/show_stock_register")
	public String showStockRegister(Model model) {
		List<Product> list = pharmacyService.getAllProduct();
		model.addAttribute("product_details",list);
		return "pharmacy/stock_register";
		}	
	
	@RequestMapping(value="/add_customer",method=RequestMethod.POST)
	public String add_customer(@RequestParam("customerName") String customerName,@RequestParam("phone") String phone,Model model) {
		CustomerTO customerto = new CustomerTO(customerName,phone);
		String customerId = pharmacyService.add_customer(customerto);
		model.addAttribute("customerId",customerId);
		List<Product> list = pharmacyService.getAllProduct();
		model.addAttribute("product_details",list);
		return "pharmacy/sales_product";
		}	
	
	@RequestMapping(value="/add_sales",method=RequestMethod.POST)
	public String add_sales(@RequestParam("customerId") String customerId,@RequestParam("brandName")String brandName,@RequestParam("quantity")String quantity,Model model) {
			
			int quantity1 = Integer.parseInt(quantity);
			pharmacyService.add_sales(customerId, brandName, quantity1);
			List<Sales> list = pharmacyService.getSalesByCustomerId(customerId);
			double totalSum = 0;
			double totalProfit = 0;
			System.out.println(list.size());
			for(int i=0;i<list.size();i++) {
				 totalSum = totalSum+ list.get(i).getAmount();
				 totalProfit = totalProfit+ list.get(i).getProfit();
				 
			}
			model.addAttribute("customerId",customerId);
			model.addAttribute("totalProfit", totalProfit);
			model.addAttribute("totalSum",totalSum);
			model.addAttribute("sales_details",list);
			return "pharmacy/sales_product";
					
		}
	
		@RequestMapping(value="/invoice")
		public String checkout(@RequestParam("customerId") String customerId,Model model) throws ParseException {
			
			
			List<Sales> sales = pharmacyService.getSalesByCustomerId(customerId);
			Customer customer = pharmacyService.getCustomerById(customerId);
			double totalAmount = 0;
			double totalProfit =0;
			for(int i=0;i<sales.size();i++) {
				 totalAmount = totalAmount+ sales.get(i).getAmount();
				 totalProfit = totalProfit + sales.get(i).getProfit();
				 
			}
			double taxAmount = (10.0/100)*totalAmount;
			double billAmount = totalAmount+taxAmount;
			pharmacyService.addTransaction(customer, sales,totalAmount,taxAmount,billAmount,totalProfit);
			model.addAttribute("totalAmount",totalAmount);
			model.addAttribute("taxAmount",taxAmount);
			model.addAttribute("billAmount",billAmount);
			model.addAttribute("customerId",customerId);
			model.addAttribute("sales_details",sales);
			model.addAttribute("customer",customer);
			return "pharmacy/sales_invoice";
					
		}
	
	
		
	 
	
	@RequestMapping("/delete_sales")
	public String delete_sales(@RequestParam("salesId") String salesId,@RequestParam("customerId") String customerId,Model model) {
		
			pharmacyService.deleteSalesBySalesId(salesId);
			List<Sales> list = pharmacyService.getSalesByCustomerId(customerId);
			double totalSum = 0;
			double totalProfit = 0;
			for(int i=0;i<list.size();i++) {
				 totalSum =+ list.get(i).getAmount();
				 totalProfit =+ list.get(i).getProfit();
			}
			model.addAttribute("customerId",customerId);
			model.addAttribute("totalProfit", totalProfit);
			model.addAttribute("totalSum",totalSum);
			model.addAttribute("sales_details",list);
			return "pharmacy/sales_product";
					
		}
		
	@RequestMapping("/back_from_invoice")
	public String backFromInvoice(@RequestParam("customerId") String customerId,Model model) {
		
			List<Sales> list = pharmacyService.getSalesByCustomerId(customerId);
			double totalSum = 0;
			double totalProfit = 0;
			for(int i=0;i<list.size();i++) {
				 totalSum =+ list.get(i).getAmount();
				 totalProfit =+ list.get(i).getProfit();
			}
			model.addAttribute("customerId",customerId);
			model.addAttribute("totalProfit", totalProfit);
			model.addAttribute("totalSum",totalSum);
			model.addAttribute("sales_details",list);
			return "pharmacy/sales_product";
					
		}
	
	
	@RequestMapping("/add_product")
	public String addProduct(@ModelAttribute ProductCommand command,Model model) throws ParseException {
		double sellingPrice = Double.parseDouble(command.getSellingPrice());
		double originalPrice = Double.parseDouble(command.getOriginalPrice());
		double profit = Double.parseDouble(command.getProfit());
		int quantity = Integer.parseInt(command.getQuantity());
		String startDate = command.getExpiryDate();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date1 = sdf1.parse(startDate);
		java.sql.Date sqlStartDate = new java.sql.Date(date1.getTime());
		Date expiryDate = sqlStartDate;
		ProductTO  productto = new ProductTO(command.getBrandName(),command.getGenericName(),command.getFormulation(),
				command.getIndication(),command.getArrivalDate(),expiryDate,sellingPrice,originalPrice,
				profit,command.getSupplier(),quantity);
		pharmacyService.addProduct(productto);
		List<Product> list = pharmacyService.getAllProduct();
		model.addAttribute("product_details",list);
		return "pharmacy/product";
		}	
	
	
	@RequestMapping("/delete_product")
	public void deleteProductById(@RequestParam("productId")String productId) {
		pharmacyService.deleteProductById(productId);
	}
	
	@RequestMapping("/back_from_sales")
	public String  deleteCustomerById(@RequestParam("customerId")String customerId) {
		pharmacyService.deleteSalesByCustomerId(customerId);
		pharmacyService.deleteCustomerById(customerId);
		return "pharmacy/sales_customer";
		
	}
	

	@RequestMapping("/update_product")
	public String updateProductById(@ModelAttribute ProductCommand command,@RequestParam("productId")String productId,Model model) throws ParseException {
		double sellingPrice = Double.parseDouble(command.getSellingPrice());
		double originalPrice = Double.parseDouble(command.getOriginalPrice());
		double profit = Double.parseDouble(command.getProfit());
		int quantity = Integer.parseInt(command.getQuantity());
		String startDate = command.getExpiryDate();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date1 = sdf1.parse(startDate);
		java.sql.Date sqlStartDate = new java.sql.Date(date1.getTime());
		Date expiryDate = sqlStartDate;
		ProductTO  productto = new ProductTO(command.getBrandName(),command.getGenericName(),command.getFormulation(),
				command.getIndication(),command.getArrivalDate(),expiryDate,sellingPrice,originalPrice,
				profit,command.getSupplier(),quantity);
		
		pharmacyService.updateProductById(productto,productId);
		List<Product> list = pharmacyService.getAllProduct();
		model.addAttribute("product_details",list);
		
		return "pharmacy/product";
	}
	

	
	


		

}
