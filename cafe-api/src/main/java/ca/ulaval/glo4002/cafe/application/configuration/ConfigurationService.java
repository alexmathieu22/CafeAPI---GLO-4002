package ca.ulaval.glo4002.cafe.application.configuration;

import ca.ulaval.glo4002.cafe.application.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeConfiguration;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;

public class ConfigurationService {
    CafeRepository cafeRepository;

    public ConfigurationService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
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
}
