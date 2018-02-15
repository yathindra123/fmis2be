package com.ugnip.controllers;

import com.ugnip.models.item.Item;
import com.ugnip.repositories.ItemRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/items")
@CrossOrigin("*")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    //View all items
    @GetMapping
    public List<Item> viewAllItems(){
        Sort sortItemsByDescOrder = new Sort(Sort.Direction.DESC, "createdAt");
        return itemRepository.findAll(sortItemsByDescOrder);
    }

    //Create a new item
    @PostMapping
    public Item createItem(@Valid @RequestBody Item item){
        return itemRepository.save(item);
    }

    @GetMapping(value="/{barcode}")
    public ResponseEntity<Item> findItem(@PathVariable("barcode") String barcode) {
        Item item = itemRepository.findByBarcode(barcode);

        if(item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
    }

    //Update an item
    @PutMapping(value="/{barcode}")
    public ResponseEntity<Item> updateItem(@PathVariable("barcode") String barcode,
                                           @Valid @RequestBody Item item) {
        Item itemData = itemRepository.findByBarcode(barcode);

        //If item not found return not found
        if(itemData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //If item found update it
        itemData.setBarcode(item.getBarcode());
        itemData.setDescription(item.getDescription());
        itemData.setCost(item.getCost());
        itemData.setSupplier(item.getSupplier());
        itemData.setRoom(item.getRoom());
        Item updatedItem = itemRepository.save(itemData);

        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    //Delete an item
    @DeleteMapping(value="/{barcode}")
    public void deleteTodo(@PathVariable("barcode") String barcode) {
        itemRepository.deleteByBarcode(barcode);
    }

    //Get Report
    @RequestMapping(value = "/FMIS2Report.pdf", method = RequestMethod.GET)
    public @ResponseBody void genReport(HttpServletResponse response){
        try {
            InputStream jasperStream = this.getClass().getResourceAsStream("/FMIS2Report.jrxml");
            JasperDesign design = JRXmlLoader.load(jasperStream);
            JasperReport report = JasperCompileManager.compileReport(design);

            Map<String, Object> parameterMap = new HashMap<>();
            List<Item> items = itemRepository.findAll();
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(items);
            parameterMap.put("datasource", jrDataSource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameterMap, jrDataSource);
            response.setContentType("application/x-pdf");
            response.setHeader("Content-Disposition", "inline; filename=FMIS2Report.pdf");

            final OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        }
        catch (JRException ex){
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (Exception e){
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}