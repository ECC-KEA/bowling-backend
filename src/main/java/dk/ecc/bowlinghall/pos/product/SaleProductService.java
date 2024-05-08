package dk.ecc.bowlinghall.pos.product;

import org.springframework.stereotype.Service;

@Service
public class SaleProductService {

    private final SaleProductRepository saleProductRepository;

    public SaleProductService(SaleProductRepository saleProductRepository) {
        this.saleProductRepository = saleProductRepository;
    }
}
