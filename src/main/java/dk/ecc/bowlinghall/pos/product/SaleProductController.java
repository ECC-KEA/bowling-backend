package dk.ecc.bowlinghall.pos.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SaleProductController {
    private final SaleProductService saleProductService;

    public SaleProductController(SaleProductService saleProductService) {
        this.saleProductService = saleProductService;
    }

    @GetMapping("/sale-products")
    public List<SaleProduct> getSaleProducts() {
        return saleProductService.getSaleProducts();
    }

    @PostMapping("/sale-products")
    public ResponseEntity<SaleProductDTO> createSaleProduct(SaleProductDTO saleProduct) {
        var responseDTO = saleProductService.addSaleProduct(saleProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
