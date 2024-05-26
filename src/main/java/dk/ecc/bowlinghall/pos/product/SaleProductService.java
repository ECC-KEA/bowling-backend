package dk.ecc.bowlinghall.pos.product;

import dk.ecc.bowlinghall.booking.bowling.BowlingBookingDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleProductService {

    private final SaleProductRepository saleProductRepository;

    public SaleProductService(SaleProductRepository saleProductRepository) {
        this.saleProductRepository = saleProductRepository;
    }

    private SaleProduct toEntity(SaleProductDTO requestDTO) {
        return new SaleProduct(
                requestDTO.name(),
                requestDTO.price()
        );
    }

    private SaleProductDTO toDTO(SaleProduct saleProduct) {
        return new SaleProductDTO(
                saleProduct.getId(),
                saleProduct.getName(),
                saleProduct.getPrice()
        );
    }

    public List<SaleProduct> getSaleProducts() {
        return saleProductRepository.findAll();
    }

    public SaleProductDTO addSaleProduct(SaleProductDTO saleProduct) {
        var product = toEntity(saleProduct);
        var savedProduct = saleProductRepository.save(product);
        return toDTO(savedProduct);
    }
}
