package com.example.shoesshop.service;

import com.example.shoesshop.entity.Sale;
import com.example.shoesshop.repository.SaleRepository;
import com.example.shoesshop.request.SaleRequest;
import com.example.shoesshop.specification.SaleSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    //tạo schedule cứ 30s sẽ tự động update những sale_percent = 0 nếu sale đó hết hạn
    @Scheduled(fixedRate = 30000) // 30 seconds in milliseconds
    @Transactional
    public void scheduleUpdateExpiredSales() {
        updateExpiredSales();
        log.info("Scheduled task executed: Updated expired sales.");
    }

    @Transactional
    public void updateExpiredSales() {
        saleRepository.updateExpiredSales();
    }

    //get All Sales
    public Page<Sale> getAllSale(Pageable pageable, String search) {
        Specification<Sale> where = null;
        if (!StringUtils.isEmpty(search)) {
            SaleSpecification specification = new SaleSpecification("sale_info", "LIKE", search);
            where = Specification.where(specification);
        }
        return saleRepository.findAll(Specification.where(where), pageable);
    }

    //create new sale
    public void createSale(SaleRequest saleRequest) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start_sale = LocalDate.parse(saleRequest.getStart_date(), formatter);
        LocalDate end_sale = LocalDate.parse(saleRequest.getEnd_date(), formatter);
        System.out.println("Start Date: " + start_sale);
        System.out.println("End Date: " + end_sale);

        Sale sale = new Sale();
        sale.setSale_info(saleRequest.getSale_info());
        sale.setPercent_sale(saleRequest.getPercent_sale());
        sale.setStart_date(start_sale);
        sale.setEnd_date(end_sale);

        saleRepository.save(sale);
    }

    //update sale
    public void updateSale(int id, SaleRequest saleRequest) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start_sale = LocalDate.parse(saleRequest.getStart_date(), formatter);
        LocalDate end_sale = LocalDate.parse(saleRequest.getEnd_date(), formatter);

        Sale sale = saleRepository.findById(id);
        sale.setSale_info(saleRequest.getSale_info());
        sale.setPercent_sale(saleRequest.getPercent_sale());
        sale.setStart_date(start_sale);
        sale.setEnd_date(end_sale);
        saleRepository.save(sale);
    }

    //get sale id
    public Sale getSaleById(int id) {
        return saleRepository.getSaleById(id);
    }

    //delete sale
    public void deleteSale(int id) {
        saleRepository.deleteById(id);
    }

    //delete multiple sales
    public void deleteSales(List<Integer> ids) {
        saleRepository.deleteByIds(ids);
    }
}
