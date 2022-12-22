package ca.ulaval.glo4002.cafe.application;

import java.util.List;

import ca.ulaval.glo4002.cafe.application.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.application.dto.LayoutDTO;
import ca.ulaval.glo4002.cafe.application.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.application.parameter.IngredientsParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeConfiguration;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;

public class CafeService {
    private final CafeRepository cafeRepository;
    private final CafeFactory cafeFactory;

    public CafeService(CafeRepository cafeRepository, CafeFactory cafeFactory) {
        this.cafeRepository = cafeRepository;
        this.cafeFactory = cafeFactory;
    }

    public LayoutDTO getLayout() {
        Cafe cafe = cafeRepository.get();
        return LayoutDTO.fromCafe(cafe);
    }

    public void updateConfiguration(ConfigurationParams configurationParams) {
        Cafe cafe = cafeRepository.get();
        CafeConfiguration cafeConfiguration = new CafeConfiguration(
            configurationParams.cubeSize(),
            configurationParams.cafeName(),
            configurationParams.reservationType(),
            configurationParams.location(),
            configurationParams.groupTipRate());
        cafe.updateConfiguration(cafeConfiguration);
        cafe.close();
        cafeRepository.saveOrUpdate(cafe);
    }

    public void closeCafe() {
        Cafe cafe = cafeRepository.get();
        cafe.close();
        cafeRepository.saveOrUpdate(cafe);
    }

    public void addIngredientsToInventory(IngredientsParams ingredientsParams) {
        Cafe cafe = cafeRepository.get();
        cafe.addIngredientsToInventory(
            List.of(ingredientsParams.chocolate(), ingredientsParams.milk(), ingredientsParams.water(), ingredientsParams.espresso()));
        cafeRepository.saveOrUpdate(cafe);
    }

    public InventoryDTO getInventory() {
        Cafe cafe = cafeRepository.get();
        return InventoryDTO.fromInventory(cafe.getInventory());
    }
}
